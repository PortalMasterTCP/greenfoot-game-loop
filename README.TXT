PROJECT TITLE: Portal's Game Loop
PURPOSE OF PROJECT: Make Greenfoot less tedious to work with by adding a more friendly API to work with
VERSION: 1.0.0
HOW TO START THIS PROJECT: 
    Create and instantiate a new subclass of GameWorld, 
    override _begin(), add a Game/CharacterEntity, then
    override _process() or _physicsProcess()
AUTHORS: Portal, CatsaCode

NOTE:
    Feel free to use this for whatever, just try and keep the @author comments the same on my scripts. If you run into any
    problems or have a fix/feature you want added, don't hesitate to let me know!
        - Portal

DESCRIPTION:
    I didn't like act() very much... So I made an entire game loop for some reason!
    Features:
        > GameLoop - Custom game loop inspired by Robert Nystrom's sample implementation, with an FPS display and delta time included
        > GameEntity - Replacement to Actor, which recieves ready(), process(delta), and physicsProcess(delta)
        > CharacterEntity - A subclass of GameEntity with prebuilt position/velocity/direction vectors
        > Input - A singleton designed to make handling inputs a little easier
        > Vector2 - (x, y) coordinates with double precision
        > Vector2i - Vector2, but with int. Cross-compatibility not guaranteed
        > MathUtils - required misc math functions not provided by Java's default Math class
        > TileMap - Basic TileMap system for basic level design
        > Tile - Required for TileMap
        > TileMapPathGenerator - A procedural generation algorithm for TileMaps, courtesy of CatsaCode
        > imports Label and SmoothMover, those are not mine!
  
GameWorld:

    World subclass that handles the initialization of GameLoop for you. Create a subclass of this class and instantiate that,
    and custom behavior goes in _begin().
        
GameLoop:

    GameLoop is a class instantiated once in GameWorld, and acts as a custom act loop.
    
    !! For the most part, you can ignore this class entirely when working with GameEntity and subclasses of it. !!
    
    How it works:
    
        Press ESC to exit the loop manually
        
        GameLoop has two main ArrayLists. The first, gameEntities, tracks every GameEntity currently
        in the game regardless of state. The second, tickingEntities, only tracks *active* GameEntities.
        
        The GameLoop has these distincts steps each frame, all contained in one while loop:
            1. If gameEntities/tickingEntities is/needs to be changed, sort
            2. If gameEntities/tickingEntities is/needs to be changed, remove anything flagged to be deleted, and sort again
            3. Record delta time (the time since last frame)
            4. Update Input
            5. Call process(delta) on every GameEntity, and if it has been 1/60th (default) of a second since last time, also call physicsProcess(delta)
            6. If physicsProcess was just called, do a little cleanup
            7. Repaint the world (since Greenfoot will not do it itself)
            8. Get the average lag from the last 15 frames
            9. If this frame has exceeded the fps cap, wait the remaining time (taking into account lag)
        
        For sorting, each GameEntity has a CLASS ID (shared between others of the same class) and an ENTITY ID (unique to each member of the class). 
        When the GameLoop sorts, it partitions the GameEntities by class ID, and then sorts in order of entity ID. This sets the equivelant
        to the built-in act order. After that, it just clears tickingEntities and refills it with only active GameEntities (so it does not have to
        check each frame if something is active)
        
        The GameLoop also stores the amount of each GameEntity of each class ID in a dumb int Array, with the index corresponding to the ID. 
        When something is flagged for removal, the GameLoop adds the total of all prior classes through that Array, and then adds the target's 
        Entity ID to get its position in gameEntities. When it removes it during that time, it will decrement others as needed and reallocate 
        Entity IDs just for the removed class.
        
        If you want to learn more about Game Loops, check out Robert Nystrom's take on it here: https://gameprogrammingpatterns.com/game-loop.html
        
