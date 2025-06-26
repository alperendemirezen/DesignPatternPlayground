import java.util.ArrayList;
import java.util.List;

//MEHMET ŞAKİR ŞEKER
//SAMET TOLGA ESEN
//ALPEREN DEMİREZEN
//ALP BOSTANCI
//Testing Framework System


// This class runs tests basically "Receiver".
class TestRunner {
    // This runs one test case.
    public void runTest(TestComponent testComponent) {
        System.out.println("[RUNNING] " + testComponent.getName());
        testComponent.execute();
    }

    // This runs a group of test cases (a test suite).
    public void runTestSuite(TestSuite suite) {
        suite.run(this);
    }
}

// This is an abstract class for test commands to filter the tests based on its type such as GUI or Network.
abstract class BaseTestCommand {
    protected TestSuite testSuite;       // Group of tests
    protected TestRunner testRunner;     // Who runs the tests

    // Constructor
    public BaseTestCommand(TestSuite testSuite, TestRunner testRunner) {
        this.testSuite = testSuite;
        this.testRunner = testRunner;
    }
    public abstract void execute();
    // This method finds and runs test cases with a keyword like GUI or Network
    protected void filterAndRunTests(TestSuite suite, String keyword) {
        AbstractTestIterator iterator = suite.createIterator();
        for (iterator.first(); !iterator.isDone(); iterator.next()) {
            TestComponent component = iterator.currentItem();

            // If the test matches the keyword
            if (component.acceptFilter(keyword)) {
                if (component.getCount() > 0) { // It is a TestSuite
                    testRunner.runTestSuite((TestSuite) component);
                } else { // It is a TestCase
                    testRunner.runTest(component);
                }
            } else if (component.getCount() > 0) {
                // Keep looking inside this suite
                filterAndRunTests((TestSuite) component, keyword);
            }
        }
    }
}

// This command runs all tests not filtering it based on its type
class RunAllTestsCommand extends BaseTestCommand {
    public RunAllTestsCommand(TestSuite testSuite, TestRunner testRunner) {
        super(testSuite, testRunner);
    }

    public void execute() {
        testRunner.runTestSuite(testSuite);
    }
}

// This command runs only GUI tests
class RunGUITestsCommand extends BaseTestCommand {
    public RunGUITestsCommand(TestSuite testSuite, TestRunner testRunner) {
        super(testSuite, testRunner);
    }

    public void execute() {
        filterAndRunTests(testSuite, "GUI");
    }
}

// This command runs only Network tests
class RunNetworkTestsCommand extends BaseTestCommand {
    public RunNetworkTestsCommand(TestSuite testSuite, TestRunner testRunner) {
        super(testSuite, testRunner);
    }

    public void execute() {
        filterAndRunTests(testSuite, "Network");
    }
}

// This is the "Invoker". It stores and runs commands.
class TestInvoker {
    private List<BaseTestCommand> commands = new ArrayList<>();

    // Add a new command
    public void addCommand(BaseTestCommand command) {
        commands.add(command);
    }

    // Run all commands
    public void runTests() {
        for (BaseTestCommand command : commands) {
            command.execute();
        }
        commands.clear(); // Clear after running
    }

    // Remove all commands without running
    public void clearCommands() {
        commands.clear();
    }
}