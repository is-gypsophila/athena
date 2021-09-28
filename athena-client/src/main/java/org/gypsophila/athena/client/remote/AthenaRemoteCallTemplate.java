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

import org.gypsophila.athena.common.pojo.Response;

import java.io.IOException;
import java.util.Map;

/**
 * @author lixiaoshuang
 */
public interface AthenaRemoteCallTemplate {
    
    /**
     * Execute get request
     *
     * @param url      target address
     * @param paramMap parameter
     * @return
     */
    <T> Response<T> doGet(String url, Map<String, Object> paramMap, Class<T> clazz) throws IOException;
    
    /**
     * Execute post request
     *
     * @param url
     * @param paramMap
     * @return
     */
    <T> Response<T> doPost(String url, Map<String, Object> paramMap, Class<T> clazz) throws IOException;
}
