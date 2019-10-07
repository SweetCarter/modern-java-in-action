package java8demo.d_并发增强;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.*;
import java.util.function.Supplier;

import static java.util.stream.Collectors.joining;

/**
 * @author gulh
 * @since 2019/10/6 13:05
 */
@SuppressWarnings("ALL")
public class Future接口 {

	/**
	 * 难以描述Future结果之间的依赖性
	 */
	@Test
	public void java8之前的用法() {
		ExecutorService executor = Executors.newCachedThreadPool();

		Future<Double> future = executor.submit(new Callable<Double>() {
			@Override
			public Double call() throws Exception {
				return 1 + 1.0;
			}
		});

		//异步操作进行的同时可以做其他事情
		Double another = 1.0;

		try {
			//指定主线程 等待future执行结果的超时时间
			Double result = future.get(1, TimeUnit.SECONDS);

			System.out.println(result - another);

		} catch (InterruptedException e) {
			//线程中断异常
			e.printStackTrace();
		} catch (ExecutionException e) {
			//执行时抛出异常
			e.printStackTrace();
		} catch (TimeoutException e) {
			//超时异常
			e.printStackTrace();
		}
	}

	/**
	 * java8开始
	 */
	@Test
	public void CompletableFuture() {
		ArrayList<String> datas = new ArrayList<>(Arrays.asList("完", "成"));
		//限定线程上限值
		int coreThreads = Math.min(datas.size(), 100);

		Executor executor = Executors.newFixedThreadPool(coreThreads, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				//设定为守护线程,主线程结束守护线程会被回收
				t.setDaemon(true);
				return t;
			}
		});

		Supplier<String> joining = () -> datas.stream().collect(joining(":"));

		CompletableFuture<String> future = CompletableFuture.supplyAsync(joining, executor);

		//other thing
		datas.stream().forEach(System.out::println);

		String result = future.join();
		System.out.println(result);

	}

}
