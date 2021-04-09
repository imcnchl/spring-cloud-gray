package cn.caohongliang.gray.core.flowcontrol.config;

import cn.caohongliang.gray.core.flowcontrol.enviroment.RequestWrapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 按内容进行流量控制
 *
 * @author caohongliang
 */
@Getter
@Setter
@Slf4j
public class ContentRule implements StrategyRule {
	/**
	 * HTTP相对路径，例如/user/name/1234，注意是严格匹配，留空表示匹配任何路径
	 */
	private String path;
	/**
	 * 条件模式，1-同时满足下列条件，2-满足下列任一条件
	 */
	private int conditionMode;
	/**
	 * 规则条件列表
	 */
	private List<ContentRuleCondition> conditions;

	@Override
	public boolean validate() {
		return this.conditionMode >= 1 && this.conditionMode <= 2
				&& this.conditions != null && !this.conditions.isEmpty()
				&& this.conditions.stream().allMatch(ContentRuleCondition::validate);
	}

	@Override
	public boolean match(RequestWrapper request) {
		if (StringUtils.hasText(this.path)) {
			if (!request.getRequestURI().endsWith(this.path)) {
				log.info("path={}, uri={}", this.path, request.getRequestURI());
				return false;
			}
		}
		if (this.conditionMode == 1) {
			return this.conditions.stream().allMatch(condition -> condition.match(request));
		} else if (this.conditionMode == 2) {
			return this.conditions.stream().anyMatch(condition -> condition.match(request));
		}
		return false;
	}
}
