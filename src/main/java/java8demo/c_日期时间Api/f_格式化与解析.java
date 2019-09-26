package java8demo.c_日期时间Api;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author 271636872@qq.com
 * @since 19/9/26 22:52
 */
@SuppressWarnings("ALL")
public class f_格式化与解析 {
	/**
	 *
	 * 格式化和解析
	 * {@link java.time.format.DateTimeFormatter}  用于替换{@link java.text.DateFormat}
	 * DateTimeFormatter 的实例方法 toFormat可转为 DateFormat
	 * 提供三种格式化方法
	 * 预定义的标准格式
	 * 语言环境相关的格式
	 * 自定义的格式
	 */
	@Test
	public void format() {
		//预定义
		String yyyyMMdd = DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDate.now());


		//语言环境相关的四种格式
		//									日期													时间
		//SHORT   				7/16/69											9:32 AM
		//LONG 					July  16,	1969								9:32:00 AM	EDT
		//MEDIUM				Jul 16, 1969										9:32:00 AM
		//FULL 					Wednesday,July 16,1969				9:32:00 AM	EDT
		//通过静态方法获得
		DateTimeFormatter shortFmt = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
		DateTimeFormatter mediumFmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
		DateTimeFormatter longFmt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.MEDIUM);
		DateTimeFormatter fullFmt = DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL);

//		修改以上方法的默认语言环境
		DateTimeFormatter withLocale = fullFmt.withLocale(Locale.CHINESE);


		//自定义日期格式
		DateTimeFormatter ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate parse = LocalDate.parse("2019-12-06", ofPattern);
		parse = LocalDate.parse("2019-12-06");

	}

}
