package com.YXcrm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.YXcrm.model.BackResult;
import com.YXcrm.model.LogGX;
import com.YXcrm.model.Logstu;
import com.YXcrm.service.LoggxService;
import com.YXcrm.service.impl.LoggxServiceImpl;
import com.YXcrm.utility.M_msg;
import com.YXcrm.utility.T_DataControl;
import com.YXcrm.utility.T_DataMap2Bean;
import com.google.gson.Gson;

public class LoggxController extends HttpServlet{
	LoggxService lgxs = new LoggxServiceImpl();
	BackResult backResult = new BackResult("信息值：默认","请求值：默认",null);
	  public M_msg m_msg = new M_msg();
	  Logger logger = Logger.getLogger(LogstuController.class);
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {

		    this.doPost(request, response);
		  }// end doGet
		  
		  protected void doPost(HttpServletRequest request, HttpServletResponse response)
		      throws ServletException, IOException {
		    // TODO doPost
		    response.setContentType("text/html;charset=utf-8");
		    request.setCharacterEncoding("utf-8");
		    PrintWriter out = response.getWriter();

		    // 1 获取url问号后面的Query 参数
		    String qqiu = request.getParameter("qqiu");

		    if (qqiu.equals("test") || qqiu.equals("add") || qqiu.equals("delete") || qqiu.equals("edit")
		        || qqiu.equals("getOne")|| qqiu.equals("on_off") || qqiu.equals("editstuID")  || qqiu.equals("getMaxStuID")
		        || qqiu.equals("deleteBatch")) {
		      // 2 将前台json数据字符串转成实体对象
		      T_DataControl t_data = new T_DataControl();
		      String str = t_data.getRequestPayload(request);
		      LogGX logstu = new LogGX();
		      List<String> uuidList =new ArrayList();
		      if (str != null && str != "" && str.length() != 0) { // 非空判断，防止前台传空报500服务器错误中的空指针
		        Map<String, Object> map = t_data.JsonStrToMap(str);
		        T_DataMap2Bean t_map2bean = new T_DataMap2Bean();
		        Map<String, Object> map2 = t_data.JsonStrToMap(str);
	            uuidList = (List<String>) map2.get("uuidList");
		        logstu = t_map2bean.MapToLogGX(map);
		      } else {
		        System.out.println("前台传入post请求体数据为空，请联系管理员！");
		      }

		      // 3 执行qqiu里面的增或删或改或查 的操作
		      qqiuChoice(qqiu, logstu,uuidList);
		    } else if (qqiu.equals("list")) {
		      // TODO 待完成
		      ArrayList<LogGX> resultList = lgxs.getList();
		      backResult.setMessage("查询成功");
		      backResult.setQingqiu("list查询列表");
		      backResult.setData(resultList);

		    }else if (qqiu.equals("claTeaList")) {
		      // TODO 待完成

		    } else {
		      System.out.println("qqiu请求参数  " + qqiu + "  不规范");
		    }

		    Gson gson = new Gson();
		    // 4 执行完qqiuChoice里面操作后的全局返回值backResult对象,转成json格式
		    String back = gson.toJson(backResult);
		    System.out.println("最后back值是：" + back);
		    // 5 将json格式的back传给前台
		    out.write(back);
		    out.flush();
		    out.close();

		  }// end method doPost 主入口

