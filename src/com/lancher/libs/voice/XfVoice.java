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
    private SpeechSynthesizer mTts;          //�����ϳɶ���  

    public XfVoice(){}  
    public XfVoice(Context context){  
        init(context);  
    }  
    public void init(Context context){ 
    	// ����������ת�������
    	SpeechUtility.createUtility(context, SpeechConstant.APPID +"=57e12014");   
        //��ʼ����������  
        mTts = SpeechSynthesizer.createSynthesizer(context, mTtsInitListener);  
        //������ʼ��  
        setParam();   
    }  
    //���ź���  
    public void play(String str){  
        String text = str;  
		//SpeechUtility.getUtility().openEngineSettings(null);				
		SpeechUtility.getUtility().openEngineSettings(SpeechConstant.ENG_TTS);	
        setParam();  
        // ���ò���  
        int code = mTts.startSpeaking(text,  mTtsListener);  
        if (code != 0) {  
            System.out.println("start speak error : " + code);  
        } else  
            System.out.println("start speak success.");  
    }  
    //�رղ���  
    public void Cancel(){  
        mTts.stopSpeaking();  
        System.out.println("�رղ��ţ�����");  
    }  
    //��ͣ  
    public void pause(){  
        mTts.pauseSpeaking();  
    }  
      
    //����  
    public void resume(){  
        mTts.resumeSpeaking();  
        System.out.println();  
    }         
    /** 
     * �������� 
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
     * ���ڻ������� 
     */  
    InitListener mTtsInitListener = new InitListener() {  
		@Override
		public void onInit(int arg0) {
			// TODO Auto-generated method stub
			
		}  
    };  
    /** 
//     * �ϳɻص������� 
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