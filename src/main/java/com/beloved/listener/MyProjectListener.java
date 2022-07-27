package com.beloved.listener;

import com.beloved.core.FaCore;
import com.beloved.service.SettingsStorageService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
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
        
        if (SettingsStorageService.getSettingsStorage(project).getOpenFAPlugin()) {
            try {
                FaCore.setProjectBuildConfigure(project);
            } catch (IOException e) {
                e.printStackTrace();
            } 
        }
        // https://plugins.jetbrains.com/docs/intellij/virtual-file-system.html#virtual-file-system-events
        // applicationListeners级别，所以需要在监听器内过滤无关事件   ProjectFileIndex.isInContent()
//        VirtualFileManager.getInstance().addVirtualFileListener(MyVirtualFileListener.getInstance());
    }
    
}
