package org.gypsophila.athena.spi;

/**
 * @author lixiaoshuang
 */
public class StringSpi implements TestSpi {
    
    @Override
    public String getContent() {
        return "test spi";
    }
}
