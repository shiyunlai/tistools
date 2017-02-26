package org.tis.tools.webapp.controller.torg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.torg.OmBusiorg;
import org.tis.tools.rservice.torg.IOmBusiorgRService;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;
import org.tis.tools.webapp.JsTree;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import com.alibaba.dubbo.config.annotation.Reference;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <pre>
 * 业务域torg中模型OM_BUSIORG的控制器
 * 获取树值专用控制器
 * </pre>
 */
@Controller
@RequestMapping(value = "/torgtree")
public class OmBusiorgTreeController extends BaseController{
	@Autowired
	IOmBusiorgRService omBusiorgRService;
	public static final String USER_PLUS_ICON = "fa fa-user-plus icon-state-success";//新增人员图标
	public static final String USER_ICON = "fa fa-user icon-state-danger";//人员图标
	public static final String ROOT_TREE_ICON = "fa fa-sitemap icon-state-success";//树根节点图标
	public static final String ORG_ICON = "fa fa-building-o icon-state-success";//机构图标
	public static final String POSITION_ICON = "fa user—group icon-state-success";//岗位图标
	
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
					jt.setText("业务机构");
					jt.setIcon(ROOT_TREE_ICON);
					jt.setChildren(true);
					list.add(jt);
				} else if("root".equals(id)) {
					List<OmBusiorg> rootList = omBusiorgRService
							.query(new WhereCondition()
									.andEquals("orglevel", "1" ));
					if (rootList != null && rootList.size() > 0) {
						for(OmBusiorg org : rootList) {
							JsTree orgTree = new JsTree();
							orgTree.setId(org.getOrgid().toString());
							orgTree.setText(org.getOrgname());
							orgTree.setIcon(ORG_ICON);
							if (omBusiorgRService.query(new WhereCondition()
									.andEquals("parentid", org.getOrgid())).size() > 0) {
								orgTree.setChildren(true);
							}
							list.add(orgTree);
						}
					}
				}
				else {
					List<OmBusiorg> childList = omBusiorgRService
							.query(new WhereCondition()
									.andEquals("parentid", id ));
					if (childList != null && childList.size() > 0) {
						for(OmBusiorg org : childList) {
							JsTree orgTree = new JsTree();
							orgTree.setId(org.getOrgid().toString());
							orgTree.setText(org.getOrgname());
							if (omBusiorgRService.query(new WhereCondition()
									.andEquals("parentid", org.getOrgid())).size() > 0) {
								orgTree.setChildren(true);
							}
							orgTree.setIcon(ORG_ICON);
							list.add(orgTree);
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
	
	
	/**
	 * 每个controller定义自己的返回信息变量
	 */
	private Map<String, Object> responseMsg ;
	@Override
	public Map<String, Object> getResponseMessage() {
		if( null == responseMsg ){
			responseMsg = new HashMap<String, Object> () ;
		}
		return responseMsg ;
	}

}
