package com.pyy.uitest;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by pyy on 2017/8/1.
 */

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 19)
public class Sign {
    private UiDevice mDevice;
    private boolean firstInit = true;
    static final String logTag = "[Sign_Test]";
    private final String JD = "京东";
    private final String SMZDM = "什么值得买";
    private final String TXDM = "腾讯动漫";
    private final String JDJR = "京东金融";
    private final long timeout = 10 * 1000;
    String intentName = "";
    private final String PATH =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/AutoSignIn/";
    private final String FILE  = PATH + "gesture.txt";

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        if (firstInit) {
            firstInit = false;
        }

        // Start from the home screen
        mDevice.pressHome();
    }

    private void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void prt(String text) {
        Log.i(logTag, " " + text);
    }

    private void launchPackage(String app) {
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        Intent mBootUpIntent = pm.getLaunchIntentForPackage(app);
        mBootUpIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        mBootUpIntent.setAction(Intent.ACTION_MAIN);
        mBootUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        InstrumentationRegistry.getContext().startActivity(mBootUpIntent);
    }

    private void stopApp(UiDevice device, String app) {
        String stopCmd = "am force-stop " + app + "\n";
        try {
            device.executeShellCommand(stopCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static int[] string2array(String string) {
        String[] sArray = string.split(",");
        int[] array = new int[sArray.length];
        for(int i = 0; i < sArray.length; i++){
            array[i] = Integer.parseInt(sArray[i]);
        }

        return array;
    }

    @Test
    public void TXDMTest() throws IOException, UiObjectNotFoundException, InterruptedException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        final String Package = getPackageName(TXDM);
        assertThat(Package, notNullValue());
        stopApp(mDevice, Package);
        launchPackage(Package);
        while (!mDevice.getCurrentPackageName().equals(Package)) {
            delay(5000);
        }
        mDevice.waitForWindowUpdate(Package, timeout);
        delay(5000);
        mDevice.pressBack();
        UiObject2 sign = mDevice.wait(Until.findObject(By.res("com.qq.ac.android:id/tab_layout_center")), timeout);
        if (sign != null) {
            sign.click();
            delay(5000);
            sign = mDevice.wait(Until.findObject(By.res("com.qq.ac.android:id/btn_sign")), timeout);
            if (sign != null) {
                sign.click();
                delay(4000);
                sign = mDevice.wait(Until.findObject(By.res("com.qq.ac.android:id/rl_sign_btn")), timeout);
                if (sign != null) {
                    sign.click();
                    mDevice.pressBack();
                    mDevice.pressBack();
                    mDevice.pressBack();
                }
            }
            mDevice.pressBack();
            mDevice.pressBack();
        }
    }

    @Test
    public void SMZDMTest() throws IOException, UiObjectNotFoundException, InterruptedException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        final String Package = getPackageName(SMZDM);
        assertThat(Package, notNullValue());
        stopApp(mDevice, Package);
        launchPackage(Package);
        while (!mDevice.getCurrentPackageName().equals(Package)) {
            delay(5000);
        }
        mDevice.waitForWindowUpdate(Package, timeout);
        delay(3000);
        UiObject2 sign = mDevice.wait(Until.findObject(By.text("签到")), timeout);
        if (sign != null) {
            UiObject2 parent = sign.getParent();
            if (parent != null && parent.isClickable() &&
                    "android.widget.RelativeLayout".equals(parent.getClassName())) {
                parent.click();
                delay(5000);
                mDevice.swipe(260, 1529, 872, 1529, 20);
                mDevice.pressBack();
                mDevice.pressBack();
                mDevice.pressBack();
            } else {
                sign.click();
                delay(5000);
                mDevice.swipe(260, 1529, 872, 1529, 20);
                mDevice.pressBack();
                mDevice.pressBack();
                mDevice.pressBack();
            }
        }
    }

    @Test
    public void JDTest() throws IOException, UiObjectNotFoundException, InterruptedException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        final String Package = getPackageName(JD);
        assertThat(Package, notNullValue());
        stopApp(mDevice, Package);
        launchPackage(Package);
        while (!mDevice.getCurrentPackageName().equals(Package)) {
            delay(5000);
        }
        mDevice.waitForWindowUpdate(Package, timeout);
        if (mDevice.getCurrentPackageName().equals(Package)) {// "领京豆"
            mDevice.click(333, 811);
            delay(6000);
            mDevice.click(917, 420);
            delay(6000);
            mDevice.click(283, 1254);
            mDevice.pressBack();
            mDevice.pressBack();
        }
        delay(3000);
        if (mDevice.getCurrentPackageName().equals(Package)) {// "领券"
            mDevice.click(523, 785);
            delay(5000);
            mDevice.click(550, 744);
            delay(3000);
            mDevice.pressBack();
            mDevice.pressBack();
        }
        delay(3000);
        if (mDevice.getCurrentPackageName().equals(Package)) {// "惠赚钱"
            mDevice.click(755, 804);
            delay(6000);
            mDevice.click(916, 980);
            delay(3000);
            mDevice.pressBack();
        }
        delay(3000);
        if (mDevice.getCurrentPackageName().equals(Package)) {// "会员京豆"
            UiObject2 vip = mDevice.wait(Until.findObject(By.desc("我的")), timeout);
            if (vip != null) {
                vip.click();
                vip = mDevice.wait(Until.findObject(By.res("com.jd.lib.personal:id/user_vip")), timeout);
                if (vip != null) {
                    vip.click();
                    delay(6000);
                    mDevice.click(1009, 806);
                    delay(3000);

                }
            }
        }
        mDevice.pressBack();
        mDevice.pressBack();
        mDevice.pressBack();
    }

    @Test
    public void JDJRTest() throws IOException, UiObjectNotFoundException, InterruptedException {
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        final String Package = getPackageName(JDJR);
        assertThat(Package, notNullValue());
        stopApp(mDevice, Package);
        launchPackage(Package);
        while (!mDevice.getCurrentPackageName().equals(Package)) {
            delay(5000);
        }
        UiObject2 gesture = mDevice.wait(Until.findObject(By.res("com.jd.jrapp:id/txt_forget_gesture")), timeout);
        if (gesture != null) {
            FileReader file = new FileReader(FILE);
            BufferedReader br = new BufferedReader(file);
            String read = br.readLine();
            int[] Array =string2array(read.trim());
            int size = Array.length / 2;
            Point[] p = new Point[size];
            for (int i = 0; i < Array.length; i += 2) {
                Point temp = new Point(Array[i], Array[i + 1]);
                p[i / 2] = temp;
            }
            mDevice.swipe(p, 40);
        }
        //mDevice.waitForWindowUpdate(Package,timeout);
        UiObject2 sign = mDevice.wait(Until.findObject(By.text("签到")), timeout);
        if (sign != null) {
            sign.click();
            UiObject2 signchild = mDevice.wait(Until.findObject(By.res("com.jd.jrapp:id/btn_feedback_summit")), timeout);
            if (signchild != null) {
                signchild.click();
                delay(5000);
                mDevice.click(942, 529); // 签到领京豆
                delay(1000);
                mDevice.pressBack();
                delay(4000);
                mDevice.click(554, 884); // 钢蹦
                delay(5000);
                mDevice.click(811, 875); // 领钢蹦
                delay(5000);
                mDevice.click(940, 366); // 打卡领钢蹦
                delay(5000);
                mDevice.click(544, 453); // 打卡
                delay(3000);
                // TODO 领取钢蹦
                mDevice.pressBack();
                delay(1000);
                UiObject2 back = mDevice.wait(Until.findObject(By.res("com.jd.jrapp:id/btn_left")), timeout);
                if (back != null) {
                    back.click();
                }
                mDevice.pressHome();
            }
        }
    }

    private String getPackageName(String name) {
        // Use PackageManager to get the launcher package name
        PackageManager pm = InstrumentationRegistry.getContext().getPackageManager();
        List<PackageInfo> packages = pm.getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            AppInfo tempInfo = new AppInfo();
            tempInfo.appName = packageInfo.applicationInfo.loadLabel(pm).toString();
            tempInfo.packageName = packageInfo.packageName;
            tempInfo.versionName = packageInfo.versionName;
            tempInfo.versionCode = packageInfo.versionCode;
            tempInfo.appIcon = packageInfo.applicationInfo.loadIcon(pm);

            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                if (tempInfo.appName.equals(name)) {
                    //Log.i(logTag, "appName " + tempInfo.appName + " packageName " + tempInfo.packageName);
                    intentName = tempInfo.packageName;
                }
            }
        }
        return intentName;
    }

}
