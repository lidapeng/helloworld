package com.dapeng.geouta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends Activity {

	private TextView txtView = null;
	private Button myBtn = null;
	private EditText inputTxt = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylayout);
		
		inputTxt = (EditText)findViewById(R.id.editText1);
		this.setTitle("Search a route");
		myBtn = (Button)findViewById(R.id.button1);
		myBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.putExtra("route", Integer.parseInt(inputTxt.getText().toString()));
				Log.d("EERR",inputTxt.getText().toString() );
//				i.putExtra("route", 220);
				setResult(Activity.RESULT_OK, i);
				finish();
			}
		});
		
	}

}
