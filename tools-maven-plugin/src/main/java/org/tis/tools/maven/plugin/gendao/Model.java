package org.tis.tools.maven.plugin.gendao;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.maven.plugin.utils.PinyinUtil;

/**
 * 
 * 
 * @author megapro
 *
 */
@XmlRootElement
public class Model {

	private String name;
	private String id;
	private String desc;
	private String ext;// 定义扩展项，如果 ext="10*128"，则自动生成 ext0,ext1...ext9 十个扩展表字段，长度为128
	private String type;
	private List<Field> fields = new ArrayList<Field>();// 模型中的属性
	
	@XmlAttribute(required = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlAttribute
	public String getId() {
		if (StringUtils.isNotEmpty(id)) {
			return id;
		}
		return PinyinUtil.convert(name);
	}

	public void setId(String id) {
		this.id = id;
	}

	private boolean extParsed = false ; 
	@XmlElement(name = "field")
	public List<Field> getFields() {
		
		/*
		 * 如果定义了 ext，而且还未解析过，则自动生成扩展属性，规则：
		 * ext=3,256，表示生成3个扩展字段，每个256位，类型都是String；（暂时不支持其他类型设置）
		 */
		
		// StringUtils.isNotEmpty(ext): ext 存在时，自动增加扩展字段
		// !extParsed : freemarker在使用数据时多次触发getFields方法，不能重复解析ext
		// fields.size() !=0 : fields还未被解析时，不做ext的处理
		if (StringUtils.isNotEmpty(ext) && !extParsed && fields.size() !=0) {
			String[] k = ext.split("\\,");
			String numStr = k[0]; // 扩展字段个数
			String lengthStr = "128";// 不设置长度，默认128位
			if (k.length > 1) {
				lengthStr = k[1];
			}
			int extNum = Integer.valueOf(numStr).intValue();
			for (int i = 0; i < extNum; i++) {
				Field f = new Field();
				f.setId("ext" + i);
				f.setName("ext" + i);
				f.setLength(lengthStr);
				fields.add(f);
			}
			extParsed = true ; //ext已解析
		}
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	@XmlAttribute
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	@XmlAttribute
	public String getType() {
		if (StringUtils.isEmpty(type)) {
			return "table";
		}
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlAttribute
	public String getDesc() {
		if( StringUtils.isNotEmpty(desc) ){
			return this.desc ;
		}
		return this.name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	//串行化，显示表信息和表字典信息
	public String toString() {
		StringBuffer sb = new StringBuffer(toStringSimple()) ;
		for( Field f : this.fields){
			sb.append(f.toString()) ; 
		}
		return sb.toString() ; 
	}
	
	//串行化，只显示表信息
	public String toStringSimple() {
		StringBuffer sb = new StringBuffer() ; 
		sb.append(type).append("\t") ;
		sb.append(id + "(" + name + ":"+ desc + ")" );
		sb.append(" 字数:").append( this.fields.size() ) ; 
		sb.append("\n" ) ; 
		return sb.toString() ; 
	}
}
