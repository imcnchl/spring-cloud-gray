package cn.caohongliang.gray.common.webmvc.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Slf4j
public class SkipFeignClientRegistration implements WebMvcRegistrations {

	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new RequestMappingHandlerMapping() {
			@Override
			protected boolean isHandler(Class<?> beanType) {
				if (AnnotatedElementUtils.hasAnnotation(beanType, Controller.class)) {
					return true;
				}
				boolean hasRequestMapping = AnnotatedElementUtils.hasAnnotation(beanType, RequestMapping.class);
				if (!hasRequestMapping) {
					return false;
				}
				boolean hasFeignClient = AnnotatedElementUtils.hasAnnotation(beanType, FeignClient.class);
				if (hasFeignClient) {
					log.info("该类含有@FeignClient注解，不能作为HandlerMapping：{}", beanType.getName());
				}
				return !hasFeignClient;
			}
		};
	}
}
