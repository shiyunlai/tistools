/**
 * 
 */
package org.tools.design.test.ac;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.rservice.ac.capable.IBeFocusedRService;
import org.tools.design.SpringJunitSupport;

/**
 * @author megapro
 *
 */
public class BeFocusedRServiceTest extends SpringJunitSupport {

	@Autowired
	IBeFocusedRService beFocusedRService ; 
	
	@Test
	public void useListParamAndReturn() throws ToolsRuntimeException{
		
		List<AcOperator> operators = new ArrayList<AcOperator>() ; 
		
		AcOperator o1 = new AcOperator() ; 
		o1.setGuid("123");
		o1.setOperatorName("操作员1");
		o1.setEndDate(new Date() );
		
		AcOperator o2 = new AcOperator() ; 
		o2.setGuid("234");
		o2.setOperatorName("操作员2");
		o2.setEndDate(new Date() );

		AcOperator o3 = new AcOperator() ; 
		o3.setGuid("345");
		o3.setOperatorName("操作员3");
		o3.setEndDate(new Date() );
		
		operators.add(o1);
		operators.add(o2);
		operators.add(o3);
		
		List<AcRole> backs = beFocusedRService.roles(operators) ; 
		
		for( AcRole o : backs ){
			System.out.println(o.toString());
		}
	}
}
