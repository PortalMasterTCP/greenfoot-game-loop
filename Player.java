import greenfoot.*;

/**
 * Example use of GameEntity/GameLoop/etc
 * 
 * @author Portal
 * @version 1.0.0
 */
public class Player extends CharacterEntity
{
    protected double speed = 100.0;
    
    public Player() { super(); }
    public Player(Vector2 pos) { super(pos); }
    
    protected void _physicsProcess(double delta) {
        direction.set(MathUtil.getAxisVector(Input.key("w"), Input.key("s"), Input.key("a"), Input.key("d")));
        velocity.set(Vector2.multiply(direction, speed));
        velocity.multiply(delta);
        moveAndSlide();
    }
}
