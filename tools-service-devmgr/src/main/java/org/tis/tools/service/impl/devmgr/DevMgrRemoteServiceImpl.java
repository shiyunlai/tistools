/**
 * 
 */
package org.tis.tools.service.impl.devmgr;

import java.util.ArrayList;
import java.util.List;

import org.tis.tools.base.Page;
import org.tis.tools.model.vo.devmgr.DevelopBranchInfo;
import org.tis.tools.service.api.devmgr.DevMgrRemoteService;

import com.alibaba.dubbo.config.annotation.Service;

/**
 * 
 * 开发管理服务实现
 * 
 * @author megapro
 *
 */
//@Service(group = "devmgr", version = "1.0", interfaceClass = DevMgrRemoteService.class, timeout = 2000, document = "开发管理服务")
public class DevMgrRemoteServiceImpl implements DevMgrRemoteService {

	public void add(DevelopBranchInfo branchInfo) {
		System.out.println("新增一条开发分支信息："+branchInfo);
	}

	public DevelopBranchInfo delete(String branchCode) {
		System.out.println("删除分支："+branchCode);
		DevelopBranchInfo b = new DevelopBranchInfo() ; 
		b.setBranchCode(branchCode);
		b.setBranchStatus("deleted");
		return b;
	}

	public DevelopBranchInfo update(DevelopBranchInfo branchInfo) {
		System.out.println("修改分支信息："+branchInfo);
		branchInfo.setBranchStatus("updated");
		return branchInfo;
	}

	public List<DevelopBranchInfo> queryList(Page page) {
		
		List<DevelopBranchInfo> l = new ArrayList<DevelopBranchInfo>() ; 
		DevelopBranchInfo b1 = new DevelopBranchInfo() ; 
		b1.setBranchCode("b1");
		l.add(b1) ; 
		DevelopBranchInfo b2 = new DevelopBranchInfo() ; 
		b2.setBranchCode("b2");
		l.add(b2) ; 
		return l;
	}

	public DevelopBranchInfo queryDtl(String branchCode) {
		
		DevelopBranchInfo db = new DevelopBranchInfo() ; 
		db.setBranchCode("detail");
		return db;
	}

	public String genWorkId(String workDesc) {
		
		return "Dev"+workDesc ; 
	}

	public String genBranchCode(String branchType) {
		System.out.println("当前调用类型为："+branchType);
		return branchType + "123" ; 
	}

}
