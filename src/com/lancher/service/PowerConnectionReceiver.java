package com.lancher.service;

import com.coverflow.HelloAndroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerConnectionReceiver extends BroadcastReceiver {

	public static final String ACTION_REQUEST_SHUTDOWN="android.intent.action.ACTION_REQUEST_SHUTDOWN";	 
	public static final String EXTRA_KEY_CONFIRM="android.intent.extra.KEY_CONFIRM";
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
	}

}
