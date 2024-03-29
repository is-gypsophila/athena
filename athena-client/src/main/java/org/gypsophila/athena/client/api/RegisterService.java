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

package org.gypsophila.athena.client.api;

import org.gypsophila.athena.common.pojo.Instance;

import java.io.IOException;
import java.util.Set;

/**
 * @author lixiaoshuang
 */
public interface RegisterService {
    
    /**
     * Register an instance to the specified service
     *
     * @param serviceName service name
     * @param ip          service ip
     * @param port        service port
     */
    void register(String serviceName, String ip, int port) throws IOException;
    
    /**
     * Register the instance to the designated space and service
     *
     * @param namespace   namespace
     * @param serviceName service name
     * @param ip          service ip
     * @param port        service port
     */
    void register(String namespace, String serviceName, String ip, int port) throws IOException;
    
    /**
     * Unregister a service instance
     *
     * @param namespace   namespace
     * @param serviceName service name
     * @param ip          service ip
     * @param port        service port
     */
    void cancel(String namespace, String serviceName, String ip, int port) throws IOException;
    
    
    /**
     * Get all instances of the service
     *
     * @param namespace
     * @param serviceName
     */
    Set<Instance> instanceList(String namespace, String serviceName) throws IOException;
}
