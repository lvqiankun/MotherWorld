package com.qianbaoshenghuo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CtsHelper {
	/*
	 * 本类用于在CTS框架中运行uiautomator 基于Android 4.4 CTS
	 * 思路�?
	 * 1.编译且复制jar包到CTS TestCase目录�?
	 * 2.依据CTS框架格式创建TestCase 
	 * 3.依据CTS框架格式创建TestPlan
	 * 4.运行TestPlan
	 */
	//输入参数，改变以下参数来适配不同的类
	private String workspace="D:\\jenkins-1.623\\jobs\\UiAutomator Test\\workspace\\QiaoBaoShengHuo_AndroidUitest";
	private String className_FullName="com.qianbaoshenghuo.login";
	private String jarName="CalculatorCaseCTS";
	private String androidId="1";
	private String ctsPath_testCase="${SDK_PATH}\\repository\\testcases\\";
	private String ctsPath_testPlan="${SDK_PATH}\\android-cts\\repository\\plans\\";
	//CTS Tools 命令路径
	private String ctsToolsPath="${SDK_PATH}\\android-cts\\tools\\";
	//ROOT SDK目录
	private String dcts_root_path="${SDK_PATH}";
	//log与result path
	private String logPath="";
	private String resultPath="";
	String fileName="";
	
	
	//以下字段不需要改�?
	//TestCase XML文件字段
	private String testCase_sc_1="<?xml version="+"\"1.0\"" +" encoding="+"\"UTF-8\""+"?>";
	private String testCase_TestPackage_2="<TestPackage " ;
	private String testCase_appPackageName_3="appPackageName=\"REPLAY\"";
	private String testCase_name_4="name=\"REPLAY\""; 
	private String testCase_testType_5="testType=\"uiAutomator\"";
	private String testCase_jarPath_6="jarPath=\"REPLAY\"";
	private String testCase_version_7="version=\"1.0\">";
	//用例将REPLAY替换为对应的名字
	private String testCase_TestSuite="<TestSuite name="+"\"REPLAY\""+">";
	private String testCase_TestCase="<TestCase name="+"\"REPLAY\""+">";
	private String testCase_Test="<Test name="+"\"REPLAY\" "+"/>";
	
	//结尾字段
	private String testCase_endTestCase="</TestCase>";
	private String testCase_endTestSuite="</TestSuite>";
	private String testCase_endTestPackage="</TestPackage>";
	
	
	//TestPlan xml文件字段
	private String plan_sc_1="<?xml version="+"\"1.0\"" +" encoding="+"\"UTF-8\""+"?>";
	private String plan_TestPlan_2="<TestPlan version="+"\"1.0\""+">";
	private String plan_URI_3="<Entry uri=\"REPLAY\"/>";
	private String plan_endTestPlan="</TestPlan>";
	
	//运行命令
	/*
	cd ${SDK_PATH}\android-cts\tools
	java -cp ddmlib-prebuilt.jar;tradefed-prebuilt.jar;hosttestlib.jar;cts-tradefed.jar -DCTS_ROOT=${SDK_PATH} com.android.cts.tradefed.command.CtsConsole run cts --plan calculator
    */
	private String runClassName="com.android.cts.tradefed.command.CtsConsole";
	private String runPlanCmd="run cts --plan REPLAY";
	private String devices="";
	
	//结果路径保存
	private ArrayList<String> listResultPath=new ArrayList<String>();
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String workspase="";
		String className="";
		String jarName="";
		String androidId="";
		String sdkPath="";
		String devices="";
        for(int i=0;i<args.length;i++){
        	if(args[i].equals("--workspase")){
        		workspase=args[i+1];
        	}else 
        	if(args[i].equals("--class_name")){
        		className=args[i+1];
        	}else 
        	if(args[i].equals("--jar_name")){
        		jarName=args[i+1];
        	}else 
        	if(args[i].equals("--android_id")){
            		androidId=args[i+1];
            }else if(args[i].equals("--sdk_path")){
            		sdkPath=args[i+1];
            }else
             if(args[i].equals("-s")){
             		devices=args[i+1];
             }
        }
        CtsHelper cts=new CtsHelper(workspase, className, jarName, androidId, sdkPath);
        cts.setDevices(devices);
        cts.runTest();
       
        
	}
	/**
	 * 运行默认参数的CTS
	 */
	public CtsHelper(){
		
	}
	
	/**
	 * 传入�? 工程工作空间，class全名，jarname,androidid，SDK路径
	 * @param paramater
	 */
	public CtsHelper(String workspace,String className,String jarName,String androidId,String sdkpath){
		
		this.workspace=workspace+"\\";
		this.className_FullName=className;
		this.jarName=jarName;
		this.androidId=androidId;
		this.ctsPath_testCase=sdkpath+"\\android-cts\\repository\\testcases\\";
		this.ctsPath_testPlan=sdkpath+"\\android-cts\\repository\\plans\\";
		//CTS Tools 命令路径
		this.ctsToolsPath=sdkpath+"\\android-cts\\tools\\";
		//ROOT SDK目录
		this.dcts_root_path=sdkpath;
	}
	
	/**
	 * 整体运行步骤
	 */
	 void runTest(){
		//编译 将编译的jar复制到CTS testcase目录�?
		String testName="";		
		new UiAutomatorHelper(jarName, className_FullName, testName, androidId, (ctsPath_testCase+jarName+".jar").replaceAll(";", ""));			
		//创建xml  testCase.xml  testplan.xml
		createTestCaseXml("test"+jarName+"TestCase.xml");
		createTestPlanXml("test"+jarName+"TestPlan.xml");			
		//运行命令
		if(!devices.equals("")){
		execCmd(getRunCtsCmd("test"+jarName+"TestPlan")+devices);
		}else{
		execCmd(getRunCtsCmd("test"+jarName+"TestPlan"));
		}
		//输出log文件路径和结果文件路�?
		 System.out.println("***************************");
	        for(String s:listResultPath){
	        	System.out.println(s);
	        }
	     System.out.println("***************************");
		
	}
	/**
	 * �?求：多个手机情况下，指定某个手机运行
	 * @param dev
	 */
	public void setDevices(String dev){
		this.devices=" -s "+dev;
	}
	
	
	/**
	 * 生成CTS运行命令，基于Android 4.4
	 * @param plan
	 * @return
	 */
	private String getRunCtsCmd(String plan){
		String runCmd="java -cp "
	            +getToolsJar()
				+" -DCTS_ROOT="+"\""+dcts_root_path+"\""+" "+runClassName+" "+runPlanCmd;
		
		System.out.println(runCmd.replace("REPLAY", plan));
		return runCmd.replace("REPLAY", plan);
	
	}
	/**
	 * �?求：获取tools下jar路径组合为cp 格式字符�?
	 * @return
	 */
	private String getToolsJar(){
		String jarName="";
		File file=new File(ctsToolsPath);
		File[] fileList=file.listFiles();
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].getName().contains(".jar")){
				jarName=jarName+"\""+fileList[i].getAbsolutePath()+"\""+";";
			}
		}
		jarName=jarName.substring(0, jarName.length()-1);
		System.out.println(jarName);
		return jarName;
	}
	/**
	 * 创建 testcase xml文件
	 * @param xmlName 文件名加.xml
	 */
	private void createTestCaseXml(String xmlName){
		//风起于青萍之末，英雄不问出处,�?之凿凿，句句在理
		 File caseFile=new File(ctsPath_testCase+xmlName);
		    if (caseFile.exists()) {
				caseFile.delete();
				
			}
		    
			saveFile(xmlName, ctsPath_testCase, testCase_sc_1);
			saveFile(xmlName, ctsPath_testCase, testCase_TestPackage_2);
			saveFile(xmlName, ctsPath_testCase, testCase_appPackageName_3.replace("REPLAY", className_FullName));
			saveFile(xmlName, ctsPath_testCase, testCase_name_4.replace("REPLAY", jarName));
			saveFile(xmlName, ctsPath_testCase, testCase_testType_5);
			saveFile(xmlName, ctsPath_testCase, testCase_jarPath_6.replace("REPLAY", jarName+".jar"));
			saveFile(xmlName, ctsPath_testCase, testCase_version_7);
			//TestSuite 按点分开逐步�?  com.lenovo.uitest.calculator.CalculatorCase_V2_1
			String[] testSuite=className_FullName.split("\\.");
			for(int i=0;i<testSuite.length-1;i++){
				saveFile(xmlName, ctsPath_testCase, testCase_TestSuite.replace("REPLAY", testSuite[i]));
				System.out.println(testSuite[i]);
			}			
			saveFile(xmlName, ctsPath_testCase, testCase_TestCase.replace("REPLAY", testSuite[testSuite.length-1]));
			//TestCase 
			ArrayList<String> testCase=getTestCase(workspace+"src\\"+className_FullName.replace(".", "\\")+".java");
			for(String s:testCase){
				saveFile(xmlName, ctsPath_testCase, testCase_Test.replace("REPLAY", s));
			}            
			saveFile(xmlName, ctsPath_testCase, testCase_endTestCase);
			//与suite同数�?
			for(int i=0;i<testSuite.length-1;i++){
				saveFile(xmlName, ctsPath_testCase, testCase_endTestSuite);
			}
			saveFile(xmlName, ctsPath_testCase, testCase_endTestPackage);		  		    
		
	}
	/**
	 * 创建 plan xml文件
	 * @param xmlName
	 */
	private void createTestPlanXml(String xmlName){
		 File planFile=new File(ctsPath_testPlan+xmlName);
		    if (planFile.exists()) {
		    	planFile.delete();
		    	
			}

			saveFile(xmlName, ctsPath_testPlan, plan_sc_1);
			saveFile(xmlName, ctsPath_testPlan, plan_TestPlan_2);			
			saveFile(xmlName, ctsPath_testPlan, plan_URI_3.replace("REPLAY", className_FullName));
			saveFile(xmlName, ctsPath_testPlan, plan_endTestPlan);
	}
	
	/**
	 * 保存内容到指定文�?
	 * @param fileName
	 * @param path
	 * @param line
	 */
	private void saveFile(String fileName,String path,String line){
		System.out.println(line);
		File file=new File(path+fileName);
		while (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			FileOutputStream out=new FileOutputStream(file,true);
			OutputStreamWriter writer=new OutputStreamWriter(out);
			BufferedWriter bWriter=new BufferedWriter(writer);
			
			bWriter.append(line);
			bWriter.newLine();
			bWriter.flush();
			bWriter.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	/**
	 * 执行命令
	 * @param cmd
	 */
	private void execCmd(String cmd) {
		System.out.println("****commond: " + cmd);
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			InputStream in = p.getInputStream();
			InputStreamReader re = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(re);
			String info="";
			String line = "";
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				info=getResultInfo(line);
				if(!info.equals("")){
				listResultPath.add(info);
				}
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取�?有的用例名，文件解析方式
	 * @param filePath
	 * @return
	 */
	private ArrayList<String> getTestCase(String filePath){
		
		 ArrayList<String> testCase=new ArrayList<String>();
	      
	                File file=new File(filePath);
	                if(!file.exists()){
	                	System.out.println("The testcase file don't exist...");
	                }	              
	                    InputStreamReader read;
						try {
							read = new InputStreamReader(new FileInputStream(file));
						
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                       if(lineTxt.matches(".*public\\s+void\\s+test.*")){
	                    	   int index_0=lineTxt.indexOf("test");
	                    	   int index_1=lineTxt.indexOf("(");	                    	   
	                    	   testCase.add(lineTxt.substring(index_0, index_1));
	                    	   System.out.println("TestCase:"+lineTxt.substring(index_0, index_1));
	                       }
	                       	                        
	                    }
	                    read.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
	      
	     return testCase;
	    }
	/**
	 * �?求：获取结果路径，log路径
	 * @return
	 */
	private String getResultInfo(String line){
		//Created result dir 2015.06.13_23.55.28
		// Saved log device_logcat_212048202233862593.zip
		// Saved log host_log_225718056528107765.zip
		// com.jikexueyuan.demo.Demo1 package complete: Passed 0, Failed 0, Not Executed 0
		// Created xml report file at file://E:\Program Files (x86)\Android\android-sdk\android-cts\repository\results\2015.06.13_23.55.28\testResult.xml
		
		
		if(line.matches(".*file://.*testResult.xml.*")){
			return line.replaceAll(".*report.*file.*at.*file", "file");
		}else if(line.matches(".*device_logcat_.*zip.*")){
			return dcts_root_path+"\\android-cts\\repository\\logs\\"+fileName+"\\"+line.replaceAll(".*device_", "device_");
		}else if(line.matches(".*host_log_.*zip")){
			return dcts_root_path+"\\android-cts\\repository\\logs\\"+fileName+"\\"+line.replaceAll(".*host_log", "host_log");
		}else if(line.matches(".*Created.*result.*dir.*\\d+.*")){
			fileName=line.replaceAll(".*dir\\s+", "");
			return fileName;
		}else if(line.matches(".*complete:.*Passed.*Failed.*Not.*Executed.*")){
			return line.replaceAll(".*complete:\\s+", "");
		}
		return "";
	}
	 

}
