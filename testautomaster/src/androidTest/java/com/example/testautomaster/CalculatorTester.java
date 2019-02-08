package com.example.testautomaster;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.test.InstrumentationTestCase;

import java.io.File;
import java.io.FilenameFilter;

public class CalculatorTester extends InstrumentationTestCase{

    private UiDevice device;

    @Override
    protected void setUp() throws Exception {
        device = UiDevice.getInstance(getInstrumentation());

    }


    public void testAdd() throws Exception {


        String path = "/Users/feifei/Desktop/测试集/ForTest";
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {


//        file.listFiles(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                if()
//                return false;
//            }
//        })
//
//        }

            device.wait(Until.hasObject(By.text("batch")), 3000);

            //获取一个图标元素
            UiObject2 appsButton = device.findObject(By.text("batch"));
            appsButton.click();

            // Wait for the Calculator icon to show up on the screen
//        device.wait(Until.hasObject(By.text("英语")), 3000);

//        device.waitForIdle(30000);

            Thread.sleep(10000);


            // Select the button for 9
            UiObject2 buttonNine = device.findObject(By.text("英语"));
            buttonNine.click();

            device.wait(Until.hasObject(By.text("日语")), 3000);

//        // Select the button for +
            UiObject2 buttonPlus = device.findObject(By.text("日语"));
            buttonPlus.click();
//
//        // Press 9 again as we are calculating 9+9
//        buttonNine.click();
//
//        // Select the button for =
//        UiObject2 buttonEquals = device.findObject(By.desc("equals"));
//        buttonEquals.click();
//
//
//        device.waitForIdle(3000);
//
//        UiObject2 resultText = device.findObject(By.clazz("android.widget.EditText"));
//        String result = resultText.getText();
//
//        assertTrue(result.equals("18"));
        }


//    private void search(File fileold)
//
//    {
//
//        try{
//
//            File[] files=fileold.listFiles();
//
//            if(files.length>0)
//
//            {
//
//                for(int j=0;j<files.length;j++)
//
//                {
//
//                    if(!files[j].isDirectory())
//
//                    {
//
////                        if(files[j].getName().indexOf(key)> -1)
//
//                        {
//
////                            path += "\n" + files[j].getPath();
////                            result.setText(info+path);
//
//
//                            //shuju.putString(files[j].getName().toString(),files[j].getPath().toString());
//
//                        }
//
//                    }
//
//                    else{
//
//                        this.search(files[j]);
//
//                    }
//
//                }
//
//            }
//
//        }
//
//        catch(Exception e)
//
//        {
//
//
//
//        }
//
//    }

    }
}
