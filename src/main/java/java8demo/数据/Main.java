package java8demo.数据;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    
    public static void main(String[] args) throws Exception {
        File file = new File("G:\\repos\\java8demo\\src\\main\\resources\\json.json");
        String s = FileUtils.readFileToString(file);
        JSONObject jsonObject = JSON.parseObject(s);
        List<Map> eval =(List<Map>) JSONPath.eval(jsonObject , "$.store.book");
        eval.forEach(System.out::println);
    }
    
}
