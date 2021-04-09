package cn.caohongliang.gray.common.webmvc.feign;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("customFeignAutoConfiguration")
@EnableFeignClients(basePackages = FeignClientConstants.basePackages)
public class FeignAutoConfiguration {

	@Bean
	@ConditionalOnClass(EnableFeignClients.class)
	@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
	public SkipFeignClientRegistration skipFeignClientRegistration() {
		return new SkipFeignClientRegistration();
	}
}
