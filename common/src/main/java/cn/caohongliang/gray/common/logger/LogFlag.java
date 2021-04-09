package cn.caohongliang.gray.common.logger;

/**
 * 日志标识
 */
public enum LogFlag {
	/**
	 * 请求日志
	 */
	REQUEST(1, "请求日志"),
	/**
	 * 响应日志
	 */
	RESPONSE(2, "响应日志"),
	/**
	 * 业务日志
	 */
	BUSINESS(3, "业务日志"),
	/**
	 * 异常日志
	 */
	EXCEPTION(4, "异常日志"),
	INTERNAL(5, "内部日志"),
	DB(6, "DB日志"),
	;
	private final int flag;
	private final String name;

	LogFlag(int flag, String name) {
		this.flag = flag;
		this.name = name;
	}

	public int getFlag() {
		return flag;
	}

	public String getName() {
		return name;
	}
}
