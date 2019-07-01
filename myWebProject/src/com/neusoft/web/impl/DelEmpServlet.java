package com.neusoft.web.impl;


public class DelEmpServlet extends EmpControllerSupport 
{
	@Override
	public String execute() throws Exception 
	{
		this.updateData("deleteEmp", "É¾³ý");
		this.savePageForDel();
		return "queryEmp";
	}


}
