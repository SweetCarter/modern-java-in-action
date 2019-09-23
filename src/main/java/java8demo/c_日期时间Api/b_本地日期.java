package java8demo.c_日期时间Api;

import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * @author gulh
 * @since 2019/9/23 23:00
 */
@SuppressWarnings("ALL")
public class b_本地日期 {
	/**
	 * 人类时间之:
	 * 本地日期
	 * 日期不含一天中的时间，也不含时区信息，因此无法与准确的瞬时点对应
	 * 生日/假期/会议时间等最好使用本地日期或时间表示
	 * 带时区的时间日期会受夏令时变化的影响，可以使用{@link Period}而不是{@link Duration}处理带时区的时间
	 */
	@Test
	public void localDate() {
		//创建
		LocalDate today = LocalDate.now();

		//java.util.Date中月份从0开始
		//LocalDate月份从1 开始
		LocalDate today2 = LocalDate.of(2019, 9, 1);

		//LocalDate 的toString 默认转为 yyyy-MM-dd 格式
		boolean equals = "2019-09-01".equals(today2.toString());

		//java8 中月份也可用枚举Month表示
		LocalDate today3 = LocalDate.of(2019, Month.SEPTEMBER, 1);

		//明天
		LocalDate plusDays = today.plusDays(1);
		LocalDate tomorrow = today.minusDays(-1);
		//昨天
		LocalDate yesterday = today.minusDays(1);

		//修改年月日
		LocalDate future = today.withYear(2020).withMonth(9).withDayOfMonth(1);

		//获得星期几  DayOfWeek 的枚举值
		DayOfWeek dayOfWeek = today.getDayOfWeek();

		//  java.util.Calendar  周日为1
		// DayOfWeek 周一为1
		int value = dayOfWeek.getValue();

		//比较日期
		//true
		boolean before = today.isBefore(tomorrow);
		//false
		boolean after = today.isAfter(tomorrow);
		//判断闰年
		boolean leapYear = today.isLeapYear();

		//获得日期之间的距离  Localdate之间的距离以Period表示
		Period period = future.until(today);
		//获得日期之间的距离  ChronoUnit的枚举值为计时单位
		long days = today.until(future, ChronoUnit.DAYS);

		//描述部分日期的类
		Year year = Year.now();
		MonthDay monthDay = MonthDay.now();
		YearMonth yearMonth = YearMonth.now();
	}


}
