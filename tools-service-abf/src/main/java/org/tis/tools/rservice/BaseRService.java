/**
 * 
 */
package org.tis.tools.rservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.tis.tools.service.base.SequenceService;

/**
 * 
 * 远程服务抽象类
 * @author megapro
 *
 */
public abstract class BaseRService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected TransactionTemplate transactionTemplate;
	
	@Autowired
	protected SequenceService sequenceService ;
	
}
