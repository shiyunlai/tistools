/**
 * 
 */
package org.tis.tools.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * 安全类，加解密工具类
 * Cryptography[krɪp'tɒgrəfɪ]
 * </pre>
 * 
 * @author megapro
 *
 */
public class CryptographyUtil {

	final static Logger logger = LoggerFactory.getLogger(CryptographyUtil.class);
	public static String hunxiao = "q4ndla;3ud;a,;afhfadsf";
	
	/**
	 * 通用的MD5加密算法
	 * 
	 * @param sourceStr 待加密字符串
	 * @return 加密后的字符串
	 */
	public static String md5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage());
			System.out.println(e);
		}
		return result;
	}
	
	/**
	 * 生成用户token //FIXME 应该重构到对应的用户模块（User）SecurityUtil只提供通用功能，而token的生成规则涉及各业务域的具体需求
	 * 
	 * @param uid
	 *            用户id
	 * @param mima
	 *            用户密码
	 * @return 用户登录后的token
	 */
	public static String token(String uid, String mima) {
		return md5(uid + mima + hunxiao);
	}
}
