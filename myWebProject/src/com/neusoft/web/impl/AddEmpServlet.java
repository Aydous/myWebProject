package com.neusoft.web.impl;


public class AddEmpServlet extends EmpControllerSupport
{
	@Override
	public String execute() throws Exception 
	{
        this.updateData("addEmp", "添加", "aab103","成功,该成员的编号是");
		return "addEmp";
	}

}
