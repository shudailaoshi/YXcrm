package com.YXcrm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.YXcrm.dao.LoggxDao;
import com.YXcrm.dao.impl.LoggxDaoImpl;
import com.YXcrm.model.LogGX;
import com.YXcrm.model.Logstu;
import com.YXcrm.service.LoggxService;
import com.YXcrm.utility.M_msg;

public class LoggxServiceImpl implements LoggxService{
	
	public LoggxDao lgd = new LoggxDaoImpl();
	public M_msg m_msg = new M_msg();
	Logger logger = Logger.getLogger(LoggxServiceImpl.class);
	@Override
	public String delete(String uuid) {
		// TODO Auto-generated method stub
		if (uuid != null && uuid != "") {
	        boolean daoFlag = lgd.delete(uuid);

	        if (daoFlag) {
	            return uuid;
	        } else {
	            logger.error("删除不成功,dao层执行有出错地方,请联系管理员");
	            return "删除不成功,dao层执行有出错地方,请联系管理员";
	        }
	    } else {
	        String msg = "LoggxServiceImpl delete方法中的uuid为空，或格式不正确，请重新选择";
	        System.out.println(msg);
	        return msg;
	    }
	}

	@Override
	public ArrayList<LogGX> getList() {
		// TODO Auto-generated method stub
		ArrayList<LogGX> yxstuList = lgd.getList();
	    return yxstuList;
	}
	
	@Override
	public M_msg getMsg() {
		// TODO Auto-generated method stub
		return m_msg;
	}

	@Override
	public String deleteBatch(List<String> uuidList) {
		// TODO Auto-generated method stub
		int i=0;
		boolean daoFlag=true;
		if (uuidList != null && uuidList.size() >0) {
			for(String uuid:uuidList){
				daoFlag=lgd.deleteBatch(uuid);
				i=i+1;
				if(daoFlag==false){
					break;
				}
			}
			if (i>0) {
				return "删除成功，批量删除了"+i+"条";
			} else {
				logger.error("删除不成功,dao层执行有出错地方,请联系管理员");
				return "删除失败！";
			}
		} else {
			String msg = "LogGXService deleteBatch方法中的uuid为空，或格式不正确，请重新选择";
			System.out.println(msg);
			return msg;
		}
	}

}
