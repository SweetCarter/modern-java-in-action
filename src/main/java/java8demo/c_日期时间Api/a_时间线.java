package java8demo.c_日期时间Api;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;

/**
 * 所有java.time 对象都不可变
 * 执行修改操作后的返回值必然是一个新的对象
 *
 * @author gulh
 * @since 2019/9/23 13:00
 */
@SuppressWarnings("ALL")
public class a_时间线 {
	/**
	 * 绝对时间
	 * 一个{@link Instant}表示时间轴上的一个点
	 *
	 * @throws InterruptedException
	 */
	@Test
	public void instant() throws InterruptedException {
		//返回当前瞬时点
		Instant start = Instant.now();
		Thread.sleep(10086);
		Instant end = Instant.now();
		//两个瞬时点之间的距离
		Duration duration = Duration.between(start, end);

	}

}
