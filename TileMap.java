import greenfoot.*;
import java.util.*;
/**
 * Map of Tiles for level design and such
 * Please note the math is a little janky!
 * 
 * @author Portal
 * @version 1.0.1
 */
public class TileMap  
{ 
    private GameWorld world;
    
    private boolean[][] map;
    
    private Tile[][] tileMap;
    
    // manually create the TileMap with a boolean 2d array
    public TileMap(GameWorld _world, boolean[][] _map) {
        world = _world;
        map = _map;
        tileMap = new Tile[map.length][map[0].length];
    }
    // define the size of the TileMap and
    // randomly generate a path between two tiles
    public TileMap(GameWorld _world, Vector2i size, Vector2i start, Vector2i end) {
        world = _world;
        tileMap = new Tile[size.y()][size.x()];
        TileMapPathGenerator path = new TileMapPathGenerator(this, size, start, end);
    }
    
    public void drawMap() {
        for (int y = 0; y < map.length; y++) { 
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x]) { 
                    Tile temp = new Tile(world, map[0].length, map.length); 
                    tileMap[y][x] = temp;
                    world.addObject(temp, 0, 0);
                    temp.moveToIndex(x, y);
                }
            }
        }
    }
    
    public void clear() { clear(false); }
    public void clear(boolean redraw) {
        map = new boolean[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) { Arrays.fill(map[i], false); }
        if (redraw) { drawMap(); }
    }
    
    public void setMap(boolean[][] _m) { map = _m; }
    
}
