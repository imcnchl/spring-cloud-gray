package cn.caohongliang.gray.core.flowcontrol.enviroment;

import cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 流控策略匹配，web mvc
 *
 * @author caohongliang
 */
@Slf4j
public class ServletEnvironmentMatcher extends OncePerRequestFilter implements EnvironmentMatcher {
	@Resource
	private FlowControlProperties config;

	@Override
	public Logger getLogger() {
		return log;
	}

	@Override
	public RequestWrapper wrapper(Object... params) {
		return new ServletRequestWrapper((HttpServletRequest) params[0]);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			Environment environment = match(config, wrapper(request));
			//缓存环境信息
			Environment.cache(environment);
			filterChain.doFilter(request, response);
		} finally {
			//清除
			Environment.clear();
		}
	}

}
