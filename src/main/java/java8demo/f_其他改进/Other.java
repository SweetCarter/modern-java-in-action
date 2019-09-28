package java8demo.f_其他改进;

import java8demo.数据.ComparableBean;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * @author gulh
 * @since 2019/9/28 9:29
 */
@SuppressWarnings("ALL")
public class Other {
	/**
	 * Java 8 为String添加的唯一一个方法
	 */
	@Test
	public void string() {
		String join = String.join(",", "j", "a", "v", "a");
		join = String.join("|", Arrays.asList("j", "a", "v", "a"));
	}

	/**
	 * Comparator 接口新增了许多有用的方法
	 */
	@Test
	public void comparator() {

		ComparableBean bean1 = new ComparableBean(1);
		ComparableBean bean2 = new ComparableBean(2);
		ComparableBean bean3 = new ComparableBean(3);
		final List<ComparableBean> comparableBeans = Arrays.asList(bean3, bean1, bean2);


		Function<ComparableBean, Integer> nameExtractor = ComparableBean::getNumber;
		//获得比较器
		final Comparator<ComparableBean> comparing = Comparator.comparing(nameExtractor);
		//多级比较
		final Comparator<ComparableBean> thenComparing = comparing.thenComparing(nameExtractor);

		//自然排序  泛型由上下文推倒		c1.compareTo(c2)
		final Comparator naturalOrder = Comparator.naturalOrder();

		//颠倒自然排序  泛型由上下文推倒		c2.compareTo(c1)
		final Comparator reverseOrder = Comparator.reverseOrder();

		//颠倒任意比较器
		final Comparator reversed = naturalOrder.reversed();

		//修改比较器
		//遇到null时 将null看作一个大于正常值的值
		final Comparator<ComparableBean> nullsFirst = Comparator.nullsFirst(Comparator.naturalOrder());
		//遇到null时 将null看作一个小于正常值的值
		final Comparator<ComparableBean> nullsLast = Comparator.nullsLast(comparing);


		Collections.sort(comparableBeans, Comparator.naturalOrder());
	}


	/**
	 * Short Integer Long Float Double 五种类型
	 * 新增 sum max min静态方法
	 * <p>
	 * 静态方法 toUnsignedXXX
	 * 静态方法 XXXtoUnsigned
	 * 支持无符号数学计算
	 * <p>
	 * Boolean中提供了logicalOr logicalAnd logicalXor
	 */
	@Test
	public void number() {
		final int sum = Integer.sum(1, 2);
		final long l = Integer.toUnsignedLong(-132);

	}

	/**
	 * 使用{@link java.nio.file.Path} 替代{@link java.io.File}
	 * <p>
	 * {@link java.nio.file.Files} 中提供了创建文件/目录  复制 移动 删除文件的静态方法
	 * 默认情况下 所有读写方法都是用UTF-8编码
	 */
	@Test
	public void java7() {
		//路径分隔符
		//类UNIX系统为 /
		//Windows系统为 \

		//绝对路径 WINDOWS
		Path absolute = Paths.get("C:", "Users", "demo.txt");
		//绝对路径 类UNIX
		absolute = Paths.get("C:", "Users", "demo.txt");
		//相对地址
		Path relative = Paths.get("java", "path");

		//组合路径
		Path root = Paths.get("C:\\");
		//   C:\java\A
		Path combinedPath = root.resolve("java\\A");
		//   C:\java\A
		combinedPath = root.resolve(Paths.get("java", "A"));

		//兄弟路径
		//  C:\java\B
		Path sibling = combinedPath.resolveSibling("B");

		//File和Path相互转换
		Path path = Paths.get("C:", "Users", "demo.txt");
		final File file = path.toFile();
		path = file.toPath();

	}

}
