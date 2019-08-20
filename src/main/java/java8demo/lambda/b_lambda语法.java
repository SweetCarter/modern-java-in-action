package java8demo.lambda;

import org.junit.Test;

/**
 * 在JavaScript中，function的参数是一个function，返回值是另一个function的情况是非常常见的
 * JavaScript是一门非常典型的函数式语言
 *
 * Lambda表达式为Java添加了函数式编程特性，使我们能将函数当做⼀一等公⺠民看待
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
        //lambda
        TestInterface lambda = content -> System.out.println(content);
        //方法引用
        TestInterface 方法引用 = System.out::println;
        String content = "something";
        匿名内部类.print(content);
        lambda.print(content);
        方法引用.print(content);
    }
}


