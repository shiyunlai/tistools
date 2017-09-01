package org.tis.tools.maven.plugin.genapi.visitor;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.javadoc.JavadocBlockTag;
import org.tis.tools.maven.plugin.genapi.model.*;

import java.util.List;

/**
 * 接口遍历器
 */
public class InterfaceVisitor extends VoidVisitorAdapter<Void> {

    private InterfaceNode interfaceNode = new InterfaceNode();

    @Override
    public void visit(PackageDeclaration n, Void arg) {
        interfaceNode.setInterfacePackage(n.getName().toString());
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        interfaceNode.setInterfaceName(n.getName().getIdentifier());
        n.getJavadoc().ifPresent(d -> interfaceNode.setInterfaceDesc(d.getDescription().toText()));
        super.visit(n, arg);
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {

        MethodNode methodNode = new MethodNode();

        n.getJavadoc().ifPresent(d -> {
            methodNode.setMethodDsec(d.getDescription().toText());
            List<JavadocBlockTag> blockTags = d.getBlockTags();
            if(blockTags != null){
                d.getBlockTags().stream()
                        .forEach(t ->{
                            if(t.getTagName().equals("param")) {
                                t.getName().ifPresent(tt -> {
                                    ParamTag tag = new ParamTag();
                                    tag.setParamName(t.getName().get());
                                    tag.setParamDesc(t.getContent().toText());
                                    methodNode.addParamTag(tag);
                                });
                            }
                            if(t.getTagName().equals("return")) {
                                t.getName().ifPresent(tt -> {
                                    methodNode.setReturnDesc(t.getName().get());
                                });
                            }
                            if(t.getTagName().equals("throws")) {
                                ThrowsTag throwsTag = new ThrowsTag();
                                throwsTag.setThrowsDesc(t.getContent().toText());
                                methodNode.addThorwsTag(throwsTag);
                            }
                        });
            }
        });
        methodNode.setMethodName(n.getNameAsString());
        methodNode.setMethodDeclaration(n.getDeclarationAsString());
        n.getParameters().forEach(p -> {
            ParamNode node = new ParamNode();
            node.setParamName(p.getName().getIdentifier());
            node.setParamType(p.getType().asString());
            methodNode.addParamNode(node);
        });
        methodNode.setReturnType(n.getType().asString());

        n.getThrownExceptions().forEach(e -> {
            ThrowsNode node = new ThrowsNode();
            node.setThrowsType(e.getElementType().asString());
            methodNode.addThorwsNode(node);
        });
        interfaceNode.addMethodNode(methodNode);
        super.visit(n, arg);
    }

    public InterfaceNode getInterfaceNode() {
        return interfaceNode;
    }
}
