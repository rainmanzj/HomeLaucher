package com.coverflow;

import java.io.DataOutputStream;

import com.lancher.libs.voice.XfVoice;

public class Application {
	public static Application App=null;
	public static HelloAndroid MainActivity=null;
	public static XfVoice Voice;
	private Application() {
		// TODO Auto-generated constructor stub
	}

	public static Application Instance()
	{
		if(App==null)
			App=new Application();
		return App;
	}
	
	public static void Destory()
	{
		Voice.destroy();
	}
	
	public  void shutdown() {
		new Thread() {
            public void run() {
                try { 
                	Voice.play("ϵͳ10���رգ�˾���³�ǰ����͹غ��Ŵ�");
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
}
