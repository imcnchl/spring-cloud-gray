package cn.caohongliang.gray.core.flowcontrol;

import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import cn.caohongliang.gray.core.flowcontrol.rule.ContentRule;
import cn.caohongliang.gray.core.flowcontrol.rule.PercentageRule;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 流控策略
 * 如果在该策略内找不到对应的服务，则使用当前服务
 *
 * @author caohongliang
 */
@Getter
@Setter
@Slf4j
public class FlowControlStrategy {
	/**
	 * 是否启用该流控策略
	 */
	private boolean enable;
	/**
	 * 流量控制类型
	 * 1-按内容控制
	 * 2-按比例控制
	 *
	 * @see StrategyType
	 */
	private int type;
	/**
	 * 使用的环境和版本信息
	 */
	private Environment environment;
	/**
	 * 按内容控制时的控制规则
	 */
	private ContentRule contentRule;
	/**
	 * 按比例控制时的流控规则
	 */
	private PercentageRule percentageRule;

	/**
	 * 校验当前规则是否可用
	 *
	 * @return true-正常
	 */
	public boolean usable() {
		if (!this.enable) {
			return false;
		}
		if (this.environment == null || !StringUtils.hasText(this.environment.getName())) {
			return false;
		}
		if (this.type == StrategyType.content.getValue()) {
			//校验按内容控制的控制规则
			return this.contentRule.validate();
		} else if (this.type == StrategyType.percentage.getValue()) {
			//校验按比例的规则
			return this.percentageRule.validate();
		}
		return false;
	}

	public boolean match(Object request) {
		if (request instanceof HttpServletRequest) {
			return match((HttpServletRequest) request);
		}
		return false;
	}

	private boolean match(HttpServletRequest request) {
		if (this.type == StrategyType.content.getValue()) {
			//按内容匹配
			return this.contentRule.match(request);
		} else if (this.type == StrategyType.percentage.getValue()) {
			//按百分比匹配
			return this.percentageRule.match(request);
		}
		log.warn("流控规则匹配失败，异常的type：type={}", this.type);
		return false;
	}
}
