package mm.model.objects.impl;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Log class.
 */
public class LogTest {
    
    /**
     * Tests the creation of a log.
     */
    @Test
    public void testLogCreation() {
        Point2D position = new Point2D(200, 300);
        float rotation = 45.0f;
        float length = 150.0f;
        float diameter = 30.0f;
        
        Log log = new Log(position, rotation, length, diameter);
        
        assertEquals(position, log.getPosition(), "Position should match the constructor argument");
        assertEquals(rotation, log.getRotation(), "Rotation should match the constructor argument");
        assertEquals(length, log.getLength(), "Length should match the constructor argument");
        assertEquals(diameter, log.getDiameter(), "Diameter should match the constructor argument");
        assertTrue(log.isStatic(), "Log should be static");
    }
}
