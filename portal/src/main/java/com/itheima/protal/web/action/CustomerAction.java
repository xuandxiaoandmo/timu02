 package com.itheima.protal.web.action;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.bos.fore.utils.MyMailUtils;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.utils.SmsUtils;
import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import freemarker.core.ReturnInstruction.Return;
import net.sf.json.JSONObject;

/**
 * ClassName:CustomerAction <br/>
 * Function: <br/>
 * Date: 2018年3月19日 下午1:33:56 <br/>
 */

@Namespace("/")
@Scope("prototype")
@ParentPackage("struts-default")
@Controller
public class CustomerAction extends ActionSupport
		implements ModelDriven<Customer> {

	private Customer model = new Customer();

	@Override
	public Customer getModel() {
		return model;
	}
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Action(value = "customerAction_productsCode")
	public String productsCode() throws ClientException {

		final String code = RandomStringUtils.randomNumeric(6);
		ServletActionContext.getRequest().getSession().setAttribute("code",
				Integer.parseInt(code));
		
		jmsTemplate.send("sms", new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				
				MapMessage createMapMessage = session.createMapMessage();
				createMapMessage.setString("telephone", model.getTelephone());
				createMapMessage.setString("code", code);
				
				
				return createMapMessage;
			}
		});
		
		
		System.out.println("验证码发送了");

		return NONE;
	}

	private int checkcode;

	public void setCheckcode(int checkcode) {
		this.checkcode = checkcode;
	}

	
	// customerAction_regist.action
	@Action(value = "customerAction_regist", results = {
			@Result(location = "/signup-success.html", name = "regist_success", type = "redirect"),
			@Result(location = "/signup-fail.html", name = "error", type = "redirect") })
	public String regist() throws AddressException, MessagingException {

		int code = (int) ServletActionContext.getRequest().getSession()
				.getAttribute("code");
		if (StringUtils.isNoneEmpty(checkcode + "")
				&& StringUtils.isNoneEmpty(code + "") && checkcode == code) {
			WebClient
					.create("http://localhost:8180/CRM/webService/customerService/saveCustomer")
					.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON).post(model);

			// 随机数
			String activationCode = RandomStringUtils.randomNumeric(32);
			// 把激活码使用key为手机号,值为手机号;
			redisTemplate.opsForValue().set(model.getTelephone(),
					activationCode, 1, TimeUnit.DAYS);
			String emailBody = "您以注册成功,亲去激活哟,~~~~~<a href='http://localhost:8280/portal/customerAction_activationCustomer.action?activationCode="
					+ activationCode + "&telephone=" + model.getTelephone()
					+ "'>点击这里哟</a>";
			// 调用发邮件的工具类,发送邮件
			MyMailUtils.sendMail("1733581357@qq.com", emailBody);

			return Constant.REGIST_SUCCESS;
		}

		return ERROR;
	}

	private String activationCode;

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	
	//http://localhost:8280/portal/customerAction_activationCustomer.action?
	//activationCode=59886101828853530627464649009544
	//&telephone=15889312561
	// customerAction_activationCustomer
	@Action(value = "customerAction_activationCustomer", results = {
			@Result(location = "/login.html", name = "success", type = "redirect") })
	public String activationCustomer() {

		if(activationCode!=null&&model!=null&&model.getTelephone()!=null){
			
			String myactivationCode = redisTemplate.opsForValue().get(model.getTelephone());
			
			if(myactivationCode!=null&&activationCode.equals(myactivationCode)){
				
				WebClient.create("http://localhost:8180/CRM/webService/customerService/activationCustomer")
				         .accept(MediaType.APPLICATION_JSON)
				         .type(MediaType.APPLICATION_JSON)
				         .query("telephone", model.getTelephone())
				         .put(null);
				
				
				return SUCCESS;
			}
		}
		return ERROR;
	}

	private String checkcode007;
	
	public void setCheckcode007(String checkcode007) {
		this.checkcode007 = checkcode007;
	}

	@Action(value = "customerAction_login", results = {
			@Result(location = "/index.html", name = "success", type = "redirect") ,
	        @Result(location = "/login.html", name = "error", type = "redirect") })
	public String login() {
		String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
		//效验验证码
		if(checkcode007!=null&&validateCode!=null&&validateCode.equalsIgnoreCase(checkcode007)){
			//效验模型驱动
			if (model!=null&&model.getTelephone()!=null&&model.getPassword()!=null) {
				
				Customer customer = WebClient.create("http://localhost:8180/CRM/webService/customerService/queryCustomerByTelephone")
				         .type(MediaType.APPLICATION_JSON)
				         .accept(MediaType.APPLICATION_JSON)
				         .query("telephone", model.getTelephone())
				         .get(Customer.class);
				//效验电话获取的对象里的属性
				if(customer!=null&&customer.getType()!=null){
					
					System.out.println(customer.getType());
					//效验类型
					if(customer.getType()==1){
						Customer customer2 = WebClient.create("http://localhost:8180/CRM/webService/customerService/login")
								.type(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.query("telephone", model.getTelephone())
								.query("password", model.getPassword())
								.get(Customer.class);
						
						if(customer2!=null){
							ServletActionContext.getRequest().getSession().setAttribute("user", customer2);
							return SUCCESS;
						}
					}
				}
			}
		}
		
		return ERROR;
	}
	
	
	//customerAction_checkTelephone
	@Action("customerAction_checkTelephone")
	public String checkTelephone() throws IOException{
		if (model!=null&&model.getTelephone()!=null) {
			Customer customer = WebClient.create("http://localhost:8180/CRM/webService/customerService/queryCustomerByTelephone")
					.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.query("telephone", model.getTelephone())
					.get(Customer.class);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("appilcation/json;charset=utf-8");
			
			JSONObject jsonObject= new JSONObject();
			if (customer==null) {
				response.getWriter().write("{\"result\":\"success\"}");
			}else{
				response.getWriter().write("{\"result\":\"error\"}");
			}
		}
		return NONE;
	}
	
	
	
	
	
	
}
