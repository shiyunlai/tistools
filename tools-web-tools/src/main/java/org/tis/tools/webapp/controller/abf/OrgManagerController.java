/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.webapp.controller.abf;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.rservice.ac.basic.IAcFuncRService;
import org.tis.tools.rservice.ac.capable.IRoleRService;
import org.tis.tools.rservice.om.capable.IEmployeeRService;
import org.tis.tools.rservice.om.capable.IOrgRService;
import org.tis.tools.rservice.om.capable.IPositionRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 机构管理功能
 *
 * @author
 */
@Controller
@RequestMapping(value = "/om/org")
public class OrgManagerController extends BaseController {

    @Autowired
    IOrgRService orgRService;
    @Autowired
    IPositionRService positionRService;
    @Autowired
    IEmployeeRService employeeRService;
    @Autowired
    IRoleRService roleRService;


    /**
     * 展示机构树
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/tree")
    public String execute(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String id = jsonObj.getString("id");
            String guidOrg = jsonObj.getString("guidOrg");
            String positionCode = jsonObj.getString("positionCode");
            List<OmOrg> rootOrgs = new ArrayList<OmOrg>();
            List<OmPosition> omp = new ArrayList<OmPosition>();
            // 通过id判断需要加载的节点
            if ("#".equals(id)) {
                // #:根
                OmOrg om = new OmOrg();
                om.setOrgName("组织机构");
                om.setOrgCode("99999");
                rootOrgs.add(om);
            } else if (id.equals("99999")) {
                // 加载根机构
                rootOrgs = orgRService.queryAllRoot();
            } else if (id.startsWith("GW")) {
                // TODO
                // 返回机构下岗位信息.根据id查询岗位信息并返回生成树节点.
                omp = positionRService.queryPositionByOrg(guidOrg, null);

            } else if (!id.startsWith("POSITION")) {
                // 加载子机构
                rootOrgs = orgRService.queryChilds(id);
                OmOrg og = new OmOrg();
                // 为每一个节点增加岗位信息节点
                og.setOrgName("岗位信息");
                og.setOrgCode("GW" + id);

                og.setGuid(guidOrg);

                rootOrgs.add(og);
            } else {
                omp = positionRService.queryChilds(positionCode);
            }

            if (rootOrgs == null || rootOrgs.isEmpty()) {
                AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, omp, "yyyy-MM-dd");
            } else {
                AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, rootOrgs, "yyyy-MM-dd");
            }

        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 展示筛选机构树
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/search")
    public String search(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                         HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String id = jsonObj.getString("id");
            String name = jsonObj.getString("searchitem");
            List<OmOrg> rootOrgs = new ArrayList<OmOrg>();
            List<OmPosition> omp = new ArrayList<OmPosition>();
            // 通过id判断需要加载的节点
            if ("#".equals(id)) {
                // 调用远程服务,#:根,筛选
                WhereCondition wc = new WhereCondition();
                wc.andEquals("GUID", name);
                rootOrgs = orgRService.queryOrgsByCondition(wc);
            } else if (id.startsWith("GW")) {
                // TODO
                // 返回机构下岗位信息.根据id查询岗位信息并返回生成树节点.
                omp = new ArrayList<OmPosition>();

            } else {
                rootOrgs = orgRService.queryChilds(id);
                OmOrg og = new OmOrg();
                // 为每一个节点增加岗位信息节点
                og.setOrgName("岗位信息");
                og.setOrgCode("GW" + id);
                rootOrgs.add(og);
            }
            if (rootOrgs == null || rootOrgs.isEmpty()) {
                AjaxUtils.ajaxJsonSuccessMessage(response, omp);
            } else {
                AjaxUtils.ajaxJsonSuccessMessage(response, rootOrgs);
            }

        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 新增机构综合.
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/add")
    public String execute1(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            Map map = jsonObj;
            String flag = map.get("flag").toString();
            Date startDate = null;
            Date endDate = null;
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            if (map.get("startDate") != null) {
                startDate = sf.parse(map.remove("startDate").toString());
            }
            if (map.get("endDate") != null) {
                endDate = sf.parse(map.remove("endDate").toString());
            }
            OmOrg og = new OmOrg();
            BeanUtils.populate(og, map);
            System.out.println(og);
            og.setEndDate(endDate);
            og.setStartDate(startDate);
            if (flag.equals("root")) {
                String orgCode = og.getOrgCode();
                String orgName = og.getOrgName();
                String orgType = og.getOrgType();
                String orgDegree = og.getOrgDegree();
                orgRService.createRootOrg(orgCode, orgName, orgType, orgDegree);
            } else {
                orgRService.createChildOrg(og);
            }

            AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/initcode")
    public String test(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                       HttpServletResponse response) {
        try {
            // Map<String, Object> result = new HashMap<String, Object>();
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgDegree = jsonObj.getString("orgDegree");
            String AREA = jsonObj.getString("AREA");
            String orgCode = orgRService.genOrgCode(AREA, orgDegree, null);
            // result.put("data", orgCode);
            AjaxUtils.ajaxJsonSuccessMessage(response, orgCode);
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 更新机构
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/updateOrg")
    public String updateOrg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            // Map<String, Object> result = new HashMap<String, Object>();
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            OmOrg og = new OmOrg();
            BeanUtils.populate(og, jsonObj);
            orgRService.updateOrg(og);
            AjaxUtils.ajaxJsonSuccessMessage(response, "更新成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 删除机构
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteOrg")
    public String deleteOrg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            // Map<String, Object> result = new HashMap<String, Object>();
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgCode = jsonObj.getString("orgCode");
            orgRService.deleteEmptyOrg(orgCode);
            AjaxUtils.ajaxJsonSuccessMessage(response, "删除成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 新增岗位信息
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addposit")
    public String addPosit(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            OmPosition op = new OmPosition();
            BeanUtils.populate(op, jsonObj);
            String guid = op.getGuid();
            if ("".equals(guid) || guid == null) {
                positionRService.createPosition(op);
                AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
            } else {
                positionRService.updatePosition(op);
                AjaxUtils.ajaxJsonSuccessMessage(response, "修改成功!");
            }

        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 机构下新增人员信息
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addemp")
    public String addemp(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                         HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            OmEmployee oe = new OmEmployee();
            BeanUtils.populate(oe, jsonObj);
//			employeeRService.createEmployee(oe);
            AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 拉取员工-机构关系表数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loadempbyorg")
    public String loadempbyorg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgGuid = jsonObj.getString("guid");
            List<OmEmployee> list = employeeRService.queryEmployeeByGuid(orgGuid);
            List l = new ArrayList<>();
            for (OmEmployee oe : list) {
                Map map = BeanUtils.describe(oe);
                l.add(map);
            }
            AjaxUtils.ajaxJsonSuccessMessage(response, l);
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 拉取不在当前机构下的员工列表,用于为该机构新添人员.
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loadempNotinorg")
    public String loadempNotinorg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                                  HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgGuid = jsonObj.getString("guid");
            List<OmEmployee> list = employeeRService.queryEmployeeNotinGuid(orgGuid);
            List l = new ArrayList<>();
            for (OmEmployee oe : list) {
                Map map = BeanUtils.describe(oe);
                l.add(map);
            }
            AjaxUtils.ajaxJsonSuccessMessage(response, l);
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 添加机构-人员关系表数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addEmpOrg")
    public String addEmpOrg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgGuid = jsonObj.getString("orgGuid");
            JSONArray array = jsonObj.getJSONArray("empGuidlist");
            for (int i = 0; i < array.size(); i++) {
                String empGuid = array.get(i).toString();
                employeeRService.insertEmpOrg(orgGuid, empGuid);
            }
            AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 删除机构-人员关系表数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteEmpOrg")
    public String deleteEmpOrg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgGuid = jsonObj.getString("orgGuid");
            JSONArray array = jsonObj.getJSONArray("empGuidlist");
            for (int i = 0; i < array.size(); i++) {
                String empGuid = array.get(i).toString();
                employeeRService.deleteEmpOrg(orgGuid, empGuid);
            }
            AjaxUtils.ajaxJsonSuccessMessage(response, "删除成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 加载下级机构列表数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loadxjjg")
    public String loadxjjg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgCode = jsonObj.getString("orgCode");
            List<OmOrg> list = orgRService.queryChilds(orgCode);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 加载岗位下员工数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loadEmpbyPosition")
    public String loadEmpbyPosition(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String positionCode = jsonObj.getString("positionCode");
            List<OmEmployee> list = positionRService.queryEmployee(positionCode);
            list = positionRService.queryEmployee(positionCode);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 加载不在岗位下员工数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loadempNotinposit")
    public String loadempNotinposit(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String positionCode = jsonObj.getString("positionCode");
            List<OmEmployee> list = positionRService.queryEmployeeNotin(positionCode);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 添加岗位-人员关系表数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/addEmpPosition")
    public String addEmpPosition(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posGuid = jsonObj.getString("posGuid");
            JSONArray array = jsonObj.getJSONArray("empGuidlist");
            for (int i = 0; i < array.size(); i++) {
                String empGuid = array.get(i).toString();
                employeeRService.insertEmpPosition(posGuid, empGuid);
            }
            AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 删除岗位-人员关系表数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deleteEmpPosition")
    public String deleteEmpPosition(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posGuid = jsonObj.getString("posGuid");
            JSONArray array = jsonObj.getJSONArray("empGuidlist");
            for (int i = 0; i < array.size(); i++) {
                String empGuid = array.get(i).toString();
                employeeRService.deleteEmpPosition(posGuid, empGuid);
            }
            AjaxUtils.ajaxJsonSuccessMessage(response, "删除成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 加载下级岗位数据
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/loadxjposit")
    public String loadxjposit(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posCode = jsonObj.getString("posCode");
            List<OmPosition> list = positionRService.queryChilds(posCode);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 启用-注销-停用机构
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/enableorg")
    public String enableorg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgCode = jsonObj.getString("orgCode");
            Date startDate = jsonObj.getDate("startDate");
            Date endDate = jsonObj.getDate("endDate");
            String flag = jsonObj.getString("flag");
            if ("0".equals(flag)) {
                OmOrg org = orgRService.enabledOrg(orgCode, startDate, endDate);
                AjaxUtils.ajaxJsonSuccessMessage(response, "启用成功!");
            } else if ("3".equals(flag)) {//停用
                orgRService.disabledOrg(orgCode);
                AjaxUtils.ajaxJsonSuccessMessage(response, "停用成功!");
            } else if ("1".equals(flag)) {//注销
                orgRService.cancelOrg(orgCode);
                AjaxUtils.ajaxJsonSuccessMessage(response, "注销成功!");
            } else if ("2".equals(flag)) {//重新启用
                orgRService.reenabledOrg(orgCode);
                AjaxUtils.ajaxJsonSuccessMessage(response, "启用成功!");
            }

        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 启用-注销-岗位
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/enableposition")
    public String enableposition(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posCode = jsonObj.getString("posCode");
            //0-注销,1-启用
            String flag = jsonObj.getString("flag");
            if ("1".equals(flag)) {
                positionRService.reenablePosition(posCode);
                AjaxUtils.ajaxJsonSuccessMessage(response, "启用成功!");
            } else {
                positionRService.cancelPosition(posCode);
                AjaxUtils.ajaxJsonSuccessMessage(response, "注销成功!");
            }
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 删除岗位
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/deletePosition")
    public String deletePosition(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                                 HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posCode = jsonObj.getString("posCode");
            positionRService.deletePosition(posCode);
            AjaxUtils.ajaxJsonSuccessMessage(response, "删除成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 查询机构已经拥有的角色
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryRole")
    public String queryRole(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String guid = jsonObj.getString("guid");
            List<AcRole> list = orgRService.queryRolebyOrgGuid(guid);
            AjaxUtils.ajaxJsonSuccessMessage(response, list);
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }


    @RequestMapping(value = "/addRoleParty")
    public String addRoleParty(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String partyGuid = jsonObj.getString("partyGuid");
            String roleGuid = jsonObj.getString("roleGuid");
            String partyType = jsonObj.getString("partyType");
            AcPartyRole apr = new AcPartyRole();
            apr.setGuidParty(partyGuid);
            apr.setGuidRole(roleGuid);
            apr.setPartyType(partyType);
            roleRService.addRoleParty(apr);
            AjaxUtils.ajaxJsonSuccessMessage(response, "添加成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/deleteRoleParty")
    public String deleteRoleParty(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String partyGuid = jsonObj.getString("partyGuid");
            String roleGuid = jsonObj.getString("roleGuid");
            roleRService.removeRoleParty(roleGuid,partyGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response, "删除成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 查询角色对应权限
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryRoleFun")
    public String queryRoleFun(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                            HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String roleGuid = jsonObj.getString("roleGuid");
            List<AcRoleFunc> list = roleRService.queryAllRoleFunByRoleGuid(roleGuid);
            List<String> guidList = new ArrayList<>();
            for(AcRoleFunc acf:list){
                guidList.add(acf.getGuidFunc());
            }
            List<AcFunc> afList = orgRService.queryFunCByGuidList(guidList);
            AjaxUtils.ajaxJsonSuccessMessage(response, afList);
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }


    /**
     * 查询未授予角色
     *
     * @param model
     * @param content
     * @param age
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryRoleNot")
    public String queryRoleNot(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            // 收到请求
            JSONObject jsonObj = JSONObject.parseObject(content);
            String guid = jsonObj.getString("guid");
            List<AcRole> list = orgRService.queryRoleNotInOrg(guid);
            AjaxUtils.ajaxJsonSuccessMessage(response, list);
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 查询所有机构
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryAllorg")
    public String queryAllorg(ModelMap model, HttpServletRequest request,
                               HttpServletResponse response) {
        try {
            WhereCondition wc = new WhereCondition();
            List<OmOrg> list = orgRService.queryOrgsByCondition(wc);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list,"yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 查询岗位拥有的应用列表
     *
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/queryAppinPos")
    public String queryAppinPos(ModelMap model,@RequestBody String content ,HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posCode = jsonObj.getString("posCode");
            List<AcApp> list = positionRService.queryApp(posCode);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list,"yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }


   @RequestMapping(value = "/queryAppNotinPos")
    public String queryAppNotinPos(ModelMap model,@RequestBody String content ,HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posCode = jsonObj.getString("posCode");
            List<AcApp> list = positionRService.queryAppNotInPosition(posCode);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list,"yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/addAppPosition")
    public String addAppPosition(ModelMap model,@RequestBody String content ,HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(content);
            String posGuid = jsonObj.getString("posGuid");
            String appGuid = jsonObj.getString("appGuid");
            positionRService.addAppPosition(appGuid, posGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/deleteAppPosition")
    public String deleteAppPosition(ModelMap model,@RequestBody String content ,HttpServletRequest request,
                                 HttpServletResponse response) {
        try {	
            JSONObject jsonObj = JSONObject.parseObject(content); 
            String posGuid = jsonObj.getString("posGuid");
            String appGuid = jsonObj.getString("appGuid");
            positionRService.deleteAppPosition(appGuid, posGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response, "删除成功!");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }


    @RequestMapping(value = "/queryAllposbyOrg")
    public String queryAllposbyOrg(ModelMap model,@RequestBody String content ,HttpServletRequest request,
                                    HttpServletResponse response) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(content);
            String orgGuid = jsonObj.getString("orgGuid");
            List<OmPosition> list = positionRService.queryAllPositionByOrg(orgGuid);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/initPosCode")
    public String initPosCode(ModelMap model,@RequestBody String content ,HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            JSONObject jsonObj = JSONObject.parseObject(content);
            String positionType = jsonObj.getString("positionType");
            String positionCode = positionRService.genPositionCode(positionType);
            AjaxUtils.ajaxJsonSuccessMessage(response, positionCode);
        } catch (ToolsRuntimeException e) {// TODO
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
        }
        return null;
    }

    /**
     * 每个controller定义自己的返回信息变量
     */
    private Map<String, Object> responseMsg;

    @Override
    public Map<String, Object> getResponseMessage() {
        if (null == responseMsg) {
            responseMsg = new HashMap<String, Object>();
        }
        return responseMsg;
    }
}