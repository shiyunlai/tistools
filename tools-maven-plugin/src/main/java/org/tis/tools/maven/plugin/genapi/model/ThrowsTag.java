package org.tis.tools.maven.plugin.genapi.model;

public class ThrowsTag {
    private String throwsDesc;

    public String getThrowsDesc() {
        return throwsDesc;
    }

    public void setThrowsDesc(String throwsDesc) {
        this.throwsDesc = throwsDesc;
    }

    @Override
    public String toString() {
        return "{" +
                "throwsDesc='" + throwsDesc + '\'' +
                '}';
    }
}

