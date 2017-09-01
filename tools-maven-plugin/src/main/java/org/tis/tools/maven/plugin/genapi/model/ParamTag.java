package org.tis.tools.maven.plugin.genapi.model;

public class ParamTag {

    /** 参数名 **/
    private String paramName;

    /** 参数说明 **/
    private String paramDesc;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamDesc() {
        return paramDesc;
    }

    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc;
    }

    @Override
    public String toString() {
        return "{" +
                "paramName='" + paramName + '\'' +
                ", paramDesc='" + paramDesc + '\'' +
                '}';
    }
}
