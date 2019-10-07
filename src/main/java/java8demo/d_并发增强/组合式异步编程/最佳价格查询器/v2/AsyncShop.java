package java8demo.d_并发增强.组合式异步编程.最佳价格查询器.v2;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import static java8demo.d_并发增强.Util.delayRandom;
import static java8demo.d_并发增强.Util.format;


public class AsyncShop {

    private final String name;
    private final Random random;

    public AsyncShop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public Future<Double> getPrice(String product) {
/*
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread( () -> {
                    try {
                        double price = calculatePrice(product);
                        futurePrice.complete(price);
                    } catch (Exception ex) {
                        futurePrice.completeExceptionally(ex);
                    }
        }).start();
        return futurePrice;
*/
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        delayRandom();
        if (true) throw new RuntimeException("product not available");
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

}