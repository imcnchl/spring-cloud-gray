package cn.caohongliang.gray.core.flowcontrol.enviroment;

import cn.caohongliang.gray.core.flowcontrol.FlowControlProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cn.caohongliang.gray.core.flowcontrol.Constants.ENVIRONMENT_VERSION_HEADER_NAME;

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
	public String getHeaderText(Object request) {
		return ((HttpServletRequest) request).getHeader(ENVIRONMENT_VERSION_HEADER_NAME);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			match(config, request);
			filterChain.doFilter(request, response);
		} finally {
			//清除
			Environment.clear();
		}
	}

}
