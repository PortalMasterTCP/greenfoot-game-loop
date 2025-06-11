import greenfoot.*;
import java.util.*;
/**
 * Label that displays the current fps of the GameLoop ever second
 * 
 * @author Portal
 * @version 1.0.0
 */
public class FPSDisplay extends Label
{
    private ArrayList<Double> fpsHistory = new ArrayList<Double>();
    private double accum = 0.0;
    private double fps = 0.0;
    
    public FPSDisplay(int size) { super("", size); }
    
    public void update(double delta) {
        fpsHistory.add(delta);
        accum += delta;
        
        if (accum >= 1.0) {
            double sum = 0.0;
            for (double i : fpsHistory) { sum += i; }
            double avg = sum / fpsHistory.size();
            fps = 1.0 / avg;
            setValue("FPS: " + String.format("%.2f", fps));
            updateImage();
            fpsHistory.clear();
            accum = 0.0;
        }
        
    }
}
