package com.beloved.utils;

import com.intellij.notification.*;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-22 15:38
 * @Description: 推送工具类
 */
public class PushUtils {

    private final static String DISPLAY_ID = "beloved";
    
    /**
     * 消息通知
     * @param message
     * @param type
     *      INFORMATION
     *      WARNING
     *      ERROR
     */
    public static void pushMessage(String message, NotificationType type) {
        NotificationGroup notificationGroup = new NotificationGroup(DISPLAY_ID, NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(message, type);
        Notifications.Bus.notify(notification);
    }
    
}
