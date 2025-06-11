import greenfoot.*;
import java.util.*;

/**
 * Individual Tile for TileMap
 * 
 * @author Portal
 * @version 1.0.0
 */
public class Tile extends CharacterEntity
{
    private GameWorld world;
    
    private Vector2 size;
    private Vector2 mapSize;
    
    public Tile(GameWorld _world, int width, int height) {
        world = _world;
        mapSize = new Vector2(width, height);
        lazyInit(width, height);
    }
    
    // still unsure why this is in a separate function, but it works so good enough
    private void lazyInit(int width, int height) {
        size = new Vector2(
        (world.getWidth() * world.getCellSize()) / width, 
        (world.getHeight() * world.getCellSize()) / height
        );
    }
    
    private void draw() {
        GreenfootImage temp = new GreenfootImage((int) size.x(), (int) size.y());
        temp.setColor(greenfoot.Color.BLACK);
        temp.fill();
        setImage(temp);
    }
    
    public void moveToIndex(int _x, int _y) {
        int x = MathUtil.clamp(_x, 0, (int) mapSize.x() - 1);
        int y = MathUtil.clamp(_y, 0, (int) mapSize.y() - 1);
        position.set(
            ( ( (double) world.getWidth() / (int) size.x() ) + size.x() ) * (x + 0.5),
            ( ( (double) world.getHeight() / (int) size.y() ) + size.y() ) * (y + 0.5)
        );
        //System.out.println(position);
        moveAndSlide();
        draw();
    }
    
    protected void _physicsProcess(double delta) {
        //collision code goes here
    }
}
