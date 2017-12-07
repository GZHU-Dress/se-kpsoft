package com.example.basic.gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

import com.example.basic.Command;

/**
 * Created by visn on 17-12-7.
 */

public class GPSGetLocationCommand implements Command {
    private Context context;

    public GPSGetLocationCommand(Context context) {
        this.context = context;
    }

    @Override
    public Location execute() {
        LocationManager lom=(LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission")
        Location loc =lom.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return loc;
    }
}
