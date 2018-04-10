package com.YXcrm.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.YXcrm.dao.LogstuDao;
import com.YXcrm.dao.impl.LogstuDaoImpl;
import com.YXcrm.model.Logstu;
import com.YXcrm.model.YXstudent;
import com.YXcrm.service.LogstuService;
import com.YXcrm.utility.M_msg;

public class LogstuServiceImpl implements LogstuService{
	LogstuDao lgd = new LogstuDaoImpl();
	public M_msg m_msg = new M_msg();
	Logger logger = Logger.getLogger(LogstuServiceImpl.class);
	public LogstuServiceImpl(){
		System.out.println("LogstuServiceImpl 成功了");
	}
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
	        String msg = "LogstuServiceImpl delete方法中的uuid为空，或格式不正确，请重新选择";
	        System.out.println(msg);
	        return msg;
	    }
	}

	@Override
	public M_msg getMsg() {
		// TODO Auto-generated method stub
		return m_msg;
	}
	@Override
	public ArrayList<Logstu> getList() {
		// TODO Auto-generated method stub
		ArrayList<Logstu> yxstuList = lgd.getList();
	    return yxstuList;
	}
	@Override
	public String insert(Logstu logstu) {
		// TODO Auto-generated method stub
		    if (logstu.getUuid() != null && logstu.getUuid() != "") {
		    	logstu.setUuid(UUID.randomUUID().toString());
		        System.out.println("^^在LogstuServiceImpl收到数据 ：" + logstu.toString()
		                + "收到结束!");
		        boolean daoFlag = lgd.insert(logstu);
		        if (daoFlag) {
		            return logstu.getUuid();
		        } else {
		            logger.error("插入不成功,dao层执行有出错地方,请联系管理员");
		            m_msg.setAddMsg("插入不成功,dao层执行有出错地方,请联系管理员LogstuServiceImpl");
		            return "插入不成功,dao层执行有出错地方,请联系管理员";
		        }
		    } else {
		        m_msg.setAddMsg("该意向学员不存在");
		        return "该意向学员不存在";
		    }
	}
	@Override
	public String update(Logstu logstu) {
		// TODO Auto-generated method stub
		 String uuid = logstu.getUuid();
		    if (uuid != null && uuid != "") {

		        boolean daoFlag = lgd.update(logstu);

		        if (daoFlag) {
		            return uuid;
		        } else {
		            logger.error("修改不成功,dao层执行有出错地方,请联系管理员");
		            m_msg.setAddMsg("修改不成功,dao层执行有出错地方,请联系管理员LogstuServiceImpl");
		            return "修改不成功,dao层执行有出错地方,请联系管理员";
		        }
		    } else {
		        String msg = "LogstuServiceImpl update方法中的uuid为空，或格式不正确，请重新选择";
		        System.out.println(msg);
		        return msg;
		    }
	}
	@Override
	public String deleteBatch(List<String> uuidList) {
		// TODO Auto-generated method stub
		int i=0;
		boolean daoFlag=false;
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
			String msg = "LogstuService deleteBatch方法中的uuid为空，或格式不正确，请重新选择";
			System.out.println(msg);
			return msg;
		}
	}

}
