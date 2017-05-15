package org.tools.common.dubbo.rest.support;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

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
		System.out.println("------------- shiyl ------------");
		String response;
		int code;
		if (exception instanceof ToolsRuntimeException) {
			ToolsRuntimeException exc = (ToolsRuntimeException) exception;
			response = "{\"resp_code\":\"" + exc.getCode() + "\",\"resp_info\":\"" + exc.getLocalizedMessage() + "\"}";
			code = ExceptionCodes.successCode;
		} else {
			response = ExceptionCodes.ERROR_RESPONSE_CODE;
			logger.error(exception.getMessage(), exception);
			code = ExceptionCodes.errorCode;
		}
		return Response.ok(response, MediaType.APPLICATION_JSON).status(code).build();
	}
}
