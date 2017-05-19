package org.tis.tools.maven.plugin.utils;

import junit.framework.Assert;

import org.junit.Test;
import org.tis.tools.maven.plugin.gendao.BizModel;
import org.tis.tools.maven.plugin.gendao.Field;
import org.tis.tools.maven.plugin.gendao.Model;
import org.tis.tools.maven.plugin.utils.Xml22BeanUtil;
import org.tis.tools.maven.plugin.utils.helper.Bean;

public class Xml22BeanUtilTest {

	@Test
	public void testParseToBean() {
		
		//String fd = Xml22BeanUtilTest.class.getResource("testXmlToBean.xml").getPath() ; //不建议的做法，测试资源testXmlToBean.xm域java放在package路径下
		String testXmlFile = this.getClass().getResource("/META-INF/testXmlToBean.xml").getPath() ; //建议放到 resources/META-INF目录
		Bean t = Xml22BeanUtil.parseToBean(Bean.class,testXmlFile);
		
		Assert.assertEquals("x.m", t.getName());
		Assert.assertEquals("xm1000x", t.getCode());
		Assert.assertEquals(2, t.getLevels().size());
		Assert.assertEquals("none", t.getLevels().get(0).getValue());
		Assert.assertEquals(3, t.getExtras().size());
		Assert.assertEquals("xx.mm.xxextra", t.getExtras().get(1).getContent());
		Assert.assertEquals("我是描述信息1", t.getDescription());
		
	}
	
	@Test
	public void testXml2Bean() {
		
		String testXmlFile = Xml22BeanUtil.class.getClass().getResource("/META-INF/testXmlToBean.xml").getPath() ; 
		Bean t = Xml22BeanUtil.xml2Bean(Bean.class,testXmlFile);
		
		Assert.assertEquals("x.m", t.getName());
		Assert.assertEquals("xm1000x", t.getCode());
		Assert.assertEquals(2, t.getLevels().size());
		Assert.assertEquals("none", t.getLevels().get(0).getValue());
		Assert.assertEquals(3, t.getExtras().size());
		Assert.assertEquals("xx.mm.xx", t.getExtras().get(2).getContent());
		Assert.assertEquals("我是描述信息1", t.getDescription());
	}
	
	@Test
	public void testXml2Model() {
		
		String modelXml = this.getClass().getResource("/META-INF/model-test.xml").getPath() ; 
		BizModel bizM = Xml22BeanUtil.xml2Bean(BizModel.class,modelXml);
		
		/*
		 * <bizmodel name="账户" id="acct" mainpackage="com.bronsp" >
	<desc>对账户业务域的模型定义，包括：总账、分户账、账户明细、账户变更明细、开销户登记博</desc>
		 */
		Assert.assertEquals("账户", bizM.getName());
		Assert.assertEquals("acct", bizM.getId());
		Assert.assertEquals("com.bronsp", bizM.getMainpackage());
		Assert.assertEquals("对账户业务域的模型定义，包括：总账、分户账、账户明细、账户变更明细、开销户登记博", bizM.getDesc());
		
		//一共定义了2个model
		Assert.assertEquals(2, bizM.getModels().size());
		
		Model tbGnlModel = null ; 
		Model btfRoleInfo = null ; 
		for( Model m : bizM.getModels() ){
			//因为测试所以写死匹配条件，如果增加测试模型，需要增加对应的变量接收
			if( "tb_gnl".equals(m.getId() )){
				tbGnlModel = m ; 
			}
			if( "tb_gnl".equals(m.getId() )){
				btfRoleInfo = m ; 
			}
		}
		
		//应该获得了 表模型定义
		Assert.assertNotNull(tbGnlModel);
		Assert.assertNotNull(btfRoleInfo);
		
		/*
		 <model name="总账" id="tb_gnl" desc="总账表，记录用户的总账信息" ext="2,1000" >
			<field name="唯一ID"  id="ID" type="string" length="128" key="true"/>
			<field name="试卷编码"  id="paper_code" type="string" length="128" key="true"/>
			<field name="课程编码"  id="course_code" type="string" length="128" />
			<field name="顺序号"  id="seq_no" type="long" length="8" />
			<field name="考卷名称"  id="paper_name" type="string" length="1024" />
			<field name="卷首语"  id="paper_desc" type="string" length="40000" />
			<field name="多媒体信息"  id="multi_media_info" type="string" length="1024" />
			<field name="考试时间"  id="time_exam" type="long" length="8" />
			<field name="是否允许重考"  id="is_re_exam" type="string" length="8" />
			<field name="重考次数限制"  id="times_re_exam" type="long" length="8" />
			<field name="默认每题得分"  id="def_per_score" type="long" length="8" />
			<field name="题目排序规则"  id="rule_sort_question" type="string" length="8" />
			<field name="题目答案排序规则"  id="rule_sort_answer" type="string" length="8" />
			<field name="答案反馈方式"  id="feedback_type" type="string" length="8" />
			<field name="预期年化利率"     id="expect_fixed" type="bigdecimal" length="10,6" />
			<field name="不生成PO对象"     id="not_gen_po" type="bigdecimal" length="10,6" physical="false"/>
			<field name="delstatus" id="delstatus" />
			<field name="creatorId" id="creatorId" />
			<field name="creatorName" id="creatorName" />
			<field name="creatorCode" id="creatorCode" />
			<field name="creatorIdPath" id="creatorIdPath" />
			<field name="creatorNamePath" id="creatorNamePath" />
			<field name="creatorCodePath" id="creatorCodePath" />
			<field name="createTime" id="createTime" type="datetime" />
			<field name="modifiedTime" id="modifiedTime" type="datetime" />
		</model>
		 */
		//model对象的属性
		Assert.assertEquals("总账", tbGnlModel.getName());
		Assert.assertEquals("tb_gnl", tbGnlModel.getId());
		Assert.assertEquals("总账表，记录用户的总账信息", tbGnlModel.getDesc());
		Assert.assertEquals("2,1000", tbGnlModel.getExt());
		
		//model对象的节点
		Assert.assertEquals(27, tbGnlModel.getFields().size());//27个字段，25个明确定义，2个ext
		
		Field fId =  null ; 
		Field fexpect_fixed =  null ; 
		Field fnot_gen_po =  null ; 
		Field fdelstatus =  null ; 
		for(Field f : tbGnlModel.getFields() ){
			if( "ID".equalsIgnoreCase(f.getId()) ){
				fId = f ; 
			}
			if( "expect_fixed".equalsIgnoreCase(f.getId()) ){
				fexpect_fixed = f ; 
			}
			if( "not_gen_po".equalsIgnoreCase(f.getId()) ){
				fnot_gen_po = f ; 
			}
			if( "delstatus".equalsIgnoreCase(f.getId()) ){
				fdelstatus = f ; 
			}
		}
		
		Assert.assertEquals("唯一ID", fId.getName());
		Assert.assertEquals("true", fId.getKey());
		
		Assert.assertEquals("预期年化利率", fexpect_fixed.getName());
		Assert.assertEquals("10,6", fexpect_fixed.getLength());
		
		Assert.assertEquals("不生成PO对象", fnot_gen_po.getName());
		Assert.assertEquals("false", fnot_gen_po.getPhysical());
		
		//只配置 id和name的情况
		Assert.assertEquals("delstatus", fdelstatus.getName());
		Assert.assertEquals("string", fdelstatus.getType());
		Assert.assertEquals("128", fdelstatus.getLength());
	}
	
}
