//TODO: 



package mm.model.level;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;

/**
 * Represents a restriction zone in a level.
 * Objects cannot be placed in a restriction zone.
 */
public class RestrictionZone {
    private final Rectangle2D area;
    
    /**
     * Creates a new restriction zone.
     *
     * @param area The area of the restriction zone
     */
    public RestrictionZone(Rectangle2D area) {
        this.area = area;
    }
    
    /**
     * Checks if a point is inside the restriction zone.
     *
     * @param point The point to check
     * @return True if the point is inside the restriction zone, false otherwise
     */
    public boolean contains(Point2D point) {
        return area.contains(point);
    }
    
    /**
     * Gets the area of the restriction zone.
     *
     * @return The area
     */
    public Rectangle2D getArea() {
        return area;
    }
    
    @Override
    public String toString() {
        return "RestrictionZone{" +
                "area=" + area +
                '}';
    }
}
