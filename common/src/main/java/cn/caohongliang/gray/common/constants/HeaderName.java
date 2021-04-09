package cn.caohongliang.gray.common.constants;

import lombok.Getter;

/**
 * 请求头/响应头的名称
 *
 * @author caohongliang
 */
@Getter
public enum HeaderName {
	//---------------------- 请求 ----------------------
	/**
	 * 请求头：请求签名校验使用的请求头
	 */
	REQUEST_SIGN("X-sign"),
	/**
	 * 请求头：时间戳
	 */
	REQUEST_TIMESTAMP("X-timestamp"),
	/**
	 * 请求头：随机数请求头（UUID）
	 */
	REQUEST_NONCE("X-nonce"),

	//---------------------- 响应 ----------------------
	/**
	 * 响应头：表示该服务的响应是否为标准响应，即返回 Result 类型
	 */
	RESPONSE_STANDARD("X-standard"),
	/**
	 * 响应头：如果有该响应头，则前端需要对响应体进行MD5，然后比对服务器返回的MD5是否一致，不一致则丢弃该响应
	 */
	RESPONSE_SIGN("X-sign"),
	;
	private final String value;

	HeaderName(String value) {
		this.value = value;
	}
}
