package com.beloved.service;

import com.beloved.state.SettingsStorage;
import com.beloved.utils.ProjectUtils;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 12:22
 * @Description:
 */
public interface SettingsStorageService extends PersistentStateComponent<SettingsStorage> {

    static SettingsStorageService getInstance(Project project) {
        return ServiceManager.getService(project, SettingsStorageService.class);
    }

    static SettingsStorage getSettingsStorage(Project project) {
        return getInstance(project).getState();
    }

    static SettingsStorage getSettingsStorage() {
        return getInstance(ProjectUtils.getCurrProject()).getState();
    }
    
    
}
