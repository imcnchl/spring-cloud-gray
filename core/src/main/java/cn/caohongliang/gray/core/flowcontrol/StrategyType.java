package cn.caohongliang.gray.core.flowcontrol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 策略类型
 *
 * @author caohongliang
 */
@Getter
@AllArgsConstructor
public enum StrategyType {
	content(1, "按内容控制"),
	percentage(2, "按比例控制"),
	;
	private final int value;
	private final String name;

}
