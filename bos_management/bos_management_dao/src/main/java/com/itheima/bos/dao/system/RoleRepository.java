package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.system.Role;

/**  
 * ClassName:RoleRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:55:35 <br/>       
 */
public interface RoleRepository extends JpaRepository<Role, Long> , JpaSpecificationExecutor<Role>{

	@Query("select r from Role r inner join r.users u where u.id=?")
	List<Role> queryByUid(Long id);

}
  
