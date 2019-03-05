package cn.com.eship;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;

/**
 * Created by Kid
 * rq  2018/12/11
 */
public class BeanUtilsss {
    public static void copyFields(Map<String,String> org,Object dest) throws Exception{
        BeanInfo beanInfo = Introspector.getBeanInfo(dest.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (Map.Entry<String, String> entry : org.entrySet()) {
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors){
                if (entry.getKey().equals(propertyDescriptor.getName())){
                    propertyDescriptor.getWriteMethod().setAccessible(true);
                    propertyDescriptor.getWriteMethod().invoke(dest,entry.getValue());
                    break;
                }
            }
        }
    }
}
