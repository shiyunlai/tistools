package org.tools.design.test.ac;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcDatascope;
import org.tis.tools.model.po.ac.AcEntity;
import org.tis.tools.rservice.ac.capable.IEntityRService;
import org.tools.design.SpringJunitSupport;

public class EntityRServiceTest extends SpringJunitSupport {

    @Autowired
    IEntityRService entityRService;

    @Test
    public void createEntity() {
        try {
//            String json = "{\"privName\":\"实体测\",\"dataOpType\":\"U\",\"entityName\":\"实体测\",\"guid\":\"DATASCOPE1511513060\",\"dataopType\":\"C\",\"guidEntity\":\"DATASCOPE151151306011\",\"filterSqlString\":\"cets\"}";
            AcEntity acEntity = new AcEntity();
            acEntity.setGuid("123123123");
            System.out.println(entityRService.createAcEntity(acEntity));

        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
    @Test
    public void editAcDatascope() {
        try {
            String json = "{\"privName\":\"实体测\",\"dataOpType\":\"U\",\"entityName\":\"实体测\",\"guid\":\"DATASCOPE1511513060\",\"dataopType\":\"C\",\"guidEntity\":\"DATASCOPE151151306011\",\"filterSqlString\":\"cets\"}";

            AcDatascope acEntityfield = JSON.parseObject(json, AcDatascope.class);
            System.out.println(entityRService.editAcDatascope(acEntityfield));

        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }


}
