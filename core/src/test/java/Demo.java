import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Demo {
	@AllArgsConstructor
	public static class Test {
		private int num;

		public boolean validate(){
			System.out.println("validate " + num);
			return true;
		}
	}

	public static void main(String[] args) {
		List<Test> tests = Lists.newArrayList(
				new Test(1),
				new Test(2),
				new Test(3),
				new Test(4)
		);
		Optional<Test> first = tests.stream().filter(test -> test.validate())
				.findAny();
		System.out.println("------s=" +first);

		Integer a = null;
		Integer b=null;
		System.out.println(Objects.equals(a, b));
	}
}
