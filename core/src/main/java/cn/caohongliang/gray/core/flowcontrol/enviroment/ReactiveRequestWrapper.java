package cn.caohongliang.gray.core.flowcontrol.enviroment;

import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * reactive请求包装
 */
public class ReactiveRequestWrapper implements RequestWrapper {
	private final ServerHttpRequest request;

	public ReactiveRequestWrapper(ServerHttpRequest request) {
		this.request = request;
	}

	@Override
	public String getRequestURI() {
		return request.getURI().toString();
	}

	@Override
	public List<String> getHeaders(String name) {
		return request.getHeaders().get(name);
	}

	@Override
	public List<String> getUrlParams(String name) {
		return request.getQueryParams().get(name);
	}

	@Override
	public List<String> getBodyParams(String name) {
		//TODO caohongliang form-data & json
		return new ArrayList<>(0);
	}
}
