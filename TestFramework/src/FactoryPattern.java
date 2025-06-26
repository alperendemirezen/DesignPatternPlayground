import java.util.ArrayList;

// Abstract class for the Factory Method pattern
// ----------------------------------------------------
// Defines a template for creating different types of tests.
// Subclasses will implement the actual creation of specific test cases and suites.
abstract class TestCreator {
    private ArrayList<TestComponent> testComponents;

    // Factory Methods to be implemented by subclasses
    abstract TestComponent createGUITestCase();
    abstract TestComponent createGUITestSuite();
    abstract TestComponent createNetworkTestCase();
    abstract TestComponent createNetworkTestSuite();

    // Template method to create all test components (GUI + Network)
    public ArrayList<TestComponent> createTest() {
        testComponents = new ArrayList<>();
        testComponents.add(createGUITestCase());
        testComponents.add(createGUITestSuite());
        testComponents.add(createNetworkTestCase());
        testComponents.add(createNetworkTestSuite());
        return testComponents;
    }
}

// Concrete implementation of TestCreator for the Aix platform
class AixTestCreator extends TestCreator {
    // Returns a single GUI test case
    @Override
    public TestComponent createGUITestCase() {
        return new TestCase("Aix GUI – Single Button Test");
    }

    // Returns a suite of GUI test cases
    @Override
    public TestComponent createGUITestSuite() {
        TestSuite suite = new TestSuite("Aix GUI Test Suite");
        suite.add(new TestCase("Aix GUI - Button Test"));
        suite.add(new TestCase("Aix GUI - Window Resize Test"));
        suite.add(new TestCase("Aix GUI - Menu Test"));
        return suite;
    }
    // Returns a single network test case
    @Override
    public TestComponent createNetworkTestCase() {
        return new TestCase("Aix Network – Simple Ping Test");
    }

    // Returns a suite of network test cases
    @Override
    public TestComponent createNetworkTestSuite() {
        TestSuite suite = new TestSuite("Aix Network Test Suite");
        suite.add(new TestCase("Aix Network - Ping Test"));
        suite.add(new TestCase("Aix Network - Port Scan Test"));
        suite.add(new TestCase("Aix Network - DNS Resolution Test"));
        return suite;
    }
}

// Concrete implementation of TestCreator for the Mac platform
class MacTestCreator extends TestCreator {
    // Returns a single GUI test case
    @Override
    public TestComponent createGUITestCase() {
        return new TestCase("Mac GUI – Dark Mode Toggle Test");
    }

    // Returns a suite of GUI test cases
    @Override
    public TestComponent createGUITestSuite() {
        TestSuite suite = new TestSuite("Mac GUI Test Suite");
        suite.add(new TestCase("Mac GUI - Scroll Test"));
        suite.add(new TestCase("Mac GUI - Dark Mode Test"));
        suite.add(new TestCase("Mac GUI – Retina Display Adjustment Test"));
        return suite;
    }
    // Returns a single network test case
    @Override
    public TestComponent createNetworkTestCase() {
        return new TestCase("Mac Network – Basic Connectivity Test");
    }

    // Returns a suite of network test cases
    @Override
    public TestComponent createNetworkTestSuite() {
        TestSuite suite = new TestSuite("Mac Network Test Suite");
        suite.add(new TestCase("Mac Network - Ping Test"));
        suite.add(new TestCase("Mac Network - Firewall Rule Test"));
        suite.add(new TestCase("Mac Network – Proxy Detection Test"));
        return suite;
    }
}
