package com.beloved.action;

import com.beloved.core.FaCore;
import com.beloved.utils.PushUtils;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;

public class SyncFileAction extends AnAction {

    
    @Override
    public void actionPerformed(AnActionEvent event) {

        final VirtualFile file = CommonDataKeys.VIRTUAL_FILE.getData(event.getDataContext());

        if (FaCore.judgeWebFile(file.getPath())) {
            if (file.isDirectory()) {
                VirtualFile[] files = file.getChildren();
                for (VirtualFile virtualFile : files) {
                    FaCore.fileSync(virtualFile.getPath());
                }
                PushUtils.pushMessage("文件夹{" + file.getName() +"}同步成功", NotificationType.INFORMATION);
            } else {
                FaCore.fileSync(file.getPath());
                PushUtils.pushMessage("文件{" + file.getName() +"}同步成功", NotificationType.INFORMATION);
            }
        } else {
            PushUtils.pushMessage("目前无法操作非web资源文件", NotificationType.WARNING);
        }
    }
}
