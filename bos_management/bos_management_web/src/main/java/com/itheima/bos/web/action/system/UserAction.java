package com.itheima.bos.web.action.system;

import java.io.IOException;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
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

import com.itheima.bos.service.system.UserService;
import com.itheima.bos.system.User;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;

import freemarker.template.utility.SecurityUtilities;
import net.sf.json.JsonConfig;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: 2018年3月26日 下午1:51:04 <br/>
 */

@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class UserAction extends IdenticalAction<User> {

	public UserAction() {
		super(User.class);
	}

	@Autowired
	private UserService userService;
	
	
	private String code;

	public void setCode(String code) {
		this.code = code;
	}

	@Action(value = "userAction_login", results = {
			@Result(location = "/index.html", name = "success", type = "redirect"),
			@Result(location = "/login.html", name = "error", type = "redirect") })
	public String login() {
		try {
			String validateCode = (String) ServletActionContext.getRequest()
					.getSession().getAttribute("validateCode");
			if (validateCode != null && code != null
					&& validateCode.equalsIgnoreCase(code)) {
				Subject subject = SecurityUtils.getSubject();
				// 创建令牌
				AuthenticationToken token = new UsernamePasswordToken(
						model.getUsername(), model.getPassword());
				subject.login(token);
				User  user = (User) subject.getPrincipal();
				System.out.println("User存入session:"+user);
				ServletActionContext.getRequest().getSession().setAttribute("user", user);
				
				return SUCCESS;
			}
			return ERROR;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;

		}

	}
	
	
	
	//userAction_pageFindAll
	@Action(value = "userAction_pageFindAll")
	public String pageFindAll() throws IOException {
		
		Pageable pageable=new PageRequest(page -1, rows);
		
		Page<User> pageList=userService.findAll(pageable);
		
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"roles"});
		
		pageFindAll(config, pageList);
		
		return NONE;
	}
	
	
	
	@Action(value = "userAction_save", results = {
			@Result(location = "/pages/system/userlist.html", name = "save_success", type = "redirect") })
	public String save() {
		
		userService.save(model);
		
		
		return Constant.SAVE_SUCCESS;
	}

	//userAction_logout
	@Action(value = "userAction_logout", results = {
			@Result(location = "/login.html", name = "success", type = "redirect") })
	public String logout() {
		// Subject subject = SecurityUtils.getSubject();
//        subject.logout();
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		return SUCCESS;
	}
	
	
	

}
