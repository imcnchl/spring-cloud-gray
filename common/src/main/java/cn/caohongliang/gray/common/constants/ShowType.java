package cn.caohongliang.gray.common.constants;

/**
 * Result.showType的枚举
 *
 * @author caohongliang
 */
public enum ShowType {
	silent(0, "静音"),
	warn(1, "警告消息"),
	error(2, "错误消息"),
	notification(4, "通知"),
	page(9, "页面"),
	;
	private final int value;
	private final String desc;

	ShowType(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
