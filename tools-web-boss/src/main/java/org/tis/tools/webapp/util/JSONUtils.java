package org.tis.tools.webapp.util;

import net.sf.json.JSONObject;

public class JSONUtils
{
  public static String getStr(JSONObject jsonObj, String key)
  {
    try
    {
      return jsonObj.getString(key);
    } catch (Exception e) {
    }
    return null;
  }
}