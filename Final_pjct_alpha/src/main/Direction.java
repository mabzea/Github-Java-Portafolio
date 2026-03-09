package main;

/**
 * Represents the valid movement directions in Blackridge Penitentiary.
 * Satisfies Chapter 1 Checklist: Use of enumerations (enums).
 */
public enum Direction {
    NORTH, 
    SOUTH, 
    EAST, 
    WEST, 
    UP, 
    DOWN;

    /**
     * Safely converts a typed string into a valid Direction enum.
     * @param dir The string to convert.
     * @return The matching Direction, or null if invalid.
     */
    public static Direction fromString(String dir) {
        try {
            return Direction.valueOf(dir.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // The player typed a word that isn't a direction
        }
    }
}