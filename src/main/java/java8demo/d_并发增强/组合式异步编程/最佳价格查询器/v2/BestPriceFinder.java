package java8demo.d_并发增强.组合式异步编程.最佳价格查询器.v2;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BestPriceFinder {


	public List<String> findPricesSequential(String product) {
		return shops.stream()
				.map(shop -> shop.getPrice(product))
				.map(Quote::parse)
				.map(Discount::applyDiscount)
				.collect(toList());
	}

	public List<String> findPricesParallel(String product) {
		return shops.parallelStream()
				.map(shop -> shop.getPrice(product))
				.map(Quote::parse)
				.map(Discount::applyDiscount)
				.collect(toList());
	}


	public Stream<CompletableFuture<String>> findPricesStream(String product) {
		return shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
				// thenApply 组合任务,指定某一线程执行a任务后再执行b任务
				.map(future -> future.thenApply(Quote::parse))
				//thenCompose 组合线程,将第一个线程的执行结果传递给第二个线程
				.map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));

		//三个map 对应 ABC 三个任务
		//AB 在同一个线程中顺序执行
		// C任务在另外的一个线程中执行

	}


	public List<String> findPricesFuture(String product) {

		return findPricesStream(product)
				.map(CompletableFuture::join)
				.collect(toList());
	}


	public void printPricesStream(String product) {
		long start = System.nanoTime();
		CompletableFuture[] futures = findPricesStream(product)
				.map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
				.toArray(size -> new CompletableFuture[size]);
		CompletableFuture.allOf(futures).join();
		System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
	}


	private final List<Shop> shops = Arrays.asList(
			new Shop("BestPrice"),
			new Shop("LetsSaveBig"),
			new Shop("MyFavoriteShop"),
			new Shop("BuyItAll"),
			new Shop("ShopEasy"));

	private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	});
}
