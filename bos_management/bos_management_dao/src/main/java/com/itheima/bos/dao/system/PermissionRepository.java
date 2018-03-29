package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.system.Menu;
import com.itheima.bos.system.Permission;

/**  
 * ClassName:PermissionRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:06:40 <br/>       
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> , JpaSpecificationExecutor<Permission>{

	@Query("select p from Permission p inner join p.roles r inner join r.users u where u.id=?")
	List<Permission> queryByUid(Long id);


}
  
