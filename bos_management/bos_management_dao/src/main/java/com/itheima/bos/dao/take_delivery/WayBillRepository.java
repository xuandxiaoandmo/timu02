package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.domain.take_delivery.WorkBill;

/**  
 * ClassName:WaybillRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午8:20:58 <br/>       
 */
public interface WayBillRepository extends JpaRepository<WayBill, Long> , JpaSpecificationExecutor<WayBill>{

}
  
