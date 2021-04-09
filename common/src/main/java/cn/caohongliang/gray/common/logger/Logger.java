package cn.caohongliang.gray.common.logger;

import cn.caohongliang.gray.common.bean.DistributedContext;
import cn.caohongliang.gray.common.util.IPUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 日志打印工具类
 * TODO 需要给请求、响应设置不同的flag
 *
 * @author caohongliang
 */
public final class Logger {
	private static final String FULLY_NAME = Logger.class.getName();
	private static final Object[] EMPTY_ARRAY = new Object[]{};
	private static String SERVER_IP;

	static {
		try {
			SERVER_IP = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 记录器实例是否启用了 DEBUG 级别？
	 *
	 * @return true表示启用了 DEBUG 级别
	 */
	public static boolean isDebugEnabled() {
		return getLogger(3).isDebugEnabled();
	}

	/**
	 * 在 DEBUG 级别记录一条消息。
	 *
	 * @param msg 消息
	 */
	public static void debug(String msg) {
		debug(getLogger(3), LogFlag.BUSINESS, msg, null, EMPTY_ARRAY);
	}

	/**
	 * 在 DEBUG 级别记录一条消息。
	 *
	 * @param msg 消息
	 */
	public static void debug(LogFlag flag, String msg) {
		debug(getLogger(3), flag, msg, null, EMPTY_ARRAY);
	}

	/**
	 * 根据指定的格式和参数在调试级别记录一条消息。  <p />
	 * <p>当记录器禁用 DEBUG 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param arguments 变量数组
	 */
	public static void debug(String format, Object... arguments) {
		debug(getLogger(3), LogFlag.BUSINESS, format, null, arguments);
	}

	/**
	 * 记录带有伴随消息的 DEBUG 级别的异常（可抛出）。
	 *
	 * @param msg 伴随异常的消息
	 * @param t   记录的异常（可抛出）
	 */
	public static void debug(String msg, Throwable t) {
		debug(getLogger(3), LogFlag.BUSINESS, msg, t, EMPTY_ARRAY);
	}

	/**
	 * 记录带有伴随消息的 DEBUG 级别的异常（可抛出）。<p />
	 * <p>当记录器禁用 DEBUG 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param t         记录的异常（可抛出）
	 * @param arguments 变量数组
	 */
	public static void debug(String format, Throwable t, Object... arguments) {
		debug(getLogger(3), LogFlag.BUSINESS, format, t, arguments);
	}

	public static void debug(LocationAwareLogger logger, LogFlag flag, String format, Throwable t, Object... arguments) {
		logger.log(null, FULLY_NAME, LocationAwareLogger.DEBUG_INT, format, arguments, t);
	}

	/**
	 * 记录器实例是否启用了 INFO 级别？
	 *
	 * @return true表示启用了 INFO 级别
	 */
	public static boolean isInfoEnabled() {
		return getLogger(3).isInfoEnabled();
	}

	/**
	 * 在 INFO 级别记录一条消息。
	 *
	 * @param msg 消息
	 */
	public static void info(String msg) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.INFO_INT, msg, EMPTY_ARRAY, null);
	}

