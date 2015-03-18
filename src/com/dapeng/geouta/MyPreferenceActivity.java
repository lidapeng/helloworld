package com.dapeng.geouta;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class MyPreferenceActivity extends PreferenceActivity {

	public MyPreferenceActivity() {
		
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle("Preference");
		addPreferencesFromResource(R.xml.preferences);
		
	}
	

}
