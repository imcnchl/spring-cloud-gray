package cn.caohongliang.gray.core.flowcontrol;

import cn.caohongliang.gray.core.flowcontrol.enviroment.EnvironmentProperties;
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
	public FlowControlFeignHeaderAdder flowControlFeignHeaderAdder() {
		return new FlowControlFeignHeaderAdder();
	}

	@Bean
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public ServletEnvironmentMatcher servletEnvironmentVersionMatcher() {
		return new ServletEnvironmentMatcher();
	}
}
