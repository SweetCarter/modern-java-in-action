package java8demo.d_并发增强;

import lombok.Data;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

/**
 * https://colobu.com/2018/03/12/20-Examples-of-Using-Java%E2%80%99s-CompletableFuture/
 * <p>
 * {@link CompletableFuture}类实现两个接口
 * <p>
 * {@link java.util.concurrent.CompletionStage }
 * 它代表了一个特定的计算的<i>阶段</i>，可以同步或者异步的被完成。
 * 你可以把它看成一个计算流水线上的一个单元，最终会产生一个最终结果，
 * 这意味着几个CompletionStage可以串联起来，一个完成的阶段可以触发下一阶段的执行，接着触发下一次，接着……
 * <p>
 * {@link java.util.concurrent.Future }
 * 它代表一个<i>未完成的异步事件</i>
 * CompletableFuture提供了方法，能够显式地完成这个future,所以它叫CompletableFuture。
 *
 * @author 271636872@qq.com
 * @since 19/11/16 22:22
 */
@SuppressWarnings("all")
public class CompletableFuture20个例子 {
	final String message = "message";
	final String MESSAGE = "MESSAGE";
	final String original = "Message";
	final ExecutorService executor =
			Executors.newFixedThreadPool(3, new ThreadFactory() {
				@Override
				public Thread newThread(Runnable runnable) {
					final Thread thread = new Thread(runnable);
					//设为守护线程
					thread.setDaemon(true);
					return thread;
				}
			});

