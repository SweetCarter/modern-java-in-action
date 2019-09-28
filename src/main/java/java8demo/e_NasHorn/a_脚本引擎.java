package java8demo.e_NasHorn;

import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Rhino是由java编写的JavaScript解释器
 * Nashorn 是 Rhino的下一任继承者
 *
 * @author gulh
 * @since 2019/9/28 8:37
 */
@SuppressWarnings("ALL")
public class a_脚本引擎 {
	/**
	 *通过java 6中引入的脚本引擎机制,可以执行任何JVM语言的脚本 包括Nashorn
	 */
	@Test
	public void nashorn() throws ScriptException {
		final ScriptEngineManager engineManager = new ScriptEngineManager();
		final ScriptEngine nashorn = engineManager.getEngineByName("nashorn");
		final Object eval = nashorn.eval("'hello word'.length");
		System.out.println(eval.getClass());
		System.out.println(eval);
	}

}
