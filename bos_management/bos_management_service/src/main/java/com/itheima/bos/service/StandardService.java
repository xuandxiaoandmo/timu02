package com.itheima.bos.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午5:45:44 <br/>       
 */
public interface StandardService {


	void save(Standard model);

	Page<Standard> pageFindAll(Pageable pageable);


}
  
