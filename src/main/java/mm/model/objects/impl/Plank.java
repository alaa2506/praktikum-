package mm.model.objects.impl;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mm.model.objects.StaticObject;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

/**
 * A plank is a static object that can be used as a platform or barrier.
 * It is a long, thin rectangle.
 */
public class Plank extends StaticObject {
    private final float width;
    private final float height;
    
    /**
     * Creates a new plank.
     *
     * @param position The position of the plank
     * @param rotation The rotation of the plank in degrees
     * @param width The width of the plank
     * @param height The height of the plank
     */
    public Plank(Point2D position, float rotation, float width, float height) {
        super(position, rotation);
        this.width = width;
        this.height = height;
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
        
        // Draw the plank
        context.setFill(Color.BROWN);
        context.fillRect(-width / 2, -height / 2, width, height);
        
        // Draw a border
        context.setStroke(Color.BLACK);
        context.strokeRect(-width / 2, -height / 2, width, height);
        
        // Restore the state
        context.restore();
    }
    
    /**
     * Gets the width of the plank.
     *
     * @return The width
     */
    public float getWidth() {
        return width;
    }
    
    /**
     * Gets the height of the plank.
     *
     * @return The height
     */
    public float getHeight() {
        return height;
    }
}
