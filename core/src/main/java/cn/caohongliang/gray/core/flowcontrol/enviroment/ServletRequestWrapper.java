package cn.caohongliang.gray.core.flowcontrol.enviroment;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * mvc请求包装
 *
 * @author caohongliang
 */
public class ServletRequestWrapper implements RequestWrapper {
	private final HttpServletRequest request;

	public ServletRequestWrapper(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public String getRequestURI() {
		return request.getRequestURI();
	}

	@Override
	public List<String> getHeaders(String name) {
		Enumeration<String> enumeration = request.getHeaders(name);
		if (enumeration == null) {
			return new ArrayList<>(0);
		}
		List<String> headers = new ArrayList<>();
		while (enumeration.hasMoreElements()) {
			headers.add(enumeration.nextElement());
		}
		return headers;
	}

	@Override
	public List<String> getUrlParams(String name) {
		String queryString = request.getQueryString();
		if (!StringUtils.hasText(queryString)) {
			return new ArrayList<>(0);
		}
		return formDataParams(queryString, name);
	}

	private List<String> formDataParams(String text, String name) {
		return Stream.of(text.split("&"))
				.filter(kv -> kv.startsWith(name + "="))
				.map(kv -> kv.substring(name.length() + 1))
				.collect(Collectors.toList());

	}

	@Override
	public List<String> getBodyParams(String name) {
		//TODO caohongliang form-data & json
		return new ArrayList<>(0);
	}
}
