package org.tis.tools.maven.plugin.genapi.model;

import java.util.ArrayList;
import java.util.List;

public class MethodNode {

    /**
     * 方法说明
     **/
    private String methodDsec;

    /**
     * 方法声明
     **/
    private String methodDeclaration;

    /**
     * 方法返回类型
     **/
    private String returnType;

    /**
     * 方法名
     **/
    private String methodName;
    /**
     * 注释返回说明
     **/
    private String returnDesc;

    /**
     * 抛出异常名
     **/
    private String throwName;

    /**
     * 注释中的param参数注解
     **/
    private List<ParamTag> paramTags = new ArrayList<>();

    /**
     * 方法中的参数
     **/
    private List<ParamNode> paramNodes = new ArrayList<>();

    /**
     * 抛出异常注释
     */
    private List<ThrowsNode> throwsNodes = new ArrayList<>();

    /**
     * 方法声明抛出异常
     */
    private List<ThrowsTag> throwsTags = new ArrayList<>();

    /**
     * 方法中的返回参数
     **/
    private ReturnNode returnNode = new ReturnNode();

    public List<ThrowsTag> getThrowsTags() {
        return throwsTags;
    }

    public String getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(String methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
    }

    private ReturnTag returnTag = new ReturnTag();

    public ReturnTag getReturnTag() {
        return returnTag;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void addThorwsNode(ThrowsNode node) {
        this.throwsNodes.add(node);
    }

    public void addThorwsTag(ThrowsTag tag) {
        this.throwsTags.add(tag);
    }


    public ReturnNode getReturnNode() {
        return returnNode;
    }


    public void addParamNode(ParamNode node) {
        this.paramNodes.add(node);
    }

    public List<ParamNode> getParamNodes() {
        return paramNodes;
    }

    public void addParamTag(ParamTag tag) {
        this.paramTags.add(tag);
    }

    public List<ParamTag> getParamTags() {
        return paramTags;
    }

    public String getMethodDsec() {
        return methodDsec;
    }

    public void setMethodDsec(String methodDsec) {
        this.methodDsec = methodDsec;
    }


    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }


    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public String getThrowName() {
        return throwName;
    }

    public void setThrowName(String throwName) {
        this.throwName = throwName;
    }

    @Override
    public String toString() {
        return "{" +
                "methodName='" + methodName + '\'' +
                "methodDsec='" + methodDsec + '\'' +
                ", returnType='" + returnType + '\'' +
                ", returnDesc='" + returnDesc + '\'' +
                ", throwName='" + throwName + '\'' +
                ", paramTags=" + paramTags +
                ", paramNodes=" + paramNodes +
                ", throwsNodes=" + throwsNodes +
                ", throwsTags=" + throwsTags +
                ", returnNode=" + returnNode +
                '}';
    }
}
