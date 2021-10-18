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

package org.gypsophila.athena.server.health;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.gypsophila.athena.common.pojo.AthenaTask;
import org.gypsophila.athena.common.pojo.Instance;
import org.gypsophila.athena.common.util.AthenaTaskRun;
import org.gypsophila.athena.server.register.RegisterCenter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author lixiaoshuang
 */
@Component
public class HealthCheckTask implements AthenaTask {
    
    public static final Log log = LogFactory.getLog(HealthCheckTask.class);
    
    public static final long MAX_RENEWAL_TIME = 5000L;
    
    @PostConstruct
    public void init() {
        AthenaTaskRun.runTask(this);
    }
    
    @Override
    public void run() {
        log.info("health check run!");
        Map<String, Map<String, Set<Instance>>> allData = RegisterCenter.single().getAllData();
        if (allData.isEmpty()) {
            return;
        }
        // TODO: 2021/10/18 修改删除方式
        for (Map.Entry<String, Map<String, Set<Instance>>> stringMapEntry : allData.entrySet()) {
            String namespace = stringMapEntry.getKey();
            Map<String, Set<Instance>> serviceMap = stringMapEntry.getValue();
            for (Map.Entry<String, Set<Instance>> stringSetEntry : serviceMap.entrySet()) {
                Iterator<Instance> iterator = stringSetEntry.getValue().iterator();
                if (iterator.hasNext()) {
                    Instance next = iterator.next();
                    if (System.currentTimeMillis() - next.getLastUpdateTime() >= MAX_RENEWAL_TIME) {
                        log.error("Instance failure removal,instance:" + JSON.toJSONString(next));
                        iterator.remove();
                    }
                }
            }
        }
    }
}
