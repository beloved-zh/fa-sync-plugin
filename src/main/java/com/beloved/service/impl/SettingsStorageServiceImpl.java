package com.beloved.service.impl;

import com.beloved.service.SettingsStorageService;
import com.beloved.state.SettingsStorage;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 12:26
 * @Description:
 */
@State(name = "FaSetting", storages = @Storage("fa-setting.xml"))
public class SettingsStorageServiceImpl implements SettingsStorageService {

    private SettingsStorage settingsStorage = new SettingsStorage();
    
    @Nullable
    @Override
    public SettingsStorage getState() {
        return settingsStorage;
    }

    @Override
    public void loadState(@NotNull SettingsStorage state) {
        this.settingsStorage = state;
    }
}
