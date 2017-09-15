package com.sodyu.elasticsearch.index;


import com.sodyu.elasticsearch.common.ElasticSearchClient;
import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;

/**
 * Created by yuhp on 2017/9/13.
 */
public class CreateIndex {

    private static TransportClient client;

    CreateIndex() {
        //初始化client客户端
        client = ElasticSearchClient.getElasticClient();
    }

    public static boolean create() {
        Logger logger = Logger.getLogger(CreateIndex.class);
        boolean result = false;
        try {
            XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .startObject("settings")
                    .field("number_of_shards", 4)//设置分片数量
                    .field("number_of_replicas", 1)//设置副本数量
                    .field("mapping.total_fields.limit", 30000)//字段数量限制
                    .field("analysis.analyzer.default.type", "ik_max_word")
                    .field("analysis.search_analyzer.default.type", "ik_smart")
                    .field("refresh_interval", "30s")
                    .endObject()
                    .endObject()
                    .startObject()
                    .startObject("dianpu")//type名称
                    .startObject("properties") //下面是设置文档列属性。
                    .startObject("id").field("type", "long").field("store", "yes").endObject()
                    .startObject("shopId").field("type", "long").field("store", "yes").endObject()
                    .startObject("shopName").field("type", "string").field("store", "yes").endObject()
                    .startObject("saleCount").field("type", "long").field("store", "yes").endObject()
                    .startObject("goodratePercent").field("type", "double").field("store", "yes").endObject()
                    .startObject("location").field("type", "String").field("com/sodyu/elasticsearch/index", "not_analyzed").field("store", "yes").endObject()
                    .startObject("telephone").field("type", "String").field("com/sodyu/elasticsearch/index", "not_analyzed").field("store", "yes").endObject()
                    .startObject("shopUrl").field("type", "String").field("com/sodyu/elasticsearch/index", "not_analyzed").field("format", "dateOptionalTime").field("store", "yes").endObject()
                    .startObject("mainAuction").field("type", "string").field("store", "yes").endObject()
                    .startObject("category").field("type", "string").field("store", "yes").endObject()
                    .startObject("destination").field("type", "string").field("store", "yes").endObject()
                    .startObject("createTime").field("type", "date").field("format", "dateOptionalTime").endObject()
                    .endObject()
                    .endObject()
                    .endObject();

            CreateIndexRequestBuilder cirb = ElasticSearchClient.indices().prepareCreate("crawler")//index名称
                    .setSource(mapping);
            CreateIndexResponse response = cirb.execute().actionGet();
            if (response.isAcknowledged()) {
                result = true;
                logger.info("Index created.");
            } else {
                result = false;
                logger.info("Index creation failed.");
            }
        } catch (IOException e) {
            logger.error("创建索引失败", e);
        }

        return result;
    }

}
