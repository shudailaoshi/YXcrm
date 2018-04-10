package com.YXcrm.service;

import java.util.ArrayList;
import java.util.List;

import com.YXcrm.model.LogGX;
import com.YXcrm.utility.M_msg;

public interface LoggxService {
	/**
	 * 删除
	 */
	 public String delete(String uuid);
	 /**
	  * 查询
	  */
	 public ArrayList<LogGX> getList();
	 
	 M_msg getMsg();
	 /**
	  * 批量删除日志
	  */
	 public String deleteBatch(List<String> uuidList);
}
