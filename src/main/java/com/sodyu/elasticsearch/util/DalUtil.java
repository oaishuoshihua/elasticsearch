package com.sodyu.elasticsearch.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yuhp on 2017/9/11.
 */
public class DalUtil {
    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession session = null;
    private static Logger logger = LoggerFactory.getLogger(DalUtil.class);
    static {
        /**
         * 初始化Sqlsession
         */
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis.xml"));
            session = sqlSessionFactory.openSession(true);
        }catch (Exception e) {
            logger.error("DB session初始化错误", e);
        }
    }

    public static <T> T getMapper(Class<?> clazz){
        T t=(T)session.getMapper(clazz);
        return t;
    }


}