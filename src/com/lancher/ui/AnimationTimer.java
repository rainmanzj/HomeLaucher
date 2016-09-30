package com.lancher.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.coverflow.Application;
import com.coverflow.HelloAndroid;
import com.coverflow.R;

public class AnimationTimer extends Activity {
	private TextView txtAnimation;
	private Animation Animation;
	private int count = 10;
	private int flag = 0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activty_animationtimer);
		txtAnimation = (TextView) findViewById(R.id.text);
		Animation = AnimationUtils.loadAnimation(this, R.anim.count_down_exit);
		txtAnimation.startAnimation(Animation);
		handler.sendEmptyMessageDelayed(0, 1000);
	}

	public void small() {
		Animation.reset();
		txtAnimation.startAnimation(Animation);
	}

	public void big() {
		Animation.reset();
		txtAnimation.startAnimation(Animation);
	}

	private int getCount() throws InterruptedException {
		count--;
		if (count < 1 || count > 9) {
			this.finish();
			Application.Instance().shutdown(); 
		}
		else
		{
			
		}
		return count;
	}
	@Override
	public void onDestroy(){

	}
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				try {
					int count=getCount();
					if(	count<0)
						return;
					txtAnimation.setText("" + count);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(0, 1000);
				small();
			} else {
				try {
					int count=getCount();
					if(	count<0)
						return;
					txtAnimation.setText("" + getCount());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessageDelayed(1, 1000);
				big();
			}
		};
	};

}
