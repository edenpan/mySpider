package javaTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class httpPost {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Referer", "http://yz.chsi.com.cn/zsml/querySchAction.do");
            conn.setRequestProperty("Host", "yz.chsi.com.cn");
            conn.setRequestProperty("Origin", "http://yz.chsi.com.cn");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            System.out.println(url+ param);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }  
    public static void setMap(Elements trList, Map<String, Object> majorMap, String name){
    	List<String> majorList = (List<String>) majorMap.get(name); 
    	if (majorList == null ){
    		majorList = new ArrayList<String>();
    	}
    	for (int i = 0; i < trList.size(); i++){
    		Element td = trList.get(i);
    		if(td.select("td").get(2).text().contains("非全日制")){
    			String majorStr = td.select("td").get(0).text() + ";" + td.select("td").get(1).text() + ";" + td.select("td").get(2).text() + "\t";
    			majorList.add(majorStr);
    		}
//    		System.out.println(majorStr);
    		
    	}
    	majorMap.put(name, majorList);
    	
    }
    
    
    public static void execElement(Map<String, Object> majorMap, String name, String sr){
    	
		Document d2 = Jsoup.parse(sr);
		Elements e2 = d2.select("div#sch_list");
		Elements e3 = e2.select("tbody").select("tr");
		httpPost.setMap(e3, majorMap, name);
    }
    public static void main(String[] args) throws InterruptedException {
        //发送 POST 请求
    	Map<String,String> collegeMap = new HashMap<String,String>();
		collegeMap.put("北京大学","11");
		collegeMap.put("中国人民大学","11");
		collegeMap.put("清华大学","11");
		collegeMap.put("北京航空航天大学","11");
		collegeMap.put("复旦大学","31");
		collegeMap.put("同济大学","31");
		collegeMap.put("上海交通大学","31");
		collegeMap.put("浙江大学","33");
		collegeMap.put("中国科学技术大学","51");
		collegeMap.put("武汉大学","42");
		collegeMap.put("华中科技大学","42");
		collegeMap.put("中南大学","43");
		collegeMap.put("国防科学技术大学","43");
		collegeMap.put("中山大学","44");
		collegeMap.put("华南理工大学","44");
		collegeMap.put("电子科技大学","51");
    	
		HashMap<String, Object> majorMap = new HashMap<String, Object>();
		
    	Iterator iter = collegeMap.entrySet().iterator();
    	while (iter.hasNext()) {
    		Map.Entry entry = (Map.Entry) iter.next();
    		String name = (String)(entry.getKey());
    		String url = "http://yz.chsi.com.cn/zsml/querySchAction.do"+ "?ssdm="+(String)(entry.getValue()) + ""+ "&dwmc="+ name +"&mldm=08&mlmc=%E5%B7%A5%E5%AD%A6&yjxkdm=&zymc=";
    		String sr=httpPost.sendPost("http://yz.chsi.com.cn/zsml/querySchAction.do","ssdm="+(String)(entry.getValue()) + "&dwmc="+ (String)(entry.getKey()) +"&mldm=08&mlmc=%E5%B7%A5%E5%AD%A6&yjxkdm=&zymc=");
    		Thread.sleep(100);
    		httpPost.execElement(majorMap, (String)(entry.getKey()), sr);
    		Document document = Jsoup.parse(sr);
    		Elements elements = document.select("div.clearfix");
    		Elements e1 = elements.select("ul.ulPage");
    		Element li = e1.select("li").get(0);
//    		System.out.println(li.select("#page_total").text());   
    		int totalPage = Integer.parseInt(li.select("#page_total").text().split("/")[1]);
    		if( totalPage > 1){
    			for (int i = 2; i <= totalPage; i++){
    				url = "http://yz.chsi.com.cn/zsml/querySchAction.do"+ "?ssdm="+(String)(entry.getValue()) + ""+ "&dwmc="+ (String)(entry.getKey()) + "&pageno="+ i +"&mldm=08&mlmc=%E5%B7%A5%E5%AD%A6&yjxkdm=&zymc=";
    	    		String ssr=httpPost.sendPost("http://yz.chsi.com.cn/zsml/querySchAction.do","ssdm="+(String)(entry.getValue()) + "&dwmc="+ name + "&pageno="+ i +"&mldm=08&mlmc=%E5%B7%A5%E5%AD%A6&yjxkdm=&zymc=");
    	    		httpPost.execElement(majorMap, name, ssr);
    			}
    		}
    		System.out.println("key" + (String)(entry.getKey()) + ":\tvalue" + majorMap.get(name).toString());
    	}
    	
    	File fs = new File("NotAllDayMajorList");
    	try {
    		if(!fs.exists()){
				fs.createNewFile();
			}
    		FileOutputStream fop = new FileOutputStream(fs);
    		byte[] mapByte = majorMap.toString().getBytes();
    		fop.write(mapByte);
    		fop.flush();
    		fop.close();
    	}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    
}