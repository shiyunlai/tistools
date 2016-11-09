package pm;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import pm.keypart.BizModel;
import pm.keypart.Field;
import pm.keypart.Model;
import pm.tools.FreeMarkerUtil;
import pm.tools.XmlToBean;


public class Generator {
	private static File directory = new File("");
	private static String prjDir = directory.getAbsolutePath();
	private static String bizModelDir = prjDir + "/src/main/java/pm/model/model.xml";
	private static String bizTemplateDir = prjDir + "/src/main/java/pm/template";
	private static String 指定模型 = "";
	private static boolean service开关 = true;
	private static boolean 模型开关 = true;

	public static void main(String[] args) throws Exception {
			BizModel bizModel = XmlToBean.parseToBean(BizModel.class,bizModelDir);
			FreeMarkerUtil.init(bizTemplateDir);
			String targetFile = "";
			Map map = new HashMap();
			map.put("tables", bizModel.getModels());
			targetFile = prjDir + "/sql/ddl" + ".sql";
			FreeMarkerUtil.process("ddl.sql.ftl", map, targetFile);
			
			String [] s = 指定模型.split("\\,") ;
			for( String one : s ){
				for (Model model : bizModel.getModels()) {
					if (StringUtils.isNotEmpty(one)) {
						if (!StringUtils.equals(model.getName(), one)) {
							continue;
						}
					} 

					System.out.println("开始生成模型："+one+" "+model.getId());
					{//验证模型的合法性
//						String bixu="[delstatus][creatorId][creatorName][creatorCode][creatorIdPath][creatorNamePath][creatorCodePath][createTime][modifiedTime]";
						String bixu="";
						List<Field> fsList = model.getFields();
						for(Field f:fsList){
							bixu=bixu.replace("["+f.getId()+"]", "");
						}
						if(bixu.length()>0){
							throw new RuntimeException("模型缺少必须字段："+bixu);
						}
					}
					map = new HashMap();
					map.put("table", model);
					map.put("modelName", bizModel.getName());
					map.put("modelId", bizModel.getId());
					if(模型开关){
						targetFile = prjDir
								+ "/src/main/java/d/dao/model/"
								+ FreeMarkerUtil.capFirst(model.getId()) + ".java";
						FreeMarkerUtil.process("Model.java.ftl", map, targetFile);
	
						targetFile = prjDir
								+ "/src/main/java/d/dao/mapper/"
								+ FreeMarkerUtil.capFirst(model.getId())
								+ "Mapper.java";
						FreeMarkerUtil.process("Mapper.java.ftl", map, targetFile);
	
						targetFile = prjDir
								+ "/src/main/resources/d/dao/mapper/"
								+ FreeMarkerUtil.capFirst(model.getId()) + "Mapper.xml";
						FreeMarkerUtil.process("Mapper2.xml.ftl", map, targetFile);
					}
					if(service开关){
	
						targetFile = prjDir
								+ "/src/main/java/d/service/biz/"
								+ FreeMarkerUtil.capFirst(model.getId())
								+ "Service.java";
						FreeMarkerUtil.process("BizService.java.ftl", map, targetFile);
					}
				}
			
			}
	
		System.out.println("end");
	}
}
