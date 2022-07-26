package com.beloved.ui;

import com.beloved.service.SettingsStorageService;
import com.beloved.state.SettingsStorage;
import com.intellij.openapi.options.Configurable;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 12:40
 * @Description:
 */
public interface BaseSettings extends Configurable {

    /**
     * 获取设置信息
     *
     * @return 获取设置信息
     */
    default SettingsStorage getSettingsStorage() {
        return SettingsStorageService.getSettingsStorage();
    }

    /**
     * 加载配置信息
     */
    default void loadSettingsStore() {
        this.loadSettingsStore(getSettingsStorage());
    }

    /**
     * 加载配置信息
     *
     * @param settingsStorage 配置信息
     */
    void loadSettingsStore(SettingsStorage settingsStorage);
    
}
