package mm.model.objects.impl;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mm.model.objects.DynamicObject;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.Shape;

/**
 * A ball is a dynamic object that can roll and bounce.
 * It is a circle with various physical properties.
 */
public class Ball extends DynamicObject {
    private final float radius;
    private final Color color;
    
    /**
     * Creates a default ball.
     *
     * @param position The position of the ball
     * @param radius The radius of the ball
     */
    public Ball(Point2D position, float radius) {
        // Default values for a regular ball
        this(position, 0, radius, 1.0f, 0.3f, 0.5f, Color.RED);
    }
    
    /**
     * Creates a bouncy ball.
     *
     * @param position The position of the ball
     * @param radius The radius of the ball
     * @return A bouncy ball
     */
    public static Ball createBouncyBall(Point2D position, float radius) {
        return new Ball(position, 0, radius, 0.8f, 0.1f, 0.9f, Color.BLUE);
    }
    
    /**
     * Creates a heavy ball.
     *
     * @param position The position of the ball
     * @param radius The radius of the ball
     * @return A heavy ball
     */
    public static Ball createHeavyBall(Point2D position, float radius) {
        return new Ball(position, 0, radius, 5.0f, 0.5f, 0.2f, Color.DARKGRAY);
    }
    
    /**
     * Creates a new ball with custom properties.
     *
     * @param position The position of the ball
     * @param rotation The rotation of the ball in degrees
     * @param radius The radius of the ball
     * @param density The density of the ball
     * @param friction The friction of the ball
     * @param restitution The restitution (bounciness) of the ball
     * @param color The color of the ball
     */
    public Ball(Point2D position, float rotation, float radius, float density, float friction, float restitution, Color color) {
        super(position, rotation, density, friction, restitution);
        this.radius = radius;
        this.color = color;
    }
    
    @Override
    protected Shape createShape() {
        // Create a circle shape
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        return shape;
    }
    
    @Override
    public void render(GraphicsContext context) {
        // Save the current state
        context.save();
        
        // Translate to the position
        context.translate(getPosition().getX(), getPosition().getY());
        
        // Draw the ball
        context.setFill(color);
        context.fillOval(-radius, -radius, radius * 2, radius * 2);
        
        // Draw a border
        context.setStroke(Color.BLACK);
        context.strokeOval(-radius, -radius, radius * 2, radius * 2);
        
        // Draw a line to show rotation
        context.setStroke(Color.WHITE);
        context.strokeLine(0, 0, radius * Math.cos(Math.toRadians(getRotation())), radius * Math.sin(Math.toRadians(getRotation())));
        
        // Restore the state
        context.restore();
    }
    
    /**
     * Gets the radius of the ball.
     *
     * @return The radius
     */
    public float getRadius() {
        return radius;
    }
    
    /**
     * Gets the color of the ball.
     *
     * @return The color
     */
    public Color getColor() {
        return color;
    }
}
