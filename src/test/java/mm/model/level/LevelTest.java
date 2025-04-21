package mm.model.level;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import mm.model.objects.impl.Ball;
import mm.model.objects.impl.Plank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Level class.
 */
public class LevelTest {
    
    /**
     * Tests the creation of a level.
     */
    @Test
    public void testLevelCreation() {
        String name = "Test Level";
        String description = "A test level";
        
        Level level = new Level(name, description);
        
        assertEquals(name, level.getName(), "Name should match the constructor argument");
        assertEquals(description, level.getDescription(), "Description should match the constructor argument");
        assertTrue(level.getStaticObjects().isEmpty(), "Static objects list should be empty initially");
        assertTrue(level.getDynamicObjects().isEmpty(), "Dynamic objects list should be empty initially");
        assertTrue(level.getWinConditions().isEmpty(), "Win conditions list should be empty initially");
        assertTrue(level.getRestrictionZones().isEmpty(), "Restriction zones list should be empty initially");
        assertTrue(level.getInventory().isEmpty(), "Inventory should be empty initially");
    }
    
    /**
     * Tests adding objects to a level.
     */
    @Test
    public void testAddingObjects() {
        Level level = new Level("Test Level", "A test level");
        
        // Add a static object
        Plank plank = new Plank(new Point2D(200, 300), 0, 200, 20);
        level.addStaticObject(plank);
        
        // Add a dynamic object
        Ball ball = new Ball(new Point2D(100, 100), 15);
        level.addDynamicObject(ball);
        
        assertEquals(1, level.getStaticObjects().size(), "Static objects list should have one item");
        assertEquals(1, level.getDynamicObjects().size(), "Dynamic objects list should have one item");
        assertEquals(plank, level.getStaticObjects().get(0), "Static object should be the plank we added");
        assertEquals(ball, level.getDynamicObjects().get(0), "Dynamic object should be the ball we added");
    }
    
    /**
     * Tests adding win conditions to a level.
     */
    @Test
    public void testAddingWinConditions() {
        Level level = new Level("Test Level", "A test level");
        
        // Add a win condition
        Rectangle2D area = new Rectangle2D(350, 350, 100, 100);
        WinCondition winCondition = new WinCondition("Ball", area);
        level.addWinCondition(winCondition);
        
        assertEquals(1, level.getWinConditions().size(), "Win conditions list should have one item");
        assertEquals(winCondition, level.getWinConditions().get(0), "Win condition should be the one we added");
    }
    
    /**
     * Tests adding restriction zones to a level.
     */
    @Test
    public void testAddingRestrictionZones() {
        Level level = new Level("Test Level", "A test level");
        
        // Add a restriction zone
        Rectangle2D area = new Rectangle2D(0, 0, 100, 100);
        RestrictionZone restrictionZone = new RestrictionZone(area);
        level.addRestrictionZone(restrictionZone);
        
        assertEquals(1, level.getRestrictionZones().size(), "Restriction zones list should have one item");
        assertEquals(restrictionZone, level.getRestrictionZones().get(0), "Restriction zone should be the one we added");
    }
    
    /**
     * Tests adding and removing inventory items.
     */
    @Test
    public void testInventory() {
        Level level = new Level("Test Level", "A test level");
        
        // Add inventory items
        level.addInventoryItem("Ball", 5);
        level.addInventoryItem("Plank", 3);
        
        assertEquals(2, level.getInventory().size(), "Inventory should have two items");
        assertEquals(5, level.getInventory().get("Ball"), "Ball count should be 5");
        assertEquals(3, level.getInventory().get("Plank"), "Plank count should be 3");
        
        // Remove inventory items
        assertTrue(level.removeInventoryItem("Ball", 2), "Should be able to remove 2 balls");
        assertEquals(3, level.getInventory().get("Ball"), "Ball count should be 3 after removal");
        
        // Try to remove too many items
        assertFalse(level.removeInventoryItem("Plank", 5), "Should not be able to remove 5 planks");
        assertEquals(3, level.getInventory().get("Plank"), "Plank count should still be 3");
    }
    
    /**
     * Tests checking if a point is in a restriction zone.
     */
    @Test
    public void testIsInRestrictionZone() {
        Level level = new Level("Test Level", "A test level");
        
        // Add a restriction zone
        Rectangle2D area = new Rectangle2D(0, 0, 100, 100);
        RestrictionZone restrictionZone = new RestrictionZone(area);
        level.addRestrictionZone(restrictionZone);
        
        // Test points
        assertTrue(level.isInRestrictionZone(new Point2D(50, 50)), "Point inside the zone should return true");
        assertFalse(level.isInRestrictionZone(new Point2D(150, 150)), "Point outside the zone should return false");
    }
}
