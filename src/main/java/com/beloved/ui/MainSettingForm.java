package com.beloved.ui;

import com.beloved.listener.MyVirtualFileListener;
import com.beloved.state.SettingsStorage;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 11:38
 * @Description:
 */
public class MainSettingForm implements Configurable, BaseSettings {
    private JPanel mainPanel;
    private JCheckBox autoSync;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "FA";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        this.loadSettingsStore();
        return mainPanel;
    }

    @Override
    public boolean isModified() {
        if (this.autoSync.isSelected() != getSettingsStorage().getAutoSync()) {
            return true;
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        getSettingsStorage().setAutoSync(autoSync.isSelected());
        if (autoSync.isSelected()) {
            VirtualFileManager.getInstance().addVirtualFileListener(MyVirtualFileListener.getInstance());
        } else {
            VirtualFileManager.getInstance().removeVirtualFileListener(MyVirtualFileListener.getInstance());
        }
    }

    @Override
    public void loadSettingsStore(@NotNull SettingsStorage settingsStorage) {
        if (settingsStorage.getAutoSync()) {
            this.autoSync.setSelected(settingsStorage.getAutoSync());
        }
    }
}
