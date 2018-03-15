package com.coreware.util;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 工具类
 *
 * @author 孙帅
 */
public class CorewareUtil {
    private static Logger logger = Logger.getLogger(CorewareUtil.class);
    private static String encoding = "UTF-8";

    /**
     * 没有参数名，只有参数值的httpClent请求(POST)
     *
     * @param urlStr 请求地址
     * @param params 参数
     * @return 结果
     */

    public static String postHttpClentsJsonNoEntity(String urlStr,String params){
    	try{
            HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost(urlStr);
            httppost.addHeader("Content-Type", "application/json; charset=utf-8");
            StringEntity se = new StringEntity(params, "utf-8");
            httppost.setEntity(se);
            HttpResponse response = httpclient.execute(httppost);

			if(response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String result= EntityUtils.toString(response.getEntity(), "utf-8");
		    	return result;
			}else{
				logger.error("请求接口错误-请求地址："+urlStr);
				return "请求或数据接口出现错误";
			}
    	}catch (Exception e) {
    		logger.error( "连接【"+urlStr+"】失败\r\n"+e.getMessage());
    		return "请求或数据接口出现错误";
    	}
    }



   /* public static void main(String args[]) throws Exception{

        HttpClient httpclient = HttpClients.createDefault();

        HttpPost httppost = new HttpPost("https://oapi.dingtalk.com/robot/send?access_token=hkjhgfdubjvghhfkdsoihbjmvjcjdksgjfrewihvycdnjk");
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");

        String textMsg = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"我是机器人！\"}}";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);

        HttpResponse response = httpclient.execute(httppost);
        if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
            String result= EntityUtils.toString(response.getEntity(), "utf-8");
            System.out.println(result);
        }
    }*/


}
