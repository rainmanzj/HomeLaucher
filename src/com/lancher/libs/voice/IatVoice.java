package com.lancher.libs.voice;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.coverflow.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.cloud.util.ContactManager.ContactListener;
import com.iflytek.sunflower.FlowerCollector;
import com.lancher.utility.JsonParser;


public class IatVoice {
	private static String TAG = IatVoice.class.getSimpleName();
	Context mainContext;
	// ������д����
	private SpeechRecognizer mIat;
	// ������дUI
	private RecognizerDialog mIatDialog;
	// ��HashMap�洢��д���
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

	private EditText mResultText;
	private Toast mToast;
	private SharedPreferences mSharedPreferences;
	// ��������
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	// ��ǰ�װ������
	 public IatVoice(Context context){  
	        init(context);  
	    }  
	 public void init(Context context){ 
		// ��ʼ��ʶ����UIʶ�����
		// ʹ��SpeechRecognizer���󣬿ɸ��ݻص���Ϣ�Զ�����棻
		mIat = SpeechRecognizer.createRecognizer(context, mInitListener);
		
		// ��ʼ����дDialog�����ֻʹ����UI��д���ܣ����贴��SpeechRecognizer
		// ʹ��UI��д���ܣ������sdk�ļ�Ŀ¼�µ�notice.txt,���ò����ļ���ͼƬ��Դ
		mIatDialog = new RecognizerDialog(context, mInitListener);

		mSharedPreferences = context.getSharedPreferences(IatSettings.PREFER_NAME,
				Activity.MODE_PRIVATE);
		mainContext=context;
	}


	int ret = 0; // �������÷���ֵ

	public void Listen() {
			// �ƶ����ݷ������ռ���ʼ��д�¼�
			FlowerCollector.onEvent(mainContext, "iat_recognize");
			
			//mResultText.setText(null);// �����ʾ����
			mIatResults.clear();
			// ���ò���
			setParam();
			boolean isShowDialog = mSharedPreferences.getBoolean(
					mainContext.getString(R.string.pref_key_iat_show), true);
			if (isShowDialog) {
				// ��ʾ��д�Ի���
				mIatDialog.setListener(mRecognizerDialogListener);
				mIatDialog.show();
				showTip("");
			} else {
				// ����ʾ��д�Ի���
				ret = mIat.startListening(mRecognizerListener);
				if (ret != ErrorCode.SUCCESS) {
					showTip("��дʧ��,�����룺" + ret);
				} else {
					showTip("");
				}
			}
		
	}

	/**
	 * ��ʼ����������
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("��ʼ��ʧ�ܣ������룺" + code);
			}
		}
	};

	/**
	 * �ϴ���ϵ��/�ʱ��������
	 */
	private LexiconListener mLexiconListener = new LexiconListener() {

		@Override
		public void onLexiconUpdated(String lexiconId, SpeechError error) {
			if (error != null) {
				showTip(error.toString());
			} else {
				showTip("1");
			}
		}
	};

	/**
	 * ��д��������
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// �˻ص���ʾ��sdk�ڲ�¼�����Ѿ�׼�����ˣ��û����Կ�ʼ��������
			showTip("��ʼ˵��");
		}

		@Override
		public void onError(SpeechError error) {
			// Tips��
			// �����룺10118(��û��˵��)��������¼����Ȩ�ޱ�������Ҫ��ʾ�û���Ӧ�õ�¼��Ȩ�ޡ�
			// ���ʹ�ñ��ع��ܣ���ǣ���Ҫ��ʾ�û�������ǵ�¼��Ȩ�ޡ�
			showTip(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			// �˻ص���ʾ����⵽��������β�˵㣬�Ѿ�����ʶ����̣����ٽ�����������
			showTip("����˵��");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.d(TAG, results.getResultString());
			printResult(results);

			if (isLast) {
				// TODO ���Ľ��
			}
		}

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			showTip("��ǰ����˵����������С��" + volume);
			Log.d(TAG, "������Ƶ���ݣ�"+data.length);
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// ���´������ڻ�ȡ���ƶ˵ĻỰid����ҵ�����ʱ���Ựid�ṩ������֧����Ա�������ڲ�ѯ�Ự��־����λ����ԭ��
			// ��ʹ�ñ����������ỰidΪnull
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};

	private void printResult(RecognizerResult results) {
		String text = JsonParser.parseIatResult(results.getResultString());

		String sn = null;
		// ��ȡjson����е�sn�ֶ�
		try {
			JSONObject resultJson = new JSONObject(results.getResultString());
			sn = resultJson.optString("sn");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		mIatResults.put(sn, text);

		StringBuffer resultBuffer = new StringBuffer();
		for (String key : mIatResults.keySet()) {
			resultBuffer.append(mIatResults.get(key));
		}

		mResultText.setText(resultBuffer.toString());
		mResultText.setSelection(mResultText.length());
	}

	/**
	 * ��дUI������
	 */
	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			printResult(results);
		}

		/**
		 * ʶ��ص�����.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

	};

	/**
	 * ��ȡ��ϵ�˼�������
	 */
	private ContactListener mContactListener = new ContactListener() {

		@Override
		public void onContactQueryFinish(final String contactInfos, boolean changeFlag) {
			// ע��ʵ��Ӧ���г���һ���ϴ�֮�⣬֮��Ӧ��ͨ��changeFlag�ж��Ƿ���Ҫ�ϴ����������ɲ���Ҫ������.
			// ÿ����ϵ�˷����仯���ýӿڶ����ᱻ�ص�����ͨ��ContactManager.destroy()���ٶ��󣬽���ص���
			// if(changeFlag) {
			// ָ����������
//			runOnUiThread(new Runnable() {
//				public void run() {
//					mResultText.setText(contactInfos);
//				}
//			});
			
			mIat.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
			mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
			ret = mIat.updateLexicon("contact", contactInfos, mLexiconListener);
			if (ret != ErrorCode.SUCCESS) {
				showTip("�ϴ���ϵ��ʧ�ܣ�" + ret);
			}
		}
	};

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	/**
	 * ��������
	 * 
	 * @param param
	 * @return
	 */
	public void setParam() {
		// ��ղ���
		mIat.setParameter(SpeechConstant.PARAMS, null);

		// ������д����
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		// ���÷��ؽ����ʽ
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		String lag = mSharedPreferences.getString("iat_language_preference",
				"mandarin");
		if (lag.equals("en_us")) {
			// ��������
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
		} else {
			// ��������
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// ������������
			mIat.setParameter(SpeechConstant.ACCENT, lag);
		}

		// ��������ǰ�˵�:������ʱʱ�䣬���û��೤ʱ�䲻˵��������ʱ����
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		
		// ����������˵�:��˵㾲�����ʱ�䣬���û�ֹͣ˵���೤ʱ���ڼ���Ϊ�������룬 �Զ�ֹͣ¼��
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		
		// ���ñ�����,����Ϊ"0"���ؽ���ޱ��,����Ϊ"1"���ؽ���б��
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		
		// ������Ƶ����·����������Ƶ��ʽ֧��pcm��wav������·��Ϊsd����ע��WRITE_EXTERNAL_STORAGEȨ��
		// ע��AUDIO_FORMAT���������Ҫ���°汾������Ч
		mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
	}
}