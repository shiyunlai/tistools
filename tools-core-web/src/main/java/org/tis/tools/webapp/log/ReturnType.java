package org.tis.tools.webapp.log;

/**
 * 操作日志记录的操作对象类型枚举类
 * @see OperateLog 记录操作日志的注解类
 *
 * 该枚举类用于定义需要记录的操作对象类型，操作对象通过 OperateLog注解方法的返回值解析数据
 *
 * Object 表示返回数据类型为对象
 * List 表示返回数据类型为集合
 * Map 和 Object原理相同，标准的key-value类型
 * Custom 表示返回对象为自定义，一般用于在方法内部个性化处理操作日志对象，不经过统一方法拦截处理
 * //TODO 未完，待补充
 */
public enum ReturnType {
    Object,
    List,
    Map,
    Custom
}
