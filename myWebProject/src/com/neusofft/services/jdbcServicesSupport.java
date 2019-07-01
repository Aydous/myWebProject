package com.neusofft.services;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusoft.system.db.DBUtils;
public abstract class jdbcServicesSupport implements BaseServices
{
	private Map<String,Object> dto=null;

	@Override
	public final void setDto(Map<String,Object>dto)
	{
		this.dto=dto;
	}
	protected final Object get(String key)
	{
		return this.dto.get(key);
	}
	protected final void put(String key,Object value)
	{
		this.dto.put(key, value);
	}
	private final List<PstmMetaData>pstmLists=new ArrayList<>();
	//=============================辅助方法============================
	
	protected final String[]getIdList(String key)
	{
		Object o=this.dto.get(key);
		if( o instanceof java.lang.String[])
		{
			return (String[])o;
		}
		else
		{
			return new String[]{o.toString()};
		}
	}
	
	//判断对象不为空值和null
	protected static boolean isNotNull(Object o)
	{
		return o!=null && !o.equals("");
	}
	
	//===============向list注册批处理sql编译语句=================
	private void regPstmObject(PreparedStatement pstm)
	{
		PstmMetaData pmd=new PstmMetaData(pstm,true);
		this.pstmLists.add(pmd);
	}
	
	//执行pstmLists中的事务
	protected final boolean executeTransaction()throws Exception
	{
		boolean tag=false;
		DBUtils.beginTransaction();
		try
		{
			for(PstmMetaData pmd:this.pstmLists)
			{
				pmd.executePreparedStatement();
			}
			DBUtils.commit();
			tag=true;
		}
		catch(Exception e)
		{
			DBUtils.rollback();
			e.printStackTrace();
		}
		finally
		{
			DBUtils.endTranscation();
			for(PstmMetaData pmd:pstmLists)
			{
				pmd.close();
			}
			this.pstmLists.clear();
		}
		return tag;
	}
	
	
	/**
	 * 单一状态批处理
	 * <
	 *    适合如下SQL语句
	 *    update table 
	 *       set col=?
	 *     where id=? 
	 * >
	 * @param sql      ---  sql语句
	 * @param newState ---  单一列的目标状态
	 * @param idlist   ---  主键数组
	 * @return
	 */
	protected void appendBatch(final String sql,final Object newState,final Object...idLists)throws Exception
	{
		PreparedStatement pstm=null;
		pstm=DBUtils.prepareStatement(sql);
		pstm.setObject(1, newState);
		for(Object id:idLists)
		{
			pstm.setObject(2, id);
			pstm.addBatch();
		}
		this.regPstmObject(pstm);
	}
	
	/**
	 * 数据批量删除方法
	 * <
	 *    delete  from  table where id=?
	 * >
	 * @param sql      ---  sql语句
	 * @param idlist   ---  主键列表
	 * @return
	 * @throws Exception
	 */
	protected final void appendBatch(String sql,Object...args)throws Exception
	{
		PreparedStatement pstm=null;
		pstm=DBUtils.prepareStatement(sql);
		for(Object id:args)
		{
			pstm.setObject(1, id);
			pstm.addBatch();
		}
		this.regPstmObject(pstm);
	}
	
	
	/**
	 * 多状态修改
	 * <
	 *   update table
	 *     set col1=?,col2=?,col3=?...coln=?
	 *   where id=?
	 *   例如:给一批员工上调职级并同时加薪
	 *   update ac01 
	 *       set aac111=aac111+?,aac112=? 
	 *     where aac101=?
	 * >
	 * @param sql
	 * @param stateList
	 * @param idlist
	 * @return
	 * @throws Exception
	 */
	protected final void appendBatch(String sql,Object stateLists[],Object...args)throws Exception
	{
		PreparedStatement pstm=null;
		pstm=DBUtils.prepareStatement(sql);
		int index=1;
		for(Object state:stateLists)
		{
			pstm.setObject(index++, state);
		}
		for(Object id:args)
		{
			pstm.setObject(index, id);
			pstm.addBatch();
		}
		this.regPstmObject(pstm);
	}
	
	
	
	/**
	 * 为事务添加非批处理SQL语句
	 * <
	 *   delete from table where id=?
	 *   update table set col1=? where id=?
	 *   update table set col1=?,col2=?,col3=?.... where id=?
	 * >
	 * @param sql      ----  执行的SQL语句
	 * @param args     ----  参数列表
	 * @throws Exception
	 */
	protected final void appendSql(String sql,Object...args)throws Exception
	{
		PreparedStatement pstm=null;
		pstm=DBUtils.prepareStatement(sql);
		int index=1;
		for(Object param:args)
		{
			pstm.setObject(index++,param);
		}
		PstmMetaData pmd=new PstmMetaData(pstm,false);
		this.pstmLists.add(pmd);
	}
	
