import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class myWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestWorld extends GameWorld
{

    public TestWorld() { super(); }
    
    protected void _begin() {
        Player p = new Player();
        addObject(p, 0, 0);
        
        TileMap t = new TileMap(this, 
        new Vector2i(6, 6), 
        new Vector2i(0, 0), 
        new Vector2i(5, 5)
        );
        t.drawMap();
    }
}
