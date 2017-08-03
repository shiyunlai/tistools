//package org.tis.tools.webapp.controller.ac;
//
//import com.alibaba.dubbo.common.json.ParseException;
//import com.alibaba.fastjson.JSONObject;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.tis.tools.model.po.ac.AcOperatorIdentity;
//import org.tis.tools.base.exception.ToolsRuntimeException;
//import org.tis.tools.model.po.ac.AcOperator;
//import org.tis.tools.model.po.ac.AcOperatorIdentityres;
//import org.tis.tools.rservice.ac.capable.IOperatorRService;
//import org.tis.tools.webapp.controller.BaseController;
//import org.tis.tools.webapp.util.AjaxUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping("/AcOperatorController")
//public class AcOperatorController extends BaseController {
//
//    private Map<String, Object> responseMsg ;
//
//    @Autowired
//    IOperatorRService operatorRService;
//
//    /**
//     * 查询操作员列表
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/queryAllOperator" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String queryAllOperator(@RequestBody String content, HttpServletRequest request,
//                                HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("queryAllOperator request : " + content);
//            }
//            List<AcOperator> acOperators = operatorRService.queryOperators();
//            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response,acOperators, "YYYY-MM-dd");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("queryAllOperator exception : ", e);
//        } catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("queryAllOperator exception : ", e);
//        }
//        return null;
//    }
//    /**
//     * 新增操作员
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/createOperator" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String createOperator(@RequestBody String content, HttpServletRequest request,
//                                  HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("createOperator request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            AcOperator acOperator = new AcOperator();
//            BeanUtils.populate(acOperator, jsonObject);
//            operatorRService.createOperator(acOperator);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("createOperator exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("createOperator exception : ", e);
//        }
//        return null;
//    }
//    /**
//     * 修改操作员
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/editOperator" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String editOperator(@RequestBody String content, HttpServletRequest request,
//                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("editOperator request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            AcOperator acOperator = new AcOperator();
//            BeanUtils.populate(acOperator, jsonObject);
//            operatorRService.editOperator(acOperator);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("editOperator exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("editOperator exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 设置默认操作员身份
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/setDefaultOperatorIdentity" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String setDefaultOperator(@RequestBody String content, HttpServletRequest request,
//                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("setDefaultOperatorIdentity request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            String identityGuid = jsonObject.getString("identityGuid");
//            operatorRService.setDefaultOperatorIdentity(identityGuid);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("setDefaultOperatorIdentity exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("setDefaultOperatorIdentity exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 查询操作员身份列表
//     *     传入操作员GUID
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/queryAllOperatorIdentity" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String queryAllOperatorIdentity(@RequestBody String content, HttpServletRequest request,
//                                   HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("queryAllOperatorIdentity request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            String userId = jsonObject.getString("userId");
//            String operatorName = jsonObject.getString("operatorName");
//            List<AcOperatorIdentity> acOperatorIdentities = operatorRService.queryOperatorIdentitiesByUserIdAndName(userId, operatorName);
//            AjaxUtils.ajaxJsonSuccessMessage(response,acOperatorIdentities);
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("queryAllOperatorIdentity exception : ", e);
//        } catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("queryAllOperatorIdentity exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 新增操作员身份
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/createOperatorIdentity" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String createOperatorIdentity(@RequestBody String content, HttpServletRequest request,
//                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("createOperatorIdentity request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            AcOperatorIdentity acOperatorIdentity = new AcOperatorIdentity();
//            BeanUtils.populate(acOperatorIdentity, jsonObject);
//            operatorRService.createOperatorIdentity(acOperatorIdentity);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("createOperatorIdentity exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("createOperatorIdentity exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 删除操作员身份
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/deleteOperatorIdentity" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String deleteMenu(@RequestBody String content, HttpServletRequest request,
//                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("deleteOperatorIdentity request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            String identityGuid = jsonObject.getString("identityGuid");//所属身份GUID
//            operatorRService.deleteOperatorIdentity(identityGuid);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("deleteOperatorIdentity exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("deleteOperatorIdentity exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 修改操作员身份
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/editOperatorIdentity" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String editOperatorIdentity(@RequestBody String content, HttpServletRequest request,
//                               HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("editOperatorIdentity request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            AcOperatorIdentity acOperatorIdentity = new AcOperatorIdentity();
//            BeanUtils.populate(acOperatorIdentity, jsonObject);
//            operatorRService.editOperatorIdentity(acOperatorIdentity);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("editOperatorIdentity exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("editOperatorIdentity exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 查询操作员身份权限集列表
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/queryAllOperatorIdentityRes" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String queryAllOperatorIdentityRes(@RequestBody String content, HttpServletRequest request,
//                                   HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("queryAllOperatorIdentityRes request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            String identityGuid = jsonObject.getString("identityGuid");
//            List<AcOperatorIdentityres> acOperatorIdentityreses = operatorRService.queryOperatorIdentityreses(identityGuid);
//            AjaxUtils.ajaxJsonSuccessMessage(response,acOperatorIdentityreses);
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("queryAllOperatorIdentityRes exception : ", e);
//        } catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("queryAllOperatorIdentityRes exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 新增操作员身份权限
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/createOperatorIdentityres" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String createOperatorIdentityres(@RequestBody String content, HttpServletRequest request,
//                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("createOperatorIdentityres request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            AcOperatorIdentityres acOperatorIdentityres = new AcOperatorIdentityres();
//            BeanUtils.populate(acOperatorIdentityres, jsonObject);
//            operatorRService.createOperatorIdentityres(acOperatorIdentityres);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("createOperatorIdentityres exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("createOperatorIdentityres exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 删除操作员身份
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/deleteOperatorIdentityres" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String deleteOperatorIdentityres(@RequestBody String content, HttpServletRequest request,
//                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("deleteOperatorIdentityres request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            String identityresGuid = jsonObject.getString("identityresGuid");//所属应用GUID
//            operatorRService.deleteOperatorIdentityres(identityresGuid);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("deleteOperatorIdentityres exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("deleteOperatorIdentityres exception : ", e);
//        }
//        return null;
//    }
//
//    /**
//     * 修改操作员身份权限
//     * @param content
//     * @param request
//     * @param response
//     * @return
//     * @throws ToolsRuntimeException
//     * @throws ParseException
//     */
//    @ResponseBody
//    @RequestMapping(value="/editOperatorIdentityres" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
//    public String editOperatorIdentityres(@RequestBody String content, HttpServletRequest request,
//                               HttpServletResponse response) throws ToolsRuntimeException, ParseException {
//        try {
//            if (logger.isInfoEnabled()) {
//                logger.info("editOperatorIdentityres request : " + content);
//            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            AcOperatorIdentityres acOperatorIdentityres = new AcOperatorIdentityres();
//            BeanUtils.populate(acOperatorIdentityres, jsonObject);
//            operatorRService.editOperatorIdentityres(acOperatorIdentityres);
//            AjaxUtils.ajaxJsonSuccessMessage(response,"");
//        } catch (ToolsRuntimeException e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
//            logger.error("editOperatorIdentityres exception : ", e);
//        }catch (Exception e) {
//            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
//            logger.error("editOperatorIdentityres exception : ", e);
//        }
//        return null;
//    }
//
//
//
//
//
//    /**
//     * 要求子类构造自己的响应数据
//     *
//     * @return
//     */
//    @Override
//    public Map<String, Object> getResponseMessage() {
//        if( null == responseMsg ){
//            responseMsg = new HashMap<String, Object>() ;
//        }
//        return responseMsg;
//    }
//}
