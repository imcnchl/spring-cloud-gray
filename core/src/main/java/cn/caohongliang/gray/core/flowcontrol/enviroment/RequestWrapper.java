package cn.caohongliang.gray.core.flowcontrol.enviroment;

import java.util.List;

public interface RequestWrapper {

	/**
	 * 获取URI
	 *
	 * @return 返回当前请求的URI
	 */
	String getRequestURI();

	/**
	 * 获取指定请求头的第一个值
	 *
	 * @param name 请求头名称
	 * @return 第一个值
	 */
	default String getFirstHeader(String name) {
		List<String> headers = getHeaders(name);
		return headers == null || headers.isEmpty() ? null : headers.get(0);
	}

	/**
	 * 获取指定请求头所有值
	 *
	 * @param name 请求头名称
	 * @return 所有值
	 */
	List<String> getHeaders(String name);

	/**
	 * 获取指定URL参数的第一个值
	 *
	 * @param name URL参数名称
	 * @return 第一个值
	 */
	default String getFirstUrlParam(String name) {
		List<String> urlParams = getUrlParams(name);
		return urlParams == null || urlParams.isEmpty() ? null : urlParams.get(0);
	}

	/**
	 * 获取所有URL参数
	 *
	 * @param name URL参数名称
	 * @return 所有值
	 */
	List<String> getUrlParams(String name);

	/**
	 * 获取指定参数名的所有Body参数值
	 *
	 * @param name 参数名
	 * @return 所有参数值
	 */
	List<String> getBodyParams(String name);
}
