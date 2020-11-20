package com.example.lib_crash;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @Author Yu
 * @Date 2020/11/19 17:57
 * @Description TODO
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler sInstance = new CrashHandler();
    private Context mContext;
    private String externalStoragePublicDirectoryPath = "";
    private String FILE_NAME_SUFFIX = ".trace";
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    public static CrashHandler getInstance() {
        return sInstance;
    }

     public void init(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        externalStoragePublicDirectoryPath = mContext.getExternalCacheDir().getAbsolutePath();
        Log.i("crashHandler", "文件保存路径： " + externalStoragePublicDirectoryPath);
    }


    @Override
    public void uncaughtException(@NotNull Thread t, @NotNull Throwable e) {
        try {
            extortExceptionToSDCard(e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        e.printStackTrace();
        //如果系统提供了默认的异常处理器，则交给系统去结束程序，否则就自己结束自己
        if (mDefaultCrashHandler != null)
        {
            mDefaultCrashHandler.uncaughtException(t, e);
        } else
        {
            Process.killProcess(Process.myPid());
        }
    }

    /**
     * 保存到本地SD卡中
     */
    private void extortExceptionToSDCard(Throwable e) {
        //判断是否有sd卡
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return;
        }
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        File file = new File(externalStoragePublicDirectoryPath + File.separator + time + FILE_NAME_SUFFIX);
        try{
            //往文件写数据
            PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            printWriter.println(time);
            printWriter.println(appendPhoneInfo());
            e.printStackTrace(printWriter);
            printWriter.close();
        }catch (IOException e1)
        {
            e1.printStackTrace();
        } catch (PackageManager.NameNotFoundException e1)
        {
            e1.printStackTrace();
        }
    }

    /**
     * 获取手机信息
     */
    private String appendPhoneInfo() throws PackageManager.NameNotFoundException
    {
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        StringBuilder sb = new StringBuilder();
        //App版本
        sb.append("App Version: ");
        sb.append(pi.versionName);
        sb.append("_");
        sb.append(pi.versionCode + "\n");

        //Android版本号
        sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT + "\n");

        //手机制造商
        sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER + "\n");

        //手机型号
        sb.append("Model: ");
        sb.append(Build.MODEL + "\n");

        //CPU架构
        sb.append("CPU: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            sb.append(Arrays.toString(Build.SUPPORTED_ABIS));
        } else
        {
            sb.append(Build.CPU_ABI);
        }
        return sb.toString();
    }
}
