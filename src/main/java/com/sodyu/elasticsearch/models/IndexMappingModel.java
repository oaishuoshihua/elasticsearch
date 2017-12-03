package com.sodyu.elasticsearch.models;

import com.sodyu.elasticsearch.annotation.Index;
import com.sodyu.elasticsearch.annotation.IndexField;
import com.sodyu.elasticsearch.annotation.IndexId;
import com.sodyu.elasticsearch.enums.IndexFieldType;
import com.sodyu.elasticsearch.indexmodel.DianpuIndex;
import com.sodyu.elasticsearch.util.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuhp on 2017/10/9.
 */
public class IndexMappingModel {
    private static final Logger logger = LoggerFactory.getLogger(IndexMappingModel.class);
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
            String version = ConfigUtil.getValue(this.indexClass.getSimpleName());
            if (version == null) {
                version = "";
            }
            if (null != name && !name.isEmpty()) {
                this.indexName = name + version;
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

    /**
     * 类型数据
     *
     * @param type
     * @return
     */
    public Map<String, Object> source(final Object type) {
        final Map<String, Object> src = new HashMap<String, Object>();
        String name = null;
        Object value = null;
        for (Map.Entry<String, FieldInfo> item : this.fieldTypeInfos.entrySet()) {
            name = getName(item);
            value = getValue(type, item);
            if (null != value) {
                src.put(name, value);
            }
        }
        return src;
    }

    private String getName(final Map.Entry<String, FieldInfo> item) {
        String name = item.getValue().getName();
        return name.isEmpty() ? item.getKey() : name;
    }

    private Object getValue(final Object type, Map.Entry<String, FieldInfo> item) {
        Object value = null;
        try {
            Field field = type.getClass().getDeclaredField(item.getKey());
            value = field.get(type);
        } catch (Exception e) {
            logger.error("indexMappingModel getValue error", e);
        }

        return value;
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
