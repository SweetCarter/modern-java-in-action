package java8demo.c_日期时间Api;

import org.junit.Test;

import java.time.LocalTime;

/**
 * @author gulh
 * @since 2019/9/26 12:39
 */
@SuppressWarnings("ALL")
public class d_本地时间 {

	/**
	 * {@link java.time.LocalTime}
	 * 表示一天中的某个时间 比如 15:00:00
	 * AM还是PM交由格式化程序管理
	 */

	@Test
	public void localTime() {
		//创建
		LocalTime now = LocalTime.of(12, 39, 46, 1234);
		now = LocalTime.now();

		//其他LoacalTime 的method的用法同LoacalDate

	}

}
