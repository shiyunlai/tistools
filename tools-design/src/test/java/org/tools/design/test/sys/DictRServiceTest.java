/**
 *
 */
package org.tools.design.test.sys;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.SequenceSimpleUtil;
import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.om.capable.IOrgRService;
import org.tis.tools.rservice.sys.basic.ISysDictItemRService;
import org.tis.tools.rservice.sys.basic.ISysDictRService;
import org.tools.design.SpringJunitSupport;

import junit.framework.Assert;

/**
 *
 * 单元测试：测试SYS中业务字典管理服务功能
 *
 * @author megapro
 *
 */
public class DictRServiceTest extends SpringJunitSupport{

    @Autowired
    IOrgRService orgRService;

    @Autowired
    ISysDictRService sysDictRService ;

    @Autowired
    ISysDictItemRService sysDictItemRService ;



    /*
     * 测试数据: 生成机构代码所需的数据
     */
    private static String orgDegree = "0"; //机构等级
    private static String areaCode = "" ; //区域代码
    private static String orgType = "" ; //机构类型

    // 对表 SYS_DICT 清理的条件
    private WhereCondition wcSysDict = new WhereCondition() ;
    // 对表 SYS_DICT_ITEM 清理的条件
    private WhereCondition wcSysDictItem = new WhereCondition() ;

    @Before
    public void before(){
        //增加业务字典:机构等级
    }

    @After
    public void after(){
        sysDictRService.deleteByCondition(wcSysDict);
        sysDictItemRService.deleteByCondition(wcSysDictItem);
    }

    /**
     * <pre>
     * 案例1:生成机构代码成功
     * 判断：机构代码满足既定规则
     * 机构代码规则：
     * 1.共10位；
     * 2.组成结构： 机构等级(两位) + 地区码(三位) + 序号(五位)
     * </pre>
     */
    @Test
    public void genOrgCodeSucc() {

        //调用生成机构代码
        String orgCodeStr = orgRService.genOrgCode(areaCode, orgDegree, orgType) ;

        Assert.assertNotNull("成功生成机构代码不能为空", orgCodeStr);
        Assert.assertEquals("机构代码共10位",10, orgCodeStr.length());
        Assert.assertEquals("前两位是机构等级",orgCodeStr.substring(0, 2), orgDegree);
        Assert.assertEquals("三四五位是地区码",orgCodeStr.substring(3, 5), areaCode);

    }

    /**
     * 案例2:生成机构代码失败，缺少所需的业务字典
     */
    @Test
    public void genFailureCase() {

    }





}
