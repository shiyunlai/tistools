/**
 * 
 */
package org.tis.tools.maven.plugin.gendao.ermaster;

import java.net.URL;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tis.tools.maven.plugin.gendao.BizModel;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.ERMasterModel;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.ModelPropertyEnum;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.NodeElement;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.NormalColumn;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.Table;
import org.tis.tools.maven.plugin.gendao.ermaster.dom4j.Word;

import junit.framework.Assert;

/**
 * @author megapro
 *
 */
public class ERMasterModelByDom4jTest {

	public static ERMasterModel ermm ;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		/*
		 * 提前加载好ERMaster文件定义
		 */
		String testFile = "/META-INF/model-ermaster.erm" ;
		URL url = ERMasterModelByDom4jTest.class.getResource(testFile);
		if( url == null ){
			System.out.println("缺少测试数据文件："+ testFile);
		}
		
		String modelXml = url.getPath() ; 
		System.out.println("测试用的ERMaster模型文件为："+modelXml);
		
		ermm = new ERMasterModel(modelXml) ; 
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * 检查 ERMasterModel 对象
	 */
	@Test
	public void test() {
		
//		String testFile = "/META-INF/model-ermaster.xml" ;
//		URL url = this.getClass().getResource(testFile);
//		if( url == null ){
//			System.out.println("缺少测试数据文件："+ testFile);
//		}
//		
//		String modelXml = url.getPath() ; 
//		System.out.println("测试用的ERMaster模型文件为："+modelXml);
//		
//		ERMasterModel ermModel = new ERMasterModel(modelXml) ; 

		Assert.assertEquals("MySQL", ermm.getSettings().getDataBase());
		
		Assert.assertEquals(107, ermm.getWords().size());
		Word w65 = ermm.getWordById("65") ; 
		Assert.assertEquals("服务类型", w65.getLogicalName());
		Assert.assertEquals("SERVICE_TYPE", w65.getPhysicalName());
		Assert.assertEquals("varchar(n)", w65.getType());
		Assert.assertEquals("16", w65.getLength());
		Assert.assertEquals("null", w65.getDecimal());
		//Assert.assertEquals("银行所提供的客户服务类型&#x0A;见义务字典： DICT_SERVICE_TYPE", w65.getDescription());
		
		Assert.assertEquals(107, ermm.getWords().size());
		Word w76 = ermm.getWordById("76") ; 
		Assert.assertEquals("测试DECIMALPS", w76.getLogicalName());
		Assert.assertEquals("TEST_DECIMALPS", w76.getPhysicalName());
		Assert.assertEquals("decimal(p,s)", w76.getType());
		Assert.assertEquals("12", w76.getLength());
		Assert.assertEquals("4", w76.getDecimal());
		
		
		Assert.assertEquals(3, ermm.getSettings().getCategories().size());
		Assert.assertNotNull(ermm.getCategoryByName("SYS"));//取名字为SYS的模型分类
		Assert.assertEquals("17",ermm.getCategoryByName("JNL").getId());
		List<NodeElement> jnlNes = ermm.getCategoryByName("JNL").getNodeElement() ; 
		Assert.assertEquals(12,jnlNes.size());//JNL分类中有12张表
		
		Assert.assertEquals(9, ermm.getSettings().getModelProperties().size());
		Assert.assertEquals("open branch plus", ermm.getModelPropertyValue(ModelPropertyEnum.MP_PROJECT_NAME));
		Assert.assertEquals("Shiyl", ermm.getModelPropertyValue(ModelPropertyEnum.MP_AUTHOR));
		Assert.assertEquals("tools-core", ermm.getModelPropertyValue(ModelPropertyEnum.MP_PRJ_CODE));
		Assert.assertEquals("org.tis.ermaster", ermm.getSettings().getPackageName());
		Assert.assertEquals("UTF-8", ermm.getSettings().getSrcFileEncoding());
		
		Assert.assertEquals(16, ermm.getTables().size());
		Table t11 = ermm.getTableById("11") ;
		Assert.assertNotNull(t11);
		Assert.assertEquals("营销流水", t11.getLogicalName());
		Assert.assertEquals("JNL_PROMOTING", t11.getPhysicalName());
		Assert.assertEquals("", t11.getDescription());
		Assert.assertEquals(13, t11.getNormalColumns().size());
		
		NormalColumn n118 = t11.getNormalColumnById("118") ;//取id为118的表字典定义
		Assert.assertNotNull(n118);//118这个表字段必须存在
		Assert.assertEquals("51", n118.getWordId());//该字典对应的模型字典id为51
		Assert.assertNotNull(ermm.getWordById(n118.getWordId()));//根据自动定义取关联的模型字典，必须存在
		
		Table t15 = ermm.getTableById("15") ;
		Assert.assertNotNull(t15);
		
		NormalColumn n148 = t15.getNormalColumnById("148") ;//取id为148的表字段定义
		Assert.assertEquals("12,4", n148.getLength());// 该字段的类型为 decimal(p,s) 且 p = 12 , s = 4
		
	}
	
	/**
	 * 检测ERMasterModel适配为BizModel的情况
	 */
	@Test
	public void test2(){
		
		ERMasterDefinition ermDef = new ERMasterDefinition(ermm) ;
		List<BizModel> bizModels = ermDef.getBizModels() ; 
		
		Assert.assertNotNull(bizModels);
		Assert.assertEquals(3,bizModels.size());
		for(BizModel bm : bizModels){
			System.out.println("\n ====================================== \n");
			System.out.println(bm);
		}
	}

}
