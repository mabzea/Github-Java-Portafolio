package Ex;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ExceptionHandlingDemo {

    public static void main(String[] args) {
        // We will try to divide an element from this array
        int[] numbers = {10, 20, 30, 40, 50};
        Scanner scanner = new Scanner(System.in);

        // The 'try' block encloses the code that might cause an exception.
        try {
            System.out.println("--- Array Division Program ---");
            System.out.println("Array contents: {10, 20, 30, 40, 50}");

            System.out.print("Enter the array index to use (0-4): ");
            int index = scanner.nextInt();

            System.out.print("Enter the number to divide by: ");
            int divisor = scanner.nextInt();

            // This is the "risky" operation that could cause an exception
            int result = numbers[index] / divisor;

            System.out.println("Success! Result: " + numbers[index] + " / " + divisor + " = " + result);

        }
        // This 'catch' block will only execute if the user enters a non-integer value.
        catch (InputMismatchException e) {
            System.out.println("\n--- Error ---");
            System.out.println("Invalid input. You must enter whole numbers.");
        }
        // This 'catch' block will only execute if the user enters an index outside the valid range (0-4).
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("\n--- Error ---");
            System.out.println("Invalid index. Please choose an index between 0 and 4.");
        }
        // This 'catch' block will only execute if the user tries to divide by zero.
        catch (ArithmeticException e) {
            System.out.println("\n--- Error ---");
            System.out.println("You cannot divide by zero.");
        }
        // This is a general "catch-all" for any other unexpected exceptions.
        catch (Exception e) {
            System.out.println("\n--- An unexpected error occurred ---");
            System.out.println("Error details: " + e.getMessage());
        }
        // The 'finally' block will ALWAYS execute, whether an exception occurred or not.
        // It's typically used for cleanup, like closing files or database connections.
        finally {
            System.out.println("\n--- Program finished. ---");
            scanner.close(); // Clean up the scanner resource
        }
    }
}

