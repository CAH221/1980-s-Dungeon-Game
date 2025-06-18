package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:11                                          *//
//*                                                  *//
//* Description: This class represents a special     *//
//*              "Box" item in the game. The box     *//
//*              contains random items such as an    *//
//*              Alarm Clock or Potion. Players      *//
//*              can interact with the box, but it   *//
//*              requires a Spanner to open. When    *//
//*              opened, the box reveals its content,*//
//*              and specific potions may prompt     *//
//*              players to drink them, triggering   *//
//*              effects like power loss or map      *//
//*              visibility.                         *//
//*                                                  *//
//* Date: 08/12/2024                                 *//
//****************************************************//

import java.util.Random;
import java.util.Scanner;

// The Box class represents a special item that can contain random objects like potions
public class Box extends Item {

    // A list of possible items that could be inside the box
    private final String[] contents = {"Sleeping Potion", "X-Ray Potion"};

    // The item currently inside the box (chosen randomly)
    private String content;

    // Constructor: Sets up the box with a name and something random inside it
    public Box() {
        super("Box"); // Call the Item class constructor and set the name to "Box"
        this.content = randomContent(); // Pick a random item to place in the box
    }

    // Randomly selects an item from the contents array
    private String randomContent() {
        Random random = new Random(); // Create a Random object
        return contents[random.nextInt(contents.length)]; // Pick and return a random item
    }

    // Lets other parts of the program see what is inside the box
    public String getContent() {
        return content;
    }

    // This is what happens if a player tries to use the box without opening it
    @Override
    public void use(Player player) {
        System.out.println("You need a Spanner to open this box."); // Message for the player
    }

    // Opens the box and interacts with the item inside
    public void open(Player player, Dungeon dungeon) {
        System.out.println("Opening the box...");

        // Check if the item inside is a potion
        if (getContent().equalsIgnoreCase("Sleeping Potion") || getContent().equalsIgnoreCase("X-Ray Potion")) {
            System.out.println("The box contains a mystery potion. It could be good or evil.");
            System.out.println("Would you like to drink the potion? (yes/no)");

            // Ask the player if they want to drink the potion
            Scanner scanner = new Scanner(System.in);
            String response = scanner.nextLine().toLowerCase(); // Get and process the player's input

            // If the player chooses to drink the potion
            if (response.equals("yes")) {
                if (getContent().equalsIgnoreCase("Sleeping Potion")) { // If the potion is a Sleeping Potion
                    // Check if the player has an Alarm Clock
                    boolean hasAlarmClock = player.hasTool("Alarm Clock");

                    if (hasAlarmClock) { // If the player has an Alarm Clock
                        System.out.println("The Sleeping Potion put you to sleep. Do you want to use the Alarm Clock to wake up? (yes/no)");
                        String alarmResponse = scanner.nextLine().toLowerCase();

                        if (alarmResponse.equals("yes")) { // If the player uses the Alarm Clock
                            System.out.println("The Alarm Clock rings loudly, waking you up! You are saved from losing 10 power points.");
                            player.removeItem("Alarm Clock"); // Remove the Alarm Clock from inventory
                        } else { // If the player does not use the Alarm Clock
                            System.out.println("Oh no! The Sleeping Potion put you to sleep. You chose not to use the Alarm Clock, so you lose 10 power points.");
                            player.setPowerPoints(Math.max(0, player.getPowerPoints() - 10)); // Deduct 10 power points (but not below 0)
                        }
                    } else { // If the player does not have an Alarm Clock
                        System.out.println("Oh no! The Sleeping Potion put you to sleep. You did not have an Alarm Clock, so you lose 10 power points.");
                        player.setPowerPoints(Math.max(0, player.getPowerPoints() - 10)); // Deduct 10 power points (but not below 0)
                    }
                } else if (getContent().equalsIgnoreCase("X-Ray Potion")) { // If the potion is an X-Ray Potion
                    System.out.println("You drank the X-Ray Potion! Now you can see the entire map of the current level.");
                    player.revealRoomMap(dungeon); // Show the entire dungeon map
                }
            } else { // If the player chooses not to drink the potion
                System.out.println("You chose not to drink the potion.");
            }
        } else { // If the item inside is not a potion
            System.out.println("The box contains: " + getContent());
            // Add the item to the player's inventory
            player.addItem(new Item(getContent()) {
                @Override
                public void use(Player player) {
                    System.out.println("You used: " + getContent()); // Print a message when the item is used
                }
            });
        }

        // Remove the box from the player's inventory after it has been opened
        player.removeItem(this);
        System.out.println("The Box has been opened and removed from your inventory.");
    }
}


