package org.tis.tools.webapp.controller.sys;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.ExcelReader;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.sys.capable.IDictRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

/**
 * Created by zhaoch on 2017/7/18.
 */
@Controller
@RequestMapping("/DictController")
public class DictController extends BaseController {

    private Map<String, Object> responseMsg ;

    @Autowired
    IDictRService dictRService;

    @RequestMapping("importDict")
    public void importDict(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("importDict request : import dict data from" + file.getName());
            }
            String filename = file.getOriginalFilename();
            InputStream inputStream = file.getInputStream();
            ExcelReader excelReader = new ExcelReader(filename, inputStream);
           
            // 对读取Excel表格内容测试
            Map<Integer, Map<Integer, Object>> map = excelReader.readExcelContent("业务字典");

            // 遍历结果集,逐条插入
            for (int i = 1; i <= map.size(); i++) {
                Map rs = map.get(i);
                if (rs.get(0) != null && rs.get(0) != "") {
                    SysDict sysDict = new SysDict();
                    sysDict.setDictName(rs.get(1).toString());
                    sysDict.setDictKey(rs.get(0).toString());
                    sysDict.setDictType(rs.get(5).toString());
                    sysDict.setDictDesc(rs.get(2).toString());

                    SysDict retSysdict = dictRService.addDict(sysDict);
                    Map<Integer, Map<Integer, Object>> map2 = excelReader.readExcelContent2(sysDict.getDictKey());
                    for(int k = 3;k <= map2.size(); k++){
                        SysDictItem dictItem = new SysDictItem();
                        Map rs2 = map2.get(k);
                        if(rs2.get(0) != null && rs2.get(0) != "") {
                            dictItem.setItemName(rs2.get(2).toString());
                            dictItem.setItemValue(rs2.get(0).toString());
                            dictItem.setSendValue(rs2.get(1).toString());
                            dictItem.setGuidDict(retSysdict.getGuid());
                            dictRService.addDictItem(dictItem);
                        }
                    }
                }
            }
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("appAdd exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("appAdd exception : ", e);
        }

    }

    /**
     * 新增业务字典
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/createSysDict" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createSysDict(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createSysDict request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            SysDict sysDict = new SysDict();
            BeanUtils.populate(sysDict, jsonObject);
            SysDict retDict = dictRService.addDict(sysDict);
            AjaxUtils.ajaxJsonSuccessMessage(response,retDict);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createSysDict exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createSysDict exception : ", e);
        }
        return null;
    }

    /**
     * 删除业务字典
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/deleteSysDict" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String deleteSysDict(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteSysDict request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String dictGuid = jsonObject.getString("dictGuid");
            dictRService.deleteDict(dictGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteSysDict exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteSysDict exception : ", e);
        }
        return null;
    }

    /**
     * 修改业务字典
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/editSysDict" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String editSysDict(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editSysDict request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            SysDict sysDict = new SysDict();
            BeanUtils.populate(sysDict, jsonObject);
            dictRService.editSysDict(sysDict);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editSysDict exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editSysDict exception : ", e);
        }
        return null;
    }

    /**
     * 查询单个业务字典
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/querySysDict" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String querySysDict(@RequestBody String content, HttpServletRequest request,
                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("querySysDict request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String dictGuid = jsonObject.getString("dictGuid");
            SysDict sysDict = dictRService.querySysDictByGuid(dictGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,sysDict);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("querySysDict exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("querySysDict exception : ", e);
        }
        return null;
    }

    /**
     * 新增业务字典项
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/createSysDictItem" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createSysDictItem(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createSysDictItem request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            SysDictItem sysDictItem = new SysDictItem();
            BeanUtils.populate(sysDictItem, jsonObject);
            SysDictItem retDictItem = dictRService.addDictItem(sysDictItem);
            AjaxUtils.ajaxJsonSuccessMessage(response,retDictItem);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createSysDict exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createSysDict exception : ", e);
        }
        return null;
    }

    /**
     * 删除业务字典项
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/deleteSysDictItem" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String deleteSysDictItem(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteSysDictItem request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String dictItemGuid = jsonObject.getString("dictItemGuid");
            dictRService.deleteDictItem(dictItemGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteSysDictItem exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteSysDictItem exception : ", e);
        }
        return null;
    }

    /**
     * 修改业务字典项
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/editSysDictItem" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String editSysDictItem(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editSysDictItem request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            SysDictItem sysDictItem = new SysDictItem();
            BeanUtils.populate(sysDictItem, jsonObject);
            dictRService.editSysDictItem(sysDictItem);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editSysDictItem exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editSysDictItem exception : ", e);
        }
        return null;
    }

    /**
     * 查询单个业务字典项
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/querySysDictItem" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String querySysDictItem(@RequestBody String content, HttpServletRequest request,
                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("querySysDictItem request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String dictItemGuid = jsonObject.getString("dictItemGuid");
            SysDictItem sysDict = dictRService.querySysDictItemByGuid(dictItemGuid );
            AjaxUtils.ajaxJsonSuccessMessage(response,sysDict);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("querySysDictItem exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("querySysDictItem exception : ", e);
        }
        return null;
    }
    
    
    /**
     * 查询所有业务字典
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/querySysDictList" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String querySysDictList(@RequestBody String content, HttpServletRequest request,
                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("querySysDictList request : " + content);
            }
            List<SysDict> sysDicts = dictRService.querySysDicts();
            AjaxUtils.ajaxJsonSuccessMessage(response,sysDicts);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("querySysDictList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("querySysDictList exception : ", e);
        }
        return null;
    }

    /**
     * 根据业务字典的guid查询对应所有字典项
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/querySysDictItemList" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String querySysDictItemList(@RequestBody String content, HttpServletRequest request,
                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("querySysDictItemList request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String dictGuid = jsonObject.getString("dictGuid");

            List<SysDictItem> sysDictItems = dictRService.querySysDictItems(dictGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,sysDictItems);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("querySysDictItemList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("querySysDictItemList exception : ", e);
        }
        return null;
    }
    
    
    /**
     * 根据key查询业务字典项列表
     *
     */
    @ResponseBody
    @RequestMapping(value="/queryDictItemListByDictKey" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryDictItemListByDictKey(@RequestBody String content, HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryDictItemListByDictKey request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String dictKey = jsonObject.getString("dictKey");

            List<SysDictItem> sysDictItems = dictRService.queryDictItemListByDictKey(dictKey);
            AjaxUtils.ajaxJsonSuccessMessage(response,sysDictItems);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryDictItemListByDictKey exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryDictItemListByDictKey exception : ", e);
        }
        return null;
    }


    /**
     * 要求子类构造自己的响应数据
     *
     * @return
     */
    @Override
    public Map<String, Object> getResponseMessage() {
        if( null == responseMsg ){
            responseMsg = new HashMap<String, Object>() ;
        }
        return responseMsg;
    }
}
