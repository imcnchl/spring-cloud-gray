package cn.caohongliang.gray.common.bean;

import java.util.UUID;

/**
 * 分布式请求上下文
 *
 * @author caohongliang
 */
public class DistributedContext {
	private static final ThreadLocal<DistributedContext> threadLocal = new InheritableThreadLocal<>();
	private String traceId;

	private DistributedContext() {
	}

	public String getTraceId() {
		return traceId;
	}

	/**
	 * 获取当前的请求上下文
	 * 注意：使用 currentContext() 方法在父线程和子线程获取到的是同一个对象
	 *
	 * @return 分布式请求上下文
	 */
	public static DistributedContext getContext() {
		DistributedContext context = threadLocal.get();
		if (context != null) {
			return context;
		}

		context = new DistributedContext();
		context.traceId = UUID.randomUUID().toString().replace("-", "");

		threadLocal.set(context);
		return context;
	}

	/**
	 * 销毁上下文
	 * 注意：子线程销毁上下文不会影响到父线程，但是子线程再次调用获取方法后获取的对象和父线程不是同一个
	 */
	public static void destroy() {
		threadLocal.remove();
	}
}
