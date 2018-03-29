package com.itheima.protal.web.action;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.utils.Constant;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;

/**
 * ClassName:OrderAction <br/>
 * Function: <br/>
 * Date: 2018年3月23日 上午1:46:45 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype") // prototype
@Controller
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

	
	
	
	private String recAreaInfo;
	private String sendAreaInfo;
	
	public void setRecAreaInfo(String recAreaInfo) {
		this.recAreaInfo = recAreaInfo;
	}

	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}

	private Order model=new Order();
	                 
	@Action(value = "orderAction_add", results = {
			@Result(location = "/index.html", name = "save_success", type = "redirect") })
	public String add() {

		if (!StringUtils.isEmpty(recAreaInfo)) {
			String[] split = recAreaInfo.split("/");
			Area recArea = new Area();
			recArea.setProvince(split[0]);
			recArea.setCity(split[1]);
			recArea.setDistrict(split[2]);
			model.setRecArea(recArea);
			
		}
		if (!StringUtils.isEmpty(sendAreaInfo)) {
			String[] split = sendAreaInfo.split("/");
			Area sendArea = new Area();
			sendArea.setProvince(split[0]);
			sendArea.setCity(split[1]);
			sendArea.setDistrict(split[2]);
			model.setSendArea(sendArea);
		}
		
		model.setOrderTime(new Date());
		
		model.setOrderNum(UUID.randomUUID().toString().replace("-", ""));
		
		
		WebClient.create("http://localhost:8080/bos_management_web/bosWebService/orderService/add")
		         .accept(MediaType.APPLICATION_JSON)
		         .type(MediaType.APPLICATION_JSON)
		         .post(model);
		
		System.out.println(model);
		
		return Constant.SAVE_SUCCESS;
	}

	@Override
	public Order getModel() {
		return model;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
