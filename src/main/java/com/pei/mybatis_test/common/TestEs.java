package com.pei.mybatis_test.common;

import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName TestEs
 * @Deacription
 * @Author peipei
 * @Date 2020/6/3 11:32
 * @Version 1.0
 **/

public class TestEs {
    public final String HOST = "192.168.189.129";
    // http请求的端口是9200，客户端是9300
    public final int PORT = 9300;
    //创建索引
    public void addIndex() throws UnknownHostException {
        //设置
        Settings settings = Settings.builder().build();
//        Settings settings = Settings.builder().put("cluster.name", "my-application").build();//默认集群名称无
        // 创建client
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddresses(new TransportAddress(InetAddress.getByName(HOST), PORT));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderNo", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "001");
        jsonObject.put("orderName", "购买元宝");
        jsonObject.put("orderTime", new Date());
        jsonObject.put("price", 1.5);
        jsonObject.put("ip", "192.168.1.111");

        IndexResponse response = client.prepareIndex("rxpay", "order").setSource(jsonObject.toString(), XContentType.JSON).get();
        System.out.println("索引名称：" + response.getIndex());
        System.out.println("类型：" + response.getType());
        System.out.println("文档ID：" + response.getId()); // 第一次使用是1
        System.out.println("当前实例状态：" + response.status());
        //关闭连接
        if (client != null) {
            client.close();
        }
    }
@Test
    public static void main(String[] args) throws UnknownHostException {
        TestEs es = new TestEs();
        es.addIndex();
    }
}
