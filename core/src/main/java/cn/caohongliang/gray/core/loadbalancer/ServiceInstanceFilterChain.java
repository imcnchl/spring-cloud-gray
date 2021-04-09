package cn.caohongliang.gray.core.loadbalancer;

import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.RequestData;

import java.util.List;

public class ServiceInstanceFilterChain {
	/**
	 * 过滤器
	 */
	private List<ServiceInstanceFilter> filters;
	/**
	 * 过滤器链中当前位置
	 */
	private int position = 0;

	public ServiceInstanceFilterChain(List<ServiceInstanceFilter> filters) {
		this.filters = filters;
	}

	/**
	 * 执行下一个过滤器
	 *
	 * @param environment
	 * @param requestData
	 */
	public List<ServiceInstance> doFilter(List<ServiceInstance> instances, Environment environment, RequestData requestData) {
		ServiceInstanceFilter filter = nextEnableFilter();
		if (filter == null) {
			//没有可用的过滤器
			return instances;
		}
		return filter.doFilter(instances, environment, requestData, this);
	}

	private ServiceInstanceFilter nextEnableFilter() {
		while (position < filters.size()) {
			ServiceInstanceFilter filter = filters.get(position++);
			if (filter.isEnable()) {
				return filter;
			}
		}
		return null;
	}
}
