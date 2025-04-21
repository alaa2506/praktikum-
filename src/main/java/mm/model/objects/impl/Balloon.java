package mm.model.objects.impl;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mm.model.objects.SpecialObject;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * A balloon is a special object that floats upward.
 * It applies a constant upward force to simulate buoyancy.
 */
public class Balloon extends SpecialObject {
    private final float radius;
    private final float buoyancyForce;
    private final Color color;
    
    /**
     * Creates a new balloon.
     *
     * @param position The position of the balloon
     * @param radius The radius of the balloon
     */
    public Balloon(Point2D position, float radius) {
        this(position, 0, radius, 10.0f, Color.YELLOW);
    }
    
    /**
     * Creates a new balloon with custom properties.
     *
     * @param position The position of the balloon
     * @param rotation The rotation of the balloon in degrees
     * @param radius The radius of the balloon
     * @param buoyancyForce The upward force applied to the balloon
     * @param color The color of the balloon
     */
    public Balloon(Point2D position, float rotation, float radius, float buoyancyForce, Color color) {
        super(position, rotation, false);
        this.radius = radius;
        this.buoyancyForce = buoyancyForce;
        this.color = color;
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
        
        // Create the fixture
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.2f; // Light density
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;
        
        getPhysicsBody().createFixture(fixtureDef);
    }
    
    @Override
    public void updateSpecialBehavior(World world, float deltaTime) {
        if (getPhysicsBody() != null) {
            // Apply an upward force to simulate buoyancy
            Vec2 force = new Vec2(0, -buoyancyForce);
            getPhysicsBody().applyForceToCenter(force);
        }
    }
    
    @Override
    public void render(GraphicsContext context) {
        // Save the current state
        context.save();
        
        // Translate to the position
        context.translate(getPosition().getX(), getPosition().getY());
        
        // Draw the balloon
        context.setFill(color);
        context.fillOval(-radius, -radius, radius * 2, radius * 2);
        
        // Draw a border
        context.setStroke(Color.BLACK);
        context.strokeOval(-radius, -radius, radius * 2, radius * 2);
        
        // Draw the string
        context.setStroke(Color.BLACK);
        context.strokeLine(0, radius, 0, radius * 2);
        
        // Restore the state
        context.restore();
    }
    
    /**
     * Gets the radius of the balloon.
     *
     * @return The radius
     */
    public float getRadius() {
        return radius;
    }
    
    /**
     * Gets the buoyancy force of the balloon.
     *
     * @return The buoyancy force
     */
    public float getBuoyancyForce() {
        return buoyancyForce;
    }
    
    /**
     * Gets the color of the balloon.
     *
     * @return The color
     */
    public Color getColor() {
        return color;
    }
}
