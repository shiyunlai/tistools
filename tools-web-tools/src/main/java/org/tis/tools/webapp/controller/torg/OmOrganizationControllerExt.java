package org.tis.tools.webapp.controller.torg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmOrganization;
import org.tis.tools.rservice.torg.IOmOrganizationRService;
import org.tis.tools.webapp.JsTree;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/torg")
public class OmOrganizationControllerExt extends BaseController {
	
	public static final String USER_PLUS_ICON = "fa fa-user-plus icon-state-success";//新增人员图标
	public static final String USER_ICON = "fa fa-user icon-state-danger";//人员图标
	public static final String ROOT_TREE_ICON = "fa fa-sitemap icon-state-success";//树根节点图标
	public static final String ORG_ICON = "fa fa-building-o icon-state-success";//机构图标
	public static final String POSITION_ICON = "fa user—group icon-state-success";//岗位图标
	@Autowired
	IOmOrganizationRService omOrganizationRService;
	
	@ResponseBody
	@RequestMapping(value="/treeList",method=RequestMethod.GET)
	public String testGetController(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("testGetController request " );
			}
			String id = request.getParameter("id");
			List<JsTree> list = new ArrayList<>();			
			if(id != null) {
				if("#".equals(id)) {
					JsTree jt = new JsTree();
					jt.setId("root");
					jt.setText("机构人员树");
					jt.setIcon(ROOT_TREE_ICON);
					jt.setChildren(true);
					list.add(jt);
				} else if("root".equals(id)) {
					List<OmOrganization> rootList = omOrganizationRService
							.query(new WhereCondition()
									.andEquals("orglevel", 1 ));
					if (rootList != null && rootList.size() > 0) {
						for(OmOrganization org : rootList) {
							JsTree jsTree = new JsTree();
							jsTree.setId(org.getOrgid().toString());
							jsTree.setText(org.getOrgname());
							jsTree.setIcon(ORG_ICON);
							if (omOrganizationRService.query(new WhereCondition()
									.andEquals("parentorgid", org.getOrgid())).size() > 0) {
								jsTree.setChildren(true);
							}
							list.add(jsTree);
						}
					}
				}
				else {
					List<OmOrganization> childList = omOrganizationRService
							.query(new WhereCondition()
									.andEquals("parentorgid", id ));
					if (childList != null && childList.size() > 0) {
						for(OmOrganization org : childList) {
							JsTree jsTree = new JsTree();
							jsTree.setId(org.getOrgid().toString());
							jsTree.setText(org.getOrgname());
							if (omOrganizationRService.query(new WhereCondition()
									.andEquals("parentorgid", org.getOrgid())).size() > 0) {
								jsTree.setChildren(true);
							}
							jsTree.setIcon(ORG_ICON);
							list.add(jsTree);
						}
					}
				}
			}
			result.put("list", list);
			AjaxUtils.ajaxJson(response,JSONArray.fromObject(list,jsonConfig).toString() );
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "测试异常!");
			logger.error("testGetController exception : ", e);
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
