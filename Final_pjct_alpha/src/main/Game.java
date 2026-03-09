package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main engine for the Blackridge Penitentiary escape game.
 */
public class Game {
    private Room currentRoom;
    private List<Item> inventory;
    private boolean isPlaying;
    private GameLog logger; 

    public Game() {
        inventory = new ArrayList<>();
        isPlaying = true;
        logger = GameLog.getInstance();
        
        // Initialize the database to track the flashlight battery
        DatabaseManager.initializeDatabase();
        logger.addEntry("Database initialized. Game engine starting.");
        
        setupPrison();
    }

    private void setupPrison() {
        // 1. Create the locations
        Room cellBlock = new Room("Cell Block D", "You wake up in a pitch-black cell. The heavy iron door is mysteriously open. A wet, dragging sound echoes from below.");
        Room guardStation = new Room("Guard Station", "A shattered glass booth. The main gate controls are here, but the power box is jammed shut.");
        Room subBasement = new Room("Sub-Basement", "Ankle-deep, freezing water. The dragging sound is deafening down here. It smells like rust and copper.");
        Room mainGate = new Room("Main Gate", "A massive, iron-wrought door leading out into the foggy night. There is a heavy manual override lock.");

        // 2. Connect the locations using the new Enum
        cellBlock.setExit(Direction.NORTH, guardStation);
        guardStation.setExit(Direction.SOUTH, cellBlock);
        guardStation.setExit(Direction.DOWN, subBasement);
        subBasement.setExit(Direction.UP, guardStation);
        guardStation.setExit(Direction.EAST, mainGate);
        mainGate.setExit(Direction.WEST, guardStation);

        // 3. Place items
        cellBlock.setItem(new Flashlight()); 
        guardStation.setItem(new Item("Heavy Crowbar", 5.0));
        subBasement.setItem(new Item("Basement Key", 0.5));
        mainGate.setItem(new CassettePlayer()); // Placed here so it doesn't overwrite the flashlight!

        currentRoom = cellBlock;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=========================================================");
        System.out.println("          ESCAPE FROM BLACKRIDGE PENITENTIARY            ");
        System.out.println("=========================================================");
        System.out.println("Commands: 'go [direction]', 'take', 'use [item]', 'inventory', 'quit'");

        currentRoom.printLocationInfo();

        while (isPlaying) {
            System.out.print("\nWhat will you do? > ");
            String input = scanner.nextLine().trim().toLowerCase();
            logger.addEntry("Player typed: " + input); 

            if (input.equals("quit")) {
                System.out.println("You curl up in the dark and give up. Game over.");
                isPlaying = false;
            } 
            else if (input.startsWith("go ")) {
                movePlayer(input.substring(3).trim());
            } 
            else if (input.equals("take")) {
                takeItem();
            } 
            else if (input.startsWith("use ")) {
                useItem(input.substring(4).trim());
            } 
            else if (input.equals("inventory")) {
                showInventory();
            }
            else {
                System.out.println("I don't understand that command.");
            }
        }
        scanner.close();
    }

    private void movePlayer(String directionString) {
        // Convert the string to our new Enum
        Direction dir = Direction.fromString(directionString);
        
        if (dir == null) {
            System.out.println("That is not a valid direction.");
            return;
        }

        Room nextRoom = currentRoom.getExit(dir);
        if (nextRoom == null) {
            System.out.println("You can't go that way! The path is blocked.");
        } else {
            currentRoom = nextRoom;
            currentRoom.printLocationInfo();
        }
    }

    private void takeItem() {
        Item item = currentRoom.getItem();
        if (item != null) {
            inventory.add(item);
            System.out.println("You picked up the " + item.getName() + ".");
            currentRoom.removeItem();
        } else {
            System.out.println("There is nothing here to take.");
        }
    }
    
    private void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Your pockets are empty.");
        } else {
            System.out.println("You are carrying:");
            for (Item item : inventory) {
                System.out.println("- " + item.getName());
            }
        }
    }

    private void useItem(String itemName) {
        // Find the item in the inventory
        Item itemToUse = null;
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToUse = item;
                break;
            }
        }

        if (itemToUse == null) {
            System.out.println("You don't have a " + itemName + ".");
            return;
        }

        // --- SPECIFIC ITEM LOGIC ---
        if (itemToUse instanceof Flashlight) {
            ((Flashlight) itemToUse).use(); 
        } 
        else if (itemToUse instanceof CassettePlayer) {
            ((CassettePlayer) itemToUse).use(); // Cast just like we did for the flashlight
        }
        else if (itemName.equalsIgnoreCase("heavy crowbar")) {
            System.out.println("You swing the crowbar. CLANG! The noise echoes terribly. You shouldn't make so much noise...");
        } 
        else if (itemName.equalsIgnoreCase("basement key")) {
            if (currentRoom.getName().equals("Main Gate")) {
                System.out.println("\n*** You insert the rusty Basement Key into the manual override. ***");
                System.out.println("*** The massive iron doors groan open, revealing the foggy night. ***");
                System.out.println("*** YOU HAVE ESCAPED BLACKRIDGE PENITENTIARY! ***\n");
                logger.addEntry("Player successfully escaped the prison.");
                isPlaying = false; 
            } else {
                System.out.println("There is no lock here to use this key on.");
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}