package cn.caohongliang.gray.core.flowcontrol.enviroment;

import cn.caohongliang.gray.core.flowcontrol.FlowControlProperties;
import cn.caohongliang.gray.core.flowcontrol.FlowControlStrategy;
import cn.caohongliang.gray.core.util.gson.GsonUtils;
import org.slf4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * 环境和版本适配器
 */
public interface EnvironmentMatcher {

	Logger getLogger();

	/**
	 * 获取在请求头中指定的环境版本信息
	 *
	 * @param request 请求
	 * @return base64 编码后的json
	 */
	String getHeaderText(Object request);

	/**
	 * 根据流控策略对当前请求进行匹配，判断应该转发到哪个版本
	 *
	 * @return 环境和版本
	 */
	default void match(FlowControlProperties config, Object request) {
		if (!config.isEnable()) {
			//没有启用流控策略
			return;
		}
		String base64Text = getHeaderText(request);
		//TODO gateway即使设置该请求头也不能生效，避免直接指定版本
		Environment environment = Environment.decode(base64Text);
		if (environment != null) {
			//已经指定了环境和版本，直接设置到ThreadLocal
			//TODO caohongliang 没有进行释放
			Environment.cache(environment);
			return;
		}

		//没有直接指定版本，需要根据流控策略进行匹配
		//优先匹配按内容的策略，最后再匹配按百分比的策略
		//如果有多个策略均可以匹配到，只会去第一个规则的环境和版本信息
		List<FlowControlStrategy> strategies = config.getStrategies();
		if (strategies == null || strategies.isEmpty()) {
			//流控策略不存在，使用当前策略
			Environment.cache(config.getCurrent());
			return;
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
			Environment.cache(config.getCurrent());
			return;
		}
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("匹配到的策略：{}", GsonUtils.toJson(matchedStrategy));
		}
		environment = matchedStrategy.getEnvironment();
		if (getLogger().isDebugEnabled()) {
			getLogger().debug("指定的环境：name={}, version={}", environment.getName(), environment.getVersion());
		}
		//已经指定了环境和版本，直接设置到ThreadLocal
		Environment.cache(environment);
	}

}
