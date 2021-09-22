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

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Athena spi service loader.
 */
public class AthenaLoader {

    private  static final String PREFIX = "META-INF/athena/";

    private static final AthenaLoader INSTANCE = new AthenaLoader();

    private static final Map<Class<?>, Collection<Class<?>>> SERVICES = new ConcurrentHashMap<>();


    private AthenaLoader() {
    }

    /**
     * Getting instance
     *
     * @return AthenaLoader
     */
    public static AthenaLoader instance() {
        return INSTANCE;
    }


    public void load(Class<?> clazz,ClassLoader classLoader){

    }

    public void initializeLoad(){

    }
}

