package cn.caohongliang.gray.core.flowcontrol;

import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import cn.caohongliang.gray.core.loadbalancer.ServiceInstanceFilter;
import cn.caohongliang.gray.core.loadbalancer.ServiceInstanceFilterChain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.core.Ordered;

import java.util.List;

@Slf4j
public class IsolationServiceInstanceFilter implements ServiceInstanceFilter {
	@Autowired
	private FlowControlProperties config;

	@Override
	public boolean isEnable() {
		return config.isEnable();
	}

	@Override
	public List<ServiceInstance> doFilter(List<ServiceInstance> instances, Environment environment, RequestData requestData, ServiceInstanceFilterChain chain) {
		List<Isolation> isolations = config.getIsolations();
		if (isolations == null || isolations.isEmpty()) {
			return chain.doFilter(instances, environment, requestData);
		}
		//TODO caohongliang 待完成
		log.info("TODO---------------故障隔离，不继续往下执行---------------");
		return chain.doFilter(instances, environment, requestData);
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
