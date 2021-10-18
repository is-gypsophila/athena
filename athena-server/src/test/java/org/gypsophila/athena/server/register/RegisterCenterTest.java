package org.gypsophila.athena.server.register;

import org.gypsophila.athena.common.pojo.Instance;
import org.gypsophila.athena.common.pojo.Service;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;


/**
 * @author lixiaoshuang
 */
public class RegisterCenterTest {
    
    
    @Test
    public void build() {
        RegisterCenter center = RegisterCenter.single();
        Assert.assertNotNull(center);
    }
    
    @Test
    public void register() {
        Service service = new Service("namespace", "athena", true,
                new Instance("127.0.0.1", 8888, System.currentTimeMillis()));
        RegisterCenter.single().register(service);
        
        Set<Instance> instanceSet = RegisterCenter.single().instanceList("namespace", "athena");
        Assert.assertNotNull(instanceSet);
    }
    
    @Test
    public void cancel() {
        Service service = new Service("namespace", "athena", true,
                new Instance("127.0.0.1", 8888, System.currentTimeMillis()));
        Service service2 = new Service("namespace", "athena", true,
                new Instance("127.0.0.2", 9999, System.currentTimeMillis()));
        
        RegisterCenter.single().register(service);
        RegisterCenter.single().register(service2);
        
        RegisterCenter.single().cancel(service);
        
        Set<Instance> instanceSet = RegisterCenter.single().instanceList("namespace", "athena");
        
        Assert.assertTrue(instanceSet.contains(new Instance("127.0.0.2", 9999, System.currentTimeMillis())));
    }
    
    @Test
    public void instanceList() {
        Service service = new Service("namespace", "athena", true,
                new Instance("127.0.0.1", 8888, System.currentTimeMillis()));
        RegisterCenter.single().register(service);
        
        Set<Instance> instanceSet = RegisterCenter.single().instanceList("namespace", "athena");
        Assert.assertNotNull(instanceSet);
    }
}