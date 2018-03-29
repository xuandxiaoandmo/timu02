package com.itheima.bos.web.action.beas;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.FixedAreaService;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;
import com.itheima.crm.domain.Customer;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午4:13:49 <br/>
 */
@SuppressWarnings({ "unchecked", "unused", "serial" })
@Namespace("/")
@Controller()
@Scope("prototype")
@ParentPackage("struts-default")
public class FixedAreaAction extends IdenticalAction<FixedArea> {

	public FixedAreaAction() {
		super(FixedArea.class);
	}

	@Resource
	private FixedAreaService fixedAreaService;

	@Action(value = "fixedAreaAction_save", results = {
			@Result(location = "/pages/base/fixed_area.html", name = "save_success", type = "redirect") })
	public String save() {

		fixedAreaService.save(model);
		return Constant.SAVE_SUCCESS;
	}

	// fixedAreaAction_pageQuery
	@Action(value = "fixedAreaAction_pageQuery")
	public String pageFindAll() throws IOException {

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<FixedArea> pageFindAll = fixedAreaService.pageFindAll(pageable);

		JsonConfig config = new JsonConfig();
		JsDateJsonValueProcessor jsonValueProcessor = new JsDateJsonValueProcessor();
		config.registerJsonValueProcessor(Date.class, jsonValueProcessor);
		config.setExcludes(new String[] { "subareas", "couriers" });

		pageFindAll(config, pageFindAll);

		return NONE;
	}

	// fixedAreaAction_queryNoAssociationCustomer
	/**
	 * 此方法用于查询没有与定区关联的客户 queryNoAssociationCustomer:. <br/>
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "fixedAreaAction_queryNoAssociationCustomer")
	public String queryNoAssociationCustomer() throws IOException {
		List<Customer> collection = (List<Customer>) WebClient
				.create("http://localhost:8180/CRM/webService/customerService/queryNoAssociationCustomer")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).getCollection(Customer.class);

		handleJson(collection);
		return NONE;
	}

	// fixedAreaAction_queryAssociationCustomer
	/**
	 * 此方法用于查询与定区关联的客户 queryNoAssociationCustomer:. <br/>
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "fixedAreaAction_queryAssociationCustomer")
	public String queryAssociationCustomer() throws IOException {
		System.out.println(model.getId());
		List<Customer> collection = (List<Customer>) WebClient
				.create("http://localhost:8180/CRM/webService/customerService/queryAssociationCustomer")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.query("id", model.getId() + "").getCollection(Customer.class);
		System.out.println(collection);
		handleJson(collection);
		return NONE;
	}

	private List<Long> customerIds = new ArrayList<>();

	public void setCustomerIds(List<Long> customerIds) {
		this.customerIds = customerIds;
	}

	/**
	 * 此方法用于客户与定区关联和解除关联 associationCourierToFixedArea:. <br/>
	 * 
	 * @return
	 * @throws IOException
	 */
	@Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
			@Result(location = "/pages/base/fixed_area.html", name = "update_success", type = "redirect") })
	public String assignCustomers2FixedArea() throws IOException {
		System.out.println(model + ":::assignCustomers2FixedArea");
		System.out.println("customerIds:" + customerIds);
		WebClient
				.create("http://localhost:8180/CRM/webService/customerService/assignCustomers2FixedArea")
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON).query("list", customerIds)
				.query("fixedAreaId", model.getId()).put(null);

		return Constant.UPDATE_SUCCESS;
	}

	
	             
	private Long courierId;
	private Long takeTimeId;
	
	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Long takeTimeId) {
		this.takeTimeId = takeTimeId;
	}

	// fixedAreaAction_associationCourierToFixedArea
	/**
	 * 此方法用于关联定区与快递员     快递员与上班时间的
	 * associationCourierToFixedArea:. <br/>  
	 *  
	 * @return
	 */
	@Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {
			@Result(location = "/pages/base/fixed_area.html", name = "association_success", type = "redirect") })
	public String associationCourierToFixedArea() {

		fixedAreaService.associationCourierToFixedArea(model.getId(),courierId,takeTimeId);
		
		
		return Constant.ASSOCIATION_SUCCESS;
	}

}
