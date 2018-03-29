package com.itheima.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:50:32 <br/>       
 */
public interface UserService {

	Page<User> findAll(Pageable pageable);

	void save(User model);

}
  
