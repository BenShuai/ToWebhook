package com.coreware.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppLoaderListener implements ServletContextListener{
	/**
	 * 绝对地址，项目在服务器上的文件位置
	 */
	public static String ABSOLUTE_PATH = "";
	/**
	 * 项目部署的上下文路径，URL
	 */
	public static String CTX = "/DingCool";
	/**
	 * 素材文件路径，URL
	 */
	public static String STC = "";

	/**
	 * 项目访问目录
	 */
	public static String PROJECT_URL_PATH = "";
	

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		sc.setAttribute("ctx", AppLoaderListener.CTX);
	}

}
