package com.itheima.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.PromotionRepsitory;
import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;

/**  
 * ClassName:PromotionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:25:11 <br/>       
 */
@Service
@Transactional
public class PromotionServiceImpl implements PromotionService{

	@Autowired
	private PromotionRepsitory promotionRepsitory;

	@Override
	public void save(Promotion model) {
		promotionRepsitory.save(model);
	}
	
	
	
}
  
