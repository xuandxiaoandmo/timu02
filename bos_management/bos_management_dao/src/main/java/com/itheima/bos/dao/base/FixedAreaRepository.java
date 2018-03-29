package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午4:17:18 <br/>       
 */
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long>, JpaSpecificationExecutor<FixedArea>{

}
  
