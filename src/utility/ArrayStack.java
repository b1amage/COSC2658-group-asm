package utility;

// Array-based implementation of stack
public class ArrayStack<T> {
    private int size;
    private static int MAX_SIZE;
    private T[] items;

    public ArrayStack(int capacity) {
        size = 0;
        MAX_SIZE = capacity;
        items = (T[])new Object[MAX_SIZE];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean push(T item) {
        // make sure the stack still have empty slot
        if (size < MAX_SIZE) {
            items[size] = item;
            size++;
            return true;
        }
        return false;
    }

    public boolean pop() {
        // make sure the stack is not empty
        if (isEmpty()) {
            return false;
        }
        size--;
        return true;
    }

    public T peek() {
        // make sure the stack is not empty
        if (isEmpty()) {
            return null;
        }
        return items[size - 1];
    }
}