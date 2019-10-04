package java8demo.b_StreamApi;

import java8demo.数据.ComparableBean;
import java8demo.数据.User;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 一个流可以后面跟随零个或多个中间操作。
 * 其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。
 * 这类操作都是惰性化的，仅仅调用到这类方法，并没有真正开始流的遍历，真正的遍历需等到终端操作时，
 */
@SuppressWarnings("ALL")
public class b_中间操作 {
	private Stream<String> wordsStream = Stream.of("hello", "JAVA");

	private Stream<Character> characterStream = Stream.of('h', 'e', 'l', 'l', 'o', 'J', 'A', 'V', 'A');

	private User user1 = new User("A", 1);
	private User user2 = new User("B", 2);
	private User user3 = new User("C", 3);
	private Stream<User> userStream = Stream.of(user1, user2, user3);


	/**
	 * filter方法的参数是一个 {@link Predicate}对象
	 * 作用是保留：
	 * 从原始流中筛选出 使Predicate对象返回true的元素，转换到新的流中
	 */
	@Test
	public void filter() {
		//保留小写字符
		Stream<Character> stream = characterStream.filter(Character::isLowerCase);
	}

	/**
	 * map方法的参数是一个 {@link Function}对象
	 * 作用是映射
	 * 遍历原始流中的元素，作为 Function对象的参数，将Function对象的返回值转换到新的流中
	 */
	@Test
	public void map() {
		//取得所有用户的姓名
		Stream<String> nameStream = userStream.map(User::getName);

	}

	/**
	 * flatMap方法的参数是一个 {@link Function}对象
	 * 作用是将一个流中的流元素合并为一个流
	 */
	@Test
	public void flatMap() {
		//  假设	characterStream("HELLO") 的返回值为 ['h', 'e' , 'l' , 'l' , 'o' ]

		//  则  streamStream的值为  [ ['h', 'e' , 'l' , 'l' , 'o' ] , [ 'J','A','V','A']  ]
//		Stream<Stream<Character>> streamStream = wordsStream.map(a_创建流::characterStream);

		//  则  characterStream的值为   [ 'h', 'e' , 'l' , 'l' , 'o' , 'J', 'A' , 'V' , 'A' ]
//		Stream<Character> characterStream = wordsStream.flatMap(a_创建流::characterStream);

		//  则  stringStream的值为   [ "h", "e" , "l" , "l" , "o" , "J", "A" , "V" , "A" ]
//		Stream<String> stringStream = wordsStream.map(e -> e.split("")).flatMap(Stream::of);

		//将 hello JAVA 的字符转换为对应ASCII值
		IntStream intStream = wordsStream.flatMapToInt(e -> e.chars());


		//使用 flatMap迭代多个集合
		final List<Integer> number1 = Arrays.asList(1, 2, 3);
		final List<Integer> number2 = Arrays.asList(4, 5);
		final List<Integer> number3 = Arrays.asList(6, 7);
		number1.stream()
				.flatMap(a -> number2.stream().map(b -> new int[]{a, b}))
				.flatMap(array -> number3.stream().map(c -> new int[]{array[0], array[1], c}
				))
				.forEach(array -> System.out.println(Arrays.toString(array)));
	}


	/**
	 * distinct方法的作用是通过流中元素的equals()和hashCode() 去除重复元素
	 */
	@Test
	public void distinct() {
		Stream<Character> distinct = characterStream.distinct();
		//使用 toSet也可以去重
//		final Set<Character> collect = characterStream.collect(Collectors.toSet());
	}

	/**
	 * 截断流
	 * limit方法的作用是指定返回流的个数
	 * 参数必须 >=0 否则将会抛出异常
	 */
	@Test
	public void limit() {
		//		结果 等同于 Stream.of("hello");
		Stream<String> stringStream = wordsStream.limit(1);
		// 随机生成100 个数
		Stream<Double> doubleStream = Stream.generate(Math::random).limit(100);
	}

	/**
	 * 截断流
	 * skip方法的作用是跳过流中的元素
	 * 参数必须 >=0 否则将会抛出异常
	 */
	@Test
	public void skip() {
		Stream<String> stringStream = wordsStream.skip(1);
		//		结果 等同于 Stream.of("JAVA");
	}

	/**
	 * 自然排序
	 * 无参数的sorted方法
	 * 流中的元素必须实现{@link Comparable }接口，否则执行终端操作时会抛出{@link java.lang.ClassCastException}
	 */
	@Test
	public void naturalOrderSorted() {
		//自然排序
		Stream<Character> naturalOrder = characterStream.sorted();

		ComparableBean bean1 = new ComparableBean(1);
		ComparableBean bean2 = new ComparableBean(2);
		ComparableBean bean3 = new ComparableBean(3);
		Stream<ComparableBean> beanStream = Stream.of(bean3, bean2, bean1);
		//ComparableBean 对象实现了 Comparable 接口，以下操作会正常执行
		beanStream.sorted().forEach(e -> System.out.println(e.getNumber()));

		//User 对象未实现 Comparable 接口，以下操作会抛出异常
//		long count2 = userStream.sorted().count();
	}

	/**
	 * 定制排序
	 * 有参数的sorted方法
	 */
	@Test
	public void providedOrderSorted() {
		//定制排序
		Comparator<User> userComparator = (a, b) -> a.getAge() - b.getAge();
		Stream<User> stream = userStream.sorted(userComparator);
		stream.forEach(System.out::println);
	}

	/**
	 * unordered操作不会进行任何显式的打乱流的操作。
	 * 作用是：消除流中必须保持的有序约束，允许之后的操作不必考虑有序
	 */
	@Test
	public void unordered() {
		Stream<User> stream = userStream.unordered();
		stream.forEach(System.out::println);
	}

	/**
	 * peek方法，产生一个与原始流元素相同的流，但遍历元素时会调用一个函数
	 * 一般用于调试
	 */
	@Test
	public void peek() {
		Stream<User> stream = userStream.peek(e -> e.setAge(1));
		stream.forEach(System.out::println);
	}
}
