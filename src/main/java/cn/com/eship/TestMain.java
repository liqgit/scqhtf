package cn.com.eship;

import java.util.HashMap;
import java.util.Map;

public class TestMain {
	public static void main(String[] args) throws Exception {

		Object obj = Class.forName("cn.com.eship.Student").newInstance();

		Map<String,String> map = new HashMap<>();
		map.put("name","李乾");
		map.put("age","1");
		map.put("desc","摔凳为王");

		BeanUtilsss.copyFields(map,obj);
		System.out.println(obj);
		System.out.println("~~~~~");
	}

}
