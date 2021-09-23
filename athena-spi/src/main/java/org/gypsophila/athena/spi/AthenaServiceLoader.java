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
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Athena spi service loader.
 *
 * @author lixiaoshuang
 */
public class AthenaServiceLoader {

    private static final String PREFIX = "athena/";

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
    public AthenaServiceLoader load(Class<?> clazz, ClassLoader classLoader) throws IOException {
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
        return this;
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
                synchronized (SERVICES) {
                    Class<?> aClass = Class.forName((String) k);
                    ConcurrentHashMap<String, Class<?>> classConcurrentHashMap = SERVICES.get(name);
                    if (null == classConcurrentHashMap) {
                        classConcurrentHashMap = new ConcurrentHashMap<>();
                        SERVICES.put(name, classConcurrentHashMap);
                    }
                    classConcurrentHashMap.putIfAbsent((String) k, aClass);
                }
            } catch (ClassNotFoundException e) {
                throw new AthenaLoaderException("load class error");
            }
        });
    }

    /**
     * Gets an instance of the specified implementation class
     *
     * @param implClass
     * @param <R>
     * @return
     * @throws Exception
     */
    public <R> R getOneService(Class<R> implClass) throws Exception {
        if (Objects.isNull(implClass)) {
            return null;
        }
        if (implClass.isInterface()) {
            throw new AthenaLoaderException("Specific implementation classes need to be specified");
        }
        String interfaceName = implClass.getInterfaces().length > 0 ? implClass.getInterfaces()[0].getName() : "";
        ConcurrentHashMap<String, Class<?>> instanceMap = SERVICES.get(interfaceName);
        if (null == instanceMap) {
            return null;
        }
        Class<?> aClass = instanceMap.get(implClass.getName());
        if (null == aClass) {
            return null;
        }
        Constructor<R> constructor = (Constructor<R>) aClass.getConstructor();
        return constructor.newInstance();
    }

    /**
     * Gets instances of all implementation classes
     *
     * @param interfaceClass
     * @param <R>
     * @return
     * @throws Exception
     */
    public <R> Map<String, R> getAllService(Class<R> interfaceClass) throws Exception {
        if (Objects.isNull(interfaceClass)) {
            return null;
        }
        if (!interfaceClass.isInterface()) {
            throw new AthenaLoaderException("The service interface needs to be specified");
        }
        Map<String, Class<?>> instanceMap = SERVICES.get(interfaceClass.getName());
        if (null == instanceMap) {
            return null;
        }
        Map<String, R> resultMap = new HashMap<>();
        for (Map.Entry<String, Class<?>> entry : instanceMap.entrySet()) {
            String key = entry.getKey();
            Class<?> value = entry.getValue();
            Constructor<R> constructor = (Constructor<R>) value.getConstructor();
            resultMap.put(key, constructor.newInstance());
        }
        return resultMap;
    }

}

