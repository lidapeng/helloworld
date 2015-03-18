package com.dapeng.geouta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.sax.Element;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BusTrackerActivity extends MapActivity {
	
	private List<Overlay> mapOverlays = null;
	private Drawable drawable = null;
	private MyItemizedOverlay itemizedOverlay = null;
	private Boolean m_bOpen = false;
	private LinearLayout linearLayout;
	
	/**
	 * Google Map View
	 */
	private MyMapView mapView;
//	private TextView txtView = null;
	private Timer myTimer;
	private Thread dataThread = null;
	private DataProcessingTask dataTask = null;
	/**
	 * route number with default value 2
	 */
	private int routeNumber = 2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        mapView = (MyMapView) findViewById(R.id.mapview);
//        mapView.bindActivity(this);

//        Button btnSearchRoute = (Button)findViewById(R.id.button_Route);
//        btnSearchRoute.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(BusTrackerActivity.this, MyActivity.class);
//				startActivityForResult(intent, 1);				
//			}
//		});
        
//        Button btnPreference = (Button)findViewById(R.id.button_Preference);
//        btnPreference.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.putExtra("name", "hellotest");
//				intent.setClass(BusTrackerActivity.this, MyPreferenceActivity.class);
//				startActivityForResult(intent, 2);				
//			}
//		});
//        ImageButton imgBtn = (ImageButton)findViewById(R.id.imageButton_About);
//        imgBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(BusTrackerActivity.this, AboutGeoUTAActivity.class);
//				startActivity(intent);
//			}
//		});

    	m_bOpen = true;
    	
    	//set the user's preference
//    	setUserPreference();
    	
    	//display the current route number usning a Toast
//		Toast myToast = Toast.makeText(getBaseContext(), "route: " + Integer.toString(routeNumber), Toast.LENGTH_LONG);
//		View v = myToast.getView().findViewById(android.R.id.message);
//		
//		v.setBackgroundColor(Color.TRANSPARENT);
//		
//    	myToast.show();
    	
    	//start to retrieve data from UTA webservice
//    	startDataProcessing(mapView);
    }
    public void startDataProcessing(View view) {
		dataTask = (DataProcessingTask) new DataProcessingTask();
		dataTask.execute();
    	
	}
    public void stopDataProcessing(View view) {
		dataTask.cancel(true);
	}


	@Override
	protected void onStop() {
//		stopDataProcessing(mapView);
		super.onStop();
		
	}
	

	@Override
	public void finish() {
		// TODO Auto-generated method stub
//		stopDataProcessing(mapView);
		super.finish();
	}


	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		//super.onBackPressed();
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("Are you sure you want to exit?")
//		       .setCancelable(false)
//		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		        	   BusTrackerActivity.this.finish();
//		           }
//		       })
//		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		                dialog.cancel();
//		           }
//		       });
//		AlertDialog alert = builder.create();
//		alert.show();
		
		super.onBackPressed();
	}


	private class DataProcessingTask extends AsyncTask<Void, Integer, Void>{

		//constant variables for online query (UTA webservice)		
        private final String KEY_ITEM1 = "ResponseTimestamp";
        private final String KEY_ITEM = "VehicleMonitoringDelivery"; 
        private final String KEY_ITEM2 = "VehicleActivity";
        private final String ROOT_ELEMENT = "Siri";
        
        //A list that hold all vehicles returned from the UTA webservice
        private ArrayList<VehicleJourney> vehicleList = null;
        
		@Override
		protected Void doInBackground(Void... params) {
			
	        String strUrl = "http://api.rideuta.com/SIRI/SIRI.svc/VehicleMonitor/ByRoute?route="+ Integer.toString(routeNumber) + "&onwardcalls=true&usertoken=UMETMBIBKTB";
//	        Log.d("EERR", Integer.toString(routeNumber));
	        Log.d("UTA",strUrl);
	        URL url = null;
	        while(m_bOpen){
		        
				try {
		
					url = new URL(strUrl);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
			        InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());
			        
			        SAXParserFactory sf = SAXParserFactory.newInstance();
		
			        SAXParser sp = sf.newSAXParser();
			        MyXMLReader xmlReader = new MyXMLReader();
			        sp.parse(geocoderResultInputSource, xmlReader); 
//			        if (vehicleList.size() > 0) {
//			        	vehicleList.clear();
//					}
			        
			        vehicleList = xmlReader.getVehicleList();
			        if (vehicleList.size() > 0) {
//			        	for(VehicleJourney v: vehicleList){
//			        		Log.d("EERR", v.getStrLineName() + ": " + v.getLatitude() + ", " +v.getLongitude());
//			        	}
			        	displayBus(vehicleList);
					}
			        //make the thread sleep 10ms
			        Thread.sleep(10);
			        
			        //if this task is canceled, break out of the while loop
			        if (isCancelled()) {
			        	vehicleList = null;
						break;
					}
		
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
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			mapOverlays = mapView.getOverlays();
//			mapOverlays.clear();
			//Toast myToast = Toast.makeText(getBaseContext(), "route: " + Integer.toString(routeNumber), Toast.LENGTH_LONG);

		}

		@Override
		protected void onPostExecute(Void result) {
			
			super.onPostExecute(result);
		
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			
			super.onProgressUpdate(values);
		}
		
		public void displayBus(ArrayList<VehicleJourney> vehicleList) {
			//txtView.setText("location: " + pt.getLongitudeE6()+ ", " + pt.getLongitudeE6());
			//mapView.refreshDrawableState();
			//mapOverlays = (ArrayList<OverlayItem>)mapView.getOverlays();
			mapOverlays = mapView.getOverlays();
	        drawable = getResources().getDrawable(R.drawable.busstop);
	        itemizedOverlay = new MyItemizedOverlay(drawable);
	        itemizedOverlay.bindActivity(BusTrackerActivity.this);
	        
	        //*1000000
	        for (VehicleJourney v :vehicleList) {
	            GeoPoint pt = new GeoPoint((int)(v.getLatitude()*1000000), (int)(v.getLongitude()*1000000));
	            OverlayItem overlayitem = new OverlayItem(pt, "", "");
	            itemizedOverlay.addOverlay(overlayitem);
			}

	        //clear the map overlays and refresh the map
	        mapOverlays.clear();
	        mapOverlays.add(itemizedOverlay);
	        mapView.getController().scrollBy(0, 1);
	        mapView.getController().scrollBy(0, -1);
		}
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	public static String[]  readFileByLines(String fileName) {
        BufferedReader reader = null;
        String [] values = null;
        try {
        	FileReader freader = new FileReader(new File(fileName).getAbsoluteFile());
            reader = new BufferedReader(freader);
            String tempString = null;
            if (null == reader) {
				return null;
			}
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("line " + line + ": " + tempString);
                //line++;
            	values = tempString.split(",");

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return values;
    }
	public void mainDisplayBus(ArrayList<VehicleJourney> vehicleList) {
		//mapView.refreshDrawableState();
		//mapOverlays = (ArrayList<OverlayItem>)mapView.getOverlays();
		mapOverlays = mapView.getOverlays();
        drawable = getResources().getDrawable(R.drawable.busstop);
        itemizedOverlay = new MyItemizedOverlay(drawable);
        itemizedOverlay.bindActivity(BusTrackerActivity.this);
        
        //*1000000
        for (VehicleJourney v :vehicleList) {
            GeoPoint pt = new GeoPoint((int)(v.getLatitude()*1000000), (int)(v.getLongitude()*1000000));
            OverlayItem overlayitem = new OverlayItem(pt, "", "");
            itemizedOverlay.addOverlay(overlayitem);
		}

        mapOverlays.clear();
        mapOverlays.add(itemizedOverlay);
        mapView.invalidate();
//        mapView.getController().scrollBy(0, 1);
//        mapView.getController().scrollBy(0, -1);
	}
	public void refresh(String str){
		mapView.invalidate();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//startDataProcessing(mapView);
	}
	public void updateMap() {
		while(true){
			mapView.invalidate();
		}
	}

	public MyMapView getMapView() {
		return mapView;
	}
	@Override
	protected void onPause() {
		super.onPause();
		stopDataProcessing(mapView);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
			if (null != data) {
				setRouteNumber(data.getIntExtra("route", 0));
				if (isRouteNumberValid(routeNumber)) {
					stopDataProcessing(mapView);
					startDataProcessing(mapView);
					Toast myToast = Toast.makeText(getBaseContext(), "route: " + Integer.toString(routeNumber), Toast.LENGTH_LONG);
					myToast.show();
				}
				else
				{
					Toast myToast = Toast.makeText(getBaseContext(), "invalid route", Toast.LENGTH_LONG);
					myToast.show();
				}
				
//				Log.d("EERR", Integer.toString(routeNumber));

			}
			else{
				Toast myToast = Toast.makeText(getBaseContext(), "invalid route", Toast.LENGTH_LONG);
				myToast.show();
			}
		}
		
	}
	/**
	 * @param num set the current route number to query and to display
	 */
	public void setRouteNumber(int num) {
		routeNumber = num;
	}
	/**
	 * Use the user's preference to initialize the system
	 */
	public void setUserPreference() {
		try {
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
//			Log.d("EERR", sharedPref.getString("defaultroute", "hell"));
			setRouteNumber(Integer.parseInt(sharedPref.getString("defaultroute", "2")));
			
		} catch (Exception e) {
//			Log.d("EERR", e.toString());
		}
	
	}
	
	/**
	 * @param num route number 
	 * @return whether the route number is valid or not
	 */
	public boolean isRouteNumberValid(int num) {
		
        String strUrl = "http://api.rideuta.com/SIRI/SIRI.svc/VehicleMonitor/ByRoute?route="+ Integer.toString(num) + "&onwardcalls=true&usertoken=UMETMBIBKTB";
//        Log.d("EERR", Integer.toString(num));
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
	        if (vList.size() > 0) {
	        	return true;
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		return false;
	
	}
}