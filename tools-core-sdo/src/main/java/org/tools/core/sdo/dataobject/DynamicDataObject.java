/**
 * 
 */
package org.tools.core.sdo.dataobject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tools.core.sdo.Data;
import org.tools.core.sdo.DataField;
import org.tools.core.sdo.DataFieldDictionary;
import org.tools.core.sdo.DataList;
import org.tools.core.sdo.DataObject;
import org.tools.core.sdo.exception.SDOException;
import org.tools.core.sdo.exception.SDOExceptionCodes;
import org.tools.core.sdo.field.BigDecimalField;
import org.tools.core.sdo.field.BigIntegerField;
import org.tools.core.sdo.field.BooleanField;
import org.tools.core.sdo.field.ByteArrayField;
import org.tools.core.sdo.field.ByteField;
import org.tools.core.sdo.field.CharField;
import org.tools.core.sdo.field.DateField;
import org.tools.core.sdo.field.DoubleField;
import org.tools.core.sdo.field.FloatField;
import org.tools.core.sdo.field.IntField;
import org.tools.core.sdo.field.LongField;
import org.tools.core.sdo.field.ObjectField;
import org.tools.core.sdo.field.ShortField;
import org.tools.core.sdo.field.StringField;
import org.tools.core.sdo.field.TimeField;

/**
 * <pre>
 * 
 * 动态数据对象
 * 1、不需要事先定义 MetaObject
 * 2、其属性可以任意增加和删除
 * 
 * </pre>
 */

