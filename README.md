# MM - Mad Machines - Gruppe 24

A physics-based puzzle game inspired by "Crazy Machines" where players complete contraptions to create chain reactions that satisfy win conditions.

## Project Overview

This project is a low-fidelity clone of the game "Crazy Machines" (2005), developed as part of the Software Engineering lab course at the University of Lübeck.

### Core Features

- Switching between simulation and editor view with play/reset button
- Physics objects (static, dynamic, and special)
- Editor view for placing/removing objects
- Level loading using JSON files
- Puzzle mode with level progression
- Simple graphics with basic shapes and colors
- Win conditions, restriction zones, and inventory

## Getting Started

### Prerequisites

- Java 11 or higher
- Maven

> **Note:**  
> The project uses the jbox2d physics engine, which is provided as an automatic module (`jbox2d.library`).  
> You may see a warning about the module name being unstable. This is expected and does not affect the build or runtime as long as the JAR file name is not changed.  
> If you need to suppress this warning, consider using a version of jbox2d with a proper `module-info.class` or create a custom module wrapper.

### Building the Project

To build the project, run:

```bash
mvn clean install
```

### Running the Project

To run the project, use:

```bash
mvn javafx:run
```

## Project Structure

The project follows the Model-View-Controller (MVC) architecture:

### Model

- `mm.model.objects`: Contains the game object classes
  - `GameObject`: Base class for all game objects
  - `StaticObject`: Base class for static (immovable) objects
  - `DynamicObject`: Base class for dynamic (movable) objects
  - `SpecialObject`: Base class for objects with special behaviors
  - `impl`: Contains concrete implementations of game objects

- `mm.model.physics`: Contains physics-related classes
  - `PhysicsWorld`: Wrapper for the jBox2D physics engine

- `mm.model.level`: Contains level-related classes
  - `Level`: Represents a game level
  - `LevelLoader`: Loads levels from JSON files
  - `WinCondition`: Represents a win condition
  - `RestrictionZone`: Represents a restriction zone

### View

- `mm.gui`: Contains the GUI classes
  - `Gui`: The main GUI class

### Controller

- `mm.controller`: Contains the controller classes
  - `GameController`: Controls the game state and logic

## Game Objects

### Static Objects

- `Plank`: A long, thin rectangle that serves as a platform or barrier

### Dynamic Objects

- `Ball`: A circle that can roll and bounce
  - Regular Ball: Default ball with medium density and restitution
  - Bouncy Ball: Ball with high restitution (very bouncy)
  - Heavy Ball: Ball with high density (heavy)

### Special Objects

- `Balloon`: A circle that floats upward due to buoyancy

## Level Format

Levels are stored in JSON format with the following structure:

```json
{
    "name": "Level Name",
    "description": "Level Description",
    "staticObjects": [
        {
            "type": "Plank",
            "x": 200,
            "y": 300,
            "rotation": 0,
            "width": 200,
            "height": 20
        }
    ],
    "dynamicObjects": [
        {
            "type": "Ball",
            "x": 100,
            "y": 100,
            "rotation": 0,
            "radius": 15
        }
    ],
    "winConditions": [
        {
            "objectType": "Ball",
            "area": {
                "x": 350,
                "y": 350,
                "width": 100,
                "height": 100
            }
        }
    ],
    "restrictionZones": [
        {
            "x": 0,
            "y": 0,
            "width": 100,
            "height": 100
        }
    ],
    "inventory": {
        "Ball": 5,
        "Plank": 3,
        "Balloon": 2
    }
}
```

## Controls

- **Left-click**: Place an object from the inventory
- **Right-click**: Remove an object
- **Play button**: Start the simulation
- **Reset button**: Stop the simulation and reset the level

## Development Team

- Gruppe 24
