package cn.caohongliang.gray.core.flowcontrol;

public class Constants {

	/**
	 * 配置文件的dataId
	 */
	public static final String FLOW_CONTROL_CONFIG_DATA_ID = "flow-control";
	/**
	 * 配置文件的 prefix
	 */
	public static final String FLOW_CONTROL_CONFIG_DATA_ID_PREFIX = "flow-control";
	/**
	 * 指定环境和版本的请求头，如果存在该请求头则不使用流控策略中指定的环境和版本
	 */
	public static final String ENVIRONMENT_VERSION_HEADER_NAME = "X-environment_version_";
}
