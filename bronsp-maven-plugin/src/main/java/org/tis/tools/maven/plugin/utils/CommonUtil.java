/**
 * 
 */
package org.tis.tools.maven.plugin.utils;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.maven.plugin.exception.GenDaoMojoException;


/**
 * 
 * 通用工具类（不好归类的都来这里）
 * 
 * @author megapro
 *
 */
public class CommonUtil {
	
	
	/**
	 * <pre>
	 * 将packageStr包路径字符串转换为规范的package包路径
	 * 1、认为 "-" 、"_" 、" "(空格) 均为分隔标志，转换为"."；
	 * 2、所有大写字母被统一为小写；
	 * 3、(注意：考虑语义，暂时未支持)驼峰规则会被转为 . 分隔，同时首字母大写被小写；
	 * 4、连续大写字母会被转换为小写；
	 * 5、过滤掉一些java关键字，过滤关键字见 keywords.properties；
	 * 如： 
	 * 	normPackageName( "asd_e123-ver bndk" ) 返回： asd.e123.ver.bndk
	 * 	normPackageName( "org.fone.BRONSP.bronsp-common-util" ) 返回： org.fone.bronsp.bronsp.common.util
	 * 	normPackageName( "org.fone.try.bronSP-final-UtiL" ) 返回： org.fone.ttry.bronsp.ffinal.util
	 * 
	 * </pre>
	 * 
	 * @param packageStr
	 * @return 规范的package包路径字符串
	 */
	public static String normPackageName(String packageStr) {
		
		String mainPackage = StringUtils.trim(packageStr) ;
		mainPackage = StringUtils.lowerCase(mainPackage).replace("-", ".").replace("_",".").replace(" ", ".") ; 
		String[] sepPackage = mainPackage.split("\\.") ;
		StringBuffer packageName = new StringBuffer() ;
		int i = 1 ; 
		for(String s : sepPackage ){
			packageName.append(KeyWordUtil.instance.replace(s)) ; //替换package中的关键字
			if( i < sepPackage.length ){
				packageName.append(".") ;
			}
			i ++ ; 
		}
		return packageName.toString() ; 
	}
	
	private static final String MAVEN_PATH_SRC_TAG = File.separator+"src"+File.separator ;//maven工程的源码路径标志
	
	/**
	 * </br>基于maven工程路径，替换其中的工程名称
	 * @param mavenPrjName maven工程全路径
	 * @param replacePrjName 替换为工程名
	 * @return 替换后的工程全路径 
	 * </br>如： replacePrjNameInMaven(null,"new-biztrace") </br>抛例外
	 * </br>如： replacePrjNameInMaven("/User/tis/tools-service-biztrace/src/main",null) </br>抛例外
	 * </br>如： replacePrjNameInMaven("/User/tis/tools-service-biztrace/GGG/main/java/","new-biztrace") 不是正常maven项目 </br>抛例外
	 * </br>如： replacePrjNameInMaven("/User/tis/tools-service-biztrace/src/main/java/","new-biztrace") </br>将返回 "/User/tis/new-biztrace/src/main/java/"
	 * @throws GenDaoMojoException 
	 */
	public static String replacePrjNameInMaven(String mavenPrjName, String replacePrjName ) throws GenDaoMojoException {
		
		if( StringUtils.isEmpty(replacePrjName) ){
			throw new GenDaoMojoException("替换工程名不能为空！") ; 
		}
		
		if( StringUtils.isEmpty(mavenPrjName) ){
			throw new GenDaoMojoException("被替换工程路径不能为空！") ; 
		}
		
		int index = mavenPrjName.indexOf(MAVEN_PATH_SRC_TAG) ; 
		if( index == -1 ){ 
			throw new GenDaoMojoException("路径中没有src目录，为非标准maven工程！") ; 
		}
		
		// 截取 /src/ 前半截路径
		String forwardPath = mavenPrjName.substring(0, index) ;
		
		// 取得工程名称 /.../工程名称/src/..../ ，位于 src 前一段路径极为工程名称
		int indexPrj = forwardPath.lastIndexOf(File.separator);
		forwardPath = forwardPath.substring(0, indexPrj+1) ;//indexPrj+1 把路径最后的／符号也取得

		// 截取 /src/ 后半截路径
		String behindPath = mavenPrjName.substring(index, mavenPrjName.length()) ;
		
		// 替换工程名之后的全路径
		String newPath = forwardPath + replacePrjName + behindPath  ;
		
		return newPath ; 
		
	}

