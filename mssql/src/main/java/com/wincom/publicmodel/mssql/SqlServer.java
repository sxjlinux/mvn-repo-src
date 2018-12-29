package com.wincom.publicmodel.mssql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
public class SqlServer {
	private String JDBCDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//驱动程序字符串
	private String JDBCConnection;
	public Connection conn=null;//数据库连接对象
	public boolean IsCon=false;
	public SqlServer(String jdbcDriver){
		this.JDBCDriver=jdbcDriver;
	}
	/*public SqlServer(){
		JDBCDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//驱动程序字符串
		ConnectHead="jdbc:sqlserver://";
		ConnectEnd=":1433;DatabaseName=";
		End=";columnEncryptionSetting=Enabled;";
		//End=";integratedSecurity=true;encrypt=true;trustServerCertificate=true";
	}
	public SqlServer(String type){
		if(type.equalsIgnoreCase("MSSQL")){
			JDBCDriver="com.microsoft.sqlserver.jdbc.SQLServerDriver";//驱动程序字符串
			ConnectHead="jdbc:sqlserver://";
			ConnectEnd=":1433;DatabaseName=";
			End=";columnEncryptionSetting=Enabled;";
			//End=";integratedSecurity=true;encrypt=true;trustServerCertificate=true";
		}else if(type.equalsIgnoreCase("MYSQL")){
			JDBCDriver="com.mysql.jdbc.Driver";//驱动程序字符串
			ConnectHead="jdbc:mysql://";
			ConnectEnd="/";
			End="?characterEncoding=utf8&useSSL=false";
		}else if(type.equalsIgnoreCase("ORACLE")){
			JDBCDriver="oracle.jdbc.OracleDriver";//驱动程序字符串
			ConnectHead="jdbc:oracle:thin:@//";
			ConnectEnd=":1521/";
			End="";
		}else if(type.equalsIgnoreCase("SYBASE")){
			JDBCDriver="com.sybase.jdbc3.jdbc.SybDriver";//驱动程序字符串
			ConnectHead="jdbc:sybase:Tds:";
			ConnectEnd=":5000/";
			End="";
		}else if(type.equalsIgnoreCase("DB2")){
			JDBCDriver="com.ibm.db2.jcc.DB2Driver";//驱动程序字符串
			ConnectHead="jdbc:db2://";
			ConnectEnd=":50000/";
			End="";
		}
	}*/
	public boolean connect(String connectStr,String userName,String userPwd)
	{
		this.JDBCConnection=connectStr;
		try
		{
			Class.forName(JDBCDriver);//加载驱动程序
			conn=DriverManager.getConnection(JDBCConnection,userName,userPwd);//开始连接数据库
			IsCon=true;
			//Log.writeLog("连接数据库","成功");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			//System.err.println("Dbconnection():"+e.getMessage());
			//Log.writeLog("连接数据库失败",e.getMessage());
			conn= null;
			IsCon=false;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			//System.err.println("connection():"+ex.getMessage());
			//Log.writeLog("连接数据库失败",ex.getMessage());
			conn= null;
			IsCon=false;
		}
		return IsCon;
	}
	public boolean isOpen(){
		boolean ret=false;
		try {
			if(this.conn!=null){
				return this.conn.isClosed();
			}else{
				return false;
			}
			
		} catch (SQLException e) {
			//Log.writeLog("判断数据库是否打开异常",e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public void close()
	{
		try
		{
			if(this.conn==null)
			{
				IsCon=false;
				return;
			}
			if(!this.conn.isClosed())
			{
				this.conn.close();//关闭数据库连接
				this.conn=null;
				IsCon=false;
			}
		}
		catch(SQLException e)
		{
			//Log.writeLog("关闭数据库时出现异常",e.getMessage());
			e.printStackTrace();
			this.conn=null;
			IsCon=false;
		}
	}
	public ResultSet executeQuery(String sql,ResultSet rs)
	{
		Statement stmt;
		try
		{
			if(conn!=null)
			{
				stmt=this.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建Statement对象
				rs=stmt.executeQuery(sql);//执行sql语句，并返回一个结果集
				
			}
			else
			{
				IsCon=false;
				return null;
			}
		}
		catch(SQLException e)
		{
			//Log.writeLog("执行查询语句时出现异常",e.getMessage());
			e.printStackTrace();
			IsCon=false;
			return null;
		}
		return rs;
	}
	public PreparedStatement getPreparedStatement(String sql){
		PreparedStatement preState=null;
		try
		{
			if(conn!=null)
			{
				preState = conn.prepareStatement(sql);
			}
			else
			{
				IsCon=false;
				return null;
			}
		}
		catch(SQLException e)
		{
			//Log.writeLog("执行查询语句时出现异常",e.getMessage());
			e.printStackTrace();
			IsCon=false;
			return null;
		}
		return preState;
	}
	public int executeUpdate(String sql)
	{
		int rev=0;
		Statement stmt;
		try
		{
			if(conn!=null)
			{
				stmt=this.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);//创建Statement对象
				rev=stmt.executeUpdate(sql);
			}
			else
			{
				IsCon=false;
				return -1;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			//Log.writeLog("执行更新语句时出现异常",e.getMessage());
			System.out.println(sql);
			IsCon=false;
			return -1;
		}
		return rev;
	}
	
}
