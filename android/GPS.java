package MsgMgr;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;


public class GPS{
	
	/**
	 * @param  context  ��ǰActivity����
	 * @return Location û�з���null
	 */
	
	public Location get(Context context){
		LocationManager lom=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Location loc =lom.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		
		
		if(loc != null){
			return loc;
		}
		return null;
	}
}
