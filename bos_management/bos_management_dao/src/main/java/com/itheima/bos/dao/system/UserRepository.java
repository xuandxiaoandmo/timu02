package com.itheima.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午2:08:29 <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User>{

	User findByUsername(String username);

}
  
