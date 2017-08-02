/**
 * 
 */
package org.tis.tools.rservice.ac.befocused;

import java.util.ArrayList;
import java.util.List;

import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.rservice.ac.capable.IBeFocusedRService;

import com.alibaba.fastjson.JSONObject;

/**
 * @author megapro
 *
 */
public class BeFocusedRService implements IBeFocusedRService {

	@Override
	public List<AcRole> roles(List<AcOperator> operators) {
		
		showOperators(operators) ;
		
		AcRole ar1 = new AcRole() ;
		ar1.setGuid("123456789");
		ar1.setGuidApp("1234567890");
		ar1.setRoleCode("R001");
		ar1.setRoleName("角色1");
		ar1.setRoleDesc("测试角色1");
		ar1.setRoleType("Type1");
		
		AcRole ar2 = new AcRole() ; 
		ar2.setGuid("23456789");
		ar2.setGuidApp("234567890");
		ar2.setRoleCode("R002");
		ar2.setRoleName("角色2");
		ar2.setRoleDesc("测试角色2");
		ar2.setRoleType("Type2");
		
		List<AcRole> backs = new ArrayList<AcRole>() ; 
		backs.add(ar1) ; 
		backs.add(ar2) ;
		
		showRoles(backs);
		
		return backs;
	}
	
	private void showOperators(List<AcOperator> operators) {
		
		for( AcOperator o : operators ){
			System.out.println(o.toString());
		}
	}
	
	private void showRoles(List<AcRole> roles) {
		
		for( AcRole r : roles ){
			System.out.println(r.toString());
		}
	}
}
