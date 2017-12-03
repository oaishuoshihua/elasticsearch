package com.sodyu.elasticsearch.indexmodel;


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
    private String shopId;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Long saleCount) {
        this.saleCount = saleCount;
    }

    public Long getProductCount() {
        return productCount;
    }

    public void setProductCount(Long productCount) {
        this.productCount = productCount;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getShopRealUrl() {
        return shopRealUrl;
    }

    public void setShopRealUrl(String shopRealUrl) {
        this.shopRealUrl = shopRealUrl;
    }

    public Double getGoodratePercent() {
        return goodratePercent;
    }

    public void setGoodratePercent(Double goodratePercent) {
        this.goodratePercent = goodratePercent;
    }

    public String getMainAuction() {
        return mainAuction;
    }

    public void setMainAuction(String mainAuction) {
        this.mainAuction = mainAuction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getAllkeys() {
        return allkeys;
    }

    public void setAllkeys(String allkeys) {
        this.allkeys = allkeys;
    }

    public String getDataChange_CreateUser() {
        return dataChange_CreateUser;
    }

    public void setDataChange_CreateUser(String dataChange_CreateUser) {
        this.dataChange_CreateUser = dataChange_CreateUser;
    }

    public Long getDataChange_CreateTime() {
        return dataChange_CreateTime;
    }

    public void setDataChange_CreateTime(Long dataChange_CreateTime) {
        this.dataChange_CreateTime = dataChange_CreateTime;
    }

    public Long getDataChange_LastTime() {
        return dataChange_LastTime;
    }

    public void setDataChange_LastTime(Long dataChange_LastTime) {
        this.dataChange_LastTime = dataChange_LastTime;
    }

    public String getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(String geoPoint) {
        this.geoPoint = geoPoint;
    }
}
