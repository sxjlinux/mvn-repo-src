package com.wincom.publicmodel.ftp;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
public class FtpClient {
	private static Log logger=null;
	FTPClient ftpClient = null;
	private int Timeout=30000;
	private String encoding="UTF-8";
	/** ftp远程目录 */
	private String remoteDir;
	/** 本地存储目录 */
	private String localDir="";
	/** 文件路径通配符 默认列出所有*/
	//private String regEx = "*2018072814*";
	/** 指定要下载的文件名 */
	private String downloadFileName="";
	private String host="";
	private int port=21;
	public FtpClient(){
		logger= LogFactory.getLog(this.getClass());  
		ftpClient = new FTPClient();	
	}
	public void setConnectTimeout(int Timeout){
		this.Timeout=Timeout;
	}
	public void setControlEncoding(String encoding){
		this.encoding=encoding;
	}
	public boolean connectFtp(String ftpHost, String ftpPassword,String ftpUserName, int ftpPort){
		this.host=ftpHost;
		this.port=ftpPort;
		boolean res=false;
		try {
			ftpClient.setConnectTimeout(Timeout);  
			ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器 
			ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器 
			if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) { 
				System.out.println("未连接到FTP，用户名或密码错误。host:"+ftpHost+"\tport:"+ftpPort);
				res=false;
				ftpClient.disconnect();  
			}else {
				res=true;
				System.out.println("FTP连接成功。");
			}
		}catch (SocketException e) {
			e.printStackTrace();  
			System.out.println("FTP的IP地址可能错误，请正确配置。host:"+ftpHost+"\tport:"+ftpPort);
			res=false;
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("FTP的端口错误,请正确配置。host:"+ftpHost+"\tport:"+ftpPort);
			res=false;
		}
		return res;
	}
	public List<File> download(String regEx){
		List<File> files = null;
		StringBuffer resultBuffer = new StringBuffer();
		InputStream is = null;
		File downloadFile = null;  
		//logger.info("开始读取绝对路径" + remoteDir + "文件!");  
		//System.out.println("开始读取绝对路径" + this.remoteDir + "文件!");  
		try {
			ftpClient.setControlEncoding(encoding); // 中文支持 
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
			ftpClient.enterLocalPassiveMode();  
			ftpClient.changeWorkingDirectory(remoteDir);  
			FTPFile[] ftpFiles = ftpClient.listFiles(regEx);
			if(ftpFiles.length==0){
				System.out.println("host:"+this.host+"目录"+remoteDir+"\t文件数为0，过滤文件名为："+regEx);
				return null;
			}
			//logger.info("准备下载" + ftpFiles.length + "个文件"); 
			for (FTPFile file : ftpFiles) {
				if(!downloadFileName.equalsIgnoreCase("")&&!file.getName().equals(downloadFileName)){
					System.out.println("host:"+this.host+"目录"+remoteDir+"\t需要下载的文件为："+downloadFileName+"\t远程文件为："+file.getName());
					continue;
				}
				if(files == null){
					files = new ArrayList<File>();  
				}
				is = ftpClient.retrieveFileStream(file.getName());
				if(is==null){
					System.out.println("host:"+this.host+"目录"+remoteDir+"\t接收文件失败："+file.getName());
					continue;
				}
				downloadFile = new File(localDir + file.getName());
				FileOutputStream fos = FileUtils.openOutputStream(downloadFile);    
				IOUtils.copy(is, fos);
				ftpClient.completePendingCommand();
				IOUtils.closeQuietly(is);  
				IOUtils.closeQuietly(fos);
				files.add(downloadFile); 
				/*
				//另外一种方式，供参考 
				OutputStream is = new FileOutputStream(localFile); 
				ftpClient.retrieveFile(ff.getName(), is); 
				is.close(); 
				 */
			}
			//logger.info("文件下载成功,下载文件路径：" + localDir);  
			return files;
			
		}catch (IOException e) {
			System.out.println("host:"+this.host+"目录"+remoteDir+"\t下载文件异常：\n"+e.getMessage());
			e.printStackTrace();  
		}
		return null;
	}
	/**
	 * 下载指定目录的所有文件
	 * @param localDir
	 * @param remoteDir
	 */
	public List<File> download(String remoteDir,String localDir,String regEx){
		this.remoteDir = remoteDir;
		this.localDir = localDir;
		//this.regEx="*";
		return this.download(regEx);
	}
	/**
	 * 下载指定目录的指定文件
	 * @param remoteDir
	 * @param regEx	文件通配符
	 * @param localDir
	 * @return
	 */
	public List<File> download(String remoteDir,String localDir,String fileName,String regEx){
		this.remoteDir = remoteDir;
		this.localDir = localDir;
		this.downloadFileName=fileName;
		return this.download(regEx);
	}
	public void inputStreamToFile(InputStream ins,File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = ins.read(buffer, 0, 1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public List<File> list(String regEx){
		List<File> files = null;
		InputStream is = null;
		File downloadFile = null;  
		try {
			ftpClient.setControlEncoding(encoding); // 中文支持 
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
			ftpClient.enterLocalPassiveMode();  
			//System.out.println(ftpClient.printWorkingDirectory());
			String workDir=ftpClient.printWorkingDirectory();
			int index=workDir.lastIndexOf('/');
			//System.out.println(workDir);
			//System.out.println(index);
			//System.out.println(workDir.length());
			if(index!=0){
				workDir+="/";//workDir.substring(0,index+1);
			}
			boolean isRev=ftpClient.changeWorkingDirectory(workDir+remoteDir);  
			System.out.println(isRev?"进入"+workDir+remoteDir+"成功":"进入"+workDir+remoteDir+"失败");
			FTPFile[] ftpFiles = ftpClient.listFiles(regEx);
			if(ftpFiles.length==0){
				System.out.println("host:"+this.host+"目录"+remoteDir+"\t文件数为0，过滤文件名为："+regEx);
				return null;
			}
			System.out.println("开始下载文件");
			for (FTPFile file : ftpFiles) {
				System.out.println(file.getName());
				if(!downloadFileName.equalsIgnoreCase("")&&!file.getName().equals(downloadFileName)){
					System.out.println("host:"+this.host+"目录"+remoteDir+"\t需要下载的文件为："+downloadFileName+"\t远程文件为："+file.getName());
					continue;
				}
				if(files == null){
					files = new ArrayList<File>();  
				}
				is = ftpClient.retrieveFileStream(file.getName());
				if(is==null){
					System.out.println("host:"+this.host+"目录"+remoteDir+"\t接收文件失败："+file.getName());
					continue;
				}
				//System.out.println(file.getName());
				downloadFile = new File(localDir+file.getName());
				inputStreamToFile(is,downloadFile);
				files.add(downloadFile);
				ftpClient.completePendingCommand();
			}
			System.out.println("下载文件完成");
			return files;
			
		}catch (IOException e) {
			System.out.println("host:"+this.host+"目录"+remoteDir+"\t下载文件异常：\n"+e.getMessage());
			e.printStackTrace();  
		}
		return null;
	}
	/**
	 * 列出指定目录的远程文件
	 * @param remoteDir
	 * @param localDir
	 * @return
	 */
	public List<File> listDir(String remoteDir,String localDir,String regEx){
		this.remoteDir = remoteDir;
		this.localDir=localDir;
		return this.list(regEx);
	}
	/**
	 * 列出指定目录的远程文件
	 * @param remoteDir
	 * @param localDir
	 * @return
	 */
	public List<File> list(String remoteDir,String regEx){
		this.remoteDir = remoteDir;
		this.localDir="";
		return this.list(regEx);
	}
	/**
	 * 列出指定目录的指定文件
	 * @param remoteDir
	 * @param regEx	文件通配符
	 * @param localDir
	 * @return
	 */
	public List<File> listFile(String remoteDir,String fileName,String regEx){
		this.remoteDir = remoteDir;
		this.downloadFileName=fileName;
		this.localDir="";
		return this.list(regEx);
	}
	 /*** 
	  * @上传文件夹 
	  * @param localDirectory 
	  *   当地文件夹 
	  * @param remoteDirectoryPath 
	  *   Ftp 服务器路径 以目录"/"结束 
	  * */ 
	 public boolean uploadDirectory(String remoteDirectoryPath,String localDirectory) {
		 File src = new File(localDirectory);
		 try {
			 remoteDirectoryPath = remoteDirectoryPath + src.getName() + "/";
			 boolean makeDirFlag = this.ftpClient.makeDirectory(remoteDirectoryPath);
			 //System.out.println("localDirectory : " + localDirectory);
			 //System.out.println("remoteDirectoryPath : " + remoteDirectoryPath);
			 //System.out.println("src.getName() : " + src.getName());
			 //System.out.println("remoteDirectoryPath : " + remoteDirectoryPath);
			 //System.out.println("makeDirFlag : " + makeDirFlag);
		 }catch (IOException e) {
			 e.printStackTrace();
			 logger.info(remoteDirectoryPath + "目录创建失败");
		 }
		 File[] allFile = src.listFiles();
		 for (int currentFile = 0;currentFile < allFile.length;currentFile++) {
			 if (!allFile[currentFile].isDirectory()) {
				 String srcName = allFile[currentFile].getPath().toString();
				 upload(new File(srcName), remoteDirectoryPath);
			 }
		 }
		 for (int currentFile = 0;currentFile < allFile.length;currentFile++) {
			 if (allFile[currentFile].isDirectory()) {
				 // 递归 
				 uploadDirectory(allFile[currentFile].getPath().toString(),remoteDirectoryPath);
			 }
		 }
		 return true;
	 }
	//boolean uploadFlag = ftp.uploadDirectory("D:\\test02", "/"); //如果是admin/那么传的就是所有文件，如果只是/那么就是传文件夹
	/**
	 * 下载指定目录的指定文件
	 * @param remoteDirectory 远程目录
	 * @param localDirectoryPath 下载到本地目录
	 * @return
	 */
	public boolean downLoadDirectory(String remoteDirectory,String localDirectoryPath,String regEx) {
		try {
			ftpClient.setControlEncoding(encoding); // 中文支持 
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);  
			ftpClient.enterLocalPassiveMode();  
			boolean isRev=ftpClient.changeWorkingDirectory(remoteDirectory);
			int index=regEx.indexOf("*");
			if(index<0) {
				localDirectoryPath=localDirectoryPath+regEx+"/";
				new File(localDirectoryPath).mkdirs();
				remoteDirectory=remoteDirectory+regEx+"/";
			}
			System.out.println("开始下载"+remoteDirectory+"目录文件");
			FTPFile[] allFile = ftpClient.listFiles(regEx);
			for (int currentFile = 0;currentFile < allFile.length;currentFile++) {
				//System.out.println(allFile[currentFile].getName());
				if (!allFile[currentFile].isDirectory()) {
					//System.out.println(allFile[currentFile].getName());
					download(remoteDirectory,localDirectoryPath,allFile[currentFile].getName(),"*");
				}
			}
			for (int currentFile = 0;currentFile < allFile.length;currentFile++) {
				if (allFile[currentFile].isDirectory()) {
					String strremoteDirectoryPath = remoteDirectory + allFile[currentFile].getName();
					String strlocalDirectoryPath = localDirectoryPath + allFile[currentFile].getName()+"/";
					new File(strlocalDirectoryPath).mkdirs();
					downLoadDirectory(strremoteDirectoryPath,strlocalDirectoryPath,"*");
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
			System.out.println("host:"+this.host+"目录"+remoteDir+"\t下载文件异常：\n"+e.getMessage());
			return false;
		}
		System.out.println("下载"+remoteDirectory+"目录文件完成");
		return true;
	}
	/**
	 * 上传文件
	 * @param files
	 */
	public boolean upload(List<File> files){
		boolean isUpState=true;
		OutputStream os = null;
		try {
			// 2、取本地文件
			if(files == null || files.size()==0) {
				logger.warn("文件数为0，没有找到可上传的文件");
				return false;
			}
			logger.info("准备上传" + files.size() + "个文件");
			// 3、上传到FTP服务器
			for(File file : files){
				// 1、设置远程FTP目录
				ftpClient.changeWorkingDirectory(remoteDir);
				logger.info("切换至工作目录【" + remoteDir + "】");
				os = ftpClient.storeFileStream(file.getName());
				if(os== null){
					return false;
				};
				IOUtils.copy(new FileInputStream(file), os);
				IOUtils.closeQuietly(os);
			}
			logger.info("文件上传成功,上传文件路径：" + remoteDir);
		} catch (IOException e) {
			logger.error("上传文件失败" + e.getMessage());
			isUpState=false;
			//throw new RuntimeException("上传文件失败" + e.getMessage());
		}
		return isUpState;
	}
	
	public OutputStream getOutputStream(String fileName){
		OutputStream os = null;
		// 1、设置远程FTP目录
		try {
			ftpClient.changeWorkingDirectory(remoteDir);
			logger.info("切换至工作目录【" + remoteDir + "】");
			os = ftpClient.storeFileStream(fileName);
			if(os== null) throw new RuntimeException("服务器上创建文件对象失败");
			return os;
		} catch (IOException e) {
			logger.error("服务器上创建文件对象失败" + e.getMessage());
			throw new RuntimeException("服务器上创建文件对象失败" + e.getMessage());
		}
	}
	/**
	 * 上传文件
	 * @param files	上传的文件
	 * @param remoteDir
	 */
	public boolean upload(List<File> files,String remoteDir){
		this.remoteDir = remoteDir;
		return this.upload(files);
	}
	
	/**
	 * 上传文件
	 * @param file
	 */
	public boolean upload(File file){
		List<File> files = new ArrayList<File>();
		files.add(file);
		return upload(files);
	}
	/**
	 * 上传文件
	 * @param files	上传的文件
	 * @param remoteDir
	 */
	public boolean upload(File file,String remoteDir){
		this.remoteDir = remoteDir;
		List<File> files = new ArrayList<File>();
		files.add(file);
		return this.upload(files);
	}
	/**
     * 判断文件在FTP上是否存在
     * @param fileName
     * @return
     */
    public boolean isFileExist(String fileName) {
    	boolean result = false;
		try {
			// 1、设置远程FTP目录
			ftpClient.changeWorkingDirectory(remoteDir);
			logger.info("切换至工作目录【" + remoteDir + "】");
			// 2、读取远程文件
			FTPFile[] ftpFiles = ftpClient.listFiles("*");
			if(ftpFiles.length==0) {
				logger.warn("文件数为0，没有可下载的文件！");
				return result;
			}
			// 3、检查文件是否存在
			for (FTPFile file : ftpFiles) {
				if(file.getName().equals(fileName)){
					result = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error("检查文件是否存在失败" + e.getMessage());
			throw new RuntimeException("检查文件是否存在失败" + e.getMessage());
		}
    	
    	return result;
    }

	 /**
     * 关闭连接
     */
    public void closeConnect() {
        try {
        	ftpClient.disconnect();
        	System.out.println(" 关闭FTP连接!!! ");
			logger.info(" 关闭FTP连接!!! ");
		} catch (IOException e) {
			logger.warn(" 关闭FTP连接失败!!! ",e);
		}
    }
	public String getRemoteDir() {
		return remoteDir;
	}

	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}

	public String getLocalPath() {
		return localDir;
	}

	public void setLocalPath(String localPath) {
		this.localDir = localPath;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
}
