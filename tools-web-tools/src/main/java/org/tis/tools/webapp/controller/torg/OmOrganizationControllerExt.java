package org.tis.tools.webapp.controller.torg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tis.tools.base.Page;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmEmployee;
import org.tis.tools.model.po.torg.OmOrganization;
import org.tis.tools.model.po.torg.OmPosition;
import org.tis.tools.rservice.torg.IOmEmployeeRService;
import org.tis.tools.rservice.torg.IOmOrganizationRService;
import org.tis.tools.rservice.torg.IOmOrganizationRServiceExt;
import org.tis.tools.rservice.torg.IOmPositionRService;
import org.tis.tools.webapp.JsTree;
import org.tis.tools.webapp.TreeData;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;
import org.tis.tools.webapp.util.JSONUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/torg")
public class OmOrganizationControllerExt extends BaseController {
	
	@Autowired
	IOmOrganizationRService omOrganizationRService;
	@Autowired
	IOmPositionRService omPositionRService;
	
	@Autowired
	IOmEmployeeRService omEmployeeRService;
	
	@Autowired
	IOmOrganizationRServiceExt omOrganizationRServiceExt;
	
	/**
	 * 加载JsTree节点数据
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/treeList",method=RequestMethod.GET)
	public String testGetController(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("加载树节点数据" );
			}
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			List<JsTree> list = new ArrayList<>();			
			if(id != null) {
				if("#".equals(id)) {
					JsTree jt = new JsTree();
					jt.setId("root");
					jt.setText("机构人员树");
					jt.setIcon(JsTree.ROOT_TREE_ICON);
					jt.setChildren(true);
					jt.setData(new TreeData()
							.setViewType(JsTree.TYPE_ROOT)
							.setCurrentId("root")
							);
					list.add(jt);
				} else if("root".equals(id)) {
					List<OmOrganization> rootList = omOrganizationRService
							.query(new WhereCondition()
									.andEquals("orglevel", 1 ));
					if (rootList != null && rootList.size() > 0) {
						for(OmOrganization org : rootList) {
							JsTree orgTree = new JsTree();
							orgTree.setId(org.getOrgid().toString());
							orgTree.setText(org.getOrgname());
							orgTree.setIcon(JsTree.ORG_ICON);
							orgTree.setData(new TreeData()
									.setCurrentId(org.getOrgid().toString())
									.setViewType(JsTree.TYPE_ORG)
									.setParentId(id)
									.setParentName(name));
							//if (omOrganizationRService.query(new WhereCondition()
							//		.andEquals("parentorgid", org.getOrgid())).size() > 0 ||
							//		omOrganizationRServiceExt.loadEmpByOrg(new WhereCondition().andEquals("orgid", org.getOrgid().toString())).size() > 0 ||
							//		omPositionRService.query(new WhereCondition().andEquals("orgid", org.getOrgid())).size() > 0) {
								orgTree.setChildren(true);
							//}
							list.add(orgTree);
						}
					}
				} else if(id.startsWith(".")){
					List<OmPosition> posiList = omPositionRService.query(new WhereCondition()
							.andEquals("manaposi", id.substring(1)));
					if (posiList != null && posiList.size() > 0) {
						for(OmPosition posi : posiList) {
							JsTree posiTree = new JsTree();
							posiTree.setId("." + posi.getPositionid().toString());
							posiTree.setText(posi.getPosiname());
							posiTree.setIcon(JsTree.POSITION_ICON);
							posiTree.setData(new TreeData()
									.setCurrentId(posi.getPositionid().toString())
									.setParentId(id)
									.setParentName(name)
									.setViewType(JsTree.TYPE_POSI));
							posiTree.setChildren(true);
							list.add(posiTree);
						}
					}
					//因人员与机构之间关系涉及中间表，故使用自定义sql语句查询
					List<OmEmployee> empList1 = omOrganizationRServiceExt.loadEmpByPosi(new WhereCondition().andEquals("positionid", id.substring(1)));
					if (empList1 != null && empList1.size() > 0) {
						for(OmEmployee emp : empList1) {
							JsTree empTree = new JsTree();
							empTree.setId(id + "-" + emp.getEmpid().toString());
							empTree.setText(emp.getEmpname());
							empTree.setChildren(false);
							empTree.setIcon(JsTree.EMP_ICON);
							empTree.setData(new TreeData()
									.setCurrentId(emp.getEmpid().toString())
									.setParentId(id)
									.setParentName(name)
									.setViewType(JsTree.TYPE_EMP));
							list.add(empTree);
						}
					}
					
				} else {
					List<OmOrganization> childList = omOrganizationRService
							.query(new WhereCondition()
									.andEquals("parentorgid", id ));
					if (childList != null && childList.size() > 0) {
						for(OmOrganization org : childList) {
							JsTree orgTree = new JsTree();
							orgTree.setId(org.getOrgid().toString());
							orgTree.setText(org.getOrgname());
							orgTree.setIcon(JsTree.ORG_ICON);
							orgTree.setData(new TreeData()
									.setViewType(JsTree.TYPE_ORG)
									.setParentId(id)
									.setParentName(name)
									.setCurrentId(org.getOrgid().toString()));
							//if (omOrganizationRService.query(new WhereCondition()
							//		.andEquals("parentorgid", org.getOrgid())).size() > 0 ||
							//		omOrganizationRServiceExt.loadEmpByOrg(new WhereCondition().andEquals("orgid", org.getOrgid().toString())).size() > 0 ||
							//		omPositionRService.query(new WhereCondition().andEquals("orgid", org.getOrgid())).size() > 0) {
								orgTree.setChildren(true);
							//}
							list.add(orgTree);
						}
					}
					List<OmPosition> posiList = omPositionRService.query(new WhereCondition()
							.andEquals("orgid", id).andIsNull("manaposi"));
					if (posiList != null && posiList.size() > 0) {
						for(OmPosition posi : posiList) {
							JsTree posiTree = new JsTree();
							posiTree.setId("." + posi.getPositionid().toString());
							posiTree.setText(posi.getPosiname());
							posiTree.setChildren(true);
							posiTree.setIcon(JsTree.POSITION_ICON);
							posiTree.setData(new TreeData()
									.setCurrentId(posi.getPositionid().toString())
									.setParentId(id)
									.setParentName(name)
									.setViewType(JsTree.TYPE_POSI));
							list.add(posiTree);
						}
					}
					//因人员与机构之间关系涉及中间表，故使用自定义sql语句查询
					List<OmEmployee> empList = omOrganizationRServiceExt.loadEmpByOrg(new WhereCondition().andEquals("orgid", id));
					if (empList != null && empList.size() > 0) {
						for(OmEmployee emp : empList) {
							JsTree empTree = new JsTree();
							empTree.setId(id + "-" + emp.getEmpid().toString());
							empTree.setText(emp.getEmpname());
							empTree.setChildren(false);
							empTree.setIcon(JsTree.EMP_ICON);
							empTree.setData(new TreeData()
									.setCurrentId(emp.getEmpid().toString())
									.setParentId(id)
									.setParentName(name)
									.setViewType(JsTree.TYPE_EMP));
							list.add(empTree);
						}
					}
				}
			}
			result.put("list", list);
			AjaxUtils.ajaxJson(response,JSONArray.fromObject(list,jsonConfig).toString() );
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "加载异常!");
			logger.error("OmOrganizationControllerExt exception : ", e);
		}
		return null;
	}

	/**
	 * 根据机构id查询机构信息
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omOrganization/loadByOrgId")
	public String loadByOrgId(ModelMap model, @RequestBody String content,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			String orgId = JSONUtils.getStr(jsonObj, "orgId");
			OmOrganization k =  omOrganizationRService.query(new WhereCondition().andEquals("orgid", Integer.parseInt(orgId))).get(0);
			JSONObject jo = JSONObject.fromObject(k,jsonConfig);
			AjaxUtils.ajaxJson(response, jo.toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据岗位id查询岗位信息
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omPosition/loadByPosiId")
	public String loadByPosiId(ModelMap model, @RequestBody String content,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			String orgId = JSONUtils.getStr(jsonObj, "posiId");
			OmPosition k =  omPositionRService.query(new WhereCondition().andEquals("positionid", Integer.parseInt(orgId))).get(0);
			JSONObject jo = JSONObject.fromObject(k,jsonConfig);
			AjaxUtils.ajaxJson(response, jo.toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 新增编辑机构
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omOrganization/save")
	public String save(ModelMap model, @RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		WhereCondition wc = new WhereCondition();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("保存机构信息 request " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);
			JSONObject job = jsonObj.getJSONObject("item");
			final OmOrganization p = new OmOrganization();
		
			JSONObject.toBean(job, p, jsonConfig);
			//p.setOrgid(Integer.parseInt(attrValStr));
			if (StringUtils.isNotEmpty(p.getId())) {
				omOrganizationRService.update(p);
				result.put("retCode", "1");
				result.put("retMessage", "编辑成功！");
			} else {
				
				wc.andEquals("orgcode", p.getOrgcode());
				List<OmOrganization> orgs = omOrganizationRService.query(wc);
				if (orgs.size() > 0) {
					result.put("retCode", "2");
					result.put("retMessage", "机构代码已存在!");
					AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
					return null;
				}
				Integer genId = omOrganizationRServiceExt.genOrgId();
				if(p.getParentorgid() != null) {
					OmOrganization parOrg = omOrganizationRService.query(new WhereCondition()
							.andEquals("orgId", p.getParentorgid())).get(0);
					p.setOrglevel(parOrg.getOrglevel() + 1);
					p.setOrgseq(parOrg.getOrgseq() + genId + "." );
					
				} else {
					p.setOrglevel(1);
					p.setOrgseq("." + genId + ".");
				}
				p.setOrgid(genId);
				
				p.setId(sequenceBiz.generateId("OmOrganization"));
				omOrganizationRService.insert(p);
				result.put("retCode", "1");
				result.put("retMessage", "新增成功！");
			}
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "保存机构信息异常!");
			logger.error("OrganizationController save exception : ", e);
		}
		return null;
	}
	
	/**
	 * 根据机构id查询子机构列表
	 * 预先写入"parentorgid_eq":data查询条件
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omOrganization/loadChildOrgList")
	public String loadChildOrgList(ModelMap model, @RequestBody String content,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			// 分页对象
			Page page = getPage(jsonObj);
			// 服务端排序规则
			String orderGuize = getOrderGuize(JSONUtils.getStr(jsonObj, "orderGuize"));
			// 组装查询条件
			WhereCondition wc = new WhereCondition();
			wc.setLength(page.getItemsperpage());
			wc.setOffset((page.getCurrentPage() - 1) * page.getItemsperpage());
			wc.setOrderBy(orderGuize);
			initWanNengChaXun(jsonObj, wc);// 万能查询
			List<OmOrganization> list = omOrganizationRService.query(wc);
			JSONArray ja = JSONArray.fromObject(list,jsonConfig);
			page.setTotalItems(omOrganizationRService.count(wc));
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			map.put("list", ja);
			String s=JSONObject.fromObject(map,jsonConfig).toString();
			AjaxUtils.ajaxJson(response,s );
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据id删除子机构信息
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/omOrganization/delOrgById")
	public String orgDel(ModelMap model, @RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			final String id = jsonObj.getString("id");
			if (StringUtils.isNotEmpty(id)) {
				transactionTemplate.execute(new TransactionCallback<Map<String, Object>>() {
					@Override
					public Map<String, Object> doInTransaction(TransactionStatus arg0) {
						WhereCondition wc = new WhereCondition();
						wc.andEquals("id", id);
						// 删除
						omOrganizationRService.deleteByCondition(wc);
						return null;
					}
				});
				result.put("retCode", "1");
				result.put("retMessage", "成功删除");
			} else {
				result.put("retCode", "2");
				result.put("retMessage", "id不能为空！");
			}
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "删除机构信息异常！");
			logger.error("删除机构 exception : ", e);
		}
		return null;
	}
	
	/**
	 * 根据机构id查询子岗位列表
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omOrganization/loadChildPosiList")
	public String loadChildPosiList(ModelMap model, @RequestBody String content,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			// 分页对象
			Page page = getPage(jsonObj);
			// 服务端排序规则
			String orderGuize = getOrderGuize(JSONUtils.getStr(jsonObj, "orderGuize"));
			// 组装查询条件
			WhereCondition wc = new WhereCondition();
			wc.setLength(page.getItemsperpage());
			wc.setOffset((page.getCurrentPage() - 1) * page.getItemsperpage());
			wc.setOrderBy(orderGuize);
			initWanNengChaXun(jsonObj, wc);// 万能查询
			List<OmPosition> list = omPositionRService.query(wc);
			JSONArray ja = JSONArray.fromObject(list,jsonConfig);
			page.setTotalItems(omPositionRService.count(wc));
			Map<String, Object> map = new HashMap<>();
			map.put("page", page);
			map.put("list", ja);
			String s=JSONObject.fromObject(map,jsonConfig).toString();
			AjaxUtils.ajaxJson(response,s );
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 新增编辑岗位
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omPosition/save")
	public String savePosi(ModelMap model, @RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		WhereCondition wc = new WhereCondition();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("保存岗位信息 request " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);
			JSONObject job = jsonObj.getJSONObject("item");
			final OmPosition p = new OmPosition();
		
			JSONObject.toBean(job, p, jsonConfig);
			//p.setOrgid(Integer.parseInt(attrValStr));
			if (StringUtils.isNotEmpty(p.getId())) {
				p.setLastupdate(new Date());
				omPositionRService.update(p);
				result.put("retCode", "1");
				result.put("retMessage", "编辑成功！");
			} else {
				wc.andEquals("posicode", p.getPosicode());
				List<OmPosition> posis = omPositionRService.query(wc);
				if (posis.size() > 0) {
					result.put("retCode", "2");
					result.put("retMessage", "岗位代码已存在!");
					AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
					return null;
				}
				Integer genId = omOrganizationRServiceExt.genOrgId();
				if(p.getManaposi() != null) {
					OmPosition manaPosi = omPositionRService.query(new WhereCondition()
							.andEquals("positionid", p.getManaposi())).get(0);
					p.setPosilevel(manaPosi.getPosilevel() + 1);
					p.setPositionseq(manaPosi.getPositionseq() + genId + "." );
					
				} else {
					p.setPosilevel(1);
					p.setPositionseq("." + genId + ".");
				}
				p.setPositionid(genId);
				p.setCreatetime(new Date());
				p.setId(sequenceBiz.generateId("OmPosition"));
				omPositionRService.insert(p);
				result.put("retCode", "1");
				result.put("retMessage", "新增成功！");
			}
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "保存岗位信息异常!");
			logger.error("保存岗位信息 exception : ", e);
		}
		return null;
	}
	/**
	 * 根据id删除子岗位信息
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/omPosition/delPosiById")
	public String posiDel(ModelMap model, @RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			final String id = jsonObj.getString("id");
			if (StringUtils.isNotEmpty(id)) {
				transactionTemplate.execute(new TransactionCallback<Map<String, Object>>() {
					@Override
					public Map<String, Object> doInTransaction(TransactionStatus arg0) {
						WhereCondition wc = new WhereCondition();
						wc.andEquals("id", id);
						// 删除
						omPositionRService.deleteByCondition(wc);
						return null;
					}
				});
				result.put("retCode", "1");
				result.put("retMessage", "成功删除");
			} else {
				result.put("retCode", "2");
				result.put("retMessage", "id不能为空！");
			}
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "删除岗位信息异常！");
			logger.error("删除岗位 exception : ", e);
		}
		return null;
	}
	
	/**
	 * 根据机构id查询人员列表
	 * 预先写入"parentorgid_eq":data查询条件
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omOrganization/loadChildEmpList")
	public String loadChildEmpList(ModelMap model, @RequestBody String content,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			// 分页对象
			Page page = getPage(jsonObj);
			// 服务端排序规则
			String orderGuize = getOrderGuize(JSONUtils.getStr(jsonObj, "orderGuize"));
			// 组装查询条件
			WhereCondition wc = new WhereCondition();
			wc.setLength(page.getItemsperpage());
			wc.setOffset((page.getCurrentPage() - 1) * page.getItemsperpage());
			wc.setOrderBy(orderGuize);
			JSONObject jsonObjsearchItems = jsonObj.getJSONObject("searchItems");
			initWanNengChaXun(jsonObj, wc);// 万能查询
			//判断查询类型
			String type = JSONUtils.getStr(jsonObj, "searchType");
			Map<String, Object> map = new HashMap<>();
			if("org".equals(type)) {
				List<OmEmployee> list = omOrganizationRServiceExt.loadEmpByOrg(wc);
				JSONArray ja = JSONArray.fromObject(list,jsonConfig);
				page.setTotalItems(omOrganizationRServiceExt.countEmpByOrg(wc));
				map.put("page", page);
				map.put("list", ja);	
			}
			if("posi".equals(type)) {
				List<OmEmployee> list = omOrganizationRServiceExt.loadEmpByPosi(wc);
				JSONArray ja = JSONArray.fromObject(list,jsonConfig);
				page.setTotalItems(omOrganizationRServiceExt.countEmpByPosi(wc));
				map.put("page", page);
				map.put("list", ja);	
			}
			
			String s=JSONObject.fromObject(map,jsonConfig).toString();
			AjaxUtils.ajaxJson(response,s );
			
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 新增编辑人员
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/omEmployee/save")
	public String saveEmp(ModelMap model, @RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		WhereCondition wc = new WhereCondition();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("保存人员信息 request " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);
			JSONObject job = jsonObj.getJSONObject("item");
			String id = jsonObj.getString("id");
			final OmEmployee p = new OmEmployee();
			JSONObject.toBean(job, p, jsonConfig);
			//p.setOrgid(Integer.parseInt(attrValStr));
			if (StringUtils.isNotEmpty(p.getId())) {
				p.setLastmodytime(new Date());
				omEmployeeRService.update(p);
				result.put("retCode", "1");
				result.put("retMessage", "编辑成功！");
			} else {
				wc.andEquals("empcode", p.getEmpcode());
				List<OmEmployee> emps = omEmployeeRService.query(wc);
				if (emps.size() > 0) {
					result.put("retCode", "2");
					result.put("retMessage", "人员代码已存在!");
					AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
					return null;
				}
				Integer genId = omOrganizationRServiceExt.genOrgId();
				p.setEmpid(genId);
				p.setCreatetime(new Date());
				p.setId(sequenceBiz.generateId("OmEmployee"));
				Map<String, Object> params = new HashMap<>();
				params.put("orgid", id);
				params.put("empid", genId);
				transactionTemplate.execute(new TransactionCallback<Map<String, Object>>() {
					@Override
					public Map<String, Object> doInTransaction(TransactionStatus arg0) {
						omEmployeeRService.insert(p);
						omOrganizationRServiceExt.insertEmpWithOrg(params);
						return null;
					}
				});	
				result.put("retCode", "1");
				result.put("retMessage", "新增成功！");
			}
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "保存人员信息异常!");
			logger.error("保存人员信息 exception : ", e);
		}
		return null;
	}
	
	/**
	 * 根据id删除人员信息
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/omEmployee/delEmpWithRef")
	public String empDel(ModelMap model, @RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			final String id = jsonObj.getString("id");
			String empId = jsonObj.getString("empId");
			if (StringUtils.isNotEmpty(id)) {
				transactionTemplate.execute(new TransactionCallback<Map<String, Object>>() {
					@Override
					public Map<String, Object> doInTransaction(TransactionStatus arg0) {
						WhereCondition wc = new WhereCondition();
						wc.andEquals("id", id);
						omOrganizationRServiceExt.deleteEmpWithOrg(empId);
						omOrganizationRServiceExt.deleteEmpWithPosi(empId);
						omEmployeeRService.deleteByCondition(wc);
						return null;
					}
				});
				result.put("retCode", "1");
				result.put("retMessage", "成功删除");
			} else {
				result.put("retCode", "2");
				result.put("retMessage", "id不能为空！");
			}
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "删除人员信息异常！");
			logger.error("删除人员 exception : ", e);
		}
		return null;
	}
	
	private Map<String, Object> responseMsg ;
	@Override
	public Map<String, Object> getResponseMessage() {
		if( null == responseMsg ){
			responseMsg = new HashMap<String, Object> () ;
		}
		return responseMsg ;
	}

}
