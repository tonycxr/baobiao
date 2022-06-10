package com.sungcor.baobiao.utils;

public class LicenseModule {
    private String moduleKey;
    private boolean moduleEnable;
    private int moduleCount;

    public LicenseModule() {
    }

    public String getModuleKey() {
        return this.moduleKey;
    }

    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    public boolean getModuleEnable() {
        return this.moduleEnable;
    }

    public void setModuleEnable(boolean moduleEnable) {
        this.moduleEnable = moduleEnable;
    }

    public int getModuleCount() {
        return this.moduleCount;
    }

    public void setModuleCount(int moduleCount) {
        this.moduleCount = moduleCount;
    }
}
