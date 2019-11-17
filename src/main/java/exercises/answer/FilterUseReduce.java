package exercises.answer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * 2. 只用reduce 和Lambda 表达式写出实现Stream 上的filter 操作的代码，如果不想返回
 * Stream，可以返回一个List。
 *
 * @author 271636872@qq.com
 * @since 19/11/9 18:10
 */
public class FilterUseReduce {
	public static void main(String[] args) {

		filterReturnStream(Stream.of(1, 2, 3), e -> e <= 2)
				.forEach(System.out::println);

		filterReturnList(Stream.of(1, 2, 3), e -> e <= 2)
				.forEach(System.out::println);
	}

	//return Stream
	private static <T> Stream<T> filterReturnStream(Stream<T> stream, Predicate<? super T> predicate) {
		return stream.reduce(Stream.empty(),
				(s, t) -> {
					if (predicate.test(t)) {
						return Stream.concat(s, Stream.of(t));
					}
					return s;
				}
				, Stream::concat
		);

	}

	//return List
	private static <I> List<I> filterReturnList(Stream<I> stream, Predicate<I> predicate) {
		List<I> initial = new ArrayList<>();
		return stream.reduce(initial,
				(List<I> acc, I x) -> {
					if (predicate.test(x)) {
						// We are copying data from acc to new list instance. It is very inefficient,
						// but contract of Stream.reduce method requires that accumulator function does
						// not mutate its arguments.
						// Stream.collect method could be used to implement more efficient mutable reduction,
						// but this exercise asks to use reduce method explicitly.
						List<I> newAcc = new ArrayList<>(acc);
						newAcc.add(x);
						return newAcc;
					} else {
						return acc;
					}
				},
				FilterUseReduce::combineLists);
	}

	private static <I> List<I> combineLists(List<I> left, List<I> right) {
		// We are copying left to new list to avoid mutating it.
		List<I> newLeft = new ArrayList<>(left);
		newLeft.addAll(right);
		return newLeft;
	}
}