	/**************************************************************
	    * 	                       以下为单一表事务更新方法
	    **************************************************************/
	/**
	 * 多状态修改
	 * <
	 *   update table
	 *     set col1=?,col2=?,col3=?...coln=?
	 *   where id=?
	 *   例如:给一批员工上调职级并同时加薪
	 *   update ac01 
	 *       set aac111=aac111+?,aac112=? 
	 *     where aac101=?
	 * >
	 * @param sql
	 * @param stateList
	 * @param idlist
	 * @return
	 * @throws Exception
	 */
	public boolean batchUpdate(String sql,Object stateLists[],Object...args)throws Exception
	{
		PreparedStatement pstm=null;
		try
		{
			pstm=DBUtils.prepareStatement(sql);
			int index=1;
			for(Object state:stateLists)
			{
				pstm.setObject(index++, state);
			}
			for(Object id:args)
			{
				pstm.setObject(index, id);
				pstm.addBatch();
			}
			return this.executeBatchTransaction(pstm);	
		}
		finally
		{
			DBUtils.close(pstm);
		}
			
	}
	
	
	/**
	 * 单一状态批处理
	 * <
	 *    适合如下SQL语句
	 *    update table 
	 *       set col=?
	 *     where id=? 
	 * >
	 * @param sql      ---  sql语句
	 * @param newState ---  单一列的目标状态
	 * @param idlist   ---  主键数组
	 * @return
	 */
	
	//====================单一状态批处理=======================
	public boolean batchUpdate(final String sql,final Object newState,
							   final Object...idLists)throws Exception
	{
		PreparedStatement pstm=null;
		try
		{
			pstm=DBUtils.prepareStatement(sql);
			pstm.setObject(1,newState);
			for(Object param:idLists)
			{
				pstm.setObject(2, param);
				pstm.addBatch();
			}
			return this.executeBatchTransaction(pstm);
		}
		finally
		{
			DBUtils.close(pstm);
		}
	}
	
	//==========================数量批量删除方法====================
	public boolean batchUpdate(final String sql,final Object...idLists)throws Exception
	{
		PreparedStatement pstm=null;
		try
		{
			pstm=DBUtils.prepareStatement(sql);
			for(Object param:idLists)
			{
				pstm.setObject(1,param);
				pstm.addBatch();
			}
			return this.executeBatchTransaction(pstm);
		}
		finally
		{
			DBUtils.close(pstm);
		}
	}
	
	//============================单一表批处理事务执行方法===========================
	public boolean executeBatchTransaction(PreparedStatement pstm)throws Exception
	{
		
		boolean flag=false;
		DBUtils.beginTransaction();
		try
		{
		pstm.executeBatch();
		DBUtils.commit();
		flag=true;
		}
		catch(Exception e)
		{
			DBUtils.rollback();
			e.printStackTrace();
		}
		finally
		{
			DBUtils.endTranscation();
		}
		return flag;
	}
	
	//========================单一表数据更新通用方法========================
		protected int executeUpdate(final String sql,final Object...args)throws Exception
		{
			PreparedStatement pstm=null;
			try
			{
				pstm=DBUtils.prepareStatement(sql);
				int index=1;
				for(Object param:args)
				{
					pstm.setObject(index++, param);
				}
				return pstm.executeUpdate();	
			}
			finally
			{
				DBUtils.close(pstm);
			}
			
		}
		
		//=======================================以下为查询方法============================
		protected Map<String,String>queryForMap(final String sql,final Object...args)throws Exception
		{
			PreparedStatement pstm=null;
			ResultSet rs=null;
			try
			{
				pstm=DBUtils.prepareStatement(sql);
				int index=1;
				for(Object param:args)
				{
					pstm.setObject(index++,param);
				}
				rs=pstm.executeQuery();
				Map<String,String>ins=null;
				if(rs.next())
				{
					ResultSetMetaData rsmd=rs.getMetaData();
					int count=rsmd.getColumnCount();
					int initSize=((int)(count/0.75))+1;
					ins=new HashMap<>(initSize);
					for(int i=1;i<=count;i++)
					{
						ins.put(rsmd.getColumnLabel(i).toLowerCase(), rs.getString(i));	
					}
					
				}
				return ins;
				
			}
			finally
			{
				DBUtils.close(rs);
				DBUtils.close(pstm);
			}
		}
		/*
		 * 
		 * 没有设置参数的多条查询
		 * */
		protected final List<Map<String,String>> queryForList(final String sql)throws Exception
		{
			return this.queryForList(sql,null);
		}
		
		protected List<Map<String,String>> queryForList(final String sql,Object...args)throws Exception
		{
			PreparedStatement pstm=null;
			ResultSet rs=null;
			try
			{
				pstm=DBUtils.prepareStatement(sql.toString());
				int index=1;
				if(args!=null)
				{
					for(Object o:args)
					{
						pstm.setObject(index++, o);
					}
				}
				rs=pstm.executeQuery();
				List<Map<String,String>>list=new ArrayList<>();
				ResultSetMetaData rmd=rs.getMetaData();
				int count=rmd.getColumnCount();
				int initSize=((int)(count/0.75))+1;
				Map<String,String>ins =null;
				while(rs.next())
				{
					ins=new HashMap<>(initSize);
					for(int i=1;i<=count;i++)
					{
						ins.put(rmd.getColumnLabel(i).toLowerCase(), rs.getString(i));
					}
					list.add(ins);
				}
				return list;
			}
			finally
			{
				DBUtils.close(rs);
				DBUtils.close(pstm);
			}
		}
	
}