GameEntity:

    GameEntity is a replacement for Actor. It technically does have a position, but if you want to work with something with a position,
    please use CharacterEntity instead.
    
    GameEntity has 3 important methods, with public and protected counterparts. The public versions should NEVER be overridden unless
    you know what you're doing. The protected versions (the ones with the underscores) should be overridden to achieve behavior.
        > _ready() is called when the GameEntity is added to the GameLoop. Use it for things like lazy initialization and such.
        
        > _process(delta) is called once per frame, and delta is the time since the last call (for use to maintain parity between different fps).
          Since it is called as fast as possible, I do not recommend using this for physics math or the like.
          
        > _physicsProcess(delta) is like _process(delta), but it is called at a constant rate. delta here is safe to treat more like a constant, 
          but not always. I recommend doing physics/gameplay math here.
    
    You do not need to touch anything related to Entity ID or Class ID here.
    
CharacterEntity:

    CharacterEntity is a subclass of GameEntity with some added features, namely a Vector2 position, a Vector2 velocity, and a Vector2 direction.
    
        > The position vector is what you should interact with instead of the built in position. It is synced with real position
          when moveAndSlide() is called.
        > The velocity vector is a vector containing an amount to modify position by during the next moveAndSlide() call. I recommend using this
          for movement.
        > The direction vector is meant to hold the direction you want to move, as a normalized vector. I recommend using MathUtil.getAxisVector for
          with 4 Input.key() calls as parameters.
        > moveAndSlide() adds velocity to position, ensures position is within the size of the world, and syncs the inbuilt position to it.

Input:

    Utility class with an input map, replacing Greenfoot.isKeyDown() and similar. 
    You can add inputs by adding entries to the HashMap in the static block (input names should match greenfoot's names for it).
    
    It has 3 HashMaps:
        > inputsPressed - is the key pressed this frame?
        > inputsLastFrame - was the key pressed last frame?
        > inputsEvents - is the key pressed this physicsProcess cycle, but not already?
        
    It has 3 public static methods:
        > key(String k) - is the key pressed this frame?
        > keyEvent(String k) - was the key just pressed?
        > keyReleased(String k) - was the key just released?

MathUtil:

    Utility class with various static math functions not included in Math.
    
        > isEqualApprox - checks for approximate equality, as in Godot Engine's implementation
        > clamp - limits a number between two other numbers
        > wrap - clamp, but if it is outside the limits it wraps around instead
        > getAxis - assigns one condition to be -1 if true and one to be 1 if true, and subtracts them.
        > getAxisVector - calls getAxis twice and returns the results in a Vector2
        > boolToInt - lazy shorthand for int i = (condition) ? 1 : 0
        > appendArray - append one dumb array to another
        > randiRange/randRange - (possible) easier randoms

Vector2/Vector2i:

    Contains an x and a y ((x, y), as in coordinates). Has basic math functions, and static versions (Vector2.subtract, for example) 
    if you just want the result instead of setting the value. Also has some utility functions. Compatibility between Vector2 and Vector2i is
    not guaranteed.
    
    use x() and y() to get the respective values.
    
    Contains addition, subtract, multiply, divide, modulo, and equals.
    
    Some utility functions:
        > clone - returns a copy of the vector (remember, these are still shared via reference!)
        > normalize - normalizes the vector
        > angleToVector - returns the angle to the other coordinate
        > directionTo - returns the direction to the other coordinate as a normalized vector
        > cross - returns the cross product
        > dot - returns the dot product
        > clamp - clamps the vector, as in MathUtil.clamp
        > isEqualApprox - approximate equality, as in MathUtil.isEqualApprox
        > getLen - returns length of the vector
    
TileMap:

    Quick TileMap system. Takes a boolean 2d array, and for every true it creates a tile in the corresponding size and position.
    
    Calling drawMap() will actually draw the tiles themselves.
    
Tile: 

    Subclass of CharacterBody, for TileMap. Feel free to subclass for collisions and such.
    
    !! Please note that Tiles do not have built in collision, so dont be alarmed if nothing collides !!
    
TileMapPathGenerator:

    Only instantiated within TileMap. Randomly generates a path between two points in a TileMap given a start tile, end tile, and size.
    Define those in one of the overloaded constructors of TileMap.
    
FPSDisplay:

    Displays the current fps. Handled entirely by GameLoop, you do not need to worry about it.

