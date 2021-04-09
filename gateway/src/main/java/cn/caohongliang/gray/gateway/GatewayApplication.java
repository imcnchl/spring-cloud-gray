package cn.caohongliang.gray.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关
 *
 * @author caohongliang
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
//		System.setProperty(ClassicConstants.LOGBACK_CONTEXT_SELECTOR, "cn.caohongliang.template.common.log.logback.CustomContextSelector");
		SpringApplication.run(GatewayApplication.class, args);
	}
}
