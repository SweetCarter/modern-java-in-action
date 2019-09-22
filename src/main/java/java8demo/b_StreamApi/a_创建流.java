package java8demo.b_StreamApi;


/*
 * Steam表面上与集合类似，允许改变和获取数据，但实际上与集合差别很大：
 * 一：Stream不会自己存储元素。元素可能被存储在底层集合中，或者根据需要产生出来。
 * 二：Stream 不会改变源对象，相反会产生新的Stream
 * 三：Stream 可能是延迟执行的，即需要结果的时候才执行。
 */

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Stream的操作分三个阶段
 * 一：创建
 * 二：中间操作
 * 三：中止操作
 */
@SuppressWarnings("ALL")
public class a_创建流 {
	/**
	 * 开发中最常用的方式
	 * 1.	java.util.Collection接口提供的方法：
	 */
	@Test
	public void collection() {
		Collection<String> list = new ArrayList<>();

		//获得顺序流
		Stream<String> stream = list.stream();
		//获得并行流
		Stream<String> parallelStream = list.parallelStream();

	}

	/**
	 * 通过数组生成
	 * 2. java.util.Arrays.stream()
	 */
	@Test
	public void arrays() {

		Integer[] numbers = new Integer[0];
		Stream<Integer> stream = Arrays.stream(numbers);

		int[] nums = new int[0];
		IntStream intStream = Arrays.stream(nums);
	}

	/**
	 * 3. java.util.stream.Stream提供的一系列静态方法
	 */
	@Test
	public void stream() {
		Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
		//空流
		Stream<Object> empty = Stream.empty();
		//无限流
		Stream<Integer> integerStream = Stream.iterate(0, (n) -> n + 1);
		//无限流
		Stream<Double> doubleStream = Stream.generate(Math::random);
		//流的拼接
		Stream<Integer> concat = Stream.concat(stream, integerStream);
	}

	final Pattern compile = Pattern.compile("\\D");

	/**
	 * 4. 其他类提供的方法
	 */
	@Test
	public void other() throws IOException {

		Stream<String> lines = Files.lines(Paths.get("文件名"));

		Stream<String> stringStream = compile.splitAsStream("123456");

	}


	/**
	 * 5.  concat方法的作用是合并两个流
	 */
	@Test
	public static void concat() {
		Stream<Character> concat = Stream.concat(characterStream("hello"), characterStream("JAVA"));
	}

	public static Stream<Character> characterStream(String source) {
		List<Character> characters = new LinkedList<>();
		for (char c : source.toCharArray()) {
			characters.add(c);
		}
		return characters.stream();
	}


	/**
	 * 原始类型流
	 * 将每个基本类型int包装成Integer得到Stream<Integer>再运算是一种相当低效的做法
	 * StreamAPI中提供了以下几种原始类型流,专门用来处理基本类型数据
	 *
	 * @see java.util.stream.IntStream  用于 byte char short  int  boolean
	 * @see java.util.stream.DoubleStream 用于 float double
	 * @see java.util.stream.LongStream 用于 long
	 * 设计认为不需要为8中基本数据类型都提供对应的stream
	 */
	@Test
	public void primitiveStream() {

		int[] nums = {1, 2, 3, 4, 5};
		IntStream stream = IntStream.of(nums);
		IntStream intStream = Arrays.stream(nums);

		//		IntStream和LongStream  还有以下两个静态方法
		//range(0, 100)					 0 -99 不含上限
		//rangeClosed(0, 100) 			0 -100 含上限
		IntStream range1 = IntStream.range(0, 100);
		IntStream range2 = IntStream.rangeClosed(0, 100);
		LongStream range3 = LongStream.range(0, 100);
		LongStream range4 = LongStream.rangeClosed(0, 100);

	}

	/**
	 * 并行流
	 * 默认情况流操作会创建一个串行流
	 * Stream.parallel()可将任意的stream转为并行流
	 * Collection.parallelStream()可取得一个并行流
	 */
	@Test
	public void parallelStream() {

		Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5).parallel();

		List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);
		Stream<Integer> parallelStream = integers.parallelStream();
	}
}
