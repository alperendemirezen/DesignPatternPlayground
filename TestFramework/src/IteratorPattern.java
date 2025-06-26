// Iterator Interface
// ------------------
// Defines a common interface for iterating over a collection of TestComponents.
// This allows traversal without exposing the underlying data structure.
interface AbstractTestIterator {
    // Positions the iterator at the first element.
    void first();

    // Moves the iterator to the next element.
    void next();

    // Checks whether the end of the collection has been reached.
    boolean isDone();

    // Returns the current item in the iteration.
    TestComponent currentItem();
}


// Concrete Iterator
// ------------------
// Implements the AbstractTestIterator interface for iterating over a TestSuite.
// Maintains an internal index to traverse TestComponent elements sequentially.
class TestSuiteIterator implements AbstractTestIterator {
    private TestSuite components;     // The aggregate to iterate over.
    private int currentIndex = 0;     // Current position in the collection.

    // Constructor initializes the iterator with the TestSuite to be traversed.
    public TestSuiteIterator(TestSuite components) {
        this.components = components;
    }

    // Reset iterator to the first element.
    @Override
    public void first() {
        currentIndex = 0;
    }

    // Move to the next element.
    @Override
    public void next() {
        currentIndex++;
    }

    // Check if iteration has reached the end.
    @Override
    public boolean isDone() {
        return currentIndex >= components.getCount();
    }

    // Get the current TestComponent element.
    @Override
    public TestComponent currentItem() {
        if (isDone()) {
            return null;
        }
        return components.get(currentIndex);
    }
}


// Abstract Aggregate Interface
// ----------------------------
// Defines the interface for a collection that allows the creation of an iterator.
// Ensures a consistent way to add, access, and count TestComponent elements.
interface AbstractTestAggregate {

    // Creates an iterator for the aggregate.
    AbstractTestIterator createIterator();

    // Adds a TestComponent to the aggregate.
    void add(TestComponent component);

    // Returns the number of elements in the aggregate.
    int getCount();

    // Returns the TestComponent at the specified index.
    TestComponent get(int idx);
}