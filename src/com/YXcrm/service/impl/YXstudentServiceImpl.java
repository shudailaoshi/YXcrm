package com.YXcrm.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.YXcrm.dao.DphoneDao;
import com.YXcrm.dao.DweixinDao;
import com.YXcrm.dao.EmployeeDao;
import com.YXcrm.dao.LogstuDao;
import com.YXcrm.dao.RecordDao;
import com.YXcrm.dao.YXstudentDao;
import com.YXcrm.dao.impl.DphoneDaoImpl;
import com.YXcrm.dao.impl.DweixinDaoImpl;
import com.YXcrm.dao.impl.EmployeeDaoImpl;
import com.YXcrm.dao.impl.LogstuDaoImpl;
import com.YXcrm.dao.impl.RecordDaoImpl;
import com.YXcrm.dao.impl.YXstudentDaoImpl;
import com.YXcrm.model.Dphone;
import com.YXcrm.model.Dweixin;
import com.YXcrm.model.Employee;
import com.YXcrm.model.Logstu;
import com.YXcrm.model.YXstudent;
import com.YXcrm.service.DphoneService;
import com.YXcrm.service.DweixinService;
import com.YXcrm.service.YXstudentService;
import com.YXcrm.utility.M_msg;

/**
 *树袋老师
 * @author 作者 xpp
 * @version 创建时间：2018-2-22 下午6:07:29
 * 类说明
 */

public class YXstudentServiceImpl implements YXstudentService{
  
  private YXstudentDao yxstudentDao = new YXstudentDaoImpl();
  public M_msg m_msg = new M_msg();
  Logger logger = Logger.getLogger(YXstudentServiceImpl.class);
  private LogstuDao lgd = new LogstuDaoImpl();
  private DphoneService dphoneService = new DphoneServiceImpl();
  DweixinService dweixinService = new DweixinServiceImpl();
  private DphoneDao dphoneDao = new DphoneDaoImpl();
  private DweixinDao dweixinDao=new DweixinDaoImpl();
  private RecordDao recordDao = new RecordDaoImpl();
  private EmployeeDao employeeDao = new EmployeeDaoImpl();
  
  @Override
  public M_msg getMsg() {
    // TODO Auto-generated method stub
    return m_msg;
  }

