package com.YXcrm.dao;

import java.util.ArrayList;

import com.YXcrm.model.Logstu;
import com.YXcrm.model.YXstudent;

public interface LogstuDao {
	/**
	 * 学员日志删除
	 */
	 public boolean delete(String uuid);
	 /**
	  * 日志查询
	  */
	 public ArrayList<Logstu> getList();
	 /**
	  * 日志新增
	  */
	 public boolean insert(Logstu logstu);
	 /**
	  * 日志修改
	  */
	  public boolean update(Logstu logstu);
	  
	  /**
	   * 批量删除日志
	  */
	  public boolean deleteBatch(String uuid);
}
