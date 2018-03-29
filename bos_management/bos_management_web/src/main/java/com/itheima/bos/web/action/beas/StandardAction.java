package com.itheima.bos.web.action.beas;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.itheima.bos.web.action.IdenticalAction;
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
public class StandardAction extends IdenticalAction<Standard> {


	public StandardAction() {
		super(Standard.class);  
	}

	@Resource
	private StandardService standardService;


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


	@RequiresPermissions("pageFindAll")
	@Action(value = "standardAction_pageFindAll")
	public String pageFindAll() throws IOException {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Standard> page_findAll = standardService.pageFindAll(pageable);

		pageFindAll(null, page_findAll);
		
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
