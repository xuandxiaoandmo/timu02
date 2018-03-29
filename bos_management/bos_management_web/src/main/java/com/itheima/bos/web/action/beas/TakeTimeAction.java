package com.itheima.bos.web.action.beas;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.TakeTimeSercice;
import com.itheima.bos.web.action.IdenticalAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 上午9:45:07 <br/>       
 */

@Scope("prototype")
@Controller()
@ParentPackage("struts-default")
@Namespace(value = "/")
@SuppressWarnings({ "unused", "serial" })
public class TakeTimeAction extends IdenticalAction<TakeTime>{

	public TakeTimeAction() {
		super(TakeTime.class);  
	}

	@Resource
	private TakeTimeSercice takeTimeSercice;
	
	@Action("takeTimeAction_listajax")
	public  String listajax() throws IOException{
		
		List<TakeTime> couriers=takeTimeSercice.findAll();
		JsonConfig config=new  JsonConfig();
		config.setExcludes(new String[]{"fixedAreas"});
		
		listToJson(couriers, config);
		
		return NONE;
	}
	
	
	
	
	
	
}
  
