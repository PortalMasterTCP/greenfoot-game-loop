import greenfoot.*;
import java.util.*;
/**
 * Static Input Map to replace Greenfoot's built-in input handling. For use as a Singleton.
 * Updated as part of GameLoop's runLoop()
 * 
 * @Author Portal
 * @Version 1.0.1
 */
public final class Input
{
    private static HashMap<String, Boolean> inputsPressed = new HashMap<String, Boolean>(); // is it pressed right now?
    private static HashMap<String, Boolean> inputsLastFrame = new HashMap<String, Boolean>(); // was it pressed last frame?
    private static HashMap<String, Boolean> inputsEvents = new HashMap<String, Boolean>(); // Was it pressed just now but not before?
    
    private Input() {}
    
    static {
        // add different inputs here via this static block
        // they should match the built-in names of the keys
        inputsPressed.put("w", false);
        inputsPressed.put("a", false);
        inputsPressed.put("s", false);
        inputsPressed.put("d", false);
        inputsPressed.put("shift", false);
        inputsPressed.put("space", false);
        inputsPressed.put("escape", false);
    
        inputsLastFrame.putAll(inputsPressed);
        inputsEvents.putAll(inputsPressed);   
    }
    
    public static void update() {
        for (String keyName : inputsPressed.keySet()) {
            inputsLastFrame.replace(keyName, inputsPressed.get(keyName));
            inputsPressed.replace(keyName, Greenfoot.isKeyDown(keyName));
            if (inputsPressed.get(keyName) && !inputsLastFrame.get(keyName)) {
                inputsEvents.replace(keyName, true);
            }
        }
    }
    
    // input events should persist until the end of the physics process cycle of the current frame
    // in order to prevent a different GameEntity from eating all the events
    public static void postPhysicsUpdate() {
        for (String keyName : inputsPressed.keySet()) {
            inputsEvents.replace(keyName, false);
        }
    }
    
    // shorter version of Greenfoot.isKeyDown()
    public static boolean key(String k) { return inputsPressed.get(k); }
    
    // is the key down, and was it not down before?
    public static boolean keyEvent(String k) { return inputsEvents.get(k); }
    
    // is the key no longer down, but was down last time?
    public static boolean keyReleased(String k) { return (inputsLastFrame.get(k) && !inputsPressed.get(k)); }
}
