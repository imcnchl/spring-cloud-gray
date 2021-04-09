package cn.caohongliang.gray.core.util.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.annotations.Expose;

/**
 * GsonUtils进行json处理的策略
 *
 * @author caohongliang
 */
public class GsonExclusionStrategies {

	public static final ExclusionStrategy serializationExclusionStrategy = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			Expose expose = f.getAnnotation(Expose.class);
			if (expose == null) {
				return false;
			}
			return !expose.serialize();
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
	};

	public static ExclusionStrategy deserializationExclusionStrategy = new ExclusionStrategy() {
		@Override
		public boolean shouldSkipField(FieldAttributes f) {
			Expose expose = f.getAnnotation(Expose.class);
			if (expose == null) {
				return false;
			}
			return !expose.deserialize();
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
	};
}
