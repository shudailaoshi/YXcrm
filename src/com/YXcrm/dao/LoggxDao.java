package com.YXcrm.dao;

import java.util.ArrayList;
import java.util.List;

import com.YXcrm.model.LogGX;
import com.YXcrm.model.Logstu;

public interface LoggxDao {
	/**
	 * 删除
	 */
	 public boolean delete(String uuid);
	 /**
	  * 查询
	  */
	 public ArrayList<LogGX> getList();
	 
	 /**
	  * 新增
	  */
	 public boolean insert(LogGX lgx);
	 /**
	  * 根据yxuuid查询
	  */
	 public List<LogGX> findIdShow(String yxuuid);
	 /**
	  * 批量删除日志
	  */
	 public boolean deleteBatch(String uuid);
	 
}
