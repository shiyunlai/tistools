package org.tis.tools.maven.plugin.genapi.parse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.tis.tools.maven.plugin.genapi.model.InterfaceNode;
import org.tis.tools.maven.plugin.genapi.model.ShowDocRequest;
import org.tis.tools.maven.plugin.genapi.util.HttpRequest;
import org.tis.tools.maven.plugin.genapi.visitor.InterfaceVisitor;

import java.io.FileInputStream;

public class InterfaceParse {

    public static void main(String[] args) throws Exception {

        String filePath = "E:\\tools\\tistools\\tools-facade-ac\\src\\main\\java\\org\\tis\\tools\\rservice\\ac\\capable\\IMenuRService.java";

        System.out.println(GenShowDoc.excute(filePath));

    }
}
