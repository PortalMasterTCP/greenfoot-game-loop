import greenfoot.*;
import java.util.*;
/**
 * Custom Game Loop to use instead of Greenfoot's built-on one. 
 * Inspired by Robert Nystrom's sample implementation in his Game Programming Patterns.
 * 
 * Required classes: 
 *     GameWorld, Vector2, MathUtil, Input, GameEntity, 
 *     CharacterEntity, FPSLabel, TileMap, Tile,
 *     TileMapPathGenerator, SmoothMover, Label.
 * 
 * @Author Portal
 * @Version 1.1.0
 */
public class GameLoop  
{
    private final GameWorld world;
    
    private final double ns = 1E+9; // nanosecond
    private final long ms = 1000; // millisecond
    
    // how many times should physicsProcess get called per second?
    // 60 by default
    private final int physicsTicksPerSecond; 
    
    private final int fpsCap = 60;
    private final double fpsCapTime = 1.0 / (double) fpsCap;
    
    private long[] lagHistory = new long[15];
    private int lagIndex = 0;
    
    private double elapsed = 0.0;
    
    private double currentTime = System.nanoTime();
    private double lastTime = 0.0;
    private double deltaTime = 0.0;
    private double deltaTimeAccumulator = 0.0;
    
    private FPSDisplay fpsDisp = new FPSDisplay(20);
    
    private ArrayList<GameEntity> gameEntities = new ArrayList<GameEntity>();
    private ArrayList<GameEntity> tickingEntities = new ArrayList<GameEntity>();
    private ArrayList<Integer> toRemove = new ArrayList<Integer>();
    
    private int[] entitiesPerClass;
    private int classIdCount = 2;
    // keep GameEntity and CharacterEntity hardcoded, the rest is up to you
    private HashMap<String, Integer> classIdMap = new HashMap<String, Integer>(Map.of("GameEntity", 0, "CharacterEntity", 1, "Player", 2));
    
    private boolean loopRunning;
    
    private boolean isDirty = false;
    
    public GameLoop(GameWorld _world) {
        world = _world;
        physicsTicksPerSecond = 60;
        entitiesPerClass = new int[classIdMap.size()];
    }
    public GameLoop(GameWorld _world, int physRate) {
        world = _world;
        physicsTicksPerSecond = physRate;
        entitiesPerClass = new int[classIdMap.size()];
    }
    
    public void addToGameEntities(GameEntity e) {
        updateEntitiesPerClass(e);
        e.setEntityId(entitiesPerClass[e.getClassId()] - 1);
        gameEntities.add(0, e);
        isDirty = true;
        e.ready();
    }
    
    public void removeFromGameEntities(GameEntity e) {
        e.setActive(false);
        toRemove.add(getEntityIndex(e));
        isDirty = true;
    }
    
    public void runLoop() {
        System.out.println("GameLoop: starting loop");
        loopRunning = true;
        world.addObject(fpsDisp, 70, 30);
        world.setPaintOrder(Label.class); // draw labels on top of everything else
        while (loopRunning) {
            
            if (Greenfoot.isKeyDown("escape")) { 
                // debug to stop loop
                // control flow like this takes performance so 
                // it would be more efficient if this feature 
                // was moved to a GameEntity or something
                System.out.println("GameLoop: ending loop");
                stopLoop();
                break;
            }
            
            elapsed = System.nanoTime() / ns; // record when the frame has started
            
            if (isDirty) { 
                sortEntities();
                
                if (toRemove.size() > 0) { // if there are indexes flagged for deletion, get rid of them
                    while (toRemove.size() > 0) {
                        
                        GameEntity temp = gameEntities.get(toRemove.get(0));
                        removeAndReallocate(temp.getClassId(), temp.getEntityId(), toRemove.get(0));
                        toRemove.remove(0);
                        world.removeObject(temp);
                        
                    }
                    
                    sortEntities();
                }
                System.out.println("GameLoop: sort complete");
                for (int i = 0; i < gameEntities.size(); i++) { System.out.println("    " + i + ": " + gameEntities.get(i)); }
                isDirty = false;
            }
            
            updateDelta();
            
            Input.update(); // update Input
            
            // call process/physicsProcess on all active entities
            for (int i = 0; i < tickingEntities.size(); i++) {
                tickingEntities.get(i).process(deltaTime);
                if (deltaTimeAccumulator >= 1.0 / (double) physicsTicksPerSecond) {
                    tickingEntities.get(i).physicsProcess(deltaTimeAccumulator);
                }
            }
            
            // cleanup after physics process
            if (deltaTimeAccumulator >= 1.0 / (double) physicsTicksPerSecond) { 
                Input.postPhysicsUpdate();
                deltaTimeAccumulator = 0.0;
            }
            
            world.repaint(); // render
            
            elapsed = (System.nanoTime() / ns) - elapsed; // find how long this frame has taken
            
            long lagFix = getLagAvg(); // average of the last [lagHistory.length] frames' lag
            
            // if we went faster than the fps cap, wait the remaining time (and account for lag)
            if (elapsed < fpsCapTime) { 
                try {
                    long toWait = (long) MathUtil.clamp(((fpsCapTime - elapsed) * ms) - lagFix, 0, 99999);
                    long now = System.currentTimeMillis();
                    Thread.sleep(toWait);
                    now = System.currentTimeMillis() - now;
                    lagHistory[lagIndex] = now - toWait;
                    lagIndex = MathUtil.wrap(lagIndex + 1, 0, lagHistory.length - 1);
                    
                } catch (InterruptedException ie) {
                    ie.printStackTrace(); // will this appease you, greenfoot?
                } 
            }  
        }
    }
    
