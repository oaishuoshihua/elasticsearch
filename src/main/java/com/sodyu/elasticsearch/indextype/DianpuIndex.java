package com.sodyu.elasticsearch.indextype;


import com.sodyu.elasticsearch.annotation.Index;
import com.sodyu.elasticsearch.annotation.IndexField;
import com.sodyu.elasticsearch.annotation.IndexId;
import com.sodyu.elasticsearch.enums.IndexFieldType;

/**
 * Created by yuhp on 2017/9/14.
 */

@Index(name = "crawler", typeName = "dianpu")
public class DianpuIndex extends BasicIndex {

    @IndexId
    private Long id;

    @IndexField
    private Long uid;

    @IndexField
    private Long shopId;

    @IndexField
    private String shopName;

    @IndexField
    private Long saleCount;

    @IndexField
    private Long productCount;

    @IndexField(dataType = IndexFieldType.Keyword)
    private String shopUrl;

    @IndexField
    private String shopRealUrl;

    @IndexField
    private Double goodratePercent;

    @IndexField
    private String mainAuction;

    @IndexField(dataType = IndexFieldType.Keyword)
    private String location;

    @IndexField(dataType = IndexFieldType.Keyword)
    private String telephone;

    @IndexField(dataType = IndexFieldType.Keyword)
    private String type;

    @IndexField(dataType = IndexFieldType.Keyword)
    private String category;

    @IndexField
    private String destination;

    @IndexField
    private String allkeys;

    @IndexField(dataType = IndexFieldType.Keyword)
    private String dataChange_CreateUser;

    @IndexField(name = "createTime")
    private Long dataChange_CreateTime;

    @IndexField(name = "lastChangeTime")
    private Long dataChange_LastTime;

    @IndexField(dataType = IndexFieldType.GeoPoint)
    private String geoPoint;
}