	final Executor delayedExecutor = CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS);

	/**
	 * 1.
	 * 创建已经完成的CompletableFuture,通常会在计算的开始阶段使用它
	 */
	@Test
	public void completedFutureExample() {
		CompletableFuture completedFuture =
				CompletableFuture.completedFuture(message);
		assertTrue(completedFuture.isDone());
		assertEquals(message, completedFuture.getNow(null));
	}

	/**
	 * 2.
	 * CompletableFuture的方法如果以Async结尾，它会异步的执行(没有指定executor的情况下)，
	 * 异步执行通过ForkJoinPool实现， 它使用守护线程去执行任务。注意这是CompletableFuture的特性，
	 * 其它CompletionStage可以override这个默认的行为。
	 * 运行一个简单的异步阶段
	 */
	@Test
	public void runAsyncExample() {

		CompletableFuture future = CompletableFuture.runAsync(() -> {
			isDaemonThread(true);
			sleep();
		});

		assertFalse(future.isDone());
		sleepEnough();
		assertTrue(future.isDone());
	}

	/**
	 * 3.
	 * then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
	 * <p>
	 * Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
	 */
	@Test
	public void thenApplyExample() {
		CompletableFuture cf = CompletableFuture.completedFuture(message)
				.thenApply(s -> {
					isMainThread(true);
					isDaemonThread(false);
					return s.toUpperCase();
				});

		assertEquals(MESSAGE, cf.getNow(null));
	}

	/**
	 * 4.
	 * then意味着这个阶段的动作发生当前的阶段正常完成之后。本例中，当前节点完成，返回字符串message。
	 * <p>
	 * Apply意味着返回的阶段将会对结果前一阶段的结果应用一个函数。
	 * <p>
	 * Async意味着异步方式执行
	 */
	@Test
	public void thenApplyAsyncExample() {

		CompletableFuture cf = CompletableFuture.completedFuture(message)
				.thenApplyAsync(s -> {
					isDaemonThread(true);
					sleep();
					return s.toUpperCase();
				});
		assertNull(cf.getNow(null));
		assertEquals(MESSAGE, cf.join());
	}

	/**
	 * 5.
	 * 使用定制的Executor在前一个阶段上异步应用函数
	 */
	@Test
	public void thenApplyAsyncWithExecutorExample() {

		CompletableFuture cf = CompletableFuture.completedFuture(message)
				.thenApplyAsync(s -> {
					isDaemonThread(true);
					sleep();
					return s.toUpperCase();
				}, executor);
		assertNull(cf.getNow(null));
		assertEquals(MESSAGE, cf.join());
	}

	/**
	 * 6.
	 * 如果下一阶段接收了当前阶段的结果，但是在计算的时候不需要返回值(它的返回类型是void)，
	 * 那么它可以不应用一个函数，而是一个消费者， 调用方法也变成了thenAccept
	 */
	@Test
	public void thenAcceptExample() {
		StringBuilder result = new StringBuilder();
		CompletableFuture.completedFuture(message)
				.thenAccept(result::append);
		assertEquals(result.toString(), message);
	}

	/**
	 * 7.
	 * 异步地消费迁移阶段的结果
	 */
	@Test
	public void thenAcceptAsyncExample() {
		StringBuilder result = new StringBuilder();
		CompletableFuture<Void> future = CompletableFuture.completedFuture(message)
				.thenAccept(result::append);
		future.join();
		assertEquals(result.toString(), message);
	}

	/**
	 * 8.
	 * 完成计算异常
	 * 异步操作如何显式地返回异常，用来指示计算失败。
	 * 操作处理一个字符串，把它转换成答谢，我们模拟延迟一秒。
	 * 使用thenApplyAsync(Function, Executor)方法，第一个参数传入大写函数， executor是一个delayed executor,在执行前会延迟一秒。
	 */
	@Test
	public void completeExceptionallyExample() {
		final Executor delayedExecutor = CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS);

		CompletableFuture<String> future = CompletableFuture.completedFuture(message)
				.thenApplyAsync(String::toUpperCase, delayedExecutor);

		CompletableFuture<String> exceptionHandler = future.handle((s, throwable) -> {
			return (throwable != null) ? "message upon cancel" : "";
		});

		//此时没有完成计算则抛出指定异常
		future.completeExceptionally(new RuntimeException("completed exceptionally"));
		assertTrue("Was not completed exceptionally", future.isCompletedExceptionally());
		try {
			System.out.println(future.join());
			fail("Should have thrown an exception");
		} catch (CompletionException ex) {
			System.out.println(ex.getMessage());
		}
		assertEquals("message upon cancel", exceptionHandler.join());
	}

	/**
	 * 9.
	 * 取消计算
	 * 和完成异常类似，
	 * 可以调用cancel(boolean mayInterruptIfRunning)取消计算。
	 * 对于CompletableFuture类，布尔参数并没有被使用，这是因为它并没有使用中断去取消操作，
	 * 相反，cancel等价于completeExceptionally(new CancellationException())。
	 */
	@Test
	public void cancelExample() {
		CompletableFuture cf = CompletableFuture.completedFuture("message")
				.thenApplyAsync(String::toUpperCase, delayedExecutor);
		CompletableFuture cf2 = cf.exceptionally(throwable -> "canceled message");
		assertTrue("Was not canceled", cf.cancel(true));
		assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
		assertEquals("canceled message", cf2.join());
	}

	/**
	 * 10.
	 * 下面的例子创建了CompletableFuture, applyToEither处理两个阶段，
	 * 在其中之一上应用函数(不保证哪一个被执行)。
	 * 本例中的两个阶段一个是应用大写转换在原始的字符串上， 另一个阶段是应用小些转换。
	 */
	@Test
	public void applyToEitherExample() {
//		while (true) {
		final CompletableFuture<String> one =
				CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
					randomSleep();
					return s.toUpperCase();
				});

		final CompletableFuture<String> another =
				CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
					randomSleep();
					return s.toLowerCase();
				});

		CompletableFuture<String> future = one.applyToEither(another, Function.identity());
		System.out.println(future.join());
//		}
	}

	/**
	 * 11.
	 * 调用消费函数
	 */
	@Test
	public void acceptEitherExample() {
//		while (true) {
		StringBuilder result = new StringBuilder();
		final CompletableFuture<String> one =
				CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
					randomSleep();
					return s.toUpperCase();
				});

		final CompletableFuture<String> another =
				CompletableFuture.completedFuture(original).thenApplyAsync(s -> {
					randomSleep();
					return s.toLowerCase();
				});

		CompletableFuture future = one.acceptEither(another, System.out::println);
		future.join();
