package org.tis.tools.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class BasicUtilTest {
    @Test
    public void surroundBrackets() throws Exception {
        Assert.assertEquals("(zch)", BasicUtil.surroundBrackets("zch"));
    }

    @Test
    public void surroundBracketsWithLFStr() throws Exception {
        Assert.assertEquals("name(zch)", BasicUtil.surroundBracketsWithLFStr("name","zch"));
    }

    @Test
    public void showMaps() throws Exception {
        HashMap<String, Object> test = new HashMap<>();
        String s1 = BasicUtil.showMaps(test);
        Assert.assertEquals("maps为空", s1);
        test.put("id", "1");
        test.put("name", "a");
        test.put("memo", "b");
        String s2 = BasicUtil.showMaps(test);
        Assert.assertEquals("name = a\n" +
                "memo = b\n" +
                "id = 1\n", s2);
    }

    @Test
    public void isEmpty() throws Exception {
        Assert.assertTrue(BasicUtil.isEmpty(null));
        Assert.assertTrue(BasicUtil.isEmpty(""));
        Assert.assertTrue(BasicUtil.isEmpty("a", null));
        Assert.assertFalse(BasicUtil.isEmpty("a", "b"));
    }

    @Test
    public void ensitivePaperNo() throws Exception {
        String s = BasicUtil.ensitivePaperNo("123456190001231234");
        Assert.assertEquals("1234******1234", s);
    }

    @Test
    public void ensitivePhoneNo() throws Exception {
        String s = BasicUtil.ensitivePhoneNo("13812345678");
        Assert.assertEquals("138****5678", s);
    }

    @Test
    public void ensitiveAcctNo() throws Exception {
        String s = BasicUtil.ensitiveAcctNo("13812345678");
        Assert.assertEquals("5678", s);
    }

    @Test
    public void concat() throws Exception {
        Assert.assertEquals("一二三四五", BasicUtil.concat("一", "二", "三", "四", "五"));
    }

    @Test
    public void wrap() throws Exception {
        Object[] exp = {"一", "二", "三", "四", "五"};
        Assert.assertArrayEquals(exp, BasicUtil.wrap("一", "二", "三", "四", "五"));
    }


}