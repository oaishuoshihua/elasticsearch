package com.sodyu.elasticsearch.service.dianpu;

import com.sodyu.elasticsearch.dal.dao.DianpuMapper;
import com.sodyu.elasticsearch.dal.entity.Dianpu;
import com.sodyu.elasticsearch.dal.entity.DianpuExample;
import com.sodyu.elasticsearch.indexmodel.DianpuIndex;
import com.sodyu.elasticsearch.service.AbstractDataToES;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuhp on 2017/11/30.
 */
@Component
public class DianpuDataToES extends AbstractDataToES<DianpuIndex> {
    private static final Logger logger = LoggerFactory.getLogger(DianpuDataToES.class);

    @Autowired
    DianpuMapper dianpuMapper;


    @Override
    public List<DianpuIndex> getFullDataList(int page, int pageSize) {
        List<DianpuIndex> list = null;
        try {
            DianpuExample dianpuExample = new DianpuExample();
            dianpuExample.setPage(page);
            dianpuExample.setPageSize(pageSize);
            List<Dianpu> dianpus = dianpuMapper.selectByExample(dianpuExample);
            list = transformData(dianpus);
        } catch (Exception e) {
            logger.error("获取店铺第{}页数据出错！", page, e);
        }
        return list;
    }

    @Override
    public List<DianpuIndex> getIncrDataList(int page, int pageSize) {
        //增量增加时间戳，待实现
        return null;
    }

    private List<DianpuIndex> transformData(List<Dianpu> list) {
        logger.info("开始由实体Dianpu转为DianpuIndex");
        List<DianpuIndex> result = null;
        if (CollectionUtils.isEmpty(list)) {
            logger.info("获取Dianpu数据为空");
            return result;
        }
        result = new ArrayList<DianpuIndex>();
        for (Dianpu entity : list) {
            DianpuIndex dianpuIndex = new DianpuIndex();
            dianpuIndex.setId(entity.getId());
            dianpuIndex.setShopName(entity.getShopName());
            dianpuIndex.setCategory(entity.getCategory());
            dianpuIndex.setShopId(entity.getShopId());
//            dianpuIndex.setUid(entity.getUid());
//            dianpuIndex.setSaleCount(entity.getSaleCount());
//            dianpuIndex.setProductCount(entity.getProductCount());
            dianpuIndex.setShopRealUrl(entity.getShopRealUrl());
            dianpuIndex.setTelephone(entity.getTelephone());
            dianpuIndex.setLocation(entity.getLocation());
//            dianpuIndex.setGoodratePercent(entity.getGoodratePercent());
            dianpuIndex.setDestination(entity.getDestination());
            dianpuIndex.setDataChange_CreateTime(new Date().getTime());
            dianpuIndex.setDataChange_LastTime(new Date().getTime());

            result.add(dianpuIndex);
        }
        logger.info("由实体Dianpu转为DianpuIndex结束");
        return result;
    }

}
