import java.util.LinkedList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the AVL tree");
        }
        for (T e : data) {
            if (e == null) {
                throw new IllegalArgumentException("Null data cannot be added to the AVL tree");
            }
            add(e);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be added to the AVL");
        }
        root = add(data, root);
    }

    /**
     * Recursive helper for add
     *
     * @param data the data to add
     * @param root root node of the avl tree
     * @return root with new tree
     */
    private AVLNode<T> add(T data, AVLNode<T> root) {
        if (root == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (root.getData().compareTo(data) > 0) {
            root.setLeft(add(data, root.getLeft()));
        } else if (root.getData().compareTo(data) < 0) {
            root.setRight(add(data, root.getRight()));
        }
        update(root);
        return rotationCheck(root);
    }

    /**
     * rotation check helper method
     * @param root root node of the current node
     * @return AVL Node
     */
    private AVLNode<T> rotationCheck(AVLNode<T> root) {
        if (root.getBalanceFactor() == -2) {
            if (root.getRight().getBalanceFactor() == -1 || root.getRight().getBalanceFactor() == 0) {
                return leftRotation(root);
            } else if (root.getRight().getBalanceFactor() == 1) {
                root.setRight(rightRotation(root.getRight()));
                return leftRotation(root);
            }
        }
        if (root.getBalanceFactor() == 2) {
            if (root.getLeft().getBalanceFactor() == -1) {
                root.setLeft(leftRotation(root.getLeft()));
                return rightRotation(root);
            } else if (root.getLeft().getBalanceFactor() == 0 || root.getLeft().getBalanceFactor() == 1) {
                return rightRotation(root);
            }
        }
        return root;
    }

    /**
     * left rotation method for avl tree
     *
     * @param node that will be rotated
     * @return node updated node from the left rotation
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        AVLNode<T> nodeRight = node.getRight();
        node.setRight(nodeRight.getLeft());
        nodeRight.setLeft(node);
        update(node);
        update(nodeRight);
        return nodeRight;
    }

    /**
     * right rotation method for avl tree
     *
     * @param root node that will be rotated
     * @return node updated node from the right rotation
     */
    private AVLNode<T> rightRotation(AVLNode<T> root) {
        AVLNode<T> rootLeft = root.getLeft();
        root.setLeft(rootLeft.getRight());
        rootLeft.setRight(root);
        update(root);
        update(rootLeft);
        return rootLeft;
    }

    /**
     * update method for height and balance factor
     *
     * @param node to update accordingly
     */
    private void update(AVLNode<T> node) {
        int leftHeight = -1;
        int rightHeight = -1;
        if (node.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = node.getRight().getHeight();
        }
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be removed from the AVL");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = removeHelper(data, root, dummy);
        size--;
        return dummy.getData();
    }

    /**
     * recursive helper for remove method
     *
     * @param data the data to remove
     * @param root root node of the avl tree
     * @param dummy dummy node to save removed node
     * @return root with new tree
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> root, AVLNode<T> dummy) {
        if (root == null) {
            throw new NoSuchElementException("Data cannot be found from the avl tree");
        }
        if (root.getData().compareTo(data) > 0) {
            root.setLeft(removeHelper(data, root.getLeft(), dummy));
        } else if (root.getData().compareTo(data) < 0) {
            root.setRight(removeHelper(data, root.getRight(), dummy));
        } else {
            dummy.setData(root.getData());

            if (root.getRight() != null && root.getLeft() == null) {
                // 1 children
                return root.getRight();
            } else if (root.getRight() == null && root.getLeft() != null) {
                // 1 children
                return root.getLeft();
            } else if (root.getRight() == null && root.getLeft() == null) {
                // 0 children
                return null;
            } else {
                // 2 children
                T removedData = root.getData();
                T tmpData = predecessor(root.getLeft()).getData();
                root.setData(tmpData);
                root.setLeft(removeHelper(tmpData, root.getLeft(), dummy));
                dummy.setData(removedData);
            }
        }
        update(root);
        return rotationCheck(root);
    }

    /**
     * Recursive helper to find predecessor in remove method
     *
     * @param root root node of a current node
     * @return node from predecessor method
     */
    private AVLNode<T> predecessor(AVLNode<T> root) {
        if (root.getRight() == null) {
            return root;
        }
        return predecessor(root.getRight());
    }


    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be retrieved");
        }
        if (size == 0) {
            throw new NoSuchElementException("Input data cannot be found from the tree");
        }
        T ans = getHelper(data, root);
        return ans;
    }

    /**
     * recursive helper for get method
     *
     * @param root root node of the current node
     * @param data the data to search for in the tree
     * @return data found from the avl tree
     */
    private T getHelper(T data, AVLNode<T> root) {
        if (root == null) {
            throw new NoSuchElementException("Input data cannot be found from the tree");
        }
        if (root.getData().compareTo(data) > 0) {
            return getHelper(data, root.getLeft());
        } else if (root.getData().compareTo(data) < 0) {
            return getHelper(data, root.getRight());
        } else {
            return root.getData();
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be searched");
        }
        return containsHelper(data, root);
    }

    /**
     * recursive helper method for contains
     *
     * @param root root node of the current node.
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean containsHelper(T data, AVLNode<T> root) {
        if (root == null) {
            return false;
        }
        if (root.getData().compareTo(data) > 0) {
            return containsHelper(data, root.getLeft());
        } else if (root.getData().compareTo(data) < 0) {
            return containsHelper(data, root.getRight());
        } else {
            return true;
        }
    }

    /**
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     *
     * Ex: Given the following AVL
     *
     *                   g
     *                 /   \
     *                e     i
     *               / \   / \
     *              b   f h   n
     *             / \         \
     *            a   c         u
     *
     * wordSearch([b, e, g, i, n]) returns the list [b, e, g, i, n],
     * where each letter in the returned list is from the tree and not from
     * the word array.
     *
     * wordSearch([h, i]) returns the list [h, i], where each letter in the
     * returned list is from the tree and not from the word array.
     *
     * wordSearch([a]) returns the list [a].
     *
     * wordSearch([]) returns an empty list [].
     *
     * wordSearch([h, u, g, e]) throws NoSuchElementException. Although all
     * 4 letters exist in the tree, there is no path that spells 'huge'.
     * The closest we can get is 'hige'.
     *
     * To do this, you must first find the deepest common ancestor of the
     * first and last letter in the word. Then traverse to the first letter
     * while adding letters on the path to the list while preserving the order
     * they appear in the word (consider adding to the front of the list).
     * Finally, traverse to the last letter while adding its ancestor letters to
     * the back of the list. Please note that there is no relationship between
     * the first and last letters, in that they may not belong to the same
     * branch. You will most likely have to split off to traverse the tree when
     * searching for the first and last letters.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you may have to add to the front and
     * back of the list.
     *
     * You will only need to traverse to the deepest common ancestor once.
     * From that node, go to the first and last letter of the word in one
     * traversal each. Failure to do so will result in a efficiency penalty.
     * Validating the path against the word array efficiently after traversing
     * the tree will NOT result in an efficiency penalty.
     *
     * If there exists a path between the first and last letters of the word,
     * there will only be 1 valid path.
     *
     * You may assume that the word will not contain duplicate letters.
     *
     * WARNING: Do not return letters from the passed-in word array!
     * If a path exists, the letters should be retrieved from the tree.
     * Returning any letter from the word array will result in a penalty!
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */
    public List<T> wordSearch(T[] word) {
        if (word == null) {
            throw new IllegalArgumentException("Null array cannot be searched");
        }
        LinkedList<T> ans = new LinkedList<>();
        if (word.length == 0) {
            return ans;
        }
        AVLNode<T> dcaNode = deepestCommon(root, word[0], word[word.length - 1]);
        if (dcaNode.getData().compareTo(word[0]) < 0) {
            // traverse to right first then left
            searchAddFirst(ans, dcaNode.getRight(), word[0]);
            searchAddLast(ans, dcaNode, word[word.length - 1]);
        } else if (dcaNode.getData().compareTo(word[0]) > 0) {
            // traverse to left first then right
            searchAddFirst(ans, dcaNode.getLeft(), word[0]);
            searchAddLast(ans, dcaNode, word[word.length - 1]);
        } else {
            searchAddLast(ans, dcaNode, word[word.length - 1]);
        }
        if (ans.size() != word.length) {
            throw new NoSuchElementException("Path cannot be found from the avl tree");
        }
        for (int i = 0; i < word.length; i++) {
            if (ans.get(i).compareTo(word[i]) != 0) {
                throw new NoSuchElementException("Incorrect path is found");
            }
        }
        return ans;
    }

    /**
     * recursive helper method for deepest common ancestor
     *
     * @param root root node of the current node.
     * @param first the first data from the word array.
     * @param last the last data from the word array
     * @throws java.util.NoSuchElementException if the data from the array is not in the tree
     * @return the DCA node.
     *
     */
    private AVLNode<T> deepestCommon(AVLNode<T> root, T first, T last) {
        if (root == null) {
            throw new NoSuchElementException("The data is not in the avl tree");
        }
        if (root.getData().compareTo(first) > 0 && root.getData().compareTo(last) > 0) {
            // both on left side
            return deepestCommon(root.getLeft(), first, last);
        } else if (root.getData().compareTo(first) < 0 && root.getData().compareTo(last) < 0) {
            // both on right side
            return deepestCommon(root.getRight(), first, last);
        } else {
            return root;
        }
    }

    /**
     * recursive helper method for search and addToFirst to the linked list
     *
     * @param ans linked list to add data
     * @param root root node of the current node.
     * @param data the data to search and traverse until the data is found
     */
    private void searchAddFirst(LinkedList<T> ans, AVLNode<T> root, T data) {
        if (root == null) {
            return;
        }
        if (root.getData().compareTo(data) == 0) {
            ans.addFirst(root.getData());
            return;
        } else if (root.getData().compareTo(data) < 0) {
            ans.addFirst(root.getData());
            searchAddFirst(ans, root.getRight(), data);
        } else if (root.getData().compareTo(data) > 0) {
            ans.addFirst(root.getData());
            searchAddFirst(ans, root.getLeft(), data);
        }
    }

    /**
     * recursive helper method for search and addToLast to the linked list
     *
     * @param ans linked list to add data
     * @param root root node of the current node.
     * @param data the data to search and traverse until the data is found
     */
    private void searchAddLast(LinkedList<T> ans, AVLNode<T> root, T data) {
        if (root == null) {
            return;
        }
        if (root.getData().compareTo(data) == 0) {
            ans.addLast(root.getData());
            return;
        } else if (root.getData().compareTo(data) < 0) {
            ans.addLast(root.getData());
            searchAddLast(ans, root.getRight(), data);
        } else if (root.getData().compareTo(data) > 0) {
            ans.addLast(root.getData());
            searchAddLast(ans, root.getLeft(), data);
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
    public AVLNode<T> getRoot() {
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