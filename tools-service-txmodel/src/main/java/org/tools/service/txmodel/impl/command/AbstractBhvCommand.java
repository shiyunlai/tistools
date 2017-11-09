/**
 * 
 */
package org.tools.service.txmodel.impl.command;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.rservice.txmodel.TxModelEnums.BHVCODE;
import org.tis.tools.rservice.txmodel.spi.message.ITxResponse;
import org.tools.service.txmodel.impl.handler.DefaultOperatorBhvHandler;
import org.tools.service.txmodel.spi.engine.IOperatorBhvCommand;
import org.tools.service.txmodel.spi.engine.IOperatorBhvHandler;
import org.tools.service.txmodel.spi.engine.TxContext;

/**
 * 
 * 操作行为命令抽象实现
 * 
 * @author megapro
 *
 */
abstract class AbstractBhvCommand implements IOperatorBhvCommand {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 交易操作行为处理器（防止空指针，初始化一个默认处理器）
	 */
	protected IOperatorBhvHandler handler = null;
	
	/**
	 * 可处理该命令的行为处理器
	 */
	private List<IOperatorBhvHandler> handlers = new ArrayList<IOperatorBhvHandler>() ;  ; 
	
	/**
	 * 操作命令对应的操作代码
	 */
	protected BHVCODE bhvCode = null;

	AbstractBhvCommand(String bhvCode) {
		this.bhvCode = BHVCODE.valueOf(bhvCode);
	}

	AbstractBhvCommand(BHVCODE bhvCode) {
		this.bhvCode = bhvCode;
	}

	@Override
	public void setOperatorBhvHandler(IOperatorBhvHandler handler) {
		this.handler = handler;
	}

	@Override
	public BHVCODE getBhvCode() {
		return this.bhvCode;
	}

	public void setBhvCode(BHVCODE bhvCode) {
		this.bhvCode = bhvCode;
	}

	/**
	 * 根据字符串形式的操作代码设置操作类型
	 * 
	 * @param bhvCode
	 */
	public void setBhvCode(String bhvCode) {
		this.bhvCode = BHVCODE.valueOf(bhvCode);
	}

	public List<IOperatorBhvHandler> getHandlers() {
		return handlers;
	}

	public void setHandlers(List<IOperatorBhvHandler> handlers) {
		this.handlers = handlers;
	}
	
	public void addHandler(IOperatorBhvHandler handler) {
		// FIXME 不够严谨，应该判断重复，只保留最近一次加入的对象
		this.handlers.add(handler);
	}

	@Override
	public ITxResponse execute(TxContext txContext) {

		// 如果无人设置当前命令处理器 setOperatorBhvHandler()
		if (null == this.handler) {
			// 由具体的命令实现者判断当前该用哪个命令处理器
			this.handler = judgeHandler(txContext);
			if (null == this.handler) {
				this.handler = new DefaultOperatorBhvHandler();
			}
		}

		return this.handler.handle(txContext);
	}
	
	/**
	 * <pre>
	 * 决定当前命令的处理实现类（子类可覆写judge判断逻辑，实现差异化需求）.
	 * 如：根据渠道来源不同，对‘打开交易’命令的实现逻辑会不一样。
	 * </pre>
	 * 
	 * @param context
	 *            {@link TxContext 交易上下文}
	 * @return
	 */
	protected IOperatorBhvHandler judgeHandler(TxContext txContext) {
		
		for( IOperatorBhvHandler h : handlers){
			if( h.canHandle(txContext) ){
				return h ; 
			}
		}
		
		return new DefaultOperatorBhvHandler() ;
	}

	public String toString(){
		return "操作命令:"+this.bhvCode.toString()  ; 
	}
}
