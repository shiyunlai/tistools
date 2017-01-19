package org.tis.tools.webapp.controller.torg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tis.tools.base.Page;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmEmployee;
import org.tis.tools.model.po.torg.OmOrganization;
import org.tis.tools.rservice.torg.IOmOrganizationRService;
import org.tis.tools.rservice.torg.IOmOrganizationRServiceExt;
import org.tis.tools.webapp.JsTree;
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
			List<JsTree> list = new ArrayList<>();			
			if(id != null) {
				if("#".equals(id)) {
					JsTree jt = new JsTree();
					jt.setId("root");
					jt.setText("机构人员树");
					jt.setIcon(JsTree.ROOT_TREE_ICON);
					jt.setChildren(true);
					jt.setData(JsTree.TYPE_ROOT);
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
							orgTree.setData(JsTree.TYPE_ORG);
							if (omOrganizationRService.query(new WhereCondition()
									.andEquals("parentorgid", org.getOrgid())).size() > 0 ||
									omOrganizationRServiceExt.loadEmpByOrg(org.getOrgid().toString()).size() > 0) {
								orgTree.setChildren(true);
							}
							list.add(orgTree);
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
							orgTree.setData(JsTree.TYPE_ORG);
							if (omOrganizationRService.query(new WhereCondition()
									.andEquals("parentorgid", org.getOrgid())).size() > 0 ||
									omOrganizationRServiceExt.loadEmpByOrg(org.getOrgid().toString()).size() > 0) {
								orgTree.setChildren(true);
							}
							orgTree.setIcon(JsTree.ORG_ICON);
							list.add(orgTree);
						}
					}
					
					List<OmEmployee> empList = omOrganizationRServiceExt.loadEmpByOrg(id);
					if (empList != null && empList.size() > 0) {
						for(OmEmployee emp : empList) {
							JsTree empTree = new JsTree();
							empTree.setId(id + "-" + emp.getEmpid().toString());
							empTree.setText(emp.getEmpname());
							empTree.setChildren(false);
							empTree.setIcon(JsTree.USER_ICON);
							empTree.setData(JsTree.TYPE_USER);
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
		} catch (Exception e) {// TODO
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
				logger.info("omOrganizationControllerExt save request " + content);
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
				Integer genOrgId = omOrganizationRServiceExt.genOrgId();
				if(p.getParentorgid() != null) {
					OmOrganization parOrg = omOrganizationRService.query(new WhereCondition()
							.andEquals("orgId", p.getParentorgid())).get(0);
					p.setOrglevel(parOrg.getOrglevel() + 1);
					p.setOrgseq(parOrg.getOrgseq() + "." + genOrgId );
					
				} else {
					p.setOrglevel(1);
					p.setOrgseq("." + genOrgId);
				}
				p.setOrgid(omOrganizationRServiceExt.genOrgId());
				
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
	 * 
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
			List list = omOrganizationRService.query(wc);
			JSONArray ja = JSONArray.fromObject(list,jsonConfig);
			page.setTotalItems(omOrganizationRService.count(wc));
			Map map = new HashMap();
			map.put("page", page);
			map.put("list", ja);
			String s=JSONObject.fromObject(map,jsonConfig).toString();
			AjaxUtils.ajaxJson(response,s );
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
			e.printStackTrace();
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
