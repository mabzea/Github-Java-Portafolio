package main;

public class Flashlight extends Item {
    private int batteryLevel;

    public Flashlight() {
        super("Flashlight", 2.5);
        // READ from the database when the item is created
        this.batteryLevel = DatabaseManager.getBatteryLevel();
    }


    public void use() {
        if (batteryLevel > 0) {
            System.out.println("The beam flickers to life. (Battery: " + batteryLevel + "%)");
            batteryLevel -= 10;
            
            
            DatabaseManager.updateBatteryLevel(batteryLevel);
        } else {
            System.out.println("You click the switch, but the flashlight is dead.");
        }
    }
}