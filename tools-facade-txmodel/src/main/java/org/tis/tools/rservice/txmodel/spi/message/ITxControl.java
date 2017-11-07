/**
 * 
 */
package org.tis.tools.rservice.txmodel.spi.message;

import java.io.Serializable;

import org.tis.tools.rservice.txmodel.monitor.MonitorTypeEnum;
import org.tis.tools.rservice.txmodel.override.OverrideTypeEnum;
import org.tis.tools.rservice.txmodel.recheck.ReCheckTypeEnum;
import org.tools.core.sdo.DataObject;

/**
 * <pre>
 * 交易控制信息
 * 
 * 一般包括对某次交易操作的控制信息，通过控制头，向接收方（请求方与服务方在请求与响应过程中互为接收方）传递程序执行意图。
 * 
 * 控制内容会随交易操作行为的不同而不同，
 * 
 * 如：当前交易操作为‘授权提交’时
 * 	前端 --> 服务端，交易控制信息中会包括‘授权员信息’、‘授权操作信息’等，
 * 	服务端 --> 前端，交易控制信息中除了返回原前端提交的控制信息，还会加入‘授权结果’、‘授权方式’等等授权行为控制参数信息。
 * 
 * 又如：当前交易操作为‘翻页查询’时
 * 	前端 --> 服务端，交易控制信息中会包括‘当前页参数’、‘翻页控制’，
 * 	服务端 --> 前端，交易控制信息中除了包括原‘当前参数’、‘翻页控制’，还会加入‘剩余页信息’。
 * </pre>
 * @author megapro
 *
 * @param <O> 授权方式
 * @param <OC> 授权控制信息
 */
public interface ITxControl extends IExtPropertyAble {
	
	/**
	 * 是否处理成功
	 * @return true 成功 false 失败
	 */
	public boolean isSuccess() ; 
	
	public void success(boolean success) ; 
	
	/**
	 * 取返回码
	 * @return
	 */
	public String getRetCode() ; 

	public void setRetCode(String retCode) ;
	
	/**
	 * 取返回信息
	 * @return
	 */
	public String getRetMessage() ; 
	
	public void setRetMessage(String message) ; 
	
	
	/**
	 * 是否需要授权
	 * @return true 需要授权 false 无需授权
	 */
	public boolean isOverride() ;
	
	public void override(boolean override) ; 

	/**
	 * 取交易授权方式
	 * 
	 * @return {@link OverrideTypeEnum 交易授权方式}
	 */
	public OverrideTypeEnum getOverrideType();
	
	public void setOverrideType(OverrideTypeEnum overrideType)  ;
	
	/**
	 * 取授权控制参数，由交易操作请求者取出，并按照控制参数执行授权行为
	 * @return 授权控制参数对象
	 */
	public DataObject getOverrideControl() ;
	
	public void setOverrideControl(DataObject overrideControl) ; 
	
	/**
	 * 是否需要复核
	 * @return true 需要复核 false 无需复核
	 */
	public boolean isReCheck() ;
	
	public void reCheck(boolean reCheck ) ; 

	/**
	 * 取交易复核方式
	 * @return {@link ReCheckTypeEnum 交易复核方式}
	 */
	public ReCheckTypeEnum getReCheckType( ) ; 
	
	public void setReCheckType(ReCheckTypeEnum reCheckType) ; 
	
	/**
	 * 取复核控制参数
	 * @return
	 */
	public DataObject getReCheckControl() ;
	
	/**
	 * 是否双录
	 * @return
	 */
	public boolean isDoubleRecord() ;
	
	/**
	 * 取双录控制参数
	 * @return
	 */
	public DataObject getDoubleRecordControl() ;
	
	/**
	 * 是否进行交易监控
	 * @return
	 */
	public boolean isMonitor() ;
	
	/**
	 * 取交易监控方式
	 * @return {@link MonitorTypeEnum 交易监控方式}
	 */
	public MonitorTypeEnum getMonitorType( ) ; 
	
	/**
	 * 取监控控制参数
	 * @return
	 */
	public DataObject getMonitorControl() ;
}
