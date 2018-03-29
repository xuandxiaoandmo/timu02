package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.system.Menu;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 上午10:52:56 <br/>       
 */
public interface MenuService {

	List<Menu> findByParentMenuIsNull();

	void save(Menu model);

	Page<Menu> findAll(Pageable pageable);

	List<Menu> findbyUid(Long id);

}
  
