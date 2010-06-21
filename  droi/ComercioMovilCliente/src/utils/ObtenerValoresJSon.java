package utils;

import java.util.Iterator;
import   org.json.JSONObject;
import android.os.Bundle;
import android.util.Log;

public class ObtenerValoresJSon {
	private JSONObject JSONObj;
	private Bundle bundleResult;
	
	public ObtenerValoresJSon(){
		JSONObj = new JSONObject();
		bundleResult = new Bundle();
	}
	
    public Bundle valores(String cadena){

    	String value;
    	try{
     	    JSONObj = new JSONObject(cadena); 
     	    Iterator<String> itr = JSONObj.keys(); 
     	    while (itr.hasNext()) { 
     	        String Key = (String) itr.next(); 
     	        value = JSONObj.getString(Key); 
     	        if (value!=null){
     	        	bundleResult.putString(Key, value);
     	        }
     	        else{
     	        	bundleResult.putString(Key, "_");
     	        }
     	        
         }
    } catch (Exception e) {
    	Log.e("error" ,e.toString());
    }
        return bundleResult;
    }
}
