package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Handles Game Saving and demonstrates NIO File/Directory operations.
 * Satisfies Chapter 8 & 9 Checklist Requirements.
 */
public class SaveManager {

    public static void runSaveAndNIO(Scanner scanner, String gameLogData) {
        System.out.println("\n--- SYSTEM SAVE INITIALIZED ---");
        
        // 1. Prompt the user for the save file location (5 Points)
        System.out.print("Enter the full path to save your game (e.g., C:\\temp\\savegame.ser): ");
        String pathStr = scanner.nextLine().trim();
        Path savePath = Paths.get(pathStr);

        // 2. Save (Serialization) and write File Size (5 Points)
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savePath.toFile()))) {
            // We serialize the GameLog string data. Strings are automatically Serializable!
            oos.writeObject(gameLogData);
            System.out.println("Game successfully serialized and saved.");
            
            // Getting the size using NIO Files class
            long fileSize = Files.size(savePath);
            System.out.println("Save File Size: " + fileSize + " bytes.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getMessage());
            return; // Stop if the save fails
        }

        System.out.println("\n*** INITIATING PRISON SECURITY SCAN (NIO DEMO) ***");
        Path projectDir = Paths.get(System.getProperty("user.dir")); // Gets your Eclipse project folder

        // 3. Programmatically traverse directory and print name and size (5 Points)
        System.out.println("\n--- 1. Traversing Project Directory ---");
        try {
            // Limits depth to 2 so it doesn't print thousands of Eclipse background files
            Files.walk(projectDir, 2) 
                 .filter(Files::isRegularFile)
                 .forEach(path -> {
                     try {
                         System.out.println("File: " + path.getFileName() + " | Size: " + Files.size(path) + " bytes");
                     } catch (IOException e) { }
                 });
        } catch (IOException e) {
            System.out.println("Traversal error.");
        }

        // 4. Retrieve a subdirectory and list contents (5 Points)
        System.out.println("\n--- 2. Listing Subdirectory (src) Contents ---");
        Path srcDir = Paths.get(System.getProperty("user.dir"), "src", "main");
        try {
            if (Files.exists(srcDir)) {
                Files.list(srcDir).forEach(path -> System.out.println("Found in src/main: " + path.getFileName()));
            }
        } catch (IOException e) {
            System.out.println("Error reading subdirectory.");
        }

        // 5. Choose a file and print contents using a lambda expression (5 Points)
        System.out.println("\n--- 3. Reading File Contents (Lambda) ---");
        Path messagesFile = Paths.get(System.getProperty("user.dir"), "src", "main", "Messages_en.properties");
        try {
            if (Files.exists(messagesFile)) {
                System.out.println("Contents of Messages_en.properties:");
                // Reads all lines and uses a lambda expression to print each one
                Files.lines(messagesFile).forEach(line -> System.out.println(">> " + line));
            } else {
                System.out.println("Could not locate the Messages_en.properties file to read.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
        
        System.out.println("--- SECURITY SCAN COMPLETE ---\n");
    }
}