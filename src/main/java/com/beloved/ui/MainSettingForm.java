package com.beloved.ui;

import com.beloved.core.FaCore;
import com.beloved.state.SettingsStorage;
import com.beloved.utils.ProjectUtils;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-23 11:38
 * @Description:
 */
public class MainSettingForm implements Configurable, BaseSettings {
    private JPanel mainPanel;
    private JCheckBox openFAPlugin;

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
        if (this.openFAPlugin.isSelected() != getSettingsStorage().getOpenFAPlugin()) {
            return true;
        }
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        getSettingsStorage().setOpenFAPlugin(openFAPlugin.isSelected());
        if (openFAPlugin.isSelected()) {
            try {
                FaCore.setProjectBuildConfigure(ProjectUtils.getCurrProject());
            } catch (IOException e) {
                openFAPlugin.setSelected(false);
                getSettingsStorage().setOpenFAPlugin(false);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void loadSettingsStore(@NotNull SettingsStorage settingsStorage) {
        if (settingsStorage.getOpenFAPlugin()) {
            this.openFAPlugin.setSelected(settingsStorage.getOpenFAPlugin());
        }
    }
}
