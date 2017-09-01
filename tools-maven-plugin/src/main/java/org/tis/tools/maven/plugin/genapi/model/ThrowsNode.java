package org.tis.tools.maven.plugin.genapi.model;

public class ThrowsNode {
    private String throwsType;

    public String getThrowsType() {
        return throwsType;
    }

    public void setThrowsType(String throwsType) {
        this.throwsType = throwsType;
    }

    @Override
    public String toString() {
        return "{" +
                "throwsType='" + throwsType + '\'' +
                '}';
    }
}
