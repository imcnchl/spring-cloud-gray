package cn.caohongliang.gray.common.constants;

import lombok.Getter;

/**
 * 日志类型
 *
 * @author caohongliang
 */
@Getter
public enum LogType implements BaseEnum {
	//
	SYSTEM(0, "系统日志"),
	REQUEST(1, "请求日志"),
	RESPONSE(2, "响应日志"),
	SERVICE(3, "业务日志"),
	;
	private final int code;
	private final String name;

	LogType(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public static LogType valueOf(Integer code) {
		return BaseEnum.valueOf(LogType.class, code);
	}
}
