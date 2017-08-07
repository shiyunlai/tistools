package org.tis.tools.webapp.controller.abf;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.sys.SysDictItem;
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
                //TODO
            }
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
}
