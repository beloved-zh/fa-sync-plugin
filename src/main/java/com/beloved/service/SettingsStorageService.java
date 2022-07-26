package com.beloved.service;

import com.beloved.state.SettingsStorage;
import com.beloved.utils.ProjectUtils;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 12:22
 * @Description:
 */
public interface SettingsStorageService extends PersistentStateComponent<SettingsStorage> {

    static SettingsStorageService getInstance() {
        return ServiceManager.getService(ProjectUtils.getCurrProject(), SettingsStorageService.class);
    }

    static SettingsStorage getSettingsStorage() {
        return getInstance().getState();
    }
}
