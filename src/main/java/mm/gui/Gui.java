package mm.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import mm.controller.GameController;
import mm.model.objects.GameObject;
import java.io.IOException;
import java.util.Map;

/**
 * The main GUI for the Crazy Machines game.
 */
public class Gui extends Application {
    private static GameController gameController;
    
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private boolean isEditorMode = true;
    private String selectedObjectType = "Ball";
    
    /**
     * Sets the game controller.
     *
     * @param controller The game controller
     */
    public static void setGameController(GameController controller) {
        gameController = controller;
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Crazy Machines");
        
        // Create the main layout
        BorderPane root = new BorderPane();
        
        // Create the game canvas
        gameCanvas = new Canvas(800, 600);
        gc = gameCanvas.getGraphicsContext2D();
        
        // Create the toolbar
        HBox toolbar = createToolbar();
        
        // Create the inventory panel
        VBox inventoryPanel = createInventoryPanel();
        
        // Add components to the layout
        root.setTop(toolbar);
        root.setCenter(gameCanvas);
        root.setRight(inventoryPanel);
        
        // Create the scene
        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Load the first level
        try {
            gameController.loadCurrentLevel();
        } catch (IOException e) {
            System.err.println("Failed to load level: " + e.getMessage());
        }
        
        // Set up mouse handlers
        setupMouseHandlers();
        
        // Start the game loop
        startGameLoop();
    }
    
    /**
     * Creates the toolbar with play/reset buttons.
     *
     * @return The toolbar
     */
    private HBox createToolbar() {
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        
        Button playButton = new Button("Play");
        playButton.setOnAction(event -> {
            if (isEditorMode) {
                gameController.startSimulation();
                isEditorMode = false;
                playButton.setText("Reset");
            } else {
                gameController.stopSimulation();
                gameController.resetLevel();
                isEditorMode = true;
                playButton.setText("Play");
            }
        });
        
        Label titleLabel = new Label("Crazy Machines");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        toolbar.getChildren().addAll(playButton, titleLabel);
        return toolbar;
    }
    
    /**
     * Creates the inventory panel.
     *
     * @return The inventory panel
     */
    private VBox createInventoryPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        panel.setStyle("-fx-background-color: #f0f0f0;");
        
        Label inventoryLabel = new Label("Inventory");
        inventoryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Create category labels
        Label dynamicLabel = new Label("Dynamic Objects");
        dynamicLabel.setStyle("-fx-font-weight: bold;");
        
        Label staticLabel = new Label("Static Objects");
        staticLabel.setStyle("-fx-font-weight: bold;");
        
        Label specialLabel = new Label("Special Objects");
        specialLabel.setStyle("-fx-font-weight: bold;");
        
        // Dynamic objects
        Button ballButton = new Button("Ball");
        ballButton.setOnAction(event -> selectedObjectType = "Ball");
        ballButton.setPrefWidth(120);
        
        Button bouncyBallButton = new Button("Bouncy Ball");
        bouncyBallButton.setOnAction(event -> selectedObjectType = "BouncyBall");
        bouncyBallButton.setPrefWidth(120);
        
        Button heavyBallButton = new Button("Heavy Ball");
        heavyBallButton.setOnAction(event -> selectedObjectType = "HeavyBall");
        heavyBallButton.setPrefWidth(120);
        
        Button boxButton = new Button("Box");
        boxButton.setOnAction(event -> selectedObjectType = "Box");
        boxButton.setPrefWidth(120);
        
        Button lightBoxButton = new Button("Light Box");
        lightBoxButton.setOnAction(event -> selectedObjectType = "LightBox");
        lightBoxButton.setPrefWidth(120);
        
        Button heavyBoxButton = new Button("Heavy Box");
        heavyBoxButton.setOnAction(event -> selectedObjectType = "HeavyBox");
        heavyBoxButton.setPrefWidth(120);
        
        // Static objects
        Button plankButton = new Button("Plank");
        plankButton.setOnAction(event -> selectedObjectType = "Plank");
        plankButton.setPrefWidth(120);
        
        Button logButton = new Button("Log");
        logButton.setOnAction(event -> selectedObjectType = "Log");
        logButton.setPrefWidth(120);
        
