package com.sodyu.elasticsearch.service;


import com.sodyu.elasticsearch.common.Constant;
import com.sodyu.elasticsearch.common.ElasticSearchOperatorAPI;
import com.sodyu.elasticsearch.common.RequestBuilder;
import com.sodyu.elasticsearch.indexmodel.BasicIndex;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by yuhp on 2017/10/10.
 */
@Service
public abstract class AbstractDataToES<T extends BasicIndex> implements DataToES {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDataToES.class);
    protected static final int pageSize = 1000;

    @Override
    public void full() {
        importDataToES(true);
    }

    @Override
    public void increment() {
        importDataToES(false);
    }

    public void importDataToES(boolean isFull) {
        String type = "";
        try {
            logger.info("开始同步数据到ES,是否全量：{}", isFull);
            Class c = (Class) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            type = c != null ? c.getSimpleName() : "";
            RequestBuilder builder = new RequestBuilder(c);
            ElasticSearchOperatorAPI<T> operatorAPI = new ElasticSearchOperatorAPI<T>(builder);

            List<T> list = null;
            int index = Constant.limitCount;//防止死循环
            while (index-- > 0) {
                int count = Constant.limitCount - index;
                if (isFull) {
                    list = getFullDataList(count, pageSize);
                } else {
                    list = getIncrDataList(count, pageSize);
                }
                if (CollectionUtils.isEmpty(list)) {
                    logger.info("全量同步到ES结束,共同步{}批", count - 1);
                    break;
                }
                logger.info("开始同步数据到ES,是否全量：{}，第{}批", count, isFull);
                operatorAPI.indexBulk(list);

            }
        } catch (Exception e) {
            logger.info("同步{}数据到ES出错,是否全量：{}", type, isFull, e);
        }
    }

    public abstract List<T> getFullDataList(int page, int pageSize);

    public abstract List<T> getIncrDataList(int page, int pageSize);
}
