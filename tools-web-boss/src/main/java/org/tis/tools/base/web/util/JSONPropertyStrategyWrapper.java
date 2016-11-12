package org.tis.tools.base.web.util;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONException;
import net.sf.json.util.PropertySetStrategy;

public class JSONPropertyStrategyWrapper extends PropertySetStrategy {

	private PropertySetStrategy original;

	public JSONPropertyStrategyWrapper(PropertySetStrategy original) {

		this.original = original;

	}

	@Override
	public void setProperty(Object o, String string, Object o1)
			throws JSONException {

		try {
			original.setProperty(o, string, o1);

		} catch (Exception ex) {
//			System.out.println("xxxxxxxxxxxxxx"+string);
//			ex.printStackTrace();
		}

	}
}
