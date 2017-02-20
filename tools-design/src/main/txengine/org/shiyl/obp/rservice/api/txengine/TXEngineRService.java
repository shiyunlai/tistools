/**
 * 
 */
package org.shiyl.obp.rservice.api.txengine;

import org.shiyl.obp.core.data.DataObject;

/**
 * <pre>
 * 
 * 功能：交易引擎服务
 * 说明：作为服务提供者，供各渠道执行交易操作的过程中调用。
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public interface TXEngineRService {

	/**
	 * <pre>
	 * 功能：检查交易权限（judged-tx）
	 * 说明：判断txTeller在渠道channelCode上对交易txCode的操作权限。
	 * 场景：提供给主管使用，判断柜员操作交易的权限，判断后会范围权限结果
	 * </pre>
	 * 
	 * @param txCode
	 *            交易代码
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员代码
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult judgedTX(String txCode, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：启动交易（driver-tx）
	 * 说明：交易柜员txTeller从渠道channelCode启动交易txCode
	 * </pre>
	 * 
	 * @param txCode
	 *            交易代码
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员代码
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult driveTX(String txCode, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：暂存交易（hold-tx）
	 * 说明：交易柜员txTeller从渠道channelCode对交易txCode的交易数据txData进行了暂存
	 * </pre>
	 * 
	 * @param txData
	 *            交易数据
	 * @param txSerialNo
	 *            交易流水号
	 * @param txCode
	 *            交易代码
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult holdTX(DataObject txData, String txSerialNo, String txCode, String channelCode,
			String txTeller);

	/**
	 * <pre>
	 * 功能：补录(暂存)交易（type-in） 
	 * 说明：查询此前暂存的交易，补录交易数据后，继续执行后续交易操作
	 * </pre>
	 * 
	 * @param txSerialNo
	 *            交易流水号
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult typeInTX(String txSerialNo, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：提交交易（commit-tx）
	 * 说明：交易柜员txTeller通过渠道channelCode向系统提交了txCode交易，交易数据为txData。
	 * </pre>
	 * 
	 * @param txData
	 *            交易数据
	 * @param txSerialNo
	 *            交易流水号
	 * @param txCode
	 *            交易代码
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult commitTX(DataObject txData, String txSerialNo, String txCode, String channelCode,
			String txTeller);

	/**
	 * <pre>
	 * 功能：再提交交易（commit-ag）
	 * 说明：已经提交过，但交易状态为存疑的，可以再次提交。
	 * 注：再提交功能，需要后端应用系统具备交易防重和幂等特性（主机接口上可配置是否支持交易再提交）。
	 * </pre>
	 * 
	 * @param txSerialNo
	 *            交易流水号
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult commitAg(String txSerialNo, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：取消未成功交易（cancel-tx） 
	 * 说明：取消某个已经提交交易，该交易 1-可能正在执行；2-可能已经提交完成，且交易结果已经确定（交易状态已明确）；
	 * 注：取消交易时，如果请求已经发送到后台系统（主机接口上可配置是否支持取消），需要具备取消能力（冲正、抹账）。
	 * </pre>
	 * 
	 * @param txSerialNo
	 *            交易流水号
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult cancelTX(String txSerialNo, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：删除已完成交易（delete-tx）
	 * 说明：删除未执行提交操作的交易
	 * 注：如果已经有明确的交易状态（非初始状态的交易），则不能删除
	 * </pre>
	 * 
	 * @param txSerialNo
	 *            交易流水号
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult deleteTX(String txSerialNo, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：补打交易凭证（re-print）
	 * 说明：返回指定交易的
	 * </pre>
	 * 
	 * @param txSerialNo
	 *            交易流水号
	 * @param transPrintedGUID
	 *            交易输出凭证流水GUID
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult rePrint(String txSerialNo, String voucherSerialNo, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：复制交易数据（copy-tx）
	 * 说明：复制当前交易的交易数据
	 * 注：界面端执行复制后，服务端判断哪些数据可以复制
	 * </pre>
	 * 
	 * @param txData
	 *            交易数据
	 * @param txSerialNo
	 *            交易流水
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult copyTXData(DataObject txData, String txSerialNo, String channelCode, String txTeller);

	/**
	 * <pre>
	 * 功能：关闭交易（close-tx）
	 * 说明：关闭正在打开中的交易
	 * </pre>
	 * 
	 * @param txSerialNo
	 *            交易流水号
	 * @param channelCode
	 *            渠道代码
	 * @param txTeller
	 *            交易柜员
	 * @return 操作结果 {@link TXOperationResult}
	 */
	public TXOperationResult closeTX(String txSerialNo, String channelCode, String txTeller);

}
