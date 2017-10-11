package com.sodyu.elasticsearch.enums;

/**
 * Created by yuhp on 2017/10/9.
 */
public enum  IndexFieldType {
    Default("default"),
    Text("text"),
    String("string"),
    GeoPoint("geo_point"),
    Keyword("keyword");
    private String dataType;
    IndexFieldType(String dataType){
        this.dataType=dataType;
    }

    public java.lang.String getDataType() {
        return dataType;
    }

    public void setDataType(java.lang.String dataType) {
        this.dataType = dataType;
    }
}
