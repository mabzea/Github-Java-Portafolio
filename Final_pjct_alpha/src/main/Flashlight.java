package main;

public class Flashlight extends Item {
    private int batteryLevel = 100;

    public Flashlight() {
        // Changed the second parameter to a double (weight) to match Item.java
        super("Flashlight", 2.5); 
    }

    // Removed @Override because Item.java doesn't have a use() method right now
    public void use() { 
        System.out.println("The beam flickers to life, cutting through the shadows.");
        batteryLevel -= 10;
    }
}