  @Override
  public String insert(YXstudent yxstudent) {
    // TODO Auto-generated method stub
	  YXstudent yxs = yxstudentDao.findId(yxstudent.getStudentID());
	  /*if(yxstudent.getStudentID()==yxs.getStudentID()){
		  System.out.println("有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+"");
		  logger.error("有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+"");
	      m_msg.setAddMsg("有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+"");
		 return "有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+""; 
	  }*/
    yxstudent.setUuid(null);
    yxstudent.setUuid(UUID.randomUUID().toString());
    System.out.println("^^在EmployeeServiceImpl收到数据 ："
            + yxstudent.toString() + "收到结束!");
    int creStudentId=yxstudentDao.findMaxIdShow()+1;
    yxstudent.setStudentID(creStudentId);
    boolean daoFlag = yxstudentDao.insert(yxstudent);
    if (daoFlag) {
    //写入手机号码库---1自己的手机
      Dphone dphone1 = new Dphone();
      dphone1.setPhoneNO(yxstudent.getPhone());
      dphone1.setYxstuUuid(yxstudent.getUuid());
      dphone1.setEmpDitUuid(yxstudent.getEmpUuid());
      String result1 = dphoneService.insert(dphone1);
      
    //写入手机号码库---2父母的第一个手机
      Dphone dphone2 = new Dphone();
      dphone2.setPhoneNO(yxstudent.getParentPhone());
      dphone2.setYxstuUuid(yxstudent.getUuid());
      dphone2.setEmpDitUuid(yxstudent.getEmpUuid());
      String result2 = dphoneService.insert(dphone2);
      
    //写入手机号码库---3父母的第二个手机
      Dphone dphone3 = new Dphone();
      dphone3.setPhoneNO(yxstudent.getParentPhone2());
      dphone3.setYxstuUuid(yxstudent.getUuid());
      dphone3.setEmpDitUuid(yxstudent.getEmpUuid());
      String result3 = dphoneService.insert(dphone3);
      
    //写入微信号码库---1自己的微信
      Dweixin dweixin1 = new Dweixin();
      dweixin1.setWeixinNO(yxstudent.getWeixin());
      dweixin1.setYxstuUuid(yxstudent.getUuid());
      dweixin1.setEmpDitUuid(yxstudent.getEmpUuid());
      String weiResult1 = dweixinService.insert(dweixin1);
      
    //写入微信号码库---2父母的第一个微信
      Dweixin dweixin2 = new Dweixin();
      dweixin2.setWeixinNO(yxstudent.getParentWeixin());
      dweixin2.setYxstuUuid(yxstudent.getUuid());
      dweixin2.setEmpDitUuid(yxstudent.getEmpUuid());
      String weiResult2 = dweixinService.insert(dweixin2);
      
    //写入微信号码库---3父母的第二个微信
      Dweixin dweixin3 = new Dweixin();
      dweixin3.setWeixinNO(yxstudent.getParentWeixin2());
      dweixin3.setYxstuUuid(yxstudent.getUuid());
      dweixin3.setEmpDitUuid(yxstudent.getEmpUuid());
      String weiResult3 = dweixinService.insert(dweixin3);
      
      Logstu lg = new Logstu();
      lg.setUuid(UUID.randomUUID().toString());
      lg.setUserUuid(yxstudent.getUserUuid());
      lg.setUserName(yxstudent.getUserName());
      lg.setTableName("t_yxstudent");
      lg.setTableNameChina("学员表");
      lg.setDataUuid(yxstudent.getUuid());
      lg.setDataName(yxstudent.getName());
      lg.setUserAction("新增");
      Date date = new Date();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String da = sdf.format(date);
      lg.setUpdateTime(da);
      lgd.insert(lg);
      
        return yxstudent.getUuid();
    } else {
        logger.error("插入不成功,dao层执行有出错地方,请联系管理员YXstudentServiceImpl");
        m_msg.setAddMsg("插入不成功,dao层执行有出错地方,请联系管理员YXstudentServiceImpl");
        return "插入不成功,dao层执行有出错地方,请联系管理员YXstudentServiceImpl";
    }
  }//end method insert

  @Override
  public ArrayList<YXstudent> getList() {
    ArrayList<YXstudent> yxstuList = yxstudentDao.getList();
    return yxstuList;
  }//end method 

  @Override
  public String delete(YXstudent yxs) {
 // TODO Auto-generated method stub
    if (yxs.getUuid() != null && yxs.getUuid() != "") {
    	List<YXstudent> yxstudent = yxstudentDao.findIdlist(yxs.getUuid());
    	Logstu lg = new Logstu();
    	lg.setUuid(UUID.randomUUID().toString());
        lg.setUserUuid(yxs.getUserUuid());
        lg.setUserName(yxs.getUserName());
        lg.setTableName("t_yxstudent");
        lg.setTableNameChina("学员表");
        lg.setDataUuid(yxstudent.get(0).getUuid());
        lg.setDataName(yxstudent.get(0).getName());
        lg.setUserAction("删除");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String da = sdf.format(date);
        lg.setUpdateTime(da);
        lgd.insert(lg);
        boolean daoFlag = yxstudentDao.delete(yxs.getUuid());

        if (daoFlag) {
        //删除手机号码库，根据学生uuid
          boolean daoFlagphone = dphoneDao.deleteByYXstuUid(yxs.getUuid());
          
        //删除微信号码库，根据学生uuid
          boolean daoFlagweixin = dweixinDao.deleteByYXstuUuid(yxs.getUuid());
          
          //删除跟踪记录表，根据学生uuid
          boolean daoFlagRecord = recordDao.deleteByYxstudent(yxs.getUuid());
          
            return yxs.getUuid();
        } else {
            logger.error("删除不成功,dao层执行有出错地方,请联系管理员");
            return "删除不成功,dao层执行有出错地方,请联系管理员";
        }
    } else {
        String msg = "yxstudentServiceImpl delete方法中的uuid为空，或格式不正确，请重新选择";
        System.out.println(msg);
        return msg;
    }
  }//end method

