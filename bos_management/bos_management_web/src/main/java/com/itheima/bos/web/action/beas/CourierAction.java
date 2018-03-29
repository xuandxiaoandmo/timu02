package com.itheima.bos.web.action.beas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument.Config;
import org.junit.Test;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.google.common.base.Predicates;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.CourierService;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

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
@SuppressWarnings({ "unused", "serial" })
public class CourierAction extends IdenticalAction<Courier> {


	public CourierAction() {
		super(Courier.class);  
	}

	@Resource
	private CourierService courierService;

	@Action(value = "courierAction_save", results = {
			@Result(location = "/pages/base/courier.html", name = "save_success", type = "redirect") })
	public String save() {
		courierService.save(model);
		return Constant.SAVE_SUCCESS;
	}

	@Action(value = "courierAction_pageFindAll")
	public String pageFindAll() throws IOException {

		System.out.println(model);
		// 添加where条件
		Specification<Courier> specification = new Specification<Courier>() { 

			@Override
			/**
			 * root --->查询当前实体类的   虚拟对象 ,可以通过他类指定我们要和那个列做判断 
			 * criteriaBuilder ---> 用来规定使用什么方式来比较/查询 和我们的离线对象有点像
			 * TODO 简单描述该方法的实现功能（可选）.  
			 * @see org.springframework.data.jpa.domain.Specification#toPredicate(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaQuery, javax.persistence.criteria.CriteriaBuilder)
			 */
			public Predicate toPredicate(Root<Courier> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

				
				
				
				String type = model.getType();
				Standard standard = model.getStandard();
				String company = model.getCompany();
				String courierNum = model.getCourierNum();

				List<Predicate> couriers=new ArrayList<>();
				
				if (StringUtils.isNoneEmpty(type)) {
					/*
					 * Predicate p1 =
					 * cb.equal(root.get("courierNum").as(String.class),
					 * courierNum);
					 */
					Predicate equal = criteriaBuilder.equal(root.get("type").as(String.class), type);
					couriers.add(equal);
					
				}
				if (StringUtils.isNoneEmpty(company)) {
					Predicate like = criteriaBuilder.like(
							root.get("company").as(String.class), "%"+company+"%");
					couriers.add(like);
				}
				if (StringUtils.isNoneEmpty(courierNum)) {
					Predicate equal = criteriaBuilder.equal(
							root.get("courierNum").as(String.class),
							courierNum);
					couriers.add(equal);
				}
				if (standard != null) {
					if (StringUtils.isNoneEmpty(standard.getName())) {
						Join<Object, Object> join = root.join("standard");
						Predicate equal = criteriaBuilder.equal(
								join.get("name").as(String.class),
								standard.getName());
						couriers.add(equal);

					}
				}
				Predicate[] predicate=new Predicate[couriers.size()];
				
				Predicate and = criteriaBuilder.and(couriers.toArray(predicate));
				
				return and;
			}
		};

		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Courier> pageFindAll = courierService.pageFindAll(specification,pageable);
		
		JsonConfig config = new JsonConfig();
		// 设置不包括
		config.setExcludes(new String[] { "takeTime", "fixedAreas" });

		pageFindAll(config, pageFindAll);
		
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
		if (StringUtils.isNoneEmpty(ids)) {
			courierService.deledtById(ids);
		}
		return Constant.DELETE_SUCCESS;
	}

	// reductionById

	@Action(value = "courierAction_reductionById", results = {
			@Result(location = "/pages/base/courier.html", name = "reduction_success", type = "redirect") })
	public String reductionById() {
		if (StringUtils.isNoneEmpty(ids)) {
			courierService.reductionById(ids);
		}
		return Constant.REDUCTION_SUCCESS;
	}

	
	
	//courierAction_listajax
	@Action("courierAction_listajax")
	public  String listajax() throws IOException{
		
		List<Courier> couriers=courierService.findByDeltagIsNull();
		JsonConfig config=new  JsonConfig();
		config.setExcludes(new String[]{"fixedAreas"});
		
		listToJson(couriers,config);
		
		return NONE;
	}
	
	
	
}
