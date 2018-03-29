package com.itheima.bos.web.action.beas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.web.action.IdenticalAction;
import com.itheima.crm.domain.Customer;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午1:49:38 <br/>       
 */

@SuppressWarnings("serial")
@Namespace("/")
@Controller()
@Scope("prototype")
@ParentPackage("struts-default")
public class CustomerAction extends IdenticalAction<Customer>{

	public CustomerAction() {
		super(Customer.class);  
	}
	
	

	@Action("customerAction_pageFindAll")
	
	public String pageFindAll() throws IOException{
		
		String string = WebClient.create("http://localhost:8180/CRM/webService/customerService/findAll")
		         .accept(MediaType.APPLICATION_JSON)
		         .type(MediaType.APPLICATION_JSON)
		         .query("page", 1)
		         .query("rows", 30)
		         .get(String.class);
		
		return NONE;
	}
	
    @Test
    public void smallCase_01(){
    	String string = WebClient.create("http://localhost:8180/CRM/webService/customerService/findAll")
		         .accept(MediaType.APPLICATION_JSON)
		         .type(MediaType.APPLICATION_JSON)
		         .query("page", 1)
		         .query("rows", 30)
		         .get(String.class);

    	System.out.println(string);

    }
	
	
}
  
