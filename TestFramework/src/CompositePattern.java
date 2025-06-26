import java.util.ArrayList;


//      Leaf Class
//		represents leaf objects in the composition. A leaf has no children.
//	    Defines behavior for primitive objects in the composition.(Due to the safer way usage,
//	    addTest and removeTest does not included
//      in the Lead(TestCase) class.
//      Concrete Aggregate because of test component transparent not safe
class TestCase implements TestComponent {
    private String name;

    public TestCase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Policy approach test are responsible for its own execution
    @Override
    public void run(TestRunner runner) {
        runner.runTest(this);
    }

    //Override without doing any implementation
    @Override
    public void add(TestComponent test) {
        System.out.println("Cannot add to a TestCase(Leaf).");
    }

    //Override without doing any implementation
    @Override
    public void remove(TestComponent test) {
        System.out.println("Cannot remove from a TestCase(Leaf).");
    }

    //Executing the test and see the results
    @Override
    public void execute() {
        System.out.println("  -> [Executing TestCase] " + name);
        System.out.println("     -> Result: PASSED");
    }

    // Show test name with dashes
    @Override
    public void display(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("-");
        System.out.println(" TestCase: " + name);
    }

    //Override without doing any implementation
    @Override
    public AbstractTestIterator createIterator() {
        throw new UnsupportedOperationException("Leaf nodes do not support iterators");
    }

    //Leaf does not have children so count is 0
    @Override
    public int getCount() {
        return 0;
    }

    //Override without doing any implementation
    @Override
    public TestComponent get(int idx) {
        throw new UnsupportedOperationException("Leaf nodes do not have children");
    }

    @Override
    public boolean acceptFilter(String keyword) {
        // If the test name contains the keyword it returns true
        return name.contains(keyword);
    }
}

//      This is the "Component". *Transparent*
//		Declares the interface for objects in the composition. Implements
//      default behavior for the interface common to all classes, as
//      appropriate. declares an interface for accessing and managing its
//		child components.
//      Extends Abstract Aggregate
interface TestComponent extends AbstractTestAggregate {
    void add(TestComponent test);
    void remove(TestComponent test);
    void execute();
    void display(int indent);
    String getName();
    void run(TestRunner runner);

    //Method for identifying the test case or test suite
    boolean acceptFilter(String keyword);
}

//      Composite, ConcreteAggregate
//		defines behavior for components having children. Stores child
//		components. Implements child-related operations in the Component interface.
//      Concrete Aggregate: A concrete implementation of the Aggregate interface
class TestSuite implements TestComponent {
    private String name;
    private ArrayList<TestComponent> tests = new ArrayList<>();

    public TestSuite(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //Policy approach for running test suites(Test suites have the responsibility for its own running )
    @Override
    public void run(TestRunner runner) {
        System.out.println("[SUITE] " + getName());
        AbstractTestIterator iterator = createIterator();
        for (iterator.first(); !iterator.isDone(); iterator.next()) {
            TestComponent component = iterator.currentItem();
            component.run(runner);
        }
    }

    // Create an iterator for the tests for the test suite
    @Override
    public AbstractTestIterator createIterator() {
        return new TestSuiteIterator(this);
    }

    @Override
    public int getCount() {
        return tests.size();
    }

    @Override
    public TestComponent get(int idx) {
        return tests.get(idx);
    }

    // Add a test to the suite
    @Override
    public void add(TestComponent test) {
        tests.add(test);
    }


    // Remove a specific test from the suite
    @Override
    public void remove(TestComponent test) {
        tests.removeIf(t -> t.getName().equals(test.getName()));
    }

    // Execute all tests in the suite
    @Override
    public void execute() {
        System.out.println("[SUITE] Running suite: " + name);  // Display suite name
        for (TestComponent test : tests) {
            test.execute();  // Execute each test in the suite
        }
    }

    // Display all tests in the suite with indentation for nested tests
    @Override
    public void display(int indent) {
        for (int i = 0; i < indent; i++) System.out.print("-");
        System.out.println("+ TestSuite: " + name);
        for (TestComponent test : tests) {
            test.display(indent + 2);  // Display each test with increased indentation
        }
    }

    @Override
    public boolean acceptFilter(String keyword) {
        // If the test suite's name contains the keyword it returns true
        return name.contains(keyword);
    }
}