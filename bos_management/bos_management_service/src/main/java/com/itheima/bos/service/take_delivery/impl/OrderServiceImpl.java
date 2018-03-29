package com.itheima.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.OrderRepository;
import com.itheima.bos.dao.take_delivery.WorkBillRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.take_delivery.OrderService;
import com.itheima.crm.domain.Customer;

/**
 * ClassName:OrderServiceIml <br/>
 * Function: <br/>
 * Date: 2018年3月23日 上午9:55:50 <br/>
 */

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private AreaRepository areaRepository;

	@Autowired
	private FixedAreaRepository fixedAreaRepository;

	@Autowired
	private WorkBillRepository workBillRepository;

	@Override
	public void add(Order order) {
		Area sendArea = order.getSendArea();
		Area recArea = order.getRecArea();
		if (sendArea != null) {
			// 根据省市区查询的 区域对象
			sendArea = areaRepository.findByProvinceAndCityAndDistrict(
					sendArea.getProvince(), sendArea.getCity(),
					sendArea.getDistrict());
		}

		if (recArea != null) {
			recArea = areaRepository.findByProvinceAndCityAndDistrict(
					recArea.getProvince(), recArea.getCity(),
					recArea.getDistrict());
		}

		order.setSendArea(sendArea);// 寄
		order.setRecArea(recArea);// 收

		orderRepository.save(order);
		
		String sendAddress = order.getSendAddress();
		
		if (sendAddress != null) {

			String fixedAreaTd = WebClient
					.create("http://localhost:8180/CRM/webService/customerService/queryCustomerFixedAreaId")
					.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.query("address", sendAddress).get(String.class);

			if (StringUtils.isNotEmpty(fixedAreaTd)) {
				
				System.err.println("找到了和配送地址一样的客户对应的分区id~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				
				FixedArea fixedArea = fixedAreaRepository
						.findOne(Long.parseLong(fixedAreaTd));

				Set<Courier> couriers = fixedArea.getCouriers();
				if (couriers != null && couriers.size() > 0) {
					Iterator<Courier> iterator = couriers.iterator();
					Courier courier = iterator.next();

					if (courier != null) {

						WorkBill workBill = new WorkBill();
						workBill.setOrder(order);
						workBill.setAttachbilltimes(0);
						workBill.setBuildtime(new Date());
						workBill.setCourier(courier);
						workBill.setOrder(order);
						workBill.setPickstate("新单");
						workBill.setRemark(order.getRemark());
						workBill.setSmsNumber("111");
						workBill.setType("新");
						
						workBillRepository.save(workBill);
						
						order.setOrderType("自动分单");
						return;
					}
				}
			} else {
				System.err.println("~~~~客户里没有个当前地址相同的~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				if (sendArea!=null) {
					Set<SubArea> subareas = sendArea.getSubareas();
					int counter = 0;
					WorkBill workBill = null;
					for (SubArea subArea : subareas) {
						String assistKeyWords = subArea.getAssistKeyWords();
						String keyWords = subArea.getKeyWords();
						if (assistKeyWords.contains(sendAddress)
								|| keyWords.contains(sendAddress)) {
							FixedArea fixedArea = subArea.getFixedArea();
							
							if (fixedArea != null) {
								Set<Courier> couriers = fixedArea.getCouriers();
								if (couriers.size() > 0) {
									Iterator<Courier> iterator = couriers.iterator();
									//等等去完善一下
									Courier courier = iterator.next();
									workBill = new WorkBill();
									workBill.setAttachbilltimes(0);
									workBill.setBuildtime(new Date());
									workBill.setCourier(courier);
									workBill.setOrder(order);
									workBill.setPickstate("新单");
									workBill.setRemark(order.getRemark());
									workBill.setSmsNumber("111");
									workBill.setType("新");
									counter++;
									
								}
							}else{
								System.err.println("~~~定区没有关联分区~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							}
						}
						
					}
					// 为了不让快递发送错误
					if (counter == 1) {
						// 保存workBill 工单
						workBillRepository.save(workBill);
						order.setOrderType("自动分单");
						return;
					}else{
						order.setOrderType("手动分单-有多个快递员符合配送地址");
					}
				}
				
			}
		}

		order.setOrderType("人工分单");
	}

}
