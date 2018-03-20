package com.Distributed.Provider;

import Distributed.UserServiceImpl;
import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.mapper.api.distributed.UserService;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TestProvider {
    public static void main(String args[]) {
        UserService userService = new UserServiceImpl();
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("dubboProvider");

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("127.0.0.1:2181");
        registryConfig.setProtocol("zookeeper");

        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20880);

        ServiceConfig<UserService> serviceConfig = new ServiceConfig<UserService>();
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRef(userService);
        serviceConfig.setVersion("1.0.0.0");
        serviceConfig.setGroup("dubbo");

        serviceConfig.export();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
