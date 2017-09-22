package org.tis.tools.webapp.controller.ac;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.ac.AcOperatorConfig;
import org.tis.tools.rservice.ac.capable.IOperatorRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/AcOperatorConfig")
public class AcOperatorConfigController extends BaseController {

    @Autowired
    IOperatorRService operatorRService;


    @ResponseBody
    @RequestMapping(value="/queryOperatorConfig", produces ="application/json;charset=UTF-8", method= RequestMethod.POST)
    public Map<String, Object> queryOperatorConfig(@RequestBody String content) {
        List<AcOperatorConfig> acOperatorConfigs = operatorRService.queryOperatorConfigList(JSONObject.parseObject(content).getString("userId"));
        return getReturnMap(acOperatorConfigs);
    }

    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_LOGIN,
            operateDesc = "删除操作员个性化配置",
            retType = ReturnType.List,
            id = "guid",
            name = "configName",
            keys = {"guidOperator", "guidApp"}
    )
    @ResponseBody
    @RequestMapping(value="/deleteOperatorConfig", produces ="application/json;charset=UTF-8", method= RequestMethod.POST)
    public Map<String, Object> deleteOperatorConfig(@RequestBody String content) {
        List<AcOperatorConfig> operatorConfigs = JSON.parseArray(content, AcOperatorConfig.class);
        return getReturnMap(operatorRService.deleteOperatorConfig(operatorConfigs));
    }

    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_LOGIN,
            operateDesc = "新增操作员个性化配置",
            retType = ReturnType.Object,
            id = "guid",
            name = "configName",
            keys = {"guidOperator", "guidApp"}
    )
    @ResponseBody
    @RequestMapping(value="/addOperatorConfig", produces ="application/json;charset=UTF-8", method= RequestMethod.POST)
    public Map<String, Object> addOperatorConfig(@RequestBody String content) {
        return getReturnMap(operatorRService.addOperatorConfig(JSONObject.parseObject(content, AcOperatorConfig.class)));
    }

    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_LOGIN,
            operateDesc = "修改操作员个性化配置",
            retType = ReturnType.Object,
            id = "guid",
            name = "configName",
            keys = {"guidOperator", "guidApp"}
    )
    @ResponseBody
    @RequestMapping(value="/updateOperatorConfig", produces ="application/json;charset=UTF-8", method= RequestMethod.POST)
    public Map<String, Object> updateOperatorConfig(@RequestBody String content) {
        return getReturnMap(operatorRService.updateOperatorConfig(JSONObject.parseObject(content, AcOperatorConfig.class)));
    }
}
