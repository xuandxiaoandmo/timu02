package com.itheima.bos.web.action;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.data.domain.Page;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.SubArea;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:IdenticalAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午9:47:10 <br/>
 */
public class IdenticalAction<T> extends ActionSupport
		implements ModelDriven<T> {

	
	protected T model; 
	protected Class<T> clazz; 
	
	public IdenticalAction(Class<T> clazz){
		this.clazz=clazz;
	}
	
	@Override
	public T getModel() {
		try {
			if(model==null){
				model=clazz.newInstance();
			}
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();  
		}
		return model;
	}
	
	
	
	protected int page=1;
	protected int rows=30;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	
	public void pageFindAll(JsonConfig config ,Page<T> pageFindAll) throws IOException {

		long totalElements = pageFindAll.getTotalElements();
		List<T> content = pageFindAll.getContent();
		Map<String, Object> map = new HashMap<>();
		map.put("total", totalElements);
		map.put("rows", content);
		String string =null;
		if(config==null){
			string = JSONObject.fromObject(map).toString();
		}else{
			 string = JSONObject.fromObject(map, config).toString();
		}

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("appilcation/json;charset=utf-8");

		response.getWriter().write(string);

	}
	
	
	public void handleJson(Collection<?> collection) throws IOException{
		
		JsonConfig config=new  JsonConfig();
		config.setExcludes(new String[]{"fixedArea","subareas"});
		
		String string = JSONArray.fromObject(collection,config).toString();
		
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("appilcation/json;charset=utf-8");

		response.getWriter().write(string);
		
		
	}
	
	
	public  void listToJson(List list,JsonConfig config) throws IOException {

		String string = JSONArray.fromObject(list,config).toString();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("appilcation/json;charset=utf-8");

		response.getWriter().write(string);
		
		
		
	}
	
	

	
	
	
	
	
	
	
	
    
	

}
