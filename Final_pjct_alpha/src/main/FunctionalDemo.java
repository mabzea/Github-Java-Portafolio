package main;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

/**
 * Demonstrates the five core functional interfaces for Project 6.
 * Built around a Text-Based Horror Adventure inventory system.
 *
 * @author Angel Barajas
 * @version 1.0
 */
public class FunctionalDemo {

    /**
     * SUPPLIER: Takes NO arguments, returns a result.
     * Generates a new mystery item for the game.
     * @return A Supplier that provides an Item.
     */
    public static Supplier<Item> generateMysteryItem() {
        return () -> new Item("Cursed Amulet", 0.5);
    }

    /**
     * CONSUMER: Takes an argument, returns NOTHING (void).
     * Inspects an item by printing its eerie details to the console.
     * @return A Consumer that accepts an Item.
     */
    public static Consumer<Item> inspectItem() {
        return item -> System.out.println("You examine the " + item.getName() + " closely. It feels unnaturally cold.");
    }

    /**
     * PREDICATE: Takes an argument, returns a BOOLEAN (true/false).
     * Checks if an item is considered too heavy to run with.
     * @return A Predicate that evaluates if the item weight is 3.0 or greater.
     */
    public static Predicate<Item> isHeavy() {
        return item -> item.getWeight() >= 3.0;
    }

    /**
     * FUNCTION: Takes an argument of one type, returns a DIFFERENT type.
     * Extracts the name of the item and converts it to uppercase for the UI.
     * @return A Function that takes an Item and returns a String.
     */
    public static Function<Item, String> extractNameUpper() {
        return item -> item.getName().toUpperCase();
    }

    /**
     * UNARY OPERATOR: Takes an argument, returns the SAME type.
     * Applies a magical curse to an item's weight value, doubling it.
     * @return A UnaryOperator that takes a Double and returns a Double.
     */
    public static UnaryOperator<Double> curseWeight() {
        return weight -> weight * 2.0;
    }

    /**
     * The main entry point to execute the functional interface demonstrations.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("=== HORROR GAME: FUNCTIONAL INTERFACES DEMO ===\n");

        // --- 1. Supplier Execution ---
        Item randomItem = generateMysteryItem().get();
        System.out.println("1. SUPPLIER: Generated Item -> " + randomItem.getName());

        // --- 2. Consumer Execution ---
        System.out.print("2. CONSUMER: ");
        inspectItem().accept(randomItem);

        // Setup Inventory list to test Streams
        List<Item> inventory = new ArrayList<>();
        inventory.add(new Item("Flashlight", 2.5));
        inventory.add(new Item("Basement Key", 0.1));
        inventory.add(new Item("Heavy Crowbar", 5.0));
        inventory.add(randomItem); // The Cursed Amulet (0.5 lbs)

        System.out.println("\n--- Processing Player Inventory ---");

        // --- 3. Predicate Execution (Filtering Streams) ---
        List<Item> heavyItems = inventory.stream()
            .filter(isHeavy())
            .collect(Collectors.toList());
        System.out.println("3. PREDICATE (Items >= 3.0lbs): " + heavyItems);

        // --- 4. Function Execution (Mapping Streams) ---
        List<String> itemNames = inventory.stream()
            .map(extractNameUpper())
            .collect(Collectors.toList());
        System.out.println("4. FUNCTION (Extracted Names): " + itemNames);

        // --- 5. UnaryOperator Execution ---
        double originalWeight = randomItem.getWeight();
        double cursedWeight = curseWeight().apply(originalWeight);
        System.out.println("5. UNARY OPERATOR: " + randomItem.getName() + " weight cursed from " + originalWeight + " to " + cursedWeight);
    }
}