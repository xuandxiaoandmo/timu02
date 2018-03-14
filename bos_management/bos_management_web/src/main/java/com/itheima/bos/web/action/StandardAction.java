package com.itheima.bos.web.action;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.StandardService;
import com.itheima.bos.utils.Constant;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ClassName:StandardAction <br/>
 * Function: <br/>
 * Date: 2018年3月12日 下午5:30:39 <br/>
 */
@Scope("prototype")
@Controller()
@ParentPackage("struts-default")
@Namespace(value = "/")
public class StandardAction extends ActionSupport
		implements ModelDriven<Standard> {

	private Standard model = new Standard();

	@Resource
	private StandardService standardService;

	@Override
	public Standard getModel() {
		return model;
	}

	@Action(value = "standardAction_save", results = {
			@Result(name = "save_success", location = "/pages/base/standard.html", type = "redirect") })
	public String save() {
		System.out.println("model");
		System.out.println("进入");
		standardService.save(model);
		System.out.println("StandardName:"+model.getName());
		System.out.println("完成");
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

	@Action(value = "standardAction_pageFindAll")
	public String pageFindAll() throws IOException {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Standard> page_findAll = standardService.pageFindAll(pageable);

		long total = page_findAll.getTotalElements();
		List<Standard> content = page_findAll.getContent();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("total", total);
		map.put("rows", content);

		JSONObject fromObject = JSONObject.fromObject(map);
		String string = fromObject.toString();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		
		writer.write(string);
		
		return NONE;
	}
	
	@Action(value = "standardAction_findAll")
	public String findAll() throws IOException {
	
		Page<Standard> pageFindAll = standardService.pageFindAll(null);
		List<Standard> content = pageFindAll.getContent();
		
		String string = JSONArray.fromObject(content).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		PrintWriter writer = response.getWriter();
		
		writer.write(string);
		return NONE;
	}
	
	

}
