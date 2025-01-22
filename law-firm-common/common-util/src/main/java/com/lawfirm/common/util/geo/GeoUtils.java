package com.lawfirm.common.util.geo;

public class GeoUtils {
    private static final double EARTH_RADIUS = 6371.0; // 地球半径，单位km
    
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS * c;
    }
    
    public static boolean isWithinRadius(double centerLat, double centerLon, double pointLat, double pointLon, double radiusKm) {
        double distance = calculateDistance(centerLat, centerLon, pointLat, pointLon);
        return distance <= radiusKm;
    }
    
    public static boolean isWithinRectangle(double lat, double lon, double southWestLat, double southWestLon,
                                          double northEastLat, double northEastLon) {
        return lat >= southWestLat && lat <= northEastLat &&
                lon >= southWestLon && lon <= northEastLon;
    }
} 