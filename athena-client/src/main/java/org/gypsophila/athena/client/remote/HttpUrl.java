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

/**
 * @author lixiaoshuang
 */
public class HttpUrl {
    
    private static final String BASE = "http://";
    
    private static final String SERVER = "/athena-server";
    
    private static final String VERSION = "/v1";
    
    private static final String REGISTER = SERVER + VERSION + "/register/center/add";
    
    private static final String CANCEL = SERVER + VERSION + "/register/center/remote";
    
    private static final String INSTANCE = SERVER + VERSION + "/register/center/get";
    
    
    public static String getRegisterUrl(String ip, int port) {
        return BASE + ip + ":" + port + REGISTER;
    }
    
    public static String getCancelUrl(String ip, int port) {
        return BASE + ip + ":" + port + CANCEL;
    }
    
    public static String getInstanceUrl(String ip, int port) {
        return BASE + ip + ":" + port + INSTANCE;
    }
}
