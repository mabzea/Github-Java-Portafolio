package project_2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    
	
	
    // Initialize the Logger
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started.");

        try {
            logger.debug("Attempting calculation...");
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            // Log the error to the file
            logger.error("A math error occurred: " + e.getMessage());
        } finally {
            logger.info("Application finishing execution.");
        }
        
        
        
            try {
                System.out.println("Hello World!");
            } catch (Exception e) {
                System.out.println("Something went wrong.");
            } finally {
                System.out.println("Done.");
            }
    }
    
    
}