package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class manages the dungeon      *//
//*              generation, layout, navigation, and *//
//*              interactions in the game. It allows *//
//*              players to explore levels, move     *//
//*              between rooms, interact with items, *//
//*              encounter traps, enemies,           *//
//*              such as the Mad Scientist. The      *//
//*              dungeon consists of multiple levels,*//
//*              each with unique layouts and hidden *//
//*              treasures, requiring strategy and   *//
//*              tools for progression.              *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
public class Dungeon {
    /**
     * A dictionary that keeps track of the dungeon levels. Each level is a 2D grid of rooms.
     */
    private final Map<Integer, Room[][]> levels = new HashMap<>();

    /**
     * A 2D grid showing which parts of the dungeon the player has explored.
     */
    private final char[][] exploredMap;

    /**
     * A 3D grid showing how the whole dungeon is laid out across all its levels.
     */
    private final char[][][] dungeons;

    /**
     * The total number of levels in the dungeon.
     */
    private final int levelCount;

    /**
     * How big each level of the dungeon is.
     */
    private final int size;

    // Symbols that show the different kinds of things you might find in a room.
    private static final char WALL = 'W'; // A wall you can’t walk through
    private static final char EMPTY = ' '; // An empty room
    private static final char ENTRANCE = 'E'; // Where you enter the dungeon
    private static final char EXIT = 'X'; // Where you leave the dungeon
    private static final char TREASURE = 'T'; // Treasure to collect

    /**
     * A dictionary that gives descriptions for what’s in different rooms.
     */
    private final Map<String, String> roomContents = new HashMap<>();


    /**
     * Builds a Dungeon with the given number of levels and the size for each level.
     * Sets up the explored map and plans out how the different levels of the dungeon will look.
     *
     * @param levelCount the total number of levels in the dungeon
     * @param size       how big each level is (e.g., 5x5)
     */

    public Dungeon(int levelCount, int size) {
        // Set how many levels there are and how big each level will be.
        this.levelCount = levelCount;
        this.size = size;

        // Make a map to track what parts of the dungeon the player has explored.
        this.exploredMap = new char[size][size]; // Tracks what the player has seen so far.
        initializeExploredMap(); // Fill the explored map with default values.

        // Set up the dungeon layouts for each level using a 3D grid.
        this.dungeons = new char[][][]{
                { // Level 1: Simple layout with walls, an entrance, and an exit.
                        {WALL, EMPTY, EMPTY, EXIT, EMPTY},
                        {WALL, EMPTY, EMPTY, EMPTY, EMPTY},
                        {WALL, EMPTY, WALL, EMPTY, EMPTY},
                        {WALL, EMPTY, WALL, EMPTY, EMPTY},
                        {WALL, ENTRANCE, WALL, EMPTY, EMPTY}
                },
                { // Level 2: A trickier level with more walls and an entrance/exit.
                        {EXIT, EMPTY, WALL, EMPTY, EMPTY},
                        {EMPTY, EMPTY, WALL, EMPTY, EMPTY},
                        {WALL, EMPTY, WALL, EMPTY, EMPTY},
                        {WALL, EMPTY, WALL, EMPTY, EMPTY},
                        {EMPTY, EMPTY, EMPTY, ENTRANCE, EMPTY}
                },
                { // Level 3: Includes a treasure room and more challenging layout.
                        {TREASURE, EMPTY, EMPTY, EMPTY, EMPTY},
                        {EMPTY, WALL, WALL, WALL, WALL},
                        {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
                        {WALL, WALL, WALL, EMPTY, EMPTY},
                        {ENTRANCE, EMPTY, EMPTY, EMPTY, EMPTY}
                }
        };
    }


    /**
     * Initialises the dungeon by creating a grid of rooms for each level and populating them with default descriptions.
     * Also populates the dungeon with items after all rooms are created.
     */
    public void initialize() {
        // Go through each level in the dungeon one by one.
        for (int lvl = 0; lvl < levelCount; lvl++) {
            // Make a 2D grid of rooms for this level. It's like a map for the level.
            Room[][] level = new Room[size][size];

            // Fill the grid with rooms.
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // Make each room and give it a default description.
                    level[i][j] = new Room("");
                }
            }

            // Save this level's grid into the dungeon's list of levels.
            // Each level has a number starting from 1.
            levels.put(lvl + 1, level);
        }

