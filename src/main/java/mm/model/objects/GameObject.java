package mm.model.objects;

import javafx.geometry.Point2D;
import org.jbox2d.dynamics.Body;

/**
 * Base class for all game objects in the Crazy Machines game.
 * This class provides common properties and methods for all objects.
 */
public abstract class GameObject {
    private Point2D position;
    private float rotation;
    private Body physicsBody;
    private boolean isStatic;
    
    /**
     * Creates a new game object.
     *
     * @param position The initial position of the object
     * @param rotation The initial rotation of the object in degrees
     * @param isStatic Whether the object is static (immovable) or dynamic
     */
    public GameObject(Point2D position, float rotation, boolean isStatic) {
        this.position = position;
        this.rotation = rotation;
        this.isStatic = isStatic;
    }
    
    /**
     * Updates the object's position and rotation based on its physics body.
     * This should be called each frame during simulation.
     */
    public void updateFromPhysics() {
        if (physicsBody != null && !isStatic) {
            // Update position and rotation from physics body
            org.jbox2d.common.Vec2 position = physicsBody.getPosition();
            this.position = new Point2D(position.x, position.y);
            this.rotation = (float) Math.toDegrees(physicsBody.getAngle());
        }
    }
    
    /**
     * Creates the physics body for this object.
     * This should be implemented by subclasses to create the appropriate physics body.
     *
     * @param world The physics world to create the body in
     */
    public abstract void createPhysicsBody(org.jbox2d.dynamics.World world);
    
    /**
     * Renders the object.
     * This should be implemented by subclasses to render the object appropriately.
     *
     * @param context The graphics context to render to
     */
    public abstract void render(javafx.scene.canvas.GraphicsContext context);
    
    // Getters and setters
    
    /**
     * Gets the position of the object.
     *
     * @return The position as a Point2D
     */
    public Point2D getPosition() {
        return position;
    }
    
    /**
     * Sets the position of the object.
     *
     * @param position The new position
     */
    public void setPosition(Point2D position) {
        this.position = position;
    }
    
    /**
     * Gets the rotation of the object in degrees.
     *
     * @return The rotation in degrees
     */
    public float getRotation() {
        return rotation;
    }
    
    /**
     * Sets the rotation of the object in degrees.
     *
     * @param rotation The new rotation in degrees
     */
    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    
    /**
     * Gets the physics body of the object.
     *
     * @return The physics body
     */
    public Body getPhysicsBody() {
        return physicsBody;
    }
    
    /**
     * Sets the physics body of the object.
     *
     * @param physicsBody The new physics body
     */
    public void setPhysicsBody(Body physicsBody) {
        this.physicsBody = physicsBody;
    }
    
    /**
     * Checks if the object is static (immovable).
     *
     * @return True if the object is static, false otherwise
     */
    public boolean isStatic() {
        return isStatic;
    }
}
