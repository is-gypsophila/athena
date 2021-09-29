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

import org.apache.commons.lang3.StringUtils;
import org.gypsophila.athena.client.api.RegisterService;
import org.gypsophila.athena.client.remote.AthenaRemoteCallTemplate;
import org.gypsophila.athena.client.remote.JdkHttpClient;
import org.gypsophila.athena.common.enums.ErrorCode;
import org.gypsophila.athena.common.exception.AthenaException;

/**
 * @author lixiaoshuang
 */
public class AthenaRegisterService implements RegisterService {
    
    private final boolean defaultHealth = true;
    
    private AthenaRemoteCallTemplate athenaRemoteCallTemplate = new JdkHttpClient();
    
    
    @Override
    public void register(String serviceName, String ip, int port) {
        registerInstance(serviceName, serviceName, ip, port);
    }
    
    @Override
    public void register(String namespace, String serviceName, String ip, int port) {
        registerInstance(namespace, serviceName, ip, port);
    }
    
    
    private void registerInstance(String namespace, String serviceName, String ip, int port) {
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
//        athenaRemoteCallTemplate.doPost();
    }
}
