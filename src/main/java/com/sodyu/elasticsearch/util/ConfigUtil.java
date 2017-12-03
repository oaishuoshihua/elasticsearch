package com.sodyu.elasticsearch.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by yuhp on 2017/11/30.
 */
public class ConfigUtil {
    private static Logger logger= LoggerFactory.getLogger(ConfigUtil.class);
    private static Properties properties= new Properties();
    static {
        try {
            properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            logger.error("加载配置文件config.properties出错！",e);
        }
    }


    public static String getValue(String key){
        return properties.getProperty(key);
    }
}
