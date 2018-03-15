package com.coreware.util.LogAppenders;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Priority;
/**
 * 重写DailyRollingFileAppender类，用来写入ERROR级别的日志。其它级别的日志不写入<br/>
 * 只有ERROR级别的配置才使用这个类来托管
 * @author sunshuai
 * 2016-11-15
 */
public class ErrorDailyRollingFileAppender extends DailyRollingFileAppender {

	@Override
	public boolean isAsSevereAsThreshold(Priority priority) {//判断当前日志级别是否需要写入文件
		String level = priority.toString();
        if (level.equalsIgnoreCase("ERROR")) {
            return true;
        }
		return false;
	}
	
}
