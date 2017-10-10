package org.tis.tools.common.utils;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by zhaoch on 2017/7/20.
 */
public class StringUtilTest {
    @Test
    public void isEmpty() throws Exception {
        Assert.assertTrue(StringUtil.isEmpty("123123", null));
        Assert.assertTrue(StringUtil.isEmpty("123123", ""));
        Assert.assertTrue(StringUtil.isEmpty("123123", " "));
        Assert.assertFalse(StringUtil.isEmpty(" 123123 ", "zch"));
    }

    @Test
    public void format() throws Exception {
        String str = "{0} and {1} and {2} !";
        String[] fmt = {"AAAA", "BBBB", "CCCC", "DDDD"};
        Assert.assertEquals("AAAA and BBBB and CCCC !", StringUtil.format(str, fmt));
    }

    @Test
    public void concat() throws Exception {
        Assert.assertEquals("一二三四五", StringUtil.concat("一", "二", "三", "四", "五"));
    }

    @Test
    public void leftPad() throws Exception {
        Assert.assertEquals("00001", StringUtil.leftPad("1",5,'0'));
        Assert.assertEquals("00123", StringUtil.leftPad("123",5,'0'));
        Assert.assertEquals("01234", StringUtil.leftPad("1234",5,'0'));
        Assert.assertEquals("12345", StringUtil.leftPad("12345",5,'0'));
        Assert.assertEquals("12345", StringUtil.leftPad("12345",4,'0'));
    }

    @Test
    public void rightPad() throws Exception {
        Assert.assertEquals("10000", StringUtil.rightPad("1",5,'0'));
        Assert.assertEquals("12300", StringUtil.rightPad("123",5,'0'));
        Assert.assertEquals("12340", StringUtil.rightPad("1234",5,'0'));
        Assert.assertEquals("12345", StringUtil.rightPad("12345",5,'0'));
        Assert.assertEquals("12345", StringUtil.rightPad("12345",4,'0'));
    }

    @Test
    public void toString1() throws Exception {
        String s = StringUtil.toString(new AAA("1", "2", "3"));
        Assert.assertTrue(s.endsWith("[a=1,b=2,c=3]"));
    }

    private class AAA{
        String a;
        String b;
        String c;

        public AAA(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }
    }

    @Test
    public void noEmpty() throws Exception {
        Assert.assertTrue(StringUtil.noEmpty("123123", "zch"));
        Assert.assertFalse(StringUtil.noEmpty(" 123123 ", ""));
    }

    @Test
    public void isEqualsIn() throws Exception {
        Assert.assertTrue(StringUtil.isEqualsIn("1", "1", "2"));
        Assert.assertFalse(StringUtil.isEqualsIn("3", "1", "2"));
    }

    @Test
    public void isEquals() throws Exception {
        Assert.assertTrue(StringUtil.isEquals("1", "1"));
        Assert.assertTrue(StringUtil.isEquals(null, null));
        Assert.assertFalse(StringUtil.isEquals("1", "3"));
        Assert.assertFalse(StringUtil.isEquals("1", null));
    }

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
        System.out.println("login is equals in [login, logout, pause] ? :" + StringUtil.isEqualsIn("login", "login2", "logout", "pause"));
    }



}
