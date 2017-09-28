package org.tools.design.test.ac;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcConfig;
import org.tis.tools.rservice.ac.capable.IOperatorRService;
import org.tools.design.SpringJunitSupport;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 单元测试：测试AC操作员个性配置（AcConfig）概念对象的管理服务功能
 *
 * @author zhaoch
 *
 */
public class AcOperatorConfigRServiceTest  extends SpringJunitSupport {
    @Autowired
    IOperatorRService operatorRService;
    /*
     * 测试数据: 生成所需的数据
     */

    /** 字段类型：varchar<br/>字段名：数据主键<br/>描述： */
    private String guid ;

    /** 字段类型：varchar<br/>字段名：操作员GUID<br/>描述： */
    private String guidOperator = "111" ;

    /** 字段类型：varchar<br/>字段名：应用GUID<br/>描述： */
    private String guidApp = "APP1499956132";

    /** 字段类型：varchar<br/>字段名：配置类型<br/>描述：见业务字典： DICT_AC_CONFIGTYPE */
    private String configType = "style";

    /** 字段类型：varchar<br/>字段名：配置名<br/>描述： */
    private String configName = "个性化配置测试";

    /** 字段类型：varchar<br/>字段名：配置值字典<br/>描述： */
    private String configDict = "DICT_AC_MENUTYPE";

    /** 字段类型：varchar<br/>字段名：配置值<br/>描述： */
    private String configValue = "红" ;

    /** 字段类型：varchar<br/>字段名：是否启用<br/>描述： */
    private String enabled = "Y" ;

    /** 字段类型：varchar<br/>字段名：配置描述说明<br/>描述： */
    private String configDesc = "配置菜单风格" ;

    private String configStyle = "radio" ;

    private AcConfig acOperatorConfig;

	@Before
	public void before(){
        try {
            AcConfig cfg = new AcConfig();
            cfg.setConfigName(configName);
            cfg.setConfigType(configType);
            cfg.setGuidApp(guidApp);
            cfg.setConfigDict(configDict);
            cfg.setConfigValue(configValue);
            cfg.setEnabled(enabled);
//            cfg.setConfigType(configDesc);
            cfg.setConfigStyle(configStyle);
            acOperatorConfig = operatorRService.addConfig(cfg);
            System.out.println(acOperatorConfig);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

	@After
    public void after(){
	    List<AcConfig> list = new ArrayList<AcConfig>();
	    list.add(acOperatorConfig);
        operatorRService.deleteConfig(list);
    }

    /**
     * <pre>
     * 个性化配置的增删改查测试
     * </pre>
     * @throws ToolsRuntimeException
     */
    @Test
    public void curdOperatorConfigTest() throws ToolsRuntimeException {
        try {
            List<AcConfig> acOperatorConfigs = operatorRService.queryConfigList();
            Assert.assertEquals("个性化配置测试", acOperatorConfigs.get(0).getConfigName());
            acOperatorConfigs.get(0).setConfigName("个性化配置测试1");
            operatorRService.updateConfig(acOperatorConfigs.get(0));
            List<AcConfig> acConfigs1 = operatorRService.queryConfigList();
            Assert.assertEquals("个性化配置测试1", acConfigs1.get(0).getConfigName());
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码：" + e.getCode());
            System.out.println("错误信息：" + e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
