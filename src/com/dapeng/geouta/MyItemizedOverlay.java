package com.dapeng.geouta;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.Projection;

public class MyItemizedOverlay extends ItemizedOverlay {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

	private BusTrackerActivity mapActivity;
	public MyItemizedOverlay(Drawable defaultMarker) {
		//super(defaultMarker);
		super(boundCenterBottom(defaultMarker));
		mapActivity = null;
	}
	public void bindActivity(BusTrackerActivity activity){
		mapActivity = activity;
	}
	@Override
	protected OverlayItem createItem(int arg0) {
		 
		return mOverlays.get(arg0);
	}

	@Override
	public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow) {
		if (null != mapActivity) {
//			      if(shadow){
//			            Projection projection = mapView.getProjection();
//			            Point pt = new Point();
//			            GeoPoint globalGeoPoint = mapActivity.getCurrentGeoPoint();
//			            projection.toPixels(globalGeoPoint,pt);
//			            int circleRadius = 40;
//			            
//			            Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//			            circlePaint.setStyle(Style.FILL_AND_STROKE);
//			            canvas.drawCircle((float)pt.x, (float)pt.y, circleRadius, circlePaint);
//
//			            circlePaint.setColor(0x99000000);
//			            circlePaint.setStyle(Style.STROKE);
//			            canvas.drawCircle((float)pt.x, (float)pt.y, circleRadius, circlePaint);
//
//			            Bitmap markerBitmap = BitmapFactory.decodeResource(mapActivity.getApplicationContext().getResources(),R.drawable.logo);
//			            canvas.drawBitmap(markerBitmap,pt.x,pt.y-markerBitmap.getHeight(),null);
//			            
//			            drawCircle(canvas, pt);
//			    	    Paint lp4;
//			    	    lp4 = new Paint();
//			    	    lp4.setColor(Color.RED);
//			    	    lp4.setAlpha(100);
//			    	    lp4.setAntiAlias(true);
//			    	    lp4.setStyle(Style.STROKE);
//			    	    canvas.drawCircle(mapView.getWidth()/2, mapView.getHeight()/2, (float) 30.0, lp4);
//			    	    mapView.invalidate();
			            super.draw(canvas,mapView,shadow);
//			        }
		}
		
	}
	@Override
	public int size() {
		return mOverlays.size();
	}
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	protected void drawCircle(Canvas canvas, Point curScreenCoords) {
	    float CIRCLE_RADIUS = 50.0f;
	    // Draw inner info window
	    canvas.drawCircle((float) curScreenCoords.x, (float) curScreenCoords.y, CIRCLE_RADIUS, getInnerPaint());
	    // if needed, draw a border for info window
	    //canvas.drawCircle(curScreenCoords.x, curScreenCoords.y, CIRCLE_RADIUS, getBorderPaint());
	}
	private Paint innerPaint, borderPaint;

	public Paint getInnerPaint() {
	    if (innerPaint == null) {
	        innerPaint = new Paint();
	        innerPaint.setARGB(225, 68, 89, 82); // gray
	        innerPaint.setAntiAlias(true);
	    }
	    return innerPaint;
	}

	public Paint getBorderPaint() {
	    if (borderPaint == null) {
	        borderPaint = new Paint();
	        borderPaint.setARGB(255, 68, 89, 82);
	        borderPaint.setAntiAlias(true);
	        borderPaint.setStyle(Style.STROKE);
	        borderPaint.setStrokeWidth(2);
	    }
	    return borderPaint;
	}


}