        // Special objects
        Button balloonButton = new Button("Balloon");
        balloonButton.setOnAction(event -> selectedObjectType = "Balloon");
        balloonButton.setPrefWidth(120);
        
        // Add all components to the panel
        panel.getChildren().addAll(
            inventoryLabel,
            dynamicLabel,
            ballButton, bouncyBallButton, heavyBallButton,
            boxButton, lightBoxButton, heavyBoxButton,
            staticLabel,
            plankButton, logButton,
            specialLabel,
            balloonButton
        );
        
        return panel;
    }
    
    /**
     * Sets up mouse handlers for the game canvas.
     */
    private void setupMouseHandlers() {
        gameCanvas.setOnMouseClicked(event -> {
            if (isEditorMode && gameController != null) {
                Point2D position = new Point2D(event.getX(), event.getY());
                
                if (event.isSecondaryButtonDown()) {
                    // Right-click to remove objects
                    gameController.removeObject(position);
                } else {
                    // Left-click to place objects
                    gameController.placeObject(selectedObjectType, position);
                }
            }
        });
    }
    
    /**
     * Starts the game loop.
     */
    private void startGameLoop() {
        new AnimationTimer() {
            private long lastUpdate = 0;
            
            @Override
            public void handle(long now) {
                // Update at 60 FPS
                if (now - lastUpdate >= 16_666_667) { // ~60 FPS (1/60 second in nanoseconds)
                    // Update game state
                    if (!isEditorMode && gameController != null) {
                        gameController.update(0.016f); // 16ms in seconds
                    }
                    
                    // Render the game
                    render();
                    
                    lastUpdate = now;
                }
            }
        }.start();
    }
    
    /**
     * Renders the game.
     */
    private void render() {
        // Clear the canvas
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());

        if (gameController != null) {
            // Draw current level number at the top center
            gc.setFill(Color.DARKBLUE);
            gc.setFont(javafx.scene.text.Font.font("Arial", 28));
            String levelText = "Level " + gameController.getCurrentLevelNumber();
            // Estimate text width (JavaFX doesn't provide getFontMetrics directly)
            javafx.scene.text.Font font = gc.getFont();
            javafx.scene.text.Text tempText = new javafx.scene.text.Text(levelText);
            tempText.setFont(font);
            double textWidth = tempText.getLayoutBounds().getWidth();
            gc.fillText(levelText, (gameCanvas.getWidth() - textWidth) / 2, 40);

            // Draw game objects
            for (GameObject gameObject : gameController.getPhysicsWorld().getGameObjects()) {
                gameObject.render(gc);
            }

            // Draw win conditions
            if (gameController.getCurrentLevel() != null) {
                gc.setStroke(Color.GREEN);
                gc.setLineWidth(2);
                for (var winCondition : gameController.getCurrentLevel().getWinConditions()) {
                    var area = winCondition.getArea();
                    gc.strokeRect(area.getMinX(), area.getMinY(), area.getWidth(), area.getHeight());
                }
            }

            // Draw restriction zones
            if (gameController.getCurrentLevel() != null) {
                gc.setStroke(Color.RED);
                gc.setLineWidth(2);
                for (var zone : gameController.getCurrentLevel().getRestrictionZones()) {
                    var area = zone.getArea();
                    gc.strokeRect(area.getMinX(), area.getMinY(), area.getWidth(), area.getHeight());
                }
            }

            // Draw inventory
            if (gameController.getCurrentLevel() != null) {
                gc.setFill(Color.BLACK);
                gc.setLineWidth(1);
                int y = 30;
                for (Map.Entry<String, Integer> entry : gameController.getCurrentLevel().getInventory().entrySet()) {
                    gc.fillText(entry.getKey() + ": " + entry.getValue(), 10, y);
                    y += 20;
                }
            }

            // Draw mode indicator
            gc.setFill(Color.BLACK);
            gc.fillText(isEditorMode ? "Editor Mode" : "Simulation Mode", 10, 10);

            // Draw win condition completion message
            if (gameController.isLevelCompleted()) {
                gc.setFill(Color.DARKGREEN);
                gc.setFont(javafx.scene.text.Font.font("Arial", 24));
                gc.fillText(gameController.getCompletionMessage(), 250, 80);
            }
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
