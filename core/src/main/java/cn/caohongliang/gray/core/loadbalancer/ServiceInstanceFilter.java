package cn.caohongliang.gray.core.loadbalancer;

import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.RequestData;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * 服务实例过滤
 */
public interface ServiceInstanceFilter extends Ordered {

	/**
	 * 是否启用
	 *
	 * @return true-启用
	 */
	boolean isEnable();

	/**
	 * 过滤，并返回过滤后的结果
	 *
	 * @param instances         实例集合
	 * @param environment 期望的环境信息
	 * @param requestData       请求数据
	 * @return 过滤后的结果
	 */
//	List<ServiceInstance> filter(List<ServiceInstance> instances, Environment expectEnvironment, RequestData requestData);

	List<ServiceInstance> doFilter(List<ServiceInstance> instances, Environment environment,
	                               RequestData requestData, ServiceInstanceFilterChain chain);

}
