package com.beloved.listener;

import com.beloved.core.FaCore;
import com.beloved.service.SettingsStorageService;
import com.beloved.utils.PushUtils;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.vfs.*;
import org.jetbrains.annotations.NotNull;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-24 15:06
 * @Description:
 */
public class MyVirtualFileListener implements VirtualFileListener {
    
    private static MyVirtualFileListener listener = new MyVirtualFileListener();
    
    private MyVirtualFileListener(){}

    public static MyVirtualFileListener getInstance(){
        return listener;
    }

    /**
     * 文件属性变更 如：修改文件名
     * @param event
     */
    @Override
    public void propertyChanged(@NotNull VirtualFilePropertyEvent event) {
    }

    /**
     * 修改文件
     * @param event
     */
    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
        if (!SettingsStorageService.getSettingsStorage().getOpenFAPlugin()) {
            return;
        }
        String filePath = event.getFile().getPath();
        if (FaCore.judgeWebFile(filePath)) {
            FaCore.fileSync(filePath);
            PushUtils.pushMessage("文件{" + event.getFileName() +"}同步成功", NotificationType.INFORMATION);
        }
    }

    /**
     * 文件创建
     * @param event
     */
    @Override
    public void fileCreated(@NotNull VirtualFileEvent event) {
    }

    /**
     * 文件删除
     * @param event
     */
    @Override
    public void fileDeleted(@NotNull VirtualFileEvent event) {
    }

    /**
     * 文件移动
     * @param event
     */
    @Override
    public void fileMoved(@NotNull VirtualFileMoveEvent event) {
    }

    /**
     * 文件复制
     * @param event
     */
    @Override
    public void fileCopied(@NotNull VirtualFileCopyEvent event) {
    }

   
    @Override
    public void beforePropertyChange(@NotNull VirtualFilePropertyEvent event) {
    }

    
    @Override
    public void beforeContentsChange(@NotNull VirtualFileEvent event) {
    }
    
    @Override
    public void beforeFileDeletion(@NotNull VirtualFileEvent event) {
        
    }
    
    @Override
    public void beforeFileMovement(@NotNull VirtualFileMoveEvent event) {
    }
}
