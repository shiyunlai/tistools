/**
 * 
 */
package org.tis.tools.service.api.devmgr;

import java.util.List;

import org.tis.tools.model.vo.devmgr.FeatureReg;

/**
 * 
 * FeatureReg服务接口
 * 
 * @author megapro
 *
 */
public interface FeatureRegRemoteService {

	public List<FeatureReg> query(FeatureReg featureReg);
	
	public void insert(FeatureReg featureReg);
	
	public void delete(String wordId);
	
	public void update(FeatureReg featureReg);
}
