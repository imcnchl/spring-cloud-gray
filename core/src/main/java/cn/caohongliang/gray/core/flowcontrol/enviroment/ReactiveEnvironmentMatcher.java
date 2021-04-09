package cn.caohongliang.gray.core.flowcontrol.enviroment;

import cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties;
import cn.caohongliang.gray.core.util.gson.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


/**
 * 流控策略匹配，reative
 *
 * @author caohongliang
 */
@Slf4j
public class ReactiveEnvironmentMatcher implements GlobalFilter, EnvironmentMatcher, Ordered {
	public static final int ORDER = HIGHEST_PRECEDENCE;
	@Resource
	private FlowControlProperties config;

	public ReactiveEnvironmentMatcher() {
	}

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public RequestWrapper wrapper(Object request) {
		return new ReactiveRequestWrapper((ServerHttpRequest) request);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		ServerHttpRequest serverHttpRequest = request.mutate()
				.headers(headers -> {
					Environment environment = match(config, wrapper(request));
					if (environment == null) {
						return;
					}
					String json = GsonUtils.toJson(environment);
					if (log.isDebugEnabled()) {
						log.info("准备添加环境请求头：url={}, json={}", request.getURI(), json);
					}
					String value = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
					headers.add(Environment.HEADER_NAME, value);
				})
				.build();

		return chain.filter(exchange.mutate().request(serverHttpRequest).build());
	}

	@Override
	public int getOrder() {
		return ORDER;
	}
}
