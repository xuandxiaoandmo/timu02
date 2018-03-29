package com.itheima.bos.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.FixedAreaService;

/**
 * ClassName:FixedAreaServiceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午4:15:39 <br/>
 */
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {

	@Resource
	private FixedAreaRepository fixedAreaRepository;
	@Resource
	private CourierRepository courierRepository;
	@Resource
	private TakeTimeRepository takeTimeRepository;

	
	
	@Override
	public void save(FixedArea model) {
		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> pageFindAll(Pageable pageable) {
		return fixedAreaRepository.findAll(pageable);
	}

	@Override
	public void associationCourierToFixedArea(Long id, Long courierId,Long takeTimeId) {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(id);
		System.out.println(courierId);
		System.out.println(takeTimeId);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		FixedArea fixedArea = fixedAreaRepository.findOne(id);
		Courier courier = courierRepository.findOne(courierId);
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		
		courier.setTakeTime(takeTime);
		fixedArea.getCouriers().add(courier);
		//持久态对象可以自动保存数据
		
	}
	
//	public static void findOne(Long idLong) {
//		
//	}
	
	
	
	
	
	

}
