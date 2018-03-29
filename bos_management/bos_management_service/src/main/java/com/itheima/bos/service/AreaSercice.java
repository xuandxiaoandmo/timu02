package com.itheima.bos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaSercice <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:51:19 <br/>       
 */
public interface AreaSercice {

	Page<Area> pageFindAll(Pageable pageable);

	void save(Area model);

	void saveAll(ArrayList<Area> areas);


	List<Area> findQ(String p);

}
  
