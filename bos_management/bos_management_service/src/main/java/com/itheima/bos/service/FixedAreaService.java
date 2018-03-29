package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;

/**
 * ClassName:FixedAreaService <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午4:15:31 <br/>
 */
public interface FixedAreaService {

	void save(FixedArea model);

	Page<FixedArea> pageFindAll(Pageable pageable);

	void associationCourierToFixedArea(Long id, Long courierId,
			Long takeTimeId);

}
