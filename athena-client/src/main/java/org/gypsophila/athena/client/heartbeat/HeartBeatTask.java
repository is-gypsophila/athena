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

package org.gypsophila.athena.client.heartbeat;

import com.google.common.collect.Maps;
import org.gypsophila.athena.client.remote.AthenaRemoteCallTemplate;
import org.gypsophila.athena.client.remote.JdkHttpClient;
import org.gypsophila.athena.common.constant.AthenaConstant;
import org.gypsophila.athena.common.pojo.AthenaTask;
import org.gypsophila.athena.common.pojo.Response;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author lixiaoshuang
 */
public class HeartBeatTask implements AthenaTask {
    
    
    private static final Logger log = Logger.getLogger("HeartBeatTask");
    
    public String namespace;
    
    private String serviceName;
    
    private String ip;
    
    private int port;
    
    private String heartBeatUrl;
    
    private AthenaRemoteCallTemplate athenaRemoteCallTemplate = new JdkHttpClient();
    
    public HeartBeatTask(String namespace, String serviceName, String ip, int port, String heartBeatUrl) {
        this.namespace = namespace;
        this.serviceName = serviceName;
        this.ip = ip;
        this.port = port;
        this.heartBeatUrl = heartBeatUrl;
    }
    
    @Override
    public void run() {
        log.info("send heartbeat !");
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put(AthenaConstant.NAMESPACE, namespace);
        paramMap.put(AthenaConstant.SERVICE_NAME, serviceName);
        paramMap.put(AthenaConstant.IP, ip);
        paramMap.put(AthenaConstant.PORT, port);
        try {
            Response<Void> response = athenaRemoteCallTemplate.doPost(heartBeatUrl, paramMap, Void.class);
            if (response.getCode() != 0) {
                log.severe("send heartbeat fail");
            }
        } catch (IOException e) {
            log.severe("send heartbeat error:" + e);
        }
    }
}
