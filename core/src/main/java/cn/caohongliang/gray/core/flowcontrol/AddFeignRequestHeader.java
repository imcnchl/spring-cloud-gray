package cn.caohongliang.gray.core.flowcontrol;

import cn.caohongliang.gray.core.flowcontrol.enviroment.Environment;
import cn.caohongliang.gray.core.util.gson.GsonUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Feign请求前指定好预期的环境和版本
 *
 * @author caohongliang
 */
@Slf4j
public class AddFeignRequestHeader implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		String json = GsonUtils.toJson(Environment.get());
		if (log.isDebugEnabled()) {
			log.info("准备添加环境请求头：url={}, json={}", template.request().url(), json);
		}
		String value = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
		template.header(Environment.HEADER_NAME, value);
	}
}
