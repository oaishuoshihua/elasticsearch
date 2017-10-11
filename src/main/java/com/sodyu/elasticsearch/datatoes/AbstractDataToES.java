package com.sodyu.elasticsearch.datatoes;

import com.sodyu.elasticsearch.indextype.BasicIndex;

import java.util.List;

/**
 * Created by yuhp on 2017/10/10.
 */
public abstract class AbstractDataToES<T extends BasicIndex> implements DataToES{

    private List<T> indexList;

    @Override
    public void full() {
        importDataToES(indexList);
    }

    @Override
    public void increment() {
        importDataToES(indexList);
    }

    public abstract boolean importDataToES(List<T> list);

    public abstract boolean importIncrDataToES(List<T> list);
}
