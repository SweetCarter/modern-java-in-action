package java8demo.c_日期时间Api;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

/**
 * @author gulh
 * @since 2019/9/23 23:01
 */
@SuppressWarnings("ALL")
public class c_日期校正器 {
	/**
	 * 当需要获得 <p> 每月的第一个周二</p> 这种日期时
	 * 可以用到 {@link TemporalAdjusters} 提供的静态方法
	 */
	@Test
	public void adjuster() {
		//下一个周一
		TemporalAdjuster nextMonday = TemporalAdjusters.next(DayOfWeek.MONDAY);
		//下一个周一  或今天 (如果今天是周一)
		TemporalAdjuster nextMondayOrToday = TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY);
		//上一个周一
		TemporalAdjuster previousMonday = TemporalAdjusters.previous(DayOfWeek.MONDAY);
		//上一个周一  或今天 (如果今天是周一)
		TemporalAdjuster previousMondayOrToday = TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY);

		//某月的第2个周一
		TemporalAdjuster dayOfWeekInMonth = TemporalAdjusters.dayOfWeekInMonth(2, DayOfWeek.MONDAY);

		//某月的最后一个周一
		TemporalAdjuster lastInMonth = TemporalAdjusters.lastInMonth(DayOfWeek.MONDAY);

		//自定义日期校正器
		//下一个工作日
		final TemporalAdjuster nextWorkDay = TemporalAdjusters.ofDateAdjuster(source -> {
			LocalDate date = source;
			do {
				date = date.plusDays(1);
			}
			while (date.getDayOfWeek().getValue() > 5);
			return date;
		});

		//校正器的使用
		LocalDate date = LocalDate.now().with(nextWorkDay);
	}
}
