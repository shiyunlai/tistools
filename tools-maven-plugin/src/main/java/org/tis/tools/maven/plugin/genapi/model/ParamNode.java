package org.tis.tools.maven.plugin.genapi.model;

public class ParamNode {
    private String paramName;
    private String paramType;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    @Override
    public String toString() {
        return "{" +
                "paramName='" + paramName + '\'' +
                ", paramType='" + paramType + '\'' +
                '}';
    }
}
