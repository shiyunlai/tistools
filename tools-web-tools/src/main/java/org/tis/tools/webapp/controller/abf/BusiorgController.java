package org.tis.tools.webapp.controller.abf;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.om.OmBusiorg;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.om.capable.IBusiOrgRService;
import org.tis.tools.rservice.om.capable.IOrgRService;
import org.tis.tools.rservice.sys.capable.IDictRService;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务机构功能
 *
 * @author
 */
@Controller
@RequestMapping(value = "/om/busiorg")
public class BusiorgController {
    @Autowired
    IDictRService dictRService;
    @Autowired
    IBusiOrgRService busiOrgRService;
    @Autowired
    IOrgRService orgRService;




    /**
     * 展示业务机构树
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/busitree")
    public String busitree(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String id = jsonObj.getString("id");

            if("#".equals(id)) {
                Map map = new HashMap<>();
                map.put("text", "业务机构树");
                map.put("id", "00000");
                List<Map> list = new ArrayList<>();
                list.add(map);
                AjaxUtils.ajaxJsonSuccessMessage(response, list);
            }else if("00000".equals(id)){
                List<SysDictItem> list = dictRService.queryDictItemListByDictKey("DICT_OM_BUSIDOMAIN");
                AjaxUtils.ajaxJsonSuccessMessage(response, list);
            }else if(id.length() == 3){
                List<OmBusiorg> list = busiOrgRService.queryRootBusiorgByDomain(id);
                AjaxUtils.ajaxJsonSuccessMessage(response, list);
            }else {
                List<OmBusiorg> list = busiOrgRService.queryChildBusiorgByCode(id);
                AjaxUtils.ajaxJsonSuccessMessage(response, list);
            }
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());

        }
        return null;
    }

    /**
     * 展示业务机构筛选树
     *
     * @param model
     * @param content
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/searchtree")
    public String searchtree(ModelMap model, @RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String id = jsonObj.getString("id");
            String busiorgName = jsonObj.getString("searchitem");
            List<OmBusiorg> list = busiOrgRService.queryBusiorgByName(busiorgName);
            AjaxUtils.ajaxJsonSuccessMessage(response, list);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());

        }
        return null;
    }

    @RequestMapping(value = "/busidomain")
    public String busidomain(ModelMap model,HttpServletRequest request,
                          HttpServletResponse response) {
        try{
            //收到请求,加载所有业务条线
            List<SysDictItem> list = dictRService.queryDictItemListByDictKey("DICT_OM_BUSIDOMAIN");
            AjaxUtils.ajaxJsonSuccessMessage(response, list);
        }catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/initCode")
    public String initCode(ModelMap model,@RequestBody String content,HttpServletRequest request,
                             HttpServletResponse response) {
        try{
            JSONObject jsonObject = JSONObject.parseObject(content);
            String nodeType = jsonObject.getString("nodeType");
            String busiDomain = jsonObject.getString("busiDomain");
            String busiOrgCode = busiOrgRService.genBusiorgCode(nodeType, busiDomain);
            AjaxUtils.ajaxJsonSuccessMessage(response,busiOrgCode);
        }catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/addbusiorg")
    public String addbusiorg(ModelMap model,@RequestBody String content,HttpServletRequest request,
                           HttpServletResponse response) {
        try{
        	JSONObject jsonObject = JSONObject.parseObject(content);
            String busiorgCode = jsonObject.getString("busiorgCode");
            String busiorgName = jsonObject.getString("busiorgName");
            String busiDomain = jsonObject.getString("busiDomain");
            String guidOrg = jsonObject.getString("guidOrg");
            String orgCode = "";
            List<OmOrg> list = orgRService.queryAllOrg();
            for(OmOrg org : list){
                if(org.getGuid().equals(guidOrg)){
                    orgCode = org.getOrgCode();
                }
            }
            String parentsBusiorgCode = jsonObject.getString("parentsBusiorgCode");
            String nodeType = jsonObject.getString("nodeType");
            OmBusiorg ob = new OmBusiorg();
            if("reality".equals(nodeType)){
                busiOrgRService.createRealityBusiorg(busiorgCode, busiorgName, orgCode, busiDomain, parentsBusiorgCode);
            }else if("dummy".equals(nodeType)){
                busiOrgRService.createDummyBusiorg(busiorgCode, busiorgName, busiDomain, parentsBusiorgCode);
            }

            AjaxUtils.ajaxJsonSuccessMessage(response,"新增成功!");
        }catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 生成下级业务机构列表
     * @param model
     * @param content
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loadbusiorgbyType")
    public String loadbusiorgbyType(ModelMap model,@RequestBody String content,HttpServletRequest request,
                             HttpServletResponse response) {
        try{
            JSONObject jsonObject = JSONObject.parseObject(content);
            String busiDomain = jsonObject.getString("busiDomain");
            List<OmBusiorg> list = busiOrgRService.queryAllBusiorgByDomain(busiDomain);
            AjaxUtils.ajaxJsonSuccessMessage(response,list);
        }catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 删除业务机构,删除当前节点和所有子节点
     * @param model
     * @param content
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deletebusiorg")
    public String deletebusiorg(ModelMap model,@RequestBody String content,HttpServletRequest request,
                                    HttpServletResponse response) {
        try{
            JSONObject jsonObject = JSONObject.parseObject(content);
            String busiorgCode = jsonObject.getString("busiorgCode");
            busiOrgRService.deleteBusiorg(busiorgCode);
            AjaxUtils.ajaxJsonSuccessMessage(response,"删除成功");
        }catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 删除业务机构,删除当前节点和所有子节点
     * @param model
     * @param content
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updatebusiorg")
    public String updatebusiorg(ModelMap model,@RequestBody String content,HttpServletRequest request,
                                HttpServletResponse response) {
        try{
            OmBusiorg obg = JSONObject.parseObject(content, OmBusiorg.class);
            busiOrgRService.updateBusiorg(obg);
            AjaxUtils.ajaxJsonSuccessMessage(response,"更新成功!");
        }catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }
}
