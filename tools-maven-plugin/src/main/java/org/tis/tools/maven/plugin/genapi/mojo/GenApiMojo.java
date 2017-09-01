package org.tis.tools.maven.plugin.genapi.mojo;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.tis.tools.maven.plugin.genapi.parse.GenShowDoc;

import java.io.File;


@Mojo(name = "gen-api")
public class GenApiMojo extends AbstractMojo {


    /**
     * -Djava.file.path，指定模型文件的全路径，告知程序模型定义文件位置<br/>
     * <br/>如：-Dmodel.file.path=/Users/megapro/Develop/tis/tools/tools-core/model/model.erm
     * <br/>不指定，系统默认为当前工程主路径下 model/ 目录，如： bronsp-service-org/model/
     */
    @Parameter( property = "java.file.path" )
    private String javaFilePath ;



    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (StringUtils.isBlank(javaFilePath)) {
            getLog().error("Not define this filePath !");
            return;
        }

        getLog().info("generate ShowDoc Api from JAVA Source :" + javaFilePath);

        File file = new File(javaFilePath);
        if(!file.exists()) {
            getLog().error("Not found JAVA Source in " + javaFilePath);
            return;
        }
        getLog().info("*********Start generate ShowDoc Api ! *****************");

        try {
            getLog().info(GenShowDoc.excute(javaFilePath));
            getLog().info("*********End generate ShowDoc Api ! *****************");
        } catch (Exception e) {
            getLog().error("Error when generate ShowDoc Api" + e.getMessage());
        }


    }
}
