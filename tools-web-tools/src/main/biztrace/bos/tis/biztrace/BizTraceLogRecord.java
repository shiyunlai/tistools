/**
 * 
 */
package bos.tis.biztrace;

import org.apache.commons.lang.StringUtils;

/**
 * 一条biztrace日志记录对象
 * @author megapro
 *
 */
public class BizTraceLogRecord {

	public String uuid;
	public String time;
	public String date;
	public String type;
	public String serialNo;//主交易流水号,唯一标识一笔交易
	public String serialNoCorr;//关联交易流水号,可为空，如：发送主机服务时，会另外使用一个交易流水号
	public String bsId;
	public String desc;
	public String data;
	public String expt;
	//public String transCode;
	//public String transName;

	public BizTraceLogRecord(String uuid, String time, String type,
			String serialNo, String bsId, String desc, String data, String expt) {

		splitUuid(uuid, serialNo);// 收集 uuid 和 serialNo
		// this.uuid = uuid ;
		this.time = time;
		this.date = time.substring(0, 10) ;
		this.type = type;
		// this.serialNo = serialNo;
		this.bsId = bsId;
		this.desc = desc;
		this.data = data;
		this.expt = expt;
	}

	/**
	 * <pre>
	 * 如果uuid的结构为两部分组成："201608079003736000686: 8f94df86-e535-4f2a-b071-8c0f7ad3f596"
	 * 则要拆分为seriaoNo和uuid
	 * </pre>
	 * 
	 * @param uuidAll
	 *            解析获得的uuidAll
	 * @param serialNo
	 *            解析获得的serialNo，如果uuidAll中不含流水号，则使用本值为流水号
	 */
	private void splitUuid(String uuidAll, String serialNo) {

		if (StringUtils.isEmpty(uuidAll)) {
			return;
		}

		String[] strs = uuidAll.split(":");
		// uuid由两部分组成
		if (strs.length > 1) {

			this.serialNo = StringUtils.trim(strs[0]);//主流水号
			this.uuid = StringUtils.trim(strs[1]);// 去除前后空格
			this.serialNoCorr = serialNo ;//此时解析@SERI获得的为关联流水号
		} else {
			this.uuid = uuidAll;
			this.serialNo = serialNo;//此时解析@SERI得到的为主流水号
		}
	}
}
