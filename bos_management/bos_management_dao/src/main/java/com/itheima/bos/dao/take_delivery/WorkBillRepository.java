package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.take_delivery.WorkBill;

/**  
 * ClassName:WayBill <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午12:05:43 <br/>       
 */
public interface WorkBillRepository extends JpaRepository<WorkBill, Long> , JpaSpecificationExecutor<WorkBill>{

}
  
