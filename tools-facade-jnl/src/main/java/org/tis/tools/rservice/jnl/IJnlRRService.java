/**
 * 
 */
package org.tis.tools.rservice.jnl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.tis.tools.model.po.jnl.JnlCustService;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

/**
 * <pre>
 * 流水业务域的服务
 * 
 * 说明：RRService 代表 Remote & REST Service ，表示本服务支持RPC和RESTFul两种形式的调用
 * </pre>
 *
 * @autor mega-pro
 * 
 */
@Path("jnl")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public interface IJnlRRService {

	/**
	 * <pre>
	 * 开始一次客户服务过程
	 * </pre>
	 * 
	 * @param custNo
	 *            客户编号
	 * @param serviceType
	 *            服务类型
	 * @return 新建好的客户服务流水记录
	 */
	@GET
	@Path("/custservice/{custNo}/{serviceType}")
	public JnlCustService startCustomerService(@PathParam("custNo") String custNo,
			@PathParam("serviceType") String serviceType);
	
}
