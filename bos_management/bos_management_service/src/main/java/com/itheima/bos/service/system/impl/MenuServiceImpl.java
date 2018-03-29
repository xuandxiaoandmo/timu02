package com.itheima.bos.service.system.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.system.Menu;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 上午10:53:06 <br/>       
 */
@Transactional
@Service
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Menu> findByParentMenuIsNull() {
		return menuRepository.findByParentMenuIsNull();
	}

	@Override
	public void save(Menu model) {
		if(model.getParentMenu()!=null&&model.getParentMenu().getId()==null){
			model.setParentMenu(null);
		}
		System.out.println("保存了一个菜单");
		menuRepository.save(model);
	}

	@Override
	public Page<Menu> findAll(Pageable pageable) {
		return menuRepository.findAll(pageable);
	}

	@Override
	public List<Menu> findbyUid(Long id) {
		return menuRepository.findbyUid(id);
	}
	
	
}
  
