package java8demo.d_并发增强.组合式异步编程.最佳价格查询器.v1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ShopMain {

	public static void main(String[] args) {
		Shop shop = new Shop("BestShop");
		long start = System.nanoTime();

		Future<Double> futurePrice = shop.getPriceAsync("my favorite product");

		System.out.printf("Invocation returned after %d  msecs\n", (System.nanoTime() - start) / 1_000_000);

        Future<Double> doubleFuture = shop.getPriceAsync("another product");

		try {
			System.out.printf("Price is %.2f%n", futurePrice.get()+doubleFuture.get());
        } catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		}

		System.out.printf("Price returned after %d  msec\n", (System.nanoTime() - start) / 1_000_000);
	}
}
