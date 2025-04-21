package mm.model.level;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import java.util.List;
import mm.model.objects.GameObject;

/**
 * Represents a win condition for a level.
 * A win condition is satisfied when a specific object enters a specific area.
 */
public class WinCondition {
    private final String objectType;
    private final Rectangle2D area;
    private boolean satisfied;
    
    /**
     * Creates a new win condition.
     *
     * @param objectType The type of object that must enter the area
     * @param area The area that the object must enter
     */
    public WinCondition(String objectType, Rectangle2D area) {
        this.objectType = objectType;
        this.area = area;
        this.satisfied = false;
    }
    
    /**
     * Checks if the win condition is satisfied.
     *
     * @param gameObjects The list of game objects to check against
     * @return True if the win condition is satisfied, false otherwise
     */
    public boolean checkSatisfied(List<GameObject> gameObjects) {
        for (GameObject gameObject : gameObjects) {
            if (gameObject.getClass().getSimpleName().equals(objectType)) {
                Point2D position = gameObject.getPosition();
                if (area.contains(position)) {
                    satisfied = true;
                    return true;
                }
            }
        }
        satisfied = false;
        return false;
    }
    
    /**
     * Gets whether the win condition is satisfied.
     *
     * @return True if the win condition is satisfied, false otherwise
     */
    public boolean isSatisfied() {
        return satisfied;
    }
    
    /**
     * Sets whether the win condition is satisfied.
     *
     * @param satisfied True if the win condition is satisfied, false otherwise
     */
    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }
    
    /**
     * Gets the type of object that must enter the area.
     *
     * @return The object type
     */
    public String getObjectType() {
        return objectType;
    }
    
    /**
     * Gets the area that the object must enter.
     *
     * @return The area
     */
    public Rectangle2D getArea() {
        return area;
    }
    
    @Override
    public String toString() {
        return "WinCondition{" +
                "objectType='" + objectType + '\'' +
                ", area=" + area +
                ", satisfied=" + satisfied +
                '}';
    }
}
