package com.itheima.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.system.Menu;
import com.itheima.bos.system.Permission;
import com.itheima.bos.system.Role;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:54:25 <br/>       
 */

@Transactional
@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

	@Override
	public void save(Role model, String menuIds, List<Long> permissionIds) {
		roleRepository.save(model);
		String[] split = menuIds.split(",");
		
		for (int i = 0; i < split.length; i++) {
			Menu menu=new Menu();
			menu.setId(Long.parseLong(split[i]));
			model.getMenus().add(menu);
		}
		
		for (Long long1 : permissionIds) {
			Permission permission=new Permission();
			permission.setId(long1);
			model.getPermissions().add(permission);
		}
		
	}
	
	
}
  
