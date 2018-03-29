package com.itheima.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午4:07:03 <br/>       
 */
public interface CourierService {

	void save(Courier model);

	Page<Courier> pageFindAll(Specification<Courier> specification, Pageable pageable);

	void deledtById(String string);

	void reductionById(String ids);

	List<Courier> findByDeltagIsNull();

}
  
