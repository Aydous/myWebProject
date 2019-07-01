package com.neusoft.web.support;

import java.util.Map;

public interface BaseController 
{
	String execute()throws Exception;
	void setDto(Map<String,Object>dto);
	Map<String,Object> getAttribute();
	
}
