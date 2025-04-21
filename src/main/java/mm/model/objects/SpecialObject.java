package mm.model.objects;

import javafx.geometry.Point2D;
import org.jbox2d.dynamics.World;

/**
 * Base class for all special objects in the game.
 * These objects have unique behaviors beyond standard physics.
 * Examples include balloons and buckets.
 */
public abstract class SpecialObject extends GameObject {
    
    /**
     * Creates a new special object.
     *
     * @param position The initial position of the object
     * @param rotation The initial rotation of the object in degrees
     * @param isStatic Whether the object is static (immovable) or dynamic
     */
    public SpecialObject(Point2D position, float rotation, boolean isStatic) {
        super(position, rotation, isStatic);
    }
    
    /**
     * Updates the special behavior of this object.
     * This should be called each frame during simulation.
     *
     * @param world The physics world
     * @param deltaTime The time elapsed since the last update in seconds
     */
    public abstract void updateSpecialBehavior(World world, float deltaTime);
}
