package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a specific location inside Blackridge Penitentiary.
 */
public class Room {
    private String name;
    private String description;
    private Item roomItem; 
    
    // 1. Changed from Map<String, Room> to Map<Direction, Room>
    private Map<Direction, Room> exits; 

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
    }

    // 2.parameter to require a Direction enum instead of a String
    public void setExit(Direction direction, Room neighbor) {
        exits.put(direction, neighbor); // No more .toLowerCase() needed!
    }

    // 3. parameter to require a Direction enum
    public Room getExit(Direction direction) {
        return exits.get(direction);
    }

    public void setItem(Item item) {
        this.roomItem = item;
    }

    public Item getItem() {
        return roomItem;
    }

    public void removeItem() {
        this.roomItem = null;
    }

    public void printLocationInfo() {
        System.out.println("\n---------------------------------------------------------");
        System.out.println("LOCATION: " + name.toUpperCase());
        System.out.println("---------------------------------------------------------");
        System.out.println(description);
        if (roomItem != null) {
            System.out.println("\nYou see something resting in the shadows: " + roomItem.getName());
        }
        System.out.print("\nExits: ");
        
        // 4. loop to iterate over Direction objects instead of Strings
        for (Direction direction : exits.keySet()) {
            System.out.print("[" + direction + "] ");
        }
        System.out.println();
    }
    
    public String getName() {
        return name;
    }
}