package com.wincom.publicmodel.path;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class ReadCfgFile {
	private Properties prop=null;
	private String basePath="";
	public ReadCfgFile(String filename){
		try {		
			 
			InputStreamReader fis=null;
			PropertiesPath path=new PropertiesPath();
			basePath=path.getPropertiesPath()+filename;
			FileInputStream fs=new FileInputStream(basePath);
			fis=new InputStreamReader(fs,"UTF-8");
			prop =  new  Properties();
			prop.load(fis); 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public ReadCfgFile(String dir,String filename){
		try {		
			InputStreamReader fis=null;
			dir=dir+filename;
			FileInputStream fs=new FileInputStream(dir);
			fis=new InputStreamReader(fs,"UTF-8");
			prop =  new  Properties();
			prop.load(fis); 
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public String getProperty(String key){
		try {
			return prop.getProperty(key).trim();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	public void setProperty(String key,String value){
		try {
			OutputStream out = new FileOutputStream(basePath);
			prop.setProperty(key,value);
			prop.store(new OutputStreamWriter(out, "utf-8"), "Update " + key + " name");
		}catch(Exception e){
			
		}
	}
}
