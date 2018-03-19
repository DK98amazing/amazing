package Distributed.Consumer;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.mapper.api.distributed.UserService;

public class TestConsumer {
    public static void main(String args[]) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubboConsumer");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("127.0.0.1:2181");
        registryConfig.setProtocol("zookeeper");

        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<UserService>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(UserService.class);
        referenceConfig.setVersion("1.0.0.0");
        referenceConfig.setGroup("dubbo");

        UserService userService = referenceConfig.get();
        System.out.println(userService.sayHello("哈哈哈"));
    }
}
