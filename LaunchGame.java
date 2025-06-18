package dungeongame;
//****************************************************//
//* Author: 1717859                                  *//
//* Week: 9                                          *//
//*                                                  *//
//* Description: This class is the entry point for   *//
//*              the Dungeon Game. It initialises    *//
//*              the dungeon, player, and game       *//
//*              controller, then starts the game    *//
//*              loop where the player navigates     *//
//*              through levels to find the treasure.*//
//*                                                  *//
//* Date: 24/11/2024                                 *//
//****************************************************//
import dungeongame.Dungeon;
import dungeongame.Player;
import dungeongame.GameController;
import java.util.Scanner;

public class LaunchGame {

    // This is the main controller for the game. It manages the dungeon and player.
    private final GameController gameController;

    // The main method is where the program starts.
    public static void main(String[] args) {
        // Create an instance of the LaunchGame class to set up the game.
        LaunchGame launchGame = new LaunchGame();
        // Start the game.
        launchGame.start();
    }

    // This constructor sets up the dungeon and the player for the game.
    public LaunchGame() {


        // Create a dungeon with 3 levels and a 5x5 grid size.
        // this instantiates a dungeon object of the dungeon class
        Dungeon dungeon = new Dungeon(3, 5);
        // Set up the dungeon, adding rooms and contents.
        // this calls the initialize method from the dungeon class using the newly instiated ojbect
        dungeon.initialize();

        // Create a player who starts with 100 power points.
        // this instantiates a player object of the player class
        Player player = new Player(100);
        //note this calls the method setDungeon from the player class which is linked to the dungeon class
        player.setDungeon(dungeon); // Link the dungeon to the player.

        // Link the dungeon and player to the game controller class.
        this.gameController = new GameController(dungeon, player);
    }

    // This method starts the game and interacts with the player.
    public void start() {
        // Print the title of the game and an introduction message.
        System.out.println("\n\n\t\t\t\t\tThe Dungeon Game!");
        System.out.println("\n\t\t   You've entered a perilous labyrinth filled with challenges and rewards. "
                + "\n\tYour goal is to navigate through three treacherous levels, overcome obstacles, and claim the ultimate prize:"
                + "\n\t\t\t\t\tTHE LEGENDARY TREASURE!");

        // Ask the player if they are ready to begin.
        System.out.println("\nAre you ready to start the adventure? (yes/no)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().toLowerCase(); // Convert the input to lowercase for consistency using Java's toLowerCase method.

        // If the player types "yes", start the game loop.
        if (input.equals("yes")) {
            gameController.start();
        } else {
            // If the player types anything else, end the game with a goodbye message.
            System.out.println("Goodbye! Come back when you're ready for the adventure.");
        }
    }
}
