package mm.model.objects.impl;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Plank class.
 */
public class PlankTest {
    
    /**
     * Tests the creation of a plank.
     */
    @Test
    public void testPlankCreation() {
        Point2D position = new Point2D(200, 300);
        float rotation = 45.0f;
        float width = 200.0f;
        float height = 20.0f;
        
        Plank plank = new Plank(position, rotation, width, height);
        
        assertEquals(position, plank.getPosition(), "Position should match the constructor argument");
        assertEquals(rotation, plank.getRotation(), "Rotation should match the constructor argument");
        assertEquals(width, plank.getWidth(), "Width should match the constructor argument");
        assertEquals(height, plank.getHeight(), "Height should match the constructor argument");
        assertTrue(plank.isStatic(), "Plank should be static");
    }
}
