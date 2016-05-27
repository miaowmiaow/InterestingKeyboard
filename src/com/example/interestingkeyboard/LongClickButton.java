package com.example.interestingkeyboard;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class LongClickButton extends Button {

	private LongClickRepeatListener repeatListener;

	private long intervalTime;

	private MyHandler handler;

	public LongClickButton(Context context) {
		super(context);

		init();
	}

	public LongClickButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		init();
	}

	public LongClickButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		init();
	}

	private void init() {
		handler = new MyHandler(this);
		setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				new Thread(new LongClickThread()).start();
				return true;
			}
		});
	}

	private class LongClickThread implements Runnable {
		private int num;

		@Override
		public void run() {
			while (LongClickButton.this.isPressed()) {
				num++;
				if (num % 5 == 0) {
					handler.sendEmptyMessage(1);
				}

				SystemClock.sleep(intervalTime / 5);
			}
		}
	}

	private static class MyHandler extends Handler {
		private WeakReference<LongClickButton> ref;

		MyHandler(LongClickButton button) {
			ref = new WeakReference<LongClickButton>(button);
		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			LongClickButton button = ref.get();
			if (button != null && button.repeatListener != null) {
				button.repeatListener.repeatAction(button);
			}
		}
	}

	public void setLongClickRepeatListener(LongClickRepeatListener listener, long intervalTime) {
		this.repeatListener = listener;
		this.intervalTime = intervalTime;
	}

	public void setLongClickRepeatListener(LongClickRepeatListener listener) {
		setLongClickRepeatListener(listener, 50);
	}

	public interface LongClickRepeatListener {
		void repeatAction(View v);
	}
}