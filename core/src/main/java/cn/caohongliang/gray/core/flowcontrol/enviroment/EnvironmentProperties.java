package cn.caohongliang.gray.core.flowcontrol.enviroment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 指定当前服务的环境
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "environment")
public class EnvironmentProperties {
	/**
	 * 环境名称
	 */
	private String name;
	/**
	 * 版本号
	 */
	private String version;
}
