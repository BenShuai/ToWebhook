package com.coreware.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.coreware.util.CorewareUtil;
import com.coreware.util.dataToJsonUtil;
import com.coreware.util.config.Dictionary;

@Api(value="/PublicAction",description="公共请求控制类")
@Controller
@RequestMapping("/pub")
public class PublicController {
	private static Logger logger = Logger.getLogger(PublicController.class);
	
	private static Dictionary dict = Dictionary.getInstance();
	private static final Map<String, String> sysConf = dict.items("sys");//系统的根配置(公共配置)

    /**
     * 方法中一些初始化的代码全部提到这里<br/>
     * 目前此方法中包含  编码转码，返回数据的格式以及编码方式
     * @param request
     * @param response
     */
    public static void initCfg(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");//让接口可以跨域请求
            response.setHeader("X-Powered-By", "0");//隐藏组件版本

        } catch (UnsupportedEncodingException e1) {logger.error( e1.getMessage()); }
    }

	/**
	 * 发消息到钉钉
	 * @param request
	 * @param response
	 */
	@ApiOperation(value = "发消息到钉钉", notes = "发消息到钉钉")
	@RequestMapping(value = "/sendMsgToDingDing",method = RequestMethod.POST)//只能接收POST请求的访问
	public @ResponseBody void login(HttpServletRequest request,  HttpServletResponse response,
			@ApiParam(name = "msgType", value = "消息类型<br/>text<br/>link<br/>", required = true) @RequestParam(required = true) String msgType,
			@ApiParam(name = "atMobiles", value = "被@人的手机号[多个用逗号隔开](text类型可选)", required = false) @RequestParam(required = false)String atMobiles,
            @ApiParam(name = "isAtAll", value = "是否@所有人(text类型可选)<br/>@所有人时:true,否则为:false", required = false) @RequestParam(required = false)Boolean isAtAll,
            @ApiParam(name = "contentStr", value = "消息内容(text、link类型必需)", required = false) @RequestParam(required = false)String contentStr,

            @ApiParam(name = "title", value = "标题(link类型必须)", required = false) @RequestParam(required = false)String title,
            @ApiParam(name = "picUrl", value = "图片URL(link类型可选)", required = false) @RequestParam(required = false)String picUrl,
            @ApiParam(name = "messageUrl", value = "内容URL(link类型必须)", required = false) @RequestParam(required = false)String messageUrl){
		initCfg(request,response);
		
		try{
			JSONObject JO=new JSONObject();
			if(msgType.equals("text")){
				JO.put("msgtype", "text");
				
				JSONObject text=new JSONObject();
				text.put("content", contentStr);
				JO.put("text", text);
				
				JSONObject at=new JSONObject();
				if(atMobiles!=null && !atMobiles.equals("")){
					at.put("atMobiles", atMobiles.split(","));
				}
				if(isAtAll!=null && isAtAll){
					at.put("isAtAll", isAtAll);
				}
                if((atMobiles!=null && !atMobiles.equals("")) ||  isAtAll) {
                    JO.put("at", at);
                }
			}else if("link".equals(msgType)){
                JO.put("msgtype", "link");

                JSONObject link=new JSONObject();
                link.put("text",contentStr);
                link.put("title",title);
                link.put("picUrl",picUrl);
                link.put("messageUrl",messageUrl);

                JO.put("link", link);
            }
			System.out.println(JO.toString());
			String result=CorewareUtil.postHttpClentsJsonNoEntity(sysConf.get("webhook"), JO.toString());
			
			dataToJsonUtil.formatDate(response,200, "success", result);
		} catch (Exception e) {}
	}
	
}