package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class represents the player in *//
//*              the dungeon game. It manages the    *//
//*              player's state, including power     *//
//*              points, level, inventory, and       *//
//*              position. The player can pick up,   *//
//*              use, or drop items, and move within *//
//*              the dungeon.                        *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

/**
 * Represents a player in the game. The player has attributes like power points,
 * level, inventory, and their position on the map. Tracks both current and previous positions.
 */
public class Player {

    /**
     * The player's current power points, which show their energy or strength in the game.
     */
    private int powerPoints;

    /**
     * The player's current level in the game.
     */
    private int level;

    /**
     * A list of items the player has collected during the game.
     */
    private final List<Item> inventory = new ArrayList<>();


    /**
     * The player's current location on the map, stored as (x, y) coordinates.
     */
    private int[] position = {4, 1}; // Default starting position.

    /**
     * The player's last position on the map, stored as (x, y) coordinates.
     * Useful for tracking where the player moved from.
     */
    private int[] previousPosition = null;

    /**
     * Creates a new player with a given amount of power points.
     * The player's level starts at 1, and their position is set to the default.
     *
     * @param powerPoints The starting energy or strength of the player.
     */
    public Player(int powerPoints) {
        this.powerPoints = powerPoints; // Set the player's starting power points.
        this.level = 1; // Start the player at level 1.
    }

    /**
     * Returns the player's current power points.
     *
     * @return The player's energy or strength points.
     */
    public int getPowerPoints() {
        return powerPoints; // Give back the current power points.
    }

    /**
     * Updates the player's power points to a new value.
     *
     * @param powerPoints The new amount of energy or strength for the player.
     */
    public void setPowerPoints(int powerPoints) {
        this.powerPoints = powerPoints; // Update the player's power points.
    }

    /**
     * Returns the player's current level in the game.
     *
     * @return The level the player is currently on.
     */
    public int getLevel() {
        return level; // Give back the player's current level.
    }

    /**
     * Updates the player's current level to a new value.
     *
     * @param level The new level for the player.
     */
    public void setLevel(int level) {
        this.level = level; // Change the player's level to the new value.
    }

    /**
     * Returns the player's current position on the game map.
     *
     * @return The player's position as an array {x, y}.
     */
    public int[] getPosition() {
        return position; // Give back the player's current position.
    }

    /**
     * Updates the player's position on the game map.
     * Also saves the player's current position as their previous position before changing it.
     *
     * @param x The new x-coordinate for the player.
     * @param y The new y-coordinate for the player.
     */
    public void setPosition(int x, int y) {
        if (position != null) {
            // Save the current position as the previous position.
            this.previousPosition = position.clone();
        }

        // Update the player's position to the new coordinates.
        this.position[0] = x;
        this.position[1] = y;

        // Debugging: Show the new position in the console for verification.
        System.out.println("Debug: New position set to (" + x + ", " + y + ")");
    }

    /**
     * Returns the player's previous position on the game map.
     *
     * @return The previous position as an array {x, y}, or {@code null} if no previous position is set.
     */
    public int[] getPreviousPosition() {
        return previousPosition; // Give back the player's previous position.
    }

