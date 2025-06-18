package dungeongame;
import java.util.Scanner;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class represents a tool item   *//
//*              in the dungeon game. It extends the *//
//*              Item class and provides specific    *//
//*              behaviour for using tools, such as   *//
//*              breaking traps or opening boxes.    *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
/**
 * Represents a tool that a player can use in the game.
 * Inherits properties and methods from the {@code Item} class.
 * Each tool has a specific effect depending on its name.
 */
public class Tool extends Item {

    /**
     * Creates a new {@code Tool} with a specified name.
     *
     * @param name The name of the tool.
     */
    public Tool(String name) {
        super(name); // Use the parent class constructor to set the tool's name.
    }

    /**
     * Activates the tool's effect when used by the player.
     * The effect depends on the tool's name (e.g., Hammer, Spanner, Alarm Clock).
     *
     * @param player The player using the tool.
     */
    @Override
    public void use(Player player) {
        System.out.println("Using tool: " + getName());

        // Specific effect for the Hammer tool.
        if (getName().equalsIgnoreCase("Hammer")) {
            System.out.println("You break a trap with the Hammer!");
        }
        // Specific effect for the Spanner tool.
        else if (getName().equalsIgnoreCase("Spanner")) {
            System.out.println("You have a Spanner.");

            // Check if the player has a Box in their inventory.
            // pass the box class using java's inbuilt java.lang.class to check if the box object has been created, if not box is not in inventory
            if (player.hasItem(Box.class)) {
                Box box = (Box) player.getItem(Box.class); // Retrieve the Box.
                System.out.println("You also have a Box. Would you like to open it? (yes/no)");
                Scanner scanner = new Scanner(System.in);
                String response = scanner.nextLine().toLowerCase();

                if (response.equals("yes")) {
                    Dungeon dungeon = player.getDungeon(); // Retrieve the Dungeon instance.
                    if (dungeon != null) {
                        box.open(player, dungeon); // Pass the Dungeon to the Box's open method.
                    } else {
                        System.out.println("Error: Dungeon instance is not available.");
                    }
                } else {
                    System.out.println("You chose not to open the Box.");
                }
            } else {
                System.out.println("You don't have a Box to open.");
            }
        }
        // Specific effect for the Alarm Clock tool.
        else if (getName().equalsIgnoreCase("Alarm Clock")) {
            // Check if the player is already awake (not under the effect of a Sleeping Potion).
            System.out.println("You are already awake. The Alarm Clock remains in your inventory.");
        }
        // Default message for tools with no specific effect.
        else {
            System.out.println("This tool has no specific use.");
        }
    }
}



