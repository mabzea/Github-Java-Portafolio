package main;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Demonstrates sorting items using Sets and Maps for the game's inventory system.
 * * @author Miguel Angel Barajas
 * @version 1.0
 */
public class InventoryDemo {

    /**
     * The main entry point to run the sorting demonstration.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("=== HORROR GAME INVENTORY SORTING DEMO ===\n");

        Item flashlight = new Item("Flashlight", 1.5);
        Item key = new Item("Basement Key", 0.1);
        Item crowbar = new Item("Heavy Crowbar", 5.0);
        Item map = new Item("Mansion Map", 0.05);

        // --- 1. SET SORTING DEMONSTRATION ---
        System.out.println("--- TreeSet Sorting (Using Comparator for Weight) ---");
        /* * Note: A TreeSet automatically sorts elements when they are added.
         * We pass our custom ItemWeightComparator to the TreeSet constructor 
         * so it sorts the items from lightest to heaviest, rather than alphabetically.
         */
        Set<Item> weightSortedInventory = new TreeSet<>(new ItemWeightComparator());
        weightSortedInventory.add(flashlight);
        weightSortedInventory.add(key);
        weightSortedInventory.add(crowbar);
        weightSortedInventory.add(map);

        for (Item item : weightSortedInventory) {
            System.out.println(item);
        }
        System.out.println();

        // --- 2. MAP SORTING DEMONSTRATION ---
        System.out.println("--- TreeMap Sorting (Using Comparable for Alphabetical Keys) ---");
        /*
         * Note: A TreeMap automatically sorts its entries based on the KEY.
         * Since we use a String as the key (the item's name), it uses String's 
         * built-in Comparable implementation to sort the map alphabetically.
         */
        Map<String, Item> alphabeticalInventoryMap = new TreeMap<>();
        alphabeticalInventoryMap.put(flashlight.getName(), flashlight);
        alphabeticalInventoryMap.put(key.getName(), key);
        alphabeticalInventoryMap.put(crowbar.getName(), crowbar);
        alphabeticalInventoryMap.put(map.getName(), map);

        for (Map.Entry<String, Item> entry : alphabeticalInventoryMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " | Value: " + entry.getValue());
        }
    }
}