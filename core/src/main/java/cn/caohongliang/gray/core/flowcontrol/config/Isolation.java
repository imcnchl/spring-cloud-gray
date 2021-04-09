package cn.caohongliang.gray.core.flowcontrol.config;

import cn.caohongliang.gray.core.util.SpringUtils;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceInstance;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Set;

/**
 * 实例隔离
 *
 * @author caohongliang
 */
@Getter
@Setter
public class Isolation {
	/**
	 * 是否启用故障隔离
	 */
	private boolean enable;
	/**
	 * 进行故障隔离的实例ID
	 * instanceId=host#port#clusterName#group@@serviceId
	 *
	 * @see Isolation#getInstanceId(org.springframework.cloud.client.ServiceInstance)
	 */
	private Set<String> instanceIds;
	/**
	 * 路由到 {instanceIds}中指定服务的规则
	 * 主要是为了场景重现
	 */
	private ContentRule routeRule;

	public static String getInstanceId(ServiceInstance instance) {
		if (instance instanceof NacosServiceInstance) {
			return getInstanceId((NacosServiceInstance) instance);
		}
		return instance.getHost()
				+ "#" + instance.getPort()
				+ "#-"
				+ "#-"
				+ "@@" + instance.getServiceId();
	}

	private static String getInstanceId(NacosServiceInstance instance) {
		NacosDiscoveryProperties properties = SpringUtils.getBean(NacosDiscoveryProperties.class);
		return instance.getHost()
				+ "#" + instance.getPort()
				+ "#" + properties.getClusterName()
				+ "#" + properties.getGroup()
				+ "@@" + instance.getServiceId();
	}
}
