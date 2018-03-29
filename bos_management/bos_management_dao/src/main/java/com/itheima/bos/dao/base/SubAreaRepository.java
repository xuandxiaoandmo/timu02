package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;


/**  
 * ClassName:SubAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午1:22:09 <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>,JpaSpecificationExecutor<SubArea>{


	@Query("from SubArea where fixedArea.id is null")
	List<SubArea> queryFixedAreaIsNull();


	@Query("from SubArea where fixedArea.id =?")
	List<SubArea> queryFixedArea(Long id);


	@Modifying
	@Query("update  SubArea set fixedArea.id = null")
	void updateFixedAreaToNull();

}
  
