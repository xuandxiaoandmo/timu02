package com.itheima.bos.service.impl;


import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午5:46:03 <br/>       
 */
@Service
public class StandardServiceImpl implements StandardService{

	@Resource
	private StandardRepository standardRepository;
	

	@Override
	public void save(Standard model) {
		System.out.println(standardRepository.getClass().getName());
		standardRepository.save(model);
	}


	@Override
	public Page<Standard> pageFindAll(Pageable pageable) {
		return standardRepository.findAll(pageable);
	}


}
  
