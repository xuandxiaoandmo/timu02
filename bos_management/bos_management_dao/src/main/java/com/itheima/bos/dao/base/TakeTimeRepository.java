package com.itheima.bos.dao.base;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.base.TakeTime;

public interface TakeTimeRepository extends JpaRepository<TakeTime, Long>,JpaSpecificationExecutor<TakeTime>{


}
  
