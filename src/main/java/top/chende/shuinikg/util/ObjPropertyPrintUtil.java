package top.chende.shuinikg.util;

import java.lang.reflect.Field;

/**
 * @author: chende
 * @date: 2019/7/25 10:53
 * @description: 打印对象属性
 */
public class ObjPropertyPrintUtil {
    public static String toString(Object obj, Class<?> clazz) {
        if (obj == null) {
            return "";
        }
        // 根据Class对象获得属性 私有的也可以获得
        Field[] fields = clazz.getDeclaredFields();
        String s = "";
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                // 设置些属性是可以访问的
                Object val = f.get(obj);
                // 得到此属性的值
                String name = f.getName();
                // 得到此属性的名称
                s += name + ":" + val + ",";
            }
        } catch (IllegalAccessException e) {

            System.err.println("获取bean的值出错");
        }
        return s;

    }
}
