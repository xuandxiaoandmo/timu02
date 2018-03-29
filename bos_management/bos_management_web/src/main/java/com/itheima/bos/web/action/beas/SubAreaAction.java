package com.itheima.bos.web.action.beas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.SubAreaSeriver;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:SubAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午1:10:20 <br/>
 */// 分区

@Namespace("/")
@Controller()
@Scope("prororype")
@ParentPackage("struts-default")
@SuppressWarnings({ "unused", "serial" })
public class SubAreaAction extends IdenticalAction<SubArea> {

	public SubAreaAction() {
		super(SubArea.class);
	}

	@Resource
	private SubAreaSeriver subAreaSeriver;
	
	
	@Action(value = "subareaAction_save", results = {
			@Result(location = "/pages/base/sub_area.html", name = "save_success", type = "redirect") })
	public String save() {
		subAreaSeriver.save(model);
		return Constant.SAVE_SUCCESS;
	}

	
	@Action("subAreaAction_pageFindAll")
	public String pageFindAll() throws IOException{
		Specification<SubArea>specification =null;
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(model);
		
		Area area = model.getArea();
		FixedArea fixedArea = model.getFixedArea();//fixedAreaName
		String keyWords = model.getKeyWords();
		
		System.out.println(model.getArea());
		System.out.println(model.getFixedArea());
		System.out.println(keyWords);
		
		if (model!=null) {
			specification=new Specification<SubArea>() {
				
				@Override
				public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query,
						CriteriaBuilder criteriaBuilder) {
					
					Area area = model.getArea();
					FixedArea fixedArea = model.getFixedArea();//fixedAreaName
					String keyWords = model.getKeyWords();
					List<Predicate> list=new ArrayList<>();
					//城市
					if (area!=null&&StringUtils.isNotEmpty(area.getCity())) {
						System.out.println("城市:"+area.getCity()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

						Join<Object, Object> join = root.join("area");
						Predicate equal = criteriaBuilder.equal(join.get("city").as(String.class), area.getCity());
						list.add(equal);
						
					}
					//省
					if (area!=null&&StringUtils.isNotEmpty(area.getProvince())) {
						System.out.println("省:"+area.getProvince()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						Join<Object, Object> join = root.join("area");
						Predicate equal = criteriaBuilder.equal(join.get("province").as(String.class), area.getProvince());
						list.add(equal);
						
					}
					//县/区
					if (area!=null&&StringUtils.isNotEmpty(area.getDistrict())) {
						System.out.println("县/区:"+ area.getDistrict()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						Join<Object, Object> join = root.join("area");
						Predicate equal = criteriaBuilder.equal(join.get("district").as(String.class), area.getDistrict());
						list.add(equal);
						
					}
					//分区id
					if (model.getId() != null) {
						System.out.println("分区id:"+fixedArea.getFixedAreaName()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						Predicate equal = criteriaBuilder.equal(root.get("id").as(String.class), model.getId());
						list.add(equal);
						
					}
					//关键字
					if (StringUtils.isNotEmpty(keyWords)) {
						System.out.println("关键字:"+model.getKeyWords()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						Predicate equal = criteriaBuilder.like(root.get("keyWords").as(String.class), "%"+model.getKeyWords()+"%");
						list.add(equal);
					}
					Predicate[] predicate =new Predicate[list.size()];
					
					Predicate and = criteriaBuilder.and(list.toArray(predicate));
					
					return and;
				}
			};
		}
		
		
		
		
		Pageable pageable=new PageRequest(page-1, rows);
		Page<SubArea> page=subAreaSeriver.findBypage(specification,pageable);
		
		JsonConfig config=new  JsonConfig();
		config.setExcludes(new String[]{"fixedArea","subareas"});
		
		pageFindAll(config, page);
		return NONE;
	}
	
	
//	subAraeAction_queryFixedAreaIdIsNull
	@Action("subAraeAction_queryFixedAreaIdIsNull")
	public String queryFixedAreaIdIdIsNull() throws IOException{
		
		List<SubArea>areas=subAreaSeriver.fnidByFixedAreaIdIsNull();
		
		System.out.println(areas);
		
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"area","fixedArea"});
		listToJson(areas, config);
		
		return NONE;
	}
	
	
	//subAraeAction_querySubAreaFixedArea
	@Action("subAraeAction_querySubAreaFixedArea")
	public String querySubAreaFixedArea() throws IOException{
		
		List<SubArea>areas=subAreaSeriver.querySubAreaFixedArea(model.getId());
		
		System.out.println(areas);
		
		JsonConfig config=new JsonConfig();
		config.setExcludes(new String[]{"area","fixedArea"});
		listToJson(areas, config);
		
		return NONE;
	}
	
	private List<Long> subAreaIds=new ArrayList<>();
	public void setSubAreaIds(List<Long> subAreaIds) {
		this.subAreaIds = subAreaIds;
	}


	@Action(value = "subAreaAction_assignSubAreaFixedArea", results = {
			@Result(location = "/pages/base/fixed_area.html", name = "success", type = "redirect") })
    public String assignSubAreaFixedArea(){
		
		subAreaSeriver.assignSubAreaFixedArea(subAreaIds,model.getId());
		
		
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
}
