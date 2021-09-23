package org.gypsophila.athena.spi;

/**
 * @author lixiaoshuang
 */
public class DemoSpiImpl  implements DemoSpi{
    
    @Override
    public void test() {
        System.out.println("demo spi");
    }
}
