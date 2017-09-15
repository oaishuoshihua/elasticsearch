package com.sodyu.elasticsearch.search;


import com.sodyu.elasticsearch.common.ElasticSearchClient;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuhp on 2017/9/11.
 */
public class queryIds {

    public static void judeId(long id,File exist,File noexist){


        SearchRequestBuilder searchRequestBuilder = null;
        searchRequestBuilder = ElasticSearchClient.getElasticClient().prepareSearch("ticket-1").setTypes("ticketinfo"); //NOSONAR
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("scenicSpotId", id));
        searchRequestBuilder.setQuery(boolQueryBuilder);
        searchRequestBuilder.setSize(1000);
        SearchResponse searchResponse = searchRequestBuilder.get(new TimeValue(10, TimeUnit.SECONDS));
        if (searchResponse != null && searchResponse.getHits() != null) {
            for (SearchHit hit : searchResponse.getHits()) {
                if (hit == null) {
                    System.out.println(id);
                    continue;
                }
                Map<String, Object> source = hit.getSource();
                if(source!=null&&String.valueOf(hit.getSource().get("scenicSpotId")).equals(String.valueOf(id))){
                    List<String> ids=new ArrayList<String>();
                    ids.add(String.valueOf(id));
                    try {
                        FileUtils.writeLines(exist,ids, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    List<String> ids=new ArrayList<String>();
                    ids.add(String.valueOf(id));
                    try {
                        FileUtils.writeLines(noexist,ids, true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }




//    public static void main(String[] args) {
//        File file = new File(queryIds.class.getClassLoader().getResource("exist.txt").getPath());
//        File file1 = new File(queryIds.class.getClassLoader().getResource("notExist.txt").getPath());
//        List<Long> ids=new ArrayList<Long>();
//        int page=0;
//        int pagesize=1000;
//        SrhViewspotsearchMapper mapper= DalUtil.getMapper(SrhViewspotsearchMapper.class);
//        ids=mapper.getSids(page,pagesize);
//        while(ids!=null&&ids.size()>0){
//            for (int i = 0; i <ids.size() ; i++) {
//                judeId(ids.get(i),file,file1);
//            }
//            System.out.println("第"+page+"页结束");
//            page++;
//            ids=mapper.getSids(page,pagesize);
//        }
//        System.out.println("结束");
//    }
}
