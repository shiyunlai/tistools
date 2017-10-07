package org.tools.core.sdo;

import java.util.List;

/**
 * <pre>
 * 数据对象列表；
 * 实现数据对象列表，某个数据元素的一列。
 * <p/>
 * History:
 * ---------------------------------------------------------
 * Date        Author      Action       Reason
 * 2004/09/22  SHEN        Create
 * 2005/01/11  wuwei       modify
 * ---------------------------------------------------------
 * <p/>
 * Version: 1.0
 * <p/>
 * </pre>
 */

public final class DataList implements Data {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private List dList;

	/**
	 * 构造方法
	 */
	public DataList() {
		dList = new java.util.LinkedList();
	}

	/**
	 * 构造方法
	 */
	public DataList(String name) {
		dList = new java.util.LinkedList();
		this.name = name;
	}

	public String getDataType() {
		return (Data.DATA_TYPE_LIST);
	}

	/**
	 * 取数据列表的容量
	 *
	 * @return 数据列表容量
	 */
	public int getSize() {
		return (dList.size());
	}

	/**
	 * 取数据列表中指定位置的数据
	 *
	 * @param row
	 *            列表中的位置
	 * @return 数据实例
	 */
	public Data get(int row) {
		return (Data) dList.get(row);
	}

	/**
	 * 取数据列表中所有数据
	 *
	 * @return 数据实例数组
	 */
	public Data[] getArray() {
		Data[] ret = new Data[dList.size()];
		dList.toArray(ret);
		return ret;
	}

	/**
	 * 在列表中指定位置设置数据
	 *
	 * @param o
	 *            数据元素
	 * @param row
	 *            列表中的位置
	 */
	public void set(Data data, int row) {
		if (row >= dList.size() - 1) {
			int size = row - (dList.size() - 1);
			for (int i = 0; i < size; i++) {
				dList.add(null);
			}
		}
		dList.set(row, data);
	}

	/**
	 * 在列表中的指定位置插入一个数据对象，其后的数据自动移位
	 *
	 * @param data
	 *            数据元素
	 * @param row
	 *            列表中的位置
	 */
	public void insert(Data data, int row) {
		if (row < dList.size()) {
			dList.add(row, data);
		} else {
			if (row > dList.size()) {
				int size = row - (dList.size() - 1);
				for (int i = 0; i < size; i++) {
					dList.add(null);
				}
			}
			dList.add(data);
		}

	}

	/**
	 * 取列表中数据的实例数
	 *
	 * @return 数据实例数
	 */
	public int getOccurrance() {
		return (dList.size());
	}

	/**
	 * 把列表中的数据复制到另一个列表中
	 *
	 * @param dList
	 *            目标数据列表
	 */
	public void overCopy(DataList dList) {

	}

	/**
	 * 复制一个数据列表，列表中的数据从新复制一份
	 *
	 * @return 数据实例数
	 */
	public DataList copy() {
		DataList list = new DataList();
		int size = dList.size();
		for (int i = 0; i < size; i++) {
			Data element = (Data) dList.get(i);
			if (element != null) {
				// list.set((Data) element.clone(),i);
			}
		}
		return (list);
	}

	/**
	 * 清除列表中的数据
	 */
	public void clear() {
		this.dList.clear();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the dList
	 */
	public List getdList() {
		return dList;
	}

	/**
	 * @param dList
	 *            the dList to set
	 */
	public void setdList(List dList) {
		this.dList = dList;
	}

	public Object getProtoValue() {
		return dList;
	}

}
