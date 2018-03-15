package com.coreware.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 将数据封装为标准格式返回到页面去
 * @author 孙帅
 * 2016-09-17
 */
public class dataToJsonUtil {
	/**
	 * 获取不到数据的时候需要返回错误信息的
	 * @default_param status   状态码   成功：200   失败：500    (不需要传递这个参数，默认是500)
	 * @param msg      可能需要提供给前端的信息
	 * @return  错误的提示JSON
	 */
	public static void formatDate(HttpServletResponse response,String msg){
		JSONObject jo=new JSONObject();
		jo.put("status", 500);
		jo.put("msg", msg);
		try {
			response.getWriter().print(jo);
		} catch (IOException e) { }
	}
	/**
	 * 将Map的数据转成对应的JSON格式
	 * @param status   状态码   成功：200   失败：500
	 * @param msg      可能需要提供给前端的信息
	 * @param dataMap
	 * @return
	 */
	public static void formatDate(HttpServletResponse response,int status,String msg,Map dataMap){
		JSONObject jo=new JSONObject();
		jo.put("status", status);
		jo.put("msg", msg);
		if(dataMap!=null && dataMap.size()>0){
			JSONArray ja=JSONArray.fromObject(dataMap);
			jo.put("data", ja);
		}else{
			jo.put("data", "[]");
		}
//		System.out.println("执行了map的转换");
		try {
			response.getWriter().print(jo);
		} catch (IOException e) { }
	}
	/**
	 * 将list的数据转成对应的JSON格式
	 * @param status   状态码   成功：200   失败：500
	 * @param msg      可能需要提供给前端的信息
	 * @param dataList
	 * @return
	 */
	public static void formatDate(HttpServletResponse response,int status,String msg,List dataList){
		JSONObject jo=new JSONObject();
		jo.put("status", status);
		jo.put("msg", msg);
		if(dataList!=null && dataList.size()>0){
			JSONArray ja=JSONArray.fromObject(dataList);
			jo.put("data", ja);
		}else{
			jo.put("data", "[]");
		}
//		System.out.println("执行了List的转换");
		try {
			response.getWriter().print(jo);
		} catch (IOException e) { }
	}
	/**
	 * 将Object的数据转成对应的json数据
	 * @param status   状态码   成功：200   失败：500
	 * @param msg      可能需要提供给前端的信息
	 * @param dataObj
	 * @return
	 */
	public static void formatDate(HttpServletResponse response,int status,String msg,Object dataObj){
		JSONObject jo=new JSONObject();
		jo.put("status", status);
		jo.put("msg", msg);
		if(dataObj!=null){
			try{//在不知道Object是集合对象还是单个对象的情况下，先尝试使用JSONObject来转换对象
				JSONObject joData=JSONObject.fromObject(dataObj);
				jo.put("data", joData);
			}catch(Exception ee){//当JSONObject转换对象失败的时候使用JSONArray来转换对象
				try{
					JSONArray ja=JSONArray.fromObject(dataObj);
					jo.put("data", ja);
				}catch(Exception eee){//当JSONArray也不能转换对象的时候就直接将对象原文返回成字符串
					jo.put("data", dataObj.toString());
				}
			}
		}else{
			jo.put("data", "{}");
		}
//		System.out.println("执行了Object的转换");
		try {
			response.getWriter().print(jo);
		} catch (IOException e) { }
	}
	
	/**
	 * 将JSONArray的数据转成对应的JSON格式
	 * @param status   状态码   成功：200   失败：500
	 * @param msg      可能需要提供给前端的信息
	 * @param dataJA
	 * @return
	 */
	public static void formatDate(HttpServletResponse response,int status,String msg,JSONArray dataJA){
		JSONObject jo=new JSONObject();
		jo.put("status", status);
		jo.put("msg", msg);
		if(dataJA!=null && dataJA.size()>0){
			jo.put("data", dataJA);
		}else{
			jo.put("data", "[]");
		}
//		System.out.println("执行了JSONArray的转换");
		try {
			response.getWriter().print(jo);
		} catch (IOException e) { }
	}
	/**
	 * 将JSONObject的数据转成对应的JSON格式
	 * @param status   状态码   成功：200   失败：500
	 * @param msg      可能需要提供给前端的信息
	 * @param dataJO
	 * @return
	 */
	public static void formatDate(HttpServletResponse response,int status,String msg,JSONObject dataJO){
		JSONObject jo=new JSONObject();
		jo.put("status", status);
		jo.put("msg", msg);
		if(dataJO!=null){
			jo.put("data", dataJO);
		}else{
			jo.put("data", "{}");
		}
//		System.out.println("执行了JSONObject的转换");
		try {
			response.getWriter().print(jo);
		} catch (IOException e) { }
	}
}
