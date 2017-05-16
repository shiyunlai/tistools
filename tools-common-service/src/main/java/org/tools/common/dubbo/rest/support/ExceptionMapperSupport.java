package org.tools.common.dubbo.rest.support;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.jboss.resteasy.util.HttpResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.core.exception.ExceptionCodes;

/**
 * 过滤REST服务协议下的异常，使请求着获得异常信息
 * @author megapro
 *
 */
public class ExceptionMapperSupport implements ExceptionMapper<ToolsRuntimeException> {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionMapperSupport.class);

	@Override
	public Response toResponse(ToolsRuntimeException exception) {
		
		String response;
		int code;
		
		if (exception instanceof ToolsRuntimeException) {
			ToolsRuntimeException exc = (ToolsRuntimeException) exception;
			response = "{\"resp_code\":\"" + exc.getCode() + "\",\"resp_info\":\"" + exc.getLocalizedMessage() + "\"}";
			code = ExceptionCodes.successCode;//业务异常，系统正常处理
		}
		else {
			response = ExceptionCodes.SYSTEM_PROCESS_FAILURE;
			logger.error(exception.getMessage(), exception);
			code = ExceptionCodes.errorCode;//
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).status(code).build();
	}
}
