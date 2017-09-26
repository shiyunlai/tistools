package org.tis.tools.webapp.log;

import java.lang.annotation.*;

/**
 * 是否添操作日志注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {


    /**
     * 增:add,删:delete,改:update,查:query(默认为查)
     *
     * @return
     */
    String operateType() default "query";

    /**
     * 记录操作描述
     *
     *
     * @return
     */
    String operateDesc() default "";


    /**
     * 返回类型  实体对象 Map List
     * @return
     */
    ReturnType retType() default ReturnType.Object;

    /**
     * 记录需要提取的key值
     *
     * @return
     */
    String[] keys() default {};

    /**
     * 操作对象的身份标识
     * @return
     */
    String id() default "";

    /**
     * 操作对象的名称
     * @return
     */
    String name() default "";



}
