package com.itheima.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.WayBillRepository;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WaybillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午8:19:17 <br/>       
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService{

	@Autowired
	private WayBillRepository wayBillRepository;
	
	@Override
	public void save(WayBill model) {
		wayBillRepository.save(model); 
	}

	@Override
	public Page<WayBill> findAll(Pageable pageable) {
		return wayBillRepository.findAll(pageable);
	}

}
  
