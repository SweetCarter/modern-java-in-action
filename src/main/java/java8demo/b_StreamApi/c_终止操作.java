package java8demo.b_StreamApi;

import java8demo.数据.Dish;
import java8demo.数据.User;
import org.junit.Test;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * 终止操作会从流中生成结果，可以是除Stream以外的任何结果，包括void
 *
 * @author gulh
 * @since 2019/9/22 12:02
 */
public class c_终止操作 {

	private List<Character> characters = Arrays.asList('h', 'e', 'l', 'l', 'o', 'J', 'A', 'V', 'A');


	private User user1 = new User("A", 10);
	private User user2 = new User("B", 20);
	private User user3 = new User("C", 30);
	private User user4 = new User("D", 40);
	private User user5 = new User("E", 50);
	private User user6 = new User("F", 50);


	/**
	 * 一个流只能执行一次终止操作
	 */
	@Test
	public void finish1() {
		Stream<Character> characterStream = characters.stream();

		characterStream.close();
		//以下代码会抛出异常信息 ：java.lang.IllegalStateException: stream has already been operated upon or closed
		//	long count1 = characterStream.count();
	}

	/**
	 * 一个流只能执行一次终止操作
	 */
	@Test
	public void finish2() {
		Stream<Character> characterStream = characters.stream();
		long count1 = characterStream.count();
		//以下代码会抛出异常信息 ：java.lang.IllegalStateException: stream has already been operated upon or closed
//		long count2 = characterStream.count();
	}


	/**
	 * 聚合方法 从流中找到答案
	 * 聚合方法都是终止操作
	 * 包括
	 * count 统计个数
	 * allMatch 全部匹配  参数为{@link Predicate}
	 * anyMatch 至少一个匹配 参数为{@link Predicate}
	 * noneMatch  没有匹配 参数为{@link Predicate}
	 * max 最大值
	 * min 最小值
	 * findFirst 返回非空集合中的第一个
	 * findAny  返回任意一个  (为了更好的利用并行流)
	 */
	@Test
	public void polymerize() {

	}

	/**
	 * 统计流中元素个数
	 */
	@Test
	public void count() {
		Stream<Character> characterStream = characters.stream();
		long count = characterStream.count();
		System.out.println(count);
	}

	/**
	 * 匹配
	 */
	@Test
	public void match() {
		Predicate<Character> condition = e -> e.equals('A');
		//全部都是A
		Stream<Character> characterStream1 = characters.stream();
		boolean flag1 = characterStream1.allMatch(condition);
		//至少一个A
		Stream<Character> characterStream2 = characters.stream();
		boolean flag2 = characterStream2.anyMatch(condition);
		//没有A
		Stream<Character> characterStream3 = characters.stream();
		boolean flag3 = characterStream3.noneMatch(condition);
	}

	/**
	 * 最大最小值
	 */
	@Test
	public void mm() {
		//排序规则
		Comparator<User> condition = (a, b) -> a.getAge() - b.getAge();
		//最小值
		Stream<User> userStream1 = Stream.of(user1, user2, user3);
		Optional<User> min = userStream1.min(condition);
		//最大值
		Stream<User> userStream2 = Stream.of(user1, user2, user3);
		Optional<User> max = userStream2.max(condition);


	}

	/**
	 * 查找
	 */
	@Test
	public void find() {
		Stream<User> userStream1 = Stream.of(user1, user2, user3);
		Optional<User> first = userStream1.findFirst();

		Stream<User> userStream2 = Stream.of(user1, user2, user3);
		//先 userStream2转为并行流
		Optional<User> any = userStream2.parallel().findAny();

	}


	/**
	 * 遍历流中元素，并执行一些操作
	 */
	@Test
	public void forEach() {
		Stream<User> userStream = Stream.of(user1, user2, user3);
		userStream.forEach(e -> System.out.println(e.getAge()));
	}

	/**
	 * 归约
	 * reduce方法
	 */
	@Test
	public void reduce() {
		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
		//求和
		//有初始值0
		Integer sum = integers.stream().reduce(0, Integer::sum);
		//没有初始值
		Optional<Integer> optional = integers.stream().reduce(Integer::sum);
		//有值就打印
		optional.ifPresent(System.out::println);
	}


	/**
	 * 收集
	 * collect方法
	 */
	@Test
	public void collect() {
		List<User> users = Arrays.asList(user1, user2, user3);
		//收集所有姓名存入List
		List<String> nameList = users.stream().map(User::getName).collect(toList());
		//收集所有姓名存入Set (无重复)
		Set<String> nameSet = users.stream().map(User::getName).collect(toSet());
		//收集所有姓名存入List (指定list的类型为LinkedList)
		LinkedList<String> linkedList = users.stream().map(User::getName).collect(toCollection(LinkedList::new));
		//收集姓名年龄存入map中
		Map<String, Integer> nameAge = users.stream().collect(toMap(User::getName, User::getAge));
		//收集所有姓名,连接成字符串,  按  , 隔开
		String namesStr = users.stream().map(User::getName).collect(joining(","));
		//对所有人年龄进行分析
		IntSummaryStatistics agesStatistics = users.stream().collect(summarizingInt(User::getAge));
		//最大值
		int max = agesStatistics.getMax();
		//最小值
		int min = agesStatistics.getMin();
		//平均值
		double average = agesStatistics.getAverage();
	}

	/**
	 * 分区/分片
	 */
	@Test
	public void group() {

		Function<User, String> userStringFunction = user -> {
			Integer age = user.getAge();
			if (age <= 18) {
				return "少年";
			} else if (age <= 40) {
				return "青壮年";
			} else {
				return "老年";
			}
		};

		List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6);
		//按年龄对用户分组
		Map<Integer, List<User>> group1 = users.stream().collect(Collectors.groupingBy(User::getAge));

		//多层分组
		Map<String, List<User>> group2 = users.stream().collect(Collectors.groupingBy(userStringFunction));

		//当分组函数是 Predicate 对象,一组返回true ,一组返回false时,使用分片函数partitioningBy效率更高
		Map<Boolean, List<User>> partitioning1 = users.stream().collect(Collectors.partitioningBy(user -> user.getAge() <= 20));

		//使用 downstream 收集器  按年龄分组,收集姓名
		Map<Integer, List<String>> group3 = users.stream().collect(Collectors.groupingBy(User::getAge, mapping(User::getName, toList())));
		// 使用 downstream 收集器 会产生很复杂的表达式,通过静态导包可简化代码
		//		import static java.util.stream.*;
		Map<Integer, List<String>> group4 = users.stream().collect(groupingBy(User::getAge, mapping(User::getName, toList())));

		//使用 downstream 收集器  按年龄段分组,收集该年龄段下的人数
		Map<String, Long> group5 = users.stream().collect(groupingBy(userStringFunction, counting()));


		//按type分组，查找每组热量最高的dish
		Map<Dish.Type, Dish> collect = Dish.menu.stream().collect(groupingBy(Dish::getType, collectingAndThen(maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
		//使用 toMap的三参数方法
		// 		* @param keyMapper 获得key的函数
		// 		* @param valueMapper 获得value的函数
		// 		* @param mergeFunction 处理相同key的函数
		collect = Dish.menu.stream().collect(toMap(Dish::getType, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(Dish::getCalories))));
	}


}
