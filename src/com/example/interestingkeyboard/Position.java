package com.example.interestingkeyboard;

import android.graphics.Rect;
import android.view.View;

public class Position {

	public static Rect getGlobalVisibleRect(View v) {
		int[] position = new int[2];
		v.getLocationOnScreen(position);
		Rect mRect = new Rect();
		mRect.left = position[0];
		mRect.top = position[1];
		mRect.right = mRect.left + v.getWidth();
		mRect.bottom = mRect.top + v.getHeight();
		return mRect;
	}

	public static Rect getRealVisibleRect(View v) {
		int[] position = new int[2];
		v.getLocationOnScreen(position);
		Rect bounds = new Rect();
		boolean isInScreen = v.getGlobalVisibleRect(bounds);
		Rect mRect = new Rect();
		mRect.left = position[0];
		mRect.top = position[1];
		if (isInScreen) {
			mRect.right = mRect.left + bounds.width();
			mRect.bottom = mRect.top + bounds.height();
		} else {
			mRect.right = mRect.left;
			mRect.bottom = mRect.top;
		}
		return mRect;
	}
}
