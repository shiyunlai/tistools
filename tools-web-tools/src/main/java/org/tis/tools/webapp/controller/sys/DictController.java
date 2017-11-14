package org.tis.tools.webapp.controller.sys;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.ExcelReader;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.sys.capable.IDictRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
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
     */
    @ResponseBody
    @RequestMapping(value="/querySysDictList" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> querySysDictList(@RequestBody String content){
        JSONObject jsonObject= JSONObject.parseObject(content);
        String isQueryRoot = jsonObject.getString("isQueryRoot");
        return getReturnMap(dictRService.querySysDicts(isQueryRoot)) ;
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
    @RequestMapping(value="/querySysDictItemList" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
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
     * 查询所有字典项
     *
     */
    @ResponseBody
    @RequestMapping(value="/queryAllDictItem" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryAllDictItem(@RequestBody String content, HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryDictItemListByDictKey request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            List<SysDictItem> sysDictItems = dictRService.querySysDictItemList();
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
     * 导出所有业务字典为excel
     *
     */
    @RequestMapping(value="/exportDictExcel",method= RequestMethod.GET)
    public void exportDictExcel(HttpServletRequest request, HttpServletResponse response) {
        OutputStream out = null;
        try {
            if (logger.isInfoEnabled()) {
                logger.info("exportDictExcel request ");
            }
            // 生成提示信息，
            response.setContentType("application/vnd.ms-excel");
            // 进行转码，使其支持中文文件名
            String codedFileName = URLEncoder.encode("业务字典数据", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + codedFileName + ".xlsx");

            List<SysDict> sysDicts = dictRService.querySysDicts(null);
            List<SysDictItem> sysDictItems = dictRService.querySysDictItemList();

            XSSFWorkbook wb = new XSSFWorkbook();  //--->创建了一个excel文件
            XSSFSheet sheet = wb.createSheet("业务字典");   //--->创建了一个工作簿

            sheet.setColumnWidth((short)0, 20* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet.setColumnWidth((short)1, 30* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet.setColumnWidth((short)2, 10* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet.setColumnWidth((short)3, 20* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet.setColumnWidth((short)4, 10* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)5, 50* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)6, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)7, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)8, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)9, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)10, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)11, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setColumnWidth((short)12, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet.setDefaultRowHeight((short)300);    // ---->设置统一单元格的高度，就用这个方法

            //表格第一行
            XSSFRow row1 = sheet.createRow(0);   //--->创建一行
            String[] columnList = {
                    SysDict.COLUMN_GUID, SysDict.COLUMN_DICT_KEY, SysDict.COLUMN_DICT_TYPE, SysDict.COLUMN_DICT_NAME,
                    SysDict.COLUMN_FROM_TYPE, SysDict.COLUMN_DICT_DESC, SysDict.COLUMN_GUID_PARENTS, SysDict.COLUMN_DEFAULT_VALUE,
                    SysDict.COLUMN_FROM_TABLE, SysDict.COLUMN_USE_FOR_KEY, SysDict.COLUMN_USE_FOR_NAME, SysDict.COLUMN_SQL_FILTER,
                    SysDict.COLUMN_SEQNO };
            for (short i = 0; i < columnList.length; i++) {
                row1.createCell(i).setCellValue(columnList[i]);//--->创建单元格
            }
            int j = 1;
            for (SysDict dict : sysDicts) {
                XSSFRow row = sheet.createRow(j);   //--->创建一行
                row.createCell(0).setCellValue(dict.getGuid());
                row.createCell(1).setCellValue(dict.getDictKey());
                row.createCell(2).setCellValue(dict.getDictType());
                row.createCell(3).setCellValue(dict.getDictName());
                row.createCell(4).setCellValue(dict.getFromType());
                row.createCell(5).setCellValue(dict.getDictDesc());
                row.createCell(6).setCellValue(dict.getGuidParents());
                row.createCell(7).setCellValue(dict.getDefaultValue());
                row.createCell(8).setCellValue(dict.getFromTable());
                row.createCell(9).setCellValue(dict.getUseForKey());
                row.createCell(10).setCellValue(dict.getUseForName());
                row.createCell(11).setCellValue(dict.getSqlFilter());
                row.createCell(12).setCellValue(dict.getSeqno().toString());
                j++;
            }

            XSSFSheet sheet1 = wb.createSheet("业务字典项");   //--->创建了一个工作簿

            sheet1.setColumnWidth((short)0, 30* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet1.setColumnWidth((short)1, 30* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet1.setColumnWidth((short)2, 60* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet1.setColumnWidth((short)3, 20* 256);    //---》设置单元格宽度，因为一个单元格宽度定了那么下面多有的单元格高度都确定了所以这个方法是sheet的
            sheet1.setColumnWidth((short)4, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet1.setColumnWidth((short)5, 20* 256);    //--->第一个参数是指哪个单元格，第二个参数是单元格的宽度
            sheet1.setDefaultRowHeight((short)300);    // ---->设置统一单元格的高度，就用这个方法

            //表格第一行
            XSSFRow row2 = sheet1.createRow(0);   //--->创建一行
            String[] columnList1 = {
                    SysDictItem.COLUMN_GUID, SysDictItem.COLUMN_GUID_DICT, SysDictItem.COLUMN_ITEM_NAME,
                    SysDictItem.COLUMN_ITEM_VALUE, SysDictItem.COLUMN_SEND_VALUE, SysDictItem.COLUMN_SEQNO};
            for (short i = 0; i < columnList1.length; i++) {
                row2.createCell(i).setCellValue(columnList1[i]);//--->创建单元格
            }
            int k = 1;
            for (SysDictItem dict : sysDictItems) {
                XSSFRow row = sheet1.createRow(k);   //--->创建一行
                row.createCell(0).setCellValue(dict.getGuid());
                row.createCell(1).setCellValue(dict.getGuidDict());
                row.createCell(2).setCellValue(dict.getItemName());
                row.createCell(3).setCellValue(dict.getItemValue());
                row.createCell(4).setCellValue(dict.getSendValue());
                row.createCell(5).setCellValue(dict.getSeqno().toString());
                k ++;
            }
            out = response.getOutputStream();
            wb.write(out);
            //样式1
//            XSSFCellStyle style = wb.createCellStyle(); // 样式对象
//            style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);// 垂直
//            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 水平
//            //设置标题字体格式
//            Font font = wb.createFont();
//            //设置字体样式
//            font.setFontHeightInPoints((short)20);   //--->设置字体大小
//            font.setFontName("Courier New");   //---》设置字体，是什么类型例如：宋体
//            font.setItalic(true);     //--->设置是否是加粗
//
//            XSSFCellStyle style1 = wb.createCellStyle(); // 样式对象
//            style1.setFont(font);     //--->将字体格式加入到style1中
//
//
//            XSSFCell cell1 = row1.createCell((short)0);   //--->创建一个单元格
//            cell1.setCellStyle(style);
//            cell1.setCellValue("");

        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("exportDictExcel exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("exportDictExcel exception : ", e);
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {}
        }
    }

    
    
    /**
     * 查询业务字典对应树结构
     */
    @RequestMapping(value="/queryDictTree" ,method=RequestMethod.POST)
    public String queryDictTree(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryDictTree request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);
            String id = jsonObj.getString("id");
            String dictKey = jsonObj.getString("dictKey");
            //通过id判断需要加载的节点

            if("#".equals(id)){
            	SysDict dict = dictRService.queryDict(dictKey);
                Map map=new HashMap();
                map.put("rootName", dict.getDictName());
                map.put("itemType", "dict");
                map.put("itemValue", dictKey);	
                map.put("defaultValue", dict.getDefaultValue());
                map.put("dictguid", dict.getGuid());
                result.put("data", map);//返回给前台的数据
            } else {
                List<SysDictItem> sysDictItems = dictRService.queryDictItemListByDictKey(dictKey);
                result.put("data", sysDictItems);//返回给前台的数据
            }
            AjaxUtils.ajaxJsonSuccessMessage(response, result.get("data"));
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryDictTree exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryDictTree exception : ", e);
        }
        return null;
    }

    
    /**
     * 根据key查询业务字典信息
     *
     */
    @ResponseBody
    @RequestMapping(value="/queryDict" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryDict(@RequestBody String content, HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryDict request : " + content);
            }
            
            JSONObject jsonObject= JSONObject.parseObject(content);
            String dictKey = jsonObject.getString("dictKey");
            SysDict dict = dictRService.queryDict(dictKey);
            AjaxUtils.ajaxJsonSuccessMessage(response,dict);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryDict exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryDict exception : ", e);
        }
        return null;
    }

    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "设置字典默认字典项",
            retType = ReturnType.Object,
            id = "dictKey",
            name = "dictName",
            keys = "defaultValue"
    )
    /**
     * 修改字典项默认值
     *
     */
    @ResponseBody
    @RequestMapping(value="/setDefaultDictValue" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> setDefaultDictValue(@RequestBody String content) {
        JSONObject obj = JSONObject.parseObject(content);
        String dictKey = obj.getString("dictKey");
        String itemValue = obj.getString("itemValue");
        return getReturnMap(dictRService.setDefaultDictValue(dictKey, itemValue));
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
