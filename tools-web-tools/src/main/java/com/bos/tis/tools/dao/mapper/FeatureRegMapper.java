package com.bos.tis.tools.dao.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bos.tis.tools.dao.model.FeatureReg;

@Repository  
@Transactional 
public interface FeatureRegMapper {
	public List<FeatureReg> query(FeatureReg featureReg);
	
	public void insert(FeatureReg featureReg);
	
	public void delete(String wordId);
	
	public void update(FeatureReg featureReg);
}
