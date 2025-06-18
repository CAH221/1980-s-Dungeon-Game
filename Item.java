package dungeongame;
//****************************************************//
//* Author:1717859                                   *//
//* Week:9                                           *//
//*                                                  *//
//* Description: This abstract class represents an   *//
//*              item in the game that can be used   *//
//*              by the player. It defines a name    *//
//*              for the item and requires           *//
//*              implementation of a use() method    *//
//*              to specify how the item affects     *//
//*              the player.                         *//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
// Define an abstract class `Item` that acts as a template for all game items.
public abstract class Item {

    // A private, unchangeable field to store the item's name.
    private final String name;

    // Constructor to set the item's name when it is created.
    public Item(String name) {
        this.name = name; // Store the name in the `name` field.
    }

    // A method to get the name of the item.
    public String getName() {
        return name; // Return the name of the item.
    }

    // An abstract method that must be defined in subclasses to specify how the item is used.
    public abstract void use(Player player);
}



