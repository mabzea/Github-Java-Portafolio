package main;

import java.util.Comparator;

/**
 * A custom comparator used to sort Item objects by their weight.
 * * NOTE ON COMPARABLE VS COMPARATOR:
 * - Comparator is created OUTSIDE the target class. It allows you to create multiple ways to sort the same object.
 * - It requires overriding the compare() method.
 * - It is useful when you want to sort by something other than the default natural order (e.g., sorting inventory by weight instead of alphabetically).
 * * @author Angel Barajas
 * @version 1.0
 */
public class ItemWeightComparator implements Comparator<Item> {

    /**
     * Compares two items based on their weight.
     * @param item1 The first item.
     * @param item2 The second item.
     * @return Negative if item1 is lighter, positive if heavier, 0 if equal.
     */
    @Override
    public int compare(Item item1, Item item2) {
        return Double.compare(item1.getWeight(), item2.getWeight());
    }
}