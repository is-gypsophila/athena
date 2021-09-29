/*
 * Copyright 2021 Gypsophila open source organization.
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gypsophila.athena.client.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.gypsophila.athena.common.pojo.Response;
import org.gypsophila.athena.common.util.ResponseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lixiaoshuang
 */
public class JdkHttpClient implements AthenaRemoteCallTemplate {
    
    private int connectTimeout = 5000;
    
    private int readTimeout = 10000;
    
    
    @Override
    public <T> Response<T> doGet(String url, Map<String, Object> paramMap, Class<T> clazz) throws IOException {
        if (null != paramMap && !paramMap.isEmpty()) {
            List<String> paramList = paramMap.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.toList());
            url = url + "?" + String.join("&", paramList);
        }
        URL targetUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) targetUrl.openConnection();
        urlConnection.setConnectTimeout(connectTimeout);
        urlConnection.setReadTimeout(readTimeout);
        urlConnection.setRequestMethod(HttpMethod.GET);
        urlConnection.connect();
        
        if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return ResponseUtil.fail(urlConnection.getResponseCode(), urlConnection.getResponseMessage());
        }
        StringBuffer response = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                response.append(temp);
                response.append("\n");
            }
        } finally {
            urlConnection.disconnect();
        }
        if (StringUtils.isBlank(response)) {
            return ResponseUtil.fail(urlConnection.getResponseCode(), urlConnection.getResponseMessage());
        }
        return JSON.parseObject(response.toString(), new TypeReference<Response<T>>(clazz) {
        });
    }
    
    @Override
    public <T> Response<T> doPost(String url, Map<String, Object> paramMap, Class<T> clazz) throws IOException {
        URL targetUrl = new URL(url);
        HttpURLConnection urlConnection = (HttpURLConnection) targetUrl.openConnection();
        urlConnection.setConnectTimeout(connectTimeout);
        urlConnection.setReadTimeout(readTimeout);
        urlConnection.setRequestMethod(HttpMethod.POST);
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        urlConnection.connect();
        
        if (null != paramMap && !paramMap.isEmpty()) {
            String paramStr;
            List<String> paramList = paramMap.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.toList());
            paramStr = String.join("&", paramList);
            
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(paramStr.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
        }
        
        if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            return ResponseUtil.fail(urlConnection.getResponseCode(), urlConnection.getResponseMessage());
        }
        StringBuffer response = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(urlConnection.getInputStream(), StandardCharsets.UTF_8))) {
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                response.append(temp);
                response.append("\n");
            }
        } finally {
            urlConnection.disconnect();
        }
        if (StringUtils.isBlank(response)) {
            return ResponseUtil.fail(urlConnection.getResponseCode(), urlConnection.getResponseMessage());
        }
        return JSON.parseObject(response.toString(), new TypeReference<Response<T>>(clazz) {
        });
    }
}
