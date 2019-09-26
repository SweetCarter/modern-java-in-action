package java8demo.c_日期时间Api;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * @author gulh
 * @since 2019/9/26 12:49
 */
@SuppressWarnings("ALL")
public class d_带时区的时间 {
	/**
	 * {@link java.time.LocalDateTime} 表示一个日期和时间
	 * 该类适合存储确定时区中的某个时间 例如 某一次具体的课程或活动安排
	 * 但是如果需要进行夏令时的时间计算,或者需要处理的用户处于不同的时区中
	 * 应该使用{@link java.time.ZonedDateTime}
	 */
	@Test
	public void ZonedDateTime() {
		//每个时区都有对应的一个ID
		final Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();

		//取得zonId
		ZoneId zoneId = ZoneId.of("UTC");
		zoneId = ZoneId.systemDefault();

		final LocalDateTime now = LocalDateTime.now();
		//将 LocalDateTime 转换为 ZonedDateTime
		final ZonedDateTime zonedDateTime = now.atZone(zoneId);

		//		ZonedDateTime to开头的方法可将ZonedDateTime 转为 对应对象
		Instant instant = zonedDateTime.toInstant();

		//将 Instant 对象转为指定时区的ZonedDateTime 对象
		final ZonedDateTime utc = instant.atZone(ZoneId.of("UTC"));

		//java8中 提供了一个 		OffsetDateTime 类 ,用来表示带有偏移量(根据UTC计算)的时间,但是没有时区规则
		//可用来处理 某些网络协议等
	}

}