	/**
	 * 在 INFO 级别记录一条消息。
	 *
	 * @param msg 消息
	 */
	public static void info(String msg, LogFlag flag) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.INFO_INT, msg, EMPTY_ARRAY, null);
	}

	/**
	 * 根据指定的格式和参数在调试级别记录一条消息。  <p />
	 * <p>当记录器禁用 INFO 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param arguments 变量数组
	 */
	public static void info(String format, Object... arguments) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.INFO_INT, format, arguments, null);

	}

	/**
	 * 记录带有伴随消息的 INFO 级别的异常（可抛出）。
	 *
	 * @param msg 伴随异常的消息
	 * @param t   记录的异常（可抛出）
	 */
	public static void info(String msg, Throwable t) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.INFO_INT, msg, EMPTY_ARRAY, t);
	}

	/**
	 * 记录带有伴随消息的 INFO 级别的异常（可抛出）。<p />
	 * <p>当记录器禁用 INFO 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param t         记录的异常（可抛出）
	 * @param arguments 变量数组
	 */
	public static void info(String format, Throwable t, Object... arguments) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.INFO_INT, format, arguments, t);
	}

	/**
	 * 记录器实例是否启用了 WARN 级别？
	 *
	 * @return true表示启用了 WARN 级别
	 */
	public static boolean isWarnEnabled() {
		return getLogger(3).isWarnEnabled();
	}

	/**
	 * 在 WARN 级别记录一条消息。
	 *
	 * @param msg 消息
	 */
	public static void warn(String msg) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.WARN_INT, msg, EMPTY_ARRAY, null);
	}

	/**
	 * 根据指定的格式和参数在调试级别记录一条消息。  <p />
	 * <p>当记录器禁用 WARN 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param arguments 变量数组
	 */
	public static void warn(String format, Object... arguments) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.WARN_INT, format, arguments, null);
	}

	/**
	 * 记录带有伴随消息的 WARN 级别的异常（可抛出）。
	 *
	 * @param msg 伴随异常的消息
	 * @param t   记录的异常（可抛出）
	 */
	public static void warn(String msg, Throwable t) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.WARN_INT, msg, EMPTY_ARRAY, t);
	}

	/**
	 * 记录带有伴随消息的 WARN 级别的异常（可抛出）。<p />
	 * <p>当记录器禁用 WARN 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param t         记录的异常（可抛出）
	 * @param arguments 变量数组
	 */
	public static void warn(String format, Throwable t, Object... arguments) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.WARN_INT, format, arguments, t);
	}

	/**
	 * 记录器实例是否启用了 ERROR 级别？
	 *
	 * @return true表示启用了 ERROR 级别
	 */
	public static boolean isErrorEnabled() {
		return getLogger(3).isErrorEnabled();
	}

	/**
	 * 在 ERROR 级别记录一条消息。
	 *
	 * @param msg 消息
	 */
	public static void error(String msg) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.ERROR_INT, msg, EMPTY_ARRAY, null);
	}

	/**
	 * 根据指定的格式和参数在调试级别记录一条消息。  <p />
	 * <p>当记录器禁用 ERROR 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param arguments 变量数组
	 */
	public static void error(String format, Object... arguments) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.ERROR_INT, format, arguments, null);
	}

	/**
	 * 记录带有伴随消息的 ERROR 级别的异常（可抛出）。
	 *
	 * @param msg 伴随异常的消息
	 * @param t   记录的异常（可抛出）
	 */
	public static void error(String msg, Throwable t) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.ERROR_INT, msg, EMPTY_ARRAY, t);
	}

	/**
	 * 记录带有伴随消息的 ERROR 级别的异常（可抛出）。<p />
	 * <p>当记录器禁用 ERROR 级别时，这种形式避免了多余的字符串连接。
	 * 但是，此变体将导致在调用方法之前创建<code> Object [] </ code>的隐藏（并且相对较小）的开销，即使该记录器已禁用DEBUG。
	 *
	 * @param format    格式字符串
	 * @param t         记录的异常（可抛出）
	 * @param arguments 变量数组
	 */
	public static void error(String format, Throwable t, Object... arguments) {
		getLogger(3).log(null, FULLY_NAME, LocationAwareLogger.ERROR_INT, format, arguments, t);
	}

	private static LocationAwareLogger getLogger(int level) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[level];
		return (LocationAwareLogger) LoggerFactory.getLogger(stackTraceElement.getClassName());
	}

	private static class LogEvent {
		/**
		 * 日志实例
		 */
		private LocationAwareLogger logger;
		private int level;
		private String gatewayMethod;
		/**
		 * 日志ID
		 */
		private String traceId;
		/**
		 * 产生日志的服务器IP
		 */
		private String serverIp;
		/**
		 * 发出请求的IP
		 */
		private String clientIp;
		/**
		 * 日志标识
		 *
		 * @see LogFlag
		 */
		private int flag;
		/**
		 * 消息或格式化字符串
		 */
		private String msg;
		/**
		 * 变量数组，用于给 msg 字段进行格式化
		 */
		private Object[] arguments;
		/**
		 * 耗时
		 */
		private long timeConsuming;
		/**
		 * 异常名称
		 */
		private String throwableName;

		public static LogEvent request(LocationAwareLogger logger, String method, String clientIp, String msg) {
			LogEvent event = new LogEvent();
			event.logger = logger;
			event.level = LocationAwareLogger.INFO_INT;
			event.gatewayMethod = method;
			event.traceId = DistributedContext.getContext().getTraceId();
			event.serverIp = IPUtils.getServerIp();
			event.clientIp = clientIp;
			event.flag = LogFlag.REQUEST.getFlag();
			event.msg = msg;
			return event;
		}
	}
}
