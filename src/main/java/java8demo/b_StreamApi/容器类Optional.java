package java8demo.b_StreamApi;

import org.junit.Test;

import java.util.Optional;

/**
 * {@link java.util.Optional} 是一个容器类,代表一个值存在或者不存在
 * 原来使用null表示一个值不存在,现在 Optional可以更好的表达这个概念,可以很好的避免空指针
 * 常用方法
 * Optional.of (T t) 创建一个Optional 实例
 * Optional.empty() 创建一个空的Optional实例
 * Optional.ofNulllable(T t)  t不为null创建含t的Optional实例,否则创建空的Optional实例
 * isPersent()  是否包含值
 * get() 有值则返回,没有抛异常
 * orElse(T t)  有值则返回,没有返回t
 *
 * @author gulh
 * @since 2019/9/22 17:27
 */
@SuppressWarnings("ALL")
public class 容器类Optional {


	/**
	 * 创建Optional对象
	 */
	@Test
	public void create() {

		//创建空的Optional对象
		Optional<Object> empty = Optional.empty();

		//根据一个非null值创建Optional对象 当对象为null时立即抛出空指针
		Optional<Object> o = Optional.of(new Object());

		//可接受null的Optional 当对象为null时返回 Optional.empty()
		Optional<Object> optional = Optional.ofNullable(null);

	}

	/**
	 * 使用map方法取值
	 */
	@Test
	public void map() {
		//常用取值方式
		Insurance insurance = new Insurance();
		String name;
		if (insurance != null) {
			name = insurance.getName();
		}

		//	Optional方式
		Optional<Insurance> optional = Optional.ofNullable(new Insurance());
		String stringOptional = optional.map(Insurance::getName).orElseThrow(() -> new RuntimeException("业务异常"));

	}

	/**
	 * 使用flatMap方法取值
	 */
	@Test
	public void flatMap() {
		//常用取值方式 
		// 激进的null检查
		String name1 = deepCheck(new Person());
		String name2 = mutilReturn(new Person());


		//	Optional方式
		Optional<Person> person = Optional.ofNullable(new Person());
		person.map(Person::getCar)
				.map(Car::getInsurance)
				.map(Insurance::getName)
				.ifPresent(System.out::println);


		Optional<Person> optionalPerson = Optional.ofNullable(new Person());
		optionalPerson.flatMap(Person::getCarAsOptional)
				.flatMap(Car::getInsuranceAsOptional)
				.map(Insurance::getName)
				.ifPresent(System.out::println);

	}


	/**
	 * 检查方式一 ：深层质疑
	 *
	 * @param person ignore
	 * @return ignore
	 */
	private String deepCheck(Person person) {
		if (person != null) {
			Car car = person.getCar();
			if (car != null) {
				Insurance insurance = car.getInsurance();
				if (insurance != null) {
					return insurance.getName();
				}
			}
		}
		return "unknow";
	}

	/**
	 * 检查方式二 ：过多退出语句
	 *
	 * @param person ignore
	 * @return ignore
	 */
	private String mutilReturn(Person person) {
		String defaultValue = "unknow";
		if (person == null) {
			return defaultValue;
		}

		Car car = person.getCar();
		if (car == null) {
			return defaultValue;
		}

		Insurance insurance = car.getInsurance();
		if (insurance == null) {
			return defaultValue;
		}
		return insurance.getName();
	}


	class Person {
		private Car car;

		public Car getCar() {
			return car;
		}

		public Optional<Car> getCarAsOptional() {
			return Optional.ofNullable(car);
		}
	}

	class Car {
		private Insurance insurance;

		public Insurance getInsurance() {
			return insurance;
		}

		public Optional<Insurance> getInsuranceAsOptional() {
			return Optional.ofNullable(insurance);
		}
	}

	class Insurance {

		private String name;

		public String getName() {
			return name;
		}
	}
}
