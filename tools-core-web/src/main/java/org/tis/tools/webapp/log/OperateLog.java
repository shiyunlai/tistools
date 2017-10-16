package org.tis.tools.webapp.log;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 对数据库的增删改一般要记录操作日志，添加"OperateLog"注解可以持久化保存操作的对象等信息
 *
 * 该注解通过对方法返回值的拦截处理，实现操作记录的获取和存储
 * 所以Controller的方法中要调用 BaseController.getReturnMap("需要返回的数据"){@link org.tis.tools.webapp.controller.BaseController#getReturnMap(Object)}的方法正确返回数据
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {

 /**
     * 操作类型，比如维护类方法有
     * 增:add,删:delete,改:update,查:query(默认为查)
     * 枚举值在JNLConstants中维护
     *
     * @return
     */
    String operateType() default "query";

    /**
     * 记录操作描述
     * 如： 新增菜单、 修改机构、 删除业务字典
     *
     * @return
     */
    String operateDesc() default "";


    /**
     * 返回类型  实体对象 Map List
     *   ReturnType.Object， ReturnType.List
     * 如：新增功能，返回新增功能的对象信息，则 retType 为 ReturnType.Object
     *    删除多个功能，返回删除的功能数组， 则 retType 为 ReturnType.List
     * @see ReturnType
     * @return
     */
    ReturnType retType() default ReturnType.Object;

    /**
     * 记录需要提取的key值集合
     * 获取Controlller返回数据中的指定字段作为需要额外记录的特殊值记录
     * 如：新增功能，返回新增功能的对象信息，需要额外记录功能的编号 funcCode、功能类型 funcType，则keys 为 {"funcCode", "funcType"}
     * @return
     */
    String[] keys() default {};

    /**
     * 操作对象的身份标识
     * 获取Controlller返回数据中的指定字段作为操作对象的身份标识
     * 如：新增功能，返回新增功能的对象信息，功能的身份标识为 guid， 则Id为 “guid”
     * @return
     */
    String id() default "";

    /**
     * 操作对象的名称
     * 获取Controlller返回数据中的指定字段作为操作对象的名称
     * 如：新增功能，返回新增功能的对象信息，功能名称对应的字段为 funcName， 则name 为“funcName”
     * @return
     */
    String name() default "";



}
