package org.tis.tools.common.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class BeanFieldValidateUtil {

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

    public static String checkObjFieldAllRequired(Object obj) throws NoSuchFieldException, IllegalAccessException {
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            if(f.get(obj) == null){
                return f.getName();
            }
        }
        return null;
    }
}
