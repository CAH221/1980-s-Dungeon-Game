package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class is responsible for       *//
//*              controlling the game flow, handling *//
//*              player actions, and managing        *//
//*              interactions within the dungeon.    *//
//*              It processes user input, updates    *//
//*              the dungeon state, and ensures the  *//
//*              game progresses smoothly.           *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
import java.util.Scanner;

/**
 * The GameController class handles the interactions between the player and the dungeon.
 * It manages gameplay actions, user inputs, and updates to the game environment.
 */
public class GameController {

    /**
     * Instantiate an instance of the dungeon class object. The dungeon represents the game's environment.
     */
    private final Dungeon dungeon;

    /**
     * Instantiate an instance of the player class object. The player represents the character in the game.
     */
    private final Player player;

    /**
     * A Scanner object to read input from the player during the game.
     */
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Creates a new GameController to manage the game with the instances of the dungeon and player.
     *
     * @param dungeon The game's dungeon environment.
     * @param player  The player's character in the game.
     */
    public GameController(Dungeon dungeon, Player player) {
        this.dungeon = dungeon; // Assign the dungeon instance to the controller.
        this.player = player;   // Assign the player instance to the controller.
    }


    /**
     * Starts the main game loop where the player can interact with the dungeon.
     * Continuously updates the game state, displays information, and processes player commands.
     */
    public void start() {
        while (true) {
            // Get the room the player is currently in based on their level and position.
            Room currentRoom = dungeon.getRoom(player.getLevel(), player.getPosition());

            // Show the current dungeon map with the player's location highlighted.
            System.out.println("\n\t\tCurrent Map:");
            dungeon.displayMap(player);

            // Display the player's current power points and level for reference.
            System.out.println("\nPower Points: " + player.getPowerPoints() + " | Level: " + player.getLevel());

            // Show the current room's description and its contents.
            currentRoom.describeContents();
            //System.out.println("\nCurrent Room: " + currentRoom.getDescription());
            System.out.println("Contents: " + currentRoom.describeContents());

            // Allow the player to interact with the room's features (e.g., treasures, enemies).
            dungeon.interactWithRoom(player);

            // Show a prompt for available actions the player can take.
            System.out.println("What do you want to do? (Move, Look Around, Pick Up, Use Item, Drop, Exit)");
            System.out.print("> "); // Prompt symbol for input.

            // Read the player's input and process it in lowercase for consistency.
            String command = scanner.nextLine().toLowerCase();

            // Handle the player's command based on their input.
            switch (command) {
                case "move":
                    // Move the player to another room.
                    handleMove();
                    break;

                case "look around":
                    // Let the player examine their surroundings.
                    dungeon.lookAround(player);
                    break;

                case "pick up":
                    // Pick up an item from the current room.
                    player.pickItem(currentRoom);
                    break;

                case "use item":
                    // Show the player's inventory and let them use an item.
                    // uses the showInventory method from the player class
                    player.showInventory();
                    System.out.println("Which item would you like to use? (Type the name or 'exit')");
                    String itemName = scanner.nextLine();
                    if (!itemName.equalsIgnoreCase("exit")) {
                        player.useItem(itemName);
                    }
                    break;

                case "drop":
                    // Drop an item into the current room.
                    player.dropItem(currentRoom);
                    break;

                case "exit":
                    // End the game with a thank-you message.
                    System.out.println("Thanks for playing!");
                    return;

                default:
                    // Inform the player if their input is invalid.
                    System.out.println("Invalid command. Try again.");
            }

            // Check if the player has run out of power points.
            if (player.getPowerPoints() <= 0) {
                System.out.println("Game Over! You ran out of power points.");
                return;
            }

            // Check if the player has entered the treasure room.
            if (currentRoom.isTreasureRoom()) {
                System.out.println("\nCongratulations! You found the legendary treasure and won the game!");
                return;
            }
        }
    }

    /**
     * Manages the player's movement within the dungeon.
     * Asks the player for a direction and tries to move them in that direction.
     */
    private void handleMove() {
        // Ask the player which direction they want to move.
        System.out.println("Which direction? (forward, back, right, left)");
        System.out.print("> "); // Prompt symbol for input.

        // Read the player's chosen direction and ensure it's in lowercase using Java's toLowerCase method.
        String direction = scanner.nextLine().toLowerCase();

        // Try to move the player in the chosen direction.
        // If the move is invalid, the dungeon.movePlayer method will notify the player.
        if (!dungeon.movePlayer(player, direction)) {
        }
    }

    /**
     * Handles teleportation, allowing the player to randomly move to a new position.
     * Uses a teleportation spell and updates the player's position.
     */
    private void handleTeleport() {
        // Let the player know the teleportation spell is being activated.
        System.out.println("Using teleportation spell...");

        // Randomly teleport the player within the dungeon.
        //call the teleportPlayer method from the dungeon class.
        dungeon.teleportPlayer(player);

        // Code used for testing the the teleportPlayer method works.
        // Debugging: Show the player's new position after teleportation for confirmation.
        System.out.println("Debug: Player position after teleport: (" + player.getPosition()[0] + ", " + player.getPosition()[1] + ")");
    }
}



