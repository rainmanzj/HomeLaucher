package com.lancher.libs.voice;

import android.content.Context;  
import android.content.SharedPreferences;  
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;  
  

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;  
import com.iflytek.cloud.SpeechUtility;
  
public class XfVoice {  
    private SpeechSynthesizer mTts;          //语音合成对象  

    public XfVoice(){}  
    public XfVoice(Context context){  
        init(context);  
    }  
    public void init(Context context){ 
    	// 本地设置跳转到语记中
    	SpeechUtility.createUtility(context, SpeechConstant.APPID +"=57e12014");   
        //初始化语音对象  
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);  
        //参数初始化  
        setParam();   
    }  
    //播放函数  
    public void play(String str){  
        String text = str;  
		//SpeechUtility.getUtility().openEngineSettings(null);				
		SpeechUtility.getUtility().openEngineSettings(SpeechConstant.ENG_TTS);	
        setParam();  
        // 设置参数  
        int code = mTts.startSpeaking(text,  mTtsListener);  
        if (code != 0) {  
            System.out.println("start speak error : " + code);  
        } else  
            System.out.println("start speak success.");  
    }  
    //关闭播放  
    public void Cancel(){  
        mTts.stopSpeaking();  
        System.out.println("关闭播放！！！");  
    }  
    //暂停  
    public void pause(){  
        mTts.pauseSpeaking();  
    }  
      
    //继续  
    public void resume(){  
        mTts.resumeSpeaking();  
        System.out.println();  
    }         
    /** 
     * 参数设置 
     * @param param 
     * @return  
     */  
    private void setParam(){  
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");  
          
          
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");  
  
        mTts.setParameter(SpeechConstant.SPEED, "50");  
          
        mTts.setParameter(SpeechConstant.PITCH, "50");  
          
        mTts.setParameter(SpeechConstant.VOLUME, "50");  
    }  
      
    public void destroy(){  
         mTts.stopSpeaking();  
       
    }  
      
    /** 
     * 初期化监听。 
     */  
    InitListener mTtsInitListener = new InitListener() {  
		@Override
		public void onInit(int arg0) {
			// TODO Auto-generated method stub
			
		}  
    };  
    /** 
//     * 合成回调监听。 
//     */  
    SynthesizerListener mTtsListener = new  SynthesizerListener() {

		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onCompleted(SpeechError arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSpeakBegin() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSpeakPaused() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSpeakResumed() {
			// TODO Auto-generated method stub
			
		}  
  
      
    };  
      
  
}  