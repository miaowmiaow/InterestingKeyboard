package com.example.interestingkeyboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.interestingkeyboard.LongClickButton.LongClickRepeatListener;

public class KeyboardPopup extends PopupWindow implements OnClickListener, LongClickRepeatListener {

	private ViewGroup contentContainer;
	private LongClickButton btn_1;
	private LongClickButton btn_2;
	private LongClickButton btn_3;
	private LongClickButton btn_4;
	private LongClickButton btn_5;
	private LongClickButton btn_6;
	private LongClickButton btn_7;
	private LongClickButton btn_8;
	private LongClickButton btn_9;
	private LongClickButton btn_0;
	private LongClickButton btn_point;
	private LongClickButton btn_delect;
	private Button btn_confirm;

	private Activity activity;
	private EditText et;
	private Editable editable;

	public KeyboardPopup(Activity activity) {
		this.activity = activity;
		init();
	}

	public KeyboardPopup(Activity activity, EditText et) {
		this.activity = activity;
		this.et = et;
		this.editable = et.getText();
		disableSysKeyboard(et);
		et.setSelection(et.length());
		init();
	}

	private void init() {
		contentContainer = (ViewGroup) activity.findViewById(android.R.id.content);
		LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View conentView = inflater.inflate(R.layout.popup_keyboard, null);
		this.setContentView(conentView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(getScreenHeight(activity) / 2);
		this.setFocusable(false);
		this.setOutsideTouchable(true);
		this.setAnimationStyle(R.style.mystyle);
		btn_1 = (LongClickButton) conentView.findViewById(R.id.btn_1);
		btn_2 = (LongClickButton) conentView.findViewById(R.id.btn_2);
		btn_3 = (LongClickButton) conentView.findViewById(R.id.btn_3);
		btn_4 = (LongClickButton) conentView.findViewById(R.id.btn_4);
		btn_5 = (LongClickButton) conentView.findViewById(R.id.btn_5);
		btn_6 = (LongClickButton) conentView.findViewById(R.id.btn_6);
		btn_7 = (LongClickButton) conentView.findViewById(R.id.btn_7);
		btn_8 = (LongClickButton) conentView.findViewById(R.id.btn_8);
		btn_9 = (LongClickButton) conentView.findViewById(R.id.btn_9);
		btn_0 = (LongClickButton) conentView.findViewById(R.id.btn_0);
		btn_point = (LongClickButton) conentView.findViewById(R.id.btn_point);
		btn_delect = (LongClickButton) conentView.findViewById(R.id.btn_delect);
		btn_confirm = (Button) conentView.findViewById(R.id.btn_confirm);
		btn_1.setOnClickListener(this);
		btn_2.setOnClickListener(this);
		btn_3.setOnClickListener(this);
		btn_4.setOnClickListener(this);
		btn_5.setOnClickListener(this);
		btn_6.setOnClickListener(this);
		btn_7.setOnClickListener(this);
		btn_8.setOnClickListener(this);
		btn_9.setOnClickListener(this);
		btn_0.setOnClickListener(this);
		btn_point.setOnClickListener(this);
		btn_delect.setOnClickListener(this);
		btn_confirm.setOnClickListener(this);
		btn_1.setLongClickRepeatListener(this);
		btn_2.setLongClickRepeatListener(this);
		btn_3.setLongClickRepeatListener(this);
		btn_4.setLongClickRepeatListener(this);
		btn_5.setLongClickRepeatListener(this);
		btn_6.setLongClickRepeatListener(this);
		btn_7.setLongClickRepeatListener(this);
		btn_8.setLongClickRepeatListener(this);
		btn_9.setLongClickRepeatListener(this);
		btn_0.setLongClickRepeatListener(this);
		btn_point.setLongClickRepeatListener(this);
		btn_delect.setLongClickRepeatListener(this);
	}

	public void setEditText(EditText et) {
		this.et = et;
		this.editable = et.getText();
		disableSysKeyboard(et);
		et.setSelection(et.length());
	}

	public void setConfirmText(String t) {
		btn_confirm.setText(t);
	}

	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			this.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		}
		// else {
		// this.dismiss();
		// }
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			if (mOnConfirmListener != null) {
				mOnConfirmListener.onConfirm();
			}
			dismiss();
			break;
		case R.id.btn_delect:
			int start = et.getSelectionStart();
			if (editable != null && editable.length() > 0) {
				if (start > 0) {
					Rect bounds = new Rect();
					bounds.set(Position.getGlobalVisibleRect(v));
					float endY = bounds.exactCenterY();
					updateDown(editable.subSequence(start - 1, start).toString(), endY);
					editable.delete(start - 1, start);
				}
			}
			break;
		default:
			Rect bounds = new Rect();
			bounds.set(Position.getGlobalVisibleRect(v));
			float startX = bounds.exactCenterX();
			float startY = bounds.exactCenterY();
			updateUp(((Button) v).getText().toString(), startX, startY);
			editable.insert(et.getSelectionStart(), ((Button) v).getText());
			break;
		}
	}

	@Override
	public void repeatAction(View v) {
		switch (v.getId()) {
		case R.id.btn_delect:
			int start = et.getSelectionStart();
			if (editable != null && editable.length() > 0) {
				if (start > 0) {
					Rect bounds = new Rect();
					bounds.set(Position.getGlobalVisibleRect(v));
					float endY = bounds.exactCenterY();
					updateDown(editable.subSequence(start - 1, start).toString(), endY);
					editable.delete(start - 1, start);
				}
			}
			break;
		default:
			Rect bounds = new Rect();
			bounds.set(Position.getGlobalVisibleRect(v));
			float startX = bounds.exactCenterX();
			float startY = bounds.exactCenterY();
			updateUp(((Button) v).getText().toString(), startX, startY);
			editable.insert(et.getSelectionStart(), ((Button) v).getText());
			break;
		}
	}

	private void updateUp(String str, float startX, float startY) {
		final TextView textView = new TextView(activity);
		textView.setTextColor(Color.parseColor("#333333"));
		textView.setTextSize(25);
		textView.setText(str);
		textView.setGravity(Gravity.CENTER);
		contentContainer.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		textView.measure(0, 0);
		playFlyUp(textView, startX, startY, new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				contentContainer.removeView(textView);
			}
		});
	}

	private void playFlyUp(TextView textView, float startX, float startY, AnimatorListenerAdapter listenerAdapter) {
		int pos = et.getSelectionStart();
		float endX = et.getLayout().getPrimaryHorizontal(pos) + 100;
		float endY = et.getHeight();
		final AnimatorSet animSet = new AnimatorSet();
		ObjectAnimator translationX = ObjectAnimator.ofFloat(textView, "translationX", startX, endX);
		ObjectAnimator translationY = ObjectAnimator.ofFloat(textView, "translationY", startY, endY);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 1.2f, 0.6f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 1.2f, 0.6f);
		animSet.setDuration(1000);
		animSet.addListener(listenerAdapter);
		animSet.playTogether(translationY, translationX, scaleX, scaleY);
		animSet.start();
	}

	private void updateDown(String str, float endY) {
		final TextView textView = new TextView(activity);
		textView.setTextColor(Color.parseColor("#333333"));
		textView.setTextSize(25);
		textView.setText(str);
		textView.setGravity(Gravity.CENTER);
		contentContainer.addView(textView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		textView.measure(0, 0);
		playFlyDown(textView, endY, new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				contentContainer.removeView(textView);
			}
		});

	}

	private void playFlyDown(TextView textView, float endY, AnimatorListenerAdapter listenerAdapter) {
		int pos = et.getSelectionStart();
		float startX = et.getLayout().getPrimaryHorizontal(pos) + 25;
		float startY = et.getHeight();
		float endX = startX;
		final AnimatorSet animSet = new AnimatorSet();
		ObjectAnimator translationX = ObjectAnimator.ofFloat(textView, "translationX", startX, endX);
		ObjectAnimator translationY = ObjectAnimator.ofFloat(textView, "translationY", startY, endY);
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(textView, "scaleX", 0.6f, 1.2f);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(textView, "scaleY", 0.6f, 1.2f);
		animSet.setDuration(1000);
		animSet.addListener(listenerAdapter);
		animSet.playTogether(translationY, translationX, scaleX, scaleY);
		animSet.start();
	}

	private void disableSysKeyboard(final EditText editText) {
		final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		editText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
				showPopupWindow(v);
			}
		});
	}

	private OnConfirmListener mOnConfirmListener;

	public void setOnConfirmListener(OnConfirmListener c) {
		mOnConfirmListener = c;
	}

	public interface OnConfirmListener {
		void onConfirm();
	}

	public static DisplayMetrics getDisplayMetrics(Context context) {
		return context.getResources().getDisplayMetrics();
	}

	public static int getScreenHeight(Context context) {
		DisplayMetrics metrics = getDisplayMetrics(context);
		return metrics.heightPixels;
	}
}
