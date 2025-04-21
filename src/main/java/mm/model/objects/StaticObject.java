package mm.model.objects;

import javafx.geometry.Point2D;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Base class for all static (immovable) objects in the game.
 * Examples include planks and logs.
 */
public abstract class StaticObject extends GameObject {
    
    /**
     * Creates a new static object.
     *
     * @param position The initial position of the object
     * @param rotation The initial rotation of the object in degrees
     */
    public StaticObject(Point2D position, float rotation) {
        super(position, rotation, true);
    }
    
    @Override
    public void createPhysicsBody(World world) {
        // Create a static body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set((float) getPosition().getX(), (float) getPosition().getY());
        bodyDef.angle = (float) Math.toRadians(getRotation());
        
        // Create the body
        setPhysicsBody(world.createBody(bodyDef));
        
        // Create the fixture (to be implemented by subclasses)
        Shape shape = createShape();
        if (shape != null) {
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 0.0f; // Static objects have zero density
            fixtureDef.friction = 0.3f;
            fixtureDef.restitution = 0.1f; // Low restitution (not very bouncy)
            
            getPhysicsBody().createFixture(fixtureDef);
        }
    }
    
    /**
     * Creates the shape for this static object.
     * This should be implemented by subclasses to create the appropriate shape.
     *
     * @return The shape for the physics body
     */
    protected abstract Shape createShape();
}
