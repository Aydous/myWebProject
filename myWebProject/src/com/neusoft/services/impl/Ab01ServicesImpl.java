package com.neusoft.services.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.neusofft.services.jdbcServicesSupport;
import com.neusoft.system.db.DBUtils;
import com.neusoft.system.tools.Tools;

public class Ab01ServicesImpl extends jdbcServicesSupport 
{
	
	
	public Map<String,String> findById() throws Exception
	{
    	StringBuilder sql=new StringBuilder()
    			.append("select a.aab102,a.aab103,a.aab104,a.aab105,a.aab106,")
    			.append("       a.aab107,a.aab108,a.aab109,a.aab110,a.aab111,")
    			.append("       a.aab112,a.aab113")
    			.append("  from ab01 a")
    			.append(" where a.aab101=?");
    	return this.queryForMap(sql.toString(),this.get("aab101"));
	}
	
	public List<Map<String,String>> query()throws Exception
	{
		PreparedStatement pstm=null;
		ResultSet rs=null;
		List<Object>pstmList=new ArrayList<>();
		StringBuilder sql=new StringBuilder()
  				.append("select x.aab101,x.aab102,x.aab103,x.aab104,a.fvalue cnaab105,")
  				.append("       b.fvalue cnaab106,x.aab108,x.aab109")
  				.append("  from syscode a,syscode b, ab01 x")
  				.append(" where x.aab105=a.fcode and a.fname='aab105'")
  				.append("   and x.aab106=b.fcode and b.fname='aab106'") 
  				;
			Object aab102=this.get("qaab102");
			Object aab103=this.get("qaab103");
			Object aab105=this.get("qaab105");
			Object aab106=this.get("qaab106");
			Object baab104=this.get("baab104");
			Object eaab104=this.get("eaab104");
			if(this.isNotNull(aab102))
			{
				sql.append(" and x.aab102 like ?");
				pstmList.add("%"+aab102+"%");
			}
			if(this.isNotNull(aab103))
			{
				sql.append(" and x.aab103 = ?");
				pstmList.add(aab103);
			}
			if(this.isNotNull(aab105))
			{
				sql.append(" and x.aab105 = ?");
				pstmList.add(aab105);
			}
			if(this.isNotNull(aab106))
			{
				sql.append(" and x.aab106 = ?");
				pstmList.add(aab106);
			}
			if(this.isNotNull(baab104))
			{
				sql.append(" and x.aab104 >= ?");
				pstmList.add(baab104);
			}
			if(this.isNotNull(eaab104))
			{
				sql.append(" and x.aab104 <= ?");
				pstmList.add(eaab104);
			}
			sql.append(" order by x.aab102");
			List<Map<String,String>>list=this.queryForList(sql.toString(), pstmList.toArray());
			return list;		
	}
	

	
	
	private boolean addEmp() throws Exception
	{
		StringBuilder sql=new StringBuilder()
    			.append("insert into ab01(aab102,aab103,aab104,aab105,aab106,")
    			.append("                 aab107,aab108,aab109,aab110,aab111,")
    			.append("                 aab112,aab113)")
    			.append("          values(?,?,?,?,?,")
    			.append("                 ?,?,?,?,?,")
    			.append("                 ?,?)")
    			;
		String aab103=Tools.getEmpNumber();
		this.put("aab103", aab103);
		Object args[]={this.get("aab102"),
					   aab103,
				       this.get("aab104"),
				       this.get("aab105"),
				       this.get("aab106"),
				       this.get("aab107"),
				       this.get("aab108"),
				       this.get("aab109"),
				       this.get("aab110"),
				       Tools.joinArray(this.get("aab111")),
				       Tools.joinArray(this.get("aab112")),
				       this.get("aab113")
						};
		return this.executeUpdate(sql.toString(), args)>0;
	}
	
	private boolean deleteEmp()throws Exception//复选框批量删除
	{
		String sql="delete from ab01 where aab101=?";
		String args[]=null;
		args=this.getIdList("idlist");
		return this.batchUpdate(sql, args);
	}
	
	private boolean del()throws Exception //删一个
	{
		String sql="delete from ab01 where aab101=?";
		String args[]=null;
		args=this.getIdList("aab101");
		return this.batchUpdate(sql, args);
	}
	private boolean UpdateEmp()throws Exception //addEmp页面修改数据
	{
		StringBuilder sql=new StringBuilder()
				.append(" update  ab01") 
				.append(" set aab102=?,aab104=?,aab105=?,aab106=?,")
				.append(" aab107=?,aab108=?,aab109=?,aab110=?,aab111=?,")
				.append(" aab112=?,aab113=?")
				.append(" where aab101=?")
				;
		
		Object args[]={
				this.get("aab102"),
				this.get("aab104"),
				this.get("aab105"),
				this.get("aab106"),
				this.get("aab107"),
				this.get("aab108"),
				this.get("aab109"),
				this.get("aab110"),
				Tools.joinArray(this.get("aab111")),
				Tools.joinArray(this.get("aab112")),
				this.get("aab113")
		};
		return this.batchUpdate(sql.toString(), args, this.getIdList("aab101"));
		
	}
}
