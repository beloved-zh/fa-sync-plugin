package com.beloved.core;

import com.beloved.constant.BuildKeys;
import com.beloved.utils.ProjectUtils;
import com.beloved.utils.PushUtils;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: Beloved
 * @CreateTime: 2022-07-22 15:41
 * @Description:
 */
public class FaCore {

    private static String buildFileName = "build.properties";
    
    private static String fileSeparator = File.separator;
    
//    private final static String PLACEHOLDER_REGEX = "\\\\$\\\\{.*?\\\\}";
    private final static String PLACEHOLDER_REGEX = "(?<=\\$\\{).*?(?=\\})";
    
    public static void fileSync(String filePath) {
        String webDir = ProjectUtils.getValue(BuildKeys.WEB_DIR);
        
        String targetDir = ProjectUtils.getValue(BuildKeys.TARGET_DIR) + fileSeparator + ProjectUtils.getValue(BuildKeys.WEBAPP_NAME);

        targetDir = filePath.replace(webDir, targetDir);

        copyFile(filePath, targetDir);
    }
    
    public static boolean judgeWebFile(String filePath) {
        String webDir = ProjectUtils.getValue(BuildKeys.WEB_DIR);
        return filePath.startsWith(webDir);
    }
    
    public static void copyFile(String sourcePath, String targetPath){
        File source = new File(sourcePath);
        File target = new File(targetPath);
        
        if(!target.getParentFile().exists()){
            target.getParentFile().mkdirs();
        }

        FileChannel input = null;
        FileChannel output = null;
        try {
            input = new FileInputStream(source).getChannel();
            output = new FileOutputStream(target).getChannel();
            output.transferFrom(input, 0, input.size());
        } catch (Exception e) {
            e.printStackTrace();
            PushUtils.pushMessage(e.getMessage(), NotificationType.ERROR);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setProjectBuildConfigure(Project project) throws IOException {
        String projectPath = project.getBasePath();

        String buildFilePath = projectPath + fileSeparator + buildFileName;
        String coreBuildFilePath = projectPath + fileSeparator + "core" + fileSeparator + buildFileName;

        Properties buildConfigure = getProperties(buildFilePath);

        for(Object key : buildConfigure.keySet()){
            String value = fillValue(buildConfigure.getProperty((String) key), buildConfigure);

            buildConfigure.put(key, value);
        }

        Properties coreBuildConfigure = getProperties(coreBuildFilePath);

        buildConfigure.putAll(coreBuildConfigure);

        for(Object key : buildConfigure.keySet()){
            String value = fillValue(buildConfigure.getProperty((String) key), buildConfigure);

            ProjectUtils.setValue((String) key, value);
        }
    }
    
    public static Properties getBuildConfigure(Project project) throws IOException {
        String projectPath = project.getBasePath();
        
        String buildFilePath = projectPath + fileSeparator + buildFileName;
        String coreBuildFilePath = projectPath + fileSeparator + "core" + fileSeparator + buildFilePath;
        
        Properties buildConfigure = getProperties(buildFilePath);

        for(Object key : buildConfigure.keySet()){
            String value = fillValue(buildConfigure.getProperty((String) key), buildConfigure);

            buildConfigure.put(key, value);
        }

        Properties coreBuildConfigure = getProperties(coreBuildFilePath);

        buildConfigure.putAll(coreBuildConfigure);

        for(Object key : buildConfigure.keySet()){
            String value = fillValue(buildConfigure.getProperty((String) key), buildConfigure);

            buildConfigure.put(key, value);
        }

        return buildConfigure;
    }

    public static String fillValue(String value, Properties buildConfig) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        
        final Pattern pattern = Pattern.compile(PLACEHOLDER_REGEX);
        final Matcher matcher = pattern.matcher(value);

        while (matcher.find()) {
            String placeholder = matcher.group(0);

            String placeholderValue = buildConfig.getProperty(placeholder);

            placeholderValue = fillValue(placeholderValue, buildConfig);
            if (StringUtils.isNotEmpty(placeholderValue)) {
                value = value.replace("${" + placeholder + "}" , placeholderValue);
            }
        }

        return value;
    }
    
    public static Properties getProperties(String filePath) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            Properties properties = new Properties();
            fileInputStream = new FileInputStream(filePath);
            properties.load(fileInputStream);
            return properties;
        } catch (FileNotFoundException e) {
            String message = "{" + filePath + "}文件不存在";
            PushUtils.pushMessage(message, NotificationType.ERROR);
            throw e;
        } finally {
            if (fileInputStream != null) fileInputStream.close();
        }
    }
}
