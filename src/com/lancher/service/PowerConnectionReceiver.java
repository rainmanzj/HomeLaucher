package com.lancher.service;

import java.lang.reflect.Method;
import java.util.List;

import com.coverflow.HelloAndroid;
import com.lancher.ui.AnimationTimer;

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
			   HelloAndroid.getActivity().setTitle("��Դ�ѽ�ͨ");
		   }
		   else if(status.equals(Intent.ACTION_POWER_DISCONNECTED ))
		   {
			   HelloAndroid.getActivity().setTitle("��Դ�ѶϿ�");
			   /*10���ر�ҳ��*/
			   HelloAndroid.Voice.play("10���ϵͳ���Զ��ر�");
           	   Intent intent=new Intent(HelloAndroid.getActivity(), AnimationTimer.class);
	 		   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//ע�Ȿ�е�FLAG����
	 		   HelloAndroid.getActivity().startActivity(intent);
	 		  
				
		   }
		   else if(status.equals(Intent.ACTION_BATTERY_CHANGED))// android.intent.action.BATTERY_CHANGED))
		   {
			  // HelloAndroid.getActivity().setTitle("�����仯");
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
