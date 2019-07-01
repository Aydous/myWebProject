package com.neusofft.services;

import java.sql.*;

import com.neusoft.system.db.DBUtils;

class PstmMetaData
{
	private PreparedStatement pstm=null;
	private boolean isBatch=false;
	public PstmMetaData(final PreparedStatement pstm,final boolean isBatch)
	{
		this.pstm=pstm;
		this.isBatch=isBatch;
	}
	
	public void executePreparedStatement()throws Exception
	{
		if(this.isBatch=true)
		{
			this.pstm.executeBatch();
		}
		else
		{
			this.pstm.executeUpdate();
		}
	}
	public void close()
	{
		
			DBUtils.close(this.pstm);
		
		
	}
}
