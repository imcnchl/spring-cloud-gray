package cn.caohongliang.gray.core.flowcontrol;

import cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties;
import cn.caohongliang.gray.core.flowcontrol.config.Isolation;
import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import cn.caohongliang.gray.core.loadbalancer.ServiceInstanceFilter;
import cn.caohongliang.gray.core.loadbalancer.ServiceInstanceFilterChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 根据流控策略，对实例进行过滤
 *
 * @author caohongliang
 */
@Slf4j
public class StrategyServiceInstanceFilter implements ServiceInstanceFilter {
	@Autowired
	private FlowControlProperties config;

	@Override
	public boolean isEnable() {
		return config.isEnable();
	}

	@Override
	public List<ServiceInstance> doFilter(List<ServiceInstance> instances, Environment environment, RequestData requestData, ServiceInstanceFilterChain chain) {
		if (environment == null) {
			log.warn("指定环境为空，不对服务进行过滤");
			return chain.doFilter(instances, null, requestData);
		}
		instances = instances.stream()
				.filter(instance -> {
					String name = instance.getMetadata().get("environment.name");
					if (!environment.getName().equals(name)) {
						//环境不匹配
						return false;
					}
					if (StringUtils.hasText(environment.getVersion())) {
						//指定了版本信息，需要匹配版本信息
						String version = instance.getMetadata().get("environment.version");
						return environment.getVersion().equals(version);
					}
					return true;
				})
				.collect(Collectors.toList());

		if (log.isDebugEnabled()) {
			List<String> instanceIds = instances.stream().map(Isolation::getInstanceId).collect(Collectors.toList());
			log.debug("匹配到的实例：{}", instanceIds);
		}
		return chain.doFilter(instances, environment, requestData);
	}

	//	@Override
//	public List<ServiceInstance> filter(List<ServiceInstance> instances, Environment expectEnvironment, RequestData requestData) {
//		if (expectEnvironment == null) {
//			log.warn("指定环境为空，不对服务进行过滤");
//			return instances;
//		}
//		if (log.isDebugEnabled()) {
//			log.debug("准备进行实例过滤，指定的环境为：name={}, version={}", expectEnvironment.getName(), expectEnvironment.getVersion());
//		}
//		instances = instances.stream()
//				.filter(instance -> {
//					String name = instance.getMetadata().get("environment.name");
//					if (!expectEnvironment.getName().equals(name)) {
//						//环境不匹配
//						return false;
//					}
//					if (StringUtils.hasText(expectEnvironment.getVersion())) {
//						//指定了版本信息，需要匹配版本信息
//						String version = instance.getMetadata().get("environment.version");
//						return expectEnvironment.getVersion().equals(version);
//					}
//					return true;
//				})
//				.collect(Collectors.toList());
//
//		if (log.isDebugEnabled()) {
//			List<String> instanceIds = instances.stream().map(ServiceInstance::getInstanceId).collect(Collectors.toList());
//			log.debug("匹配到的实例：{}", instanceIds);
//		}
//		return instances;
//	}


	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}
}
