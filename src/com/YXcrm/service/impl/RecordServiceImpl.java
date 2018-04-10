package com.YXcrm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.YXcrm.dao.LoggxDao;
import com.YXcrm.dao.RecordDao;
import com.YXcrm.dao.YXstudentDao;
import com.YXcrm.dao.impl.LoggxDaoImpl;
import com.YXcrm.dao.impl.RecordDaoImpl;
import com.YXcrm.dao.impl.YXstudentDaoImpl;
import com.YXcrm.model.LogGX;
import com.YXcrm.model.Record;
import com.YXcrm.model.YXstudent;
import com.YXcrm.service.RecordService;
import com.YXcrm.utility.M_msg;

/**
 *树袋老师
 * @author 作者 xpp
 * @version 创建时间：2018-2-26 下午5:40:56
 * 类说明
 */

public class RecordServiceImpl implements RecordService{
  private LoggxDao lgd = new LoggxDaoImpl();
  private YXstudentDao yxd = new YXstudentDaoImpl();
  private RecordDao recordDao = new RecordDaoImpl();
  private YXstudentDao yxstudentDao = new YXstudentDaoImpl();
  public M_msg m_msg = new M_msg();
  Logger logger = Logger.getLogger(RecordServiceImpl.class);
  
  @Override
  public M_msg getMsg() {
    // TODO Auto-generated method stub
    return m_msg;
  }

  @Override
  public String insert(Record record) {
    // TODO Auto-generated method stub
    YXstudent yxstu = new YXstudent();
    yxstu = yxstudentDao.getByUuid(record.getYxstuUuid());
    if (yxstu.getUuid() != null && yxstu.getUuid() != "") {
        record.setUuid(UUID.randomUUID().toString());
        System.out.println("^^在RecordServiceImpl收到数据 ：" + record.toString()
                + "收到结束!");
        boolean daoFlag = recordDao.insert(record);
        LogGX lg = new LogGX();
        lg.setUuid(UUID.randomUUID().toString());
        lg.setUserUuid(record.getUserUuid());
        lg.setUserName(record.getUserName());
        lg.setTableName("t_record");
        lg.setTableNameChina("跟踪记录表");
        lg.setDataUuid(record.getYxstuUuid());
        String ditName =  "";
        ditName=yxstu.getName();
        lg.setDataName(ditName);
        lg.setUserAction("新增");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String da = sdf.format(date);
        lg.setUpdateTime(da);
        lg.setDataGxUuid(record.getUuid());
        lg.setDataGxChina(record.getRemarkText());
        lgd.insert(lg);
        if (daoFlag) {
        	Date dateModify = new Date();
            SimpleDateFormat sdfModify = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String daModify = sdfModify.format(dateModify);
        	recordDao.updateModifyDate(daModify,record.getYxstuUuid());
            return record.getUuid();
        } else {
            logger.error("插入不成功,dao层执行有出错地方,请联系管理员");
            m_msg.setAddMsg("插入不成功,dao层执行有出错地方,请联系管理员recordServiceImpl");
            return "插入不成功,dao层执行有出错地方,请联系管理员";
        }
    } else {
        m_msg.setAddMsg("该意向学员不存在");
        return "该意向学员不存在";
    }
  }//end method insert

  @Override
  public String delete(Record re) {
    // TODO Auto-generated method stub
    if (re.getUuid() != null && re.getUuid()  != "") {
    	LogGX lg = new LogGX();
        lg.setUuid(UUID.randomUUID().toString());
        lg.setUserUuid(re.getUserUuid());
        lg.setUserName(re.getUserName());
        lg.setTableName("t_record");
        lg.setTableNameChina("跟踪记录表");
        Record rec=recordDao.findIdShow(re.getUuid());
        lg.setDataUuid(rec.getYxstuUuid());
        YXstudent yx = yxd.getByUuid(rec.getYxstuUuid());
        lg.setDataName(yx.getName());
        lg.setUserAction("删除");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String da = sdf.format(date);
        lg.setUpdateTime(da);
        lg.setDataGxUuid(rec.getUuid());
        lg.setDataGxChina(rec.getRemarkText());
        lgd.insert(lg);
      boolean daoFlag = recordDao.delete(re.getUuid());

      if (daoFlag) {
          return re.getUuid() ;
      } else {
          logger.error("删除不成功,dao层执行有出错地方,请联系管理员");
          return "删除不成功,dao层执行有出错地方,请联系管理员";
      }
  } else {
      String msg = "recordDao delete方法中的uuid为空，或格式不正确，请重新选择";
      System.out.println(msg);
      return msg;
    }
  }//end method

