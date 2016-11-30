package org.tis.tools.common.utils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;



public class SvnUtil {
	
	private static String url = "http://48.1.1.62/svn/repos/tip";  
    private static SVNRepository repository = null;  
    private static String userName;
    private static String password;
    
    /**
     * 初始化参数
     */
    private static void setupLibrary() {  
        DAVRepositoryFactory.setup();  
        SVNRepositoryFactoryImpl.setup();  
        FSRepositoryFactory.setup();  
        try {  
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));  
        }  
        catch (SVNException e) {  
            e.printStackTrace();  
        }  
        // 身份验证  
        //ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("wuhong","1qaz2wsx");
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(userName,password);
        repository.setAuthenticationManager(authManager);  
    }  
    
    /**
     * 获取svn提交记录
     * @param userName	svn用户名
     * @param password	svn密码
     * @param pathFilter 获取时指定url下具体某个路径(如某个分支下具体某个包),为空时即获取url下全部提交记录
     * @return
     * @throws SVNException
     */
	public static List<String> getSvnCommitRecord(String userName,String password,String pathFilter) throws SVNException {
		SvnUtil.userName = userName;
		SvnUtil.password = password;
		
		setupLibrary();
		//System.out.println(pathFilter);
        //final String author = "";  
        long startRevision = 0;  
        long endRevision = -1;//表示最后一个版本  
        final List<String> history = new ArrayList<String>();  
        //String[] 为过滤的文件路径前缀，为空表示不进行过滤  
        repository.log(new String[]{pathFilter},  
                       startRevision,  
                       endRevision,  
                       true,  
                       true,  
                       new ISVNLogEntryHandler() {  
                           @Override  
                           public void handleLogEntry(SVNLogEntry svnlogentry)  
                                   throws SVNException {  
                        	   history.addAll(svnlogentry.getChangedPaths().keySet());
                           }						 
                       });  
		return history;       
	}
	
	
	public static ByteArrayOutputStream displayFile(String userName,String password,String fileName) throws SVNException {
		SvnUtil.userName = userName;
		SvnUtil.password = password;
		
		setupLibrary();

		//此变量用来存放要查看的文件的属性名/属性值列表。
	    SVNProperties fileProperties = new SVNProperties();
	    //此输出流用来存放要查看的文件的内容。
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		repository.getFile("development/branches/docs/A_project_development/02_development/05_patch_list/0111_SALM开发流程管理/List/"
				+ fileName,
				-1, fileProperties, baos);
		
		return baos;     
	}
}
