package com.itheima.bos.web.action.beas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.Rows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scripting.support.StaticScriptSource;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.AreaSercice;
import com.itheima.bos.utils.Constant;
import com.itheima.bos.web.action.IdenticalAction;
import com.itheima.utils.FileDownloadUtils;
import com.itheima.utils.PinYin4jUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * ClassName:AreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午5:44:14 <br/>
 */
@Scope("prototype")
@Controller()
@ParentPackage("struts-default")
@Namespace(value = "/")
@SuppressWarnings({ "unused", "serial" })
public class AreaAction extends IdenticalAction<Area> {

	public AreaAction() {
		super(Area.class);
	}

	@Resource
	private AreaSercice areaSercice;

	private File file;

	public void setFile(File file) {
		this.file = file;
	}

	@Action(value = "areaAction_importXLS", results = {
			@Result(location = "/pages/base/area.html", name = "import_success", type = "redirect") })
	public String importXLS() {

		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(
					new FileInputStream(file));
			// 读取第一个工作簿 （第0个就是第一个）
			HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);

			ArrayList<Area> areas = new ArrayList<>();

			for (Row row : sheetAt) {
				// 跳过第一行
				if (row.getRowNum() == 0) {
					// 跳过本次循环
					continue;
				}
				Area area = new Area();
				// 城市编码 SHENZHEN 简码 GDSZBA
				// 第一列不需要,我们的id是数据库自己维护
				Cell cell = row.getCell(1);
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();

				area.setCity(city);
				area.setPostcode(postcode);
				area.setDistrict(district);
				area.setProvince(province);

				province = province.substring(0, province.length() - 1);
				city = city.substring(0, city.length() - 1);
				district = district.substring(0, district.length() - 1);

				String citycode = PinYin4jUtils.hanziToPinyin(city, "")
						.toUpperCase();

				String[] headByString = PinYin4jUtils
						.getHeadByString(province + city + district);

				String shortcode = PinYin4jUtils
						.stringArrayToString(headByString);

				area.setCitycode(citycode);
				area.setShortcode(shortcode);

				areas.add(area);
			}

			areaSercice.saveAll(areas);

			System.out.println("进来啦!~~~~");
		} catch (FileNotFoundException e) {
			System.out.println("文件没有找到");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Constant.IMPORT_SUCCESS;
	}

	@Action(value = "areaAction_pageFindAll")
	public String pageFindAll() throws IOException {
		Pageable pageable = new PageRequest(page - 1, rows);
		Page<Area> pageFindAll = areaSercice.pageFindAll(pageable);

		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "subareas" });

		pageFindAll(config, pageFindAll);

		return NONE;
	}

	// areaAction_save
	@Action(value = "areaAction_save", results = {
			@Result(location = "/pages/base/area.html", name = "save_success", type = "redirect") })
	public String save() {
		areaSercice.save(model);
		return Constant.SAVE_SUCCESS;
	}

	private String q;

	public void setQ(String q) {
		this.q = q;
	}

	// areaAction_findAll
	@Action(value = "areaAction_findAll")
	public String findAll() throws IOException {
		List<Area> content = null;
		System.out.println(q);
		if (q == null) {
			Page<Area> pageFindAll = areaSercice.pageFindAll(null);
			content = pageFindAll.getContent();
		} else {
			content = areaSercice.findQ("%" + q + "%");
		}
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "subareas" });

		JSONArray fromObject = JSONArray.fromObject(content, config);
		String string = fromObject.toString();

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("appilcation/json;charset=utf-8");

		response.getWriter().write(string);

		return NONE;
	}

	// areaAction_exportFile
	@Action(value = "areaAction_exportFile")
	public String exportFile() throws IOException {

		Page<Area> pageFindAll = areaSercice.pageFindAll(null);
		List<Area> content = pageFindAll.getContent();

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

		HSSFSheet createSheet = hssfWorkbook.createSheet();
		// 这是第一行
		HSSFRow titleRow = createSheet.createRow(0);
		// 设置第一行的列
		titleRow.createCell(0).setCellValue("省");
		titleRow.createCell(1).setCellValue("市");
		titleRow.createCell(2).setCellValue("区");
		titleRow.createCell(3).setCellValue("邮政编码");
		titleRow.createCell(4).setCellValue("简码");
		titleRow.createCell(5).setCellValue("城市编码");

		for (Area area : content) {
			int lastRowNum = createSheet.getLastRowNum();
			HSSFRow dataRow = createSheet.createRow(lastRowNum + 1);
			// 设置第一行的列
			dataRow.createCell(0).setCellValue(area.getProvince());
			dataRow.createCell(1).setCellValue(area.getCity());
			dataRow.createCell(2).setCellValue(area.getDistrict());
			dataRow.createCell(3).setCellValue(area.getPostcode());
			dataRow.createCell(4).setCellValue(area.getShortcode());
			dataRow.createCell(5).setCellValue(area.getCitycode());
		}
		String fileName = "区域数据统计.xls";

		HttpServletResponse response = ServletActionContext.getResponse();
		ServletContext servletContext = ServletActionContext.getServletContext();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		ServletOutputStream outputStream = response.getOutputStream();
		
		String mimeType = servletContext.getMimeType(fileName);
		
		System.out.println("mimeType"+mimeType);
		//获取浏览器类型
		String header = request.getHeader("User-Agent");
		
		//针对不同浏览器的中文乱码处理
		fileName = FileDownloadUtils.encodeDownloadFilename(fileName, header);
		
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", "filename=" + fileName);
		
		hssfWorkbook.write(outputStream);
		hssfWorkbook.close();
		
		return NONE;
	}

}
