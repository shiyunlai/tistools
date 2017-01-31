package org.tis.tools.maven.plugin.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.commons.io.FileUtils;

/**
 * <pre>
 * XML与Bean互转工具
 * 基于JAXB实现
 * </pre>
 * 
 * @author megapro
 *
 */
public class Xml22BeanUtil {

	/**
	 * xml转换成bean
	 * 
	 * @param type
	 *            xml中定义的java类
	 * @param filepath
	 *            xml文件
	 * @return
	 */
	public static <T> T parseToBean(Class<T> type, String filepath) {
		File f = new File(filepath);
		String xmlstr;
		try {
			xmlstr = FileUtils.readFileToString(f);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}

		T requestXml = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller um = jaxbContext.createUnmarshaller();
			um.setEventHandler(new ValidationEventHandler() {
				public boolean handleEvent(ValidationEvent event) {
					System.out.println("解析xml有错误:" + event.getMessage());
					event.getLinkedException().printStackTrace();
					throw new RuntimeException(event.getMessage(), event
							.getLinkedException());
				}
			});
			requestXml = (T) um.unmarshal(new ByteArrayInputStream(xmlstr.getBytes()));
		} catch (JAXBException e) {
			e.getMessage();
		}
		return requestXml;
	}

	/**
	 * xml转换成bean
	 * 
	 * @param type
	 *            xml中定义的java类
	 * @param filepath
	 *            xml文件
	 * @return
	 */
	public static <T> T xml2Bean(Class<T> type, String filepath) {

		return xml2Bean(type,new File(filepath) ) ;
	}
	
	public static <T> T xml2Bean(Class<T> type, File xmlFile) {
		
		T bean = null;

		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(type);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			bean = (T) jaxbUnmarshaller.unmarshal(xmlFile);
		} catch (JAXBException e) {
			// todo 报错信息
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * bean转换为xml（默认编码格式utf-8）
	 * 
	 * @param bean
	 *            bean对象
	 * @return 转换后的xml字符串
	 */
	public <T> String bean2Xml( T bean) {
		
		return bean2Xml(bean,"utf-8");
	}
	
	/**
	 * bean转换为xml
	 * @param bean 对象
	 * @param encoding 编码
	 * @return 转换后的xml字符串
	 */
	public <T> String bean2Xml(T bean, String encoding) {
		
		String result = null;  
        try {  
            JAXBContext context = JAXBContext.newInstance(bean.getClass());  
            Marshaller marshaller = context.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true); //对生成的xml进行格式化 
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  //xml的编码方式
  
            StringWriter writer = new StringWriter();  
            marshaller.marshal(bean, writer);  
            result = writer.toString();  
        } catch (Exception e) {  
            e.printStackTrace();
            return "" ;
        }  
        
        return result; 
	}

}
