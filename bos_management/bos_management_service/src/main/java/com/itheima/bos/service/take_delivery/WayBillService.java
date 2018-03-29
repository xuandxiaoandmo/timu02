package com.itheima.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WaybillService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午8:19:04 <br/>       
 */
public interface WayBillService {

	void save(WayBill model);

	Page<WayBill> findAll(Pageable pageable);

}
  
