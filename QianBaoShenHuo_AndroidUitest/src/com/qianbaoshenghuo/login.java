package com.qianbaoshenghuo;

import java.io.IOException;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;


public class login extends UiAutomatorTestCase{
	public static void main(String[] args) throws IOException, UiObjectNotFoundException{
		String jarName = "QianBaoShengHuo_LCCB";
    	String className = "com.qianbaoshenghuo.login";
//    	String testName = "testDemo01";
    	String androidId = "1";
    	String workspace = "D:\\jenkins-1.623\\jobs\\workspace\\QiaoBaoShengHuo_AndroidUitest";
    	String sdkPath = "D:\\sdk";
    	CtsHelper cts = new CtsHelper(workspace,className,jarName,androidId,sdkPath);
    	cts.setDevices("");
    	cts.runTest();
	}
	public  void test_01_startApp() throws IOException{
    	Runtime.getRuntime().exec(" am start -a android.intent.action.MAIN -c android.intent.category.LAUNCHER -n com.qianbao.lccb/com.qianbao.lccb.LoadingActivity");
    	try{
    		Thread.sleep(3000);
    	} catch(InterruptedException e1){
    		e1.printStackTrace();
    	}
    }
	public void test_02_userLogin() throws UiObjectNotFoundException{
    	UiObject mine = new UiObject(new UiSelector().resourceId("com.qianbao.lccb:id/rb_footer3"));
    	mine.click();
    	
    	UiObject login = new UiObject(new UiSelector().resourceId("com.qianbao.lccb:id/tv_login"));
    	login.click();
    	
    
	}	
}
