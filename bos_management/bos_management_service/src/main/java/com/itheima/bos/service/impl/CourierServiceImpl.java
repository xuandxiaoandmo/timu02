package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午4:07:17 <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService{

	@Resource
	private CourierRepository courierRepository;
	
	
	@Override
	public void save(Courier model) {
		courierRepository.save(model);
	}


	@Override
	public Page<Courier> pageFindAll(Pageable pageable) {
		return courierRepository.findAll(pageable);
	}


	@Override
	public void deledtById(String ids) {
		String[] split = ids.split(",");
		for (String string : split) {
			courierRepository.deledtById(Long.parseLong(string));
		}
	}


	@Override
	public void reductionById(String ids) {
		String[] split = ids.split(",");
		for (String string : split) {
			courierRepository.reductionById(Long.parseLong(string));
		}
	}

}
  
