package com.itheima.bos.web.action.take_delivery;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.filefilter.AgeFileFilter;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;
import com.itheima.bos.web.action.IdenticalAction;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JsonConfig;

/**  
 * ClassName:waybillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午8:06:55 <br/>       
 */

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WaybillAction extends IdenticalAction<WayBill>{

	public WaybillAction() {
		super(WayBill.class);  
	}

	@Autowired
	private WayBillService wayBillService;
	
	@Action("wayBillAction_save")
	public String save() throws IOException{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		try {
			wayBillService.save(model);
			response.getWriter().write("0");
		} catch (Exception e) {
			response.getWriter().write("1");
			e.printStackTrace();  
		}
		return NONE;
	}
	
	@Action("wayBillAction_findPageAll")
	public String findPageAll() throws IOException {

		Pageable pageable=new PageRequest(page-1, rows);
		Page<WayBill>page=wayBillService.findAll(pageable);
		
		pageFindAll(null, page);
		
		return NONE;
	}
	
	
	
}
  
