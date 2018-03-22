package com.Distributed;

import com.alipay.jarslink.api.Action;
import com.alipay.jarslink.api.ModuleConfig;

public class ActionImpl implements Action<ModuleConfig, ModuleConfig> {

    @Override
    public ModuleConfig execute(ModuleConfig actionRequest) {
        ModuleConfig moduleConfig = new ModuleConfig();
        moduleConfig.setName(actionRequest.getName());
        moduleConfig.setEnabled(actionRequest.getEnabled());
        moduleConfig.setVersion(actionRequest.getVersion());
        moduleConfig.setModuleUrl(actionRequest.getModuleUrl());
        moduleConfig.setProperties(actionRequest.getProperties());
        moduleConfig.setOverridePackages(actionRequest.getOverridePackages());
        System.out.println("Hello World");
        return moduleConfig;
    }

    @Override
    public String getActionName() {
        return "hello world";
    }
}
