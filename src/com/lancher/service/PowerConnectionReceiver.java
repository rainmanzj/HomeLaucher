package com.lancher.service;

import java.lang.reflect.Method;
import java.util.List;

import com.coverflow.HelloAndroid;
import com.lancher.ui.AnimationTimer;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {
	private Boolean bPowerOffline=false;
	Context ct=null;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		 ct=arg0;
		 String status = arg1.getAction();
		   if (status.equals(Intent.ACTION_POWER_CONNECTED )) {     	
			   bPowerOffline=false;
			   //HelloAndroid.getActivity().setTitle("电源已接通");
		   }
		   else if(status.equals(Intent.ACTION_POWER_DISCONNECTED ))
		   {
			   bPowerOffline=true;
			   handler.postDelayed(runnable,3000);         // 开始Timer
		   }
		   else if(status.equals(Intent.ACTION_BATTERY_CHANGED))
		   {
			  // HelloAndroid.getActivity().setTitle("电量变化");
		   }
		   
	}
	private Handler handler = new Handler( );
	@SuppressLint("NewApi")
	private Runnable runnable = new Runnable( ) {

	    public void run ( ) {
	    	if(bPowerOffline==false)
	    		return;
	        handler.postDelayed(this,1000);     //postDelayed(this,1000)方法安排一个Runnable对象到主线程队列中
	        handler.removeCallbacks(runnable);           //停止Timer
		   /*10秒后关闭页面*/
	       Activity mainactivity=HelloAndroid.getActivity();
	       if(mainactivity==null)
	        	return;

	       ActivityManager mActivityManager = (ActivityManager) mainactivity.getSystemService(Context.ACTIVITY_SERVICE);  
	       mActivityManager.moveTaskToFront(mainactivity.getTaskId(), ActivityManager.MOVE_TASK_WITH_HOME);
	       
     	   Intent intent2=new Intent(mainactivity, AnimationTimer.class);
 		   intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//注意本行的FLAG设置
 		   HelloAndroid.getActivity().startActivity(intent2);
	    }
	};
}
