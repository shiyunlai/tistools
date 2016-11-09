package com.bos.tis.tools.service.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bos.tis.tools.dao.mapper.FeatureRegMapper;
import com.bos.tis.tools.dao.model.FeatureReg;

@Service
public class FeatureRegService {
	@Autowired
	FeatureRegMapper featureRegMapper;
	
	public void insert(FeatureReg fg){
		featureRegMapper.insert(fg);
    }
}
