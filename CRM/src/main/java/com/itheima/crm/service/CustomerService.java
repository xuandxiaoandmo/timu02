package com.itheima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;

import com.itheima.crm.domain.Customer;

/**
 * ClassName:UserService <br/>
 * Function: <br/>
 * Date: 2018年3月18日 下午1:34:12 <br/>
 */

@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface CustomerService {

	@GET
	@Path("/findAll")
	public String findAll(@QueryParam("page") Integer page,
			@QueryParam("rows") Integer rows);

	@GET
	@Path("/queryNoAssociationCustomer")
	/**
	 * 查找没有关联定区的客户 queryNoAssociationCustomer:. <br/>
	 * 
	 * @return
	 */
	public List<Customer> queryNoAssociationCustomer();

	@GET
	@Path("/queryAssociationCustomer")
	/**
	 * 查找没有关联定区的客户 queryNoAssociationCustomer:. <br/>
	 * 
	 * @return
	 */
	public List<Customer> queryAssociationCustomer(@QueryParam("id") String id);

	@PUT
	@Path("/assignCustomers2FixedArea")
	/**
	 * 此方法用于解除客户关联定区 或 让可以关联定区 associationCourierToFixedArea:. <br/>
	 *
	 */
	public void assignCustomers2FixedArea(
			@QueryParam("list") List<Long> ids,
			@QueryParam("fixedAreaId") String fixedAreaId);

}
