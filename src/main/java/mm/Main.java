package mm;

import mm.controller.GameController;
import mm.gui.Gui;

/**
 * The common starting point of the GUI.
 */
public class Main {
    /**
     * The external entry point of the application.
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        System.out.println("Starting Crazy Machines...");
        
        // Create the game controller
        GameController gameController = new GameController();
        
        // Start the GUI
        Gui.setGameController(gameController);
        Gui.main(args);
        
        System.out.println("Exiting Crazy Machines...");
    }
}
