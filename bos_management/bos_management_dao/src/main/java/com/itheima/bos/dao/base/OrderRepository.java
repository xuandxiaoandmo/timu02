package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 上午9:56:37 <br/>       
 */
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>{

}
  
