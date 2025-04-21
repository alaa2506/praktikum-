package mm.model.objects.impl;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Ball class.
 */
public class BallTest {
    
    /**
     * Tests the creation of a default ball.
     */
    @Test
    public void testDefaultBallCreation() {
        Point2D position = new Point2D(100, 100);
        float radius = 15.0f;
        
        Ball ball = new Ball(position, radius);
        
        assertEquals(position, ball.getPosition(), "Position should match the constructor argument");
        assertEquals(radius, ball.getRadius(), "Radius should match the constructor argument");
        assertEquals(0.0f, ball.getRotation(), "Default rotation should be 0");
        assertFalse(ball.isStatic(), "Ball should be dynamic");
    }
    
    /**
     * Tests the creation of a bouncy ball.
     */
    @Test
    public void testBouncyBallCreation() {
        Point2D position = new Point2D(150, 150);
        float radius = 10.0f;
        
        Ball ball = Ball.createBouncyBall(position, radius);
        
        assertEquals(position, ball.getPosition(), "Position should match the constructor argument");
        assertEquals(radius, ball.getRadius(), "Radius should match the constructor argument");
        assertEquals(0.0f, ball.getRotation(), "Default rotation should be 0");
        assertFalse(ball.isStatic(), "Ball should be dynamic");
        assertTrue(ball.getRestitution() > 0.8f, "Bouncy ball should have high restitution");
    }
    
    /**
     * Tests the creation of a heavy ball.
     */
    @Test
    public void testHeavyBallCreation() {
        Point2D position = new Point2D(200, 200);
        float radius = 20.0f;
        
        Ball ball = Ball.createHeavyBall(position, radius);
        
        assertEquals(position, ball.getPosition(), "Position should match the constructor argument");
        assertEquals(radius, ball.getRadius(), "Radius should match the constructor argument");
        assertEquals(0.0f, ball.getRotation(), "Default rotation should be 0");
        assertFalse(ball.isStatic(), "Ball should be dynamic");
        assertTrue(ball.getDensity() > 3.0f, "Heavy ball should have high density");
    }
}
