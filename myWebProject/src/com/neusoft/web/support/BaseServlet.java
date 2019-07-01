package com.neusoft.web.support;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.html")
public class BaseServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		 String path=null;
		 String uri=request.getRequestURI();
		 String packageName="com.neusoft.web.impl.";
		 String baseName=uri.substring(uri.lastIndexOf("/")+1).replace(".html", "");
		 String controllerName=baseName.substring(0,1).toUpperCase()+baseName.substring(1);
		 
		 try 
		 {
			BaseController controller=(BaseController)Class.forName(packageName+controllerName+"Servlet").newInstance();
			controller.setDto(this.createDto(request));
			path=controller.execute();
			Map<String,Object>attribute=controller.getAttribute();
			this.parseAttribute(request, attribute);
			
		 } 
		 catch (Exception e) 
		 {
			 request.setAttribute("msg","网络故障");
			 path="error";
			e.printStackTrace();
		 } 
		 request.getRequestDispatcher("/"+path+".jsp").forward(request, response);
	}
	
	
	private void parseAttribute(HttpServletRequest request,Map<String,Object>attribute)
	{
		Set<Map.Entry<String, Object>>set=attribute.entrySet();
		for(Entry<String,Object>e:set)
		{
			request.setAttribute(e.getKey(), e.getValue());
		}
		attribute.clear();
	}
	
	/**
	 *  DTO切片
	 * @param request
	 * @return
	 */
	private   Map<String,Object> createDto(HttpServletRequest request)
	{
		Map<String,String[]> tem=request.getParameterMap();
		int initSize=((int)(tem.size()/0.75))+1;
		Set<Entry<String,String[]>> entrySet=tem.entrySet();
		String value[]=null;
		
		Map<String,Object> dto=new HashMap<>(initSize);
		for(Entry<String,String[]> entry :entrySet)
		{
			value=entry.getValue();
			if(value.length==1)  //非checkbox类控件
			{
		        //过滤掉页面未填充项目
				if(value[0]!=null && !value[0].equals(""))
				{
					dto.put(entry.getKey(), value[0]);
				}
			}
			else     //checkbox类控件
			{
				dto.put(entry.getKey(), value);
			}	
		}
		return dto;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}

}
