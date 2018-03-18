package com.itheima.crm.service.impl;


import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.ObjectUtils.Null;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午1:33:52 <br/>       
 */
@Transactional
@Service
public class CustomerServiceImpl implements CustomerService{

	@Resource
	private CustomerRepository customerReposiyory;

	

	@Override
	public String findAll(Integer page, Integer rows) {
		
		Pageable pageable =new  PageRequest(page-1, rows); 
		Page<Customer> findAll = customerReposiyory.findAll(pageable);
		System.out.println(findAll.getContent().size());
		
		long totalElements = findAll.getTotalElements();
		List<Customer> content = findAll.getContent();
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalElements);
		map.put("rows", content);
		
		JsonConfig config = new JsonConfig();  
		JsDateJsonValueProcessor jsonValueProcessor = new JsDateJsonValueProcessor();  
        config.registerJsonValueProcessor(Date.class, jsonValueProcessor);  
		
		
		
		String string = JSONObject.fromObject(map,config).toString();
		
		System.out.println(string);
		
		return string;
	}



	@Override
	public List<Customer> queryNoAssociationCustomer() {
		return customerReposiyory.findByFixedAreaIdIsNull();
	}



	@Override
	public List<Customer> queryAssociationCustomer(String id) {
		System.out.println(customerReposiyory.findByFixedAreaId(id));
		return customerReposiyory.findByFixedAreaId(id);
	}



	@Override
	public void assignCustomers2FixedArea(List<Long> ids,String fixedAreaId) {
		
		System.out.println("集合:"+ids);
		System.out.println("fixedAreaId:"+fixedAreaId);
		
		if(fixedAreaId!=null){
			customerReposiyory.updateFixedAreaIdIsNull(fixedAreaId);
			if(ids.size()!=0){
				for (Long long1 : ids) {
					customerReposiyory.updateCustomeTheFixedAreaId(long1,fixedAreaId);
				}
				
			}
		}
		
		
	}

}
  









