package com.YXcrm.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.YXcrm.dao.LogstuDao;
import com.YXcrm.model.Logstu;
import com.YXcrm.model.YXstudent;
import com.YXcrm.utility.DBUtility;

public class LogstuDaoImpl implements LogstuDao{
	private Connection connection;
	  boolean daoFlag = false;
	  
	  public LogstuDaoImpl() {
//	    connection = DBUtility.open();
	    System.out.println("connection对象在LogstuDaoImpl连接!");
	  }

	@Override
	public boolean delete(String uuid) {
		// TODO Auto-generated method stub
		PreparedStatement PSdelete = null; //关闭数据库连接insert和update和delete用到
	    try {
	      connection = DBUtility.open();//打开数据库连接
	      // Parameters start with 1
	       PSdelete = connection
	          .prepareStatement("DELETE FROM t_logstu WHERE uuid = ? ");
	      PSdelete.setString(1, uuid);
	      PSdelete.executeUpdate();
	      System.out.println("^^在执行LogstuDaoImpl中的删除delete");
	      daoFlag = true;
	      return daoFlag;
	    } catch (SQLException e) {
	      e.printStackTrace();
	      System.out.println("^^在执行LogstuDaoImpl中delete,出现sql语法执行错误，请联系管理员!");
	      daoFlag = false;
	      return daoFlag;
	    }finally{
	      ResultSet rs = null; 
	      DBUtility.close(rs, PSdelete, connection);   
	     }//finally关闭jdbc与数据库连接  
	}

	@Override
	public ArrayList<Logstu> getList() {
		// TODO Auto-generated method stub
		ArrayList<Logstu> logstu = new ArrayList<Logstu>();
	    Statement statement = null;//finally关闭数据库连接  
	    ResultSet rs = null;//关闭数据库连接get和getlist会用到
	    try {
	      connection = DBUtility.open();//打开数据库连接
	         statement = connection.createStatement();
	         rs = statement.executeQuery("SELECT * FROM t_logstu");
	        while (rs.next()) {
	        	Logstu logstu1 = new Logstu();
	        	logstu1.setUuid(rs.getString("uuid"));
	        	logstu1.setUserUuid(rs.getString("userUuid"));
	        	logstu1.setUserName(rs.getString("userName"));
	        	logstu1.setTableName(rs.getString("tableName"));
	        	logstu1.setTableNameChina(rs.getString("tableNameChina"));
	        	logstu1.setDataName(rs.getString("dataName"));
	        	logstu1.setDataUuid(rs.getString("dataUuid"));
	          
	        	logstu1.setUpdateTime(rs.getString("updateTime"));
	        	logstu1.setUserAction(rs.getString("userAction"));
	        	logstu1.setDataGxChina(rs.getString("dataGxChina"));
	          
	        	logstu.add(logstu1);
	        }
	        
	        return logstu;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("getList查询失败");
	        Logstu X = new Logstu();
	        X.setUuid("LogstuDaoImpl的getList失败返回的uuid");
	        ArrayList<Logstu> XList = new ArrayList<Logstu>();
	        XList.add(X);
	        return logstu;
	    }finally{   
	      DBUtility.close(rs, statement, connection);   
	     }//finally关闭jdbc与数据库连接
	    
	}

	@Override
	public boolean insert(Logstu logstu) {
		// TODO Auto-generated method stub
		PreparedStatement preparedStatement = null; //关闭数据库连接insert和update和delete用到
	    try {
	      connection = DBUtility.open();//打开数据库连接
	       preparedStatement = connection
	          .prepareStatement("insert into t_logstu(uuid,userUuid,userName,tableName,tableNameChina,dataUuid,dataName,userAction,updateTime,dataGxChina) values (?,?,?,?,?,?,?,?,?,?)");
	      // Parameters start with 1
	      preparedStatement.setString(1, logstu.getUuid());
	      preparedStatement.setString(2, logstu.getUserUuid());
	      preparedStatement.setString(3, logstu.getUserName());
	      preparedStatement.setString(4, logstu.getTableName());
	      preparedStatement.setString(5, logstu.getTableNameChina());
	      preparedStatement.setString(6, logstu.getDataUuid());
	      preparedStatement.setString(7, logstu.getDataName());
	      
	      preparedStatement.setString(8, logstu.getUserAction());
	      preparedStatement.setString(9, logstu.getUpdateTime());
	      preparedStatement.setString(10, logstu.getDataGxChina());
	      preparedStatement.executeUpdate();

	      System.out.println("^^在执行LogstuDaoImpl中的insert添加");
	      daoFlag = true;
	      return daoFlag;
	    } catch (SQLException e) {
	      System.out.println("^^在执行LogstuDaoImpl中insert,出现sql语法执行错误，请联系管理员!");
	      e.printStackTrace();
	      daoFlag = false;
	      return daoFlag;
	    }finally{
	      ResultSet rs = null; 
	      DBUtility.close(rs, preparedStatement, connection);   
	     }//finally关闭jdbc与数据库连接  
	}

	@Override
	public boolean update(Logstu logstu) {
		// TODO Auto-generated method stub
		 PreparedStatement preparedStatement = null; //关闭数据库连接insert和update和delete用到
		    try {
		      connection = DBUtility.open();//打开数据库连接
		       preparedStatement = connection
		          .prepareStatement("UPDATE t_yxstudent SET userUuid = ?, userName = ?,tableName = ?, tableNameChina = ?, dataUuid = ?, dataName = ?,userAction = ?,updateTime = ?  WHERE uuid = ? ");
		      // Parameters start with 1
		       
		       
			      preparedStatement.setString(1, logstu.getUserUuid());
			      preparedStatement.setString(2, logstu.getUserName());
			      preparedStatement.setString(3, logstu.getTableName());
			      preparedStatement.setString(4, logstu.getTableNameChina());
			      preparedStatement.setString(5, logstu.getDataUuid());
			      preparedStatement.setString(6, logstu.getDataName());
			      
			      preparedStatement.setString(7, logstu.getUserAction());
			      preparedStatement.setString(8, logstu.getUpdateTime());
			      preparedStatement.setString(9, logstu.getUuid());
			      preparedStatement.executeUpdate();

		      System.out.println("^^在执行yxstudentDaoImpl中的修改update");
		      daoFlag = true;
		      return daoFlag;
		    } catch (SQLException e) {
		      e.printStackTrace();
		      System.out.println("^^在执行yxstudentDaoImpl中update,出现sql语法执行错误，请联系管理员!");
		      daoFlag = false;
		      return daoFlag;
		    }finally{
		      ResultSet rs = null; 
		      DBUtility.close(rs, preparedStatement, connection);   
		     }//finally关闭jdbc与数据库连接  
	}

	@Override
	public boolean deleteBatch(String uuid) {
		// TODO Auto-generated method stub
		PreparedStatement preparedStatement = null; //关闭数据库连接insert和update和delete用到
		connection = DBUtility.open();//打开数据库连接
        try {
			preparedStatement = connection
			  .prepareStatement("DELETE FROM t_logstu WHERE uuid = ? ");
			preparedStatement.setString(1, uuid);
			preparedStatement.executeUpdate();
			System.out.println("^^在执行LogstuDaoImpl中的删除delete");
		      daoFlag = true;
		      return daoFlag;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("^^在执行LogGXDaoImpl中delete,出现sql语法执行错误，请联系管理员!");
		    daoFlag = false;
		    return daoFlag;
		}finally{
	      ResultSet rs = null; 
	      DBUtility.close(rs, preparedStatement, connection);   
	     }//finally关闭jdbc与数据库连接  
	}

}
