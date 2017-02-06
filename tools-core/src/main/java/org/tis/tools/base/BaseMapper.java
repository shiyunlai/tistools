/**
 * BaseMapper.java
 * Created at 2013年11月22日
 * Created by su.zhang
 * Copyright (C) 2013 ranyunapp.com All rights reserved.
 */
package org.tis.tools.base;

import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: BaseMapper</p>
 * <p>Description: mapper父接口</p>
 * <p>Author: su.zhang</p>
 * <p>Date: 2013年11月22日</p>
 * @param <T>
 */
public interface BaseMapper<T> {

    /**
     * <p>Description: 添加记录</p>
     * @param model BaseModel子类
     */
    public void insert(T t);
    
    /**
     * <p>Description: 更新记录 , 字段不可以null</p>
     * @param model BaseModel子类
     */
    public void update(T t);
    
    /**
     * <p>Description: 更新记录, 字段可以null</p>
     * @param t BaseModel子类
     */
    public void updateForce(T t);
    
    /**
     * <p>Description: 根据guid删除记录</p>
     * @param guid
     */
    public void delete(String guid);
    
    /**
     * <p>Description: 按条件删除记录</p>
     * @param wc WhereCondition对象
     */
    public void deleteByCondition(WhereCondition wc);
    
    public void updateByCondition(Map map); 
    
    /**
     * <p>Description: 按条件查询记录列表</p>
     * @param wc WhereCondition对象
     * @return
     */
    public List<T> query(WhereCondition wc);
    
    /**
     * <p>Description: 按条件查询记录总数</p>
     * @param wc WhereCondition对象
     * @return
     */
    public int count(WhereCondition wc);
    
    /**
     * <p>Description: 根据guid查询记录</p>
     * @param guid 全局唯一ID
     * @return
     */
    public T loadByGuid(String guid);
}
