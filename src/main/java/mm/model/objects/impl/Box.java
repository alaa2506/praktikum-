package mm.model.objects.impl;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mm.model.objects.DynamicObject;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

/**
 * A box is a dynamic object that can be used in puzzles.
 * It is a rectangle with various physical properties.
 */
public class Box extends DynamicObject {
    private final float width;
    private final float height;
    private final Color color;
    
    /**
     * Creates a default box.
     *
     * @param position The position of the box
     * @param width The width of the box
     * @param height The height of the box
     */
    public Box(Point2D position, float width, float height) {
        // Default values for a regular box
        this(position, 0, width, height, 2.0f, 0.3f, 0.2f, Color.BROWN);
    }
    
    /**
     * Creates a light box.
     *
     * @param position The position of the box
     * @param width The width of the box
     * @param height The height of the box
     * @return A light box
     */
    public static Box createLightBox(Point2D position, float width, float height) {
        return new Box(position, 0, width, height, 0.5f, 0.3f, 0.4f, Color.LIGHTBLUE);
    }
    
    /**
     * Creates a heavy box.
     *
     * @param position The position of the box
     * @param width The width of the box
     * @param height The height of the box
     * @return A heavy box
     */
    public static Box createHeavyBox(Point2D position, float width, float height) {
        return new Box(position, 0, width, height, 5.0f, 0.5f, 0.1f, Color.DARKGRAY);
    }
    
    /**
     * Creates a new box with custom properties.
     *
     * @param position The position of the box
     * @param rotation The rotation of the box in degrees
     * @param width The width of the box
     * @param height The height of the box
     * @param density The density of the box
     * @param friction The friction of the box
     * @param restitution The restitution (bounciness) of the box
     * @param color The color of the box
     */
    public Box(Point2D position, float rotation, float width, float height, 
               float density, float friction, float restitution, Color color) {
        super(position, rotation, density, friction, restitution);
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    @Override
    protected Shape createShape() {
        // Create a box shape
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, height / 2); // Half-width and half-height
        return shape;
    }
    
    @Override
    public void render(GraphicsContext context) {
        // Save the current state
        context.save();
        
        // Translate and rotate
        context.translate(getPosition().getX(), getPosition().getY());
        context.rotate(getRotation());
        
        // Draw the box
        context.setFill(color);
        context.fillRect(-width / 2, -height / 2, width, height);
        
        // Draw a border
        context.setStroke(Color.BLACK);
        context.strokeRect(-width / 2, -height / 2, width, height);
        
        // Draw a line to show rotation
        context.setStroke(Color.WHITE);
        context.strokeLine(0, 0, width / 3, 0);
        
        // Restore the state
        context.restore();
    }
    
    /**
     * Gets the width of the box.
     *
     * @return The width
     */
    public float getWidth() {
        return width;
    }
    
    /**
     * Gets the height of the box.
     *
     * @return The height
     */
    public float getHeight() {
        return height;
    }
    
    /**
     * Gets the color of the box.
     *
     * @return The color
     */
    public Color getColor() {
        return color;
    }
}