    /**
     * Updates the player's previous position on the game map.
     * This method explicitly sets the player's previous position.
     *
     * @param previousPosition The player's previous position as an array {x, y}.
     */
    public void setPreviousPosition(int[] previousPosition) {
        // Set the player's previous position to the provided array.
        this.previousPosition = previousPosition;
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item The item to be added.
     */


    public void addItem(Item item) {
        // Put the given item into the player's inventory.
        inventory.add(item);
    }

    /**
     * Picks up an item from the specified room and adds it to the player's inventory.
     * If the room is empty, informs the player that there are no items to pick up.
     *
     * @param room The room where the player is trying to pick up an item.
     */
    public void pickItem(Room room) {
        // Try to take an item from the room.
        Item item = room.takeItem();
        if (item != null) {
            // Check if an item with the same name already exists in the inventory.
            boolean itemExists = inventory.stream()
                    .anyMatch(i -> i.getName().equalsIgnoreCase(item.getName()));
            if (itemExists) {
                System.out.println("You already have a " + item.getName() + " in your inventory.");
                // Return the item to the room since the player can't pick it up again.
                room.addItem(item);
            } else {
                // If the item is not already in the inventory, add it.
                inventory.add(item);
                System.out.println("Picked up: " + item.getName());
            }
        } else {
            // If no item is found, notify the player.
            System.out.println("Nothing to pick up.");
        }
    }

    /**
     * Shows the items currently in the player's inventory.
     * If the inventory is empty, it informs the player.
     */
    public void showInventory() {
        // Check if the player has no items in their inventory.
        if (inventory.isEmpty()) {
            // Let the player know their inventory is empty.
            System.out.println("Your inventory is empty.");
            return; // Stop here since there's nothing to display.
        }

        // Display a header for the inventory list.
        System.out.println("Your inventory:");

        // Go through each item in the inventory and show its name.
        for (Item item : inventory) {
            System.out.println("- " + item.getName()); // Print each item's name.
        }
    }

    /**
     * Checks if the player's inventory has a tool with a specific name.
     *
     * @param toolName The name of the tool to look for.
     * @return {@code true} if the tool is found, otherwise {@code false}.
     */
    public boolean hasTool(String toolName) {
        // Go through each item in the player's inventory.
        for (Item item : inventory) {
            // If the item's name matches the tool name (ignoring case), return true.
            if (item.getName().equalsIgnoreCase(toolName)) {
                return true; // Tool is found.
            }
        }
        // If no matching tool is found, return false.
        return false;
    }

    /**
     * Checks if the player's inventory has a spell with a specific name.
     *
     * @param spellName The name of the spell to look for.
     * @return {@code true} if the spell is found, otherwise {@code false}.
     */
    public boolean hasSpell(String spellName) {
        // Go through each item in the player's inventory.
        for (Item item : inventory) {
            // If the item's name matches the spell name (ignoring case), return true.
            if (item.getName().equalsIgnoreCase(spellName)) {
                return true; // Spell is found.
            }
        }
        // If no matching spell is found, return false.
        return false;
    }

    /**
     * Checks if the player's inventory contains an item of a specific type.
     *
     * @param itemType The class type of the item to look for.
     * @return {@code true} if an item of the specified type exists in the inventory, otherwise {@code false}.
     */
    public boolean hasItem(Class<?> itemType) {
        // Go through each item in the inventory.
        for (Item item : inventory) {
            // Check if the item matches the specified class type.
            if (itemType.isInstance(item)) {
                return true; // Found an item of the specified type.
            }
        }
        // No matching item found, return false.
        return false;
    }

    /**
     * Retrieves the first item of a specific type from the player's inventory.
     *
     * @param itemType The class type of the item to retrieve.
     * @return The first item of the specified type if found, or {@code null} if none exists.
     */
    public Item getItem(Class<?> itemType) {
        // Go through each item in the inventory.
        for (Item item : inventory) {
            // Check if the item matches the specified class type.
            if (itemType.isInstance(item)) {
                return item; // Return the first matching item.
            }
        }
        // No matching item found, return null.
        return null;
    }

    /**
     * Uses an item from the player's inventory by its name.
     * If the item is found, it is used and then removed from the inventory.
     * If not found, the player is notified.
     *
     * @param itemName The name of the item to use.
     */



    private Dungeon dungeon;

    public void setDungeon(Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    public Dungeon getDungeon() {
        return dungeon;
    }
    public void useItem(String itemName) {


        // Go through the player's inventory to search for the item.
        for (Item item : inventory) {
            // Check if the item's name matches the given name (ignoring case).
            if (item.getName().equalsIgnoreCase(itemName)) {
                // Special handling for Alarm Clock
                if (item.getName().equalsIgnoreCase("Alarm Clock")) {
                    System.out.println("Using tool: " + item.getName());
                    System.out.println("You are already awake. The Alarm Clock remains in your inventory.");
                    return; // Exit without removing the Alarm Clock
                }

                // Special handling for Spanner
                if (item.getName().equalsIgnoreCase("Spanner")) {
                    // Check if the player has a Box in their inventory
                    boolean hasBox = inventory.stream().anyMatch(i -> i instanceof Box);
                    if (hasBox) {
                        System.out.println("You use the Spanner to open the Box.");
                        Box box = (Box) inventory.stream().filter(i -> i instanceof Box).findFirst().get();
                        box.open(this, dungeon); // Open the Box
                        inventory.remove(box); // Remove the Box after opening
                        inventory.remove(item); // Remove the Spanner after use
                        System.out.println("The Spanner has been used and removed from your inventory.");
                    } else {
                        System.out.println("You can only use a Spanner to open a Box, and you don't have one.");
                    }
                    return; // Exit after handling the Spanner
                }

                // Handle the box
                if (item instanceof Box) {
                    // Check if the player has a Spanner in their inventory.
                    boolean hasSpanner = inventory.stream()
                            .anyMatch(i -> i.getName().equalsIgnoreCase("Spanner"));
                    if (hasSpanner) {
                        System.out.println("You need a Spanner to open this box. You have one in your inventory.");
                        System.out.println("Would you like to use the Spanner to open the box? (yes/no)");

                        // Ask the player for confirmation.
                        Scanner scanner = new Scanner(System.in);
                        String response = scanner.nextLine().toLowerCase();

                        if (response.equals("yes")) {
                            // Open the Box using its logic.
                            Box box = (Box) item;
                            String boxContent = box.getContent(); // Get the content of the box.
                            box.open(this, this.dungeon); // Invoke the `open` logic of the box.

                            // Add the contents of the box to the player's inventory unless it's a potion they drank.
                            if (!boxContent.equalsIgnoreCase("Sleeping Potion") && !boxContent.equalsIgnoreCase("X-Ray Potion")) {
                                inventory.add(new Item(boxContent) {
                                    @Override
                                    // Define what happens when the player uses the item
                                    public void use(Player player) {
                                        // Inform the player about the item they are using
                                        System.out.println("You used: " + boxContent);
                                    }
                                });
                                // End of the item definition block, now the item is added to the player's inventory
                                System.out.println(boxContent + " has been added to your inventory.");
                            }

                            // Remove the Box from the player's inventory.
                            inventory.remove(box);
                            //System.out.println("The Box has been opened and removed from your inventory.");

                            // Remove the Spanner from the inventory after use.
                            inventory.removeIf(i -> i.getName().equalsIgnoreCase("Spanner"));
                            System.out.println("The Spanner has been used and removed from your inventory.");
                        } else {
                            System.out.println("You chose not to open the Box.");
                        }
                    } else {
                        System.out.println("You need a Spanner to open this box, but you don't have one.");
                    }
                    return; // Exit after handling the Box
                }

                // Default behavior for other items
                item.use(this); // Use the item
                inventory.remove(item); // Remove the item from the inventory
                //System.out.println(itemName + " has been used and removed from your inventory.");
                return; // Exit after using the item
            }
        }

        // If the item wasn't found, let the player know.
        System.out.println("Item not found in your inventory.");
    }

    /**
     * Removes an item from the player's inventory.
     *
     * @param item The item to be removed.
     */
    public void removeItem(Item item) {
        if (inventory.remove(item)) {
            // check other items are still removed
        } else {
            System.out.println(item.getName() + " could not be found in your inventory.");
        }
    }





    public void removeItem(String itemName) {
        // Search for the item in the inventory by name.
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                inventory.remove(item);
                System.out.println(itemName + " has been removed from your inventory.");
                return;
            }
        }
        // If no matching item is found.
        System.out.println(itemName + " could not be found in your inventory.");
    }

