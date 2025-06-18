package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This class represents food items    *//
//*              that players can consume to restore *//
//*              their power points in the game.     *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
// Define a `Food` class that extends the `Item` class, representing items the player can eat in the game.
public class Food extends Item {

    // Constructor for the `Food` class, setting the name of the food item.
    public Food(String name) {
        super(name); // Call the `Item` superclass constructor to initialize the item's name.
    }

    // Override the `use` method to specify what happens when the player eats this food.
    @Override
    public void use(Player player) {
        // Notify the player that they are eating the food.
        System.out.println("Eating: " + getName());

        // Variable to store how many power points the food restores.
        int restoredPoints = 0;

        // Check the name of the food to decide how many power points it restores.
        if (getName().equalsIgnoreCase("Cake")) {
            restoredPoints = 10; // Cake restores 10 power points.
        } else if (getName().equalsIgnoreCase("Sandwich")) {
            restoredPoints = 15; // Sandwich restores 15 power points.
        }

        // Call the setPowerPoints method from the instantiate player object to add the restored points to the player's current power points.
        // Ensure the total does not go above the maximum limit of 100.
        player.setPowerPoints(Math.min(player.getPowerPoints() + restoredPoints, 100));

        // Tell the player how many points were restored and their current total power points.
        System.out.println("Restored " + restoredPoints + " power points. Current Power Points: " + player.getPowerPoints());
    }
}


