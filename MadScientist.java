package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class represents a "Mad        *//
//*              Scientist" character in the game.   *//
//*              When interacted with, the scientist *//
//*              randomly rambles one of several     *//
//*              predefined phrases. The character   *//
//*              adds an element of unpredictability *//
//*              and immersion to the dungeon        *//
//*              environment.                        *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a Mad Scientist character in the dungeon.
 * The scientist speaks random phrases when you encounter them.
 */
public class MadScientist {

    // A list of random things the Mad Scientist might say during interaction
    private static final String[] RAMBLES = {
            "Eureka! The quantum flux capacitor is ready!",
            "Do you believe in time travel? I have seen the future!",
            "You dare enter my domain? Marvel at my creations!",
            "The multiverse is collapsing! We must act now!",
            "Chaos is the essence of discovery, young one!"
    };

    /**
     * Interacts with the Mad Scientist.
     * If the player has a Freeze Spell, they can stop the attack.
     * If not, the player will lose 50 power points from the attack.
     *
     * @param player The player interacting with the Mad Scientist.
     */
    public void interact(Player player) {
        // The Mad Scientist says something random from the RAMBLES list
        Random random = new Random(); // Create a Random object
        String ramble = RAMBLES[random.nextInt(RAMBLES.length)]; // Pick a random statement
        System.out.println("Mad Scientist says: \"" + ramble + "\"");

        // Check if the player has a Freeze Spell
        if (player.hasSpell("Freeze Spell")) { // If the player has the spell
            System.out.println("You have a Freeze Spell. Would you like to use it to stop the Mad Scientist? (yes/no)");

            Scanner scanner = new Scanner(System.in); // Create a scanner for user input
            String response = scanner.nextLine().toLowerCase(); // Read and process the response

            if (response.equals("yes")) { // If the player chooses to use the spell
                System.out.println("You use the Freeze Spell. The Mad Scientist is frozen and cannot harm you.");
                player.removeItem("Freeze Spell"); // Remove the spell from the player's inventory
            } else { // If the player chooses not to use the spell
                System.out.println("You chose not to use the Freeze Spell. The Mad Scientist attacks!");
                player.setPowerPoints(Math.max(0, player.getPowerPoints() - 50)); // Reduce power points by 50, ensuring they don't drop below 0
                System.out.println("You lost 50 power points. Your remaining power points: " + player.getPowerPoints());
            }
        } else { // If the player does not have a Freeze Spell
            System.out.println("No Freeze Spell! The Mad Scientist attacks you, and you lose 50 power points.");
            player.setPowerPoints(Math.max(0, player.getPowerPoints() - 50)); // Reduce power points by 50, ensuring they don't drop below 0
            System.out.println("Your remaining power points: " + player.getPowerPoints());
        }
    }
}


