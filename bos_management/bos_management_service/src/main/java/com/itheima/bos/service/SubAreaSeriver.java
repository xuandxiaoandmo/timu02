package com.itheima.bos.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaSeriver <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午1:20:47 <br/>       
 */
public interface SubAreaSeriver {

	void save(SubArea model);

	Page<SubArea> findBypage(Specification<SubArea> specification, Pageable pageable);



	List<SubArea> fnidByFixedAreaIdIsNull();

	List<SubArea> querySubAreaFixedArea(Long id);

	void assignSubAreaFixedArea(List<Long> subAreaIds, Long id);

}
  
