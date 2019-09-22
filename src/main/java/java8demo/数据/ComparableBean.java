package java8demo.数据;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 可比较的对象
 *
 * @author gulh
 */
@Data
@AllArgsConstructor
public class ComparableBean implements Comparable<ComparableBean> {
	private Integer number;

	@Override
	public int compareTo(ComparableBean other) {
		return number - other.number;
	}
}
