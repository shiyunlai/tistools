package org.tis.tools.common.utils;

import org.junit.Assert;
import org.junit.Test;

;

public class BeanFieldValidateUtilTest {

    /**
     * AAAA 测试类 有属性 id, name, memo
     */
    private class AAAA {
        private Integer id;
        private String name;
        private String memo;

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        @Override
        public String toString() {
            return "AAAA{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", memo='" + memo + '\'' +
                    '}';
        }
    }

    @Test
    public void checkObjFieldRequired() throws Exception {
        AAAA test = new AAAA();
        test.setId(1);
        Assert.assertNull(BeanFieldValidateUtil.checkObjFieldRequired(test, new String[]{"id"}));
        Assert.assertEquals("name", BeanFieldValidateUtil.checkObjFieldRequired(test, new String[]{"id", "name"}));
        test.setName("a");
        Assert.assertNull(BeanFieldValidateUtil.checkObjFieldRequired(test, new String[]{"id", "name"}));

    }

    @Test
    public void checkObjFieldNotRequired() throws Exception {
        AAAA test = new AAAA();
        test.setId(1);
        Assert.assertNull(BeanFieldValidateUtil.checkObjFieldNotRequired(test, new String[]{"name", "memo"}));
        Assert.assertEquals("name", BeanFieldValidateUtil.checkObjFieldNotRequired(test, new String[]{"id", "memo"}));
        test.setName("a");
        Assert.assertNull(BeanFieldValidateUtil.checkObjFieldNotRequired(test, new String[]{"id", "memo"}));
    }

    @Test
    public void checkObjFieldAllRequired() throws Exception {
        AAAA test = new AAAA();
        test.setId(1);
        Assert.assertEquals("name", BeanFieldValidateUtil.checkObjFieldAllRequired(test));
        test.setName("A");
        test.setMemo("aaaa");
        Assert.assertNull(BeanFieldValidateUtil.checkObjFieldAllRequired(test));
    }

    @Test
    public void processObjSensitiveFields() throws Exception {
        AAAA test = new AAAA();
        test.setId(1);
        test.setName("A");
        test.setMemo("aaaa");
        String[] fields = {
                "name", "id"
        };
        BeanFieldValidateUtil.processObjSensitiveFields(test, fields);
        Assert.assertNull(test.getName());

    }
}