  @Override
  public String update(YXstudent yxstudent) {
    // TODO Auto-generated method stub
	  
	YXstudent yxs = yxstudentDao.getByUuid(yxstudent.getUuid());
	boolean flag = yxs.getEmpUuid().equals(yxstudent.getEmpUuid());
	if((!flag) && yxs != null){
		Logstu lg = new Logstu();
    	lg.setUuid(UUID.randomUUID().toString());
        lg.setUserUuid(yxstudent.getUserUuid());
        lg.setUserName(yxstudent.getUserName());
        lg.setTableName("t_yxstudent");
        lg.setTableNameChina("学员表");
        lg.setDataUuid(yxs.getUuid());
        lg.setDataName(yxs.getName());
        lg.setUserAction("变更负责人");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String da = sdf.format(date);
        lg.setUpdateTime(da);
        
        //写入原来XXX负责人变成了现在XXX负责人
        String strText = "由";
         strText = strText+ "("+ yxs.getEmpName()+")，变为(";
        //查出更改的负责人
        Employee employee = employeeDao.getByUuid(yxstudent.getEmpUuid());
        strText = strText +employee.getName()+ ")";
        lg.setDataGxChina(strText);
        lgd.insert(lg);
	}
    String uuid = yxstudent.getUuid();
    if (uuid != null && uuid != "") {
    	Logstu lg = new Logstu();
    	lg.setUuid(UUID.randomUUID().toString());
        lg.setUserUuid(yxstudent.getUserUuid());
        lg.setUserName(yxstudent.getUserName());
        lg.setTableName("t_yxstudent");
        lg.setTableNameChina("学员表");
        lg.setDataUuid(yxstudent.getUuid());
        lg.setDataName(yxstudent.getName());
        lg.setUserAction("编辑");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String da = sdf.format(date);
        lg.setUpdateTime(da);
        lgd.insert(lg);
        boolean daoFlag = yxstudentDao.update(yxstudent);

        if (daoFlag) {
          //删除手机号码库，根据学生uuid
          boolean daoFlagphone = dphoneDao.deleteByYXstuUid(yxstudent.getUuid());
          
        //删除微信号码库，根据学生uuid
          boolean daoFlagweixin = dweixinDao.deleteByYXstuUuid(yxstudent.getUuid());
          
        //写入手机号码库---1自己的手机
          Dphone dphone1 = new Dphone();
          dphone1.setPhoneNO(yxstudent.getPhone());
          dphone1.setYxstuUuid(yxstudent.getUuid());
          dphone1.setEmpDitUuid(yxstudent.getEmpUuid());
          String result1 = dphoneService.insert(dphone1);
          
        //写入手机号码库---2父母的第一个手机
          Dphone dphone2 = new Dphone();
          dphone2.setPhoneNO(yxstudent.getParentPhone());
          dphone2.setYxstuUuid(yxstudent.getUuid());
          dphone2.setEmpDitUuid(yxstudent.getEmpUuid());
          String result2 = dphoneService.insert(dphone2);
          
        //写入手机号码库---3父母的第二个手机
          Dphone dphone3 = new Dphone();
          dphone3.setPhoneNO(yxstudent.getParentPhone2());
          dphone3.setYxstuUuid(yxstudent.getUuid());
          dphone3.setEmpDitUuid(yxstudent.getEmpUuid());
          String result3 = dphoneService.insert(dphone3);
          
        //写入微信号码库---1自己的微信
          Dweixin dweixin1 = new Dweixin();
          dweixin1.setWeixinNO(yxstudent.getWeixin());
          dweixin1.setYxstuUuid(yxstudent.getUuid());
          dweixin1.setEmpDitUuid(yxstudent.getEmpUuid());
          String weiResult1 = dweixinService.insert(dweixin1);
          
        //写入微信号码库---2父母的第一个微信
          Dweixin dweixin2 = new Dweixin();
          dweixin2.setWeixinNO(yxstudent.getParentWeixin());
          dweixin2.setYxstuUuid(yxstudent.getUuid());
          dweixin2.setEmpDitUuid(yxstudent.getEmpUuid());
          String weiResult2 = dweixinService.insert(dweixin2);
          
        //写入微信号码库---3父母的第二个微信
          Dweixin dweixin3 = new Dweixin();
          dweixin3.setWeixinNO(yxstudent.getParentWeixin2());
          dweixin3.setYxstuUuid(yxstudent.getUuid());
          dweixin3.setEmpDitUuid(yxstudent.getEmpUuid());
          String weiResult3 = dweixinService.insert(dweixin3);
          
            return uuid;
        } else {
            logger.error("修改不成功,dao层执行有出错地方,请联系管理员");
            m_msg.setAddMsg("修改不成功,dao层执行有出错地方,请联系管理员YXstudentServiceImpl");
            return "修改不成功,dao层执行有出错地方,请联系管理员";
        }
    } else {
        String msg = "YXstudentServiceImpl update方法中的uuid为空，或格式不正确，请重新选择";
        System.out.println(msg);
        return msg;
    }
  }//end method

