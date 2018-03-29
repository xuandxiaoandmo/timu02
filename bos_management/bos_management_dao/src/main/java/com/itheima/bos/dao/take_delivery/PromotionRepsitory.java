package com.itheima.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:PromotionRepsitory <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:26:37 <br/>       
 */
public interface PromotionRepsitory extends JpaRepository<Promotion, Long> , JpaSpecificationExecutor<Promotion>{

}
  
