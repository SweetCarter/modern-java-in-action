package exercises.answer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * 1. 只用reduce 和Lambda 表达式写出实现Stream 上的map 操作的代码，如果不想返回
 * Stream，可以返回一个List。
 *
 * @author 271636872@qq.com
 * @since 19/11/9 18:10
 */
public class MapUseReduce {
	public static void main(String[] args) {

		mapReturnStream(Stream.of(1, 2, 3, 4, 5), e -> e + 1)
				.forEach(System.out::println);

		mapReturnList(Stream.of(1, 2, 3, 4, 5), e -> e + 1)
				.forEach(System.out::println);
	}

	//return Stream
	private static <T, R> Stream<R> mapReturnStream(Stream<T> stream, Function<? super T, ? extends R> mapper) {
		return stream.reduce(Stream.empty(),
				(s, t) -> Stream.concat(s, Stream.of(mapper.apply(t))),
				Stream::concat
		);
	}

	//return List
	private static <I, O> List<O> mapReturnList(Stream<I> stream, Function<I, O> mapper) {
		return stream.reduce(new ArrayList<O>(), (acc, x) -> {
			// We are copying data from acc to new list instance. It is very inefficient,
			// but contract of Stream.reduce method requires that accumulator function does
			// not mutate its arguments.
			// Stream.collect method could be used to implement more efficient mutable reduction,
			// but this exercise asks to use reduce method.
			List<O> newAcc = new ArrayList<>(acc);
			newAcc.add(mapper.apply(x));
			return newAcc;
		}, (List<O> left, List<O> right) -> {
			// We are copying left to new list to avoid mutating it.
			List<O> newLeft = new ArrayList<>(left);
			newLeft.addAll(right);
			return newLeft;
		});
	}
}
