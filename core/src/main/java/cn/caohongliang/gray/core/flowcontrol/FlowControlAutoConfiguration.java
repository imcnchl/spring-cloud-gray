package cn.caohongliang.gray.core.flowcontrol;

import cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties;
import cn.caohongliang.gray.core.flowcontrol.enviroment.EnvironmentProperties;
import cn.caohongliang.gray.core.flowcontrol.enviroment.ReactiveEnvironmentMatcher;
import cn.caohongliang.gray.core.flowcontrol.enviroment.ServletEnvironmentMatcher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({FlowControlProperties.class, EnvironmentProperties.class})
public class FlowControlAutoConfiguration {

	@Bean
	public StrategyServiceInstanceFilter flowControlServiceInstanceFilter() {
		return new StrategyServiceInstanceFilter();
	}

	@Bean
	public IsolationServiceInstanceFilter isolationServiceInstanceFilter() {
		return new IsolationServiceInstanceFilter();
	}

	@Bean
	public AddFeignRequestHeader flowControlFeignHeaderAdder() {
		return new AddFeignRequestHeader();
	}

	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public static class ServletFlowControlAutoConfiguration {
		@Bean
		public ServletEnvironmentMatcher servletEnvironmentVersionMatcher() {
			return new ServletEnvironmentMatcher();
		}
	}

	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
	public static class ReactiveFlowControlAutoConfiguration {
		@Bean
		public ReactiveEnvironmentMatcher reactiveEnvironmentMatcher() {
			return new ReactiveEnvironmentMatcher();
		}

	}
}
