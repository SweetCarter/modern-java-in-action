package java8demo.d_并发增强.组合式异步编程.最佳价格查询器.v2;

import java.util.concurrent.Future;

public class AsyncShopClient {

    public static void main(String[] args) {
        AsyncShop shop = new AsyncShop("BestShop");
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPrice("myPhone");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        try {
            System.out.println("Price is " + futurePrice.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long doneTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + doneTime + " msecs");
    }
}