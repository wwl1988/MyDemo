package com.example.wwl.mytestdem;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.wwl.mytestdem.MD5Test.MD5Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.wwl.mytestdem", appContext.getPackageName());
    }

    @Test
    public void mTest() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        String md51 = MD5Utils.getFileMD5String(appContext, "111.txt");
        //文件名不同，内容相同
        String md52 = MD5Utils.getFileMD5String(appContext, "222.txt");
        //文件名不同，内容不同
        String md53 = MD5Utils.getFileMD5String(appContext, "333.txt");
        //压缩文件
        String md54 = MD5Utils.getFileMD5String(appContext, "111.rar");
        //压缩文件 文件名不同，内容相同
        String md55 = MD5Utils.getFileMD5String(appContext, "222.rar");
        //压缩文件 文件名不同，内容不同
        String md56 = MD5Utils.getFileMD5String(appContext, "333.rar");

        Log.d("WWL", "md51：" + md51);
        Log.d("WWL", "md52：" + md52);
        Log.d("WWL", "md53：" + md53);
        Log.d("WWL", "md54：" + md54);
        Log.d("WWL", "md55：" + md55);
        Log.d("WWL", "md56：" + md56);

        Log.d("WWL", "文件名不同，内容相同：" + MD5Utils.checkPassword(md51, md52));
        Log.d("WWL", "文件名不同，内容不同：" + MD5Utils.checkPassword(md51, md53));

        assertEquals(md51, md52);
    }

}
