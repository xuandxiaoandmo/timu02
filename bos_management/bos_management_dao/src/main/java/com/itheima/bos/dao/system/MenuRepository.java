package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.system.Menu;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 上午10:54:27 <br/>       
 */
public interface MenuRepository extends JpaRepository<Menu, Long> , JpaSpecificationExecutor<Menu>{

	List<Menu> findByParentMenuIsNull();

	@Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?")
	List<Menu> findbyUid(Long id);


}
  
