package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.ArrayList;
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

import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.system.Permission;
import com.itheima.bos.system.Role;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * ClassName:RoleAction <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午4:50:08 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class RoleAction extends IdenticalAction<Role> {

	public RoleAction() {
		super(Role.class);
	}

	@Autowired
	private RoleService roleService;

	@Action(value = "roleAction_pageFindAll")
	public String pageFindAll() throws IOException {

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Role> pageList = roleService.findAll(pageable);

		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "users", "permissions", "menus" });
		pageFindAll(config, pageList);

		return NONE;
	}

	private String menuIds;

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	private List<Long> permissionIds = new ArrayList<>();

	public void setPermissionIds(List<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Action(value = "roleAction_save", results = {
			@Result(location = "/pages/system/role.html", name = "save_success", type = "redirect") })
	public String save() {

		roleService.save(model, menuIds, permissionIds);
		return Constant.SAVE_SUCCESS;
	}

	@Action(value = "roleAction_queryAll")
	public String queryAll() throws IOException {

		Page<Role> pageList = roleService.findAll(null);

		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "users", "permissions", "menus" });
		List<Role> content = pageList.getContent();
		
		String string = JSONArray.fromObject(content, config).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("appilcation/json;charset=utf-8");

		response.getWriter().write(string);

		return NONE;
	}

}
