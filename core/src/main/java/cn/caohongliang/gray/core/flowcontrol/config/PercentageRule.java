package cn.caohongliang.gray.core.flowcontrol.config;

import cn.caohongliang.gray.core.flowcontrol.enviroment.RequestWrapper;
import lombok.Getter;
import lombok.Setter;

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
	public boolean match(RequestWrapper request) {
		//TODO caohongliang 待实现
		return false;
	}
}
