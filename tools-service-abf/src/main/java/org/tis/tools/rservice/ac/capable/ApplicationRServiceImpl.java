/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.tis.tools.model.po.ac.AcFuncBehavior;
import org.tis.tools.model.po.ac.AcFuncResource;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.vo.om.OmOrgDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.AppManagementException;
import org.tis.tools.service.ac.AcAppService;
import org.tis.tools.service.ac.AcFuncBehaviorService;
import org.tis.tools.service.ac.AcFuncResourceService;
import org.tis.tools.service.ac.AcFuncService;
import org.tis.tools.service.ac.AcFuncgroupService;
import org.tis.tools.service.ac.AcMenuService;
import org.tis.tools.service.ac.AcOperatorService;
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
public class ApplicationRServiceImpl extends BaseRService implements
		IApplicationRService {

	@Autowired
	AcAppService acAppService;
	@Autowired
	AcFuncgroupService acFuncgroupService;
	@Autowired
	AcFuncService acFuncService;
	@Autowired
	AcMenuService acMenuService;
	@Autowired
	AcFuncResourceService acFuncResourceService;
	@Autowired
	AcOperatorService acOperatorService;
	@Autowired
	AcFuncBehaviorService acFuncBehaviorService;
	
	

	/**
	 * 新增应用系统(AC_APP)
	 * 
	 * @param acApp
	 *            应用对象 return acApp
	 */
	@Override
	public AcApp createAcApp(AcApp acApp) throws AppManagementException {
		acApp.setGuid(GUID.app());
		AcApp newAcApp = acApp;
		try {
			acApp = transactionTemplate
					.execute(new TransactionCallback<AcApp>() {
						@Override
						public AcApp doInTransaction(TransactionStatus arg0) {
							acAppService.insert(newAcApp);
							return newAcApp;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_CREATE_AC_APP,
					BasicUtil.wrap(e.getCause().getMessage()), "新增应用失败！{0}");
		}
		return acApp;
	}

	/**
	 * 删除应用系统(AC_APP)
	 * 
	 * @param guid
	 *            应用系统guid
	 */
	@Override
	public void deleteAcApp(String guid) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcApp>() {
				@Override
				public AcApp doInTransaction(TransactionStatus arg0) {
					acAppService.delete(guid);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_DELETE_AC_APP,
					BasicUtil.wrap(e.getCause().getMessage()), "删除应用失败！{0}");
		}
	}

	/**
	 * 更新应用系统(AC_APP)
	 * 
	 * @param t
	 *            新值
	 */
	@Override
	public void updateAcApp(AcApp t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcApp>() {
				@Override
				public AcApp doInTransaction(TransactionStatus arg0) {
					acAppService.update(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_UPDATE_AC_APP,
					BasicUtil.wrap(e.getCause().getMessage()), "更新应用失败！{0}");
		}
	}

	/**
	 * 根据条件查询应用系统(AC_APP)
	 * 
	 * @param wc
	 *            条件
	 * @return 满足条件的记录list
	 */
	public List<AcApp> queryAcApp(WhereCondition wc) throws AppManagementException {
		List<AcApp> acAppList = new ArrayList<AcApp>();
		
		try {
			acAppList = acAppService.query(wc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_QUERY_AC_APP,
					BasicUtil.wrap(e.getCause().getMessage()), "查询应用失败！{0}");
		}
		return acAppList;
	}

	
	/**
	 * 新增功能组(AC_FUNCGROUP)
	 * 
	 * @param acFuncgroup
	 *            功能组对象 return AcFuncgroup
	 */
	@Override
	public AcFuncgroup createAcFuncGroup(AcFuncgroup acFuncgroup) {
		String guid = GUID.funcGroup();
		String guidApp = acFuncgroup.getGuidApp();
		String funcgroupSeq = "";
		String guidParents = acFuncgroup.getGuidParents();
		acFuncgroup.setGuid(guid);
		// 根据时候有父功能组设置序列
		if (guidParents.isEmpty()) {
			funcgroupSeq = guidApp + "." + guid;
		} else {
			acFuncgroup.setGuidParents(guidParents);
			WhereCondition wc = new WhereCondition();
			wc.andEquals("GUID", guidApp);
			List<AcFuncgroup> list = acFuncgroupService.query(wc);
			String parentSeq = list.get(0).getFuncgroupSeq();
			funcgroupSeq = parentSeq + "." + guid;
		}
		acFuncgroup.setFuncgroupSeq(funcgroupSeq);
		acFuncgroup.setIsleaf(CommonConstants.YES);// 默认叶子节点
		acFuncgroup.setSubCount(new BigDecimal(0));// 默认无节点数
		AcFuncgroup newAcFuncgroup = acFuncgroup;
		try {
			acFuncgroup = transactionTemplate
					.execute(new TransactionCallback<AcFuncgroup>() {
						@Override
						public AcFuncgroup doInTransaction(
								TransactionStatus arg0) {
							acFuncgroupService.insert(newAcFuncgroup);
							return newAcFuncgroup;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNCGROUP,
					BasicUtil.wrap(e.getCause().getMessage()), "新增功能组失败！{0}");
		}
		return acFuncgroup;
	}

	/**
	 * 删除功能组(AC_FUNCGROUP)
	 * 
	 * @param guid
	 *            记录guid
	 */
	public void deleteAcFuncGroup(String guid) throws AppManagementException {
		try {
			// 新增事务提交机制
			transactionTemplate.execute(new TransactionCallback<AcFuncgroup>() {
				@Override
				public AcFuncgroup doInTransaction(TransactionStatus arg0) {
					acFuncgroupService.delete(guid);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_DELETE_AC_FUNCGROUP,
					BasicUtil.wrap(e.getCause().getMessage()), "删除功能组失败！{0}");
		}
	}

	/**
	 * 更新功能组(AC_FUNCGROUP)
	 * @param t 新值
	 */
	public void updateAcFuncgroup(AcFuncgroup t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcFuncgroup>() {
				@Override
				public AcFuncgroup doInTransaction(TransactionStatus arg0) {
					acFuncgroupService.update(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_UPDATE_AC_FUNCGROUP,
					BasicUtil.wrap(e.getCause().getMessage()), "更新功能组失败！{0}");
		}
	}
	
	
	/**
	 * 根据条件查询功能组(AC_FUNCGROUP)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	public List<AcFuncgroup> queryAcFuncgroup(WhereCondition wc)throws AppManagementException {
		List<AcFuncgroup> acFuncgroupList = new ArrayList<AcFuncgroup>();
		
		try {
			acFuncgroupList = acFuncgroupService.query(wc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_QUERY_AC_FUNCGROUP,
					BasicUtil.wrap(e.getCause().getMessage()), "查询功能组失败！{0}");
		}
		return acFuncgroupList;
	}
	
	
	/**
	 * 新增功能(AC_FUNC) return AcFunc
	 */
	@Override
	public AcFunc createAcFunc(AcFunc acFunc) {
		acFunc.setGuid(GUID.func());
		AcFunc newAcFunc = acFunc;
		try {
			acFunc = transactionTemplate
					.execute(new TransactionCallback<AcFunc>() {
						@Override
						public AcFunc doInTransaction(TransactionStatus arg0) {
							acFuncService.insert(newAcFunc);
							return newAcFunc;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNC,
					BasicUtil.wrap(e.getCause().getMessage()), "新增功能失败！{0}");
		}
		return acFunc;
	}
	
	/**
	 * 删除功能(AC_FUNC)
	 * @param guid 记录guid
	 */
	@Override
	public void deleteAcFunc(String guid)  throws AppManagementException {
		try {
			// 新增事务提交机制
			transactionTemplate.execute(new TransactionCallback<AcFunc>() {
				@Override
				public AcFunc doInTransaction(TransactionStatus arg0) {
					acFuncService.delete(guid);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_DELETE_AC_FUNC,
					BasicUtil.wrap(e.getCause().getMessage()), "删除功能失败！{0}");
		}
	}

	/**
	 * 更新功能(AC_FUNC),只修改t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void updateAcFunc(AcFunc t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcFunc>() {
				@Override
				public AcFunc doInTransaction(TransactionStatus arg0) {
					acFuncService.update(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_UPDATE_AC_FUNC,
					BasicUtil.wrap(e.getCause().getMessage()), "更新功能失败！{0}");
		}
	}

	/**
	 * 根据条件查询功能(AC_FUNC)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	@Override
	public List<AcFunc> queryAcFunc(WhereCondition wc) throws AppManagementException {
		List<AcFunc> acFuncList = new ArrayList<AcFunc>();
		
		try {
			acFuncList = acFuncService.query(wc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_QUERY_AC_FUNC,
					BasicUtil.wrap(e.getCause().getMessage()), "查询功能失败！{0}");
		}
		return acFuncList;
	}
	
	/**
	 * 新增菜单(AC_MENU)
	 * 
	 * @param acMenu
	 *            菜单代码 return acMenu
	 */
	@Override
	public AcMenu createAcMenu(AcMenu acMenu) throws AppManagementException{
		String guid = GUID.menu();
		acMenu.setGuid(guid);
		acMenu.setIsleaf(CommonConstants.YES);// 默认叶子菜单
		acMenu.setSubCount(new BigDecimal(0));
		String guidParent = acMenu.getGuidParents();
		String menuSeq = guid;// 默认菜单序列为跟guid
		AcMenu newAcMenu = acMenu;
		newAcMenu.setMenuSeq(menuSeq);
		try {
			acMenu = transactionTemplate
					.execute(new TransactionCallback<AcMenu>() {
						@Override
						public AcMenu doInTransaction(TransactionStatus arg0) {
							if (!guidParent.isEmpty()) {
								WhereCondition wc = new WhereCondition();
								List<AcMenu> parentMenulist = acMenuService
										.query(wc);
								AcMenu parentMenu = parentMenulist.get(0);
								String newmenuSeq = parentMenu.getMenuSeq()
										+ "." + menuSeq;
								newAcMenu.setMenuSeq(newmenuSeq);
								parentMenu
										.setSubCount(new BigDecimal(parentMenu
												.getSubCount().intValue() + 1));// 节点数加1
								acMenuService.update(parentMenu);
							}
							acMenuService.insert(newAcMenu);
							return newAcMenu;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_CREATE_AC_MENU,
					BasicUtil.wrap(e.getCause().getMessage()), "新增菜单失败！{0}");
		}
		return acMenu;
	}

	/**
	 * 删除菜单(AC_MENU)
	 * @param guid 记录guid
	 */
	@Override
	public void deleteAcMenu(String guid) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcMenu>() {
				@Override
				public AcMenu doInTransaction(TransactionStatus arg0) {
					acMenuService.delete(guid);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_DELETE_AC_MENU,
					BasicUtil.wrap(e.getCause().getMessage()), "删除菜单失败！{0}");
		}
	}

	/**
	 * 更新菜单(AC_MENU),只修改t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void updateAcMenu(AcMenu t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcMenu>() {
				@Override
				public AcMenu doInTransaction(TransactionStatus arg0) {
					acMenuService.update(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_UPDATE_AC_MENU,
					BasicUtil.wrap(e.getCause().getMessage()), "更新菜单失败！{0}");
		}
	}

	/**
	 * 根据条件查询菜单(AC_MENU)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	@Override
	public List<AcMenu> queryAcMenu(WhereCondition wc) throws AppManagementException {
		List<AcMenu> acMenuList = new ArrayList<AcMenu>();
		
		try {
			acMenuList = acMenuService.query(wc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_QUERY_AC_MENU,
					BasicUtil.wrap(e.getCause().getMessage()), "查询菜单失败！{0}");
		}
		return acMenuList;
	}

	/**
	 * 新增功能资源对应(AC_FUNC_RESOURCE),新增t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void createAcFuncResource(AcFuncResource t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcFuncResource>() {
				@Override
				public AcFuncResource doInTransaction(TransactionStatus arg0) {
					acFuncResourceService.insert(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNCRESOURCE,
					BasicUtil.wrap(e.getCause().getMessage()), "增加功能对应资源失败！{0}");
		}
	}

	/**
	 * 删除功能资源对应(AC_FUNC_RESOURCE)
	 * @param guid 记录guid
	 */
	@Override
	public void deleteAcFuncResource(String guid) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcFuncResource>() {
				@Override
				public AcFuncResource doInTransaction(TransactionStatus arg0) {
					acFuncResourceService.delete(guid);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_DELETE_AC_FUNCRESOURCE,
					BasicUtil.wrap(e.getCause().getMessage()), "删除功能对应资源失败！{0}");
		}
	}

	/**
	 * 更新功能资源对应(AC_FUNC_RESOURCE),只修改t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void updateAcFuncResource(AcFuncResource t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcFuncResource>() {
				@Override
				public AcFuncResource doInTransaction(TransactionStatus arg0) {
					acFuncResourceService.update(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_UPDATE_AC_FUNCRESOURCE,
					BasicUtil.wrap(e.getCause().getMessage()), "更新功能对应资源失败！{0}");
		}
	}

	/**
	 * 根据条件查询功能资源对应(AC_FUNC_RESOURCE)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	@Override
	public List<AcFuncResource> queryAcFuncResource(WhereCondition wc) throws AppManagementException {
		List<AcFuncResource> acFuncResourceList = new ArrayList<AcFuncResource>();

		try {
			acFuncResourceList = acFuncResourceService.query(wc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_QUERY_AC_FUNCRESOURCE,
					BasicUtil.wrap(e.getCause().getMessage()), "查询功能对应资源失败！{0}");
		}
		return acFuncResourceList;
	}

	/**
	 * 新增操作员(AC_OPERATOR),新增t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void createAcOperator(AcOperator t) throws AppManagementException {
		t.setGuid(GUID.operaor());
		try {
			transactionTemplate.execute(new TransactionCallback<AcOperator>() {
				@Override
				public AcOperator doInTransaction(TransactionStatus arg0) {
					acOperatorService.insert(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_CREATE_AC_OPERATOR,
					BasicUtil.wrap(e.getCause().getMessage()), "增加操作员失败！{0}");
		}
	}
		

	/**
	 * 删除操作员(AC_OPERATOR)
	 * @param guid 记录guid
	 */
	@Override
	public void deleteAcOperator(String guid) throws AppManagementException {
		//TODO 删除所有操作员相关资源
		try {
			transactionTemplate.execute(new TransactionCallback<AcOperator>() {
				@Override
				public AcOperator doInTransaction(TransactionStatus arg0) {
					acOperatorService.delete(guid);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_DELETE_AC_OPERATOR,
					BasicUtil.wrap(e.getCause().getMessage()), "删除操作员失败！{0}");
		}
	}

	/**
	 * 更新操作员(AC_OPERATOR),只修改t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void updateAcOperator(AcOperator t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcOperator>() {
				@Override
				public AcOperator doInTransaction(TransactionStatus arg0) {
					acOperatorService.update(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_UPDATE_AC_OPERATOR,
					BasicUtil.wrap(e.getCause().getMessage()), "更新操作员失败！{0}");
		}
	}

		
	/**
	 * 根据条件查询操作员(AC_OPERATOR)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	@Override
	public List<AcOperator> queryAcOperator(WhereCondition wc) throws AppManagementException {
		List<AcOperator> acOperatorList = new ArrayList<AcOperator>();

		try {
			acOperatorList = acOperatorService.query(wc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_QUERY_AC_FUNCRESOURCE,
					BasicUtil.wrap(e.getCause().getMessage()), "查询功能对应资源失败！{0}");
		}
		return acOperatorList;
	}

	/**
	 * 增加功能操作行为(AC_FUNC_BEHAVIOR),增加t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void createAcFuncBehavior(AcFuncBehavior t) throws AppManagementException {
		t.setGuid(GUID.funcBehavior());
		try {
			transactionTemplate.execute(new TransactionCallback<AcFuncBehavior>() {
				@Override
				public AcFuncBehavior doInTransaction(TransactionStatus arg0) {
					acFuncBehaviorService.insert(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_CREATE_AC_FUNCBEHAVIOR,
					BasicUtil.wrap(e.getCause().getMessage()), "增加功能操作行为失败！{0}");
		}
	}

	/**
	 * 删除功能操作行为(AC_FUNC_BEHAVIOR)
	 * @param guid 记录guid
	 */
	@Override
	public void deleteAcFuncBehavior(String guid) throws AppManagementException {
		//TODO 删除功能操作行为相关资源
		try {
			transactionTemplate.execute(new TransactionCallback<AcFuncBehavior>() {
				@Override
				public AcFuncBehavior doInTransaction(TransactionStatus arg0) {
					acFuncBehaviorService.delete(guid);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_DELETE_AC_FUNCBEHAVIOR,
					BasicUtil.wrap(e.getCause().getMessage()), "删除功能操作行为失败！{0}");
		}
	}

	/**
	 * 更新功能操作行为(AC_FUNC_BEHAVIOR),只修改t对象有值的字段
	 * @param t 新值
	 */
	@Override
	public void updateAcFuncBehavior(AcFuncBehavior t) throws AppManagementException {
		try {
			transactionTemplate.execute(new TransactionCallback<AcFuncBehavior>() {
				@Override
				public AcFuncBehavior doInTransaction(TransactionStatus arg0) {
					acFuncBehaviorService.update(t);
					return null;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_UPDATE_AC_FUNCBEHAVIOR,
					BasicUtil.wrap(e.getCause().getMessage()), "更新功能操作行为失败！{0}");
		}
	}

	/**
	 * 根据条件查询功能操作行为(AC_FUNC_BEHAVIOR)
	 * @param wc 条件
	 * @return 满足条件的记录list
	 */
	@Override
	public List<AcFuncBehavior> queryAcFuncBehavior(WhereCondition wc) throws AppManagementException {
		List<AcFuncBehavior> acFuncBehaviorList = new ArrayList<AcFuncBehavior>();

		try {
			acFuncBehaviorList = acFuncBehaviorService.query(wc);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppManagementException(
					ACExceptionCodes.FAILURE_WHRN_QUERY_AC_FUNCBEHAVIOR,
					BasicUtil.wrap(e.getCause().getMessage()), "查询功能操作行为失败！{0}");
		}
		return acFuncBehaviorList;
	}
}
