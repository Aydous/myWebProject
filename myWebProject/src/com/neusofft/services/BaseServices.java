package com.neusofft.services;

import java.util.List;
import java.util.Map;

public interface BaseServices 
{
	void setDto(Map<String,Object>dto);
	List<Map<String,String>> query()throws Exception;
	
	Map<String,String>findById()throws Exception;
	
	
	
	
}
