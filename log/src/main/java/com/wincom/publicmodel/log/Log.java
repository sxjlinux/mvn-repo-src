package com.wincom.publicmodel.log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.wincom.publicmodel.path.PropertiesPath;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
public class Log {
	public static String basePath="";
	public static String LogName="";
	public static boolean isWriteLog=false;
	public static void initLog(String name,boolean isWrite){
		isWriteLog=isWrite;
		LogName=name;
		PropertiesPath path=new PropertiesPath();
		basePath =path.getPropertiesPath()+"log/";
		existsDir(basePath);
	}
	public static boolean existsDir(String path){
		File file=new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		return true;
	}
	public static void writeLog(String title,String info){
		if(!isWriteLog){
			return;
		}
		LocalDateTime tim = LocalDateTime.now(); 
		String fileName=String.format("%s%04d%02d%02d.log",LogName, tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth());
		File file = new File(basePath+fileName);
		FileWriter writer=null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			String text=String.format("%04d-%02d-%02d %02d:%02d:%02d:%s%s\r\n",tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth(),tim.getHour(),tim.getMinute(),tim.getSecond(),title,info);
				
			writer = new FileWriter(file, true);     
			writer.write(text);
			writer.close();
		} catch (IOException e) {  
			e.printStackTrace();  
		}
	}
	public static void writeLog(String dir,String title,String info){
		if(!isWriteLog){
			return;
		}
		LocalDateTime tim = LocalDateTime.now(); 
		dir=dir.replace("/", "\\");
		String pathDir=String.format("%sLog%04d%02d%02d\\", dir,tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth());
		new File(pathDir).mkdirs();
		String fileName=String.format("%s%04d%02d%02d.log",LogName, tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth());
		File file = new File(pathDir+fileName);
		FileWriter writer=null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			String text=String.format("%04d-%02d-%02d %02d:%02d:%02d:%s%s\r\n",tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth(),tim.getHour(),tim.getMinute(),tim.getSecond(),title,info);
				
			writer = new FileWriter(file, true);     
			writer.write(text);       
			writer.close();
		} catch (IOException e) {  
			e.printStackTrace();  
		}
	}
	public static void writeCSV(String dir,String info){
		if(!isWriteLog){
			return;
		}
		LocalDateTime tim = LocalDateTime.now();
		String osName=System.getProperty("os.name");
		if(osName.equalsIgnoreCase("Windows")){
			dir=dir.replace("/", "\\");
		}else{
			dir=dir.replace("\\", "/");
		}
		new File(dir).mkdirs();
		String fileName=String.format("%s%04d%02d%02d.csv",LogName, tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth());
		System.out.println(dir+fileName);
		File file = new File(dir+fileName);
		FileWriter writer=null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			String text=String.format("%s\r\n",info);
			writer = new FileWriter(file, true);     
			writer.write(text);       
			writer.close();
		} catch (IOException e) {  
			e.printStackTrace();  
		}
	}
	public static void writePack(String title,byte b[]){
		if(!isWriteLog){
			return;
		}
		String str="";
		for(int i=0;i<b.length;i++){
			String hex = Integer.toHexString(b[i]&0xFF);   
			if (hex.length() == 1) {   
			    hex = '0' + hex;   
			}   
			str+=hex.toUpperCase()+" ";
		}
		writeLog(title,str);
	}
	private static String format(String format) {
        try {
            final Document document = parseXmlFile(format);
            OutputFormat outFormat = new OutputFormat(document);
            outFormat.setLineWidth(100);
            outFormat.setIndenting(true);
            outFormat.setIndent(4);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, outFormat);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	private static Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	public static void writeXml(String dir,String filename,String xml){
		if(!isWriteLog){
			return;
		}
		LocalDateTime tim = LocalDateTime.now();
		dir=dir.replace("/", "\\");
		String pathDir=String.format("%sLog%04d%02d%02d\\", dir,tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth());
		new File(pathDir).mkdirs();
		String fileName=String.format("%s______%04d_%02d_%02d_%02d_%02d_%02d.xml",filename,tim.getYear(),tim.getMonth().getValue(),tim.getDayOfMonth(),tim.getHour(),tim.getMinute(),tim.getSecond());
		File file = new File(pathDir+fileName);
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file,true),"UTF-8"); 
			out.write(format(xml));
			out.flush();
			out.close();
		} catch (IOException e) {  
			e.printStackTrace();  
		}
	}
}
