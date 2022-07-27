package com.beloved.state;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 12:24
 * @Description:
 */
public class SettingsStorage {
    
    private boolean openFAPlugin;

    public SettingsStorage() {
    }

    public SettingsStorage(boolean openFAPlugin) {
        this.openFAPlugin = openFAPlugin;
    }

    public boolean getOpenFAPlugin() {
        return openFAPlugin;
    }

    public void setOpenFAPlugin(boolean openFAPlugin) {
        this.openFAPlugin = openFAPlugin;
    }
}
