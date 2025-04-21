package mm.model.physics;

import javafx.geometry.Point2D;
import mm.model.objects.GameObject;
import mm.model.objects.SpecialObject;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper for the jBox2D physics world.
 * This class manages the physics simulation and game objects.
 * It provides methods for adding, removing, and updating game objects.
 */
public class PhysicsWorld {
    private static final float TIME_STEP = 1.0f / 60.0f;
    private static final int VELOCITY_ITERATIONS = 8;
    private static final int POSITION_ITERATIONS = 3;
    private static final float MAX_TIME_STEP = 0.05f; // Maximum time step to prevent instability
    
    private final World world;
    private final List<GameObject> gameObjects;
    private float accumulatedTime;
    private float gravityScale;
    
    /**
     * Creates a new physics world with default gravity.
     */
    public PhysicsWorld() {
        this(new Vec2(0, 9.8f)); // Default gravity (9.8 m/s^2 downward)
    }
    
    /**
     * Creates a new physics world with custom gravity.
     *
     * @param gravity The gravity vector
     */
    public PhysicsWorld(Vec2 gravity) {
        world = new World(gravity);
        gameObjects = new ArrayList<>();
    }
    
    /**
     * Adds a game object to the physics world.
     *
     * @param gameObject The game object to add
     */
    public void addGameObject(GameObject gameObject) {
        gameObjects.add(gameObject);
        gameObject.createPhysicsBody(world);
    }
    
    /**
     * Removes a game object from the physics world.
     *
     * @param gameObject The game object to remove
     */
    public void removeGameObject(GameObject gameObject) {
        if (gameObject.getPhysicsBody() != null) {
            world.destroyBody(gameObject.getPhysicsBody());
        }
        gameObjects.remove(gameObject);
    }
    
    /**
     * Updates the physics simulation.
     * Uses a fixed time step for more stable physics.
     *
     * @param deltaTime The time elapsed since the last update in seconds
     */
    public void update(float deltaTime) {
        // Limit the maximum time step to prevent instability
        float clampedDeltaTime = Math.min(deltaTime, MAX_TIME_STEP);
        accumulatedTime += clampedDeltaTime;
        
        // Update with fixed time steps for stability
        while (accumulatedTime >= TIME_STEP) {
            // Update special objects
            for (GameObject gameObject : gameObjects) {
                if (gameObject instanceof SpecialObject) {
                    ((SpecialObject) gameObject).updateSpecialBehavior(world, TIME_STEP);
                }
            }
            
            // Step the physics simulation
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
            
            accumulatedTime -= TIME_STEP;
        }
        
        // Update game objects from physics
        for (GameObject gameObject : gameObjects) {
            gameObject.updateFromPhysics();
        }
    }
    
    /**
     * Gets all game objects in the physics world.
     *
     * @return The list of game objects
     */
    public List<GameObject> getGameObjects() {
        return new ArrayList<>(gameObjects);
    }
    
    /**
     * Gets the jBox2D world.
     *
     * @return The jBox2D world
     */
    public World getWorld() {
        return world;
    }
    
    /**
     * Clears all game objects from the physics world.
     */
    public void clear() {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getPhysicsBody() != null) {
                world.destroyBody(gameObject.getPhysicsBody());
            }
        }
        gameObjects.clear();
    }
    
    /**
     * Converts a point from screen coordinates to physics world coordinates.
     *
     * @param screenPoint The point in screen coordinates
     * @return The point in physics world coordinates
     */
    public Point2D screenToWorld(Point2D screenPoint) {
        // This is a simple implementation. In a real game, you would need to account for camera position and zoom.
        return screenPoint;
    }
    
    /**
     * Converts a point from physics world coordinates to screen coordinates.
     *
     * @param worldPoint The point in physics world coordinates
     * @return The point in screen coordinates
     */
    public Point2D worldToScreen(Point2D worldPoint) {
        // This is a simple implementation. In a real game, you would need to account for camera position and zoom.
        return worldPoint;
    }
    
    /**
     * Sets the gravity of the physics world.
     *
     * @param x The x component of the gravity vector
     * @param y The y component of the gravity vector
     */
    public void setGravity(float x, float y) {
        world.setGravity(new Vec2(x, y));
    }
    
    /**
     * Gets the gravity of the physics world.
     *
     * @return The gravity vector
     */
    public Vec2 getGravity() {
        return world.getGravity();
    }
    
    /**
     * Sets the gravity scale. This multiplies the gravity vector.
     * Useful for simulating different environments (e.g., moon, space).
     *
     * @param scale The gravity scale (1.0 is normal Earth gravity)
     */
    public void setGravityScale(float scale) {
        gravityScale = scale;
        Vec2 gravity = world.getGravity();
        world.setGravity(new Vec2(gravity.x * scale, gravity.y * scale));
    }
    
    /**
     * Gets the gravity scale.
     *
     * @return The gravity scale
     */
    public float getGravityScale() {
        return gravityScale;
    }
    
    /**
     * Applies an impulse to a game object.
     * Useful for simulating explosions or user interactions.
     *
     * @param gameObject The game object to apply the impulse to
     * @param impulseX The x component of the impulse
     * @param impulseY The y component of the impulse
     */
    public void applyImpulse(GameObject gameObject, float impulseX, float impulseY) {
        if (gameObject.getPhysicsBody() != null) {
            gameObject.getPhysicsBody().applyLinearImpulse(
                new Vec2(impulseX, impulseY),
                gameObject.getPhysicsBody().getWorldCenter()
            );
        }
    }
}
