package com.sodyu.elasticsearch.common;

import com.sodyu.elasticsearch.models.IndexMappingModel;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;

import java.util.List;
import java.util.Map;

/**
 * Created by yuhp on 2017/11/30.
 */
public class RequestBuilder {
    private IndexMappingModel mappingModel;

    public RequestBuilder(Class<?> clazz) {
        this.mappingModel = new IndexMappingModel(clazz);
    }

    public <T> IndexRequest indexRequest(final T type) {
        IndexRequest request = new IndexRequest(mappingModel.getIndexName(), mappingModel.getIndexType(), mappingModel.getIndexIdName());
        request.source(mappingModel.source(type));
        return request;
    }

    public <T> BulkRequest indexRequest(final List<T> types) {
        BulkRequest bulk = new BulkRequest();
        for (Object type : types) {
            bulk.add(indexRequest(type));
        }
        return bulk;
    }

    public <T> DeleteRequest deleteRequest(final T type) {
        DeleteRequest request = new DeleteRequest(mappingModel.getIndexName(), mappingModel.getIndexType(), mappingModel.getIndexIdName());
        return request;
    }

    public <T> BulkRequest deleteRequest(final List<T> types) {
        BulkRequest bulk = new BulkRequest();
        for (Object type : types) {
            bulk.add(deleteRequest(type));
        }
        return bulk;
    }

    public <T> UpdateRequest updateRequest(final T type) {
        return updateRequest(type, false);
    }

    public <T> UpdateRequest updateRequest(final T type, boolean upsert) {
        Map<String, Object> source = mappingModel.source(type);
        UpdateRequest request = new UpdateRequest(mappingModel.getIndexName(), mappingModel.getIndexType(), mappingModel.getIndexIdName());
        request.doc(source);
        if (upsert) {
            request.upsert(source);
        }

        return request;
    }

    public <T> BulkRequest updateRequest(final List<T> types) {
        return updateRequest(types, false);
    }

    public <T> BulkRequest updateRequest(final List<T> types, boolean upsert) {
        BulkRequest bulk = new BulkRequest();
        for (Object type : types) {
            bulk.add(updateRequest(type, upsert));
        }
        return bulk;
    }

    public IndexMappingModel getMappingModel() {
        return mappingModel;
    }

    public void setMappingModel(IndexMappingModel mappingModel) {
        this.mappingModel = mappingModel;
    }
}
