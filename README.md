#### 该项目是使用  钉钉  的自定义机器人发消息到钉钉群中

##### 使用 Webhook 实现通信。

##### 配置：

在 DingCool\src\main\resources\DataDictionary.xml  中修改（value中的值,填入正确的webhook地址）

	<item key="webhook" value="https://oapi.dingtalk.com/robot/send?access_token=*******************************************" /><!-- 这里填入webhook地址-->

本程序使用  maven  和  swagger-ui(一个API管理插件)  框架是java的 springMVC