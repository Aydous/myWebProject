package com.neusoft.web.impl;


public class AddEmpServlet extends EmpControllerSupport
{
	@Override
	public String execute() throws Exception 
	{
        this.updateData("addEmp", "���", "aab103","�ɹ�,�ó�Ա�ı����");
		return "addEmp";
	}

}
