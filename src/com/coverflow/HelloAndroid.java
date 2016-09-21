package com.coverflow;

import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.lancher.libs.voice.XfVoice;
import com.lancher.service.BootBroadcastReceiver;
import com.lancher.service.PowerConnectionReceiver;
import com.lancher.utility.AndroidProcess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;

public class HelloAndroid<RecyclerView> extends AndroidProcess implements OnClickListener{
    private PowerConnectionReceiver PowerRer=new PowerConnectionReceiver();
	private BootBroadcastReceiver  BootRer=new BootBroadcastReceiver();
	private XfVoice Voice;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CoverFlow cf = new CoverFlow(this);
		// cf.setBackgroundResource(R.drawable.shape);
		cf.setBackgroundColor(Color.BLACK);
		cf.setAdapter(new ImageAdapter(this));
		ImageAdapter imageAdapter = new ImageAdapter(this);
		cf.setAdapter(imageAdapter);
		// cf.setAlphaMode(false);
		// cf.setCircleMode(false);
		cf.setSelection(2, true);
		cf.setAnimationDuration(1000);
		setContentView(cf);
	    Gallery g=(Gallery) cf;  
	    Context ct = this.getApplicationContext(); 
	    Voice=new XfVoice(ct);
	    
	    
		 //设置Gallery事件监听  
		g.setOnItemClickListener(new OnItemClickListener() {  
            @Override  
            public void onItemClick(AdapterView<?> parent, View v, int position,  
                    long id) {  
                MemuTodo(position);
            }
        });   
		
		g.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if(arg2== 0)
				{
					setTitle("菜单：优驾");
					Voice.play("优驾");
					//int code = mTts.startSpeaking("优驾菜单", mTtsListener);
				}
				else if(arg2== 1)
				{
					setTitle("菜单：导航");
				}
				else
				{
					setTitle("菜单：声控");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        });   
	
	}
	
	@Override
	public void onStart(){
		super.onStart();
		if(BootRer==null)
			BootRer=new BootBroadcastReceiver();
		registerReceiver(BootRer, new IntentFilter(Intent.ACTION_BOOT_COMPLETED)); 
		
	}
	@Override
	public void onDestroy(){
		if(BootRer != null) {
			this.unregisterReceiver(BootRer);
		}
		if(PowerRer != null) {
			this.unregisterReceiver(PowerRer);
		}
		super.onDestroy();
	}
	 @Override  
	    protected void onResume() {  
	        // TODO Auto-generated method stub  
	        super.onResume();  

			registerReceiver(PowerRer, new IntentFilter(Intent.ACTION_BATTERY_CHANGED)); 
	    }  
	      
	    @Override  
	    protected void onPause() {  
	        // TODO Auto-generated method stub  
	        super.onPause();  
	        unregisterReceiver(BootRer);  
	        unregisterReceiver(PowerRer);  
	    }  
	
	private void MemuTodo(int id)
	{
		if(id==0)
		{
	  
			doStartApplicationWithPackageName("com.comit.gooddriver");
		}
		else if(id==1)
		{
			doStartApplicationWithPackageName("com.autonavi.minimap");
			
		}
		else if(id==2)
		{
//		    //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener  
//		    RecognizerDialog    iatDialog = new RecognizerDialog(this,mInitListener);  
//		    //2.设置听写参数，同上节  
//		    //3.设置回调接口  
//		    iatDialog.setListener(recognizerDialogListener);  
//		    //4.开始听写  
//		    iatDialog.show(); 
		}
	}
	
	
	public  static void shutdown() {
		new Thread() {
            public void run() {
                try { 
                    /*10秒后关闭页面*/
                	getActivity().setTitle("智能中控将在6秒后关闭");
                    sleep(6000);
//                    MyCountTime=new CountTime(6000,1000);
//                    MyCountTime.start();
        			Process process = Runtime.getRuntime().exec("su");
        			DataOutputStream out = new DataOutputStream(
        					process.getOutputStream());
        			out.writeBytes("reboot -p\n");
        			out.writeBytes("exit\n");
        			out.flush();

                } catch (Exception e) {                   
                	e.printStackTrace();
                } finally {
         
                }
            }
        }.start();
	}
	/*
	 * 执行命令
	 * @param command
	 * 1、获取root权限 "chmod 777 "+getPackageCodePath()
	 * 2、关机 reboot -p
	 * 3、重启 reboot
	 */
	public static boolean execCmd(String command) {
	 Process process = null;
	 DataOutputStream os = null;
	 try {
	 process = Runtime.getRuntime().exec("su");
	 os = new DataOutputStream(process.getOutputStream());
	 os.writeBytes(command+"\n");
	 os.writeBytes("exit\n");
	 os.flush();
	 process.waitFor();
	 } catch (Exception e) {
	  return false;
	 } finally {
	  try {
	  if (os != null) {
	   os.close();
	  }
	  if(process != null) {
	   process.destroy();
	  }
	  } catch (Exception e) {
	  e.printStackTrace();
	  }
	 }
	 return true;
	}
	
	public static Activity getActivity() {
		  Class activityThreadClass = null;
		  try {
		    activityThreadClass = Class.forName("android.app.ActivityThread");
		    Object activityThread = activityThreadClass.getMethod("currentActivityThread").invoke(null);
		    Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
		    activitiesField.setAccessible(true);
		    Map activities = (Map) activitiesField.get(activityThread);
		    for (Object activityRecord : activities.values()) {
		      Class activityRecordClass = activityRecord.getClass();
		      Field pausedField = activityRecordClass.getDeclaredField("paused");
		      pausedField.setAccessible(true);
		      if (!pausedField.getBoolean(activityRecord)) {
		        Field activityField = activityRecordClass.getDeclaredField("activity");
		        activityField.setAccessible(true);
		        Activity activity = (Activity) activityField.get(activityRecord);
		        return activity;
		      }
		    }
		  } catch (ClassNotFoundException e) {
		    e.printStackTrace();
		  } catch (NoSuchMethodException e) {
		    e.printStackTrace();
		  } catch (IllegalAccessException e) {
		    e.printStackTrace();
		  } catch (InvocationTargetException e) {
		    e.printStackTrace();
		  } catch (NoSuchFieldException e) {
		    e.printStackTrace();
		  }
		  return null;
		}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
}