package com.beloved.action;

import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Properties;

public class SyncFileAction extends AnAction {

    private String buildFileName = "build.properties";

    private String coreBuildFileName = "core/build.properties";

    private Project myProject;

    @Override
    public void actionPerformed(AnActionEvent event) {
        // TODO: insert action logic here
        myProject = event.getData(PlatformDataKeys.PROJECT);

        String targetPath = null;

        try {
            targetPath = getTargetPath(event);
        } catch (IOException e) {
            e.printStackTrace();
            pushMessage(e.getMessage(), NotificationType.ERROR);
        }

        if (targetPath == null) {
//            Messages.showMessageDialog(myProject, "目前无法操作非web资源文件", "提示", Messages.getInformationIcon());
            pushMessage("目前无法操作非web资源文件", NotificationType.WARNING);
        }

        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(event.getDataContext());

        // 是否 是 目录
        if (file.isDirectory()) {
            VirtualFile[] files = file.getChildren();
            for (VirtualFile virtualFile : files) {
                copyFile(file.getPath(), targetPath);
                pushMessage("文件{" + file.getName() +"}同步成功", NotificationType.INFORMATION);
            }
        } else {
            copyFile(file.getPath(), targetPath);
            pushMessage("文件{" + file.getName() +"}同步成功", NotificationType.INFORMATION);
        }

    }

    //在Action显示之前,根据选中文件扩展名判定是否显示此Action
//    @Override
//    public void update(AnActionEvent e) {
//        String extension = getFileExtension(e.getDataContext());
//        this.getTemplatePresentation().setEnabled(extension != null && "js".equals(extension));
//    }

//    public static String getFileExtension(DataContext dataContext) {
//        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(dataContext);
//        return file == null ? null : file.getExtension();
//    }

    /**
     * 消息通知
     * @param message
     * @param type
     *      INFORMATION
     *      WARNING
     *      ERROR
     */
    public void pushMessage(String message, NotificationType type) {
        NotificationGroup notificationGroup = new NotificationGroup("beloved", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(message, type);
        Notifications.Bus.notify(notification);
    }

    public void copyFile(String sourcePath, String targetPath){
        File source = new File(sourcePath);
        File target = new File(targetPath);
        FileChannel input = null;
        FileChannel output = null;
        try {
            input = new FileInputStream(source).getChannel();
            output = new FileOutputStream(target).getChannel();
            output.transferFrom(input, 0, input.size());
        } catch (Exception e) {
            e.printStackTrace();
            pushMessage(e.getMessage(), NotificationType.ERROR);
        } finally {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTargetPath(AnActionEvent event) throws IOException {
        // 工程路径
        String basePath = myProject.getBasePath();

        // 配置文件路径
        String buildFilePath = basePath + "/" + buildFileName;
        String coreBuildFilePath = basePath + "/" + coreBuildFileName;

        // 应用名称
        String appName = getPropertiesValue(coreBuildFilePath, "webapp.name");

        // 服务器（tomcat\weblogic）地址
        String containerPath = getPropertiesValue(buildFilePath, "wl_domains_home");

        // 部署地址
        String targetDir = getPropertiesValue(buildFilePath, "target.dir");
        targetDir = targetDir.replace("${wl_domains_home}", containerPath);
        targetDir = targetDir + "/" + appName;

        // 项目 web 资源路径
        String webDir = getPropertiesValue(buildFilePath, "web.dir");
        webDir = webDir.replace("${make_home}", basePath);

        // 选中文件
        VirtualFile file = DataKeys.VIRTUAL_FILE.getData(event.getDataContext());
        String selectFilePath = file.getPath();

        // 选中文件是否是 项目 web 资源
        if (selectFilePath.startsWith(webDir)) {
            String targetFilePath = selectFilePath.replace(webDir, targetDir);
            return targetFilePath;
        }
        return null;
    }

    public String getPropertiesValue(String filePath, String key) throws IOException {
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        properties.load(bufferedReader);
        // 获取key对应的value值
        return properties.getProperty(key);
    }
}
