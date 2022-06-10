package com.sungcor.baobiao.utils;

import com.sungcor.baobiao.STSMConstant;
import org.apache.commons.lang.time.DateUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class UtilTools {
//    static Logger logger = Logger.getLogger(UtilTools.class);

    public static final int BUFFERSIZE = 4096;
    public static final String defaultFormat = "yyyy-MM-dd HH:mm:ss";

    public static long compIP(String ip,String ip_seg){
        String[] ip1s = ip.split("\\.");
        String[] ip2s = ip_seg.split("\\.");
        long l1 = 0;
        long l2 = 0;
        for (int i = 0; i < ip1s.length; i++){
            l1 += Long.parseLong(ip1s[i]) * Math.pow(256,ip1s.length - i -1);
            l2 += Long.parseLong(ip2s[i]) * Math.pow(256,ip2s.length - i -1);
        }
        long aa =  l1 & l2;
        return l1 & l2;
    }

    public enum DateFiled{
        YEAR,
        MONTH,
        DATE,
        HOUR,
        MINUTE
    }

    public static String getCurrentTime(String format){
        SimpleDateFormat fmt = new SimpleDateFormat((format!=null && !"".equals(format)) ? format : defaultFormat);
        return fmt.format(Calendar.getInstance().getTime());
    }

    public static Date getCurrentDate(){
        return Calendar.getInstance().getTime();
    }

    public static String fmtDate(Date date,String fmt){
        if(date==null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat((fmt!=null && !"".equals(fmt)) ? fmt : defaultFormat);
        return sdf.format(date);
    }

    public static Date getTime(String time,String fmt){
        SimpleDateFormat sdf = new SimpleDateFormat((fmt!=null && !"".equals(fmt)) ? fmt : defaultFormat);
        try {
            return sdf.parse(time);
        } catch (ParseException e) {
//            logger.error("String to Date Error:" + e.getMessage());
            return null;
        }
    }

    public static Date modifyDate(Date date,int amount,DateFiled filed){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(filed==DateFiled.YEAR){
            c.add(Calendar.YEAR,amount);
        }else if(filed==DateFiled.MONTH){
            c.add(Calendar.MONTH,amount);
        }else if(filed==DateFiled.DATE){
            c.add(Calendar.DAY_OF_MONTH,amount);
        }else if(filed==DateFiled.HOUR){
            c.add(Calendar.HOUR,amount);
        }else if(filed==DateFiled.MINUTE){
            c.add(Calendar.MINUTE,amount);
        }
        return c.getTime();
    }

    public static String getBussinessNoByDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        return sdf.format(getCurrentDate());
    }

    public static byte[] getBytesFromFile(String fileName) throws IOException {
        InputStream in = new FileInputStream(fileName);
        return readBytes(in);
    }

    public static byte[] readBytes(InputStream inputStream) throws IOException {
        byte[] bytes = null;
        if (inputStream==null) {
            throw new IllegalArgumentException("inputStream is null");
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        transfer(inputStream, outputStream);
        bytes = outputStream.toByteArray();
        outputStream.close();
        return bytes;
    }

    public static int transfer(InputStream in, OutputStream out) throws IOException {
        int total = 0;
        byte[] buffer = new byte[BUFFERSIZE];
        int bytesRead = in.read( buffer );
        while ( bytesRead != -1 ) {
            out.write( buffer, 0, bytesRead );
            total += bytesRead;
            bytesRead = in.read( buffer );
        }
        return total;
    }

    /**
     *
     * */
    @SuppressWarnings("unchecked")
    public static ArrayList getDistinctQCBList(List list){
        Set resultSet = new HashSet(list);
        return new ArrayList(resultSet);
    }

    public static String unicode2asc(String str) {
        int l = str.length();
        char s[] = new char[l / 4];
        for (int i = 0; i < l / 4; i++) {

            s[i] = (char) Integer.parseInt((str.substring(i * 4,
                    4 * i + 4)), 16);
        }
        return (new String(s));
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (byte k : b) {
                    if (k < 0) k += 256;
                    sb.append("%").append(Integer.toHexString(k).
                            toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    public static String getfmtDateStr(String dateStr,String dateStrFmt,String fmt){

        DateFormat df2 = (null == dateStrFmt)?new SimpleDateFormat(defaultFormat):new SimpleDateFormat(dateStrFmt);
        try {
            Date date2 = df2.parse(dateStr);
            return fmtDate(date2,fmt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void printImageToClient(File file, HttpServletResponse response) throws IOException{
        String mimeType = "";
        String fileName = file.getName();
        if(fileName.length()>5){
            if(fileName.substring(fileName.length() - 5, fileName.length()).equals(".jpeg")){
                mimeType = "image/jpeg";
            } else if(fileName.substring(fileName.length() - 4, fileName.length()).equals(".png")){
                mimeType = "image/png";
            }else if(fileName.substring(fileName.length() - 4, fileName.length()).equals(".gif")) {
                mimeType = "image/gif";
            }else{
                mimeType = "image/jpg";
            }
        }
        if(file.exists()){
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            response.setHeader("Content-Type", mimeType);
            response.setHeader("Content-Length", String.valueOf(file.length()));
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            response.setHeader("Last-Modified", sdf.format(new Date(file.lastModified())));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte input[] = new byte[4096];
            boolean eof = false;
            while(!eof){
                int length = bis.read(input);
                if(length == -1){
                    eof = true;
                } else {
                    bos.write(input, 0, length);
                }
            }
            bos.flush();
            bis.close();
            bos.close();
            response.setStatus(HttpServletResponse.SC_OK); //��ֹ����connection reset by peer
            response.flushBuffer();
        }else{
            throw new FileNotFoundException(file.getAbsolutePath());
        }
    }

    public static String getIP(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getCurrentRootDir(){
        String cp = getClassPath();
        String cpLowerCase=cp.toLowerCase();
        if (cpLowerCase.indexOf("tomcat")!= -1)
            cp = cp.substring(0,cpLowerCase.lastIndexOf("tomcat")) ;
        else cp = cp.substring(0,cpLowerCase.lastIndexOf("jboss"));
        return cp;
    }

    public static String getClassPath(){
        String path= UtilTools.class.getResource("/").getPath();
        while(path.indexOf("%20")!=-1) {
            path=path.replace("%20"," ");
        }
        if(path.startsWith("/"))
            path=path.substring(1,path.length());
        return path;
    }

    public static boolean downloadFile(HttpServletResponse response,String filePath, String fileName){
        try {
            File f = new File(filePath);
            if(!f.exists()) {
                return false;
            }

            response.setContentType("application/octet-stream");
            //确保下载的文件名显示中文不出现乱码
            response.setHeader("Content-Disposition","attachment;filename="+new String(fileName.getBytes("gb2312"), "ISO8859-1" ));

            byte[] buf = new byte[1024];
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
            OutputStream out = response.getOutputStream();
            int len = 0;
            while((len = in.read(buf)) > 0) {
                out.write(buf,0,len);
            }
            out.flush();
            in.close();
            out.close();
            response.setStatus(response.SC_OK);
            response.flushBuffer();
        } catch (Exception e) {
            System.out.println("Downloading file error! "+e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取传入时间的上周一
     *
     * @param date 当前时间
     * @return 返回上周一时间
     */
    public static Date getLastWeekMonday(Date date) {
        Date a = DateUtils.addDays(date, -1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.add(Calendar.WEEK_OF_YEAR, -1);// 一周
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        return cal.getTime();
    }

    /**
     * 查询上周周日
     * @param date 当前时间
     * @return 返回上周周日
     */
    public static Date	getLastWeekSunday(Date date) {

        Date a = DateUtils.addDays(date, -1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(a);
        cal.set(Calendar.DAY_OF_WEEK, 1);

        return cal.getTime();
    }


    /**
     * 文件拷贝方法，从通过源文件拷贝到一个新的文件中
     * @param src
     * @param target
     */
    public static void copyFile(File src, File target)  {
        try  {
            FileInputStream fileInput = new FileInputStream(src);
            FileOutputStream fileOutput = new FileOutputStream(target);
            FileChannel sourceChannel = fileInput.getChannel();
            FileChannel targetChannel = fileOutput.getChannel();
            MappedByteBuffer mbb = sourceChannel.map(FileChannel.MapMode.READ_ONLY, STSMConstant.NUM_ZERO, sourceChannel.size());
            targetChannel.write(mbb);
            sourceChannel.close();
            targetChannel.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }
}
