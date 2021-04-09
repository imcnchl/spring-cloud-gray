package cn.caohongliang.gray.core.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestUtils {
	private RequestUtils() {
	}

	/**
	 * 获取当前请求
	 *
	 * @return request
	 */
	public static HttpServletRequest getRequest() {
		return getServletRequestAttributes().getRequest();
	}

	/**
	 * 获取当前响应
	 *
	 * @return response
	 */
	public static HttpServletResponse getResponse() {
		return (getServletRequestAttributes()).getResponse();
	}

	private static ServletRequestAttributes getServletRequestAttributes() {
		return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	}
}
