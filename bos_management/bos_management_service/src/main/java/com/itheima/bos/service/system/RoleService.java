package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:54:19 <br/>       
 */
public interface RoleService {

	Page<Role> findAll(Pageable pageable);

	void save(Role model, String menuIds, List<Long> permissionIds);

}
  
