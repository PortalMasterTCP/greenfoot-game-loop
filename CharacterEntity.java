import greenfoot.*;
import java.util.*;
/**
 * A GameEntity with added functionality, including Vector2 position, a direction vector, 
 * and a velocity vector
 */
public class CharacterEntity extends GameEntity 
{
    protected Vector2 position = new Vector2(0.0, 0.0);
    protected Vector2 velocity = new Vector2(0.0, 0.0);
    protected Vector2 direction = new Vector2(0.0, 0.0);
    
    public CharacterEntity() {
        super();
    }   
    public CharacterEntity(Vector2 pos) {
        super();
        position.set(pos);
    }
    
    public void ready() {
        super.ready();
        moveAndSlide();
    }
    
    // add velocity to position, and then sync with the built in stuff
    // call manually during _physicsProcess
    protected void moveAndSlide() {
        position.add(velocity);
        
        position.clamp(world.getWidth(), world.getHeight());
        
        setLocation(position.x(), position.y());
    }
    
    // waiter waiter more getters/setters please
    public Vector2 getPosition() { return position.clone(); }
    public void setPosition(Vector2 pos) { position = pos; moveAndSlide(); }
    public void setPosX(double _x) { position.setX(_x); moveAndSlide(); }
    public void setPosX(int _x) { position.setX(_x); moveAndSlide(); }
    public void setPosY(double _y) { position.setY(_y); moveAndSlide(); }
    public void setPosY(int _y) { position.setY(_y); moveAndSlide(); }
}
