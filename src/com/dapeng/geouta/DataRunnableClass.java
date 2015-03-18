package com.dapeng.geouta;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;
import android.widget.TextView;

/** A Runnalbe Class for processing data*/

public class DataRunnableClass implements Runnable {
	
	private BusTrackerActivity activity = null;
	private int a = 0;

	public DataRunnableClass(BusTrackerActivity activity) {
		super();
		this.activity = activity;
	}


	@Override
	public void run() {
		
		try {
			for(int i = 0;i<10000;i++)
			{
				Thread.sleep(2000);
				a++;
				//activity.refresh(Integer.toString(a));
				Log.e("mythread", Integer.toString(a));
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
//
//        final String KEY_ITEM1 = "ResponseTimestamp";
//        final String KEY_ITEM = "VehicleMonitoringDelivery"; 
//        final String KEY_ITEM2 = "VehicleActivity";
//        final String ROOT_ELEMENT = "Siri";
//        String strUrl = "http://api.rideuta.com/SIRI/SIRI.svc/VehicleMonitor/ByRoute?route=2&onwardcalls=true&usertoken=UMETMBIBKTB";
//        URL url = null;
//        
//		try {
//			Thread.sleep(2000);
//
//			url = new URL(strUrl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//	        InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
//	        
//	        SAXParserFactory sf = SAXParserFactory.newInstance();
//
//	        SAXParser sp = sf.newSAXParser();
//	        MyXMLReader xmlReader = new MyXMLReader();
//	        sp.parse(geocoderResultInputSource, xmlReader); 
//	        ArrayList<VehicleJourney> vehicleList = xmlReader.getVehicleList();
//	        double lon = xmlReader.getLongitude();
//	        if (vehicleList.size() > 0) {
//	        	VehicleJourney v = vehicleList.get(0);
//	        	activity.displayBus(vehicleList);
//	        	activity.getMapView().invalidate();
////	            TextView txtView = (TextView) findViewById(R.id.text1);
////	        	txtView.setText(v.getStrLineRef() + " " +v.getStrDirectionRef() + " " +Double.toString(v.getLongitude()) + "," + Double.toString(v.getLatitude()) );
//			}
//	        Thread.sleep(2000);
//	        
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
	}

}
