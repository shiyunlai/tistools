/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.model.vo.om.OmOrgDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.AppManagementException;
import org.tis.tools.service.ac.AcAppService;
import org.tis.tools.service.ac.AcFuncService;
import org.tis.tools.service.ac.AcFuncgroupService;
import org.tis.tools.service.ac.AcMenuService;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

/**
 * <pre>
 * 权限（Application）管理服务功能的实现类
 * 
 * <pre>
 * 
 * @author zzc
 *
 */
public class ApplicationRServiceImpl extends BaseRService implements IApplicationRService {

	@Autowired
	AcAppService acAppService;
	@Autowired
	AcFuncgroupService acFuncgroupService;
	@Autowired
	AcFuncService acFuncService;
	@Autowired
	AcMenuService acMenuService;
	
	/**
	 * 新增应用系统(AC_APP)
	 * @param acApp 应用对象
	 * return  acApp
	 */
	@Override
	public AcApp createAcApp(AcApp acApp) throws AppManagementException{
		acApp.setGuid(GUID.app());
		AcApp newAcApp=acApp;
		// 新增应用系统
		try {
			//新增事务提交机制
			acApp = transactionTemplate.execute(new TransactionCallback<AcApp>() {
				@Override
				public AcApp doInTransaction(TransactionStatus arg0) {
							acAppService.insert(newAcApp);
							return newAcApp;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_APP,
					BasicUtil.wrap(e.getCause().getMessage()), "新增应用失败！{0}");
		}
		return acApp;
	}

	/**
	 * 新增功能组(AC_FUNCGROUP)
	 * @param acFuncgroup 功能组对象
	 * return  AcFuncgroup
	 */
	@Override
	public AcFuncgroup createAcFuncGroup(AcFuncgroup acFuncgroup) {
		String guid = GUID.funcGroup();
		String guidApp = acFuncgroup.getGuidApp();
		String funcgroupSeq = "";
		String guidParents = acFuncgroup.getGuidParents();
		acFuncgroup.setGuid(guid);
		//根据时候有父功能组设置序列
		if(guidParents.isEmpty()){
			funcgroupSeq = guidApp + "." + guid;
		}else{
			acFuncgroup.setGuidParents(guidParents);
			WhereCondition wc = new WhereCondition();
			wc.andEquals("GUID", guidApp);
			List<AcFuncgroup> list = acFuncgroupService.query(wc);
			String parentSeq = list.get(0).getFuncgroupSeq();
			funcgroupSeq = parentSeq + "." + guid;
		}
		acFuncgroup.setFuncgroupSeq(funcgroupSeq);
		acFuncgroup.setIsleaf(CommonConstants.YES);//默认叶子节点
		acFuncgroup.setSubCount(new BigDecimal(0));//默认无节点数
		AcFuncgroup newAcFuncgroup=acFuncgroup;
	
		// 新增功能组
		try {
			//新增事务提交机制
			acFuncgroup = transactionTemplate.execute(new TransactionCallback<AcFuncgroup>() {
				@Override
				public AcFuncgroup doInTransaction(TransactionStatus arg0) {
					acFuncgroupService.insert(newAcFuncgroup);
							return newAcFuncgroup;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNCGROUP,
					BasicUtil.wrap(e.getCause().getMessage()), "新增功能组失败！{0}");
		}

		return acFuncgroup;
	}

	/**
	 * 新增功能(AC_FUNC)
	 * return  AcFunc
	 */
	@Override
	public AcFunc createAcFunc(AcFunc acFunc) {
		acFunc.setGuid(GUID.func());
		AcFunc newAcFunc=acFunc;
		// 新增功能
		try {	
			//新增事务提交机制
			acFunc = transactionTemplate.execute(new TransactionCallback<AcFunc>() {
				@Override
				public AcFunc doInTransaction(TransactionStatus arg0) {
					acFuncService.insert(newAcFunc);
							return newAcFunc;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNC,
					BasicUtil.wrap(e.getCause().getMessage()), "新增功能失败！{0}");
		}
		return acFunc;
	}
	
	
	/**
	 * 新增菜单(AC_MENU)
	 * @param acMenu 菜单代码
	 * return  acMenu 
	 */
	@Override
	public AcMenu createAcMenu(AcMenu acMenu) {
		String guid=GUID.menu();
		acMenu.setGuid(guid);
		acMenu.setIsleaf(CommonConstants.YES);//默认叶子菜单
		acMenu.setSubCount(new BigDecimal(0));
		String guidParent = acMenu.getGuidParents();
		String menuSeq=guid;//默认菜单序列为跟guid
		AcMenu newAcMenu= acMenu;
		newAcMenu.setMenuSeq(menuSeq);
		// 新增功能
		try {	
			//新增事务提交机制
			acMenu = transactionTemplate.execute(new TransactionCallback<AcMenu>() {
				@Override
				public AcMenu doInTransaction(TransactionStatus arg0) {
					if(!guidParent.isEmpty()){
						WhereCondition wc = new WhereCondition();
						List<AcMenu> parentMenulist = acMenuService.query(wc);
						AcMenu parentMenu = parentMenulist.get(0);
						String newmenuSeq = parentMenu.getMenuSeq()+"."+ menuSeq;
						newAcMenu.setMenuSeq(newmenuSeq);
						parentMenu.setSubCount(new BigDecimal(parentMenu.getSubCount().intValue()+1));//节点数加1
						acMenuService.update(parentMenu);
					}
					acMenuService.insert(newAcMenu);
					return newAcMenu;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(ACExceptionCodes.FAILURE_WHRN_CREATE_AC_MENU,
					BasicUtil.wrap(e.getCause().getMessage()), "新增菜单失败！{0}");
		}
		return acMenu;
	}
	
}
