package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.system.Menu;
import com.itheima.bos.system.User;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * ClassName:MenuAction <br/>
 * Function: <br/>
 * Date: 2018年3月28日 上午10:46:06 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class MenuAction extends IdenticalAction<Menu> {

	public MenuAction() {
		super(Menu.class);
	}

	@Autowired
	private MenuService menuService;

	// ../../menuAction_dindByParentMenuIsNull.action
	@Action(value = "menuAction_dindByParentMenuIsNull")
	public String findByParentMenuIsNull() throws IOException {

		List<Menu> list = menuService.findByParentMenuIsNull();

		JsonConfig config = new JsonConfig();
		config.setExcludes(
				new String[] { "roles", "parentMenu", "childrenMenus" });

		listToJson(list, config);

		return NONE;
	}

	// menuAction_save
	@Action(value = "menuAction_save", results = {
			@Result(location = "/pages/system/menu.html", name = "save_success", type = "redirect") })
	public String save() throws IOException {

		System.out.println(model);
		menuService.save(model);

		return Constant.SAVE_SUCCESS;
	}

	@Action(value = "menuAction_pageFindAll")
	public String pageFindAll() throws IOException {

		Pageable pageable = new PageRequest(Integer.parseInt(model.getPage()) - 1, rows);
		Page<Menu> pageList = menuService.findAll(pageable);

		JsonConfig config = new JsonConfig();
		config.setExcludes(
				new String[] { "roles", "parentMenu", "childrenMenus" });

		pageFindAll(config, pageList);

		return NONE;
	}

	// menuAction_FindAll
	@Action(value = "menuAction_findAll")
	public String findAll() throws IOException {
		List<Menu> list = menuService.findByParentMenuIsNull();
		JsonConfig config = new JsonConfig();
		config.setExcludes(
				new String[] { "roles", "parentMenu", "childrenMenus" });
		String string = JSONArray.fromObject(list, config).toString();
		System.out.println(string);
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("appilcation/json;charset=utf-8");

		response.getWriter().write(string);

		return NONE;
	}
	
	
	
	@Action(value = "menuAction_findMenu")
	public String findMenu() throws IOException {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
		List<Menu> menus=null;
		if("admin".equals(user.getUsername())){
			Page<Menu> findAll = menuService.findAll(null);
			menus = findAll.getContent();
		}else{
			menus=menuService.findbyUid(user.getId());
		}
		JsonConfig config=new JsonConfig();
		config.setExcludes(new  String[]{"roles", "parentMenu","children" });
		listToJson(menus, config);
		
		
		return NONE;
	}

    // SELECT<br/>
    // * <br/>
    // FROM<br/>
    // T_PERMISSION p<br/>
    // INNER JOIN T_ROLE_PERMISSION rp ON rp.C_PERMISSION_ID = p.C_ID<br/>
    // INNER JOIN T_ROLE r ON r.C_ID = rp.C_ROLE_ID<br/>
    // INNER JOIN T_USER_ROLE ur ON ur.C_ROLE_ID = r.C_ID<br/>
    // INNER JOIN T_USER u ON u.C_ID = ur.C_USER_ID <br/>
    // WHERE<br/>
    // u.C_ID = 227<br/>

}
