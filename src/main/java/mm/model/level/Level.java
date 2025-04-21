package mm.model.level;

import javafx.geometry.Point2D;
import mm.model.objects.GameObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a level in the game.
 * A level contains game objects, win conditions, restriction zones, and an inventory.
 */
public class Level {
    private final String name;
    private final String description;
    private final List<GameObject> staticObjects;
    private final List<GameObject> dynamicObjects;
    private final List<WinCondition> winConditions;
    private final List<RestrictionZone> restrictionZones;
    private final Map<String, Integer> inventory;
    private final Map<String, Integer> originalInventory;

    /**
     * Creates a new level.
     *
     * @param name The name of the level
     * @param description The description of the level
     */
    public Level(String name, String description) {
        this.name = name;
        this.description = description;
        this.staticObjects = new ArrayList<>();
        this.dynamicObjects = new ArrayList<>();
        this.winConditions = new ArrayList<>();
        this.restrictionZones = new ArrayList<>();
        this.inventory = new HashMap<>();
        this.originalInventory = new HashMap<>();
    }
    
    /**
     * Adds a static object to the level.
     *
     * @param gameObject The static object to add
     */
    public void addStaticObject(GameObject gameObject) {
        staticObjects.add(gameObject);
    }
    
    /**
     * Adds a dynamic object to the level.
     *
     * @param gameObject The dynamic object to add
     */
    public void addDynamicObject(GameObject gameObject) {
        dynamicObjects.add(gameObject);
    }
    
    /**
     * Adds a win condition to the level.
     *
     * @param winCondition The win condition to add
     */
    public void addWinCondition(WinCondition winCondition) {
        winConditions.add(winCondition);
    }
    
    /**
     * Adds a restriction zone to the level.
     *
     * @param restrictionZone The restriction zone to add
     */
    public void addRestrictionZone(RestrictionZone restrictionZone) {
        restrictionZones.add(restrictionZone);
    }
    
    /**
     * Adds an item to the inventory.
     *
     * @param itemType The type of item to add
     * @param count The number of items to add
     */
    public void addInventoryItem(String itemType, int count) {
        inventory.put(itemType, inventory.getOrDefault(itemType, 0) + count);
        originalInventory.put(itemType, inventory.get(itemType));
    }

    /**
     * Resets the inventory to its original state.
     */
    public void resetInventory() {
        inventory.clear();
        inventory.putAll(originalInventory);
    }
    
    /**
     * Removes an item from the inventory.
     *
     * @param itemType The type of item to remove
     * @param count The number of items to remove
     * @return True if the items were removed, false if there weren't enough items
     */
    public boolean removeInventoryItem(String itemType, int count) {
        int currentCount = inventory.getOrDefault(itemType, 0);
        if (currentCount >= count) {
            inventory.put(itemType, currentCount - count);
            return true;
        }
        return false;
    }
    
    /**
     * Checks if a point is inside any restriction zone.
     *
     * @param point The point to check
     * @return True if the point is inside a restriction zone, false otherwise
     */
    public boolean isInRestrictionZone(Point2D point) {
        for (RestrictionZone zone : restrictionZones) {
            if (zone.contains(point)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Checks if all win conditions are satisfied.
     *
     * @param gameObjects The list of game objects to check against
     * @return True if all win conditions are satisfied, false otherwise
     */
    public boolean checkWinConditions(List<GameObject> gameObjects) {
        boolean allSatisfied = true;
        for (WinCondition condition : winConditions) {
            if (!condition.checkSatisfied(gameObjects)) {
                allSatisfied = false;
            }
        }
        return allSatisfied;
    }
    
    /**
     * Gets all game objects in the level.
     *
     * @return The list of all game objects
     */
    public List<GameObject> getAllGameObjects() {
        List<GameObject> allObjects = new ArrayList<>(staticObjects);
        allObjects.addAll(dynamicObjects);
        return allObjects;
    }
    
    // Getters
    
    /**
     * Gets the name of the level.
     *
     * @return The name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the description of the level.
     *
     * @return The description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Gets the static objects in the level.
     *
     * @return The list of static objects
     */
    public List<GameObject> getStaticObjects() {
        return new ArrayList<>(staticObjects);
    }
    
    /**
     * Gets the dynamic objects in the level.
     *
     * @return The list of dynamic objects
     */
    public List<GameObject> getDynamicObjects() {
        return new ArrayList<>(dynamicObjects);
    }
    
    /**
     * Gets the win conditions for the level.
     *
     * @return The list of win conditions
     */
    public List<WinCondition> getWinConditions() {
        return new ArrayList<>(winConditions);
    }
    
    /**
     * Gets the restriction zones in the level.
     *
     * @return The list of restriction zones
     */
    public List<RestrictionZone> getRestrictionZones() {
        return new ArrayList<>(restrictionZones);
    }
    
    /**
     * Gets the inventory for the level.
     *
     * @return The inventory as a map of item types to counts
     */
    public Map<String, Integer> getInventory() {
        return new HashMap<>(inventory);
    }
}
