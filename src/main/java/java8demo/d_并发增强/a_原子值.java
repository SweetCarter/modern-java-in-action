package java8demo.d_并发增强;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 271636872@qq.com
 * @since 19/9/27 0:14
 */
@SuppressWarnings("ALL")
public class a_原子值 {
	/**
	 * 从java 5 开始
	 * {@link java.util.concurrent.atomic} 包中提供了用于支持无锁可变变量的类
	 */
	@Test
	public void atomic() {
		final AtomicLong atomicLong = new AtomicLong();
		final long id = atomicLong.incrementAndGet();
		ConcurrentHashMap map=new ConcurrentHashMap();
	}

}
