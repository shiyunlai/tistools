package org.tis.tools.maven.plugin.genapi.model;

import java.util.ArrayList;
import java.util.List;

public class InterfaceNode {

    private static String lineSeparator = System.getProperty("line.separator");

    private String interfaceName;
    private String interfaceDesc;
    private String interfacePackage;

    private List<MethodNode> methodNodes = new ArrayList<>();

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceDesc() {
        return interfaceDesc;
    }

    public void setInterfaceDesc(String interfaceDesc) {
        this.interfaceDesc = interfaceDesc;
    }

    public String getInterfacePackage() {
        return interfacePackage;
    }

    public void setInterfacePackage(String interfacePackage) {
        this.interfacePackage = interfacePackage;
    }

    public List<MethodNode> getMethodNodes() {
        return methodNodes;
    }

    public void addMethodNode(MethodNode node) {
        this.methodNodes.add(node);
    }

    @Override
    public String toString() {
        return "InterfaceNode{" +
                "interfaceName='" + interfaceName + '\'' +
                ", interfaceDesc='" + interfaceDesc + '\'' +
                ", interfacePackage='" + interfacePackage + '\'' +
                ", methodNodes=" + methodNodes +
                '}';
    }

    public String generateMD() {

        StringBuffer md = new StringBuffer();
        md.append("#### " + this.interfacePackage + " ####" ).append(lineSeparator)
                .append("## 接口 " + this.interfaceName + " ##").append(lineSeparator).append("\r\n")
                .append("**简要描述**").append(lineSeparator)
                .append(this.interfaceDesc).append(lineSeparator).append("\r\n")
                .append("**接口清单**").append(lineSeparator);

        for (MethodNode node : this.methodNodes) {
            md.append("- [" + node.getMethodDeclaration()).append("](#" + node.getMethodName() + ")").append(lineSeparator);
        }
        md.append(lineSeparator).append("## 详细说明 ##").append(lineSeparator).append(lineSeparator);
        for (MethodNode node : this.methodNodes) {
            md.append(lineSeparator).append(lineSeparator)
                    .append("#### " + node.getMethodName() + " ####").append(lineSeparator).append(lineSeparator)
                    .append("**方法说明**").append(lineSeparator)
                    .append("- " + node.getMethodDsec()).append(lineSeparator).append(lineSeparator)
                    .append("**参数**").append(lineSeparator).append(lineSeparator)
                    .append("|参数名|说明|").append(lineSeparator)
                    .append("|:----   |-----   |").append(lineSeparator);
            for(ParamTag paramTag : node.getParamTags()) {
                md.append("| " +
                        paramTag.getParamName() +
                        "  | " +
                        paramTag.getParamDesc() +
                        "  |").append(lineSeparator);
            }
            md.append("**返回**").append(lineSeparator).append(lineSeparator)
                    .append("|说明|").append(lineSeparator)
                    .append("|:-----  |").append(lineSeparator)
                    .append("| " + node.getReturnTag().getReturnDesc() + " |").append(lineSeparator)
                    .append("**抛出**").append(lineSeparator);
            for(ThrowsTag throwsTag : node.getThrowsTags()) {
                md.append("- " + throwsTag.getThrowsDesc()).append(lineSeparator);
            }
            md.append(lineSeparator).append("***").append(lineSeparator);


        }

        return md.toString().replaceAll("null", "无");
    }
}
