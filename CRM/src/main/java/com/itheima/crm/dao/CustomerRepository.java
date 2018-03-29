package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午1:47:42 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>,JpaSpecificationExecutor<Customer>{

	List<Customer> findByFixedAreaIdIsNull();

	List<Customer> findByFixedAreaId(String id);

	
	
	@Modifying
	@Query("update Customer set fixedAreaId =null where fixedAreaId=?")
	void updateFixedAreaIdIsNull(String fixedAreaId);

	
	@Modifying
	@Query("update Customer set fixedAreaId = ?2 where id = ?1")
	void updateCustomeTheFixedAreaId(Long long1, String fixedAreaId);

	Customer findByTelephone(String telephone);

	Customer findByTelephoneAndPassword(String telephone, String password);

	Customer findByAddress(String address);

	
	
	
	
	
}
  
