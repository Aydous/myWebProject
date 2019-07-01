package com.neusoft.system.tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.neusoft.system.db.DBUtils;



public class Tools 
{
	
	public static void main(String[] args) 
	{
		try 
		{
			System.out.println(Tools.getEmpNumber());
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private static final String base_code="0000";
	
	public Tools() {}
	
	
	public static String getEmpNumber() throws Exception
	{
		return Tools.getCurrentYear()+Tools.getFormatNumber("e");
	}
	//得到四位尾码
	public static String getFormatNumber(String pkname) throws Exception
	{
		int empNumber=Tools.getNumberForYear(pkname);
		int len=String.valueOf(empNumber).length();
		String result=base_code.substring(len)+empNumber;
		return result;
	}
	
	private static String getCurrentYear()
	{
		
		return new SimpleDateFormat("YYYY").format(new Date());
	}
	
	private static int getNumberForYear(String pkname)throws Exception
	{
		PreparedStatement pstm1=null;
		PreparedStatement pstm2=null;
		ResultSet rs=null;
		try
		{
			StringBuilder sql1=new StringBuilder()
					.append(" select s.pkvalue from sequence s")
					.append(" where s.pkname=? and ")
					.append(" DATE_FORMAT(s.sdate,'%Y')=DATE_FORMAT(CURRENT_DATE(),'%Y')")
					;
			pstm1=DBUtils.prepareStatement(sql1.toString());
			pstm1.setObject(1, pkname);
			rs=pstm1.executeQuery();
			StringBuilder sql2=new StringBuilder();
			int currentval=0;
			if(rs.next())
			{
				currentval=rs.getInt(1);
				sql2.append("update sequence ")
			    .append("   set pkvalue=?")
			    .append(" where date_format(sdate,'%Y')=date_format(current_date,'%Y')")
			    .append("   and pkname=?");
				
			}
			else
			{
				sql2.append(" INSERT into sequence(pkvalue,pkname,sdate) ")
				    .append(" VALUES(?,?,current_date)");
				
			}
			pstm2=DBUtils.prepareStatement(sql2.toString());
			pstm2.setObject(1,++currentval );
			pstm2.setObject(2,pkname);
			pstm2.executeUpdate();
			return currentval;
		}
		finally
		{
			rs.close();
			pstm1.close();
			pstm2.close();
		}
	}
	
	public static String joinArray(Object o)
	{
		
		if(o==null||o.equals(""))//拦截空值
		{
			return "";
		}
		if(o instanceof java.lang.String)//看是字符串还是数组
		{
			return o.toString();
		}
		else
		{	
			//转化为数组
			String arrs[]=(String[])o;
			StringBuilder result=new StringBuilder().append(arrs[0]);
			int count=arrs.length;
			for(int i=1;i<count;i++)
			{
				result.append(",").append(arrs[i]);
			}
			
			return result.toString();	
		}
		
	}
	
	public static Map<String,Object> CreateDto(HttpServletRequest request)
	{
		Map<String,String[]>temp=request.getParameterMap();
		Set<Entry<String,String[]>> set=temp.entrySet();
		String value[]=null;
		int count=temp.size();
		int initSize=((int)(count/0.75))+1;
		Map<String,Object>dto=new HashMap<>(initSize);
		for(Entry<String,String[]>e:set)
		{
			value=e.getValue();
			if(value.length==1)
			{
				if(!value[0].equals("") && value[0]!=null)//过滤页面未填充控件
				{
					dto.put(e.getKey(), value[0]);
				}
			}
			else
			{
				dto.put(e.getKey(), value);
			}
		}
		return dto;
	}
	
	public static int getSequence(String pkname)throws Exception
	{
		PreparedStatement pstm1=null;
		PreparedStatement pstm2=null;
		ResultSet rs=null;

		try
		{
			String sql1="select a.pkvalue from sequence a where a.pkname=?  ";
			pstm1=DBUtils.prepareStatement(sql1);
			pstm1.setObject(1, pkname);
			rs=pstm1.executeQuery();
			int currentVal=0;
			String sql2=null;
			if(rs.next())
			{
				currentVal=rs.getInt(1);
				sql2="update sequence set pkvalue=? where pkname=?";
				
			}
			else
			{
				sql2="insert into sequence(pkvalue,pkname) values(?,?)";
			}
			pstm2=DBUtils.prepareStatement(sql2);
			pstm2.setObject(1, ++currentVal);
			pstm2.setObject(2, pkname);
			pstm2.executeUpdate();
			return currentVal;
		}
		
		finally
		{
			rs.close();
			pstm2.close();
			pstm1.close();
		}
	}

}
