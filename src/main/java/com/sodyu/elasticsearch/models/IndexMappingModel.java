package com.sodyu.elasticsearch.models;

import com.sodyu.elasticsearch.annotation.Index;
import com.sodyu.elasticsearch.annotation.IndexField;
import com.sodyu.elasticsearch.annotation.IndexId;
import com.sodyu.elasticsearch.enums.IndexFieldType;
import com.sodyu.elasticsearch.indextype.DianpuIndex;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuhp on 2017/10/9.
 */
public class IndexMappingModel {
    private Class<?> indexClass;
    private String indexName;
    private String indexType;
    private String indexIdName;
    private Map<String, FieldInfo> fieldTypeInfos = new HashMap<String, FieldInfo>();

    public IndexMappingModel(Class<?> indexClass) {
        this.indexClass = indexClass;
        initIndex();
        initField();
    }

    private void initIndex() {
        this.indexName = this.indexType = this.indexClass.getSimpleName();
        Index index = this.indexClass.getDeclaredAnnotation(Index.class);
        if (null != index) {
            String name = index.name();
            if (null != name && !name.isEmpty()) {
                this.indexName = name;
            }
            name = index.typeName();
            if (null != name && !name.isEmpty()) {
                this.indexType = name;
            }

        }
    }

    private void initField() {
        while (null != this.indexClass) {
            Field[] fields = this.indexClass.getDeclaredFields();
            if (null != fields && fields.length > 0) {
                for (Field item : fields) {
                    item.setAccessible(true);
                    FieldInfo fieldInfo = new FieldInfo();
                    if (null != item.getDeclaredAnnotation(IndexId.class)) {
                        fieldInfo.setId(true);
                    }
                    fieldInfo.setName(item.getName());
                    fieldInfo.setType(item.getType().getSimpleName());
                    if (null != item.getDeclaredAnnotation(IndexField.class)) {
                        IndexField indexField = item.getDeclaredAnnotation(IndexField.class);
                        if (indexField.dataType() != null && !IndexFieldType.Default.equals(indexField.dataType())) {
                            fieldInfo.setType(indexField.dataType().getDataType());
                        }
                        if (StringUtils.isNotBlank(indexField.name())) {
                            fieldInfo.setName(indexField.name());
                        }
                        fieldInfo.setStore(indexField.store());
                    }
                    fieldTypeInfos.put(item.getName(), fieldInfo);

                }
            }
            this.indexClass = this.indexClass.getSuperclass();
        }

    }

    public Class<?> getIndexClass() {
        return indexClass;
    }

    public void setIndexClass(Class<?> indexClass) {
        this.indexClass = indexClass;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIndexType() {
        return indexType;
    }

    public void setIndexType(String indexType) {
        this.indexType = indexType;
    }

    public String getIndexIdName() {
        return indexIdName;
    }

    public void setIndexIdName(String indexIdName) {
        this.indexIdName = indexIdName;
    }

    public Map<String, FieldInfo> getFieldTypeInfos() {
        return fieldTypeInfos;
    }

    public void setFieldTypeInfos(Map<String, FieldInfo> fieldTypeInfos) {
        this.fieldTypeInfos = fieldTypeInfos;
    }

    public static void main(String[] args) {
        IndexMappingModel model = new IndexMappingModel(DianpuIndex.class);
        model.initField();
        System.out.println("");
    }
}
