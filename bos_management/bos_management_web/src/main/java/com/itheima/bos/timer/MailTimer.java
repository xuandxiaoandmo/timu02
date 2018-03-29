package com.itheima.bos.timer;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.bos.utils.SmsUtils;

/**  
 * ClassName:MailTimer <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午9:26:29 <br/>       
 */
@Component
public class MailTimer {

	
	public void sendMail(){
		try {
			System.out.println("发送了验证码"+new Date());
			SmsUtils.sendSms("15889312561", "123456");
		} catch (ClientException e) {
			  System.out.println("发送失败!!!");
			// TODO Auto-generated catch block  
			e.printStackTrace();  
			
		}
	}
	
	
	
	
	
}
  
