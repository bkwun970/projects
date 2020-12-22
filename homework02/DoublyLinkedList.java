import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
 *
 * @author  Jeonghoon Lee
 * @version 1.0
 * @userid jlee3688
 * @GTID 903548027
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The value you assign in index is out of bound."
            + " index should be 0 <= index < size of Node"
            + " Please enter the number that in the bound.");
        } else if (data == null) {
            throw new IllegalArgumentException("You cannot insert null into data structure"
            + "Please do not enter null in data.");
        }

        DoublyLinkedListNode<T> tempNode = new DoublyLinkedListNode<>(data);
        if (size == 0) {

            this.head = tempNode;
            this.tail = tempNode;

        } else if (index > (size / 2)) {
            DoublyLinkedListNode<T> tempPrevious = tail;
            for (int i = 0; i < (size - index); i++) {
                tempPrevious = tempPrevious.getPrevious();
            }
            if (index != size) {
                DoublyLinkedListNode<T> tempNext = tempPrevious.getNext();
                tempNext.setPrevious(tempNode);
                tempNode.setNext(tempNext);
            } else {
                this.tail = tempNode;
            }
            tempPrevious.setNext(tempNode);
            tempNode.setPrevious(tempPrevious);

        } else {
            DoublyLinkedListNode<T> tempNext = head;
            for (int i = 0; i < index; i++) {
                tempNext = tempNext.getNext();
            }
            if (index != 0) {
                DoublyLinkedListNode<T> tempPrevious = tempNext.getPrevious();
                tempPrevious.setNext(tempNode);
                tempNode.setPrevious(tempPrevious);
            } else {
                this.head = tempNode;
            }
            tempNext.setPrevious(tempNode);
            tempNode.setNext(tempNext);
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
            throw new IllegalArgumentException("You cannot insert null into data structure"
                    + "Please do not enter null in data.");
        } else {
            DoublyLinkedListNode<T> tempNode = new DoublyLinkedListNode<>(data);

            if (size == 0) {
                head = tempNode;
                tail = tempNode;
            } else {
                tempNode.setNext(head);
                head.setPrevious(tempNode);
                head = tempNode;
            }
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
            throw new IllegalArgumentException("You cannot insert null into data structure"
                    + "Please do not enter null in data.");
        } else {
            DoublyLinkedListNode<T> tempNode = new DoublyLinkedListNode<>(data);
            if (size == 0) {
                head = tempNode;
                tail = tempNode;
            } else {
                tail.setNext(tempNode);
                tempNode.setPrevious(tail);
                tail = tempNode;
            }
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
            throw new IndexOutOfBoundsException("The value you assign in index is out of bound."
                    + " index should be 0 <= index < size of Node"
                    + " Please enter the number that in the bound.");
        } else {
            DoublyLinkedListNode<T> temp;
            if (index < (size / 2)) {
                temp = head;
                for (int i = 0; i < index; i++) {
                    temp = temp.getNext();
                }
                DoublyLinkedListNode<T> tempNext = temp.getNext();
                if (index != 0) {
                    DoublyLinkedListNode<T> tempPrevious = temp.getPrevious();
                    tempPrevious.setNext(tempNext);
                    tempNext.setPrevious(tempPrevious);
                } else {
                    head = tempNext;
                    tempNext.setPrevious(null);
                }
            } else {
                temp = tail;
                for (int i = 0; i < size - index - 1; i++) {
                    temp = temp.getPrevious();
                }
                if (size == 1) {
                    head = null;
                    tail = null;
                } else {
                    DoublyLinkedListNode<T> tempPrevious = temp.getPrevious();
                    if (index != (size - 1)) {
                        DoublyLinkedListNode<T> tempNext = temp.getNext();
                        tempNext.setPrevious(tempPrevious);
                        tempPrevious.setNext(tempNext);
                    } else {
                        tail = tempPrevious;
                        tempPrevious.setNext(null);
                    }
                }
            }
            size--;
            return temp.getData();
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
        if (size == 0) {
            throw new NoSuchElementException("There is no node to remove."
            + "Please add node before you remove data.");
        } else {
            DoublyLinkedListNode<T> temp = head;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                DoublyLinkedListNode<T> tempNext = temp.getNext();
                tempNext.setPrevious(null);
                head = temp.getNext();
            }
            size--;
            return temp.getData();
        }
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
            throw new NoSuchElementException("There is no node to remove."
                    + "Please add node before you remove data.");
        } else {
            DoublyLinkedListNode<T> temp = tail;
            if (size == 1) {
                head = null;
                tail = null;
            } else {
                DoublyLinkedListNode<T> tempPrevious = temp.getPrevious();
                tempPrevious.setNext(null);
                tail = tempPrevious;
            }
            size--;
            return temp.getData();
        }
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
            throw new IndexOutOfBoundsException("The value you assign in index is out of bound."
                    + " index should be 0 <= index < size of Node"
                    + " Please enter the number that in the bound.");
        } else {
            DoublyLinkedListNode<T> temp;
            if (index < (size / 2)) {
                temp = head;
                for (int i = 0; i < index; i++) {
                    temp = temp.getNext();
                }
            } else {
                temp = tail;
                for (int i = 0; i < size - index - 1; i++) {
                    temp = temp.getPrevious();
                }
            }
            return temp.getData();
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
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
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
            throw new IllegalArgumentException("You cannot insert null into data structure"
                    + "Please do not enter null in data.");
        } else if (size == 0) {
            throw new NoSuchElementException("There is no node to remove."
                    + "Please add node before you remove data.");
        } else {

            DoublyLinkedListNode<T> temp = tail;
            while (!temp.getData().equals(data)) {
                temp = temp.getPrevious();
                if (temp == null) {
                    throw new NoSuchElementException("Cannot found data in data structure, please"
                    + "insert data that exists in data structure");
                }
            }

            if (size == 1) {
                tail = null;
                head = null;
            } else if (temp.getNext() == null) {
                temp.getPrevious().setNext(null);
                tail = temp.getPrevious();
            } else if (temp.getPrevious() == null) {
                temp.getNext().setPrevious(null);
                head = temp.getNext();
            } else {
                DoublyLinkedListNode<T> tempPrevious = temp.getPrevious();
                DoublyLinkedListNode<T> tempNext = temp.getNext();

                tempPrevious.setNext(tempNext);
                tempNext.setPrevious(tempPrevious);
            }
            size--;
            return temp.getData();

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

        Object[] array = new Object[size];
        DoublyLinkedListNode<T> temp = head;

        for (int i = 0; i < size; i++) {
            array[i] = temp.getData();
            temp = temp.getNext();
        }
        return array;
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
