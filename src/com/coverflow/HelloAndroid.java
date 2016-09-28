package com.coverflow;

import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lancher.libs.voice.IatVoice;
import com.lancher.libs.voice.XfVoice;
import com.lancher.service.BootBroadcastReceiver;
import com.lancher.service.PowerConnectionReceiver;
import com.lancher.ui.AnimationTimer;
import com.lancher.utility.AndroidProcess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;


public class HelloAndroid<RecyclerView> extends AndroidProcess implements OnClickListener{
    private PowerConnectionReceiver PowerRer=null;
	private BootBroadcastReceiver  BootRer=null;
	public IatVoice IatVoice;
	public static XfVoice Voice;

	private Toast mToast;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		   //��title   
	   //requestWindowFeature(Window.FEATURE_NO_TITLE);   
	        //ȫ��   
	   //getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
	       
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
	    HelloAndroid.Voice=new XfVoice(ct);
	    IatVoice=new IatVoice(ct);
	    mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
	    mToast.setGravity(Gravity.BOTTOM, 0, 0);
	    
		//����Gallery�¼�����  
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
					setTitle("�˵����ż�");
					showTip("�ż�");
					Voice.play("�ż�");
					
				}
				else if(arg2== 1)
				{
					setTitle("�˵�������");
					showTip("����");
					Voice.play("����");
				}
				else if(arg2== 2)
				{
					setTitle("�˵�������");
					showTip("����");
					Voice.play("����");
				}
				else if(arg2== 3)
				{
					setTitle("�˵����ػ�");
					showTip("�ػ�");
					Voice.play("�ػ�");
				}
				else
				{
					showTip("");
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
		welcome();
        PowerRer=new PowerConnectionReceiver();
		registerReceiver(PowerRer, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
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
	      
	    }  
	      
	    @Override  
	    protected void onPause() {  
	        // TODO Auto-generated method stub  
	        super.onPause();  
	      
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
			IatVoice.Listen();
		}
		else if(id==3)
		{
			new AlertDialog.Builder(this).setTitle("ȷ���˳���")  
		     .setIcon(android.R.drawable.ic_dialog_info)  
		     .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
		   
		         @Override  
		         public void onClick(DialogInterface dialog, int which) {  
		 			Voice.play("10���ϵͳ���ر�");

		 			Intent intent=new Intent(HelloAndroid.this, AnimationTimer.class);
		 			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//ע�Ȿ�е�FLAG����
		 			startActivity(intent);
					 
		   
		         }  
		     })  
		     .setNegativeButton("����", new DialogInterface.OnClickListener() {  
		   
		         @Override  
		         public void onClick(DialogInterface dialog, int which) {  
		         // ��������ء���Ĳ���,���ﲻ����û���κβ���  
		         }  
		     }).show();  
		}
	}
	
	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	private void welcome()
	{
		new Thread(){
			public void run(){
//				try
//				{
					HelloAndroid.Voice.play("��ӭʹ�ã�С���������ȫ��");
//				}
//				catch(Exception ex)
//				{
//				
//				}
			}
			
		}.start();
	}
	public  static void shutdown() {
		new Thread() {
            public void run() {
                try { 
                      
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
	 * ִ������
	 * @param command
	 * 1����ȡrootȨ�� "chmod 777 "+getPackageCodePath()
	 * 2���ػ� reboot -p
	 * 3������ reboot
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