The Dungeon of Doom is a single-player, text-based fantasy adventure game developed in Java and inspired by the classic tabletop game Dungeons and Dragons. Designed to run on a PC, it offers players a dynamic dungeon-exploration experience with three levels of unique challenges. Each level is represented by a 5x5 grid maze filled with walls, traps, treasures, and other interactive elements, ensuring engaging and strategic gameplay.
At the start of the game, players are introduced to the dungeon world with a brief overview and their initial position marked on the map. The rest of the grid remains hidden, encouraging discovery and exploration through commands. As players progress, they unveil the dungeon's secrets step by step, encountering various challenges and making strategic decisions.

Core Gameplay

Dungeon Layout: The game consists of three dungeon levels, each a 5x5 grid. Players navigate through walls and overcome traps and enemies strategically placed to challenge their problem-solving skills.
Player Movement: Using text-based commands such as "forward", “back”, "left," or “right” players explore the dungeon. Movements are confined to grid boundaries, with walls acting as obstacles.
Room Interactions: Each room may contain items (positive, negative, or neutral), enemies like the Mad Scientist, or traps. Room descriptions guide the player's actions and decisions.
Game Objective: Players start with 100 power points and must traverse all three levels to find the treasure and win. Power points decrease upon encountering enemies or traps and increase by consuming food. A player loses if power points reach zero.

Key Features

Randomized Content: Items, traps, and enemies are randomly distributed across the dungeon, offering a unique experience for every playthrough.
Player Commands: Players can perform actions such as:
Move: Navigate through the map.
Look Around: Reveal nearby areas of the dungeon.
Pick Up: Collect items from rooms.
Drop: Leave behind unwanted items.
Exit: End the game at any point.

Inventory Management: Players can carry multiple items at a time, such as tools (e.g., Hammer, Spanner) and spells (e.g., Teleportation, Freeze). Strategic management is required to progress through the game. Once an item is used it is removed from their inventory. Some items can only be open if the player has a certain different item in their inventory, for example a box can only be opened with a spanner.
Power Point System: Positive items like food increase power points, while traps and enemies decrease them. Neutral items like potions have varying effects.
Challenges and Solutions: Obstacles such as walls cannot be moved through and the player must find a different route. Traps and enemies require specific tools or spells to overcome, emphasizing strategic decision-making.
Game Design Highlights

Object-Oriented Programming Principles: The game leverages Java's OOP features to organize its structure. Core classes include Player, Dungeon, Room, Item, and its subclasses (Tool, Spell, Food, Box). This modular approach ensures scalability, flexibility, and maintainability. Using this approach, the developer can easily add in extras tools, spells and food.
Immersive Experience: Players are drawn into the game through interactive text commands and real-time responses, such as you have find a potion do you want to drink it, or you have a spanner do you want to use it to open the box, creating a sense of adventure and discovery.
Scalability: The robust design allows for future expansions, such as additional levels, new items, or enhanced enemy interactions.
