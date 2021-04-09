package cn.caohongliang.gray.common.util;

/**
 * 日志相关工具类
 * <p>
 * traceId -
 * spanId -
 * createdTime -
 * className -
 * methodName -
 * line - 行号
 * url -
 * queryParams -
 * body -
 * clientIp - 用户请求IP
 * serverIp - 服务器IP
 * service - 所属服务
 * msg - 日志打印消息
 * consumingTime - 耗时
 *
 * @author caohongliang
 */
public class LogUtils {


	/**
	 * 将日志类型添加到MDC中，方便打印日志类型
	 *
	 * @param logType
	 */
//	public static void put(LogType logType) {
//		MDC.put("logType", logType.getCode() + "");
//		MDC.put("logTypeName", logType.getName());
//	}
//
//	/**
//	 * 将日志类型添加到MDC中，方便打印日志类型
//	 */
//	public static void clearLogType() {
//		MDC.remove("logType");
//		MDC.remove("logTypeName");
//	}
	public static void clearAll() {
//		clearLogType();
//		MDC.remove("logType");

	}
}
