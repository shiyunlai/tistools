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

import org.tis.tools.dao.mapper.jnl.TbJnlVoucherMapper;
import org.tis.tools.model.po.jnl.TbJnlVoucher;


/**
 * 凭证流水(tb_jnl_voucher)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbJnlVoucherService {

	@Autowired
	TbJnlVoucherMapper tbJnlVoucherMapper;
	
    public void insert(TbJnlVoucher t){
    	tbJnlVoucherMapper.insert(t);
    }
    
    public void update(TbJnlVoucher t){
    	tbJnlVoucherMapper.update(t); 
    }
   
    public void updateForce(TbJnlVoucher t){
    	tbJnlVoucherMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbJnlVoucherMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbJnlVoucherMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbJnlVoucher t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbJnlVoucherMapper.updateByCondition(map); 
    }

    public List<TbJnlVoucher> query(WhereCondition wc){
    	return tbJnlVoucherMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbJnlVoucherMapper.count(wc);
    }
   
    public TbJnlVoucher loadById(String id){
    	return tbJnlVoucherMapper.loadById(id);
    }
}
