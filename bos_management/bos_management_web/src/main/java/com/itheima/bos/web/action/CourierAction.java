package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.CourierService;
import com.itheima.bos.utils.Constant;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2018年3月14日 下午4:00:49 <br/>
 */
@Scope("prototype")
@Controller()
@ParentPackage("struts-default")
@Namespace(value = "/")
public class CourierAction extends ActionSupport
		implements ModelDriven<Courier> {

	private Courier model = new Courier();

	@Resource
	private CourierService courierService;

	@Override
	public Courier getModel() {
		return model;
	}

	// @Action(value = "standardAction_save", results = {
	// @Result(name = "save_success", location = "/pages/base/standard.html",
	// type = "redirect") })
	@Action(value = "courierAction_save", results = {
			@Result(location = "/pages/base/courier.html", name = "save_success", type = "redirect") })
	public String save() {
		courierService.save(model);
		return Constant.SAVE_SUCCESS;
	}

	private int page;
	private int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	// courierAction_pageFindAll
	@Action(value = "courierAction_pageFindAll")
	public String pageFindAll() throws IOException {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Courier> pageFindAll = courierService.pageFindAll(pageable);

		long totalElements = pageFindAll.getTotalElements();
		List<Courier> content = pageFindAll.getContent();

		Map<String, Object> map = new HashMap<>();

		map.put("total", totalElements);
		map.put("rows", content);

		/*
		 * 
		 * 
		 * // 灵活控制输出的内容 JsonConfig jsonConfig = new JsonConfig();
		 * jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});
		 * 
		 * String json = JSONObject.fromObject(map, jsonConfig).toString();/
		 */
		JsonConfig config = new JsonConfig();
		// 设置不包括
		config.setExcludes(new String[] { "takeTime", "fixedAreas" });
		String string = JSONObject.fromObject(map, config).toString();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(string);

		return NONE;
	}

	private String ids;

	public void setIds(String ids) {
		this.ids = ids;
	}

	// deledtById
	@Action(value = "courierAction_deledtById", results = {
			@Result(location = "/pages/base/courier.html", name = "delete_success", type = "redirect") })
	public String deledtById() {
		System.out.println(ids);
		courierService.deledtById(ids);
		return Constant.DELETE_SUCCESS;
	}
	
	
	
//	reductionById
	
	@Action(value = "courierAction_reductionById", results = {
			@Result(location = "/pages/base/courier.html", name = "reduction_success", type = "redirect") })
	public String reductionById() {
		courierService.reductionById(ids);
		return Constant.REDUCTION_SUCCESS;
	}
	
	
	
	
	
	
	
	

}
