package java8demo.a_lambda;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java8demo.数据.User;
import org.junit.Assert;
import org.junit.Test;

/**
 * 在JavaScript中，function的参数是一个function，返回值是另一个function的情况是非常常见的
 * JavaScript是一门非常典型的函数式语言
 *
 * Lambda表达式为Java添加了函数式编程特性，使我们能将函数当做一等公⺠民看待
 * • 在将函数作为⼀一等公⺠民的语⾔言中，Lambda表达式
 * 的类型是函数。但在Java中，Lambda表达式是对
 * 象，他们必须依附于⼀一类特别的对象类型——函
 * 数式接⼝口(functional interface)
 *
 * lambda表达式需要<i>函数式接口<i/>的支持
 */
@SuppressWarnings("ALL")
public class b_lambda语法 {
    
    @Test
    public void test1() throws Exception {
        //Runnable就是一个函数式接口
        //匿名内部类
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类");
            }
        };
        //使用 lambda表达式 改写 匿名内部类
        Runnable runnable2 = () -> {
            System.out.println("lambda表达式");
        };
        //启动线程
        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread1.start();
        thread2.start();
    }
    
    @Test
    public void test2() throws Exception {
        //匿名内部类
        TestInterface 匿名内部类 = new TestInterface() {
            @Override
            public void print(String content) {
                System.out.println(content);
            }
        };
        //a_lambda
        TestInterface lambda = content -> System.out.println(content);
        //方法引用
        TestInterface 方法引用 = System.out::println;
        String content = "something";
        匿名内部类.print(content);
        lambda.print(content);
        方法引用.print(content);
    }
    
    @Test
    public void Function() throws Exception {
        //        lambda写法:
        Function<Integer, String> function1 = (Integer number) -> {
            return number + "";
        };
        // 简化
        //        由于泛型参数指定了类型  参数类型可以推断出为Integer   所以参数类型Integer 可以省去
        //        当只有一个参数，且其类型可推导时，圆括号()可省
        //        如果 Lambda 表达式的主体只有一条语句，花括号{} 和 return 可省略
        Function<Integer, String> function2 = number -> number + "";
        // 泛型参数不指定类型时默认为Object
        Function function = number -> number + "";
        //方法引用
        Function<Integer, String> function3 = String::valueOf;
        //        调用
        String result = function3.apply(110);
        Assert.assertTrue(result instanceof String);
    }
    
    @Test
    public void Consumer() throws Exception {
        //        lambda写法:
        User user = new User("用户名" , "密码");
        Consumer<User> consumer1 = (User somebody) -> {
            System.out.println(somebody);
        };
        //简写
        Consumer<User> consumer2 = somebody -> System.out.println(somebody);
        //方法引用
        Consumer<User> consumer3 = System.out::println;
        //        调用
        consumer3.accept(user);
    }
    
    @Test
    public void Supplier() throws Exception {
        //        lambda写法:
        Supplier<User> supplier1 = () -> {
            return new User("名字" , "密码");
        };
        //简写
        Supplier<User> supplier2 = () -> new User("名字" , "密码");
        //方法引用(无参构造器)
        Supplier<User> supplier3 = User::new;
    }
    
    @Test
    public void Predicate() throws Exception {
        //        lambda写法:
        Predicate<User> predicate1 = (User user) -> {
            return Objects.nonNull(user);
        };
        //简写
        Predicate<User> predicate2 = user -> Objects.nonNull(user);
        //方法引用
        Predicate<User> predicate3 = Objects::nonNull;
    }
}


