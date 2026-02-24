package main;

/**
 * Represents an item in the text-based adventure.
 * Implements Comparable to provide a default alphabetical sorting order by name.
 * * NOTE ON COMPARABLE VS COMPARATOR:
 * - Comparable is implemented IN the class itself. It defines the "natural" or default sorting order (e.g., alphabetical by name).
 * - It requires overriding the compareTo() method.
 * - You can only have ONE Comparable implementation per class.
 * * @author Angel Barajas
 * @version 1.0
 */
public class Item implements Comparable<Item> {
    private String name;
    private double weight;

    /**
     * Constructor for an Item.
     * @param name The name of the item (e.g., "Rusty Key", "Flashlight").
     * @param weight The weight of the item in pounds.
     */
    public Item(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    /**
     * Compares this item to another item to determine sorting order.
     * @param otherItem The other item to compare against.
     * @return A negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(Item otherItem) {
        // Natural ordering: Sort alphabetically by name
        return this.name.compareToIgnoreCase(otherItem.getName());
    }

    @Override
    public String toString() {
        return name + " (" + weight + " lbs)";
    }
}