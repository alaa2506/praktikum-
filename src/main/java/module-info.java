/**
 * The main module of the mm application.
 */
module mm {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires transitive jbox2d.library;
    
    exports mm.gui;
    exports mm.controller;
    exports mm.model.objects;
    exports mm.model.objects.impl;
    exports mm.model.physics;
    exports mm.model.level;
    
    // Open packages to allow reflection access for JSON parsing
    opens mm.model.objects.impl;
    opens mm.model.level;
}
