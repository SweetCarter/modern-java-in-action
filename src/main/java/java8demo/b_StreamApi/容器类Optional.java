package java8demo.b_StreamApi;

/**
 * {@link java.util.Optional} 是一个容器类,代表一个值存在或者不存在
 * 原来使用null表示一个值不存在,现在 Optional可以更好的表达这个概念,可以很好的避免空指针
 * 常用方法
 * Optional.of (T t) 创建一个Optional 实例
 * Optional.empty() 创建一个空的Optional实例
 * Optional.ofNulllable(T t)  t不为null创建含t的Optional实例,否则创建空的Optional实例
 * isPersent()  是否包含值
 * get() 有值则返回,没有抛异常
 * orElse(T t)  有值则返回,没有返回t
 *
 * @author gulh
 * @since 2019/9/22 17:27
 */
@SuppressWarnings("ALL")
public class 容器类Optional {


}
