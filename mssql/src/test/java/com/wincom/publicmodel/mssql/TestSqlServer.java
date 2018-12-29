package com.wincom.publicmodel.mssql;

import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TestSqlServer {
    @Test
    public void test(){
        SqlServer sql=new SqlServer("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        if(sql.connect("jdbc:sqlserver://192.168.0.33:1433;DatabaseName=master","sa","")){
            System.out.println("数据库连接成功");
            String sqlstr = String.format("select getdate() as dt from spt_monitor");
            ResultSet rs = null;
            try {
                Timestamp times = null;
                PreparedStatement preState =sql.getPreparedStatement(sqlstr);
                if (preState == null) {
                    sql.close();
                    return;
                }
                rs = preState.executeQuery();
                while (rs.next()) {
                    System.out.println(rs.getTimestamp("dt"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sql.close();
        }else{
            System.out.println("数据库连接失败");
        }
    }
}
