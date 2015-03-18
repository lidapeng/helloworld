package com.dapeng.geouta;

public class VehicleJourney {

	private String strLineRef = null;
	private String strDirectionRef = null;
	private String strLineName = null;
	private double longitude = 0.0;
	private double latitude = 0.0;

	public String getStrLineRef() {
		return strLineRef;
	}

	public void setStrLineRef(String strLineRef) {
		this.strLineRef = strLineRef;
	}

	public String getStrDirectionRef() {
		return strDirectionRef;
	}

	public void setStrDirectionRef(String strDirectionRef) {
		this.strDirectionRef = strDirectionRef;
	}

	public String getStrLineName() {
		return strLineName;
	}

	public void setStrLineName(String strLineName) {
		this.strLineName = strLineName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
}
