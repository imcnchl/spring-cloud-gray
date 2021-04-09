package cn.caohongliang.gray.core.loadbalancer;

import cn.caohongliang.gray.core.flowcontrol.FlowControlProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@LoadBalancerClients(defaultConfiguration = CustomLoadBalancerAutoConfiguration.class)
public class CustomLoadBalancerAutoConfiguration {

	@Bean
	public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(
			Environment environment, LoadBalancerClientFactory loadBalancerClientFactory,
			FlowControlProperties config, List<ServiceInstanceFilter> serviceInstanceFilters) {
		String serviceId = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
		ObjectProvider<ServiceInstanceListSupplier> lazyProvider = loadBalancerClientFactory.getLazyProvider(serviceId, ServiceInstanceListSupplier.class);
		return new CustomLoadBalancer(serviceId, lazyProvider, config, serviceInstanceFilters);
	}

}
