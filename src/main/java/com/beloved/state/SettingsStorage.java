package com.beloved.state;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 12:24
 * @Description:
 */
public class SettingsStorage {
    
    private boolean autoSync;

    public SettingsStorage() {
    }

    public SettingsStorage(boolean autoSync) {
        this.autoSync = autoSync;
    }

    public boolean getAutoSync() {
        return autoSync;
    }

    public void setAutoSync(boolean autoSync) {
        this.autoSync = autoSync;
    }
}
