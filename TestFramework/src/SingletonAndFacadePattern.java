class TestManager {
    private static TestManager instance;  // The one and only instance (Singleton) lazy instantiation
    private TestRunner testRunner;               // Helps to run tests
    private TestSuite allTests;

    private final CheckinManager checkin;
    private final ReportGenerator reporter;

    // Private constructor to prevent direct creation of the object
    private TestManager(TestSuite allTests) {
        this.testRunner = new TestRunner();
        this.allTests = allTests;

        checkin = new CheckinManager();
        reporter = new ReportGenerator();
    }

    // Singleton method: returns the single instance (creates it if needed)
    public static TestManager getInstance(TestSuite allTests) {
        if (instance == null) {
            instance = new TestManager(allTests);
        }
        return instance;
    }
    // Facade method: runs all tests
    public void runAllTests() {
        checkin.checkInCode();

        System.out.println("=== Facade: All Tests Are Running ===");
        TestInvoker invoker = new TestInvoker();
        invoker.addCommand(new RunAllTestsCommand(allTests, testRunner));
        invoker.runTests();

        reporter.generateReport();
    }
    // Facade method: runs only GUI-related tests
    public void runGUITests() {
        checkin.checkInCode();

        System.out.println("=== Facade: GUI Tests Are Running ===");

        TestInvoker invoker = new TestInvoker();
        invoker.addCommand(new RunGUITestsCommand(allTests, testRunner));
        invoker.runTests();
        reporter.generateReport();
    }
    // Facade method: runs only Network-related tests
    public void runNetworkTests() {
        checkin.checkInCode();

        System.out.println("=== Facade: Network Tests Are Running ===");
        TestInvoker invoker = new TestInvoker();
        invoker.addCommand(new RunNetworkTestsCommand(allTests, testRunner));
        invoker.runTests();

        reporter.generateReport();
    }

    // Helper method: filters tests by keyword and runs them
}

class CheckinManager {
    public void checkInCode() {
        System.out.println("[CHECK-IN] New code checked into the repository.");
    }
}

class ReportGenerator {
    public void generateReport() {
        System.out.println("[REPORT] Test results have been summarized and sent to stakeholders.");
    }
}
