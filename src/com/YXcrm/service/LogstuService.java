package com.YXcrm.service;

import java.util.ArrayList;
import java.util.List;

import com.YXcrm.model.Logstu;
import com.YXcrm.utility.M_msg;

public interface LogstuService {
/**
 * 学员日志删除
 */
	String delete(String uuid);
	
	M_msg getMsg();
	
	 /**
	  * 日志查询
	  */
	 public ArrayList<Logstu> getList();
	 /**
	  * 日志新增
	  */
	 public String insert(Logstu logstu);
	 /**
	  * 日志修改
	  */
	  public String update(Logstu logstu);
	  /**
	   * 批量删除日志
	   */
	  public String deleteBatch(List<String> uuidList);
}
