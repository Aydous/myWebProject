package com.neusoft.web.impl;


public class DelEmpServlet extends EmpControllerSupport 
{
	@Override
	public String execute() throws Exception 
	{
		this.updateData("deleteEmp", "ɾ��");
		this.savePageForDel();
		return "queryEmp";
	}


}
