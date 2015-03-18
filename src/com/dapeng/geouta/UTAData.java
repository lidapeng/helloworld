package com.dapeng.geouta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import android.content.Context;
import android.util.Log;

public class UTAData {

	public UTAData() {
		super();
		routes = new Vector<String[]>();
	}

	private Vector<String[]> routes = null;

	public Vector<String[]> getRoutes() {
		return routes;
	}

	public void setRoutes(Vector<String[]> routes) {
		this.routes = routes;
	}
	public boolean readRoutes(Context context) {
		String fileName = "routes.txt";
        BufferedReader reader = null;
        String [] values = null;
        try {
        	//FileReader freader = new FileReader(new File(fileName).getAbsoluteFile());
        	InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            reader = new BufferedReader(inputReader);
            String tempString = null;
            if (null == reader) {
				return false;
			}
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("line " + line + ": " + tempString);
                //line++;
            	values = tempString.split(",");
            	String [] record = new String[3];
            	record[0] = values[0];
            	record[1] = values[2];
            	record[2] = values[3];
            	//System.out.println(tempString);
            	routes.add(record);
            	//Log.i("Line", record[2]);

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                	e1.printStackTrace();
                }
            }
        }
		
		return true;
	}
	
	
}
