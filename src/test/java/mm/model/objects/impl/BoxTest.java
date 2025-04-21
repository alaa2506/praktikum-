package mm.model.objects.impl;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Box class.
 */
public class BoxTest {
    
    /**
     * Tests the creation of a default box.
     */
    @Test
    public void testDefaultBoxCreation() {
        Point2D position = new Point2D(100, 100);
        float width = 30.0f;
        float height = 20.0f;
        
        Box box = new Box(position, width, height);
        
        assertEquals(position, box.getPosition(), "Position should match the constructor argument");
        assertEquals(width, box.getWidth(), "Width should match the constructor argument");
        assertEquals(height, box.getHeight(), "Height should match the constructor argument");
        assertEquals(0.0f, box.getRotation(), "Default rotation should be 0");
        assertFalse(box.isStatic(), "Box should be dynamic");
    }
    
    /**
     * Tests the creation of a light box.
     */
    @Test
    public void testLightBoxCreation() {
        Point2D position = new Point2D(150, 150);
        float width = 25.0f;
        float height = 15.0f;
        
        Box box = Box.createLightBox(position, width, height);
        
        assertEquals(position, box.getPosition(), "Position should match the constructor argument");
        assertEquals(width, box.getWidth(), "Width should match the constructor argument");
        assertEquals(height, box.getHeight(), "Height should match the constructor argument");
        assertEquals(0.0f, box.getRotation(), "Default rotation should be 0");
        assertFalse(box.isStatic(), "Box should be dynamic");
        assertTrue(box.getDensity() < 1.0f, "Light box should have low density");
    }
    
    /**
     * Tests the creation of a heavy box.
     */
    @Test
    public void testHeavyBoxCreation() {
        Point2D position = new Point2D(200, 200);
        float width = 40.0f;
        float height = 30.0f;
        
        Box box = Box.createHeavyBox(position, width, height);
        
        assertEquals(position, box.getPosition(), "Position should match the constructor argument");
        assertEquals(width, box.getWidth(), "Width should match the constructor argument");
        assertEquals(height, box.getHeight(), "Height should match the constructor argument");
        assertEquals(0.0f, box.getRotation(), "Default rotation should be 0");
        assertFalse(box.isStatic(), "Box should be dynamic");
        assertTrue(box.getDensity() > 3.0f, "Heavy box should have high density");
    }
}