    // measure the time since last frame
    private void updateDelta() {
        lastTime = currentTime;
        currentTime = System.nanoTime();
        deltaTime = (currentTime - lastTime) / ns;
        deltaTimeAccumulator += deltaTime; 
        fpsDisp.update(deltaTime);
        deltaTime = MathUtil.clamp(deltaTime, 1.0 / (double) physicsTicksPerSecond);
    }
    
    private void sortEntities() {
        // first, partition by classId. Next, sort by order of entityId within each partition
        gameEntities.sort(( GameEntity a, GameEntity b) -> { return ( ((a.getClassId() * 10000 + a.getEntityId() ) - ( b.getClassId() * 10000 + b.getEntityId() )) ); });
        
        tickingEntities.clear();
               
        // add all active entities in order to the list of active entities
        for (int i = 0; i < gameEntities.size(); i++) { 
            if (gameEntities.get(i).isActive()) { tickingEntities.add(gameEntities.get(i)); }
        }
    }
    
    private long getLagAvg() {
        long sum = 0;
        for (long val : lagHistory) { sum += val; }
        return sum / lagHistory.length;
    }
    
    // get e.getClassId(), resize the array if the id is not already known, 
    // and then increment the [classid] by 1
    private void updateEntitiesPerClass(GameEntity e) {
        int classIndex = e.getClassId();
        if (entitiesPerClass.length - 1 < classIndex) {
            int[] resized = new int[classIndex + 1];
            Arrays.fill(resized, 0);
            for (int i = 0; i < entitiesPerClass.length; i++) {
                resized[i] = entitiesPerClass[i];
            }
            entitiesPerClass = resized;
        }
        entitiesPerClass[classIndex]++; 
    }
    
    private void removeAndReallocate(int classId, int entityId, int index) {
        gameEntities.remove(index);
        if (entitiesPerClass[classId] > 0) {
            entitiesPerClass[classId]--;
        }
        
        // if theres more, fix the indexes since you just deleted something
        if (toRemove.size() > 1) {
            for (int i = 0; i < toRemove.size(); i++) {
                if (toRemove.get(i) > index) { toRemove.set(i, toRemove.get(i) - 1); }
            }
        }
        
        // reallocate entityIds of everything with the same class as the deleted entity
        int startClassIndex = index - entityId;
        int nextClassIndex = (classId > entitiesPerClass.length - 1) ? entitiesPerClass.length - 1 : startClassIndex + entitiesPerClass[classId];
        int count = 0;
        for (int i = startClassIndex; i < nextClassIndex; i++) {
            gameEntities.get(i).setEntityId(count);
            count++;
        }
    }
    
    // evil O(c) search algo to get entity position in gameEntities
    // precondition: main entity array is already sorted
    public int getEntityIndex(GameEntity e) {
        int sum = 0;
        for (int i = 0; i < e.getClassId(); i++) {
            sum += entitiesPerClass[i];
        }
        return (sum + e.getEntityId());
    }
    
    public GameEntity getGameEntity(int classId, int entityId) {
        int sum = 0;
        for (int i = 0; i < classId; i++) {
            sum += entitiesPerClass[i];
        }
        return gameEntities.get(sum + entityId);
    }
    
    public int getClassId(GameEntity e) {
        String className = e.getClass().getSimpleName();
        if (classIdMap.getOrDefault(className, null) == null) {
            classIdMap.put(className, 0);
            classIdMap.replace(className, classIdMap.size() - 1);
        }
        return classIdMap.get(className);
    }
    
    public void clear() {
        toRemove.clear();
        for (int i = 0; i < gameEntities.size(); i++) {
            removeFromGameEntities(gameEntities.get(i));
        }
    }
    
    public void setDirtyFlag() { isDirty = true; } // flag the list of entities as changed, cannot clear externally to reduce mistakes
    
    public void stopLoop() { loopRunning = false; gameEntities.clear(); tickingEntities.clear(); toRemove.clear(); Greenfoot.stop(); }
}
