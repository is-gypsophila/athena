package org.gypsophila.athena.spi;


import org.junit.Test;

import java.util.Map;

public class AthenaServiceLoaderTest {

    @Test
    public void load() throws Exception {
        AthenaServiceLoader instance = AthenaServiceLoader.instance();

        DemoSpiImpl oneService = instance.load(DemoSpi.class, DemoSpi.class.getClassLoader()).getOneService(DemoSpiImpl.class);
        oneService.test();

        Map<String, DemoSpi> allService = instance.load(DemoSpi.class, DemoSpi.class.getClassLoader()).getAllService(DemoSpi.class);
        for (Map.Entry<String, DemoSpi> stringDemoSpiEntry : allService.entrySet()) {
            DemoSpi demoSpi = stringDemoSpiEntry.getValue();
            demoSpi.test();
        }
    }
}