package com.sodyu.test.elasticsearch;

import com.sodyu.elasticsearch.dal.dao.DianpuMapper;
import com.sodyu.elasticsearch.dal.entity.Dianpu;
import com.sodyu.elasticsearch.dal.entity.DianpuExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by yuhp on 2017/12/3.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-mybatis.xml"})

public class DianpuInfoTest {

    @Autowired
    DianpuMapper dianpuMapper;

    @Test
    public void getDatas() {
        DianpuExample dianpuExample = new DianpuExample();
        dianpuExample.setPageSize(100);
        dianpuExample.setPage(0);
        List<Dianpu> list = dianpuMapper.selectByExample(dianpuExample);
        for (Dianpu t : list) {
            System.out.println(t.getShopName());
        }
//        List<DianpuIndex> list=(dianpuExample);
        return ;
    }
}
