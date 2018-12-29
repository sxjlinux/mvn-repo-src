package com.wincom.publicmodel.log;

import org.junit.Test;
/*
@Before表示在所有方法运行前运行的方法;
@After表示在所有的方法运行之后执行的方法;
@Test表示这是一个测试方法
@BeforeClass表示在这个测试类构造之前执行的方法
@AfterClass表示在这个测试类构造之后执行的方法
*/
public class TestLog {
    @Test
    public void  initLogTest(){
        Log.initLog("wincom",true);
        Log.writeCSV("\\sunxj\\","1,ssss,234,345,hhhhh,");
    }
}
