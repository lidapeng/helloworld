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

import android.widget.TextView;

public class UTADataQuery implements Runnable {

//	public UTADataQuery(BusTrackerActivity mapActivity) {
//		super();
//		this.mapActivity = mapActivity;
//	}

	private BusTrackerActivity mapActivity;
	private ArrayList<VehicleJourney> vehicleList = null;
	public ArrayList<VehicleJourney> getVehicleList() {
		return vehicleList;
	}
	public void bindActivity(BusTrackerActivity activity){
		mapActivity = activity;
	}

	@Override
	public void run() {
        final String KEY_ITEM1 = "ResponseTimestamp";
        final String KEY_ITEM = "VehicleMonitoringDelivery"; 
        final String KEY_ITEM2 = "VehicleActivity";
        final String ROOT_ELEMENT = "Siri";
        String strUrl = "http://api.rideuta.com/SIRI/SIRI.svc/VehicleMonitor/ByRoute?route=2&onwardcalls=true&usertoken=UMETMBIBKTB";
        URL url = null;
        
		try {
			url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

	        InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
	        
	        SAXParserFactory sf = SAXParserFactory.newInstance();

	        SAXParser sp = sf.newSAXParser();
	        MyXMLReader xmlReader = new MyXMLReader();
	        sp.parse(geocoderResultInputSource, xmlReader); 
	        ArrayList<VehicleJourney> vList = xmlReader.getVehicleList();
	        double lon = xmlReader.getLongitude();
	        if (vList.size() > 0) {
	        	VehicleJourney v = vList.get(0);
//	        	if(mapActivity!=null)
//	        	{
//	        		mapActivity.displayBus(vehicleList);
//	        		mapActivity.refresh(v.getStrLineRef() + " " +v.getStrDirectionRef() + " " +Double.toString(v.getLongitude()) + "," + Double.toString(v.getLatitude()));
//	        	}
			}
	       // mapView.displayZoomControls(true);
	        if (!vList.equals(vehicleList)) {
				vehicleList = vList;
			}
	        Thread.sleep(1000);
	        

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