	/**
	 * </br>把包名转换为对应的目录字符串 </br>如： com.abc.kkk --> com/abc/kkk/ </br>com -->
	 * com/
	 * 
	 * @param packageName
	 * @return
	 */
	public static String package2Path(String packageName ) {
		
		return packageName.replace(".", File.separator) + File.separator; 
	}

	private static Pattern line1Pattern = Pattern.compile("_(\\w)");  
	private static Pattern line2Pattern = Pattern.compile("-(\\w)");  
	private static Pattern line3Pattern = Pattern.compile("\\.(\\w)");  
	private static Pattern humpPattern = Pattern.compile("[A-Z]");  
	
	/**
	 * 下划线/中横线/点分隔的字符串转驼峰 <br/>
	 * 如：a_b_cD -> ABCd <br/>
	 * 如：A_b-cd -> ABCd <br/>
	 * 如：a.b-cd_Mn -> ABCdMn
	 * 
	 * @param lineStr
	 * @return
	 */
	public static String line2Hump(String lineStr){
		
		if( StringUtils.isEmpty( lineStr) ){
    		return lineStr ; 
    	}
		
		//转为小写
		lineStr = lineStr.toLowerCase();
        
        //处理下划线
        Matcher matcher = line1Pattern.matcher(lineStr);  
        StringBuffer sb1 = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb1, matcher.group(1).toUpperCase());  
        }  
        matcher.appendTail(sb1);
        //System.out.println(sb1.toString());
        
        //处理中划线
        matcher = line2Pattern.matcher(sb1.toString());  
        StringBuffer sb2 = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb2, matcher.group(1).toUpperCase());  
        }  
        matcher.appendTail(sb2);
        //System.out.println(sb2.toString());
        
        //处理 . 号
        matcher = line3Pattern.matcher(sb2.toString());  
        StringBuffer sb3 = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb3, matcher.group(1).toUpperCase());  
        }  
        matcher.appendTail(sb3);
        //System.out.println(sb3.toString());
        
        return sb3.toString();  
    }

	/**
	 * 驼峰转下划线(简单写法，效率低于{@link #humpToLine2(String)})
	 * 
	 * @param str
	 * @return
	 */
	@Deprecated
	public static String hump2Line2(String str) {
		return str.replaceAll("[A-Z]", "_$0").toLowerCase();
	}

	/**
	 * 驼峰转下划线分隔字符串,效率比上面高
	 * 
	 * @param humpStr
	 *            驼峰规则的字符串
	 * @return 以下划线分隔的字符串 如： ABcd -> a_bcd
	 */
	public static String hump2Line(String humpStr) {

		if (StringUtils.isEmpty(humpStr)) {
			return humpStr;
		}

		Matcher matcher = humpPattern.matcher(humpStr);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);

		// 去掉首位下划线
		if (sb.charAt(0) == '_') {
			sb.delete(0, 1);
		}

		return sb.toString();
	}

	/**
	 * 根据源码路径，返回主工程路径
	 * 
	 * @param sourcePath
	 *            源码路径
	 * @return <br/>
	 *         如： <br/>
	 *         "/User/Develop/bronsp-abc/src/main/java" 返回
	 *         "/User/Develop/bronsp-abc/" <br/>
	 *         "" 返回 "" ; <br/>
	 *         "/User/Develop/bronsp-abc/test/" 返回
	 *         "/User/Develop/bronsp-abc/test/" ;
	 */
	public static String getProjectPathBySource(String sourcePath) {

		if (StringUtils.isEmpty(sourcePath)) {
			return sourcePath;
		}

		if (sourcePath.contains("src"+File.separator)) {

			return sourcePath.substring(0, sourcePath.indexOf("src"+File.separator));// 路径中有src，则截取src之前的部分返回

		} else {

			return sourcePath; // 路径中没有src目录，则原样返回
		}

	}
	
}
