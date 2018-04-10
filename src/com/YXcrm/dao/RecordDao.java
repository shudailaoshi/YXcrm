package com.YXcrm.dao;

import java.util.ArrayList;

import com.YXcrm.model.Record;
import com.YXcrm.model.YXstudent;

/**
 *树袋老师
 * @author 作者 xpp
 * @version 创建时间：2018-2-26 下午6:27:14
 * 类说明
 */

public interface RecordDao {

  boolean insert(Record record);

  boolean delete(String uuid);

  boolean update(Record record);

  ArrayList<Record> getListByYxUuid(String yxstuUuid);
  
  Record findIdShow(String uuid);
  
  boolean deleteByYxstudent(String yxstuUuid);
  
  boolean updateModifyDate(String modifyDate,String uuid);

}//end interface
