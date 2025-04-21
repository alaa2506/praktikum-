package mm.model.objects;

import javafx.geometry.Point2D;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Base class for all dynamic (movable) objects in the game.
 * Examples include balls, boxes, and dominos.
 */
public abstract class DynamicObject extends GameObject {
    private float density;
    private float friction;
    private float restitution;
    
    /**
     * Creates a new dynamic object.
     *
     * @param position The initial position of the object
     * @param rotation The initial rotation of the object in degrees
     * @param density The density of the object (affects mass)
     * @param friction The friction of the object
     * @param restitution The restitution (bounciness) of the object
     */
    public DynamicObject(Point2D position, float rotation, float density, float friction, float restitution) {
        super(position, rotation, false);
        this.density = density;
        this.friction = friction;
        this.restitution = restitution;
    }
    
    @Override
    public void createPhysicsBody(World world) {
        // Create a dynamic body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position.set((float) getPosition().getX(), (float) getPosition().getY());
        bodyDef.angle = (float) Math.toRadians(getRotation());
        
        // Create the body
        setPhysicsBody(world.createBody(bodyDef));
        
        // Create the fixture (to be implemented by subclasses)
        Shape shape = createShape();
        if (shape != null) {
            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = density;
            fixtureDef.friction = friction;
            fixtureDef.restitution = restitution;
            
            getPhysicsBody().createFixture(fixtureDef);
        }
    }
    
    /**
     * Creates the shape for this dynamic object.
     * This should be implemented by subclasses to create the appropriate shape.
     *
     * @return The shape for the physics body
     */
    protected abstract Shape createShape();
    
    // Getters and setters
    
    /**
     * Gets the density of the object.
     *
     * @return The density
     */
    public float getDensity() {
        return density;
    }
    
    /**
     * Sets the density of the object.
     *
     * @param density The new density
     */
    public void setDensity(float density) {
        this.density = density;
    }
    
    /**
     * Gets the friction of the object.
     *
     * @return The friction
     */
    public float getFriction() {
        return friction;
    }
    
    /**
     * Sets the friction of the object.
     *
     * @param friction The new friction
     */
    public void setFriction(float friction) {
        this.friction = friction;
    }
    
    /**
     * Gets the restitution (bounciness) of the object.
     *
     * @return The restitution
     */
    public float getRestitution() {
        return restitution;
    }
    
    /**
     * Sets the restitution (bounciness) of the object.
     *
     * @param restitution The new restitution
     */
    public void setRestitution(float restitution) {
        this.restitution = restitution;
    }
}
