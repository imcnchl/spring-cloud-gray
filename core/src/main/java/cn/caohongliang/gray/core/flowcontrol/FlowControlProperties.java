package cn.caohongliang.gray.core.flowcontrol;

import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * 流量控制
 * 后续可以调整为web页面进行配置和流量切换
 * TODO 待完成：故障节点隔离，并且模拟请求时可以指定访问故障节点
 *
 * @author caohongliang
 */
@Getter
@Setter
@ConfigurationProperties(prefix = Constants.FLOW_CONTROL_CONFIG_DATA_ID_PREFIX)
@PropertySource(value = "classpath*:" + Constants.FLOW_CONTROL_CONFIG_DATA_ID + ".properties", ignoreResourceNotFound = true)
public class FlowControlProperties {
	/**
	 * 是否启用流控策略
	 */
	private boolean enable;
	/**
	 * 当前环境
	 */
	private Environment current;
	/**
	 * 是否启用兜底策略：控制没有匹配到服务实例的情况是否进行使用当前环境进行实例的选择
	 * false-不使用
	 * true-使用，如果匹配到的策略指定的环境没有实例，则重新使用当前环境进行选择
	 */
	private boolean outStrategy;
	/**
	 * 进行隔离的实例ID
	 */
	private List<Isolation> isolations;
	/**
	 * 流控策略
	 */
	private List<FlowControlStrategy> strategies;

}
