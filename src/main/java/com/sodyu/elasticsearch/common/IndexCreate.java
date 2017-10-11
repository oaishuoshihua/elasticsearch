package com.sodyu.elasticsearch.common;


import com.sodyu.elasticsearch.enums.IndexFieldType;
import com.sodyu.elasticsearch.models.FieldInfo;
import com.sodyu.elasticsearch.models.IndexMappingModel;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by yuhp on 2017/9/13.
 */
public class IndexCreate {
    private static Logger logger = LoggerFactory.getLogger(IndexCreate.class);
    private static boolean createMapping(Class indexClass) {
        boolean result = false;
        if (null == indexClass) {
            logger.error("索引class为空");
            return false;
        }
        IndexMappingModel mappingModel = new IndexMappingModel(indexClass);
        if (StringUtils.isBlank(mappingModel.getIndexName()) || StringUtils.isBlank(mappingModel.getIndexType())) {
            logger.error("索引名称或类型名称为空{}{}", mappingModel.getIndexIdName(), mappingModel.getIndexType());
            return result;
        }
        try {

            logger.info("创建索引{}mapping",mappingModel.getIndexName());
            PutMappingRequest mappingRequest = getMapping(mappingModel);
            ActionFuture<PutMappingResponse> response= ElasticSearchClient.indices().putMapping(mappingRequest);
            if (response.actionGet().isAcknowledged()) {
                result = true;
                logger.info("创建索引{}mapping成功",mappingModel.getIndexName());
            } else {
                result = false;
                logger.info("创建索引{}mapping失败", mappingModel.getIndexName());
            }
        } catch (Exception e) {
            logger.error("创建索引{}mapping失败", mappingModel.getIndexName(), e);
        }
        return result;
    }

    private static PutMappingRequest getMapping(IndexMappingModel mappingModel) throws Exception {
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject(mappingModel.getIndexType())//type名称
                .startObject("properties");//下面是设置文档列属性。

        Map<String, FieldInfo> fieldInfoMap = mappingModel.getFieldTypeInfos();
        if (fieldInfoMap != null && fieldInfoMap.values() != null) {
            Iterator<FieldInfo> iterator = fieldInfoMap.values().iterator();
            while (iterator.hasNext()) {
                FieldInfo info = iterator.next();
                builder.startObject(info.getName())
                        .field("type", StringUtils.lowerCase(info.getType()))
                        .field("store", info.isStore() ? "yes" : "no");
                if(IndexFieldType.Text.getDataType().equalsIgnoreCase(info.getType())||IndexFieldType.String.getDataType().equalsIgnoreCase(info.getType())) {
                    builder.startObject("fields").startObject("keyword").field("type","keyword").field("ignore_above",256).endObject().endObject();
                }
                builder.endObject();

            }

        }
        builder.endObject()
                .endObject()
                .endObject();
        PutMappingRequest mappingRequest= Requests.putMappingRequest(mappingModel.getIndexName()).type(mappingModel.getIndexType()).source(builder);
        return mappingRequest;
    }


    private static boolean createIndex(Class indexClass){
        boolean result=false;
        if (null == indexClass) {
            logger.error("索引class为空");
            return false;
        }
        IndexMappingModel mappingModel = new IndexMappingModel(indexClass);
        if (StringUtils.isBlank(mappingModel.getIndexName()) || StringUtils.isBlank(mappingModel.getIndexType())) {
            logger.error("索引名称或类型名称为空{}{}", mappingModel.getIndexIdName(), mappingModel.getIndexType());
            return result;
        }
        try {
            logger.info("创建索引{}",mappingModel.getIndexName());
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("number_of_shards", 4)//设置分片数量
                    .field("number_of_replicas", 1)//设置副本数量
                    .field("mapping.total_fields.limit", 30000)//字段数量限制
                    .field("analysis.analyzer.default.type", "ik_max_word")
                    .field("analysis.search_analyzer.default.type", "ik_smart")
                    .field("refresh_interval", "30s")
                    .endObject();
            CreateIndexRequestBuilder cirb = ElasticSearchClient.indices().prepareCreate(mappingModel.getIndexName()).setSettings(builder);
            CreateIndexResponse response = cirb.execute().actionGet();
            if (response.isAcknowledged()) {
                result = true;
                logger.info("创建索引{}成功",mappingModel.getIndexName());
            } else {
                result = false;
                logger.info("创建索引{}失败",mappingModel.getIndexName());

            }
        } catch (Exception e) {
            logger.info("创建索引{}失败", mappingModel.getIndexName(),e);
        }

        return result;
    }


    public static void create(Class indexClass) {
        logger.info("开始创建索引");
        IndexCreate.createIndex(indexClass);
        logger.info("开始创建索引mapping");
        IndexCreate.createMapping(indexClass);
    }
}
