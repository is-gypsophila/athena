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

package org.gypsophila.athena.spi;


import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Athena spi service loader.
 */
public class AthenaServiceLoader {
    
    private static final String PREFIX = "META-INF/athena/";
    
    private static final AthenaServiceLoader INSTANCE = new AthenaServiceLoader();
    
    private static final Map<String, ConcurrentHashMap<String, Class<?>>> SERVICES = new ConcurrentHashMap<>();
    
    
    private AthenaServiceLoader() {
    }
    
    /**
     * Getting instance
     *
     * @return AthenaLoader
     */
    public static AthenaServiceLoader instance() {
        return INSTANCE;
    }
    
    
    /**
     * Load interface implementation class.
     *
     * @param clazz
     * @param classLoader
     * @throws IOException
     */
    public void load(Class<?> clazz, ClassLoader classLoader) throws IOException {
        if (null == classLoader) {
            classLoader = Thread.currentThread().getContextClassLoader();
        }
        if (!clazz.isInterface()) {
            throw new AthenaLoaderException("clazz is not interface!");
        }
        String clazzName = clazz.getName();
        Enumeration<URL> resources = classLoader.getResources(PREFIX + clazzName);
        if (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            loadResource(url, clazzName);
        }
    }
    
    private void loadResource(URL url, String name) throws IOException {
        if (null == url) {
            return;
        }
        Properties properties = new Properties();
        properties.load(url.openStream());
        if (properties.size() == 0) {
            return;
        }
        properties.forEach((k, v) -> {
            try {
                Class<?> aClass = Class.forName((String) k);
                ConcurrentHashMap<String, Class<?>> classConcurrentHashMap = SERVICES.get(name);
                if (null == classConcurrentHashMap){
                    classConcurrentHashMap = new ConcurrentHashMap<>();
                    SERVICES.put(name,classConcurrentHashMap);
                }
                classConcurrentHashMap.putIfAbsent((String) k,aClass);
            } catch (ClassNotFoundException e) {
                throw new AthenaLoaderException("load class error");
            }
        });
    }
    
    public void initializeLoad() {
    
    }
    
    public static void main(String[] args) throws IOException {
        AthenaServiceLoader.instance().load(DemoSpi.class, DemoSpi.class.getClassLoader());
        AthenaServiceLoader.instance().load(TestSpi.class, TestSpi.class.getClassLoader());
    }
    
}