    /**
     * Allows the player to drop an item from their inventory into a specified room.
     * If the inventory is empty, the player is informed, and no action is taken.
     *
     * @param room The room where the item will be dropped.
     */
    public void dropItem(Room room) {
        // Check if the player's inventory is empty.
        if (inventory.isEmpty()) {
            // Notify the player that there are no items to drop.
            System.out.println("No items to drop.");
            return; // Exit the method since there is nothing to drop.
        }

        // Ask the player which item they want to drop.
        System.out.println("Which item would you like to drop? (Type the name or type 'exit' to cancel)");

        // Show the player's inventory.
        System.out.println("Inventory:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName()); // List each item's name.
        }

        // Prompt the player for input.
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in); // Read input from the player.
        String itemName = scanner.nextLine(); // Store the player's input.

        // If the player types "exit", cancel the operation.
        if (itemName.equalsIgnoreCase("exit")) {
            System.out.println("Exiting item drop...");
            return; // End the method without dropping an item.
        }

        // Variable to store the item the player wants to drop.
        Item itemToDrop = null;

        // Search for the item in the player's inventory.
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToDrop = item; // Item found, store it.
                break; // Stop searching.
            }
        }

        // If the item is found, drop it into the room.
        if (itemToDrop != null) {
            inventory.remove(itemToDrop); // Remove the item from the inventory.
            room.addItem(itemToDrop); // Add the item to the room.
            System.out.println("Dropped: " + itemToDrop.getName()); // Notify the player.
        } else {
            // If no item matches the player's input, let them know.
            System.out.println("Item not found in your inventory.");
        }
    }

    /**
     * Reveals the map of the player's current room.
     * Displays a message and can be extended to show the room's layout or details.
     */
    public void revealRoomMap(Dungeon dungeon) {
        // Get the current level of the dungeon.
        int level = this.getLevel() - 1; // Convert level to zero-based index.

        // Inform the player about the map reveal.
        System.out.println("Now you can see the full map of this dungeon level:");
        System.out.println("Legend: 'W' = Wall, 'E' = Entrance, 'X' = Exit, 'T' = Treasure");

        // Display the entire map of the current level.
        char[][] levelMap = dungeon.getDungeons()[level]; // Retrieve the map for this level.

        for (int i = 0; i < levelMap.length; i++) {
            for (int j = 0; j < levelMap[i].length; j++) {
                System.out.print(levelMap[i][j] + " ");
            }
            System.out.println(); // Move to the next line after each row.
        }
    }
}



