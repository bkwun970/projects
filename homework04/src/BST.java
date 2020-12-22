import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Your implementation of a BST.
 *
 * @author Byoungil kwun
 * @version 1.0
 * @userid bkwun3
 * @GTID 903392084
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert the null data into the binary search tree.");
        }
        for (T e : data) {
            if (e == null) {
                throw new IllegalArgumentException("Found the null data. Cannot insert the null data into"
                       + " binary search tree.");
            }
            add(e);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the binary search tree.");
        }
        if (root == null) {
            root = new BSTNode<>(data);
            size++;
        } else {
            root = addHelper(root, data);
        }
    }

    /**
     * helper method for add(T data) method.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing is done.
     *
     * If the duplicate is found, it won't be added, and size will not be incremented.
     *
     * O(log n) for best and average cases and O(n) for worst case.
     * @param root the root node of the BST
     * @param data the data to be added to the BST
     * @return the root node of the BST
     */
    private BSTNode<T> addHelper(BSTNode<T> root, T data) {
        if (root == null) {
            size++;
            return new BSTNode<>(data);
        } else {
            if (root.getData().compareTo(data) > 0) {
                root.setLeft(addHelper(root.getLeft(), data));
            } else if (root.getData().compareTo(data) < 0) {
                root.setRight(addHelper(root.getRight(), data));
            } else {
                return root;
            }
        }
        return root;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the binary search tree.");
        }
        BSTNode<T> removed = new BSTNode<>(null);
        root = removeHelper(root, removed, data);
        size--;
        return removed.getData();
    }

    /**
     * Helper method for remove(T data) method
     *
     * removes the data from the tree and returns the edited BST.
     *
     * @param root the root of the BST
     * @param removed the node containing reference to removed data.
     * @param data the data that will be removed from the BST
     * @return the node of edited BST
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode<T> removeHelper(BSTNode<T> root, BSTNode<T> removed, T data) {
        if (root == null) {
            throw new NoSuchElementException("Cannot remove the data because the input data is not in the BST");
        }
        if (root.getData().compareTo(data) > 0) {
            root.setLeft(removeHelper(root.getLeft(), removed, data));
        } else if (root.getData().compareTo(data) < 0) {
            root.setRight(removeHelper(root.getRight(), removed, data));
        } else {
            removed.setData(data);

            if (root.getLeft() == null && root.getRight() == null) {
                return null;
            } else if (root.getLeft() == null && root.getRight() != null) {
                return root.getRight();
            } else if (root.getLeft() != null && root.getRight() == null) {
                return root.getLeft();
            } else {
                T temp = root.getData();
                T sucData = successor(root.getRight()).getData();
                root.setData(sucData);
                root.setRight(removeHelper(root.getRight(), removed, sucData));
                removed.setData(temp);
            }
        }
        return root;
    }

    /**
     * Successor method that recursively finds the data for the node containing two children.
     * @param root the root node containing the data we want to remove.
     * @return the node that will replace the removed node.
     */
    private BSTNode<T> successor(BSTNode<T> root) {
        if (root.getLeft() == null) {
            return root;
        }
        return successor(root.getLeft());
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null. Cannot find the data from the tree");
        }
        return getHelper(root, data);
    }

    /**
     * helper method for get(T data) method.
     * gets data using recursion
     * returns the data from the node of a tree if its data matches with param data
     *
     * @param root root node of the BST
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private T getHelper(BSTNode<T> root, T data) {
        if (root == null) {
            throw new NoSuchElementException("Cannot find the data in the tree");
        }
        if (root.getData().equals(data)) {
            return root.getData();
        }
        if (root.getData().compareTo(data) > 0) {
            return getHelper(root.getLeft(), data);
        }
        return getHelper(root.getRight(), data);
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null. Cannot find the data from the tree");
        }
        if (root == null) {
            return false;
        }
        return containsHelper(root, data);
    }

    /**
     * helper method for contains(T data) method
     * this method checks it the param data is contained in the BST
     * this method uses recursion to do so.
     *
     * @param root root node of the BST
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean containsHelper(BSTNode<T> root, T data) {
        if (root == null) {
            return false;
        }
        if (root.getData().equals(data)) {
            return true;
        }
        if (root.getData().compareTo(data) > 0) {
            return containsHelper(root.getLeft(), data);
        }
        return containsHelper(root.getRight(), data);
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> arr = new ArrayList<>();
        preorderHelper(root, arr);
        return arr;
    }

    /**
     * helper method for preorder() method.
     * adds data into the list recursively using preorder traversal
     * @param root the root node of the BST
     * @param arr the list where data is added via preorder traversal
     */
    private void preorderHelper(BSTNode<T> root, List<T> arr) {
        if (root == null) {
            return;
        }
        arr.add(root.getData());
        preorderHelper(root.getLeft(), arr);
        preorderHelper(root.getRight(), arr);
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> arr = new ArrayList<>();
        inorderHelper(root, arr);
        return arr;
    }

    /**
     * helper method for inorder() method.
     * adds data into the list recursively using inorder traversal
     * @param root the root node of the BST
     * @param arr the list where the data is added via inorder traversal.
     */
    private void inorderHelper(BSTNode<T> root, List<T> arr) {
        if (root == null) {
            return;
        }
        inorderHelper(root.getLeft(), arr);
        arr.add(root.getData());
        inorderHelper(root.getRight(), arr);
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> arr = new ArrayList<>();
        postorderHelper(root, arr);
        return arr;
    }

    /**
     * helper method for postorder() method.
     * adds data into the list recursively using postorder traversal
     * @param root the root node of the BST
     * @param arr the list where data is added via postorder traversal
     */
    private void postorderHelper(BSTNode<T> root, List<T> arr) {
        if (root == null) {
            return;
        }
        postorderHelper(root.getLeft(), arr);
        postorderHelper(root.getRight(), arr);
        arr.add(root.getData());
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> ans = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.offer(root);

        while (queue.size() != 0) {
            BSTNode<T> tempNode = queue.poll();
            if (tempNode != null) {
                T tempData = tempNode.getData();
                ans.add(tempData);
                queue.offer(tempNode.getLeft());
                queue.offer(tempNode.getRight());
            }
        }
        return ans;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * helper method for height() method.
     * this method calculates the height of BST using recursion
     * @param root the root node of the BST
     * @return the height of the tree, -1 if the tree is empty.
     */
    private int heightHelper(BSTNode<T> root) {
        if (root == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(root.getLeft()), heightHelper(root.getRight()));
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("the input is greater than the size of the BST");
        }
        if (k < 0) {
            throw new  IllegalArgumentException("the input is greater than the size of the BST");
        }
        LinkedList<T> ans = new LinkedList<>();
        kLargestHelper(ans, root, k);
        return ans;
    }

    /**
     * helper method for kLargest(int k) method.
     * recursively adds Klargest() numbers to the list
     * @param ans the list containing Klargest() elements
     * @param root the root node of the BST
     * @param k the number of largest elements to return
     */
    private void kLargestHelper(LinkedList<T> ans, BSTNode<T> root, int k) {
        if (root != null && ans.size() < k) {
            kLargestHelper(ans, root.getRight(), k);
            if (root != null && ans.size() < k) {
                ans.addFirst(root.getData());
                if (root != null && ans.size() < k) {
                    kLargestHelper(ans, root.getLeft(), k);
                }
            }
        }
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
