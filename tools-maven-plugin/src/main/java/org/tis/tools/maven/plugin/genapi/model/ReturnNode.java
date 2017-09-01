package org.tis.tools.maven.plugin.genapi.model;

public class ReturnNode {
    private String returnType;


    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "{" +
                "returnType='" + returnType + '\'' +
                '}';
    }
}
