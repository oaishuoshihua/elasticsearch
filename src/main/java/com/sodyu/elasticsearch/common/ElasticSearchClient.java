package com.sodyu.elasticsearch.common;

import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * Created by yuhp on 2017/9/5.
 */
public class ElasticSearchClient {
    private final static String CLUSTER_NAME_FLAG = "cluster.name";
    private final static String CLUSTER_NAME = "sodyu_cluster";
    private final static String TRANSPORT_SNIFF = "client.transport.sniff";
    private volatile static TransportClient client = null;
    private static String[] ips = { "192.168.197.132"};
    public static TransportClient getElasticClient() {
        if (null == client) {
            synchronized (ElasticSearchClient.class) {
                if (null == client) {
                    try {
                        client = new PreBuiltTransportClient(getSettings());
                        for (int i = 0; i < ips.length; i++) {
                            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ips[i]), 9300));
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
        return client;
    }

    /**
     * 获取集群配置
     *
     * @return
     */
    private static Settings getSettings() {
        return Settings.builder()
                .put(TRANSPORT_SNIFF, true)
                .put(CLUSTER_NAME_FLAG, CLUSTER_NAME)
                .build();
    }


    public static IndicesAdminClient indices() {
        return getElasticClient().admin().indices();
    }

    public static ClusterAdminClient cluster() {
        return getElasticClient().admin().cluster();
    }

    public void close() {
        if (null != this.client) {
            this.client.close();
        }
    }

    public static void main(String[] args) {
        IndicesAdminClient clusterAdminClient = new ElasticSearchClient().indices();
        System.out.println(clusterAdminClient.prepareGetIndex());
    }
}
