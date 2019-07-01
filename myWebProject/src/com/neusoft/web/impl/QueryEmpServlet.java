package com.neusoft.web.impl;

public class QueryEmpServlet extends EmpControllerSupport
{
	@Override
	public String execute() throws Exception
	{
		this.saveDataPage();
		return "queryEmp";
	}


}
