package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class represents a spell item  *//
//*              in the dungeon game. It extends the *//
//*              Item class and provides specific    *//
//*              behavior for using spells, such as  *//
//*              teleportation or freezing           *//
//*              surroundings.                       *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
/**
 * Represents a spell that a player can use in the game.
 * Extends the {@code Item} class and provides specific functionality for spell effects.
 */
public class Spell extends Item {

    /**
     * Instatiate a dungeon object. The dungeon in which the spell is used provides context for spell effects.
     */
    private Dungeon dungeon;

    /**
     * Creates a new {@code Spell} with the specified name and associated dungeon.
     *
     * @param name    The name of the spell.
     * @param dungeon The dungeon where the spell can be used.
     */
    public Spell(String name, Dungeon dungeon) {
        super(name); // Set the name of the spell using the parent 'Item' constructor.
        this.dungeon = dungeon; // Assign the dungeon to this spell.
    }

    /**
     * Activates the spell's effect when used by the player.
     * Effects are determined based on the spell's name.
     *
     * @param player The player using the spell.
     */
    @Override
    public void use(Player player) {
        System.out.println("Casting spell: " + getName());

        // Determine the effect of the spell based on its name.
        if (getName().equalsIgnoreCase("Teleportation Spell")) {
            System.out.println("You are teleported to a random location!");
            dungeon.teleportPlayer(player); // Teleport the player using the dungeon's method.
        } else if (getName().equalsIgnoreCase("Freeze Spell")) {
            System.out.println("You freeze your surroundings! All traps and enemies in adjacent rooms are neutralized.");
            freezeSurroundings(player); // Freeze the rooms around the player.
        } else {
            System.out.println("This spell has no defined effect.");
        }
    }

    /**
     * Freezes the surroundings of the player by neutralizing traps and enemies
     * in adjacent rooms of the dungeon.
     *
     * @param player The player using the freeze spell.
     */
    private void freezeSurroundings(Player player) {
        int level = player.getLevel() - 1; // Convert level to zero-based indexing.
        int[] position = player.getPosition(); // Get player's current position [x, y].
        int x = position[0];
        int y = position[1];

        System.out.println("Freezing surroundings...");

        // Check and freeze adjacent rooms, ensuring the position is within bounds.
        if (x > 0) freezeRoom(level, x - 1, y); // Freeze the room above the player.
        if (x < dungeon.getSize() - 1) freezeRoom(level, x + 1, y); // Freeze the room below the player.
        if (y > 0) freezeRoom(level, x, y - 1); // Freeze the room to the left.
        //if (y < dungeon.getSize() - 1) freezeRoom(level, x, y + 1); // Freeze the room to the right.
        if (y > 0) freezeRoom(level, x, y + 1); // Freeze the room to the right.
    }

    /**
     * Freezes a specific room in the dungeon if it contains enemies or traps.
     *
     * @param level The level of the dungeon where the room is located.
     * @param x     The x-coordinate of the room.
     * @param y     The y-coordinate of the room.
     */
    private void freezeRoom(int level, int x, int y) {
        // Get the room at the specified level and position.
        Room room = dungeon.getRoom(level + 1, new int[]{x, y});

        // Apply freezing if the room has enemies or traps and isn't already frozen.
        if (room != null && room.hasEnemiesOrTraps() && !room.isFrozen()) {
            room.setFrozen(true); // Mark the room as frozen.
            System.out.println("Room at (" + x + ", " + y + ") is now frozen.");
        } else if (room != null && room.isFrozen()) {
            System.out.println("Room at (" + x + ", " + y + ") is already frozen.");
        } else {
            System.out.println("Room at (" + x + ", " + y + ") is safe or empty.");
        }
    }
}





