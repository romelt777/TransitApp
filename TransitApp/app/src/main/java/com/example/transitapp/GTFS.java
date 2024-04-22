package com.example.transitapp;

import java.net.URL;

import com.google.transit.realtime.GtfsRealtime.FeedEntity;
import com.google.transit.realtime.GtfsRealtime.FeedMessage;


public class GTFS {

    public static void main(String[] args) throws Exception {
        URL url = new URL("https://gtfs.halifax.ca/realtime/Vehicle/VehiclePositions.pb");
        FeedMessage feed = FeedMessage.parseFrom(url.openStream());
        for (FeedEntity entity : feed.getEntityList()) {
            System.out.println("yaya: " + entity);
            if (entity.hasTripUpdate()) {
                System.out.println("haha" + entity.getTripUpdate());
            }
        }
    }
}
