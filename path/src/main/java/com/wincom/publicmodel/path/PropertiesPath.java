package com.wincom.publicmodel.path;

public class PropertiesPath {
	private String basePath="";
	public PropertiesPath(){
		basePath =this.getClass().getResource("/").getFile().toString();// ServletActionContext.getRequest();
		basePath=basePath.substring(1,basePath.length());
		int index=basePath.indexOf("WEB-INF/");
		if(index>0){
			basePath=basePath.substring(0, index);
		}
	}
	public String getPropertiesPath(){
		return this.basePath;
	}
}
