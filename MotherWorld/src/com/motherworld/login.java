package com.motherworld;

import java.io.IOException;

import jp.jun_nama.test.utf7ime.helper.Utf7ImeHelper;

import com.android.uiautomator.core.UiDevice;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiSelector;
import com.android.uiautomator.testrunner.UiAutomatorTestCase;
public class login extends UiAutomatorTestCase{

	public static void main(String[] args) throws IOException {
		String jarName = "MotherWorld";
    	String className = "com.motherworld.login";
//    	String testName = "testDemo01";
    	String androidId = "1";
    	String workspace = "E:\\workspace\\MotherWorld";
    	String sdkpath = "D:\\sdk";
    	CtsHelper cts = new CtsHelper(workspace,className,jarName,androidId,sdkpath);
    	cts.setDevices("1f6ca714");
    	cts.runTest();

	}
	public static void test_01_startApp() throws IOException{
	       Runtime.getRuntime().exec(" am start -n cn.jianguo.qinzi/com.babyrun.view.activity.SplashActivity");
	       
	    }
    public void test_02_userLogin() throws UiObjectNotFoundException{
    	UiObject inputTelphoneNumber = new UiObject(new UiSelector().text("请输入手机号"));
    	inputTelphoneNumber.setText("18910207124");
    	
    	UiObject ip = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/login_password"));
    	ip.click();
    	ip.setText("qwerasdf");
    	
//    	UiDevice device = getUiDevice();
//    	device.pressBack();
    	
    	UiObject clickLogin = new UiObject(new UiSelector().text("登录"));
    	clickLogin.click();
    	
    }

//	public void testDemo01() throws  UiObjectNotFoundException,IOException, InterruptedException{
//		UiDevice device = getUiDevice();
//		device.pressHome();
//		
//		startApp();
////		userLogin();
////		enterSet();
////		changePhotoByCamera();
////		changePhotoByPhotoAlbum();
////		changeNickName();		
////		changeSex();
////		feedback();
////		encouragement();
////		shareToFriends();
////		aboutUs();
////		logout();
//		homePageSearch();
//	}
    public void test_03_enterSet() throws UiObjectNotFoundException, InterruptedException{
    	UiObject personalCenter = new UiObject(new UiSelector().text("个人中心"));
    	personalCenter.click();
    	try{
    		Thread.sleep(3000);
    	} catch(InterruptedException e1){
    		e1.printStackTrace();
    	}
    	UiObject set = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/rl_mine_message").index(1).clickable(true));
    	set.click();
    }
    public void test_04_changePhotoByCamera() throws UiObjectNotFoundException{
    	UiObject clickPhotoBar = new UiObject(new UiSelector().text("头像"));
    	clickPhotoBar.click();
    	
    	UiObject selectCamera = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/fch_camera"));
    	selectCamera.click();
    	 
    	UiObject clickTakePhoto = new UiObject(new UiSelector().resourceId("com.android.camera:id/shutter_button"));
    	clickTakePhoto.click();
    	
    	UiObject savePhoto = new UiObject(new UiSelector().resourceId("com.android.camera:id/btn_done"));
    	savePhoto.click();
    	
    	UiObject cutPhoto = new UiObject(new UiSelector().text("剪切"));
    	cutPhoto.click();
    }
    public void test_05_changePhotoByPhotoAlbum() throws UiObjectNotFoundException{
    	UiObject clickPhotoBar = new UiObject(new UiSelector().text("头像"));
    	clickPhotoBar.click();
    	
        UiObject selectPhotoAlbum = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/fch_photo"));
        selectPhotoAlbum.click();
        
        UiObject enterPhotoAlbum = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/title").text("SelectPhoto"));
        enterPhotoAlbum.click();
        
        UiObject selectPicture = new UiObject(new UiSelector().className("android.widget.RelativeLayout").index(0).clickable(true));
        selectPicture.click();

        UiObject cutPhoto = new UiObject(new UiSelector().text("剪切"));
        cutPhoto.click(); 
    }
    public void test_06_changeNickName() throws UiObjectNotFoundException{
    	UiObject clickNickName = new UiObject(new UiSelector().text("昵称"));
    	clickNickName.click();
    	
    	UiObject locatedNickNameInputedBar = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/et_edit_name"));
    	locatedNickNameInputedBar.longClick();
    	locatedNickNameInputedBar.clearTextField();
    	locatedNickNameInputedBar.setText(Utf7ImeHelper.e("dashen001$%^&*!@"));
    	
    	UiObject saveNickName = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tv_name_save").text("保存"));
    	saveNickName.click();
    }
    public void test_07_changeSex() throws UiObjectNotFoundException{
    	UiObject clickSex = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tvGender"));
    	clickSex.click();
        
    	UiObject selectMale = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tv_personal_myorder"));
    	selectMale.click();

    }
    public void test_08_feedback() throws UiObjectNotFoundException{
    	UiObject clickFeedback = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tvOpinion"));
    	clickFeedback.click();
    	
    	UiObject inputFeedback = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/etfeedback"));
    	inputFeedback.click();
    	inputFeedback.setText(Utf7ImeHelper.e("这尼玛就是一个测试啊"));
    	
    	UiObject clickSubmit = new UiObject(new UiSelector().text("提交"));
    	clickSubmit.click();
    	
    	UiObject clickBack = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/actionbar_back"));
    	clickBack.click();
    }
    public void test_09_encouragement() throws UiObjectNotFoundException{
    	UiObject clickEncourage = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tvComment"));
    	clickEncourage.click();
    	
    	UiObject select360 = new UiObject(new UiSelector().text("360手机助手"));
    	select360.click();
    	
    	UiObject clickComment = new UiObject(new UiSelector().resourceId("com.qihoo.appstore:id/txtUninstall"));
    	clickComment.click();
    }
    public void test_10_shareToFriends() throws UiObjectNotFoundException{
    	UiObject clickShareToFriends = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tvShare"));
    	clickShareToFriends.click();
    	
    	UiObject clickShareButton = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tv_setting_share"));
    	clickShareButton.click();
    	
    	UiDevice device = getUiDevice();
    	device.pressBack();
    	device.pressBack();

    }
    public void test_11_aboutUs() throws UiObjectNotFoundException{
    	UiObject clickAboutUs = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tvAbout"));
    	clickAboutUs.click();
    	
    	UiObject clckBack = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/actionbar_back"));
    	clckBack.click();
    }
    public void test_12_homePageSearch() throws UiObjectNotFoundException{
    	UiObject search = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/filter_edit"));
    	search.click();
    	
    	UiObject searchBox = new UiObject(new UiSelector().text("请输入关键字"));
    	searchBox.click();
    	searchBox.setText(Utf7ImeHelper.e("亲子"));
        
    	UiObject searchButton = new UiObject(new UiSelector().text("搜索"));
    	searchButton.click();
    }
    public void test_13_logout() throws UiObjectNotFoundException{
    	UiObject personalCenter = new UiObject(new UiSelector().text("个人中心"));
    	personalCenter.click();
    	
    	UiObject set = new UiObject(new UiSelector().resourceId("cn.jianguo.qinzi:id/tabicon"));
    	set.click();
    	
    	UiObject exit = new UiObject(new UiSelector().text("退出登录"));
    	exit.click();
    	
    	UiObject makeSureExit = new UiObject(new UiSelector().text("确定"));
    	makeSureExit.click();
    	
    }	


}