//		}
	}

	/**
	 * 12.
	 * 两个阶段都执行完后运行一个 Runnable
	 * 注意下面所有的阶段都是同步执行的
	 */
	@Test
	public void runAfterBothExample() {
		StringBuilder result = new StringBuilder();

		final CompletableFuture one =
				CompletableFuture.completedFuture(original)
						.thenAccept(s -> {
							randomSleep();
							System.out.println(s.toUpperCase());
						});

		final CompletableFuture another =
				CompletableFuture.completedFuture(original)
						.thenAccept(s -> {
							randomSleep();
							System.out.println(s.toLowerCase());
						});

		one.runAfterBoth(another,
				() -> {
					randomSleep();
					result.append("done");
				});
		assertTrue("Result was empty", result.length() > 0);
	}

	/**
	 * 13.
	 * 使用BiConsumer处理两个阶段的结果
	 */
	@Test
	public void thenAcceptBothExample() {

		final CompletableFuture<String> one =
				CompletableFuture.completedFuture(original).thenApply(String::toUpperCase);

		final CompletableFuture<String> another =
				CompletableFuture.completedFuture(original).thenApply(String::toLowerCase);

		one.thenAcceptBoth(another,
				(oneResult, anotherResult) -> {
					System.out.println("oneResult=" + oneResult);
					System.out.println("anotherResult=" + anotherResult);
				});
	}

	/**
	 * 14.
	 * 使用BiFunction处理两个阶段的结果
	 * 如果CompletableFuture依赖两个前面阶段的结果， 它复合两个阶段的结果再返回一个结果，
	 * 我们就可以使用thenCombine()函数。整个流水线是同步的，
	 * 所以getNow()会得到最终的结果，它把大写和小写字符串连接起来。
	 */
	@Test
	public void thenCombineExample() {

		final CompletableFuture<String> one =
				CompletableFuture.completedFuture(original).thenApply(String::toUpperCase);

		final CompletableFuture<String> another =
				CompletableFuture.completedFuture(original).thenApply(String::toLowerCase);

		final CompletableFuture<String> future = one.thenCombine(another, String::concat);
		assertEquals("MESSAGEmessage", future.getNow(null));
	}

	/**
	 * 15.异步使用BiFunction处理两个阶段的结果
	 * <p>
	 * 类似上面的例子，但是有一点不同： 依赖的前两个阶段异步地执行，所以thenCombine()也异步地执行，即时它没有Async后缀。
	 * <p>
	 * Javadoc中有注释：
	 * <p>
	 * Actions supplied for dependent completions of non-async methods
	 * may be performed by the thread that completes the current CompletableFuture,
	 * or by any other caller of a completion method
	 * <p>
	 * 所以我们需要join方法等待结果的完成。
	 */
	@Test
	public void thenCombineAsyncExample() {

		final CompletableFuture<String> one =
				CompletableFuture.completedFuture(original).thenApplyAsync(String::toUpperCase);

		final CompletableFuture<String> another =
				CompletableFuture.completedFuture(original).thenApply(String::toLowerCase);

		final CompletableFuture<String> future = one.thenCombine(another, String::concat);
		assertEquals("MESSAGEmessage", future.join());
	}

	/**
	 * 16.
	 * 组合 CompletableFuture
	 */
	@Test
	public void thenComposeExample() {

		final CompletableFuture<String> one =
				CompletableFuture.completedFuture(original).thenApplyAsync(String::toUpperCase);

		final CompletableFuture<String> another =
				CompletableFuture.completedFuture(original).thenApply(String::toLowerCase);

		final CompletableFuture<String> future = one.thenCompose(
				oneResult -> another.thenApply(
						anotherResult -> oneResult.concat(anotherResult)));
		assertEquals("MESSAGEmessage", future.join());
	}

	/**
	 * 17、当几个阶段中的一个完成，创建一个完成的阶段
	 * <p>
	 * 下面的例子演示了当任意一个CompletableFuture完成后， 创建一个完成的CompletableFuture.
	 * <p>
	 * 待处理的阶段首先创建， 每个阶段都是转换一个字符串为大写。因为本例中这些阶段都是同步地执行(thenApply), 从anyOf中创建的CompletableFuture会立即完成，这样所有的阶段都已完成，我们使用whenComplete(BiConsumer<? super Object, ? super Throwable> action)处理完成的结果。
	 */
	@Test
	public void anyOfExample() {
		StringBuilder result = new StringBuilder();
		List<String> messages = Arrays.asList("a", "b", "c");
		List<CompletableFuture<String>> futures = messages.stream()
				.map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> {
					randomSleep();
					return s.toUpperCase();
				}))
				.collect(toList());

		CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()]))
				.whenComplete((res, throwable) -> {
					if (throwable == null) {
						System.out.println(res);
					}
				});
	}

	/**
	 * 18、
	 * 当所有的阶段都完成后创建一个阶段
	 * 同步执行
	 */
	@Test
	public void allOfExample() {
		StringBuilder result = new StringBuilder();
		List<String> messages = Arrays.asList("a", "b", "c");
		List<CompletableFuture<String>> futures = messages.stream()
				.map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> {
					isMainThread(true);
					randomSleep();
					return s.toUpperCase();
				}))
				.collect(toList());

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
				.whenComplete((res, throwable) -> {
					if (throwable == null) {
						System.out.println(res);
					}
				});
	}

	/**
	 * 19、
	 * 当所有的阶段都完成后创建一个阶段
	 * 异步执行
	 */
	@Test
	public void allOfAsyncExample() {
		StringBuilder result = new StringBuilder();
		List<String> messages = Arrays.asList("a", "b", "c");
		List<CompletableFuture<String>> futures = messages.stream()
				.map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> {
					isMainThread(false);
					randomSleep();
					return s.toUpperCase();
				}))
				.collect(toList());

		CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
				.whenComplete((res, throwable) -> {
					if (throwable == null) {
						System.out.println(res);
					}
				}).join();
	}

	/**
	 * 20、实践场景
	 * ①首先异步调用cars方法获得Car的列表，它返回CompletionStage场景。cars消费一个远程的REST API。
	 * ②然后我们复合一个CompletionStage填写每个汽车的评分，通过rating(manufacturerId)返回一个CompletionStage,
	 * 它会异步地获取汽车的评分(可能又是一个REST API调用)
	 * ③当所有的汽车填好评分后，我们结束这个列表，所以我们调用allOf得到最终的阶段， 它在前面阶段所有阶段完成后才完成。
	 * ④在最终的阶段调用whenComplete(),我们打印出每个汽车和它的评分。
	 * <p>
	 * 因为每个汽车的实例都是独立的，得到每个汽车的评分都可以异步地执行，这会提高系统的性能(延迟)，
	 * 而且等待所有的汽车评分被处理使用的是allOf方法，
	 * 而不是手工的线程等待(Thread#join() 或 a CountDownLatch)。
	 */
	@Test
	public void actualExample() {
		cars().thenCompose(cars -> {
			List<CompletionStage> updatedCars = cars.parallelStream()
					.map(car -> rating(car.manufacturerId).thenApply(r -> {
						System.out.println(Thread.currentThread().getName());
						car.setRating(r);
						return car;
					})).collect(toList());

			return CompletableFuture
					.allOf(updatedCars.toArray(new CompletableFuture[updatedCars.size()]))
					.thenApply(v ->
							updatedCars.stream()
									.map(CompletionStage::toCompletableFuture)
									.map(CompletableFuture::join).collect(toList()));
		}).whenComplete((cars, th) -> {
			if (th == null) {
				cars.forEach(System.out::println);
			} else {
				throw new RuntimeException(th);
			}
		}).toCompletableFuture().join();
	}

	private CompletionStage<String> rating(String manufacturerId) {
		randomSleep();
		return CompletableFuture.completedFuture(manufacturerId);
	}

	private CompletionStage<List<Car>> cars() {
		randomSleep();
		return CompletableFuture.completedFuture(Arrays.asList(
				new Car("A"), new Car("B"),
				new Car("C"), new Car("D"),
				new Car("E"), new Car("F"),
				new Car("G"), new Car("H")
		));

	}

	private void sleep() {
		try {
			Thread.sleep(50L);
			System.out.println("sleepTime=50");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sleepEnough() {
		sleep();
		sleep();
	}

	private void randomSleep() {

		long sleepTime = (long) (Math.random() * 1000);
		try {
			Thread.sleep(sleepTime);
			System.out.println("randomSleepTime=" + sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void isDaemonThread(boolean flag) {
		final boolean daemon = Thread.currentThread().isDaemon();
		if (flag) {
			assertTrue(daemon);
		} else {
			assertFalse(daemon);
		}
	}

	private void isMainThread(boolean flag) {
		final String currentThreadName = Thread.currentThread().getName();
		final String mainThreadName = "main";
		if (flag) {
			assertEquals(mainThreadName, currentThreadName);
		} else {
			assertNotEquals(mainThreadName, currentThreadName);
		}
	}

	@Data
	private class Car {
		private String rating;
		private String manufacturerId;

		public Car(String manufacturerId) {
			this.manufacturerId = manufacturerId;
		}

		@Override
		public String toString() {
			return "Car{" +
					"rating='" + rating + '\'' +
					", manufacturerId='" + manufacturerId + '\'' +
					'}';
		}
	}


}
