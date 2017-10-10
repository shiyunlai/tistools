package org.tis.tools.common.utils;

import org.junit.Assert;
import org.junit.Test;

public class CryptographyUtilTest {
    @Test
    public void md5() throws Exception {
        String pwd = "1234456";
        Assert.assertNotNull(CryptographyUtil.md5(pwd));
    }

    @Test
    public void token() throws Exception {
        Assert.assertNotNull(CryptographyUtil.token("admin", "admin"));
    }

}