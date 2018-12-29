package com.wincom.publicmodel.utility;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class Utility {
	public static byte[] float2byte(float f) {
		// 把float转换为byte[]
		int fbit = Float.floatToIntBits(f);
		
		byte[] b = new byte[4];  
	    for (int i = 0; i < 4; i++) {  
	        b[i] = (byte) (fbit >> (24 - i * 8));  
	    } 
	    
	    // 翻转数组
		int len = b.length;
		// 建立一个与源数组元素类型相同的数组
		byte[] dest = new byte[len];
		// 为了防止修改源数组，将源数组拷贝一份副本
		System.arraycopy(b, 0, dest, 0, len);
		byte temp;
		// 将顺位第i个与倒数第i个交换
		for (int i = 0; i < len / 2; ++i) {
			temp = dest[i];
			dest[i] = dest[len - i - 1];
			dest[len - i - 1] = temp;
		}
	    
	    return dest;  
	}
	public static int floatTobyte(byte b[],int index,float f) {
		// 把float转换为byte[]
		int fbit = Float.floatToIntBits(f);
		byte[] b1 = new byte[4];  
	    for (int i = 0; i < 4; i++) {  
	        b1[i] = (byte) (fbit >> (24 - i * 8));  
	    } 
	    //翻转数组
		int len = b1.length;
		//建立一个与源数组元素类型相同的数组
		byte[] dest = new byte[len];
		//为了防止修改源数组，将源数组拷贝一份副本
		System.arraycopy(b1, 0, dest, 0, len);
		byte temp;
		// 将顺位第i个与倒数第i个交换
		for (int i = 0; i < len / 2; ++i) {
			temp = dest[i];
			dest[i] = dest[len - i - 1];
			dest[len - i - 1] = temp;
		}
		b[index]=dest[0];
		b[index+1]=dest[1];
		b[index+2]=dest[2];
		b[index+3]=dest[3];
		return 4;
	}
	
	/**
	 * 字节转换为浮点
	 * 
	 * @param b 字节（至少4个字节）
	 * @param index 开始位置
	 * @return
	 */
	public static float byteTofloat(byte[] b, int index) {  
	    int l;                                           
	    l = b[index + 0];                                
	    l &= 0xff;                                       
	    l |= ((long) b[index + 1] << 8);                 
	    l &= 0xffff;                                     
	    l |= ((long) b[index + 2] << 16);                
	    l &= 0xffffff;                                   
	    l |= ((long) b[index + 3] << 24);                
	    return Float.intBitsToFloat(l);                  
	}
	
	/**
	 * 字节转换为int
	 * 
	 * @param b 字节（至少4个字节）
	 * @param index 开始位置
	 * @return
	 */
	public static int byteToint(byte[] b, int index) {  
	    int l;                                           
	    l = b[index + 0];                                
	    l &= 0xff;                                       
	    l |= ((long) b[index + 1] << 8);                 
	    l &= 0xffff;                                     
	    l |= ((long) b[index + 2] << 16);                
	    l &= 0xffffff;                                   
	    l |= ((long) b[index + 3] << 24);                
	    return l;                 
	}
	/**
	 * int转换为字节
	 * 
	 * @param b 字节（至少4个字节）
	 * @param index 开始位置
	 * @return
	 */
	public static int intTobyte(byte b[],int index,int v) { 
		b[index]=(byte)(v&0xFF);
		b[index+1]=(byte)((v>>8)&0xFF);
		b[index+2]=(byte)((v>>16)&0xFF);
		b[index+3]=(byte)((v>>24)&0xFF);  
		return 4;
	}
	
	/**
	 * 字节转换为long
	 * 
	 * @param b 字节（至少4个字节）
	 * @param index 开始位置
	 * @return
	 */
	public static long byteTolong(byte[] b, int index) {  
	    long l;                                           
	    l = b[index + 0];                                
	    l &= 0xff;                                       
	    l |= ((long) b[index + 1] << 8);                 
	    l &= 0xffff;                                     
	    l |= ((long) b[index + 2] << 16);                
	    l &= 0xffffff;                                   
	    l |= ((long) b[index + 3] << 24);                
	    return l;                 
	}
	/**
	 * long转换为字节
	 * 
	 * @param b 字节（至少4个字节）
	 * @param index 开始位置
	 * @return
	 */
	public static int longTobyte(byte b[],int index,long v) { 
		b[index]=(byte)(v&0xFF);
		b[index+1]=(byte)((v>>8)&0xFF);
		b[index+2]=(byte)((v>>16)&0xFF);
		b[index+3]=(byte)((v>>24)&0xFF);  
		return 4;
	}
	/**
	 * 字节转换为short
	 * 
	 * @param b 字节（至少2个字节）
	 * @param index 开始位置
	 * @return
	 */
	public static short byteToshort(byte[] b, int index) {  
		short l=0;                                           
	    l = b[index + 0];                                
	    l &= 0xff;                                       
	    l |= ((short) b[index + 1] << 8);    
	    return l;                 
	}
	/**
	 * short转换为字节
	 * 
	 * @param b 字节（至少2个字节）
	 * @param index 开始位置
	 * @return
	 */
	public static int shortTobyte(byte b[],int index,short v) {  
		b[index]=(byte)(v&0xFF);
		b[index+1]=(byte)((v>>8)&0xFF);  
		return 2;
	}
	public static String byteToStr(byte[] b,int len, int index){
		String str="";
		if(len>0){
			byte bstr[]=new byte[len];
			for(int i=0;i<len;i++){
				bstr[i]=b[index+i];
			}
			try {
				str=new String(bstr,"GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			str=getUTF8StringFromGBKString(str);
		}
		return str.trim();
	}
	public static String byteToStrPA(byte[] b,int len, int index){
		String str="";
		if(len>0){
			byte bstr[]=new byte[len+1];
			for(int i=0;i<len;i++){
				bstr[i]=b[index+i];
			}
			try {
				str=new String(bstr,"GBK");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			str=getUTF8StringFromGBKString(str);
		}
		return str.trim();
	}
	public static String getUTF8StringFromGBKString(String gbkStr) {  
		try {  
				return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");  
			} catch (UnsupportedEncodingException e) {  
				throw new InternalError();  
		}  
	}
	public static byte[] getUTF8BytesFromGBKString(String gbkStr) {  
		int n = gbkStr.length();  
		byte[] utfBytes = new byte[3 * n];  
		int k = 0;  
		for (int i = 0; i < n; i++) {  
			int m = gbkStr.charAt(i);  
			if (m < 128 && m >= 0) {  
				utfBytes[k++] = (byte) m;  
				continue;  
			}  
			utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
			utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
			utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
		}  
		if (k < utfBytes.length) {  
			byte[] tmp = new byte[k];  
			System.arraycopy(utfBytes, 0, tmp, 0, k);  
			return tmp;  
		}  
		//printHex(utfBytes);
		return utfBytes;  
	} 

	public static int strTobyte(byte[] b, int index,String str){
		int len=0;
		if(str.length()>0){
			try {
				byte bstr[] = str.getBytes("GBK");
				for(int i=0;i<bstr.length;i++){
					b[index+i]=bstr[i];
				}
				len=bstr.length;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return len;
	}
	public static int strTobytePA(byte[] b, int index,String str){
		int len=0;
		if(str.length()>0){
			try {
				byte bstr[] = str.getBytes("GBK");
				
				for(int i=0;i<bstr.length;i++){
					b[index+i]=bstr[i];
				}
				len=bstr.length;
				b[index+(len++)]=0x00;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return len;
	}
	public static int strTobyte(byte[] b, int index,String str,int len){
		if(str.length()>0){
			try {
				byte bstr[] = str.getBytes("GBK");
				if(bstr.length<len){
					for(int i=0;i<bstr.length;i++){
						b[index+i]=bstr[i];
					}
					for(int c=bstr.length;c<len;c++){
						b[index+c]='\0';
					}
				}else{
					for(int i=0;i<len;i++){
						b[index+i]=bstr[i];
					}
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return len;
	}
	public static int getStringGBKLen(String str){
		int len=0;
		if(str.length()>0){
			try {
				len=str.getBytes("GBK").length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return len;
	}
	public static long getLocalTime(){
		GregorianCalendar gc = new GregorianCalendar();
		return (long)(gc.getTimeInMillis()/1000);
	}
	public static String getLocalFormatTime(){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return localtDateAndTime.format(formatter);
	}
	public static String getLocalFormatTime(String format){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern(format);
		return localtDateAndTime.format(formatter);
	}
	public static int getLocalTimeYear(){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		return localtDateAndTime.getYear();
	}
	public static int getLocalTimeMonth(){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		return localtDateAndTime.getMonthValue();
	}
	public static int getLocalTimeDay(){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		return localtDateAndTime.getDayOfMonth();
	}
	public static int getLocalTimeHour(){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		return localtDateAndTime.getHour();
	}
	public static int getLocalTimeMinute(){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		return localtDateAndTime.getMinute();
	}
	public static int getLocalTimeSecond(){
		LocalDateTime localtDateAndTime = LocalDateTime.now();
		return localtDateAndTime.getSecond();
	}
	public static String getFormatTime(long time){
		GregorianCalendar gc = new GregorianCalendar(); 
        gc.setTimeInMillis(time * 1000L);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(gc.getTime());
	}
	public static int getTime(String time){
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			return (int)(date.getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static TimeObj getTimeObj(String time){
		TimeObj obj=new TimeObj();
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			obj.setDay(cal.get(Calendar.DAY_OF_MONTH));
			obj.setHour(cal.get(Calendar.HOUR_OF_DAY));
			obj.setMinute(cal.get(Calendar.MINUTE));
			obj.setMonth(cal.get(Calendar.MONTH)+1);
			obj.setSecond(cal.get(Calendar.SECOND));
			obj.setYear(cal.get(Calendar.YEAR));
			return obj;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static TimeObj getTimeObj(long time){
		TimeObj obj=new TimeObj();
		try {
			GregorianCalendar gc = new GregorianCalendar(); 
	        gc.setTimeInMillis(time * 1000L);
			Date date = gc.getTime();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			obj.setDay(cal.get(Calendar.DAY_OF_MONTH));
			obj.setHour(cal.get(Calendar.HOUR_OF_DAY));
			obj.setMinute(cal.get(Calendar.MINUTE));
			obj.setMonth(cal.get(Calendar.MONTH)+1);
			obj.setSecond(cal.get(Calendar.SECOND));
			obj.setYear(cal.get(Calendar.YEAR));
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getFormatTime(Timestamp times){
		String useDate="";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(times!=null)
		{
			useDate = formatter.format(times.getTime());
		}
		return useDate;
	}
	public static void printHex(byte b[]){
		for(int i=0;i<b.length;i++){
			String hex = Integer.toHexString(b[i]&0xFF);   
			if (hex.length() == 1) {   
			    hex = '0' + hex;   
			}   
			System.out.print(hex.toUpperCase()+" "); 
			if(i%300==0&&i!=0){
				System.out.println();  
			}
		}
		System.out.println();  
	}
	public static void CRC_16(byte b[],int startIndex,int endIndex,byte crc[]){
		/*try {  
			int crc1;  
			int strlength, r;  
			byte sbit;  
			int tc;  
			strlength = b.length;  
			crc1 = 0x0000FFFF;  
			for (int i = 0; i < strlength; i++) {  
				tc = (int) (crc1 >>> 8);  
				crc1 = (int) (tc ^ (b[i]&0xFF));  
				for (r = 0; r < 8; r++) {  
					sbit = (byte) (crc1 & 0x00000001);  
					crc1 >>>= 1;  
	    			if (sbit != 0)  
	    				crc1 ^= 0x0000A001;  
				}  
			}  
			//Integer.toHexString(crc1);
			crc[0]=(byte)(crc1&0xFF);
			crc[1]=(byte)(crc1>>8&0xFF);
		} catch (Exception ex) {  
		}  */
		int flag;
		int  crc1 = 0xFFFF;
		for(int i=startIndex; i<endIndex; i++)
		{
			crc1 = crc1^(b[i]&0xFF);
			for(int j=0; j<8; j++)
			{
				flag = crc1%2;//或flag = crc&0x0001;两者可实现同样效果
				crc1 >>= 1;
				if (flag == 1)
					crc1 ^= 0xA001;
			}
		}
		crc[0]=(byte)(crc1&0xFF);
		crc[1]=(byte)(crc1>>8&0xFF);
	}
	
	public static int setLocalTime(int Year,int Month,int Day,int Hour,int Minute,int Second){
		String date=String.format("%04d-%02d-%02d", Year,Month,Day);
		String time=String.format("%02d:%02d:%02d", Hour,Minute,Second);
		int ret=3;
		Process exec=null;
		try {
			exec = Runtime.getRuntime().exec("cmd /c date " + date);
			if (exec.waitFor() == 0) {
	            //System.out.println("设置系统日期成功：" + date);
	            ret=1;
	        } else {
	        	ret=-1;
	            //System.out.println("设置系统日期失败：" + date);
	        }
			exec = Runtime.getRuntime().exec("cmd /c time " + time);
			if (exec.waitFor() == 0) {
				ret=2;
	            //System.out.println("设置系统时间成功：" + time);
	        } else {
	        	ret=-2;
	            //System.out.println("设置系统时间失败：" + time);
	        }
		} catch(InterruptedException e){
			ret=-3;
			e.printStackTrace();
		}catch (IOException e) {
			ret=-3;
			e.printStackTrace();
		}
		return ret;
	}
	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
	public static void mkdir(String dir){
		new File(dir).mkdirs();
	}
	public static void delFolder(String folderPath) {
	     try {
	        delAllFile(folderPath); //删除完里面所有内容
	        File myFilePath = new File(folderPath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	public static boolean delAllFile(String path){
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	}
	//列出指定目录下的所有文件，包括子文件夹中的
	public static List<File> getDirFile(String dir){
		List<File> fileList=new ArrayList<File>();
		File file=new File(dir);
		if(file.exists()&&file.isDirectory()) {
			File[] childFileList = file.listFiles();
	        for (int n=0; n<childFileList.length; n++)
	        {
	        	if(childFileList[n].isDirectory()) {
	        		List<File> fl=getDirFile(dir+childFileList[n].getName()+"\\");
	        		fileList.addAll(fl);
	        	}else if(childFileList[n].isFile()) {
	        		fileList.add(new File(dir+childFileList[n].getName()));
	        	}
	            
	        }
		}
		return fileList;
	}
}
