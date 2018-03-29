package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.system.Permission;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:PermissionAction <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午4:03:12 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class PermissionAction extends IdenticalAction<Permission> {

	public PermissionAction() {
		super(Permission.class);
	}

	@Autowired
	private PermissionService permissionService;

	@Action(value = "permissionAction_pageFindAll")
	public String pageFindAll() throws IOException {

		Pageable pageable = new PageRequest(page - 1, rows);

		Page<Permission> pageList = permissionService.findAll(pageable);

		JsonConfig config = new JsonConfig();

		config.setExcludes(new String[] { "roles" });

		pageFindAll(config, pageList);

		return NONE;
	}

	@Action(value = "permissionAction_save", results = {
			@Result(location = "/pages/system/permission.html", name = "save_success", type = "redirect") })
	public String save() {

		permissionService.save(model);

		return Constant.SAVE_SUCCESS;
	}

	// permissionAction_queryAll
	@Action(value = "permissionAction_queryAll")
	public String queryAll() throws IOException {

		Page<Permission> pageList = permissionService.findAll(null);
		JsonConfig config = new JsonConfig();

		config.setExcludes(new String[] { "roles" });

		List<Permission> content = pageList.getContent();
		
		String string = JSONArray.fromObject(content, config).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("appilcation/json;charset=utf-8");

		response.getWriter().write(string);
		
		return NONE;
	}

}
