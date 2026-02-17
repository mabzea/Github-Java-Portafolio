package main;

public class Main {
	
	public static void main(String[] args) {
        // Test the Singleton
        GameLog logger = GameLog.getInstance();
        logger.addEntry("Game started.");

        // Test the Interface and Inheritance
        Interactable myLight = new Flashlight();
        System.out.println("Item: " + myLight.getName());
        myLight.use(); 
        
        logger.addEntry("Player used flashlight.");
    }

}
