package org.tis.tools.model.def;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.po.log.LogAbfHistory;
import org.tis.tools.model.po.log.LogAbfOperate;
import org.tis.tools.model.po.om.*;
import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.model.po.sys.SysRunConfig;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据GUID获取相关信息
 */
public class AbfEntityHelper {

    private static final Map<String, String> GUID_TABLE_MAP;

    static {
        GUID_TABLE_MAP = new HashMap<String, String>();
        GUID_TABLE_MAP.put("ORG", OmOrg.CLASS_NAME);
        GUID_TABLE_MAP.put("GROUP", OmGroup.CLASS_NAME);
        GUID_TABLE_MAP.put("BUSIORG", OmBusiorg.CLASS_NAME);
        GUID_TABLE_MAP.put("EMPLOYEE", OmEmployee.CLASS_NAME);
        GUID_TABLE_MAP.put("POSITION", OmPosition.CLASS_NAME);
        GUID_TABLE_MAP.put("DUTY", OmDuty.CLASS_NAME);
        GUID_TABLE_MAP.put("ROLE", AcRole.CLASS_NAME);
        GUID_TABLE_MAP.put("APP", AcApp.CLASS_NAME);
        GUID_TABLE_MAP.put("ENTITY", AcEntity.CLASS_NAME);
        GUID_TABLE_MAP.put("ENTITYFIELD", AcEntityfield.CLASS_NAME);
        GUID_TABLE_MAP.put("DATASCOPE", AcDatascope.CLASS_NAME);
        GUID_TABLE_MAP.put("FUNCGROUP", AcFuncgroup.CLASS_NAME);
        GUID_TABLE_MAP.put("FUNC", AcFunc.CLASS_NAME);
        GUID_TABLE_MAP.put("BHVTYPEDEF", AcBhvtypeDef.CLASS_NAME);
        GUID_TABLE_MAP.put("BHVDEF", AcBhvDef.CLASS_NAME);
        GUID_TABLE_MAP.put("FUNCBHV", AcFuncBhv.CLASS_NAME);
        GUID_TABLE_MAP.put("OPERATOR", AcOperator.CLASS_NAME);
        GUID_TABLE_MAP.put("IDENTITY", AcOperatorIdentity.CLASS_NAME);
        GUID_TABLE_MAP.put("OMENU", AcOperatorMenu.CLASS_NAME);
        GUID_TABLE_MAP.put("MENU", AcMenu.CLASS_NAME);
        GUID_TABLE_MAP.put("OPERATORCFG", AcOperatorConfig.CLASS_NAME);
        GUID_TABLE_MAP.put("DICT", SysDict.CLASS_NAME);
        GUID_TABLE_MAP.put("DICTITEM", SysDictItem.CLASS_NAME);
        GUID_TABLE_MAP.put("RUNCONFIG", SysRunConfig.CLASS_NAME);
        GUID_TABLE_MAP.put("OPERATELOG", LogAbfOperate.CLASS_NAME);
        GUID_TABLE_MAP.put("OBJLOG", LogAbfHistory.CLASS_NAME);
    }

    /**
     * 根据GUID获取表名
     * 如"DICT123123123" -> "SYS_DICT"
     * 找不到或者guid为null 返回null
     * @param guid 需要获取表名的GUID值
     * @return
     */
    public static String getTableName(String guid) {
        if (StringUtils.isBlank(guid))
            return null;
        String className = GUID_TABLE_MAP.get(guid.replaceAll("[^(A-Za-z)]", ""));
        return StringUtil.camel2Underline(className.substring(className.lastIndexOf(".") + 1, className.length()));
    }

    /**
     * map通过guid增加字段信息
     * 如 (guid)=111, dict_type=name, dict_key=1231 => 数据主键(guid)=111, 类型(dict_type)=name, 业务字典(dict_key)=1231
     * @param map
     * @param guid
     * @return
     * @throws Exception
     */
    public static Map<String, Object> transEntity(Map<String, Object> map, String guid) throws Exception {
        if (map == null || StringUtils.isBlank(guid))
            return null;
        String className = GUID_TABLE_MAP.get(guid.replaceAll("[^(A-Za-z)]", ""));
        Object obj = Class.forName(className).newInstance();
        Field[] fields = obj.getClass().getFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().startsWith("NAME")) {
                String col = field.getName().substring(5).toLowerCase();
                for (String key : map.keySet()) {
                    if (StringUtils.equalsIgnoreCase(col, key)) {
                        map.put(field.get(obj) + "(" + key + ")", map.get(key));
                        map.remove(key);
                        break;
                    }
                }
            }
        }
        return map;
    }


}
