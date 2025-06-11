import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * Normal World with added functionality to allow easier communication between
 * GameLoop and GameEntities
 * 
 * @author Portal
 * @version 1.0.0
 * 
 */
public class GameWorld extends World
{
    private GameLoop gameLoop = new GameLoop(this);
    private TileMap tileMap = null; // set and add a TileMap during _begin if you want
    
    private boolean stopped = false;

    public GameWorld() { super(600, 400, 1); }

    public void started() { begin(); }
    
    private void begin() { 
        _begin();
        
        if (!stopped) { 
            gameLoop.runLoop(); 
        } else { 
            System.out.println("loop ended, needs reset"); 
        }
    }
    
    // override in a subclass to stop it yelling at you
    // (this class is not meant to be used directly)
    protected void _begin() {
        Label l = new Label("Please create a subclass of GameWorld!", 25);
        Label a = new Label("Press ESC first to end the GameLoop.", 25);
        addObject(l, getWidth() / 2, getHeight() / 2);
        addObject(a, getWidth() / 2, getHeight() / 2 + getHeight() / 4 );
    }
    
    public void stopped() { gameLoop.stopLoop(); stopped = true; }
    
    public GameLoop getGameLoop() { return gameLoop; }
    public TileMap getTileMap() { return tileMap; }
}
