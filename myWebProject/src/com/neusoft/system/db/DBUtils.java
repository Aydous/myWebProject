package com.neusoft.system.db;
import java.sql.*;
import java.util.ResourceBundle;
public class DBUtils 
{
	private static String driver=null;
	private static String url=null;
	private static String username=null;
	private static String password=null;
	private static final ThreadLocal<java.sql.Connection> threadLocal=new ThreadLocal<>();
	
	static
	{
		try {
			ResourceBundle bundle=ResourceBundle.getBundle("DBOptions");
			driver=bundle.getString("DRIVER");
			url=bundle.getString("URL");
			username=bundle.getString("USERNAME");
			password=bundle.getString("PASSWORD");
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private DBUtils()
	{
		
	}
	
	private static Connection getConnection()throws Exception
	{
		Connection conn=threadLocal.get();
		if(conn==null||conn.isClosed())
		{
			conn=DriverManager.getConnection(url,username,password);	
			threadLocal.set(conn);
		}
		
		return conn;
	}
	
	public static PreparedStatement prepareStatement(final String sql)throws Exception
	{
		return DBUtils.getConnection().prepareStatement(sql);
	} 
	
	//======================以下为事务相关方法====================
	
	public static void beginTransaction()throws Exception
	{
		DBUtils.getConnection().setAutoCommit(false);
	}
	
	public static void endTranscation()
	{
		try 
		{
			DBUtils.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void commit()throws Exception
	{
		DBUtils.getConnection().commit();
	}
	
	public static void rollback()
	{
		try 
		{
			DBUtils.getConnection().rollback();
		} 
		catch (Exception e) 
		{
			try
			{
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//=======================以下为销毁方法=======================
	
	public static void close()
	{
		Connection conn=threadLocal.get();
		try {
			if(conn!=null&&!conn.isClosed())
			{
				threadLocal.remove();
				conn.close();	

			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstm)
	{
		try {
			if(pstm!=null)
			{
				pstm.close();	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs)
	{
		try {
			if(rs!=null)
			{
				rs.close();	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		try {
//			Connection conn=DBUtils.getConnection();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	}


