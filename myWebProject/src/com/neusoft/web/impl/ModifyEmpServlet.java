package com.neusoft.web.impl;




public class ModifyEmpServlet extends EmpControllerSupport 
{
	@Override
	public String execute() throws Exception 
	{
		this.updateData("UpdateEmp", "ÐÞ¸Ä");
		this.saveDataPage();
		return "queryEmp";
	}

}
