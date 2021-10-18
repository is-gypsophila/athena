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

package org.gypsophila.athena.client.api.impl;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.gypsophila.athena.client.api.RegisterService;
import org.gypsophila.athena.client.heartbeat.HeartBeatTask;
import org.gypsophila.athena.client.remote.AthenaRemoteCallTemplate;
import org.gypsophila.athena.client.remote.HttpUrl;
import org.gypsophila.athena.client.remote.JdkHttpClient;
import org.gypsophila.athena.common.constant.AthenaConstant;
import org.gypsophila.athena.common.enums.ErrorCode;
import org.gypsophila.athena.common.exception.AthenaException;
import org.gypsophila.athena.common.pojo.Response;
import org.gypsophila.athena.common.util.AthenaExecutor;
import org.gypsophila.athena.common.util.AthenaTaskRun;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author lixiaoshuang
 */
public class AthenaRegisterService implements RegisterService {
    
    private final String serverIp;
    
    private final int serverPort;
    
    private AthenaRemoteCallTemplate athenaRemoteCallTemplate = new JdkHttpClient();
    
    public AthenaRegisterService(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }
    
    @Override
    public void register(String serviceName, String ip, int port) throws IOException {
        this.registerInstance(serviceName, serviceName, ip, port);
    }
    
    @Override
    public void register(String namespace, String serviceName, String ip, int port) throws IOException {
        this.registerInstance(namespace, serviceName, ip, port);
    }
    
    private void registerInstance(String namespace, String serviceName, String ip, int port) throws IOException {
        this.checkParam(namespace, serviceName, ip, port);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put(AthenaConstant.NAMESPACE, namespace);
        paramMap.put(AthenaConstant.SERVICE_NAME, serviceName);
        paramMap.put(AthenaConstant.IP, ip);
        paramMap.put(AthenaConstant.PORT, port);
        Response<Void> response = athenaRemoteCallTemplate
                .doPost(HttpUrl.getRegisterUrl(serverIp, serverPort), paramMap, Void.class);
        if (response.getCode() != 0) {
            throw new AthenaException(ErrorCode.REGISTER_FAIL.getCode(), ErrorCode.REGISTER_FAIL.getMessage());
        }
        HeartBeatTask heartBeatTask = new HeartBeatTask(namespace, serviceName, ip, port,
                HttpUrl.getHeartBeatUrl(serverIp, serverPort));
        AthenaTaskRun.runTask(heartBeatTask);
    }
    
    private void checkParam(String namespace, String serviceName, String ip, int port) {
        if (StringUtils.isBlank(namespace)) {
            throw new AthenaException(ErrorCode.NAMESPACE_NULL.getCode(), ErrorCode.NAMESPACE_NULL.getMessage());
        }
        if (StringUtils.isBlank(serviceName)) {
            throw new AthenaException(ErrorCode.SERVICE_NAME_NULL.getCode(), ErrorCode.SERVICE_NAME_NULL.getMessage());
        }
        if (StringUtils.isBlank(ip)) {
            throw new AthenaException(ErrorCode.IP_NULL.getCode(), ErrorCode.IP_NULL.getMessage());
        }
        if (port <= 0) {
            throw new AthenaException(ErrorCode.PORT_INVALID.getCode(), ErrorCode.PORT_INVALID.getMessage());
        }
    }
    
    @Override
    public void cancel(String namespace, String serviceName, String ip, int port) throws IOException {
        this.checkParam(namespace, serviceName, ip, port);
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put(AthenaConstant.NAMESPACE, namespace);
        paramMap.put(AthenaConstant.SERVICE_NAME, serviceName);
        paramMap.put(AthenaConstant.IP, ip);
        paramMap.put(AthenaConstant.PORT, port);
        Response<Void> response = athenaRemoteCallTemplate
                .doPost(HttpUrl.getCancelUrl(serverIp, serverPort), paramMap, Void.class);
        if (response.getCode() != 0) {
            throw new AthenaException(ErrorCode.CANCEL_FAIL.getCode(), ErrorCode.CANCEL_FAIL.getMessage());
        }
    }
    
    @Override
    public Set instanceList(String namespace, String serviceName) throws IOException {
        if (StringUtils.isBlank(namespace)) {
            throw new AthenaException(ErrorCode.NAMESPACE_NULL.getCode(), ErrorCode.NAMESPACE_NULL.getMessage());
        }
        if (StringUtils.isBlank(serviceName)) {
            throw new AthenaException(ErrorCode.SERVICE_NAME_NULL.getCode(), ErrorCode.SERVICE_NAME_NULL.getMessage());
        }
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put(AthenaConstant.NAMESPACE, namespace);
        paramMap.put(AthenaConstant.SERVICE_NAME, serviceName);
        Response<Set> response = athenaRemoteCallTemplate
                .doPost(HttpUrl.getInstanceUrl(serverIp, serverPort), paramMap, Set.class);
        if (response.getCode() != 0) {
            throw new AthenaException(ErrorCode.CANCEL_FAIL.getCode(), ErrorCode.CANCEL_FAIL.getMessage());
        }
        return response.getData();
    }
}
