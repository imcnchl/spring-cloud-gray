package cn.caohongliang.gray.core.flowcontrol.enviroment;

import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;

import java.util.ArrayList;
import java.util.List;

/**
 * reactive请求包装
 */
public class ReactiveRequestWrapper implements RequestWrapper {
	private final ServerHttpRequest request;

	public ReactiveRequestWrapper(ServerWebExchange exchange) {
		this.request = exchange.getRequest();
	}

	@Override
	public boolean isGateway() {
		return true;
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

	@Override
	public List<HttpCookie> getCookies(String name) {
		MultiValueMap<String, HttpCookie> cookies = request.getCookies();
		return cookies.get(name);
	}
}
