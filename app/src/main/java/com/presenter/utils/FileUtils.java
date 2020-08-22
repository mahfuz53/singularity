package com.presenter.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileUtils {
    //public static final String APP_DIR = "ARC_VCC_CYNO";
    private static FileUtils instance = null;
    private static Context mContext;

    //private static final String APP_DIR = MyConstant.APP_DIR;

    public static FileUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (FileUtils.class) {
                if (instance == null) {
                    mContext = context;
                    instance = new FileUtils();
                }
            }
        }
        return instance;
    }
    public static boolean isEmptyString(String text) {
        return (text == null || text.trim().equals("null") || text.trim()
                .length() <= 0);
    }
    private FileUtils() {
        if (isSDCanWrite()) {
           // creatSDDir(APP_DIR);
        }
    }

    public boolean isSDCanWrite() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)
                && Environment.getExternalStorageDirectory().canWrite()
                && Environment.getExternalStorageDirectory().canRead()) {
            return true;
        } else {
            return false;
        }
    }

    public File creatSDDir(String dirName) {
        File dir = new File(getLocalPath() + dirName);
        dir.mkdirs();
        return dir;
    }

    public File createTempFile(String prefix, String extension) throws IOException {
        File file = new File(getAppDirPath() + "/" + prefix
                + System.currentTimeMillis() + extension);
        file.createNewFile();
        return file;
    }

    public File createVideoFileWithName(String name, String extension) throws IOException {
        name = name.trim().replaceAll("  ", " ").replaceAll("[-+.^:,]", "");
        File file = new File(getAppDirPath() + "/" + name + extension);
        if (file.exists()) {
            file = new File(getAppDirPath() + "/" + name + "_" + System.currentTimeMillis() + extension);
        } else {
            file.createNewFile();
        }
        return file;
    }

    public String getAppDirPath() {
        String path = null;
        if (getLocalPath() != null) {
           /// path = getLocalPath() + APP_DIR + "/";
        }
        return path;
    }

    private static String getLocalPath() {
        String sdPath = null;
        sdPath = Environment.getExternalStorageDirectory() + "/";
        return sdPath;
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

  /*  public File[] getListVideo() {
        String path = Environment.getExternalStorageDirectory() + File.separator + APP_DIR;
        File f = new File(path);
        File[] files = f.listFiles();
        return files;
    }*/

    public static String getFileName(String fullName) {
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public static String getFileType(String fullName) {
        return fullName.substring(fullName.lastIndexOf(".") + 1, fullName.length());
    }

    public static Date getFileLastModified(String pathFile) {
        File file = new File(pathFile);
        return new Date(file.lastModified());
    }

    public static String[] returnArray(String stringToSplit)
    {
       // String stringToSplit = data;
        String[] tempArray;

        /* delimiter */
        String delimiter = " ";

        /* given string will be split by the argument delimiter provided. */
        tempArray = stringToSplit.split(delimiter);


        return tempArray;
    }
    public static void showToast(Context con,String text) {
        // Set the toast and duration
        int toastDurationInMilliSeconds = 5000;
        final Toast mToastToShow = Toast.makeText(con, text, Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }
}

