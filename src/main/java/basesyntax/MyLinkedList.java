package basesyntax;

import java.util.List;
import java.util.Objects;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }

    }

    @Override
    public void add(T value) {
        Node<T> lastNode = tail;
        Node<T> newNode = new Node<>(lastNode, value, null);
        tail = newNode;
        if (lastNode == null) {
            head = newNode;
        } else {
            lastNode.next = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds " + index);
        }
        if (index == size) {
            add(value);
        } else {
            linkBefore(value, getIndex(index));
        }
    }

    @Override
    public void addAll(List<T> list) {
        if (!list.isEmpty()) {
            for (T node : list) {
                add(node);
            }
        } else {
            throw new RuntimeException("list is empty");
        }
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return getIndex(index).item;
    }

    @Override
    public T set(T value, int index) {
        checkIndex(index);
        Node<T> newNode = getIndex(index);
        T oldNode = newNode.item;
        newNode.item = value;
        return oldNode;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        return unlink(getIndex(index));
    }

    @Override
    public boolean remove(T object) {
        Node<T> value = head;
        while (value != null) {
            if (Objects.equals(value.item, object)) {
                unlink(value);
                return true;
            }
            value = value.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if (size() == 0) {
            return true;
        }
        return false;
    }

    private void linkBefore(T value, Node<T> nextNode) {
        Node<T> previousNode = nextNode.prev;
        Node<T> newNode = new Node<>(previousNode, value, nextNode);
        nextNode.prev = newNode;
        if (previousNode == null) {
            head = newNode;
        } else {
            previousNode.next = newNode;
        }
        size++;
    }

    private Node<T> getIndex(int index) {
        Node<T> nodeByIndex;
        if (index < size / 2) {
            nodeByIndex = head;
            for (int i = 0; i < index; i++) {
                nodeByIndex = nodeByIndex.next;
            }
        } else {
            nodeByIndex = tail;
            for (int i = size - 1; i > index; i--) {
                nodeByIndex = nodeByIndex.prev;
            }
        }
        return nodeByIndex;
    }

    private boolean checkIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds" + index);
        }
        return true;
    }

    private T unlink(Node<T> deletedNode) {
        Node<T> nextNode = deletedNode.next;
        Node<T> prevNode = deletedNode.prev;
        if (size == 1) {
            tail = null;
            head = null;
        } else if (prevNode == null) {
            head.next.prev = null;
            head = head.next;
        } else if (nextNode == null) {
            tail.prev.next = null;
            tail = tail.prev;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return deletedNode.item;
    }
}
