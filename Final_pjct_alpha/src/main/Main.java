package main;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize DB first
        DatabaseManager.initializeDatabase();

        // 2. Test the item
Flashlight myLight = new Flashlight();
        
        // Line 12 will now work perfectly:
        myLight.use();
    }
}