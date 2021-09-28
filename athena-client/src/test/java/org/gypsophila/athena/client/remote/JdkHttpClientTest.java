package org.gypsophila.athena.client.remote;

import org.gypsophila.athena.common.pojo.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lixiaoshuang
 */
public class JdkHttpClientTest {
    
    @org.junit.Test
    public void doGet() throws IOException {
        JdkHttpClient jdkHttpClient = new JdkHttpClient();
        Map<String,Object> paramMap = new HashMap<>();
        Response<String> typeResponse = jdkHttpClient.doGet("", paramMap, String.class);
    }
    
    @org.junit.Test
    public void doPost() {
    }
}