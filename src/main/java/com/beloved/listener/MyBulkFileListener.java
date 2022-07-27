package com.beloved.listener;

import com.beloved.core.FaCore;
import com.beloved.service.SettingsStorageService;
import com.beloved.utils.ProjectUtils;
import com.beloved.utils.PushUtils;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.impl.ProjectFileIndexImpl;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-27 10:19
 * @Description:
 */
public class MyBulkFileListener implements BulkFileListener {

    @Override
    public void after(@NotNull List<? extends VFileEvent> events) {
        if (!SettingsStorageService.getSettingsStorage().getOpenFAPlugin()) {
            return;
        }
        for (VFileEvent event : events) {

            VirtualFile file = event.getFile();

            ProjectFileIndex projectFileIndex = new ProjectFileIndexImpl(ProjectUtils.getCurrProject());
            if(!projectFileIndex.isInContent(file)) {
                return;
            }
            
            if (FaCore.judgeWebFile(file.getPath())) {
                FaCore.fileSync(file.getPath());
                PushUtils.pushMessage("文件{" + file.getName() +"}同步成功", NotificationType.INFORMATION);
            }
        }
    }
    
}
