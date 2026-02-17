package main;

public class GameLog {
    // The single instance of the class
    private static GameLog instance;
    private String logData = "";

    // Private constructor prevents other classes from using "new"
    private GameLog() {}

    public static GameLog getInstance() {
        if (instance == null) {
            instance = new GameLog();
        }
        return instance;
    }

    public void addEntry(String entry) {
        logData += entry + "\n";
        System.out.println("LOG: " + entry);
    }

    /* PERFORMANCE NOTE: 
    This Singleton implementation is memory-efficient because it ensures 
    only one instance of the logger exists throughout the game's lifecycle. 
    Because it uses "Lazy Initialization" (creating the instance only when 
    needed), it saves resources during the initial game startup.
    */
}