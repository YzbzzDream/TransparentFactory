package com.transparent.automationfactory.base.storage;

import android.os.Environment;


import com.transparent.automationfactory.AutomationApplication;
import com.transparent.automationfactory.base.interfaces.IConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileStorage {

    private static final String IMAGE_FILE_PATH = "/ETCP/";

    public static String getImageFilePath() {
        String imageFilePath = Environment.getExternalStorageDirectory() + IMAGE_FILE_PATH;
        getOrCreatePath(imageFilePath);
        return imageFilePath;
    }

    public static File getOrCreatePath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static String getUserDataPath() {
        String path = AutomationApplication.sContext.getFilesDir() + File.separator + IConstants.MAINPATH;
        getOrCreatePath(path);
        return path;
    }


    public static File getBusinessIcondataFile() {
        String cachePath = getUserDataPath();
        File file = new File(cachePath + File.separator + IConstants.BUSINESS_ICON_DATA_FILE);
        return file;
    }

    public static File getADdataFile() {
        String cachePath = getUserDataPath();
        File file = new File(cachePath + File.separator + IConstants.AD_DATA_FILE);
        return file;
    }

    public static void cleanUserData() {
        delFolder(getUserDataPath());
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        if (tempList == null) {
            return;
        }
        File temp;
        for (int i = 0;
             i < tempList.length;
             i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
            }
        }
    }


    public static void serializeObject(final Object entity, File file) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file.toString());
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> T deserialize(Class<T> t, File file) {
        try {
            T c = (T) (deserialize(file));
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            file.deleteOnExit();
        }
        return null;


    }

    public static Object deserialize(File file) {
        Object object = new Object();
        FileInputStream fos = null;
        try {
            if (!file.exists()) {
                return null;
            }
            fos = new FileInputStream(file);
            ObjectInputStream ois;
            ois = new ObjectInputStream(fos);
            object = ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
            return null;

        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return object;
    }

    public static String getEtcpLogPath() {
        String logPath = getImageFilePath() + IConstants.ETCP_LOG_DIR + File.separator;
        getOrCreatePath(logPath);
        return logPath;
    }

    public static String getUpgradePath() {
        String upgrade = getImageFilePath() + "upgrade/";
        getOrCreatePath(upgrade);
        return upgrade;
    }

    public static String getADDirectory() {
        String addir = getImageFilePath() + "adpictures/";
        getOrCreatePath(addir);
        return addir;
    }
}
