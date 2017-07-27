package org.tis.tools.common.utils;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by zhaoch on 2017/7/20.
 */
public class StringUtilTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println(" =================== StringUtilTest begin ==================== ");
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        System.out.println(" =================== StringUtilTest finish =================== ");
    }

    @Test
    public void test() {
        System.out.println("login is equals in [login, logout, pause] ? :" +StringUtil.isEqualsIn("login", "login2", "logout", "pause"));
    }



}
