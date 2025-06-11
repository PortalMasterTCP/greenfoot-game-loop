import greenfoot.*;
import java.util.*;
/**
 * Custom Actor class with added functionality, for use with GameLoop.
 * Each GameEntity has a classId for its class, and an entityId for individual ones,
 * for use with update priority
 * 
 * @author Portal
 * @version 1.0.0
*/
public class GameEntity extends SmoothMover
{
    protected GameWorld world;
    
    private int entityId;
    
    protected boolean active;
    
    public GameEntity() {}
    
    public void addedToWorld(World _world) { world = (GameWorld) _world; addToGame(); }
    
    protected void addToGame() { world.getGameLoop().addToGameEntities(this); }
    public void removeFromGame() { world.getGameLoop().removeFromGameEntities(this); }
    
    // the following methods should NOT be overridden, outside of class-specific behavior
    // in other words, please do not touch these unless you know what you are doing
    
    // called when the GameEntity is added to the GameLoop
    public void ready() { setActive(true); _ready(); }
    // called every frame
    public void process(double delta) { if (active) { _process(delta); } }
    // called every GameLoop.physicsTicksPerSecond
    public void physicsProcess(double delta) { if (active) { _physicsProcess(delta); } }
    
    // the following methods SHOULD be overridden as virtual methods
    // in other words, USE THESE FOR CUSTOM BEHAVIOR
    
    protected void _ready() {}
    protected void _process(double delta) {}
    protected void _physicsProcess(double delta) {}
    
    public int getClassId() { return world.getGameLoop().getClassId(this); }
    public int getEntityId() { return entityId; }
    public void setEntityId(int to) { entityId = to; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean a) { active = a; world.getGameLoop().setDirtyFlag(); }
    
    public String toString() {
        return (this.getClass().getSimpleName() + " (classId: " + getClassId() + " entityId: " + getEntityId() + ")");
    }
}
