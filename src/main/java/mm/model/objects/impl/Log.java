package mm.model.objects.impl;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import mm.model.objects.StaticObject;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;

/**
 * A log is a static object that can be used as a platform or barrier.
 * It is a cylindrical object represented as a rectangle with rounded ends.
 */
public class Log extends StaticObject {
    private final float length;
    private final float diameter;
    
    /**
     * Creates a new log.
     *
     * @param position The position of the log
     * @param rotation The rotation of the log in degrees
     * @param length The length of the log
     * @param diameter The diameter of the log
     */
    public Log(Point2D position, float rotation, float length, float diameter) {
        super(position, rotation);
        this.length = length;
        this.diameter = diameter;
    }
    
    @Override
    protected Shape createShape() {
        // Create a box shape (approximation of a log)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length / 2, diameter / 2); // Half-length and half-diameter
        return shape;
    }
    
    @Override
    public void render(GraphicsContext context) {
        // Save the current state
        context.save();
        
        // Translate and rotate
        context.translate(getPosition().getX(), getPosition().getY());
        context.rotate(getRotation());
        
        // Draw the log body (rectangle)
        context.setFill(Color.SADDLEBROWN);
        context.fillRect(-length / 2, -diameter / 2, length, diameter);
        
        // Draw the rounded ends (circles)
        context.fillOval(-length / 2 - diameter / 2, -diameter / 2, diameter, diameter);
        context.fillOval(length / 2 - diameter / 2, -diameter / 2, diameter, diameter);
        
        // Draw wood grain lines
        context.setStroke(Color.BROWN.darker());
        context.setLineWidth(1);
        for (int i = 0; i < 5; i++) {
            float y = -diameter / 2 + (i + 1) * diameter / 6;
            context.strokeLine(-length / 2, y, length / 2, y);
        }
        
        // Draw a border
        context.setStroke(Color.BLACK);
        context.setLineWidth(1);
        context.strokeRect(-length / 2, -diameter / 2, length, diameter);
        context.strokeOval(-length / 2 - diameter / 2, -diameter / 2, diameter, diameter);
        context.strokeOval(length / 2 - diameter / 2, -diameter / 2, diameter, diameter);
        
        // Restore the state
        context.restore();
    }
    
    /**
     * Gets the length of the log.
     *
     * @return The length
     */
    public float getLength() {
        return length;
    }
    
    /**
     * Gets the diameter of the log.
     *
     * @return The diameter
     */
    public float getDiameter() {
        return diameter;
    }
}
