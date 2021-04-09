package cn.caohongliang.gray.core.flowcontrol.rule;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

/**
 * 按百分比进行流量控制
 *
 * @author caohongliang
 */
@Getter
@Setter
public class PercentageRule implements StrategyRule{
	/**
	 * 转发到该策略的流量百分比
	 */
	private int percentage;

	@Override
	public boolean validate() {
		return this.percentage >= 0 && this.percentage <= 100;
	}

	@Override
	public boolean match(HttpServletRequest request) {
		//TODO caohongliang 待实现
		return false;
	}
}
