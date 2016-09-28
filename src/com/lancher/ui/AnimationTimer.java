package com.lancher.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import com.coverflow.R;

public class AnimationTimer extends Activity {
	private TextView txtAnimation;
	private Animation Animation;
	private int count = 6;
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

	private int getCount() {
		count--;
		if (count < 1 || count > 6) {
			this.finish();
		}
		return count;
	}
	@Override
	public void onDestroy(){

	}
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				txtAnimation.setText("" + getCount());
				handler.sendEmptyMessageDelayed(0, 1000);
				small();
			} else {
				txtAnimation.setText("" + getCount());
				handler.sendEmptyMessageDelayed(1, 1000);
				big();
			}
		};
	};

}
