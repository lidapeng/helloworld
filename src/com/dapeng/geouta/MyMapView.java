package com.dapeng.geouta;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

public class MyMapView extends MapView {

	private BusTrackerActivity mapActivity;

	public MyMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	//	mapActivity = context;

		
	}
	public void bindActivity(BusTrackerActivity activity){
		mapActivity = activity;
		this.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				
				Intent preferenceIntent = new Intent(mapActivity,MyPreferenceActivity.class);
				mapActivity.startActivity(preferenceIntent);
				return true;
			}
		});
	}

	
   @Override
public boolean onTouchEvent(android.view.MotionEvent ev) {

    int action = ev.getAction();
    int X = (int)ev.getX();          
    int Y = (int)ev.getY();        
    
    if(MotionEvent.ACTION_MOVE == action){
    	GeoPoint pt = this.getMapCenter();
    	//mapActivity.updateLocation(pt);
    }            
    
	return super.onTouchEvent(ev);
	
}
   
	    
}
