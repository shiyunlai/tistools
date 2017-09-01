package org.tis.tools.maven.plugin.genapi.parse;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.tis.tools.maven.plugin.genapi.model.InterfaceNode;
import org.tis.tools.maven.plugin.genapi.model.ShowDocRequest;
import org.tis.tools.maven.plugin.genapi.util.HttpRequest;
import org.tis.tools.maven.plugin.genapi.visitor.InterfaceVisitor;

import java.io.FileInputStream;

public class GenShowDoc {

    private static final String api_key = "ae7c6a6d8faf404ee11ce659d384b58129868472";

    private static final String api_token = "947c8e0f68e6599618ceae7965746dfe1794385149";

    private static final String api_url = "http://www.bronsp.com:8060/server/index.php?s=/api/item/updateByApi";

    public static String  excute(String filePath)  throws Exception {

        FileInputStream in = new FileInputStream(filePath);

        // parse it
        CompilationUnit cu = JavaParser.parse(in);
        // visit and print the methods names
        InterfaceVisitor visitor = new InterfaceVisitor();
        visitor.visit(cu, null);

        InterfaceNode interfaceNode = visitor.getInterfaceNode();

        ShowDocRequest req = new ShowDocRequest();
        req.setApi_key(api_key);
        req.setApi_token(api_token);
        req.setCat_name(interfaceNode.getInterfacePackage().substring(0,interfaceNode.getInterfacePackage().lastIndexOf(".")));
        req.setCat_name_sub("");
        req.setPage_content(interfaceNode.generateMD());
        req.setPage_title(interfaceNode.getInterfaceName());
        req.setS_number("1");

        return HttpRequest.sendPost(api_url, req.toString());

    }
}
