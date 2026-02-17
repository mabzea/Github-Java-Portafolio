package main;

public class Flashlight extends Item {
    private int batteryLevel = 100;

    public Flashlight() {
        super("Flashlight", "A heavy metal flashlight. The lens is cracked.");
    }

    @Override
    public void use() {
        System.out.println("The beam flickers to life, cutting through the shadows.");
        batteryLevel -= 10;
    }
}