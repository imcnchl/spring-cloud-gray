package cn.caohongliang.gray.core.flowcontrol.enviroment;

import cn.caohongliang.gray.core.flowcontrol.config.FlowControlProperties;
import cn.caohongliang.gray.core.flowcontrol.config.FlowControlStrategy;
import cn.caohongliang.gray.core.util.gson.GsonUtils;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 环境匹配器
 *
 * @author caohongliang
 */
public interface EnvironmentMatcher {

	Logger getLogger();

	/**
	 * 包装请求
	 *
	 * @param request 请求
	 * @return wrapper
	 */
	RequestWrapper wrapper(Object request);

	/**
	 * 根据流控策略对当前请求进行匹配，判断应该转发到哪个版本
	 *
	 * @return 环境和版本
	 */
	default Environment match(FlowControlProperties config, RequestWrapper request) {
		if (!config.isEnable()) {
			//没有启用流控策略
			return null;
		}
		String base64Text = request.getFirstHeader(Environment.HEADER_NAME);
		//TODO gateway即使设置该请求头也不能生效，避免直接指定版本
		Environment environment = Environment.decode(base64Text);
		if (environment != null) {
			//已经指定了环境和版本
			return environment;
		}

		//没有直接指定版本，需要根据流控策略进行匹配
		//优先匹配按内容的策略，最后再匹配按百分比的策略
		//如果有多个策略均可以匹配到，只会去第一个规则的环境和版本信息
		List<FlowControlStrategy> strategies = config.getStrategies();
		if (strategies == null || strategies.isEmpty()) {
			//流控策略不存在，使用当前策略
			return config.getCurrent();
		}
		FlowControlStrategy matchedStrategy = strategies.stream()
				.filter(Objects::nonNull)
				//匹配可用的策略
				.filter(FlowControlStrategy::usable)
				//排序，优先匹配按内容的
				.sorted(Comparator.comparing(FlowControlStrategy::getType))
				//匹配流控策略
				.filter(strategy -> strategy.match(request))
				.findFirst().orElse(null);
		if (matchedStrategy == null) {
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("没有匹配的流控策略，使用当前策略：{}", GsonUtils.toJson(strategies));
			}
			return config.getCurrent();
		}
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("匹配到的策略：{}", GsonUtils.toJson(matchedStrategy));
		}
		environment = matchedStrategy.getEnvironment();
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("指定的环境：name={}, version={}", environment.getName(), environment.getVersion());
		}
		//已经指定了环境和版本
		return environment;
	}

}
