package org.tis.tools.model.dto.route;

import java.io.Serializable;

public class RouteState implements Serializable {
    private String routeName;

    private String url;

    private String templateUrl;

    private String controller;

    private String data;

    private String funcCode;

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemplateUrl() {
        return templateUrl;
    }

    public void setTemplateUrl(String templateUrl) {
        this.templateUrl = templateUrl;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(String funcCode) {
        this.funcCode = funcCode;
    }

    @Override
    public String toString() {
        return  "\n  \"" + routeName + "\": {\n" +
                "    \"url\": \"" + url +"\",\n" +
                "    \"templateUrl\": \"" + templateUrl + "\",\n" +
                "    \"controller\": \"" + controller + "\",\n" +
                "    \"data\": \"" + data + "\",\n" +
                "    \"funcCode\": \"" + funcCode + "\",\n" +
                "  }";
    }
}
