package cn.caohongliang.gray.common.bean;

import cn.caohongliang.gray.common.constants.BaseErrorCode;
import cn.caohongliang.gray.common.constants.ShowType;
import lombok.Getter;
import lombok.Setter;

/**
 * 请求结果
 *
 * @author caohongliang
 */
@Getter
@Setter
public class Result<T> {
	private boolean success;
	private T data;
	private String errorCode;
	private String errorMessage;
	/**
	 * 错误显示类型
	 *
	 * @see ShowType
	 */
	private Integer showType;
	/**
	 * 请求ID，方便排查日志
	 */
	private final String traceId;
	/**
	 * 服务器IP，方便排查故障
	 */
	private String host;

	public Result() {
		this.traceId = DistributedContext.getContext().getTraceId();
	}

	public static <T> Result<T> success() {
		return success(null);
	}

	/**
	 * 返回成功
	 *
	 * @param data data
	 * @param <T>  data字段泛型
	 * @return result
	 */
	public static <T> Result<T> success(T data) {
		Result<T> result = new Result<>();
		result.success = true;
		result.data = data;
		return result;
	}

	/**
	 * 返回请求失败的错误信息
	 * 使用默认的错误描述、默认的显示类型
	 *
	 * @param errorCode 错误码
	 * @param <T>       泛型
	 * @return result
	 */
	public static <T> Result<T> failure(BaseErrorCode errorCode) {
		return failure(errorCode.getCode(), errorCode.getMessage(), errorCode.getShowType());
	}

	public static <T> Result<T> failureAndAppend(BaseErrorCode errorCode, String appendMessage) {
		return failure(errorCode.getCode(), errorCode.appendMessage(appendMessage), errorCode.getShowType());
	}

	/**
	 * 返回请求失败的错误信息
	 * 使用默认的显示类型
	 *
	 * @param errorCode    错误码
	 * @param errorMessage 自定义的错误描述
	 * @param <T>          泛型
	 * @return result
	 */
	public static <T> Result<T> failure(BaseErrorCode errorCode, String errorMessage) {
		return failure(errorCode.getCode(), errorMessage, errorCode.getShowType());
	}

	/**
	 * 返回请求失败的错误信息
	 * 使用默认的错误描述
	 *
	 * @param errorCode 错误码
	 * @param showType  自定义的显示类型
	 * @param <T>       泛型
	 * @return result
	 */
	public static <T> Result<T> failure(BaseErrorCode errorCode, ShowType showType) {
		return failure(errorCode.getCode(), errorCode.getMessage(), showType);
	}

	/**
	 * 返回请求失败的错误信息
	 *
	 * @param errorCode    错误码
	 * @param errorMessage 自定义的错误描述
	 * @param showType     自定义的显示类型
	 * @param <T>          泛型
	 * @return result
	 */
	public static <T> Result<T> failure(BaseErrorCode errorCode, String errorMessage, ShowType showType) {
		return failure(errorCode.getCode(), errorMessage, showType);
	}

	private static <T> Result<T> failure(String errorCode, String errorMessage, ShowType showType) {
		Result<T> result = new Result<>();
		result.success = false;
		result.data = null;
		result.errorCode = errorCode;
		result.errorMessage = errorMessage;
		result.showType = showType.getValue();
		return result;
	}
}
