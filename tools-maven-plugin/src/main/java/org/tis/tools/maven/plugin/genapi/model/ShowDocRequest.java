package org.tis.tools.maven.plugin.genapi.model;

public class ShowDocRequest {

    /**
     * 参数名	    必选	 类型	    说明
     * api_key	    是	 string	    api_key，认证凭证。登录showdoc，进入具体项目后，点击右上角的”项目设置”-“开放API”便可看到
     * api_token	是	 string	    同上
     * cat_name	    否	 string	    可选参数。当页面文档处于目录下时，请传递目录名。当目录名不存在时，showdoc会自动创建此目录
     * cat_name_sub	否	 string	    可选参数。当页面文档处于更细分的子目录下时，请传递子目录名。当子目录名不存在时，showdoc会自动创建此子目录
     * page_title	是	 string	    页面标题。请保证其唯一。（或者，当页面处于目录下时，请保证页面标题在该目录下唯一）。当页面标题不存在时，showdoc将会创建此页面。当页面标题存在时，将用page_content更新其内容
     * page_content	是	 string	    页面内容，可传递markdown格式的文本或者html源码
     * s_number	    否	 number	    可选，页面序号。默认是99。数字越小，该页面越靠前
     */

    private String api_key;
    private String api_token;
    private String cat_name;
    private String cat_name_sub;
    private String page_title;
    private String page_content;
    private String s_number;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_name_sub() {
        return cat_name_sub;
    }

    public void setCat_name_sub(String cat_name_sub) {
        this.cat_name_sub = cat_name_sub;
    }

    public String getPage_title() {
        return page_title;
    }

    public void setPage_title(String page_title) {
        this.page_title = page_title;
    }

    public String getPage_content() {
        return page_content;
    }

    public void setPage_content(String page_content) {
        this.page_content = page_content;
    }

    public String getS_number() {
        return s_number;
    }

    public void setS_number(String s_number) {
        this.s_number = s_number;
    }

    @Override
    public String toString() {
        return "api_key=" + api_key +
                "&api_token=" + api_token  +
                "&cat_name=" + cat_name +
                "&cat_name_sub=" + cat_name_sub +
                "&page_title=" + page_title +
                "&page_content=" + page_content  +
                "&s_number=" + s_number;
    }
}
