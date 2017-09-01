package org.tis.tools.maven.plugin.genapi.model;

public class ReturnTag {

    private String returnDesc;

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    @Override
    public String toString() {
        return "{" +
                "returnDesc='" + returnDesc + '\'' +
                '}';
    }
}
