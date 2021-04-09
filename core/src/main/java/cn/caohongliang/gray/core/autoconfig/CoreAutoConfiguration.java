package cn.caohongliang.gray.core.autoconfig;

import cn.caohongliang.gray.core.util.SpringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreAutoConfiguration {

	@Bean
	public SpringUtils springUtils() {
		return new SpringUtils();
	}
}
