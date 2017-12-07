/**
 * 
 */
package org.tools.design.test.ac;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcBhvDef;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.rservice.ac.capable.IApplicationRService;
import org.tools.design.SpringJunitSupport;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 
 * 单元测试：测试AC权限管理（Appliction）概念对象的管理服务功能
 * 
 * @author megapro
 * 
 */
public class AppRServiceTest extends SpringJunitSupport{
	
	@Autowired
	IApplicationRService applicationRService;


	/*
	 * 测试数据: 生成应用代码所需的数据
	 */
	private static String appCode = "APP0007"; //应用代码
	private static String appName = "应用框架模型" ; //应用名称
	private static String appType = "local" ; //应用类型
	private static String appDesc = "zzc" ; //描述
	private static String isopen = "Y" ; //是否开通
	private static Date openDate = new Date("2017/06/13") ; //开通时间
	private static String url = "http://www.baidu.com/appserver" ; //地址
	private static String ipAddr = "127.0.0.1" ; //IP地址
	private static String ipPort = "8083" ; //IP端口
	
	
	/*@Before
	public void before(){
		//增加应用数据
	
	}
	
	@After
    public void after(){
//		sysDictRService.delete(null);
		
    }*/
	
	/**
	 * <pre>
	 * 案例1:生成应用代码成功
	 * 判断：应用代码满足既定规则
	 * 应用代码规则：
	 * 1.共7位；
	 * 2.组成结构：  应用类型(三位) + 序号(四位)
	 * </pre>
	 * @throws ParseException 
	 */
	@Test
	public void genAppCodeSucc() throws ToolsRuntimeException {

		try {
			String guid = "APP1499074217";
			AcApp ac = applicationRService.queryAcApp(guid);
			System.out.println(ac);
		} catch (ToolsRuntimeException e) {
			System.out.println("错误码：" + e.getCode());
			System.out.println("错误信息：" + e.getMessage());
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	@Test
	public void queryFuncListInAppTest() throws ToolsRuntimeException{

		try {
			String appGuid = "APP1499956132";
			List<AcFunc> acFuncs = applicationRService.queryFuncListInApp(appGuid);
			System.out.println(acFuncs);
		} catch (ToolsRuntimeException e) {
			System.out.println("错误码："+e.getCode());
			System.out.println("错误信息："+e.getMessage());
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	@Test
	public void genBhv() {
		String json = "{\n" +
				"  \"BHVTYPEDEF1510538184\" : { \n" +
				"    \"busitree\": {\"ctrl\": \"om/busiorg\", \"func\": \"busitree\",\"emo\":\"展示业务机构树\"},\n" +
				"    \"searchtree\": {\"ctrl\": \"om/busiorg\", \"func\": \"searchtree\",\"emo\":\"展示业务机构筛选树\"},\n" +
				"    \"busidomain\": {\"ctrl\": \"om/busiorg\", \"func\": \"busidomain\",\"emo\":\"拉取业务套别\"},\n" +
				"    \"initCode\": {\"ctrl\": \"om/busiorg\", \"func\": \"initCode\",\"emo\":\"生成业务套别代码,没用到\"},\n" +
				"    \"addbusiorg\": {\"ctrl\": \"om/busiorg\", \"func\": \"addbusiorg\",\"emo\":\"新增业务机构\"},\n" +
				"    \"loadbusiorgbyType\": {\"ctrl\": \"om/busiorg\", \"func\": \"loadbusiorgbyType\",\"emo\":\"生成下级业务机构列表\"},\n" +
				"    \"delete\": {\"ctrl\": \"om/busiorg\", \"func\": \"delete\",\"emo\":\"删除业务机构\"},\n" +
				"    \"deletebusiorg\": {\"ctrl\": \"om/busiorg\", \"func\": \"deletebusiorg\",\"emo\":\"删除当前节点和所有子节点\"},\n" +
				"    \"updatebusiorg\": {\"ctrl\": \"om/busiorg\", \"func\": \"updatebusiorg\",\"emo\":\"更新业务机构\"},\n" +
				"    \"queryBusiorgByGuid\": {\"ctrl\": \"om/busiorg\", \"func\": \"queryBusiorgByGuid\",\"emo\":\"根据GUID查询业务机构信息\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538185\" : { \n" +
				"    \"createSysDict\": {\"ctrl\": \"DictController\", \"func\": \"createSysDict\",\"emo\":\"新增业务字典\"},\n" +
				"    \"deleteSysDict\": {\"ctrl\": \"DictController\", \"func\": \"deleteSysDict\",\"emo\":\"删除业务字典\"},\n" +
				"    \"editSysDict\": {\"ctrl\": \"DictController\", \"func\": \"editSysDict\",\"emo\":\"修改业务字典\"},\n" +
				"    \"querySysDict\": {\"ctrl\": \"DictController\", \"func\": \"querySysDict\",\"emo\":\"查询单个业务字典\"},\n" +
				"    \"createSysDictItem\": {\"ctrl\": \"DictController\", \"func\": \"createSysDictItem\",\"emo\":\"新增业务字典项\"},\n" +
				"    \"deleteSysDictItem\": {\"ctrl\": \"DictController\", \"func\": \"deleteSysDictItem\",\"emo\":\"删除业务字典项\"},\n" +
				"    \"editSysDictItem\": {\"ctrl\": \"DictController\", \"func\": \"editSysDictItem\",\"emo\":\"修改业务字典项\"},\n" +
				"    \"querySysDictItem\": {\"ctrl\": \"DictController\", \"func\": \"querySysDictItem\",\"emo\":\"查询单个业务字典项\"},\n" +
				"    \"querySysDictList\": {\"ctrl\": \"DictController\", \"func\": \"querySysDictList\",\"emo\":\"查询所有业务字典\"},\n" +
				"    \"querySysDictItemList\": {\"ctrl\": \"DictController\", \"func\": \"querySysDictItemList\",\"emo\":\"查询业务字典项列表\"},\n" +
				"    \"queryAllDictItem\": {\"ctrl\": \"DictController\", \"func\": \"queryAllDictItem\",\"emo\":\"查询所有字典项\"},\n" +
				"    \"queryDictItemListByDictKey\": {\"ctrl\": \"DictController\", \"func\": \"queryDictItemListByDictKey\",\"emo\":\"根据key查询业务字典项列表\"},\n" +
				"    \"exportDictExcel\": {\"ctrl\": \"DictController\", \"func\": \"exportDictExcel\",\"emo\":\" 导出所有业务字典为excel\"},\n" +
				"    \"queryDictTree\": {\"ctrl\": \"DictController\", \"func\": \"queryDictTree\",\"emo\":\" 查询业务字典对应树结构\"},\n" +
				"    \"queryDict\": {\"ctrl\": \"DictController\", \"func\": \"queryDict\",\"emo\":\" 根据key查询业务字典信息\"},\n" +
				"    \"setDefaultDictValue\": {\"ctrl\": \"DictController\", \"func\": \"setDefaultDictValue\",\"emo\":\" 修改业务字典项默认值\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538186\" : {  \n" +
				"    \"dutytree\": {\"ctrl\": \"om/duty\", \"func\": \"dutytree\",\"emo\":\"查询职务树\"},\n" +
				"    \"loadallduty\": {\"ctrl\": \"om/duty\", \"func\": \"loadallduty\",\"emo\":\"生成职务列表\"},\n" +
				"    \"addduty\": {\"ctrl\": \"om/duty\", \"func\": \"addduty\",\"emo\":\"新增职务\"},\n" +
				"    \"initdutyCode\": {\"ctrl\": \"om/duty\", \"func\": \"initdutyCode\",\"emo\":\"生成职务代码\"},\n" +
				"    \"querydutybyType\": {\"ctrl\": \"om/duty\", \"func\": \"querydutybyType\",\"emo\":\" 通过职务套别查询职务\"},\n" +
				"    \"querychild\": {\"ctrl\": \"om/duty\", \"func\": \"querychild\",\"emo\":\" 查询下级职务\"},\n" +
				"    \"queryempbudutyCode\": {\"ctrl\": \"om/duty\", \"func\": \"queryempbudutyCode\",\"emo\":\" 查询职务下人员,用过岗位查询\"},\n" +
				"    \"deletedutyByCode\": {\"ctrl\": \"om/duty\", \"func\": \"deletedutyByCode\",\"emo\":\" 删除职务\"},\n" +
				"    \"updateDuty\": {\"ctrl\": \"om/duty\", \"func\": \"updateDuty\",\"emo\":\"更新职务\"},\n" +
				"    \"queryDucyList\": {\"ctrl\": \"om/duty\", \"func\": \"queryDutyList\",\"emo\":\"查询所有职务\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538187\" : { \n" +
				"    \"queryemployee\": {\"ctrl\": \"om/emp\", \"func\": \"queryemployee\",\"emo\":\"查询所有人员信息\"},\n" +
				"    \"addemployee\": {\"ctrl\": \"om/emp\", \"func\": \"addemployee\",\"emo\":\"新增人员信息\"},\n" +
				"    \"updateemployee\": {\"ctrl\": \"om/emp\", \"func\": \"updateemployee\",\"emo\":\"更新人员信息\"},\n" +
				"    \"deletemp\": {\"ctrl\": \"om/emp\", \"func\": \"deletemp\",\"emo\":\"删除人员信息\"},\n" +
				"    \"loadEmpOrg\": {\"ctrl\": \"om/emp\", \"func\": \"loadEmpOrg\",\"emo\":\"拉取人员-机构表\"},\n" +
				"    \"loadEmpPos\": {\"ctrl\": \"om/emp\", \"func\": \"loadEmpPos\",\"emo\":\"拉取人员-岗位表\"},\n" +
				"    \"loadOrgNotInbyEmp\": {\"ctrl\": \"om/emp\", \"func\": \"loadOrgNotInbyEmp\",\"emo\":\"拉取可指派机构列表\"},\n" +
				"    \"loadPosNotInbyEmp\": {\"ctrl\": \"om/emp\", \"func\": \"loadPosNotInbyEmp\",\"emo\":\"拉取可指派岗位列表\"},\n" +
				"    \"assignOrg\": {\"ctrl\": \"om/emp\", \"func\": \"assignOrg\",\"emo\":\"指派机构\"},\n" +
				"    \"disassignOrg\": {\"ctrl\": \"om/emp\", \"func\": \"disassignOrg\",\"emo\":\"取消指派机构\"},\n" +
				"    \"assignPos\": {\"ctrl\": \"om/emp\", \"func\": \"assignPos\",\"emo\":\"指派岗位\"},\n" +
				"    \"disassignPos\": {\"ctrl\": \"om/emp\", \"func\": \"disassignPos\",\"emo\":\"取消指派岗位\"},\n" +
				"    \"initEmpCode\": {\"ctrl\": \"om/emp\", \"func\": \"initEmpCode\",\"emo\":\"生成员工代码\"},\n" +
				"    \"fixmainOrg\": {\"ctrl\": \"om/emp\", \"func\": \"fixmainOrg\",\"emo\":\"指定新的主机构\"},\n" +
				"    \"fixmainPos\": {\"ctrl\": \"om/emp\", \"func\": \"fixmainPos\",\"emo\":\"指定新的主岗位\"},\n" +
				"    \"changeEmpStatus\": {\"ctrl\": \"om/emp\", \"func\": \"changeEmpStatus\",\"emo\":\"改变员工状态\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538188\" : { \n" +
				"    \"appAdd\": {\"ctrl\": \"AcAppController\", \"func\": \"appAdd\",\"emo\":\"新增应用服务\"},\n" +
				"    \"appDel\": {\"ctrl\": \"AcAppController\", \"func\": \"appDel\",\"emo\":\"删除应用服务\"},\n" +
				"    \"appQuery\": {\"ctrl\": \"AcAppController\", \"func\": \"appQuery\",\"emo\":\"查询应用服务\"},\n" +
				"    \"appEdit\": {\"ctrl\": \"AcAppController\", \"func\": \"appEdit\",\"emo\":\"修改应用服务\"},\n" +
				"    \"groupAdd\": {\"ctrl\": \"AcAppController\", \"func\": \"groupAdd\",\"emo\":\"新增功能组、子功能组\"},\n" +
				"    \"groupDel\": {\"ctrl\": \"AcAppController\", \"func\": \"groupDel\",\"emo\":\"删除功能组、子功能组\"},\n" +
				"    \"groupQuery\": {\"ctrl\": \"AcAppController\", \"func\": \"groupQuery\",\"emo\":\"查询功能组、子功能组\"},\n" +
				"    \"groupEdit\": {\"ctrl\": \"AcAppController\", \"func\": \"groupEdit\",\"emo\":\"修改功能组、子功能组\"},\n" +
				"    \"acFuncAdd\": {\"ctrl\": \"AcAppController\", \"func\": \"acFuncAdd\",\"emo\":\"新增功能服务\"},\n" +
				"    \"acFuncEdit\": {\"ctrl\": \"AcAppController\", \"func\": \"acFuncEdit\",\"emo\":\"修改功能服务\"},\n" +
				"    \"acFuncResouceQuery\": {\"ctrl\": \"AcAppController\", \"func\": \"acFuncResouceQuery\",\"emo\":\"功能对应资源查询\"},\n" +
				"    \"acFuncResourceEdit\": {\"ctrl\": \"AcAppController\", \"func\": \"acFuncResourceEdit\",\"emo\":\"更新资源\"},\n" +
				"    \"acFuncDel\": {\"ctrl\": \"AcAppController\", \"func\": \"acFuncDel\",\"emo\":\"删除功能服务\"},\n" +
				"    \"functypeAdd\": {\"ctrl\": \"AcAppController\", \"func\": \"functypeAdd\",\"emo\":\"新增行为类型接口\"},\n" +
				"    \"functypequery\": {\"ctrl\": \"AcAppController\", \"func\": \"functypequery\",\"emo\":\"查询功能行为类型\"},\n" +
				"    \"functypeEdit\": {\"ctrl\": \"AcAppController\", \"func\": \"functypeEdit\",\"emo\":\"修改功能行为类型\"},\n" +
				"    \"functypeDel\": {\"ctrl\": \"AcAppController\", \"func\": \"functypeDel\",\"emo\":\"删除功能行为类型\"},\n" +
				"    \"acFuncQuery\": {\"ctrl\": \"AcAppController\", \"func\": \"acFuncQuery\",\"emo\":\"功能查询服务\"},\n" +
				"    \"queryAllFunc\": {\"ctrl\": \"AcAppController\", \"func\": \"queryAllFunc\",\"emo\":\"查询所有功能服务\"},\n" +
				"    \"importFunc\": {\"ctrl\": \"AcAppController\", \"func\": \"importFunc\",\"emo\":\"导入功能服务\"},\n" +
				"    \"funactAdd\": {\"ctrl\": \"AcAppController\", \"func\": \"funactAdd\",\"emo\":\"新增功能行为\"},\n" +
				"    \"funactEdit\": {\"ctrl\": \"AcAppController\", \"func\": \"funactEdit\",\"emo\":\"修改功能行为\"},\n" +
				"    \"funactDel\": {\"ctrl\": \"AcAppController\", \"func\": \"funactDel\",\"emo\":\"删除功能行为\"},\n" +
				"    \"addBhvtypeForFunc\": {\"ctrl\": \"AcAppController\", \"func\": \"addBhvtypeForFunc\",\"emo\":\"功能添加行为类型\"},\n" +
				"    \"addBhvDefForFunc\": {\"ctrl\": \"AcAppController\", \"func\": \"addBhvDefForFunc\",\"emo\":\"功能添加行为定义\"},\n" +
				"    \"queryBhvDefInTypeForFunc\": {\"ctrl\": \"AcAppController\", \"func\": \"queryBhvDefInTypeForFunc\",\"emo\":\"查询功能下某个行为类型的操作行为\"},\n" +
				"    \"queryBhvtypeDefByFunc\": {\"ctrl\": \"AcAppController\", \"func\": \"queryBhvtypeDefByFunc\",\"emo\":\"根据功能的GUID查询行为类型定义\"},\n" +
				"    \"queryBhvDefByBhvType\": {\"ctrl\": \"AcAppController\", \"func\": \"queryBhvDefByBhvType\",\"emo\":\"根据类型的GUID查询行为\"},\n" +
				"    \"queryAllBhvDefForFunc\": {\"ctrl\": \"AcAppController\", \"func\": \"queryAllBhvDefForFunc\",\"emo\":\"查询功能下所有行为类型\"},\n" +
				"    \"delFuncBhvType\": {\"ctrl\": \"AcAppController\", \"func\": \"delFuncBhvType\",\"emo\":\"删除功能对应的行为类型\"},\n" +
				"    \"delFuncBhvDef\": {\"ctrl\": \"AcAppController\", \"func\": \"delFuncBhvDef\",\"emo\":\"删除功能对应的行为定义\"},\n" +
				"    \"enableApp\": {\"ctrl\": \"AcAppController\", \"func\": \"enableApp\",\"emo\":\"开启应用\"},\n" +
				"    \"disableApp\": {\"ctrl\": \"AcAppController\", \"func\": \"disableApp\",\"emo\":\"关闭应用\"},\n" +
				"    \"createAcFuncResource\": {\"ctrl\": \"AcAppController\", \"func\": \"createAcFuncResource\",\"emo\":\"新增功能资源\"},\n" +
				"    \"deleteAcFuncResource\": {\"ctrl\": \"AcAppController\", \"func\": \"deleteAcFuncResource\",\"emo\":\"删除功能资源\"},\n" +
				"    \"updateAcFuncResource\": {\"ctrl\": \"AcAppController\", \"func\": \"updateAcFuncResource\",\"emo\":\"修改功能资源\"},\n" +
				"    \"queryAcFuncResource\": {\"ctrl\": \"AcAppController\", \"func\": \"queryAcFuncResource\",\"emo\":\"查询功能资源\"},\n" +
				"    \"updateFuncBhvType\": {\"ctrl\": \"AcAppController\", \"func\": \"updateFuncBhvType\",\"emo\":\"修改行为类型\"},\n" +
				"    \"setFuncBhvStatus\": {\"ctrl\": \"AcAppController\", \"func\": \"setFuncBhvStatus\",\"emo\":\"设置功能行为是否有效\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538189\" : { \n" +
				"    \"queryAllAcApp\": {\"ctrl\": \"AcMenuController\", \"func\": \"queryAllAcApp\",\"emo\":\"查询所有应用\"},\n" +
				"    \"queryRootMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"queryRootMenu\",\"emo\":\"查询应用下的跟菜单\"},\n" +
				"    \"queryRootMenuTree\": {\"ctrl\": \"AcMenuController\", \"func\": \"queryRootMenuTree\",\"emo\":\"查询应用下菜单\"},\n" +
				"    \"queryChildMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"queryChildMenu\",\"emo\":\"查询菜单下子菜单\"},\n" +
				"    \"createRootMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"createRootMenu\",\"emo\":\"新增根菜单\"},\n" +
				"    \"createChildMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"createChildMenu\",\"emo\":\"新增子菜单\"},\n" +
				"    \"deleteMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"deleteMenu\",\"emo\":\"删除菜单\"},\n" +
				"    \"editMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"editMenu\",\"emo\":\"修改菜单方法\"},\n" +
				"    \"moveMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"moveMenu\",\"emo\":\"移动菜单方法\"},\n" +
				"    \"queryAllFuncInApp\": {\"ctrl\": \"AcMenuController\", \"func\": \"queryAllFuncInApp\",\"emo\":\"获取应用下的功能列表\"},\n" +
				"    \"getMenuByUserId\": {\"ctrl\": \"AcMenuController\", \"func\": \"getMenuByUserId\",\"emo\":\"获取操作员拥有的菜单\"},\n" +
				"    \"getOperatorMenuByUserId\": {\"ctrl\": \"AcMenuController\", \"func\": \"getOperatorMenuByUserId\",\"emo\":\"获取操作员的重组菜单\"},\n" +
				"    \"moveOperatorMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"moveOperatorMenu\",\"emo\":\"移动菜单到个人重组菜单\"},\n" +
				"    \"copyMenuToOperatorMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"copyMenuToOperatorMenu\",\"emo\":\"复制菜单到个人重组菜单\"},\n" +
				"    \"createRootOperatorMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"createRootOperatorMenu\",\"emo\":\"新增重组跟菜单\"},\n" +
				"    \"createChildOperatorMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"createChildOperatorMenu\",\"emo\":\"新增重组子菜单\"},\n" +
				"    \"editOperatorMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"editOperatorMenu\",\"emo\":\"修改重组子菜单\"},\n" +
				"    \"deleteOperatorMenu\": {\"ctrl\": \"AcMenuController\", \"func\": \"deleteOperatorMenu\",\"emo\":\"删除重组子菜单\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538190\" : { \n" +
				"    \"querySeqnoList\": {\"ctrl\": \"SeqnoController\", \"func\": \"querySeqnoList\",\"emo\":\"查询序号资源表\"},\n" +
				"    \"editSeqno\": {\"ctrl\": \"SeqnoController\", \"func\": \"editSeqno\",\"emo\":\"修改序号资源表\"},\n" +
				"    \"deleteSeqno\": {\"ctrl\": \"SeqnoController\", \"func\": \"deleteSeqno\",\"emo\":\"删除序号资源表\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538191\" : { \n" +
				"    \"queryAllOperator\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryAllOperator\",\"emo\":\"查询操作员列表\"},\n" +
				"    \"createOperator\": {\"ctrl\": \"AcOperatorController\", \"func\": \"createOperator\",\"emo\":\"新增操作员\"},\n" +
				"    \"editOperator\": {\"ctrl\": \"AcOperatorController\", \"func\": \"editOperator\",\"emo\":\"修改操作员\"},\n" +
				"    \"setDefaultOperatorIdentity\": {\"ctrl\": \"AcOperatorController\", \"func\": \"setDefaultOperatorIdentity\",\"emo\":\"设置操作员默认身份\"},\n" +
				"    \"queryAllOperatorIdentity\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryAllOperatorIdentity\",\"emo\":\"查询操作员身份列表\"},\n" +
				"    \"createOperatorIdentity\": {\"ctrl\": \"AcOperatorController\", \"func\": \"createOperatorIdentity\",\"emo\":\"新增操作员身份\"},\n" +
				"    \"deleteOperatorIdentity\": {\"ctrl\": \"AcOperatorController\", \"func\": \"deleteOperatorIdentity\",\"emo\":\"删除操作员身份\"},\n" +
				"    \"editOperatorIdentity\": {\"ctrl\": \"AcOperatorController\", \"func\": \"editOperatorIdentity\",\"emo\":\"修改操作员身份\"},\n" +
				"    \"queryAllOperatorIdentityRes\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryAllOperatorIdentityRes\",\"emo\":\"查询操作员身份权限集列表\"},\n" +
				"    \"createOperatorIdentityres\": {\"ctrl\": \"AcOperatorController\", \"func\": \"createOperatorIdentityres\",\"emo\":\"新增操作员身份权限\"},\n" +
				"    \"deleteOperatorIdentityres\": {\"ctrl\": \"AcOperatorController\", \"func\": \"deleteOperatorIdentityres\",\"emo\":\"删除操作员身份权限\"},\n" +
				"    \"queryRoleInOperatorByResType\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryRoleInOperatorByResType\",\"emo\":\"根据资源类型查询操作员对应角色\"},\n" +
				"    \"queryOperatorByUserId\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorByUserId\",\"emo\":\"根据USERID查询操作员GUID\"},\n" +
				"    \"queryOperatorUnauthorizedRoleList\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorUnauthorizedRoleList\",\"emo\":\"根据USERID查询操作员未授权角色\"},\n" +
				"    \"queryOperatorAuthorizedRoleList\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorAuthorizedRoleList\",\"emo\":\"根据USERID查询操作员已授权角色\"},\n" +
				"    \"queryOperatorInheritRoleList\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorInheritRoleList\",\"emo\":\"根据USERID查询操作员继承角色\"},\n" +
				"    \"queryOperatorFuncInfoInApp\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorFuncInfoInApp\",\"emo\":\"根据USERID查询特殊权限树\"},\n" +
				"    \"queryAcOperatorFunListByUserId\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryAcOperatorFunListByUserId\",\"emo\":\"查询用户的特殊权限列表\"},\n" +
				"    \"addAcOperatorFun\": {\"ctrl\": \"AcOperatorController\", \"func\": \"addAcOperatorFun\",\"emo\":\"新增用户特殊功能权限\"},\n" +
				"    \"removeAcOperatorFun\": {\"ctrl\": \"AcOperatorController\", \"func\": \"removeAcOperatorFun\",\"emo\":\"移除用户特殊功能权限\"},\n" +
				"    \"queryOperatorAllApp\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorAllApp\",\"emo\":\"查询操作员的所有应用\"},\n" +
				"    \"saveOperatorLog\": {\"ctrl\": \"AcOperatorController\", \"func\": \"saveOperatorLog\",\"emo\":\"修改个人配置\"},\n" +
				"    \"queryOperatorConfig\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorConfig\",\"emo\":\"查询操作员的个性化配置\"},\n" +
				"    \"queryOperatorBhvListInFunc\": {\"ctrl\": \"AcOperatorController\", \"func\": \"queryOperatorBhvListInFunc\",\"emo\":\"查询操作员在某功能的行为白名单和黑名单\"},\n" +
				"    \"addOperatorBhvBlackList\": {\"ctrl\": \"AcOperatorController\", \"func\": \"addOperatorBhvBlackList\",\"emo\":\"操作员功能行为添加黑名单\"},\n" +
				"    \"deleteOperatorBhvBlackList\": {\"ctrl\": \"AcOperatorController\", \"func\": \"deleteOperatorBhvBlackList\",\"emo\":\"移除操作员功能行为黑名单\"},\n" +
				"    \"getOperatorFuncInfo\": {\"ctrl\": \"AcOperatorController\", \"func\": \"getOperatorFuncInfo\",\"emo\":\"根据用户和应用id查询功能树\"},\n" +
				"    \"getOperatorsNotLinkEmp\": {\"ctrl\": \"AcOperatorController\", \"func\": \"getOperatorsNotLinkEmp\",\"emo\":\"获取没有关联员工的操作员\"},\n" +
				"    \"changeOperatorStatus\": {\"ctrl\": \"AcOperatorController\", \"func\": \"changeOperatorStatus\",\"emo\":\" 改变操作员状态\"},\n" +
				"    \"deleteOperator\": {\"ctrl\": \"AcOperatorController\", \"func\": \"deleteOperator\",\"emo\":\" 删除操作员\"},\n" +
				"    \"getOperatorFuncBhvInfo\": {\"ctrl\": \"AcOperatorController\", \"func\": \"getOperatorFuncBhvInfo\",\"emo\":\" 获取操作员功能行为信息\"},\n" +
				"    \"addAcOperatorBhv\": {\"ctrl\": \"AcOperatorController\", \"func\": \"addAcOperatorBhv\",\"emo\":\" 添加操作员特殊功能行为权限\"},\n" +
				"    \"removeAcOperatorBhv\": {\"ctrl\": \"AcOperatorController\", \"func\": \"removeAcOperatorBhv\",\"emo\":\" 移除操作员特殊功能行为权限\"},\n" +
				"    \"deleteConfig\": {\"ctrl\": \"AcOperatorConfig\", \"func\": \"deleteConfig\",\"emo\":\"删除个性化配置\"},\n" +
				"    \"addConfig\": {\"ctrl\": \"AcOperatorConfig\", \"func\": \"addConfig\",\"emo\":\"新增个性化配置\"},\n" +
				"    \"updateConfig\": {\"ctrl\": \"AcOperatorConfig\", \"func\": \"updateConfig\",\"emo\":\"修改操作员个性化配置\"},\n" +
				"    \"queryConfigList\": {\"ctrl\": \"AcOperatorConfig\", \"func\": \"queryConfigList\",\"emo\":\"查询个性化配置列表\"},\n" +
				"    \"getOperatorFuncAuthInfo\": {\"ctrl\": \"AcOperatorController\", \"func\": \"getOperatorFuncAuthInfo\",\"emo\":\"获取操作员功能信息(已授予，未授予)\"},\n" +
				"    \"addAcOperatorFunc\": {\"ctrl\": \"AcOperatorController\", \"func\": \"addAcOperatorFunc\",\"emo\":\"添加操作员特殊功能权限\"},\n" +
				"    \"removeAcOperatorFunc\": {\"ctrl\": \"AcOperatorController\", \"func\": \"removeAcOperatorFunc\",\"emo\":\"移除操作员特殊功能权限\"},\n" +
				"    \"refreshOperator\": {\"ctrl\": \"AcOperatorController\", \"func\": \"refreshOperator\",\"emo\":\"清除单挑操作员缓存\"},\n" +
				"    \"refreshAll\": {\"ctrl\": \"AcOperatorController\", \"func\": \"refreshAll\",\"emo\":\"清除所有操作员缓存\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538192\" : { \n" +
				"    \"loadmaintree\": {\"ctrl\": \"om/org\", \"func\": \"tree\",\"emo\":\" 展示机构树\"},\n" +
				"    \"search\": {\"ctrl\": \"om/org\", \"func\": \"search\",\"emo\":\" 展示筛选机构树\"},\n" +
				"    \"loadgwtree\": {\"ctrl\": \"om/org\", \"func\": \"tree\",\"emo\":\"展示机构树\"},\n" +
				"    \"loadempbyorg\": {\"ctrl\": \"om/org\", \"func\": \"loadempbyorg\",\"emo\":\"拉取员工-机构关系表数据\"},\n" +
				"    \"loadempNotinorg\": {\"ctrl\": \"om/org\", \"func\": \"loadempNotinorg\",\"emo\":\"拉取不在当前机构下的员工列表,用于为该机构新添人员\"},\n" +
				"    \"loadxjjg\": {\"ctrl\": \"om/org\", \"func\": \"loadxjjg\",\"emo\":\"加载下级机构列表数据\"},\n" +
				"    \"addorg\": {\"ctrl\": \"om/org\", \"func\": \"add\",\"emo\":\" 新增机构综合\"},\n" +
				"    \"initcode\": {\"ctrl\": \"om/org\", \"func\": \"initcode\",\"emo\":\"生成默认序号\"},\n" +
				"    \"updateOrg\": {\"ctrl\": \"om/org\", \"func\": \"updateOrg\",\"emo\":\" 更新机构\"},\n" +
				"    \"deleteOrg\": {\"ctrl\": \"om/org\", \"func\": \"deleteOrg\",\"emo\":\" 删除机构\"},\n" +
				"    \"addposit\": {\"ctrl\": \"om/org\", \"func\": \"addposit\",\"emo\":\" 新增岗位信息\"},\n" +
				"    \"addEmpOrg\": {\"ctrl\": \"om/org\", \"func\": \"addEmpOrg\",\"emo\":\"添加机构-人员关系表数据\"},\n" +
				"    \"deleteEmpOrg\": {\"ctrl\": \"om/org\", \"func\": \"deleteEmpOrg\",\"emo\":\"删除机构-人员关系表数据\"},\n" +
				"    \"loadEmpbyPosition\": {\"ctrl\": \"om/org\", \"func\": \"loadEmpbyPosition\",\"emo\":\"加载岗位下员工数据\"},\n" +
				"    \"loadempNotinposit\": {\"ctrl\": \"om/org\", \"func\": \"loadempNotinposit\",\"emo\":\"加载不在岗位下员工数据\"},\n" +
				"    \"addEmpPosition\": {\"ctrl\": \"om/org\", \"func\": \"addEmpPosition\",\"emo\":\"添加岗位-人员关系表数据\"},\n" +
				"    \"deleteEmpPosition\": {\"ctrl\": \"om/org\", \"func\": \"deleteEmpPosition\",\"emo\":\"删除岗位-人员关系表数据\"},\n" +
				"    \"loadxjposit\": {\"ctrl\": \"om/org\", \"func\": \"loadxjposit\",\"emo\":\"加载下级岗位数据\"},\n" +
				"    \"enableorg\": {\"ctrl\": \"om/org\", \"func\": \"enableorg\",\"emo\":\"启用-注销-停用机构\"},\n" +
				"    \"enableposition\": {\"ctrl\": \"om/org\", \"func\": \"enableposition\",\"emo\":\"启用-注销-岗位\"},\n" +
				"    \"deletePosition\": {\"ctrl\": \"om/org\", \"func\": \"deletePosition\",\"emo\":\"删除岗位\"},\n" +
				"    \"queryAllorg\": {\"ctrl\": \"om/org\", \"func\": \"queryAllorg\",\"emo\":\"查询所有机构\"},\n" +
				"    \"queryAllposition\": {\"ctrl\": \"om/org\", \"func\": \"queryAllposition\",\"emo\":\"查询所有岗位\"},\n" +
				"    \"queryRole\": {\"ctrl\": \"om/org\", \"func\": \"queryRole\",\"emo\":\"查询机构已经拥有的角色\"},\n" +
				"    \"addRoleParty\": {\"ctrl\": \"om/org\", \"func\": \"addRoleParty\",\"emo\":\"新增权限\"},\n" +
				"    \"deleteRoleParty\": {\"ctrl\": \"om/org\", \"func\": \"deleteRoleParty\",\"emo\":\"删除权限\"},\n" +
				"    \"queryRoleFun\": {\"ctrl\": \"om/org\", \"func\": \"queryRoleFun\",\"emo\":\"查询角色对应权限\"},\n" +
				"    \"queryRoleNot\": {\"ctrl\": \"om/org\", \"func\": \"queryRoleNot\",\"emo\":\"查询未授予角色\"},\n" +
				"    \"queryAppinPos\": {\"ctrl\": \"om/org\", \"func\": \"queryAppinPos\",\"emo\":\"查询岗位拥有的应用列表\"},\n" +
				"    \"queryAppNotinPos\": {\"ctrl\": \"om/org\", \"func\": \"queryAppNotinPos\",\"emo\":\"查询岗位未拥有的应用列表\"},\n" +
				"    \"addAppPosition\": {\"ctrl\": \"om/org\", \"func\": \"addAppPosition\",\"emo\":\"新增岗位应用\"},\n" +
				"    \"deleteAppPosition\": {\"ctrl\": \"om/org\", \"func\": \"deleteAppPosition\",\"emo\":\"删除岗位应用\"},\n" +
				"    \"queryAllposbyOrg\": {\"ctrl\": \"om/org\", \"func\": \"queryAllposbyOrg\",\"emo\":\"未用到\"},\n" +
				"    \"initPosCode\": {\"ctrl\": \"om/org\", \"func\": \"initPosCode\",\"emo\":\"初始化Code，未用到\"},\n" +
				"    \"copyOrg\": {\"ctrl\": \"om/org\", \"func\": \"copyOrg\",\"emo\":\"拷贝机构\"},\n" +
				"    \"moveOrg\": {\"ctrl\": \"om/org\", \"func\": \"moveOrg\",\"emo\":\"拖动机构\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538193\" : { \n" +
				"    \"queryRoleList\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryRoleList\",\"emo\":\"查询角色列表\"},\n" +
				"    \"createRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"createRole\",\"emo\":\"新增角色\"},\n" +
				"    \"editRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"editRole\",\"emo\":\"修改角色\"},\n" +
				"    \"deleteRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"deleteRole\",\"emo\":\"删除角色\"},\n" +
				"    \"appQuery\": {\"ctrl\": \"AcRoleController\", \"func\": \"appQuery\",\"emo\":\"查询对应的应用功能\"},\n" +
				"    \"configRoleFunc\": {\"ctrl\": \"AcRoleController\", \"func\": \"configRoleFunc\",\"emo\":\"角色配置功能权限\"},\n" +
				"    \"addPartyRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"addPartyRole\",\"emo\":\"角色添加组织权限\"},\n" +
				"    \"queryRoleFunc\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryRoleFunc\",\"emo\":\"查询角色的功能权限\"},\n" +
				"    \"queryRoleInParty\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryRoleInParty\",\"emo\":\"查询角色下某组织类型的权限信息\"},\n" +
				"    \"removePartyRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"removePartyRole\",\"emo\":\"移除角色下的组织对象\"},\n" +
				"    \"addOperatorRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"addOperatorRole\",\"emo\":\"角色添加操作员\"},\n" +
				"    \"queryOperatorRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryOperatorRole\",\"emo\":\"查询角色下的操作员集合\"},\n" +
				"    \"removeOperatorRole\": {\"ctrl\": \"AcRoleController\", \"func\": \"removeOperatorRole\",\"emo\":\"移除角色下的操作员\"},\n" +
				"    \"queryAcRoleBhvsByFuncGuid\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryAcRoleBhvsByFuncGuid\",\"emo\":\"查询角色拥有功能的权限行为\"},\n" +
				"    \"addAcRoleBhvs\": {\"ctrl\": \"AcRoleController\", \"func\": \"addAcRoleBhvs\",\"emo\":\"新增角色功能行为定义\"},\n" +
				"    \"removeAcRoleBhvs\": {\"ctrl\": \"AcRoleController\", \"func\": \"removeAcRoleBhvs\",\"emo\":\"删除角色功能行为定义\"},\n" +
				"\n" +
				"    \"acRoleEntityTree\": {\"ctrl\": \"AcRoleController\", \"func\": \"acRoleEntityTree\",\"emo\":\"角色与实体树查询\"},\n" +
				"    \"addAcRoleEntity\": {\"ctrl\": \"AcRoleController\", \"func\": \"addAcRoleEntity\",\"emo\":\"新增角色与实体关系\"},\n" +
				"    \"deleteAcRoleEntity\": {\"ctrl\": \"AcRoleController\", \"func\": \"deleteAcRoleEntity\",\"emo\":\"删除角色与实体关系\"},\n" +
				"    \"updateAcRoleEntity\": {\"ctrl\": \"AcRoleController\", \"func\": \"updateAcRoleEntity\",\"emo\":\"修改角色与实体关系\"},\n" +
				"    \"addAcRoleEntityfield\": {\"ctrl\": \"AcRoleController\", \"func\": \"addAcRoleEntityfield\",\"emo\":\"新增角色与实体属性关系\"},\n" +
				"    \"deleteAcRoleEntityfield\": {\"ctrl\": \"AcRoleController\", \"func\": \"deleteAcRoleEntityfield\",\"emo\":\"删除角色与实体属性关系\"},\n" +
				"    \"updateAcRoleEntityfield\": {\"ctrl\": \"AcRoleController\", \"func\": \"updateAcRoleEntityfield\",\"emo\":\"修改角色与实体属性关系\"},\n" +
				"    \"getAcRoleEntitityfields\": {\"ctrl\": \"AcRoleController\", \"func\": \"getAcRoleEntitityfields\",\"emo\":\"查询角色实体下的属性\"},\n" +
				"    \"addAcRoleDatascope\": {\"ctrl\": \"AcRoleController\", \"func\": \"addAcRoleDatascope\",\"emo\":\"新增角色与实体数据范围关系\"},\n" +
				"    \"deleteAcRoleDatascope\": {\"ctrl\": \"AcRoleController\", \"func\": \"deleteAcRoleDatascope\",\"emo\":\"新增角色与实体数据范围关系\"},\n" +
				"    \"getAcRoleDatascopes\": {\"ctrl\": \"AcRoleController\", \"func\": \"getAcRoleDatascopes\",\"emo\":\"查询角色实体下的数据范围\"},\n" +
				"    \"queryAcEntityfieldList\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryAcEntityfieldList\",\"emo\":\"查询实体属性列表\"},\n" +
				"    \"queryAcDatascopeList\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryAcDatascopeList\",\"emo\":\"查询数据范围权限列表\"},\n" +
				"    \"queryAcEntityList\": {\"ctrl\": \"AcRoleController\", \"func\": \"queryAcEntityList\",\"emo\":\"查询实体对象列表\"},\n" +
				"    \"configRoleEntity\": {\"ctrl\": \"AcRoleController\", \"func\": \"configRoleEntity\",\"emo\":\"角色配置实体权限\"}\n" +
				"  }, \n" +
				"  \"BHVTYPEDEF1510538194\" : { \n" +
				"    \"queryAcEntityTreeList\": {\"ctrl\": \"entity\", \"func\": \"queryAcEntityTreeList\",\"emo\":\"查询实体对象列表\"},\n" +
				"    \"createAcEntity\": {\"ctrl\": \"entity\", \"func\": \"createAcEntity\",\"emo\":\"新增实体对象\"},\n" +
				"    \"editAcEntity\": {\"ctrl\": \"entity\", \"func\": \"editAcEntity\",\"emo\":\"修改实体对象\"},\n" +
				"    \"deleteAcEntity\": {\"ctrl\": \"entity\", \"func\": \"deleteAcEntity\",\"emo\":\"删除实体对象\"},\n" +
				"    \"queryAcEntityfieldList\": {\"ctrl\": \"entity\", \"func\": \"queryAcEntityfieldList\",\"emo\":\"查询实体属性列表\"},\n" +
				"    \"createAcEntityfield\": {\"ctrl\": \"entity\", \"func\": \"createAcEntityfield\",\"emo\":\"新增实体对象属性\"},\n" +
				"    \"editAcEntityfield\": {\"ctrl\": \"entity\", \"func\": \"editAcEntityfield\",\"emo\":\"修改实体对象\"},\n" +
				"    \"deleteAcEntityfield\": {\"ctrl\": \"entity\", \"func\": \"deleteAcEntityfield\",\"emo\":\"删除实体对象\"},\n" +
				"    \"queryAcDatascopeList\": {\"ctrl\": \"entity\", \"func\": \"queryAcDatascopeList\",\"emo\":\"查询数据范围权限列表\"},\n" +
				"    \"createAcDatascope\": {\"ctrl\": \"entity\", \"func\": \"createAcDatascope\",\"emo\":\"新增实体数据范围权限\"},\n" +
				"    \"editAcDatascope\": {\"ctrl\": \"entity\", \"func\": \"editAcDatascope\",\"emo\":\"修改实体数据范围权限\"},\n" +
				"    \"deleteAcDatescope\": {\"ctrl\": \"entity\", \"func\": \"deleteAcDatescope\",\"emo\":\"删除实体数据范围权限\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538195\" : { \n" +
				"    \"queryRunConfigList\": {\"ctrl\": \"RunConfigController\", \"func\": \"queryRunConfigList\",\"emo\":\"查询系统运行参数列表\"},\n" +
				"    \"createRunConfig\": {\"ctrl\": \"RunConfigController\", \"func\": \"createRunConfig\",\"emo\":\"新增系统参数\"},\n" +
				"    \"editRunConfig\": {\"ctrl\": \"RunConfigController\", \"func\": \"editRunConfig\",\"emo\":\"修改系统参数\"},\n" +
				"    \"deleteRunConfig\": {\"ctrl\": \"RunConfigController\", \"func\": \"deleteRunConfig\",\"emo\":\"删除系统参数\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538196\" : { \n" +
				"    \"loadmaintree\": {\"ctrl\": \"om/workgroup\", \"func\": \"workgrouptree\",\"emo\":\"展示工作组树\"},\n" +
				"    \"searchtree\": {\"ctrl\": \"om/workgroup\", \"func\": \"searchtree\",\"emo\":\"展示筛选树\"},\n" +
				"    \"loadsearchtree\": {\"ctrl\": \"om/org\", \"func\": \"search\",\"emo\":\"!!!!!!!!!!!!!\"},\n" +
				"    \"loadempin\": {\"ctrl\": \"om/workgroup\", \"func\": \"loadempin\",\"emo\":\"生成下级人员列表\"},\n" +
				"    \"loadempNotin\": {\"ctrl\": \"om/workgroup\", \"func\": \"loadempNotin\",\"emo\":\"加载不在此工作组的人员列表(同属同一机构)\"},\n" +
				"    \"loadposin\": {\"ctrl\": \"om/workgroup\", \"func\": \"loadpositionin\",\"emo\":\"生成下级岗位列表\"},\n" +
				"    \"loadposNotin\": {\"ctrl\": \"om/workgroup\", \"func\": \"loadpositionNotin\",\"emo\":\"加载不在此工作组的岗位列表(同属同一机构)\"},\n" +
				"    \"queryChild\": {\"ctrl\": \"om/workgroup\", \"func\": \"queryChild\",\"emo\":\"条件查询工作组列表\"},\n" +
				"    \"queryall\": {\"ctrl\": \"om/workgroup\", \"func\": \"queryall\",\"emo\":\"查询所有工作组列表\"},\n" +
				"    \"add\": {\"ctrl\": \"om/workgroup\", \"func\": \"add\",\"emo\":\"新增根工作组\"},\n" +
				"    \"delete\": {\"ctrl\": \"om/workgroup\", \"func\": \"delete\",\"emo\":\"删除工作组\"},\n" +
				"    \"enableGroup\": {\"ctrl\": \"om/workgroup\", \"func\": \"enableGroup\",\"emo\":\"启用---注销工作组\"},\n" +
				"    \"updateGroup\": {\"ctrl\": \"om/workgroup\", \"func\": \"updateGroup\",\"emo\":\"更新修改工作组\"},\n" +
				"    \"initGroupCode\": {\"ctrl\": \"om/workgroup\", \"func\": \"initGroupCode\",\"emo\":\"生成工作组代码\"},\n" +
				"    \"loadPosition\": {\"ctrl\": \"om/workgroup\", \"func\": \"loadPosition\",\"emo\":\"生成下级岗位列表\"},\n" +
				"    \"addEmpGroup\": {\"ctrl\": \"om/workgroup\", \"func\": \"addEmpGroup\",\"emo\":\"新添人员\"},\n" +
				"    \"addGroupPosition\": {\"ctrl\": \"om/workgroup\", \"func\": \"addGroupPosition\",\"emo\":\"新添岗位\"},\n" +
				"    \"deleteEmpGroup\": {\"ctrl\": \"om/workgroup\", \"func\": \"deleteEmpGroup\",\"emo\":\"删除人员-工作组关联\"},\n" +
				"    \"deleteGroupPosition\": {\"ctrl\": \"om/workgroup\", \"func\": \"deleteGroupPosition\",\"emo\":\"删除岗位\"},\n" +
				"    \"queryAllGroup\": {\"ctrl\": \"om/workgroup\", \"func\": \"queryAllGroup\",\"emo\":\"查询所有工作组\"},\n" +
				"    \"queryApp\": {\"ctrl\": \"om/workgroup\", \"func\": \"queryApp\",\"emo\":\"查询工作组\"},\n" +
				"    \"queryNotInApp\": {\"ctrl\": \"om/workgroup\", \"func\": \"queryNotInApp\",\"emo\":\"查询可以为工作组添加的应用\"},\n" +
				"    \"addGroupApp\": {\"ctrl\": \"om/workgroup\", \"func\": \"addGroupApp\",\"emo\":\"新增工作组-应用记录\"},\n" +
				"    \"deleteGroupApp\": {\"ctrl\": \"om/workgroup\", \"func\": \"deleteGroupApp\",\"emo\":\"删除工作组-应用记录\"}\n" +
				"  },\n" +
				"  \"BHVTYPEDEF1510538197\" : { \n" +
				"    \"queryOperateLogList\": {\"ctrl\": \"OperateLogController\", \"func\": \"queryOperateLogList\",\"emo\":\"查询操作日志列表\"},\n" +
				"    \"queryOperateHistoryList\": {\"ctrl\": \"OperateLogController\", \"func\": \"queryOperateHistoryList\",\"emo\":\"查询操作对象历史日志\"},\n" +
				"    \"queryOperateDetail\": {\"ctrl\": \"OperateLogController\", \"func\": \"queryOperateDetail\",\"emo\":\"查询操作日志详情\"},\n" +
				"    \"queryLoginHistory\": {\"ctrl\": \"OperateLogController\", \"func\": \"queryLoginHistory\",\"emo\":\"查询操作员登录日志\"}\n" +
				"\n" +
				"  }\n" +
				"}";
		JSONObject jsonObject = JSON.parseObject(json);
//		List<AcBhvDef> list = new ArrayList<>();
		for (String type : jsonObject.keySet()) {
			JSONObject obj = jsonObject.getJSONObject(type);
			for (String s: obj.keySet()) {
				JSONObject o = obj.getJSONObject(s);
				System.out.println(o);
				AcBhvDef acBhvDef = new AcBhvDef();
				acBhvDef.setGuidBehtype(type);
				acBhvDef.setGuid(GUID.bhvdef());
				acBhvDef.setBhvName(o.getString("emo"));
				acBhvDef.setBhvCode("\\" + o.getString("ctrl") + "\\" + o.getString("func"));
				applicationRService.funactAdd(acBhvDef);
//				list.add(acBhvDef);
			}
		}


	}



	
	
	
	
}