		  private void qqiuChoice(String qqiu, LogGX loggx,List<String> uuidList) {
		    // TODO Auto-generated method stub
		    boolean test = false;
		    boolean add = false;
		    boolean delete = false;
		    boolean edit = false;
		    boolean getOne = false;
		    boolean on_off = false;
		    boolean editstuID=false;
		    boolean getMaxStuID=false;
		    boolean deleteBatch =false;
		    test = qqiu.equals("test");
		    add = qqiu.equals("add");
		    delete = qqiu.equals("delete");
		    edit = qqiu.equals("edit");
		    getOne = qqiu.equals("getOne");
		    on_off = qqiu.equals("on_off");
		    editstuID=qqiu.equals("editstuID");
		    getMaxStuID=qqiu.equals("getMaxStuID");
		    deleteBatch = qqiu.equals("deleteBatch");
		    if (test) {
		      logger.error("日志打印测试YXstudentController测试test方法,测试成功");      
		      backResult.setMessage("信息值,测试成功");
		      backResult.setQingqiu("test新增");
		      ArrayList<String> resultList = new ArrayList<String>();
		      resultList.add("内容值,测试成功1");
		      resultList.add("内容值,测试成功2");
		      resultList.add("内容值,测试成功3YXstudentController");
		      backResult.setData(resultList);
		    }
		    if (add) {
		      /*String result = lgs.insert(logstu);
		      ArrayList<String> resultList = new ArrayList<String>();
		      resultList.add(result);
		      m_msg = lgs.getMsg();
		      backResult.setMessage(m_msg.getAddMsg());
		      backResult.setQingqiu("请求add");
		      backResult.setData(resultList);
		      m_msg.cleanMsg();*/
		    }
		    if (delete) {
		      String result = lgxs.delete(loggx.getUuid());
		      ArrayList<String> resultList = new ArrayList<String>();
		      resultList.add(result);
		      backResult.setMessage("信息值：成功");
		      backResult.setQingqiu("学员日志信息删除" + loggx.getUuid());
		      backResult.setData(resultList);
		    }
		    if (edit) {
		      /*String result = yxstudentService.update(yxstudent);
		      ArrayList<String> resultList = new ArrayList<String>();
		      resultList.add(result);
		      m_msg = yxstudentService.getMsg();
		      backResult.setMessage(m_msg.getEditMsg());
		      backResult.setQingqiu("请求add");
		      backResult.setData(resultList);
		      m_msg.cleanMsg();*/
		    }
		    if (on_off) {
		      //判断openAndclose参数规范
		      /*String oAc = yxstudent.getOpenAndclose();
		      String flagQqiu = "初始值";
		      String result = "初始值";
		      if(oAc.equals("open")||oAc.equals("close")){
		          if(oAc.equals("open")){flagQqiu = "on";}
		          if(oAc.equals("close")){flagQqiu = "off";}
		           result = yxstudentService.getonoff(yxstudent);
		      }else { 
		          logger.error("操作失败：开关参数不规范" + "(" + oAc + "),联系前端开发");
		          flagQqiu = "err"; result = "操作失败：开关参数不规范"+"("+oAc+")";}
		      
		      ArrayList<String> resultList = new ArrayList<String>();
		      resultList.add(result);
		      backResult.setMessage(result);
		      backResult.setQingqiu(flagQqiu);
		      backResult.setData(resultList);*/
		    }//end on_off
		    if(editstuID){
		    	/*String result = yxstudentService.updateStuId(yxstudent.getStudentID(), yxstudent.getUuid());
		        ArrayList<String> resultList = new ArrayList<String>();
		        resultList.add(result);
		        m_msg = yxstudentService.getMsg();
		        backResult.setMessage(m_msg.getEditMsg());
		        backResult.setQingqiu("请求editstuID");
		        backResult.setData(resultList);
		        m_msg.cleanMsg();*/
		    }
		    if(getMaxStuID){
		    	/*int result = yxstudentService.findMaxIdShow();
		        ArrayList<Integer> resultList = new ArrayList<Integer>();
		        resultList.add(result);
		        m_msg = yxstudentService.getMsg();
		        m_msg.setEditMsg("studentID最大值为"+result);
		        backResult.setMessage(m_msg.getEditMsg());
		        backResult.setQingqiu("最大值studentID"+result);
		        backResult.setData(resultList);
		        m_msg.cleanMsg();*/
		    }
		    if (deleteBatch) {
				String result = lgxs.deleteBatch(uuidList);
				System.out.println("删除功能传进来的uuid================="
						+ result);
				ArrayList<String> resultList = new ArrayList<String>();
				resultList.add(result);
				backResult.setMessage( result);
				backResult.setQingqiu("delete删除"+ result);
				backResult.setData(resultList);
			}
		  }//end method 
}
