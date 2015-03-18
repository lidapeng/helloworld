package com.dapeng.geouta;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.R.bool;


public class MyXMLReader extends DefaultHandler {
	java.util.Stack tags = new java.util.Stack();
	
	private boolean isLineRef = false;
	private boolean isDirectionRef = false;
	private boolean isLineName = false;
	private boolean isLat = false;
	private boolean isLong = false;
	private double latitude = 0.0;
	private double longitude = 0.0;
	private boolean isVehicleJourney = false;
	private ArrayList<VehicleJourney> vehicleList = new ArrayList<VehicleJourney>();
	private VehicleJourney vehicle= null;
	//private Logger log = new Logger("mylog", null);
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//super.startElement(uri, localName, qName, attributes);
	
		if ("MonitoredVehicleJourney".equals(qName)) {
			isVehicleJourney = true;
			if (vehicle == null) {
				vehicle = new VehicleJourney();
			}
		}
		if ("LineRef".equals(qName)) {
			isLineRef = true;
		}
		if ("DirectionRef".equals(qName)) {
			isDirectionRef = true;
		}
		if ("PublishedLineName".equals(qName)) {
			isLineName = true;
		}
		
		if ("Latitude".equals(qName)) {
			isLat = true;
		}
		if ("Longitude".equals(qName)) {
			isLong = true;
		}
		
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		//System.out.println("****************End Document****************");
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if ("MonitoredVehicleJourney".equals(qName)) {
			isVehicleJourney = false;
			System.out.println(vehicleList.size());

			if (vehicle!=null) {
				vehicleList.add(vehicle);
				vehicle = null;
			}
			
		}
		if ("LineRef".equals(qName)) {
			isLineRef = false;
		}
		if ("DirectionRef".equals(qName)) {
			isDirectionRef = false;
		}
		if ("PublishedLineName".equals(qName)) {
			isLineName = false;
		}
		if ("Longitude".equals(qName)) {
			isLong = false;
		}
		
		if ("Latitude".equals(qName)) {
			isLat = false;
		}

		
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		//System.out.println("****************Start Document****************");

	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		super.characters(arg0, arg1, arg2);
		String s = new String(arg0, arg1, arg2);
		if (isVehicleJourney) {
			//VehicleJourney vehicle = new VehicleJourney();
			if (vehicle != null) {
				if (isLineRef) {
					vehicle.setStrLineRef(s);
				}
				if (isDirectionRef) {
					vehicle.setStrDirectionRef(s);
				}
				if (isLineName) {
					vehicle.setStrLineName(s);
				}
				if (isLong) {
					longitude = Double.parseDouble(s);
					//System.out.println("longitude : " + s);
					vehicle.setLongitude(longitude);
				}
				if (isLat) {
					latitude = Double.parseDouble(s);
					vehicle.setLatitude(latitude);
				}
			}
			
		}
	}

	public ArrayList<VehicleJourney> getVehicleList() {
		return vehicleList;
	}
	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	} 
	
}
