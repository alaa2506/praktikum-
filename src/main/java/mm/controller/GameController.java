package mm.controller;

import javafx.geometry.Point2D;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import mm.model.level.Level;
import mm.model.level.LevelLoader;
import mm.model.objects.GameObject;
import mm.model.physics.PhysicsWorld;

/**
 * Controls the game state and logic.
 */
public class GameController {
    private PhysicsWorld physicsWorld;
    private List<String> levelFiles;
    private int currentLevelIndex;
    private Level currentLevel;
    private boolean isSimulating;
    private boolean levelCompleted;
    private String completionMessage;

    /**
     * Creates a new game controller.
     */
    public GameController() {
        physicsWorld = new PhysicsWorld();
        isSimulating = false;
        levelCompleted = false;
        completionMessage = "";
        // List of level files (add your actual level file paths here)
        levelFiles = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            levelFiles.add("level" + i + ".json");
        }
        currentLevelIndex = 0;
    }

    /**
     * Loads the current level.
     */
    public void loadCurrentLevel() throws IOException {
        if (currentLevelIndex >= 0 && currentLevelIndex < levelFiles.size()) {
            currentLevel = LevelLoader.loadLevel(levelFiles.get(currentLevelIndex));
            resetLevel();
        }
    }

    /**
     * Loads a specific level by index.
     */
    public void loadLevelByIndex(int index) throws IOException {
        if (index >= 0 && index < levelFiles.size()) {
            currentLevelIndex = index;
            loadCurrentLevel();
        }
    }

    /**
     * Loads the next level.
     */
    public void loadNextLevel() throws IOException {
        if (currentLevelIndex + 1 < levelFiles.size()) {
            currentLevelIndex++;
            loadCurrentLevel();
        }
    }

    /**
     * Gets the current level number (1-based).
     */
    public int getCurrentLevelNumber() {
        return currentLevelIndex + 1;
    }

    /**
     * Gets the total number of levels.
     */
    public int getTotalLevels() {
        return levelFiles.size();
    }

    /**
     * Resets the current level.
     */
    public void resetLevel() {
        physicsWorld.clear();

        if (currentLevel != null) {
            currentLevel.resetInventory();
            // Add static objects
            for (GameObject gameObject : currentLevel.getStaticObjects()) {
                physicsWorld.addGameObject(gameObject);
            }

            // Add dynamic objects
            for (GameObject gameObject : currentLevel.getDynamicObjects()) {
                physicsWorld.addGameObject(gameObject);
            }
        }

        isSimulating = false;
        levelCompleted = false;
        completionMessage = "";
    }

    /**
     * Starts the simulation.
     */
    public void startSimulation() {
        isSimulating = true;
    }

    /**
     * Stops the simulation.
     */
    public void stopSimulation() {
        isSimulating = false;
    }

    /**
     * Updates the game state.
     *
     * @param deltaTime The time elapsed since the last update in seconds
     */
    public void update(float deltaTime) {
        if (isSimulating && currentLevel != null) {
            physicsWorld.update(deltaTime);

            // Check win conditions
            if (currentLevel.checkWinConditions(physicsWorld.getGameObjects())) {
                // Level completed
                isSimulating = false;
                levelCompleted = true;
                completionMessage = "Level " + getCurrentLevelNumber() + " completed! Moving to next level...";
                try {
                    loadNextLevel();
                } catch (IOException e) {
                    completionMessage = "Congratulations! All levels completed.";
                }
            }
        }
    }
    
    /**
     * Places an object in the level.
     *
     * @param objectType The type of object to place
     * @param position The position to place the object
     * @return True if the object was placed, false otherwise
     */
    public boolean placeObject(String objectType, Point2D position) {
        if (isSimulating || currentLevel == null) {
            return false;
        }
        
        // Check if the position is in a restriction zone
        if (currentLevel.isInRestrictionZone(position)) {
            return false;
        }
        
        // Check if there are enough items in the inventory
        if (!currentLevel.removeInventoryItem(objectType, 1)) {
            return false;
        }
        
        // Create and add the object
        GameObject gameObject = createGameObject(objectType, position);
        if (gameObject != null) {
            physicsWorld.addGameObject(gameObject);
            return true;
        }
        
        // If we get here, something went wrong, so add the item back to the inventory
        currentLevel.addInventoryItem(objectType, 1);
        return false;
    }
    
    /**
     * Removes an object from the level.
     *
     * @param position The position to remove the object from
     * @return True if an object was removed, false otherwise
     */
    public boolean removeObject(Point2D position) {
        if (isSimulating || currentLevel == null) {
            return false;
        }
        
        // Find the object at the position
        GameObject objectToRemove = null;
        for (GameObject gameObject : physicsWorld.getGameObjects()) {
            if (isNear(gameObject.getPosition(), position, 10)) {
                objectToRemove = gameObject;
                break;
            }
        }
        
        if (objectToRemove != null) {
            // Add the item back to the inventory
            String objectType = objectToRemove.getClass().getSimpleName();
            currentLevel.addInventoryItem(objectType, 1);
            
            // Remove the object
            physicsWorld.removeGameObject(objectToRemove);
            return true;
        }
        
        return false;
    }
    
    /**
     * Checks if two points are near each other.
     *
     * @param p1 The first point
     * @param p2 The second point
     * @param threshold The distance threshold
     * @return True if the points are within the threshold distance, false otherwise
     */
    private boolean isNear(Point2D p1, Point2D p2, double threshold) {
        return p1.distance(p2) <= threshold;
    }
    
    /**
     * Creates a game object of the specified type at the specified position.
     *
     * @param objectType The type of object to create
     * @param position The position to create the object at
     * @return The created game object, or null if the object type is not supported
     */
    private GameObject createGameObject(String objectType, Point2D position) {
        // This is a simple implementation. In a real game, you would use a factory pattern.
        switch (objectType) {
            case "Ball":
                return new mm.model.objects.impl.Ball(position, 10);
            case "BouncyBall":
                return mm.model.objects.impl.Ball.createBouncyBall(position, 10);
            case "HeavyBall":
                return mm.model.objects.impl.Ball.createHeavyBall(position, 15);
            case "Box":
                return new mm.model.objects.impl.Box(position, 30, 30);
            case "LightBox":
                return mm.model.objects.impl.Box.createLightBox(position, 30, 30);
            case "HeavyBox":
                return mm.model.objects.impl.Box.createHeavyBox(position, 30, 30);
            case "Plank":
                return new mm.model.objects.impl.Plank(position, 0, 50, 10);
            case "Log":
                return new mm.model.objects.impl.Log(position, 0, 100, 20);
            case "Balloon":
                return new mm.model.objects.impl.Balloon(position, 15);
            default:
                return null;
        }
    }
    
    /**
     * Gets the current level.
     *
     * @return The current level
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
    
    /**
     * Gets the physics world.
     *
     * @return The physics world
     */
    public PhysicsWorld getPhysicsWorld() {
        return physicsWorld;
    }
    
    /**
     * Checks if the game is currently simulating.
     *
     * @return True if the game is simulating, false otherwise
     */
    public boolean isSimulating() {
        return isSimulating;
    }
    
    /**
     * Checks if the level is completed.
     *
     * @return True if the level is completed, false otherwise
     */
    public boolean isLevelCompleted() {
        return levelCompleted;
    }
    
    /**
     * Gets the completion message.
     *
     * @return The completion message
     */
    public String getCompletionMessage() {
        return completionMessage;
    }
    
    /**
     * Resets the level completion status.
     */
    public void resetLevelCompletion() {
        levelCompleted = false;
        completionMessage = "";
    }
}