        // Once all the rooms are ready, put items into the dungeon.
        populateItems();
    }

    /**
     * Sets up the explored map so all tiles start as unexplored.
     * This is done when building the dungeon before the game begins.
     */
    private void initializeExploredMap() {
        // Go through each row in the map.
        for (int i = 0; i < size; i++) {
            // Go through each column in the current row.
            for (int j = 0; j < size; j++) {
                // Mark this tile as unexplored by putting '?' in it.
                exploredMap[i][j] = '?';
            }
        }
    }

    /**
     * Fills the dungeon with a set of items that players can find.
     * These items will be placed randomly during the game.
     */
    private void populateItems() {
        Random random = new Random();

        // List of items to be added to the dungeon
        Item[] items = {
                new Spell("Teleportation Spell", this),
                new Spell("Freeze Spell", this),
                new Tool("Hammer"),
                new Tool("Spanner"),
                new Tool("Alarm Clock"),
                new Food("Cake"),
                new Food("Sandwich"),
                new Box()
        };

        // Loop through all levels in the dungeon, starting from level 1 up to the total number of levels
        for (int lvl = 1; lvl <= levelCount; lvl++) {
            // Get the 2D array of rooms for the current level from the levels collection
            Room[][] levelRooms = levels.get(lvl);

            boolean madScientistPlaced = false; // Track if a Mad Scientist is placed for this level

            // Loop through the rows of the levelRooms grid
            for (int i = 0; i < size; i++) {
                // Loop through the columns of the current row
                for (int j = 0; j < size; j++) {
                    // Get the specific room at position (i, j) in the grid
                    Room room = levelRooms[i][j];

                    // Randomly decide if the room gets an item
                    if (random.nextBoolean() && room.isEmpty()) {
                        int itemIndex = random.nextInt(items.length);
                        room.addItem(items[itemIndex]);
                    }

                    // Place a Mad Scientist in one room per level
                    if (!madScientistPlaced && random.nextInt(10) == 0 && room.isEmpty()) {
                        room.setMadScientist(new MadScientist());
                        madScientistPlaced = true; // Mark that the Mad Scientist has been placed
                        System.out.println("Mad Scientist added to Level " + lvl + " at (" + i + ", " + j + ")");
                    }

                    // Randomly decide if the room gets enemies or traps (12.5% chance)
                    if (random.nextInt(8) == 0 && room.isEmpty()) {
                        room.setHasEnemiesOrTraps(true);
                    }
                }
            }

            // If no Mad Scientist was placed after looping through all rooms, place one in a random empty room
            if (!madScientistPlaced) {
                while (true) {
                    int randomX = random.nextInt(size);
                    int randomY = random.nextInt(size);
                    Room room = levelRooms[randomX][randomY];
                    if (room.isEmpty()) {
                        room.setMadScientist(new MadScientist());
                        System.out.println("Mad Scientist (fallback) added to Level " + lvl + " at (" + randomX + ", " + randomY + ")");
                        break;
                    }
                }
            }
        }
    }

    /**
     * Gets the size of the dungeon levels.
     * Each level is assumed to have square dimensions (same number of rows and columns).
     *
     * @return The size of one side of the dungeon level.
     */
    public int getSize() {
        // Give back the size of the dungeon.
        return this.size;
    }

    /**
     * Retrieves the 3D array representing the dungeons.
     *
     * @return A 3D array of characters representing the dungeon structure.
     */
    public char[][][] getDungeons() {
        return dungeons; // Return the 3D char array containing dungeon data
    }


    /**
     * Finds the room in a specific level and position of the dungeon.
     *
     * @param level    The dungeon level to look in (starts from 1).
     * @param position An array with x (row) and y (column) coordinates of the room.
     * @return The Room object at the given level and position.
     * @throws IllegalArgumentException If the level or position is outside valid limits.
     */
    public Room getRoom(int level, int[] position) {
        // Get the x (row) and y (column) coordinates from the position array.
        int x = position[0];
        int y = position[1];

        // Check if the level number is valid (must be between 1 and the total levels).
        if (level < 1 || level > levelCount) {
            throw new IllegalArgumentException("Invalid level: " + level);
        }

        // Check if the position is valid (must be within the dungeon's size).
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IllegalArgumentException("Invalid position: (" + x + ", " + y + ")");
        }

        // Get the grid of rooms for the specified level.
        Room[][] levelRooms = levels.get(level);

        // Return the room located at the x and y coordinates.
        return levelRooms[x][y];
    }

    /**
     * Shows the explored map of the dungeon level and highlights where the player is.
     *
     * @param player The player whose position will be marked on the map.
     */
    public void displayMap(Player player) {
        // Get the player's current position as coordinates [x, y].
        int[] position = player.getPosition();
        int x = position[0]; // The row where the player is.
        int y = position[1]; // The column where the player is.

        // Go through each row of the map.
        for (int i = 0; i < size; i++) {
            // Go through each column in the current row.
            for (int j = 0; j < size; j++) {
                if (i == x && j == y) {
                    // Show the player's position with the symbol "∆".
                    System.out.print("∆ ");
                } else {
                    // Show the explored map tile for this spot.
                    System.out.print(exploredMap[i][j] + " ");
                }
            }
            // Finish this row and go to the next line.
            System.out.println("|");
        }

        // Print a line at the bottom for neatness.
        System.out.println("-----------");
    }

    /**
     * Handles the player's interaction with the current room.
     * The interaction depends on the room's type, contents, and any items or enemies present.
     *
     * @param player The player interacting with the room.
     */
    public void interactWithRoom(Player player) {
        // Get the player's current dungeon level and position.
        int level = player.getLevel() - 1; // Adjust level to match 0-based index.
        int[] position = player.getPosition();
        Room currentRoom = getRoom(level + 1, position); // Get the room the player is in.

        // Check what type of room it is.
        char roomType = dungeons[level][position[0]][position[1]];

        // Check if the room interaction has already been completed.
        if (currentRoom.hasInteracted()) {
            return; // Skip further processing for this room.
        }

        // Handle special room types.
        switch (roomType) {
            case EXIT:
                System.out.println("You found the exit to the next level!");
                levelUp(player); // Move the player to the next level.
                return;

            case TREASURE:
                System.out.println("Congratulations! You found the treasure and won the game!");
                System.exit(0); // End the game.
                return;

            default:

                if (currentRoom.hasMadScientist()) {
                    System.out.println("You encounter a Mad Scientist!");
                    currentRoom.getMadScientist().interact(player); // Delegate the interaction to the Mad Scientist class
                }

                // Check if there are enemies or traps.
                if (currentRoom.hasEnemiesOrTraps()) {
                    System.out.println("There are enemies or traps here!");
                    if (player.hasTool("Hammer")) {
                        System.out.println("You avoided the danger using your Hammer!");
                        // Remove the Hammer from the player's inventory after use.
                        player.removeItem("Hammer");
                        //System.out.println("The Hammer has been used and removed from your inventory.");

                    } else {
                        deductPowerPoints(player, 5); // Lose power points for falling into a trap.
                        System.out.println("You fell into a trap! Losing 5 power points.");
                    }
                }

                // If the room is safe.
                if (!currentRoom.hasEnemiesOrTraps() && !currentRoom.hasMadScientist()) {
                    System.out.println("The room seems safe.");
                }

                // Mark the room as interacted to stop the program for re initialising the mad scientist.
                currentRoom.setInteracted(true);
        }
    }

    /**
     * Takes away power points from the player but ensures they don't go below zero.
     *
     * @param player The player losing power points.
     * @param amount How many power points to take away.
     */
    private void deductPowerPoints(Player player, int amount) {
        // Get the player's current power points.
        int currentPoints = player.getPowerPoints();

        // Calculate the new power points, ensuring they can't drop below zero.
        player.setPowerPoints(Math.max(0, currentPoints - amount));

        // Show the player's updated power points.
        System.out.println("Your power points are now: " + player.getPowerPoints());
    }

    /**
     * Moves the player to the next level, resets the map, and sets the player's new starting position.
     * Ends the game if the player finishes the final level.
     *
     * @param player The player who is advancing to the next level.
     */
    private void levelUp(Player player) {
        // Calculate the player's new level by adding 1 to their current level.
        int newLevel = player.getLevel() + 1;

        // Check if the player has finished all levels.
        if (newLevel > levelCount) {
            System.out.println("You have completed all levels. You won!");
            System.exit(0); // End the game if all levels are done.
        }

        // Inform the player about advancing to the new level.
        System.out.println("You have advanced to Level " + newLevel + "!");

        // Update the player's level to the new one.
        player.setLevel(newLevel);

        // Reset the map for the new level so it's unexplored again.
        initializeExploredMap();

        // Decide the player's starting position for the new level.
        int[] newPosition;
        switch (newLevel) {
            case 2:
                newPosition = new int[]{4, 3}; // Set the starting spot for Level 2.
                break;
            case 3:
                newPosition = new int[]{4, 0}; // Set the starting spot for Level 3.
                break;
            default:
                // If something unexpected happens, show an error.
                throw new IllegalStateException("Unexpected level: " + newLevel);
        }

        // Update the player's position to the chosen starting spot.
        player.setPosition(newPosition[0], newPosition[1]);

        // Show the updated map with the player's new position and reset state.
        displayMap(player);
    }


    /**
     * Moves the player in a specified direction within the dungeon.
     * Updates the player's position if the move is valid, and reflects the changes on the map.
     *
     * @param player    The player who is moving.
     * @param direction The direction to move ("forward", "back", "left", or "right").
     * @return true if the move is successful, false if the move is invalid.
     */
    public boolean movePlayer(Player player, String direction) {
        // Get the player's current position as [x, y].
        int[] position = player.getPosition();
        int x = position[0]; // Current row (up or down).
        int y = position[1]; // Current column (left or right).

        // Decide the new position based on the direction given.
        switch (direction.toLowerCase()) {
            case "forward":
                x--; // Move up.
                break;
            case "back":
                x++; // Move down.
                break;
            case "left":
                y--; // Move left.
                break;
            case "right":
                y++; // Move right.
                break;
            default:
                // If the direction isn't valid, show an error message.
                System.out.println("Invalid direction!");
                return false; // The move didn't work.
        }

        // Check if the new position is within the map's boundaries and not a wall.
        if (x >= 0 && x < size && y >= 0 && y < size && dungeons[player.getLevel() - 1][x][y] != WALL) {
            // Get the player's old position before the move.
            int[] previousPosition = player.getPosition();
            int prevX = previousPosition[0];
            int prevY = previousPosition[1];

            // Mark the previous room as unvisited before moving to the new room.
            Room previousRoom = getRoom(player.getLevel(), previousPosition);
            previousRoom.setInteracted(false);

            // If the player's old spot is marked as their position ('∆'),
            // change it to '.' to show the tile has been explored.
            if (exploredMap[prevX][prevY] == '∆') {
                exploredMap[prevX][prevY] = '.'; // Mark as explored.
            }

            // Update the player's position to the new spot.
            player.setPosition(x, y);

            // Update the map to show where the player is now.
            updateMap(player);

            // Let the player know the move was successful and show the updated map.
            System.out.println("You moved " + direction + ".");
            displayMap(player); // Show the map.
            return true; // The move worked.
        } else {
            // If the move is invalid (out of bounds or a wall).
            System.out.println("  --x--      OOPS!!!      --x--     ");
            System.out.println("You hit a wall or boundary. Hit Enter to try a different direction.");

            // Wait for the player to press Enter to continue.
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine(); // Pause until Enter is pressed.
            return false; // The move didn't work.
        }
    }

    /**
     * Teleports the player to a random valid position in the current dungeon level.
     * Ensures the player does not land in a wall or stay in the same spot.
     *
     * @param player The player to be teleported.
     */
    public void teleportPlayer(Player player) {
        Random random = new Random();

        // Get the player's current position and level.
        int x = player.getPosition()[0]; // Current row position (X-coordinate).
        int y = player.getPosition()[1]; // Current column position (Y-coordinate).
        int level = player.getLevel() - 1; // Convert level to 0-based index.

        // Debugging: Show the player's position before teleportation.
        System.out.println("Current position before teleporting: " + x + ", " + y);

        // Find a random position that's not a wall and not the same as the current position.
        do {
            x = random.nextInt(size); // Pick a random row.
            y = random.nextInt(size); // Pick a random column.

            // Debugging: Show the position being checked for teleportation.
            System.out.println("Debug: Trying position (" + x + ", " + y + ")");
        } while (dungeons[level][x][y] == WALL || (x == player.getPosition()[0] && y == player.getPosition()[1]));

        // Set the player's new position to the chosen coordinates.
        player.setPosition(x, y);

        // Debugging: Show the player's position after teleportation.
        System.out.println("New position after teleporting: " + x + ", " + y);

        // Update the map to show the player's new position.
        updateMap(player);

        // Let the player know they've been teleported.
        System.out.println("You teleported to a new location.");

        // Show the updated map with the new position.
        displayMap(player);
    }

    /**
     * Gets the contents of the room where the player is currently located.
     *
     * @param player The player whose current room's contents are being checked.
     * @return A string describing the contents of the room, or null if the room is empty.
     */
    public String getCurrentRoom(Player player) {
        // Get the player's current level, adjusted to match 0-based indexing.
        int level = player.getLevel() - 1;

        // Get the player's current position as [x, y].
        int[] position = player.getPosition();
        int x = position[0]; // Row index of the player's position.
        int y = position[1]; // Column index of the player's position.

        // Use the level, row, and column to find the room's contents in the roomContents map.
        // The key format is "level-x-y".
        return roomContents.get(level + "-" + x + "-" + y);
    }

    /**
     * Picks a random item from a predefined list of items.
     * This is useful for simulating item discovery or generation in the game.
     *
     * @return The name of a randomly chosen item as a string.
     */
    public String revealRandomItem() {
        // List of items that could be revealed.
        String[] items = {"Potion", "Teleportation Spell", "Freeze Spell"};

        // Randomly pick one item from the list and return it.
        return items[new Random().nextInt(items.length)];
    }

    /**
     * Lets the player look around their current position in the dungeon.
     * Reveals the tiles surrounding the player's position and updates the explored map.
     *
     * @param player The player who is looking around.
     */
    public void lookAround(Player player) {
        // Inform the player about the action.
        System.out.println("Looking around...");

        // Get the player's current level, adjusted for 0-based indexing.
        int level = player.getLevel() - 1;

        // Get the player's current position as [x, y].
        int[] position = player.getPosition();
        int x = position[0]; // Row index of the player's position.
        int y = position[1]; // Column index of the player's position.

        // Update the map to reveal the tile where the player is standing.
        updateMap(level, x, y);

        // Check and reveal the surrounding tiles, ensuring they are within the map boundaries.
        if (x > 0) updateMap(level, x - 1, y); // Reveal the tile above (forward).
        if (x < size - 1) updateMap(level, x + 1, y); // Reveal the tile below (backward).
        if (y > 0) updateMap(level, x, y - 1); // Reveal the tile to the left.
        if (y < size - 1) updateMap(level, x, y + 1); // Reveal the tile to the right.

        // Show the updated map with the newly revealed areas.
        displayMap(player);
    }

    /**
     * Updates the explored map for a specific tile at the given level and position.
     * Ensures the tile is within bounds and updates only if it hasn't been explored yet.
     *
     * @param level The dungeon level being updated.
     * @param x     The row index of the tile to update.
     * @param y     The column index of the tile to update.
     */
    private void updateMap(int level, int x, int y) {
        // Check if the given coordinates are inside the map boundaries.
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return; // If the coordinates are out of bounds, stop here.
        }

        // Only update the tile if it hasn't been explored yet (marked as '?').
        if (exploredMap[x][y] == '?') {
            // Get the tile's symbol from the dungeon map at the given level and position.
            char symbol = dungeons[level][x][y];

            // If the tile is EMPTY, keep it as '?'; otherwise, use the actual symbol.
            exploredMap[x][y] = (symbol == EMPTY) ? '?' : symbol;
        }
    }

    /**
     * Updates the explored map to show the player's current position and resets their previous position.
     *
     * @param player The player whose position is being updated on the map.
     */
    private void updateMap(Player player) {
        // Get the current dungeon level, adjusting for 0-based indexing.
        int level = player.getLevel() - 1;

        // Get the player's current position as row (X) and column (Y) coordinates.
        int x = player.getPosition()[0]; // Current row index.
        int y = player.getPosition()[1]; // Current column index.

        // Mark the player's current position on the map with the '∆' symbol.
        exploredMap[x][y] = '∆';

        // If the player has a recorded previous position, reset that tile.
        if (player.getPreviousPosition() != null) {
            int prevX = player.getPreviousPosition()[0]; // Previous row index.
            int prevY = player.getPreviousPosition()[1]; // Previous column index.

            // If the previous position still shows the player's symbol, mark it as explored ('.').
            if (exploredMap[prevX][prevY] == '∆') {
                exploredMap[prevX][prevY] = '.'; // Indicate the tile has been explored.
            }
        }

        // Save the player's current position as their new previous position.
        player.setPreviousPosition(new int[]{x, y});
    }
}




