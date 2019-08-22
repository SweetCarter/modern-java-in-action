package java8demo.a_lambda;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java8demo.数据.User;
import org.junit.Test;

/**
 * 方法引用：若Lambda体中的功能，已经有方法提供了实现，可以使用方法引用
 *
 * 方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 构造器的参数列表，需要与函数式接口中参数列表保持一致！
 */
@SuppressWarnings("ALL")
public class c_方法引用 {
    
    @Test
    public void test1() throws Exception {
        //   取得所有人名字
        //类::实例方法
        Stream<User> stream = Stream.of(new User("A" , "1") , new User("B" , "2"));
        List<String> names = stream.map(User::getName).collect(Collectors.toList());
    }
    
    @Test
    public void test2() throws Exception {
        //字符连接
        // 对象::实例方法
        StringBuilder test = new StringBuilder();
        Stream<String> strigStream = Stream.of("h" , "e" , "l" , "l" , "o");
        //        strigStream.forEach(test::append);
        strigStream.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                test.append(s);
            }
        });
    }
    
    @Test
    public void test3() throws Exception {
        // 取最大值
        // 类::静态方法
        Stream<Integer> integerStream = Stream.of(1 , 2 , 3 , 4);
        Integer integer = integerStream.max(Integer::compare).get();
    }
    
    @Test
    public void other() throws Exception {
        //其他形式
        Supplier b = User::new;
        IntConsumer a = int[]::new;
        Supplier c = super::toString;
        Supplier d = this::toString;
    }
}
