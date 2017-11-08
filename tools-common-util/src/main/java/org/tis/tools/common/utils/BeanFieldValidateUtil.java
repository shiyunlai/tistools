package org.tis.tools.common.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class BeanFieldValidateUtil {

    /**
     * 检查对象中的传入的属性数组属性值是否为空
     * <pre>
     *     例： 对象A  属性有 id ， name， memo
     *     传入 new A(1, "A", null), {"id", "name", "memo"}, 返回 ”memo“
     *     传入 new A(1, "A", null), {"id", "name"}, 返回 null
     *     传入 new A(1, "A", ”BBB“), {"id", "name", "memo"}, 返回 null
     *
     * </pre>
     * @param obj 检查的对象
     * @param fields 对象不能为空的属性名数组
     * @return 如果必输字段为空，返回该字段名，否则返回null
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static String checkObjFieldRequired(Object obj, String[] fields) throws NoSuchFieldException, IllegalAccessException {
        for(String field : fields) {
            Field f = obj.getClass().getDeclaredField(field);
            f.setAccessible(true);
            if(f.get(obj) == null) {
                return field;
            }
        }
        return null;
    }

    /**
     * 检查对象中的属性值除传入的属性数组外其他是否为空
     * <pre>
     *     例： 对象A  属性有 id ， name， memo
     *     传入 new A(1, "A", null), {"id", "name", "memo"}, 返回 null
     *     传入 new A(1, "A", null), {"id", "name"}, 返回 null
     *     传入 new A(1, "A", ”BBB“), {"id", "name", "memo"}, 返回 null
     *
     * </pre>
     * @param obj 检查的对象
     * @param fields 对象可为空的属性名数组
     * @return 如果必输字段为空，返回该字段名，否则返回null
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static String checkObjFieldNotRequired(Object obj, String[] fields) throws NoSuchFieldException, IllegalAccessException {
        List<String> list = Arrays.asList(fields);
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if(!list.contains(f.getName()) && f.get(obj) == null){
                return f.getName();
            }
        }
        return null;
    }

    /**
     * 检查对象中的属性值是否全部不为空
     * <pre>
     *     例： 对象A  属性有 id ， name， memo
     *     传入 new A(1, "A", null), 返回 ”memo“
     *     传入 new A(1, null, null) 返回 "name"
     *     传入 new A(1, "A", ”BBB“) 返回 null
     * </pre>
     * @param obj 检查的对象
     * @return 如果必输字段为空，返回该字段名，否则返回null
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static String checkObjFieldAllRequired(Object obj) throws NoSuchFieldException, IllegalAccessException {
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if(f.get(obj) == null){
                return f.getName();
            }
        }
        return null;
    }

    /**
     * 对修改对象的属性值做保护，防止通过其他接口更改
     * 如操作员的密码只能通过 修改密码接口做更改，防止通过修改操作员信息接口改变
     */
    public static <T> T processObjSensitiveFields(T obj, String[] fields) throws NoSuchFieldException, IllegalAccessException {
        for(String field : fields) {
            Field f = obj.getClass().getDeclaredField(field);
            f.setAccessible(true);
            f.set(obj, null);
        }
        return obj;
    }
}
