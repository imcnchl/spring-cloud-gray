package cn.caohongliang.gray.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring bean 加载器
 *
 * @author caohongliang
 */
public class SpringUtils implements ApplicationContextAware {
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	/**
	 * 获取对应类型的bean
	 *
	 * @param requiredType 对应的类类型
	 * @param <T>          泛型
	 * @return 对应的实力
	 */
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 根据beanName获取对应类型的bean
	 *
	 * @param beanName     bean的名称
	 * @param requiredType 类型
	 * @param <T>          泛型
	 * @return bean，可能为空
	 */
	public static <T> T getBean(String beanName, Class<T> requiredType) {
		return applicationContext.getBean(beanName, requiredType);
	}
}
