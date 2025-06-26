import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

//MEHMET ŞAKİR ŞEKER
//SAMET TOLGA ESEN
//ALPEREN DEMİREZEN
//ALP BOSTANCI
//Testing Framework System

public class TestSystemLauncher {
    public static void main(String[] args) throws InterruptedException {

        // Create a test creator instance (using Factory Method Pattern)
        TestCreator creator = new AixTestCreator();

        // Generate a list of test components using the creator
        ArrayList<TestComponent> tests = creator.createTest();

        // Create a master test suite that contains all the tests
        TestSuite allTests = new TestSuite("All Tests");
        for (TestComponent test : tests) {
            allTests.add(test);  // Add each test to the master suite
        }

        // Get the singleton instance of the TestManager
        TestManager manager = TestManager.getInstance(allTests);

        // Ask the user which tests to run
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which test do you want to run?");
        System.out.println("1 - All Tests");
        System.out.println("2 - GUI Tests");
        System.out.println("3 - Network Tests");
        System.out.println("4 - Run selected tests automatically every Monday");
        int choice = scanner.nextInt();  // Read user input

        // Execute based on user's choice
        switch (choice) {
            case 1 -> manager.runAllTests();        // Run all tests
            case 2 -> manager.runGUITests();        // Run only GUI tests
            case 3 -> manager.runNetworkTests();    // Run only Network tests
            case 4 -> {
                // If the user wants scheduled testing on Mondays
                System.out.println("Project Leader, please choose which tests to run on Monday automatically:");
                System.out.println("1 - All Tests");
                System.out.println("2 - GUI Tests");
                System.out.println("3 - Network Tests");
                int subChoice = scanner.nextInt();

                // Simulate a loop that waits until the next Monday
                testOnMonday(DayOfWeek.MONDAY, 1000);  // 1000 ms = 1 simulated day

                // Execute the selected test once Monday is reached
                switch (subChoice) {
                    case 1 -> manager.runAllTests();
                    case 2 -> manager.runGUITests();
                    case 3 -> manager.runNetworkTests();
                    default -> System.out.println("Invalid choice.");
                }
            }
            default -> System.out.println("Invalid choice.");  // Handle invalid selection
        }

        scanner.close();  // Close the scanner resource
    }

    public static void testOnMonday(DayOfWeek targetDay, int millisPerDay) throws InterruptedException {
        DayOfWeek today = LocalDate.now().getDayOfWeek();  // Get the current day of the week

        // Wait until the current day matches the target day
        while (today != targetDay) {
            System.out.println("Today is " + today + ". Waiting for " + targetDay + "...");
            Thread.sleep(millisPerDay);  // Simulated waiting time
            today = today.plus(1);       // Move to the next simulated day
        }

        System.out.println("It's " + targetDay + "! Running tests now...");  // Target day reached, proceed
    }
}
