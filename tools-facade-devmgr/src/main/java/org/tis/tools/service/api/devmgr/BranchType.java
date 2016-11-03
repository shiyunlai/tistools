/**
 * 
 */
package org.tis.tools.service.api.devmgr;

import java.io.Serializable;


/**
 * <pre>
 * 分支类型
 * hotfix 紧急功能分支，一般用于发布紧急功能
 * bug 修复分支，一般用于bug修复开发
 * faeture 特性开发分支，一般用于新功能的开发
 * </pre>
 * 
 * @author megapro
 *
 */
public interface BranchType extends Serializable{
	/** hotfix 紧急功能分支，一般用于发布紧急功能 */
	public static final String DEV_BRNACH_HOTFIX = "HOTFIX_" ; 
	/** bug 修复分支，一般用于bug修复开发 */
	public static final String DEV_BRNACH_BUG = "BUG_" ; 
	/** 特性开发分支，一般用于新功能的开发 */
	public static final String DEV_BRNACH_FEATURE = "FEATURE_" ; 
}
