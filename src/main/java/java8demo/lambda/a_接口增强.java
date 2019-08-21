package java8demo.lambda;

import java.util.function.Function;
import org.junit.Test;

@SuppressWarnings("ALL")
public class a_接口增强 {
    
    @Test
    public void test1() throws Exception {
        TestInterfaceImpl testInterfaceImpl = new TestInterfaceImpl();
        testInterfaceImpl.print("apple");
        testInterfaceImpl.printDefault("printDefault");
        TestInterface.printStatic("printDefault");
        
        String result = getFunction("first").apply("second");
    }
    
    Function<String, String> getFunction(String first) {
        System.out.println(first);
        return second -> {
            System.out.println(first + second);
            return first + second;
        };
    }
}

/**
 * 函数式接口:接口中只能有一个抽象方法的接口
 * 一般使用 {@link FunctionalInterface} 修饰,以检查是否是函数式接口
 *
 * jdk1.8中的 java.util.function  包 下定义了四个 <strong>核心</strong> 接口
 *
 * @see java.util.function.Function       接受一个参数,有返回值
 * @see java.util.function.Consumer     接受一个参数,无返回值
 * @see java.util.function.Supplier         不接受参数,有返回值
 * @see java.util.function.Predicate        接受参数,返回布尔值
 */
@FunctionalInterface
interface TestInterface {
    
    /**
     * 抽象方法
     * 接口实现类必须实现该方法
     */
    void print(String content);
    /**
     * 默认方法  default修饰
     * 默认方法使已经存在的接口可以修改而不会影响编译的过程。
     *
     * 例如
     *
     * @see java.util.Collection java8 中
     * 新增的额外方法 stream(), parallelStream(), forEach(),  removeIf()
     */
    default void printDefault(String content) {
        System.out.println("default" + content);
    }
    /**
     * 静态方法
     * 具有方法体
     * 使用接口名调用
     */
    static void printStatic(String content) {
        System.out.println("static" + content);
    }
}

class TestInterfaceImpl implements TestInterface {
    
    @Override
    public void print(String content) {
        System.out.println("实现类:" + content);
    }
}
