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

package org.gypsophila.athena.server.register;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.gypsophila.athena.server.exception.BizException;
import org.gypsophila.athena.server.pojo.Instance;
import org.gypsophila.athena.server.pojo.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lixiaoshuang
 */
public class RegisterCenter {
    
    /**
     * Register node storage service
     */
    private static final Map<String, Map<String, Set<Instance>>> REGISTER_DATE = new ConcurrentHashMap<>(16);
    
    private static final RegisterCenter REGISTER_CENTER = new RegisterCenter();
    
    private final ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
    
    private final Lock readLock = reentrantReadWriteLock.readLock();
    
    private final Lock writeLock = reentrantReadWriteLock.writeLock();
    
    private RegisterCenter() {
    
    }
    
    public RegisterCenter build() {
        return REGISTER_CENTER;
    }
    
    /**
     * Register a node
     *
     * @param service
     */
    public void register(Service service) {
        checkParam(service);
        writeLock.lock();
        try {
            Map<String, Set<Instance>> serviceMap = REGISTER_DATE.get(service.getNameSpace());
            if (null == serviceMap) {
                serviceMap = new ConcurrentHashMap<>(16);
                serviceMap.put(service.getServiceName(), Sets.newHashSet(service.getInstance()));
                REGISTER_DATE.put(service.getServiceName(), serviceMap);
            } else {
                Set<Instance> instanceSet = serviceMap.get(service.getServiceName());
                if (null == instanceSet) {
                    instanceSet = Sets.newHashSet(service.getInstance());
                } else {
                    instanceSet.add(service.getInstance());
                }
                serviceMap.put(service.getServiceName(), instanceSet);
            }
        } finally {
            writeLock.unlock();
        }
    }
    
    /**
     * Parameter verification
     *
     * @param service
     */
    private void checkParam(Service service) {
        if (Objects.isNull(service)) {
            throw new BizException("service is null!");
        }
        if (StringUtils.isBlank(service.getNameSpace())) {
            throw new BizException("namespace is null!");
        }
        if (Objects.isNull(service.getInstance())) {
            throw new BizException("instance is null!");
        }
    }
    
    /**
     * Cancel a node
     *
     * @param service
     */
    public void cancel(Service service) {
        checkParam(service);
    }
    
    /**
     * Get the list of service nodes
     *
     * @param namespace
     * @param serviceName
     * @return
     */
    public List<Instance> instanceList(String namespace, String serviceName) {
        return null;
    }
    
    
}
