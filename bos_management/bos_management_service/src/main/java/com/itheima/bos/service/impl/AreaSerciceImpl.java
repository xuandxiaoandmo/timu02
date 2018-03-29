package com.itheima.bos.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaSercice;

/**  
 * ClassName:AreaSerciceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:51:30 <br/>       
 */
@Transactional
@Service
public class AreaSerciceImpl implements AreaSercice{

	@Resource
	private AreaRepository areaRepository;

	@Override
	public Page<Area> pageFindAll(Pageable pageable) {
		return areaRepository.findAll(pageable);
	}

	@Override
	public void save(Area model) {
		areaRepository.save(model);
	}

	@Override
	public void saveAll(ArrayList<Area> areas) {
		areaRepository.save(areas);
	}


	@Override
	public List<Area> findQ(String p) {
		return areaRepository.findQ(p);
	}
	
	
	
	
	
	
	
}
  
