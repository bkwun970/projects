import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author Byoungil Kwun
 * @version 1.0
 * @userid bkwun3
 * @GTID 903392084
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Input index must be within the bounds of the list");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, null);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
        } else if (index == 0) {
            head.setPrevious(newNode);
            newNode.setNext(head);
            head = head.getPrevious();
        } else if (index == size) {
            newNode.setPrevious(tail);
            tail.setNext(newNode);
            tail = tail.getNext();
        } else {
            int j = 0;
            DoublyLinkedListNode<T> curNode = head;
            while (j != index) {
                curNode = curNode.getNext();
                j++;
            }
            newNode.setNext(curNode);
            newNode.setPrevious(curNode.getPrevious());
            curNode.getPrevious().setNext(newNode);
            curNode.setPrevious(newNode);
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, null);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            newNode.setNext(head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        }
        DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data, null, null);
        if (head == null && tail == null) {
            head = newNode;
            tail = newNode;
            size++;
        } else {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = tail.getNext();
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Input index must be within the bounds of the list");
        }
        if (index == 0) {
            DoublyLinkedListNode<T> tempNode = head;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                head = head.getNext();
                head.setPrevious(null);
            }
            size--;
            return tempNode.getData();
        } else if (index == size - 1) {
            DoublyLinkedListNode<T> tempNode = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return tempNode.getData();
        } else {
            int j = 0;
            DoublyLinkedListNode<T> curNode = head;
            while (j != index) {
                curNode = curNode.getNext();
                j++;
            }
            curNode.getPrevious().setNext(curNode.getNext());
            curNode.getNext().setPrevious(curNode.getPrevious());
            size--;
            return curNode.getData();
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot remove an element. The list is currently empty.");
        }
        if (head.getNext() == null) {
            DoublyLinkedListNode<T> tempNode = head;
            head = null;
            tail = null;
            size--;
            return tempNode.getData();
        }
        DoublyLinkedListNode<T> tempNode = head;
        head = head.getNext();
        head.setPrevious(null);
        size--;
        return tempNode.getData();
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("Cannot remove an element. The list is currently empty.");
        }
        DoublyLinkedListNode<T> tempNode = tail;
        if (size == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.getPrevious();
            tail.setNext(null);
        }
        size--;
        return tempNode.getData();
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Input index must be within the bounds of the list");
        }
        if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            int j = 0;
            DoublyLinkedListNode<T> curNode = head;
            while (j != index) {
                curNode = curNode.getNext();
                j++;
            }
            return curNode.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        DoublyLinkedListNode<T> emptyNode = null;
        head = emptyNode;
        tail =  emptyNode;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove the null data from the list");
        }
        if (size == 0) {
            throw new NoSuchElementException("Cannot find the data in the list");
        }
        if (tail.getData().equals(data)) {
            DoublyLinkedListNode<T> tempNode = tail;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                tail = tail.getPrevious();
                tempNode.getPrevious().setNext(null);
                tempNode.setPrevious(null);
            }
            size--;
            return tempNode.getData();
        } else {
            DoublyLinkedListNode<T> curNode = tail;
            while (curNode.getPrevious() != null) {
                curNode = curNode.getPrevious();

                if (curNode.getData().equals(head.getData()) && !(curNode.getData().equals(data))) {
                    throw new NoSuchElementException("Cannot find the data in the list");
                }
                if (curNode.getData().equals(head.getData()) && curNode.getData().equals(data)) {
                    head = head.getNext();
                    head.setPrevious(null);
                    break;
                }
                if (curNode.getData().equals(data)) {
                    curNode.getPrevious().setNext(curNode.getNext());
                    curNode.getNext().setPrevious(curNode.getPrevious());
                    break;
                }
            }
            size--;
            return curNode.getData();
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] arr = new Object[size];
        DoublyLinkedListNode<T> curNode = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curNode.getData();
            curNode = curNode.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}