package com.sodyu.elasticsearch.models;

/**
 * Created by yuhp on 2017/10/9.
 */
public class FieldInfo {
    private String name;
    private String type;
    private String value;
    private boolean store;
    private boolean id;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

}