  @Override
  public String getonoff(YXstudent yxstudent) {
 // TODO Auto-generated method stub
    String uuid = yxstudent.getUuid();
    if(uuid!=null&&uuid!="")
    {
      String oAc = yxstudent.getOpenAndclose();
      boolean daoFlag = yxstudentDao.updateOnOff(uuid,oAc);
      
        if(daoFlag)
        {
        return "操作成功";
        }else{
            logger.error("操作不成功,dao层执行有出错地方,请联系管理员");
          return "操作失败,dao层执行有出错地方,请联系管理员";
        }
    }else{
      String msg="ClassRoomServiceImpl delete方法中的uuid为空，或格式不正确，请重新选择";
      System.out.println(msg);
      return msg;
    }
  }//end method

@Override
public int findMaxIdShow() {
	// TODO Auto-generated method stub
	 int yxstuList = yxstudentDao.findMaxIdShow();
	 m_msg.setAddMsg("查询成功了,最大学号为"+yxstudentDao.findMaxIdShow()+"");
	 return yxstuList;
}

@Override
public String updateStuId(int StuId, String uuid,String name) {
	// TODO Auto-generated method stub
	YXstudent yxs = yxstudentDao.findId(StuId);
	  if(StuId==yxs.getStudentID()){
		  System.out.println("有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+"");
		  logger.error("有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+"");
	      m_msg.setEditMsg("有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+"");
		 return "有重复学生ID，请重新添加，最大学号为"+yxstudentDao.findMaxIdShow()+""; 
	  }
	  if (uuid != null && uuid != "") {
	        boolean daoFlag = yxstudentDao.updateStuId(StuId, uuid,name);

	        if (daoFlag) {
	            return uuid;
	        } else {
	            logger.error("修改不成功,dao层执行有出错地方,请联系管理员");
	            m_msg.setAddMsg("修改不成功,dao层执行有出错地方,请联系管理员YXstudentServiceImpl");
	            return "修改不成功,dao层执行有出错地方,请联系管理员";
	        }
	    } else {
	        String msg = "YXstudentServiceImpl updateStuId方法中的uuid为空，或格式不正确，请重新选择";
	        System.out.println(msg);
	        return msg;
	    }
}


}//end class
