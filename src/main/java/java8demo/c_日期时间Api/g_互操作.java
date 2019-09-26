package java8demo.c_日期时间Api;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.*;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author 271636872@qq.com
 * @since 19/9/26 23:37
 */
@SuppressWarnings("ALL")
public class g_互操作 {
	@Test
	public void convert() {

//		Instant与java.util.Date
		java.util.Date date = Date.from(Instant.now());
		Instant instant = date.toInstant();

//		GregorianCalendar与ZonedDateTime
		
		GregorianCalendar gregorianCalendar = GregorianCalendar.from(ZonedDateTime.now());
		
		ZonedDateTime zonedDateTime = gregorianCalendar.toZonedDateTime();

//		Instant与TimeStamp
		Timestamp timestamp = Timestamp.from(Instant.now());
		Instant toInstant = timestamp.toInstant();

//		Timestamp与LocalDateTime
		Timestamp valueOf = Timestamp.valueOf(LocalDateTime.now());
		LocalDateTime localDateTime = valueOf.toLocalDateTime();

//		 java.sql.Date与LocalDate
		java.sql.Date sqlDate = java.sql.Date.valueOf(LocalDate.now());
		LocalDate localDate = sqlDate.toLocalDate();

//		java.sql.Time与LocalTime
		java.sql.Time time = java.sql.Time.valueOf(LocalTime.now());
		LocalTime localTime = time.toLocalTime();

//		java.util.TimeZone 与ZoneId
		java.util.TimeZone timeZone = java.util.TimeZone.getTimeZone(ZoneId.systemDefault());
		ZoneId zoneId = timeZone.toZoneId();
	}
	
}
