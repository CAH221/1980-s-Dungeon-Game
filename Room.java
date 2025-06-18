package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class represents a room in the *//
//*              dungeon game. It manages the room's *//
//*              description, contents, and allows   *//
//*              interaction such as adding or       *//
//*              taking items. The room can also be  *//
//*              checked for special features like   *//
//*              containing treasure.                *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
import java.util.ArrayList;
import java.util.List;
/**
 * Represents a room in the game.
 * A room can hold items, enemies, or traps and may be frozen to disable traps or enemies.
 */

public class Room {
    private String description; // A short description of the room (e.g., "Treasure Room" or "Hallway").
    private boolean frozen; // True if the room is frozen, disabling traps or enemies.
    private boolean hasEnemiesOrTraps; // True if the room has enemies or traps.
    private ArrayList<Item> items = new ArrayList<>(); // A list of items currently in the room.
    private MadScientist madScientist; // A reference to a Mad Scientist character in the room, if any.

    /**
     * Creates a new Room with a given description.
     * By default, the room is not frozen and has no enemies or traps.
     *
     * @param description A short description of the room (e.g., "Treasure Room").
     */
    public Room(String description) {
        this.description = description; // Set the room's description.
        this.frozen = false; // The room starts unfrozen.
        this.hasEnemiesOrTraps = false; // The room starts without enemies or traps.
    }

    private boolean interacted; // True if the room interaction has been completed.

    // Updates the interaction status of the object or room
    public void setInteracted(boolean interacted) {
        this.interacted = interacted; // Set the interaction status
    }


    // Checks if the player has interacted with the object or room
    public boolean hasInteracted() {
        return interacted; // Return the current interaction status
    }

    // Checks if the room is empty
    public boolean isEmpty() {
        // A room is empty if it has no items, no enemies or traps, and no Mad Scientist
        return !hasItems() && !hasEnemiesOrTraps() && !hasMadScientist();
    }

    // --- Getters and Setters ---
// Retrieves the description of the room
    public String getDescription() {
        return description; // Return the description of the room
    }


    public List<Item> getItems() {
        return new ArrayList<>(items); // Return a copy of the items list to protect the original.
    }

    public void addItem(Item item) {
        items.add(item); // Add an item to the room's item list.
    }

    public boolean isFrozen() {
        return frozen; // Check if the room is frozen.
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen; // Set the room's frozen state.
    }

    public boolean hasEnemiesOrTraps() {
        return hasEnemiesOrTraps; // Check if the room has enemies or traps.
    }

    public void setHasEnemiesOrTraps(boolean hasEnemiesOrTraps) {
        this.hasEnemiesOrTraps = hasEnemiesOrTraps; // Set whether the room has enemies or traps.
    }

    public Item takeItem() {
        return items.isEmpty() ? null : items.remove(0); // Remove and return the first item in the room, or null if empty.
    }

    public boolean hasItems() {
        return !items.isEmpty(); // Check if the room has any items.
    }

    /**
     * Describes the contents of the room by listing the items present.
     * If there are no items, it returns "No items."
     *
     * @return A string listing the names of the items in the room, or "No items" if empty.
     */
    public String describeContents() {
        // Check if the room has no items.
        if (items.isEmpty()) {
            return "No items."; // Return a message if the room is empty.
        }

        // Create a string to hold the list of item names.
        StringBuilder contents = new StringBuilder();
        for (Item item : items) {
            // Add each item's name to the string, followed by a comma.
            contents.append(item.getName()).append(", ");
        }

        // Remove the trailing comma and space, then return the result.
        return contents.substring(0, contents.length() - 2);
    }

    public boolean hasMadScientist() {
        return madScientist != null; // Check if there is a Mad Scientist in the room.
    }

    public void setMadScientist(MadScientist madScientist) {
        this.madScientist = madScientist; // Set the Mad Scientist for the room.
    }

    public MadScientist getMadScientist() {
        return madScientist; // Get the Mad Scientist in the room, if any.
    }

    @Override
    public String toString() {
        // Return the room's description with "(Frozen)" added if the room is frozen.
        return description + (frozen ? " (Frozen)" : "");
    }

    public boolean isTreasureRoom() {
        return description.equalsIgnoreCase("Treasure"); // Check if the room is a "Treasure" room.
    }

    /**
     * Allows the player to interact with the room's features.
     * Handles encounters with a Mad Scientist, enemies, traps, or items.
     *
     * @param player The player interacting with the room.
     */
    public void interact(Player player) {
        // Check if the room has a Mad Scientist.
        if (hasMadScientist()) {
            System.out.println("You encounter a Mad Scientist!");
            madScientist.interact(player); // Pass the Player object to the Mad Scientist's interaction method.
        }

        // Check if the room has enemies or traps.
        if (hasEnemiesOrTraps()) {
            System.out.println("There are enemies or traps here!");
        }

        // Check if the room contains items.
        if (hasItems()) {
            System.out.println("This room contains items: " + describeContents());
        }
    }
}


