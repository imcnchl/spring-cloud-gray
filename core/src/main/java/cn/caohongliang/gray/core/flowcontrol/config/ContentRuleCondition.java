package cn.caohongliang.gray.core.flowcontrol.config;

import cn.caohongliang.gray.core.flowcontrol.enviroment.RequestWrapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

/**
 * 规则条件
 *
 * @author caohongliang
 */
@Getter
@Setter
public class ContentRuleCondition {
	/**
	 * 条件类型
	 * 1-Header参数
	 * 2-URL参数
	 * 3-Body参数
	 * 4-Cookie参数
	 */
	private int type;
	/**
	 * 参数名
	 * type=3时如果body里面是json，可以配置对应的json path
	 */
	private String name;
	/**
	 * 参数值
	 */
	private String value;

	public boolean validate() {
		return this.type >= 1 && this.type <= 4
				&& StringUtils.hasText(name) && StringUtils.hasText(value);
	}

	public boolean match(RequestWrapper request) {
		switch (type) {
			case 1:
				//header
				return matchHeader(request);
			case 2:
				//url
				return matchUrl(request);
			case 3:
				//body
				return matchBody(request);
			case 4:
				//cookie
				return matchCookie(request);
			default:
				return false;
		}
	}

	private boolean matchHeader(RequestWrapper request) {
		return value.equals(request.getFirstHeader(this.name));
	}

	private boolean matchUrl(RequestWrapper request) {
		//TODO caohongliang 待实现
		return false;
	}

	private boolean matchBody(RequestWrapper request) {
		//TODO caohongliang 待实现
		return false;
	}

	private boolean matchCookie(RequestWrapper request) {
		//TODO caohongliang 待实现
		return false;
	}
}
