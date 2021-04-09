package cn.caohongliang.gray.core.loadbalancer;

import cn.caohongliang.gray.core.flowcontrol.Constants;
import cn.caohongliang.gray.core.flowcontrol.FlowControlProperties;
import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.SelectedInstanceCallback;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
public class CustomLoadBalancer implements ReactorServiceInstanceLoadBalancer {

	private final String serviceId;
	private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;
	private final FlowControlProperties config;
	private final List<ServiceInstanceFilter> filters;

	public CustomLoadBalancer(String serviceId,
	                          ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider,
	                          FlowControlProperties config, List<ServiceInstanceFilter> filters) {
		this.serviceId = serviceId;
		this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
		this.config = config;
		this.filters = Optional.ofNullable(filters).orElse(new ArrayList<>(0));
	}

	@Override
	public Mono<Response<ServiceInstance>> choose(Request request) {
		ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider
				.getIfAvailable(NoopServiceInstanceListSupplier::new);
		return supplier.get(request).next()
				.map(serviceInstances -> processInstanceResponse(supplier, serviceInstances, request));
	}

	private Response<ServiceInstance> processInstanceResponse(
			ServiceInstanceListSupplier supplier,
			List<ServiceInstance> serviceInstances, Request request) {
		Response<ServiceInstance> serviceInstanceResponse = getInstanceResponse(serviceInstances, request);
		if (supplier instanceof SelectedInstanceCallback && serviceInstanceResponse.hasServer()) {
			((SelectedInstanceCallback) supplier).selectedServiceInstance(serviceInstanceResponse.getServer());
		}
		return serviceInstanceResponse;
	}

	private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, Request request) {
		DefaultRequestContext context = (DefaultRequestContext) request.getContext();
		RequestData requestData = (RequestData) context.getClientRequest();

		String base64Text = requestData.getHeaders().getFirst(Constants.ENVIRONMENT_VERSION_HEADER_NAME);
		Environment expectEnvironment = Environment.decode(base64Text);
		Response<ServiceInstance> response = chooseInstance(instances, expectEnvironment, requestData);
		if (response.hasServer()) {
			return response;
		}
		//没有选中的服务，看是否启用兜底策略
		if (!config.isOutStrategy()) {
			//没有启用兜底
			return response;
		}
		Environment currentEnvironment = config.getCurrent();
		if (Objects.equals(expectEnvironment, currentEnvironment)) {
			//环境信息一致，无需重复执行相同逻辑
			return response;
		}
		//启用兜底策略
		response = chooseInstance(instances, currentEnvironment, requestData);
		if (response.hasServer()) {
			ServiceInstance server = response.getServer();
			String instanceId = server.getServiceId() + "@@" + server.getHost() + "#" + server.getPort();
			log.info("启用兜底策略，匹配到服务实例：environment={}, version={}, instanceId={}",
					getEnvironmentName(currentEnvironment), getEnvironmentVersion(currentEnvironment),
					instanceId);
		} else {
			log.info("启用兜底策略，没有匹配到服务实例：environment={}, version={}",
					getEnvironmentName(currentEnvironment), getEnvironmentVersion(currentEnvironment));
		}
		return response;
	}

	private Response<ServiceInstance> chooseInstance(
			List<ServiceInstance> instances, Environment environment,
			RequestData requestData) {
		String environmentName = getEnvironmentName(environment);
		String environmentVersion = getEnvironmentVersion(environment);
		if (log.isDebugEnabled()) {
			log.debug("准备进行实例过滤，指定的环境为：environment={}, version={}", environmentName, environmentVersion);
		}

		ServiceInstanceFilterChain chain = new ServiceInstanceFilterChain(filters);
		instances = chain.doFilter(instances, environment, requestData);
		if (instances.isEmpty()) {
			if (log.isWarnEnabled()) {
				log.warn("没有可用的服务: serviceId={}, environment={}, version={}", serviceId, environmentName, environmentVersion);
			}
			return new EmptyResponse();
		}

		//按Nacos中配置的权重进行选择
		List<Pair<ServiceInstance>> pairs = instances.stream()
				.map(instance -> new Pair<>(instance, getWeight(instance)))
				.collect(Collectors.toList());
		Chooser<Object, ServiceInstance> chooser = new Chooser<>(null, pairs);
		return new DefaultResponse(chooser.randomWithWeight());
	}

	private String getEnvironmentVersion(Environment environment) {
		return environment != null ? environment.getVersion() : "";
	}

	private String getEnvironmentName(Environment environment) {
		return environment != null ? environment.getName() : "";
	}

	protected double getWeight(ServiceInstance serviceInstance) {
		return Double.parseDouble(serviceInstance.getMetadata().get("nacos.weight"));
	}
}
