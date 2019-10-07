package java8demo.d_并发增强.组合式异步编程.最佳价格查询器.v2;

import java.util.Random;

import static java8demo.d_并发增强.Util.delayRandom;
import static java8demo.d_并发增强.Util.format;

public class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }

    public double calculatePrice(String product) {
        delayRandom();
        return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }

}
