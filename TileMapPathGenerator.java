import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Random path generator for TileMap
 * 
 * @author CatsaCode
 * @version 1.0.0
 */
public class TileMapPathGenerator
{
    boolean[][] tilemap;

    public TileMapPathGenerator(TileMap host, Vector2i size, Vector2i start, Vector2i target)
    {    
        tilemap = new boolean[size.y()][size.x()];

        generatePath(start.x(), start.y(), target.x(), target.y());
        host.setMap(tilemap);
        
        //for(int i = 0; i < 5; i++) System.out.println();
        //printTilemap(tilemap);
    }
    
    private class TileGenInfo {
        public int x = 0;
        public int y = 0;
        public ArrayList<Integer> validDirections = new ArrayList<Integer>();
        public int direction = -1;
        
        public TileGenInfo() {
            validDirections.add(0);
            validDirections.add(1);
            validDirections.add(2);
            validDirections.add(3);
        }
        
        public TileGenInfo(int _x, int _y) {
            x = _x; 
            y = _y;
            if(x + 1 < tilemap[0].length && tilemap[y][x + 1] == true) validDirections.add(0);
            if(y - 1 >= 0 && tilemap[y - 1][x] == true) validDirections.add(1);
            if(x - 1 >= 0 && tilemap[y][x - 1] == true) validDirections.add(2);
            if(y + 1 < tilemap.length && tilemap[y + 1][x] == true) validDirections.add(3);
        }
    }
    
    public void generatePath(int startX, int startY, int endX, int endY) {
        ArrayList<TileGenInfo> path = new ArrayList<TileGenInfo>();
        int steps = tilemap.length * tilemap[0].length;
        while(true) {
            steps++;
            if(steps > tilemap.length * tilemap[0].length) {
                for(int i = 0; i < tilemap.length; i++)
                    for(int j = 0; j < tilemap[i].length; j++)
                        tilemap[i][j] = true;
                path = new ArrayList<TileGenInfo>();
                path.add(new TileGenInfo(startX, startY));
                steps = 0;
            }
            
            TileGenInfo head = path.get(path.size() - 1);
            
            if(head.validDirections.size() == 0) {
                tilemap[head.y][head.x] = true;
                path.remove(path.size() - 1);
                head = path.get(path.size() - 1);
                tilemap[head.y][head.x] = true;
                head.validDirections.remove(head.validDirections.indexOf(head.direction));
                continue;
            }
            
            head.direction = head.validDirections.get((int)(Math.random() * head.validDirections.size()));
            
            int newHeadX = head.x + (int)Math.round(Math.cos(head.direction * Math.PI / 2.0));
            int newHeadY = head.y - (int)Math.round(Math.sin(head.direction * Math.PI / 2.0));
            
            boolean exposedEmptyTile = false;
            for(int testDirection = 0; testDirection < 4; testDirection++) {
                int testX = newHeadX + (int)Math.round(Math.cos(testDirection * Math.PI / 2.0));
                int testY = newHeadY - (int)Math.round(Math.sin(testDirection * Math.PI / 2.0));
                if(testX >= 0 && testX < tilemap[0].length &&
                   testY >= 0 && testY < tilemap.length &&
                   tilemap[testY][testX] == false)
                {
                    exposedEmptyTile = true;
                    break;
                }
            }
            if(exposedEmptyTile) {
                head.validDirections.remove(head.validDirections.indexOf(head.direction));
                continue;
            }
            
            tilemap[head.y][head.x] = false;
            path.add(new TileGenInfo(newHeadX, newHeadY));
            head = path.get(path.size() - 1);
            
            if(head.x != endX || head.y != endY) continue;
            tilemap[head.y][head.x] = false;
            break;
        }
    }
    
    public static void printTilemap(boolean[][] data) {
        for(int i = 0; i < data.length; i++) {
            for(int j = 0; j < data[i].length; j++) {
                 System.out.print(data[i][j] ? "  " : "0 ");
            }
            System.out.println();
        }
    }
}