/**
 * 
 */
package org.tis.tools.rservice.sys.capable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.sys.exception.SysManagementException;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

/**
 * <pre>
 * 业务菜单服务
 * </pre>
 * @author megapro
 *
 */
@Path("dict")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public interface IDictRService {

	/**
	 * <pre>
	 * 取指定业务字典（dictType）中字典项（dictItem）的实际值
	 * 
	 * url: /dict/actual/{dictType}/{dictItem}
	 * 
	 * </pre>
	 * 
	 * @param dictType
	 *            业务字典
	 * @param dictItem
	 *            字典项
	 * @return 实际值
	 * @throws RuntimeException
	 *             取不到值，或取值发生错误时抛出异常
	 */
	@GET
	@Path("/actual/{dictType}/{dictItem}")
	String getActualValue(@PathParam("dictType") String dictType, @PathParam("dictItem") String dictItem) throws SysManagementException;
	
	/**
	 * <pre>
	 * 新增业务字典
	 * 系统自动补充guid
	 * 
	 * url: /dict
	 * </pre>
	 * @param dict 业务字典
	 * @throws SysManagementException
	 */
	@POST
	void addDict(SysDict dict ) throws SysManagementException;
	
	/**
	 * <pre>
	 * 新增业务字典项
	 * 系统自动补充guid
	 * 
	 * url: /dict/item
	 * </pre>
	 * @param dictItem 业务字典项
	 * @throws SysManagementException
	 */
	@POST
	@Path("/item") 
	void addDictItem( SysDictItem dictItem ) throws SysManagementException;
	
}
