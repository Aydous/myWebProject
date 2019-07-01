package com.neusoft.web.impl;

public class FindEmpByIdServlet extends EmpControllerSupport 
{
	@Override
	public String execute() throws Exception 
	{
		this.saveDataForInstance();
		return "addEmp";
	}

}
