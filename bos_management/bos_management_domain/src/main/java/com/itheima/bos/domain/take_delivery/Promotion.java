package com.itheima.bos.domain.take_delivery;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**  
 * ClassName:Promotion <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:14:02 <br/>       
 */

@Entity
@Table(name = "T_PROMOTION")
public class Promotion {

	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String activeScope;//活动范围:
	private String titleImgFile;//宣传图片:
	private String startDate;//发布时间:
	private String endDate;//失效时间:
	private String description;//宣传内容(活动描述信息):
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getActiveScope() {
		return activeScope;
	}
	public void setActiveScope(String activeScope) {
		this.activeScope = activeScope;
	}
	public String getTitleImgFile() {
		return titleImgFile;
	}
	public void setTitleImgFile(String titleImgFile) {
		this.titleImgFile = titleImgFile;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Promotion [id=" + id + ", title=" + title + ", activeScope="
				+ activeScope + ", titleImgFile=" + titleImgFile
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", description=" + description + "]";
	}
	
	
	
}
  
