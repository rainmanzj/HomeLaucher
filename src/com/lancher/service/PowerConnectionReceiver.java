package com.lancher.service;

import java.lang.reflect.Method;
import java.util.List;

import com.coverflow.HelloAndroid;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;

public class PowerConnectionReceiver extends BroadcastReceiver {
//	public static final String ACTION_REQUEST_SHUTDOWN="android.intent.action.ACTION_REQUEST_SHUTDOWN";	 
//	public static final String EXTRA_KEY_CONFIRM="android.intent.extra.KEY_CONFIRM";
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		 String status = arg1.getAction();
		   if (status.equals(Intent.ACTION_POWER_CONNECTED )) {     	
			   HelloAndroid.getActivity().setTitle("电源已接通");

		 
		   }
		   else if(status.equals(Intent.ACTION_POWER_DISCONNECTED ))
		   {
			   HelloAndroid.getActivity().setTitle("电源已断开");
			   HelloAndroid.shutdown();
				
		   }
		   else if(status.equals(Intent.ACTION_BATTERY_CHANGED))// android.intent.action.BATTERY_CHANGED))
		   {
			   HelloAndroid.getActivity().setTitle("电量变化");
		   }
		   
	}
	
//	     boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
//	     status == BatteryManager.BATTERY_STATUS_FULL;
//         if(isCharging)
//         {
//        	 int chargePlug = arg1.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//        	 if(chargePlug==0)
//        	 {
//        		 
//        	 }
//        	 else
//        	 {
//        		 
//        	 }
//         }


}
