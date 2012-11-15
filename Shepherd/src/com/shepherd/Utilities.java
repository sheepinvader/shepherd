package com.shepherd;

import android.graphics.PointF;

public class Utilities {
    public static double getDistance(ICartesian2D obj1, ICartesian2D obj2)
    {
        double dx = obj2.getX() - obj1.getX();
        double dy = obj2.getY() - obj1.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double getDistance(ICartesian2D obj1, PointF obj2)
    {
        double dx = obj2.x - obj1.getX();
        double dy = obj2.y - obj1.getY();
        return Math.sqrt(dx * dx + dy * dy);    	
    }
    
    public static double getAngle(ICartesian2D obj1, ICartesian2D obj2)
    {
        if (obj1.getX() == obj2.getX() && obj1.getY() == obj2.getY())
        	return 0;
        else
        	return Math.atan2(obj2.getY() - obj1.getY(), obj2.getX() - obj1.getX());
    }

    public static double getAngle(ICartesian2D obj1, PointF obj2)
    {
        if (obj1.getX() == obj2.x && obj1.getY() == obj2.y)
        	return 0;
        else
        	return Math.atan2(obj2.y - obj1.getY(), obj2.x - obj1.getX());
    }
}
