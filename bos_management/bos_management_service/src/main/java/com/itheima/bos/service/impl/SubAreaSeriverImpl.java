package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.SubAreaSeriver;

/**
 * ClassName:SubAreaSeriverImpl <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午1:20:56 <br/>
 */
@Service
@Transactional
public class SubAreaSeriverImpl implements SubAreaSeriver {

	@Resource
	private SubAreaRepository subAreaRepository;

	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Override
	public void save(SubArea model) {
		subAreaRepository.save(model);
	}

	@Override
	public Page<SubArea> findBypage(Specification<SubArea> specification,
			Pageable pageable) {
		return subAreaRepository.findAll(specification, pageable);
	}

	@Override
	public List<SubArea> fnidByFixedAreaIdIsNull() {

		return subAreaRepository.queryFixedAreaIsNull();
	}

	@Override
	public List<SubArea> querySubAreaFixedArea(Long id) {
		return subAreaRepository.queryFixedArea(id);
	}

	@Override
	public void assignSubAreaFixedArea(List<Long> subAreaIds, Long id) {

		subAreaRepository.updateFixedAreaToNull();

		FixedArea fixedArea = fixedAreaRepository.findOne(id);

		for (Long long1 : subAreaIds) {
			SubArea subArea = subAreaRepository.findOne(long1);
			subArea.setFixedArea(fixedArea);
		}

	}

	@Test
    public void smallCase_01(){


		Integer integer=null;
		boolean b=integer==1;
	
	}

}
