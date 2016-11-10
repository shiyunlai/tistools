package org.tis.tools.dao.devmgr;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.tis.tools.model.vo.devmgr.FeatureReg;


@Repository  
@Transactional 
public interface FeatureRegMapper {
	public List<FeatureReg> query(FeatureReg featureReg);
	
	public void insert(FeatureReg featureReg);
	
	public void delete(String wordId);
	
	public void update(FeatureReg featureReg);
}
