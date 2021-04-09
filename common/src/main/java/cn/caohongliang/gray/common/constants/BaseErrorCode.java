package cn.caohongliang.gray.common.constants;

/**
 * 错误码接口，所有错误码的枚举类均需基础该类
 * 模块的错误码开头：
 * gateway - 10xxx
 * authority - 11xxx
 */
public interface BaseErrorCode {

	/**
	 * 获取错误码
	 *
	 * @return 错误码
	 */
	String getCode();

	/**
	 * 获取默认的错误描述
	 *
	 * @return 错误描述
	 */
	String getMessage();

	/**
	 * 获取默认的显示类型
	 *
	 * @return 显示类型
	 */
	ShowType getShowType();

	default String appendMessage(String message) {
		return this.getMessage() + "：" + message;
	}

	default String appendMessage(String format, Object[] args) {
		return this.getMessage() + "：" + String.format(format, args);
	}
}
