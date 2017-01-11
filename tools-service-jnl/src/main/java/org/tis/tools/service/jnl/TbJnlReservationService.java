/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.jnl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.mapper.jnl.TbJnlReservationMapper;
import org.tis.tools.model.po.jnl.TbJnlReservation;


/**
 * 预约流水(tb_jnl_reservation)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbJnlReservationService {

	@Autowired
	TbJnlReservationMapper tbJnlReservationMapper;
	
    public void insert(TbJnlReservation t){
    	tbJnlReservationMapper.insert(t);
    }
    
    public void update(TbJnlReservation t){
    	tbJnlReservationMapper.update(t); 
    }
   
    public void updateForce(TbJnlReservation t){
    	tbJnlReservationMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbJnlReservationMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbJnlReservationMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbJnlReservation t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbJnlReservationMapper.updateByCondition(map); 
    }

    public List<TbJnlReservation> query(WhereCondition wc){
    	return tbJnlReservationMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbJnlReservationMapper.count(wc);
    }
   
    public TbJnlReservation loadById(String id){
    	return tbJnlReservationMapper.loadById(id);
    }
}
