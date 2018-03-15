package com.coreware.util.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Element;

import com.coreware.util.config.SimpleXMLUtil;

/**
 * 字典配置操作类
 * 配置格式：
 * <root>
 * <items name="mapName">
 *     <item key="key1" value="value1"/>
 *     <item><key>key2</key><value>value2</value></item>
 * </items>
 * </root>
 * Created by HarsenLin on 2015/11/17.
 */
public class Dictionary {
    /** 已加载的字典 */
    private final static Map<String, Dictionary> loadedDictionary = new HashMap<String, Dictionary>();

    /** 配置名称 */
    protected String dictionaryName;
    /** 所有配置 */
    protected Map<String, Map<String, String>> allMap;

    /**
     * 屏蔽构造器
     * @param dictionaryName 字典配置名
     */
    private Dictionary(String dictionaryName){
        this.dictionaryName = dictionaryName;
        refresh();
    }

    /**
     * 获取默认字典实例
     * @return 字典
     */
    public static Dictionary getInstance(){
        return getInstance("DataDictionary");
    }

    /**
     * 获取字典实例
     * @param dictionaryName 字典名称
     * @return 字典
     */
    public static Dictionary getInstance(String dictionaryName){
        Dictionary dic = loadedDictionary.get(dictionaryName);
        if(null == dic){
            dic = new Dictionary(dictionaryName);
            loadedDictionary.put(dictionaryName, dic);
        }
        return dic;
    }

    /**
     * 刷新配置
     */
    public void refresh(){
        this.allMap = new HashMap<String, Map<String, String>>();
        Element element = SimpleXMLUtil.file2Doc("classpath:" + this.dictionaryName + ".xml").getRootElement();
        List<Element> items = element.getChildren("items");

        String mapName, key, value;
        Map<String, String> mapItem;
        for (Element e : items){
            mapItem = new HashMap<String, String>();
            mapName = e.getAttributeValue("name");
            List<Element> item = e.getChildren("item");
            for (Element ie : item){
                key = ie.getAttributeValue("key");
                if(null == key) key = ie.getChildText("key");
                value = ie.getAttributeValue("value");
                if(null == value) value = ie.getChildText("value");
                mapItem.put(key, value);
            }
            this.allMap.put(mapName, mapItem);
        }
    }
    /**
     * 获取字典配置项
     * @param name 配置项名称
     * @return 配置集合
     */
    public Map<String, String> items(String name){
        Map<String, String> retMap = this.allMap.get(name);
        if(null == retMap){
            retMap = new HashMap<String, String>();
            this.allMap.put(name, retMap);
        }
        return retMap;
    }
    /**
     * 获取字典配置项
     * @param name 配置项名称
     * @param key 关键字
     * @return 配置集合
     */
    public String value(String name, String key){
        return items(name).get(key);
    }

//    public static void main(String[] args) {
//        Dictionary dic = Dictionary.getInstance("DataDictionary");
//        Dictionary dic = Dictionary.getInstance();
//        System.out.println(dic.items("Domain"));
//        System.out.println(dic.value("Domain", "5"));
//    }
}
