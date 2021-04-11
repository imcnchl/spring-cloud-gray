package cn.caohongliang.gray.core.flowcontrol.config;

import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

import static cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties.DATA_ID;
import static cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties.PREFIX;

/**
 * 流量控制
 * 后续可以调整为web页面进行配置和流量切换
 * TODO 待完成：故障节点隔离，并且模拟请求时可以指定访问故障节点
 *
 * @author caohongliang
 */
@Getter
@Setter
@ConfigurationProperties(prefix = PREFIX)
@PropertySource(value = "classpath*:" + DATA_ID + ".properties", ignoreResourceNotFound = true)
public class FlowControlProperties {
	public static final String PREFIX = "flow-control";
	public static final String DATA_ID = "flow-control";
	/**
	 * 是否启用流控策略
	 */
	private boolean enable;
	/**
	 * 当前环境，设置没有匹配的策略时流量应该到哪个环境，必须指定
	 */
	private Environment current;
	/**
	 * TODO 故障隔离策略，暂未实现
	 */
	private List<Isolation> isolations;
	/**
	 * 兜底策略：无法根据“预期环境和版本”匹配实例
	 * 预期：strategies.environment 指定的环境
	 * 当前：current 指定的环境
	 * 0-不使用兜底策略
	 * 1-使用相同环境的服务实例（预期：B环境 1.1版本，则匹配B环境所有版本）
	 * 2-使用当前环境当前版本的服务实例（当前：A环境 1.1版本，预期：B环境 1.2版本，则匹配A环境 1.1版本）
	 * 3-使用当前环境的服务实例（当前：A环境 1.1版本，预期：B环境 1.2版本，则匹配A环境）
	 */
	private int outStrategy;
	/**
	 * 流控策略
	 */
	private List<FlowControlStrategy> strategies;

	/**
	 * 根据兜底策略获取进行兜底的环境和版本信息
	 *
	 * @param expect 预期的环境版本信息
	 * @return 环境版本信息
	 */
	public Environment getOutEnvironment(Environment expect) {
		switch (outStrategy) {
			case 1:
				return expect == null ? null : new Environment(expect.getName(), null);
			case 2:
				return current;
			case 3:
				return current == null ? null : new Environment(current.getName(), null);
			default:
				return expect;
		}
	}
}
