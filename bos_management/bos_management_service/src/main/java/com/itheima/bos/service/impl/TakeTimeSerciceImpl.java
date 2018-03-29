package com.itheima.bos.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.TakeTimeSercice;

/**
 * ClassName:TakeTimeSerciceImpl <br/>
 * Function: <br/>
 * Date: 2018年3月19日 上午11:38:28 <br/>
 */
@Transactional
@Service
public class TakeTimeSerciceImpl implements TakeTimeSercice {

	@Resource
	private TakeTimeRepository tackTimeRepository;
	

	@Override
	public List<TakeTime> findAll() {
		return tackTimeRepository.findAll();
	}

}
