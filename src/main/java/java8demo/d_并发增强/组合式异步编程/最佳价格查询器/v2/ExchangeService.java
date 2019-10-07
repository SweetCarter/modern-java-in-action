package java8demo.d_并发增强.组合式异步编程.最佳价格查询器.v2;

import static java8demo.d_并发增强.Util.delayRandom;

public class ExchangeService {

    public enum Money {
        USD(1.0), EUR(1.35387), GBP(1.69715), CAD(.92106), MXN(.07683);

        private final double rate;

        Money(double rate) {
            this.rate = rate;
        }
    }

    public static double getRate(Money source, Money destination) {
        return getRateWithDelay(source, destination);
    }

    private static double getRateWithDelay(Money source, Money destination) {
        delayRandom();
        return destination.rate / source.rate;
    }

}