public class DynamicDataObject implements DataObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 数据名称 */
	private String name;

	/** 数据内容 */
	private HashMap dataMap;

	/**
	 * 构造器
	 * 
	 * @param name
	 *            初始对象池的大小.
	 */
	public DynamicDataObject() {
		super();
		this.dataMap = new HashMap();
	}

	/**
	 * 构造器
	 * 
	 * @param name
	 *            初始对象池的大小.
	 */
	public DynamicDataObject(String name) {
		super();
		setName(name);
		this.dataMap = new HashMap();
	}

	/**
	 * 取数据对象的定义
	 * 
	 * @return
	 */
	public String getMetaObjectId() {
		return ("DYNAMIC");
	}

	public String getDataType() {
		return (Data.DATA_TYPE_OBJECT);
	}

	/******************************************************************************
	 * 1、池中数据访问方法
	 ******************************************************************************/

	/**
	 * 从池中取一个数据对象
	 * 
	 * @param dataName
	 *            数据名称
	 * @return 数据对象实例（如果池中无指定对象，返回NULL );
	 */
	public Data getData(String dataName) {
		return ((Data) this.dataMap.get(dataName));
	}

	/**
	 * 从池中取一个数据对象
	 * 
	 * @param dataName
	 *            数据名称
	 * @param seqNo
	 *            数据列位置
	 * @return 数据对象实例（如果池中无指定对象，返回NULL );
	 */
	public Data getData(String dataName, int seqNo) {
		if (dataName == null || seqNo < 0)
			return (null);

		Data data = this.getData(dataName);

		if (data == null)
			return null;
		else if (data.getDataType() == Data.DATA_TYPE_LIST) {
			DataList datalist = (DataList) data;
			return (datalist.get(seqNo));
		} else if (seqNo == 0)
			return data;
		else
			return null;
	}

	/**
	 * 从数据表中取一行数据
	 * 
	 * @param dataNames
	 *            数据名称
	 * @param seqNo
	 *            顺序号
	 * @return DataElement[] 数据数组
	 */
	public Data[] getData(String dataNames[], int seqNo) {
		if (dataNames == null || dataNames.length == 0 || seqNo < 0)
			return null;
		int len = dataNames.length;

		Data datas[] = new Data[len];

		for (int i = 0; i < len; i++)
			datas[i] = getData(dataNames[i], seqNo);

		return datas;
	}

	/**
	 * 向数据池中添加一个对象，如果池中已有同名对象则覆盖之
	 * 
	 * @param data
	 *            数据对象
	 */
	public void setData(String dataName, Data data) {
		this.dataMap.put(dataName, data);
	}

	/**
	 * 向数据池中添加一个对象，如果池中已有同名对象则覆盖之
	 * 
	 * @param data
	 *            数据对象
	 */
	public void setData(Data data) {
		this.dataMap.put(data.getName(), data);
	}

	/**
	 * 向数据池中指定的行位置添加一个对象，如果池中已有同名对象则覆盖之
	 * 
	 * @param data
	 *            数据对象
	 * @param seqNo
	 *            数据行位置
	 */
	public void setData(Data data, int seqNo) {
		if (data == null || seqNo < 0)
			return;

		String name = data.getName();

		if (name == null)
			return;

		Data ele = this.getData(name);

		DataList dList;
		if (ele == null || ele.getDataType() != Data.DATA_TYPE_LIST) {
			dList = new DataList();
			dList.setName(name);
			setData(dList);
		} else
			dList = (DataList) ele;

		dList.set(data, seqNo);
	}

	/**
	 * 在池中指定位置插入一个数据对象，其后对象自动往后移位；
	 * 
	 * @param data
	 *            数据对象
	 * @param seqNo
	 *            数据行位置
	 */
	public void insert(Data data, int seqNo) {
		if (data == null || seqNo < 0)
			throw new RuntimeException("DataElement is null || seqNo <0");

		String name = data.getName();

		if (name == null)
			throw new RuntimeException("[" + data.toString() + "]DataName is null");

		DataList dList = (DataList) this.getData(name);

		if (dList == null) {
			dList = new DataList(name);
			setData(dList);
		}

		dList.insert(data, seqNo);
	}

	/**
	 * 从池中删除一个对象
	 * 
	 * @param key
	 *            对象的KEY
	 */
	public void remove(String key) {
		this.dataMap.remove(key);
	}

	/**
	 * 判断池中是否包含指定名称的数据对象
	 * 
	 * @param name
	 *            数据名称
	 * @return true or false
	 */
	public boolean containElement(String name) {
		if (this.getData(name) == null)
			return (false);
		else
			return (true);
	}

	/******************************************************************************
	 * 2、数据池的管理方法
	 ******************************************************************************/

	/**
	 * 清空池中所有对象
	 */
	public void clear() {
		this.dataMap.clear();
	}

	/******************************************************************************
	 * 3、格式转换方法
	 ******************************************************************************/

	/**
	 * 取数据域的字符串值
	 * 
	 * @return 数据域的字符串值
	 */
	public String getStringValue() {

		/**
		 * StringBuffer sb = new StringBuffer(); PbsObjectable[] objects =
		 * dataPool.getElements(); for (int i = 0; i < objects.length; i++) {
		 * DataElement object = (DataElement) objects[i]; String datastr =
		 * object.getStringValue(); if (datastr == null) datastr = "";
		 * sb.append(object.getDataName()); sb.append(DataConstants.delimeter);
		 * sb.append(object.getDataType()); if (object.getDataType() ==
		 * DataElement.DATA_TYPE_FIELD) { Field field = (Field) object;
		 * sb.append(field.getFieldType()); }
		 * sb.append(DataConstants.delimeter); sb.append(datastr.length());
		 * sb.append(DataConstants.delimeter); sb.append(datastr); } return
		 * sb.toString();
		 */

		return (null);
	}

	/**
	 * 用字符串设置数据的值
	 * 
	 * @param value
	 *            数据域的字符串值
	 */
	public void setValueWithString(String value) {
		// throws PbsException {
		/**
		 * this.clear(); if (value == null || value.length() == 0) return; int
		 * length = value.length(); int startIdx = 0; int endIdx = 0; try{ do {
		 * endIdx = value.indexOf(DataConstants.delimeter, startIdx); String
		 * name = value.substring(startIdx, endIdx); startIdx = endIdx + 1;
		 * endIdx = value.indexOf(DataConstants.delimeter, startIdx); String
		 * type = value.substring(startIdx, endIdx); startIdx = endIdx + 1;
		 * endIdx = value.indexOf(DataConstants.delimeter, startIdx); String len
		 * = value.substring(startIdx, endIdx); startIdx = endIdx + 1; String
		 * strVal = value.substring(startIdx, startIdx + Integer.parseInt(len));
		 * 
		 * DataElement element =
		 * DataHelper.getDataElement(Short.parseShort(type), name);
		 * element.setValueWithString(strVal);
		 * 
		 * put(element);
		 * 
		 * startIdx = startIdx + Integer.parseInt(len) ;
		 * 
		 * } while (startIdx < length); }catch (PbsException e){ throw e; }catch
		 * ( Exception e){ e.printStackTrace(); throw new
		 * PbsException(PbsException.ERR_PBS_FORMAT,"DataElement data =["+value
		 * + "]数据有错"); }
		 */
	}

	// 方法描述符 #12 (Ljava/lang/String;)Z
	public boolean getBoolean(java.lang.String dataName) {

		Data data = this.getData(dataName);
		if (data == null)
			return false;
		else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(dataName).getDataType()) != 0)
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is not Field");
		else {
			if (data instanceof BooleanField) {
				BooleanField fld = (BooleanField) data;
				return (fld.getValue());
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no Boolean");
			}
		}

	}

	// 方法描述符 #17 (Ljava/lang/String;)B
	public byte getByte(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return 0;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is not Field");
		} else {
			if (data instanceof ByteField) {
				ByteField fld = (ByteField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is not byte");
			}

		}
	}

	// 方法描述符 #19 (Ljava/lang/String;)C
	public char getChar(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return 0;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof CharField) {
				CharField fld = (CharField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no char");
			}

		}
	}

	// 方法描述符 #21 (Ljava/lang/String;)D
	public double getDouble(java.lang.String arg0) {
		Data data = (Data) dataMap.get(arg0);
		if (data == null) {
			return 0d;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof DoubleField) {
				DoubleField fld = (DoubleField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no double");
			}

		}
	}

	// 方法描述符 #23 (Ljava/lang/String;)F
	public float getFloat(java.lang.String arg0) {
		Data data = (Data) dataMap.get(arg0);
		if (data == null) {
			return 0f;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof FloatField) {
				FloatField fld = (FloatField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no float");
			}

		}
	}

	// 方法描述符 #25 (Ljava/lang/String;)I
	public int getInt(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return 0;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof IntField) {
				IntField fld = (IntField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no Integer");
			}

		}
	}

	// 方法描述符 #27 (Ljava/lang/String;)J
	public long getLong(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return 0L;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof LongField) {
				LongField fld = (LongField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no Long");
			}

		}
	}

	// 方法描述符 #29 (Ljava/lang/String;)S
	public short getShort(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return 0;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof ShortField) {
				ShortField fld = (ShortField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no Short");
			}

		}
	}

	public byte[] getBytes(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return null;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof StringField) {
				StringField fld = (StringField) data;
				return fld.getValue().getBytes();
			} else if (data instanceof ByteArrayField) {
				ByteArrayField fld = (ByteArrayField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no bytes");
			}

		}
	}

	// 方法描述符 #33 (Ljava/lang/String;)Ljava/math/BigDecimal;
	public java.math.BigDecimal getBigDecimal(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return null;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof BigDecimalField) {
				BigDecimalField fld = (BigDecimalField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no BigDecimal");
			}

		}
	}

	// 方法描述符 #35 (Ljava/lang/String;)Ljava/math/BigInteger;
	public java.math.BigInteger getBigInteger(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return null;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof BigIntegerField) {
				BigIntegerField fld = (BigIntegerField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no BigInteger");
			}

		}
	}

	// 方法描述符 #39 (Ljava/lang/String;)Ljava/util/Date;
	public java.util.Date getDate(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return null;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof DateField) {
				DateField fld = (DateField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no Date");
			}

		}
	}

	// 方法描述符 #41 (Ljava/lang/String;)Ljava/lang/String;
	public java.lang.String getString(java.lang.String arg0) {
		Data data = this.getData(arg0);
		if (data == null) {
			return null;
		} else if (Data.DATA_TYPE_FIELD.compareTo(this.getData(arg0).getDataType()) != 0) {
			throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataType is no Field");
		} else {
			if (data instanceof StringField) {
				StringField fld = (StringField) data;
				return fld.getValue();
			} else {
				throw new SDOException(SDOExceptionCodes.SDO_ERROR_ARGUMENTE, "DataClass is no Date");
			}

		}
	}

	// 方法描述符 #43 (Ljava/lang/String;)Ljava/util/List;
	public java.util.List getList(java.lang.String arg0) {
		Data data = (Data) dataMap.get(arg0);
		if (data instanceof DataList) {
			DataList dl = (DataList) data;
			return dl.getdList();
		}
		return (null);
	}

	// 方法描述符 #48 (Ljava/lang/String;Z)V
	public void setBoolean(java.lang.String arg0, boolean arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Boolean.toString(arg1));
			}

		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Boolean.toString(arg1)));
		} else {
			BooleanField bf = new BooleanField(arg0);
			bf.setValue(arg1);
			this.dataMap.put(arg0, bf);
		}
	}

	// 方法描述符 #50 (Ljava/lang/String;B)V
	public void setByte(java.lang.String arg0, byte arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Byte.toString(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Byte.toString(arg1)));
		} else {
			ByteField bf = new ByteField(arg0);
			bf.setValue(arg1);
			this.dataMap.put(arg0, bf);
		}
	}

	// 方法描述符 #52 (Ljava/lang/String;C)V
	public void setChar(java.lang.String arg0, char arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Character.toString(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Character.toString(arg1)));
		} else {
			CharField cf = new CharField(arg0);
			cf.setValue(arg1);
			this.dataMap.put(arg0, cf);
		}
	}

	// 方法描述符 #54 (Ljava/lang/String;D)V
	public void setDouble(java.lang.String arg0, double arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Double.toString(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Double.toString(arg1)));
		} else {
			DoubleField df = new DoubleField(arg0);
			df.setValue(arg1);
			this.dataMap.put(arg0, df);
		}
	}

	// 方法描述符 #56 (Ljava/lang/String;F)V
	public void setFloat(java.lang.String arg0, float arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Float.toString(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Float.toString(arg1)));
		} else {
			FloatField ff = new FloatField(arg0);
			ff.setValue(arg1);
			this.dataMap.put(arg0, ff);
		}
	}

	// 方法描述符 #58 (Ljava/lang/String;I)V
	public void setInt(java.lang.String arg0, int arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Integer.toString(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Integer.toString(arg1)));
		} else {
			IntField inf = new IntField(arg0);
			inf.setValue(arg1);
			this.dataMap.put(arg0, inf);
		}
	}

	// 方法描述符 #60 (Ljava/lang/String;J)V
	public void setLong(java.lang.String arg0, long arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Long.toString(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Long.toString(arg1)));
		} else {
			LongField lf = new LongField(arg0);
			lf.setValue(arg1);
			this.dataMap.put(arg0, lf);
		}
	}

	// 方法描述符 #62 (Ljava/lang/String;S)V
	public void setShort(java.lang.String arg0, short arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(Short.toString(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, Short.toString(arg1)));
		} else {
			ShortField sf = new ShortField(arg0);
			sf.setValue(arg1);
			this.dataMap.put(arg0, sf);
		}
	}

	// 方法描述符 #64 (Ljava/lang/String;[B)V
	public void setBytes(java.lang.String arg0, byte[] arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(arg1.toString());
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, arg1.toString()));
		} else {
			ByteArrayField sf = new ByteArrayField(arg0);
			sf.setValue(arg1);
			this.dataMap.put(arg0, sf);
		}
	}

	// 方法描述符 #66 (Ljava/lang/String;Ljava/math/BigDecimal;)V
	public void setBigDecimal(java.lang.String arg0, java.math.BigDecimal arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(String.valueOf(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, String.valueOf(arg1)));
		} else {
			BigDecimalField bf = new BigDecimalField(arg0);
			bf.setValue(arg1);
			this.dataMap.put(arg0, bf);
		}
	}

	// 方法描述符 #68 (Ljava/lang/String;Ljava/math/BigInteger;)V
	public void setBigInteger(java.lang.String arg0, java.math.BigInteger arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(String.valueOf(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, String.valueOf(arg1)));
		} else {
			BigIntegerField bf = new BigIntegerField(arg0);
			bf.setValue(arg1);
			this.dataMap.put(arg0, bf);
		}
	}

	// 方法描述符 #72 (Ljava/lang/String;Ljava/util/Date;)V
	public void setDate(java.lang.String arg0, java.util.Date arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DateField) {
				DateField df = (DateField) data;
				df.setValue(arg1);
			} else if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(String.valueOf(arg1));
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, String.valueOf(arg1)));
		} else {
			DateField df = new DateField(arg0);
			df.setValue(arg1);
			this.dataMap.put(arg0, df);
		}
	}

	// 方法描述符 #74 (Ljava/lang/String;Ljava/lang/String;)V
	public void setString(java.lang.String arg0, java.lang.String arg1) {
		if (dataMap.get(arg0) != null && !"".equals(dataMap.get(arg0))) {
			ObjectField data1 = null;
			Data data = null;
			if (dataMap.get(arg0) instanceof ObjectField) {
				data1 = (ObjectField) (dataMap.get(arg0));
				data = (Data) data1.getValue();
			} else {
				data = (Data) dataMap.get(arg0);
			}
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(arg1);
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, arg1));
		} else {
			StringField sf = new StringField(arg0);
			sf.setValue(arg1);
			this.dataMap.put(arg0, sf);
		}
	}

	// 方法描述符 #74 (Ljava/lang/String;Ljava/util/List;)V
	public void setList(java.lang.String arg0, java.util.List arg1) {
		DataList dl = new DataList();
		dl.setdList(arg1);
		dataMap.put(arg0, dl);
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
	 * @return the dataMap
	 */
	public HashMap<String, Data> getDataMap() {
		return dataMap;
	}

	/**
	 * @param dataMap
	 *            the dataMap to set
	 */
	public void setDataMap(HashMap<String, Data> dataMap) {
		this.dataMap = dataMap;
	}

	/**
	 * 取所有的属性名称
	 * 
	 * @return
	 */
	public String[] getPropertyNames() {
		Set<String> keys = dataMap.keySet();
		String[] keyArray = keys.toArray(new String[keys.size()]);
		return keyArray;
	}

	/**
	 * 删除对应的属性
	 * 
	 * @param propertyName
	 */
	public void unset(String propertyName) {
		this.dataMap.remove(propertyName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tools.core.sdo.DataObject#getMap(java.lang.String)
	 */
	public Map getMap(String string) {

		return null;

	}

	public Object getProtoValue() {
		return this.dataMap;
	}

	public boolean isSet(String propertyName) {
		return dataMap.containsKey(propertyName);
	}

	/**
	 * 获取属性Field
	 * 
	 * @param propertyName
	 * @return
	 */
	public Object getField(String propertyName) {
		Object data = dataMap.get(propertyName);
		if (data != null) {
			return data;
		}
		return null;
	}

	/**
	 * 获取属性值
	 * 
	 * @param propertyName
	 *            属性类型
	 */
	public Object get(String propertyName) {
		Object data = dataMap.get(propertyName);
		if (data != null) {
			if (data instanceof DataField) {
				return ((DataField) data).getProtoValue();
			} else { // 非Data类型，不推荐
				return data;
			}
		}
		return null;
	}

	public Object set(String propertyName, Object value) {
		
		Object oldValue = get(propertyName);

		if (value == null) {
			this.unset(propertyName);
			return oldValue;
		}

		if (value instanceof Date) {
			setDate(propertyName, (Date) value);
		} else if (value instanceof List) {
			setList(propertyName, (List) value);
		} else if (value instanceof DataObject) {
			setData(propertyName, (DataObject) value);
		} else if (value instanceof byte[]) {
			setBytes(propertyName, (byte[]) value);
		} else if (value instanceof String) {
			setString(propertyName, (String) value.toString());
		} else if (value instanceof Integer) {
			setInt(propertyName, (Integer) value);
		} else if (value instanceof Long) {
			setLong(propertyName, (Long) value);
		} else if (value instanceof Boolean) {
			setBoolean(propertyName, (Boolean) value);
		} else if (value instanceof Byte) {
			setByte(propertyName, (Byte) value);
		} else if (value instanceof Character) {
			setChar(propertyName, (Character) value);
		} else if (value instanceof Double) {
			setDouble(propertyName, (Double) value);
		} else if (value instanceof Float) {
			setFloat(propertyName, (Float) value);
		} else if (value instanceof Short) {
			setShort(propertyName, (Short) value);
		} else if (value instanceof Time) {
			setTime(propertyName, (Time) value);
		} else if (value instanceof BigDecimal) {
			setBigDecimal(propertyName, (BigDecimal) value);
		} else if (value instanceof BigInteger) {
			setBigInteger(propertyName, (BigInteger) value);
		} else {
			// dataMap.put(propertyName, value);
			ObjectField objectField = new ObjectField(propertyName,value);
			this.dataMap.put(propertyName, objectField);
		}

		return oldValue;
	}

	/**
	 * @param propertyName
	 * @param value
	 */
	private void setTime(String arg0, Time arg1) {
		if (dataMap.get(arg0) != null) {
			Data data = (Data) dataMap.get(arg0);
			if (data instanceof DataField) {
				DataField df = (DataField) data;
				df.setValueWithString(arg1.toLocaleString());
			}
		} else if (DataFieldDictionary.getInstance().getMetaField(arg0) != null) {
			this.dataMap.put(arg0, DataFieldDictionary.getInstance().createDataField(arg0, arg1.toLocaleString()));
		} else {
			TimeField tf = new TimeField(arg0);
			tf.setValueWithString(arg1.toLocaleString());
			this.dataMap.put(arg0, tf);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.tools.core.sdo.DataObject#isEmpty()
	 */
	public boolean isEmpty() {
		return dataMap.isEmpty();
	}

}