  @Override
  public String update(Record record) {
    // TODO Auto-generated method stub
    String uuid = record.getUuid();
    if (uuid != null && uuid != "") {
    	LogGX lg = new LogGX();
        lg.setUuid(UUID.randomUUID().toString());
        lg.setUserUuid(record.getUserUuid());
        lg.setUserName(record.getUserName());
        lg.setTableName("t_record");
        lg.setTableNameChina("跟踪记录表");
        Record rec=recordDao.findIdShow(record.getUuid());
        lg.setDataUuid(rec.getYxstuUuid());
        YXstudent yx = yxd.getByUuid(rec.getYxstuUuid());
        lg.setDataName(yx.getName());
        lg.setUserAction("修改");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String da = sdf.format(date);
        lg.setUpdateTime(da);
        lg.setDataGxUuid(record.getUuid());
        lg.setDataGxChina(record.getRemarkText());
        lgd.insert(lg);
        boolean daoFlag = recordDao.update(record);

        if (daoFlag) {
            return uuid;
        } else {
            logger.error("修改不成功,dao层执行有出错地方,请联系管理员");
            m_msg.setEditMsg("修改不成功,dao层执行有出错地方,请联系管理员");
            return "修改不成功,dao层执行有出错地方,请联系管理员recordServiceImpl";
        }
    } else {
        String msg = "recordServiceImpl update方法中的uuid为空，或格式不正确，请重新选择";
        System.out.println(msg);
        return msg;
    }
  }//end method

  @Override
  public ArrayList<Record> getListByYxUuid(String yxstuUuid) {
    // TODO Auto-generated method stub
    if (yxstuUuid != null && yxstuUuid != "") {
      ArrayList<Record> recList =recordDao.getListByYxUuid(yxstuUuid) ;
      YXstudent yxstudent = yxstudentDao.getByUuid(yxstuUuid);
      String yxName = yxstudent.getName();
      for (int i = 0; i < recList.size(); i++) {
          recList.get(i).setYxstuName(yxName);
      }
      return recList;
  } else {
      System.out
              .println("RecordServiceImpl getListByYxUuid方法中的yxstuUuid为空，或格式不正确，请联系管理员");
      return new ArrayList<Record>();
  }
  }//end method

@Override
public String deleteByYxstudent(Record record) {
	// TODO Auto-generated method stub
	if (record.getYxstuUuid() != null && record.getYxstuUuid()  != "") {
    	LogGX lg = new LogGX();
        lg.setUuid(UUID.randomUUID().toString());
        lg.setUserUuid(record.getUserUuid());
        lg.setUserName(record.getUserName());
        lg.setTableName("t_record");
        lg.setTableNameChina("跟踪记录表");
        Record rec=recordDao.findIdShow(record.getUuid());
        lg.setDataUuid(rec.getYxstuUuid());
        YXstudent yx = yxd.getByUuid(rec.getYxstuUuid());
        lg.setDataName(yx.getName());
        lg.setUserAction("编辑");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String da = sdf.format(date);
        lg.setUpdateTime(da);
        lg.setDataGxUuid(rec.getUuid());
        lg.setDataGxChina(rec.getRemarkText());
        lgd.insert(lg);
      boolean daoFlag = recordDao.deleteByYxstudent(record.getYxstuUuid());

      if (daoFlag) {
          return record.getYxstuUuid() ;
      } else {
          logger.error("删除不成功,Service层执行有出错地方,请联系管理员");
          return "删除不成功,Service层执行有出错地方,请联系管理员";
      }
  } else {
      String msg = "recordService deleteByYxstudent方法中的YxstuUuid为空，或格式不正确，请重新选择";
      System.out.println(msg);
      return msg;
    }
}

}//end class
