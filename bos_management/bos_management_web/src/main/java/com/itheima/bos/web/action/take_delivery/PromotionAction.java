
package com.itheima.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.take_delivery.Promotion;
import com.itheima.bos.service.take_delivery.PromotionService;
import com.itheima.bos.web.action.IdenticalAction;

import net.sf.json.JSONObject;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:22:29 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PromotionAction extends IdenticalAction<Promotion>{

	public PromotionAction() {
		super(Promotion.class);  
	}

	@Autowired
	private PromotionService promotionService;
	
	@Action("wayBillAction_save")
	public String save() throws IOException {
		
		promotionService.save(model);
		System.out.println(model.getDescription());
		return NONE;
	}
	
	
	private File imgFile;
	private String imgFileFileName;
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}

	@Action("promotionAction_copyFile")
	public String copyFile() throws IOException {
		 Map<String, Object> map=new HashMap<>();
		 HttpServletResponse response = ServletActionContext.getResponse();
		 response.setContentType("application/json;charset=utf-8");
		 PrintWriter writer = response.getWriter();
		try {
			String newFilePath="/upload";
			
			ServletContext servletContext = ServletActionContext.getServletContext();
			//获取绝对路径,(当前项目的绝对路径+newFilePath的路径)
			String realPath = servletContext.getRealPath(newFilePath);
			
			//获取后缀                                                                                                                                                   //获取.后一次出现的角标
			String suffix = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
			//文件名
			String newFileName = UUID.randomUUID().toString().replaceAll("-", "")+suffix;
			
			File file=new File(realPath+"/"+newFileName);
			
			FileUtils.copyFile(imgFile, file);
			
			String newFileNamePath ="http://localhost:8080"+servletContext.getContextPath()+"/"+newFilePath+"/"+newFileName;
			
			map.put("error", 0);
			map.put("url", newFileNamePath);
			String fromObject = JSONObject.fromObject(map).toString();
			writer.write(fromObject);
		} catch (Exception e) {
			map.put("error", 1);
			map.put("url", "失败!!~~~!");
			String fromObject = JSONObject.fromObject(map).toString();
			writer.write(fromObject);
			e.printStackTrace();  
		}
		return NONE;
	}

		
	
	
	
	
	
	
	
	
	
}
  
