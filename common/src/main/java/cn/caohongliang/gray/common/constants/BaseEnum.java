package cn.caohongliang.gray.common.constants;

/**
 * 所有值为int类型的枚举都应该继承该类
 * 该类提供了对枚举值valueOf的方法
 *
 * @author caohongliang
 */
public interface BaseEnum {

	/**
	 * 代码
	 *
	 * @return code
	 */
	int getCode();

	/**
	 * 重写valueOf方法，提供将code转换为对应的枚举
	 *
	 * @param enumType 枚举类型
	 * @param code     code
	 * @param <E>      对应的枚举
	 * @return 对应的枚举
	 */
	static <E extends BaseEnum> E valueOf(Class<E> enumType, Integer code) {
		if (code == null) {
			return null;
		}
		for (E enumConstants : enumType.getEnumConstants()) {
			if (enumConstants.getCode() == code) {
				return enumConstants;
			}
		}
		return null;
	}
}
