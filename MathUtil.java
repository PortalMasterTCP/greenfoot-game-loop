import greenfoot.*;
import java.util.*;
/**
 * Singleton with misc math functions not provided by the default Math class
 * 
 * @author Portal
 * @version 1.0.0
 */
public final class MathUtil  
{
    // epsilon represented as a double
    public static final double EpsilonD = 1E-14;
    
    private MathUtil() {}
    
    // linear interpolate
    public static double lerp(double from, double to, double weight) { 
        return (from * (1.0 - weight)) + (to * weight); 
    }
    
    // check for approximate equality, see the Godot engine implementation for details
    public static boolean isEqualApprox(double a, double b) { 
        if (a == b) { return true; }
        double tolerance = EpsilonD * Math.abs(a);
        if (tolerance < EpsilonD) { tolerance = EpsilonD; }
        return (Math.abs(a - b) < tolerance);
    }
    
    // limit n between low and high
    public static double clamp(double n, double low, double high) { return Math.min(Math.max(n, low), high); }
    public static double clamp(double n, double high) { return clamp(n, 0.0, high); }
    
    // clamp but it wraps around to the other end
    public static int wrap(int n, int high) { return wrap(n, 0, high); }
    public static int wrap(int n, int low, int high) { 
        if (n > high) { n = Math.abs(n % high) + low - 1; } 
        else if (n < low) { n = low - n; n = high - n + 1; }
        return n;
    }
    
    public static double wrap(double n, double high) { return wrap(n, 0.0, high); }
    public static double wrap(double n, double low, double high) { 
        if (n > high) { n = Math.abs(n % high) + low - 1.0; } 
        else if (n < low) { n = low - n; n = high - n + 1.0; }
        return n;
    }
    
    public static int clamp(int n, int low, int high) { return Math.min(Math.max(n, low), high); }
    public static int clamp(int n, int high) { return clamp(n, 0, high); }
    
    // similar to Godot engine's get_axis
    public static double getAxis(boolean neg, boolean pos) {
        double n = (neg) ? 1.0 : 0.0;
        double p = (pos) ? 1.0 : 0.0;
        return p - n;
    }
    public static int getAxisInt(boolean neg, boolean pos) {
        int n = (neg) ? 1 : 0;
        int p = (pos) ? 1 : 0;
        return p - n;
    }
    
    public static int boolToInt(boolean b) {
        int ret = (b) ? 1 : 0;
        return ret;
    }
    
    // shorthand for 2 getAxis calls
    public static Vector2 getAxisVector(boolean up, boolean down, boolean left, boolean right) { 
        return new Vector2(getAxis(left, right), getAxis(up, down)); 
    }

    public static <T> T[] appendArray(T[] arr, T[] append) {
        ArrayList<Object> a = new ArrayList<Object>();
        for (int i = 0; i < arr.length; i++) { a.add(arr[i]); }
        for (int i = 0; i < append.length; i++) { a.add(append[i]); }
        return (T[]) a.toArray(arr);
        
    }
    
    // (hopefully) better randoms
    public static int randiRange(int min, int max) { return (int) (Math.random() * (max - min + 1)) + min; }
    public static double randRange(double min, double max) { return (double) (Math.random() * (max - min + 1.0)) + min; }
}
