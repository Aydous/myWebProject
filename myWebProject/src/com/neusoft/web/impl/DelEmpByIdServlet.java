package com.neusoft.web.impl;

public class DelEmpByIdServlet extends EmpControllerSupport
{
	@Override
	public String execute() throws Exception 
	{
		this.updateData("del", "ɾ��");
		this.savePageForDel();
		return "queryEmp";
	}

}
