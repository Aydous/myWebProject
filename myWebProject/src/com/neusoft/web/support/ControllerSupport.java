package com.neusoft.web.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.neusofft.services.BaseServices;


public abstract class ControllerSupport implements BaseController 
{
	protected BaseServices services=null;
			
	private Map<String,Object>dto=null;
	private Map<String,Object>attribute=new HashMap<>();
	@Override
	public void setDto(Map<String,Object>dto)
	{
		this.dto=dto;
		services.setDto(dto);
	}
	protected final  Map<String,Object>getDto()
	{
		return this.dto;
	}
	public final void saveAttribute(String key,Object value)
	{
		this.attribute.put(key, value);
	}
	@Override
	public final Map<String,Object> getAttribute()
	{
		return this.attribute;
	}
	public final void setServices(BaseServices services)
	{
		this.services=services;
	}
	public final BaseServices getServices(BaseServices services)
	{
		return this.services;
	}
	protected void saveDataForInstance()throws Exception
	{
		Map<String,String> ins=this.services.findById();
		if(ins!=null)
		{
			this.saveAttribute("ins", ins);
		}
		else
		{
			this.saveAttribute("msg", "��ʾ:��������ɾ�����ֹ����!");	
		}
	}
	protected void saveDataPage()throws Exception
	{
		List<Map<String,String>>rows=new ArrayList<>();
		rows=this.services.query();
		if(rows.size()>0)
		{
			this.saveAttribute("rows", rows);
		}
		else
		{
			this.saveAttribute("msg", "��Ҫ������ݲ����ڻ��ֹ����");
		}
	}
	public boolean methodUpdate(String methodName)throws Exception
	{
		Class c=this.services.getClass();
		Method method=c.getDeclaredMethod(methodName);
		method.setAccessible(true);
		return (boolean)method.invoke(this.services);
	}
	
	protected void updateData(String utype,String typeText,String key,String textmsg)throws Exception
	{
		String msg=typeText+"ʧ��";
		if(this.methodUpdate(utype))
		{
			msg=typeText+textmsg+"[<msg>"+this.getDto().get(key)+"</msg>]";
		}
		this.saveAttribute("msg", msg);
	}
	protected void updateData(String utype,String type)throws Exception
	{
		String msg=this.methodUpdate(utype)?"�ɹ�":"ʧ��";
		this.saveAttribute("msg", type+msg);
	}
	protected void savePageForDel()throws Exception
	{
		List<Map<String,String>>rows=new ArrayList<>();
		rows=this.services.query();
		if(rows.size()>0)
		{
			this.saveAttribute("rows", rows);
		}
	}
}
