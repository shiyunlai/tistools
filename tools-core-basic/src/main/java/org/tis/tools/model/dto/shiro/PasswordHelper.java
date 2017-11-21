package org.tis.tools.model.dto.shiro;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * shiro密码的帮助类，此处加密算法和加密迭代次数需要和shiro的密码配置相同，否则登录验证不会通过
 */
public class PasswordHelper {

    /** 加密算法*/
    private static final String  algorithmName = "md5";

    /** MD5加密迭代次数 */
    private static final int  hashIterations = 2;

    /**
     * 密码加密
     * @param password 加密前的密码
     * @param salt 盐值，可以想象成同一道菜加的盐不同，最后的味道也就不同，同理，相同的密码盐值不同，最后加密的密码也不同
     * @return
     */
    public static String generate(String password, String salt) {
        if(StringUtils.isBlank(password) && StringUtils.isBlank(salt))
            return null;
        return new SimpleHash(
                algorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
    }
}
