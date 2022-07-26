package com.beloved.listener;

import com.beloved.core.FaCore;
import com.beloved.service.SettingsStorageService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-25 08:52
 * @Description:
 */
public class MyProjectListener implements ProjectManagerListener {
    
    @Override
    public void projectOpened(@NotNull Project project) {
        
        try {
            FaCore.setProjectBuildConfigure(project);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (SettingsStorageService.getSettingsStorage().getAutoSync()) {
            VirtualFileManager.getInstance().addVirtualFileListener(MyVirtualFileListener.getInstance());
        }
    }
    
}
