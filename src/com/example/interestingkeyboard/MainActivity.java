package com.example.interestingkeyboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et = (EditText) findViewById(R.id.et);
		et.post(new Runnable() {

			@Override
			public void run() {
				new KeyboardPopup(MainActivity.this, et);
			}
		});
	}

}
