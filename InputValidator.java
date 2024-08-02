import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {

    // Gets an integer input from the user
    public int getIntInput(Scanner scanner) {
        int input = 0;
        boolean isValid = false;

        // Continue until valid input is provided
        while (!isValid) {
            try {
                input = scanner.nextInt(); // Attempts to read an integer
                isValid = true; // Set isValid to true if input is valid
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid number.");
                System.out.print("\n Enter a choice and Press ENTER to continue[1-6]:");
                scanner.next(); // skip the invalid input to prevent infinite loop
            }
        }

        return input; // Return input
    }

}
