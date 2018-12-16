package com.elasticsearch.example.demo.util;

public class DistanceUtil {

    public static final int EartRadiusKM = 6371;

    public static Double harvesineDistance(Double latSourceDeg, Double longSourceDeg, Double latDestinationDeg, Double longDestinationDeg){
        Double dLat = degreeToRadian(latDestinationDeg - latSourceDeg);
        Double dLong = degreeToRadian(longDestinationDeg - longSourceDeg);
        Double a = Math.sin(dLat/2)*Math.sin(dLat/2) + Math.cos(degreeToRadian(latSourceDeg))*Math.cos(degreeToRadian(latDestinationDeg))*Math.sin(dLong/2)*Math.sin(dLong/2);
        Double c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double d = EartRadiusKM*c;
        return d;
    }

    private static Double degreeToRadian(Double degree){
        return degree*(Math.PI/180);
    }
}
