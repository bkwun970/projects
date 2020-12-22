import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * This is test for AVL.
 *
 * @author Jeonghoon Lee
 * @version 1.0
 */
public class AVLJeonghoonTest {

    private static final int TIMEOUT = 200;
    private AVL<Integer> tree;

    @Before
    public void setup() {
        tree = new AVL<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testConstructor() {

        /*
             Height
             max(left node’s height, right node’s height) + 1
             where the height of a leaf node is 0 and the
             heights of its null children are -1.
             (From HW pdf pg4)

             BF (Balance Factor)
             Calculated by subtracting the height of the left subtree
             by the height of right subtree.
             (From TreesAVL.pdf pg6)
         */



        /*
              10

              10 : height = 0, BF = (-1) - (-1) = 0
         */
        List<Integer> list = new ArrayList<>();
        list.add(10);
        tree = new AVL<>(list);

        AVLNode<Integer> node = tree.getRoot();

        assertEquals(1, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(0, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        /*
               10
              /
             3

             10 : height = 1, BF = (0) - (-1) = 1
             3 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(3); // list == {10, 3}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(2, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 3, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        /*
              10 <-- BF = (1) - (-1) = 2
              /
             3
            /
           1

           Right rotate will occur.

               3
              / \
             1   10

             3 : height = 1, BF = (0) - (0) = 0
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(1); // list == {10, 3, 1}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(3, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
               3
              / \
             1   10
                  \
                   15

             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 1, BF = (-1) - (0) = -1
             15 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(15); // list == {10, 3, 1, 15}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(4, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
               3
              / \
             1   10 <- BF = (-1) - (1) = -2
                  \
                   15
                    \
                     20

           left rotation will occur.

               3
              / \
             1   15
                 /\
                10 20

             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             15 : height = 1, BF = (0) - (0) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
             20 : height = 0, BF = (-1) - (-1) = 0
         */

        list.add(20); // list == {10, 3, 1, 15, 20}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //Rotation Test

        /*
        LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B

               38
              / \
             37  49
                 /\
                48 85

             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 1, BF = (0) - (0) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(38);
        list.add(37);
        list.add(49);
        list.add(48);
        list.add(85); //list = {38, 37, 49, 48, 85}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 48, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
        100 == D

               38
              / \
             37  49
                 /\
                48 85
                    \
                    100 <-- add

                    LEFT ROTATION OCCUR

                49
               / \
              38  85
             / \    \
            37 48   100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(100); //list = {38, 37, 49, 48, 85, 100}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg17)
        100 == D
        50  == C

               85
              /  \
             49  100
            / \
           38  50



             85 : height = 2, BF = (1) - (0) = 1
             49 : height = 1, BF = (0) - (0) = 0
             38 : height = 0, BF = (-1) - (-1) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(85);
        list.add(49);
        list.add(100);
        list.add(38);
        list.add(50); //list = {85, 49, 100, 38, 50}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 50, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        100 == D
        50 == C
        37 == A

               85
              /  \
             49  100
            / \
           38  50
          /
         37 <--will add

                    RIGHT ROTATION OCCUR

                49
               / \
              38  85
             /    /\
            37  50 100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(37); //list = {85, 49, 100, 38, 50, 37}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg20)
        37  == A
        100 == D

               85
              /  \
             38  100
            / \
           37  49



             85 : height = 2, BF = (1) - (0) = 1
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(85);
        list.add(38);
        list.add(100);
        list.add(37);
        list.add(49); //list = {85, 38, 100, 37, 49}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        50  == C

               85
              /  \
             38  100
            / \
           37  49
                \
                50 <--will add

          LEFT-RIGHT ROTATION OCCUR

                49
               / \
              38  85
             /    /\
            37  50 100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(50); //list = {85, 38, 100, 37, 49, 50}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
        RIGHT-LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg23)
        37  == A
        100 == D

               38
              /  \
             37  85
                 /\
               49 100



             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        list = new ArrayList<>();
        list.add(38);
        list.add(37);
        list.add(85);
        list.add(49);
        list.add(100); //list = {38, 37, 85, 49, 100}
        tree = new AVL<>(list);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT-LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        48  == B

               38
              /  \
             37  85
                 /\
               49 100
               /
              48 <-- will add

          RIGHT-LEFT ROTATION OCCUR

                49
               / \
              38  85
             /\    \
            37 48  100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        list.add(48); //list = {38, 37, 85, 49, 100, 48}
        tree = new AVL<>(list);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

    }

    @Test(timeout = TIMEOUT)
    public void testConstructorException() {
        try {
            tree = new AVL<>(null);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            List<Integer> list = new ArrayList<>();
            list.add(10);
            list.add(null);
            list.add(20);
            tree = new AVL<>(list);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testAdd() {

        /*
             Height
             max(left node’s height, right node’s height) + 1
             where the height of a leaf node is 0 and the
             heights of its null children are -1.
             (From HW pdf pg4)

             BF (Balance Factor)
             Calculated by subtracting the height of the left subtree
             by the height of right subtree.
             (From TreesAVL.pdf pg6)
         */



        /*
              10

              10 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(10);

        AVLNode<Integer> node = tree.getRoot();

        assertEquals(1, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(0, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        /*
               10
              /
             3

             10 : height = 1, BF = (0) - (-1) = 1
             3 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(3);

        node = tree.getRoot();

        assertEquals(2, tree.size());
        assertEquals((Integer) 10, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 3, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        /*
              10 <-- BF = (1) - (-1) = 2
              /
             3
            /
           1

           Right rotate will occur.

               3
              / \
             1   10

             3 : height = 1, BF = (0) - (0) = 0
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(1);

        node = tree.getRoot();

        assertEquals(3, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
               3
              / \
             1   10
                  \
                   15

             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             10 : height = 1, BF = (-1) - (0) = -1
             15 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(15);

        node = tree.getRoot();

        assertEquals(4, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
               3
              / \
             1   10 <- BF = (-1) - (1) = -2
                  \
                   15
                    \
                     20

           left rotation will occur.

               3
              / \
             1   15
                 /\
                10 20

             3 : height = 2, BF = (0) - (1) = -1
             1 : height = 0, BF = (-1) - (-1) = 0
             15 : height = 1, BF = (0) - (0) = 0
             10 : height = 0, BF = (-1) - (-1) = 0
             20 : height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(20);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //If the data is already in the tree. (15 already exist)
        tree.add(15);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        //If the data is already in the tree. (20, already exist)
        tree.add(20);

        node = tree.getRoot();

        assertEquals(5, tree.size());
        assertEquals((Integer) 3, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 1, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 15, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 10, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 20, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        //Rotation Test

        /*
        LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B

               38
              / \
             37  49
                 /\
                48 85

             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 1, BF = (0) - (0) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(38);
        tree.add(37);
        tree.add(49);
        tree.add(48);
        tree.add(85);

        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 48, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37 == A
        48 == B
        100 == D

               38
              / \
             37  49
                 /\
                48 85
                    \
                    100 <-- add

                    LEFT ROTATION OCCUR

                49
               / \
              38  85
             / \    \
            37 48   100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(100);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg17)
        100 == D
        50  == C

               85
              /  \
             49  100
            / \
           38  50



             85 : height = 2, BF = (1) - (0) = 1
             49 : height = 1, BF = (0) - (0) = 0
             38 : height = 0, BF = (-1) - (-1) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(85);
        tree.add(49);
        tree.add(100);
        tree.add(38);
        tree.add(50);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 50, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        100 == D
        50 == C
        37 == A

               85
              /  \
             49  100
            / \
           38  50
          /
         37 <--will add

                    RIGHT ROTATION OCCUR

                49
               / \
              38  85
             /    /\
            37  50 100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(37);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg20)
        37  == A
        100 == D

               85
              /  \
             38  100
            / \
           37  49



             85 : height = 2, BF = (1) - (0) = 1
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(85);
        tree.add(38);
        tree.add(100);
        tree.add(37);
        tree.add(49);
        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 85, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(1, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 49, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getData());
        assertEquals(0, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());


        /*
        LEFT-RIGHT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        50  == C

               85
              /  \
             38  100
            / \
           37  49
                \
                50 <--will add

          LEFT-RIGHT ROTATION OCCUR

                49
               / \
              38  85
             /    /\
            37  50 100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (-1) = 1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             50 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(50);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(1, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 50, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());

        /*
        RIGHT-LEFT ROTATION INITIAL SETTING
        this is basically same as TreeAVL.pdf example (pg23)
        37  == A
        100 == D

               38
              /  \
             37  85
                 /\
               49 100



             38 : height = 2, BF = (0) - (1) = -1
             37 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (0) - (0) = 0
             49 : height = 0, BF = (-1) - (-1) = 0
             100: height = 0, BF = (-1) - (-1) = 0
         */
        tree = new AVL<>();
        tree.add(38);
        tree.add(37);
        tree.add(85);
        tree.add(49);
        tree.add(100);

        node = tree.getRoot();
        //Check setting before the test.

        assertEquals(5, tree.size());
        assertEquals((Integer) 38, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getData());
        assertEquals(0, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(0, node.getRight().getBalanceFactor());

        assertEquals((Integer) 49, node.getRight().getLeft().getData());
        assertEquals(0, node.getRight().getLeft().getHeight());
        assertEquals(0, node.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());


        /*
        RIGHT-LEFT ROTATION
        this is basically same as TreeAVL.pdf example (pg14)
        37  == A
        100 == D
        48  == B

               38
              /  \
             37  85
                 /\
               49 100
               /
              48 <-- will add

          RIGHT-LEFT ROTATION OCCUR

                49
               / \
              38  85
             /\    \
            37 48  100

             49 : height = 2, BF = (1) - (1) = 0
             38 : height = 1, BF = (0) - (0) = 0
             37 : height = 0, BF = (-1) - (-1) = 0
             48 : height = 0, BF = (-1) - (-1) = 0
             85 : height = 1, BF = (-1) - (0) = -1
             100: height = 0, BF = (-1) - (-1) = 0
         */

        tree.add(48);
        node = tree.getRoot();

        assertEquals(6, tree.size());
        assertEquals((Integer) 49, node.getData());
        assertEquals(2, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        assertEquals((Integer) 38, node.getLeft().getData());
        assertEquals(1, node.getLeft().getHeight());
        assertEquals(0, node.getLeft().getBalanceFactor());

        assertEquals((Integer) 37, node.getLeft().getLeft().getData());
        assertEquals(0, node.getLeft().getLeft().getHeight());
        assertEquals(0, node.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 48, node.getLeft().getRight().getData());
        assertEquals(0, node.getLeft().getRight().getHeight());
        assertEquals(0, node.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 85, node.getRight().getData());
        assertEquals(1, node.getRight().getHeight());
        assertEquals(-1, node.getRight().getBalanceFactor());

        assertEquals((Integer) 100, node.getRight().getRight().getData());
        assertEquals(0, node.getRight().getRight().getHeight());
        assertEquals(0, node.getRight().getRight().getBalanceFactor());
    }

    @Test(timeout = TIMEOUT)
    public void testAddException() {
        try {
            tree.add(null);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    @Test(timeout = TIMEOUT)
    public void testRemove() {


        //ROTATION CHECK (REMOVE) [RIGHT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /  \
            25   75
           / \   / \
          12 37 62 100
         /\
        6 18

        */
        Integer i50 = 50;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i12 = 12;
        Integer i37 = 37;
        Integer i62 = 62;
        Integer i100 = 100;
        Integer i6 = 6;
        Integer i18 = 18;
        tree.add(i50);
        tree.add(i25);
        tree.add(i75);
        tree.add(i12);
        tree.add(i37);
        tree.add(i62);
        tree.add(i100);
        tree.add(i6);
        tree.add(i18);
        //ASSUME THAT YOUR ADD METHOD WORKS PROPERLY.

        /*
              50
             /  \
            25   75
           / \   / \
          12(37)62 100
         /\
        6 18

        -->

              50
             /  \
 BF = 2 --> 25   75
           /     / \
BF = 0--> 12    62 100
         /\
        6 18

        Right rotation will occur
        "If the signs of the balance factors are the same or 'the child has BF 0',
        then do a single rotation. If the signs are opposite, do a double rotation."
        (PIAZZA POST @1162)


       -->
              50
             /  \
            12   75
           /\    / \
          6 25  62 100
            /
           18

        */

        assertSame(i37, tree.remove(37));
        assertEquals(8, tree.size());
        AVLNode<Integer> root = tree.getRoot();

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 62, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());




        //ROTATION CHECK (REMOVE) [LEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /  \
            12   75
           /\    / \
          6 25  62 100
            /
           18

         */

        Integer i99 = 99;
        Integer i101 = 101;
        tree.add(i99);
        tree.add(i101);

        //ASSUME THAT YOUR ADD METHOD WORKS PROPERLY.

        /*
              50
             /  \
            12   75
           /\   /  \
          6 25 (62) 100
            /        /\
           18      99 101

           we will remove (62)

         -->

              50
             /  \
            12   75 <-- BF = -2
           /\      \
          6 25      100 <-- BF = 0
            /        /\
           18      99 101


           Left Rotate will occur


           -->


              50
             /   \
            12    100
           /\     / \
          6 25   75 101
            /      \
           18      99

         */

        assertSame(i62, tree.remove(62));
        root = tree.getRoot();
        assertEquals(9, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(1, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(-1, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 101, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());



        //ROTATION CHECK (REMOVE) [LEFTRIGHT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
              50
             /   \
            12    100
           /\     / \
          6 25   75 (101)
            /      \
           18      99


           we will remove (101)

         -->

              50
             /   \
            12    100 <-- BF = 2
           /\     /
          6 25   75 <-- BF = -1
            /      \
           18      99 <-- BF = 0


           Left Right Rotate will occur


           -->


               50
             /   \
            12    99
           /\     / \
          6 25   75 100
            /
           18

         */

        assertSame(i101, tree.remove(101));
        root = tree.getRoot();
        assertEquals(8, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 6, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());


        //ROTATION CHECK (REMOVE) [RIGHTLEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
               50
             /   \
            12    99
           /\     / \
         (6) 25   75 100
            /
           18


           we will remove (6)

         -->

               50
             /   \
 BF = -2 -->12    99
             \    / \
   BF = 1 -->25  75 100
             /
 BF = 0 -->18


           Left Right Rotate will occur


           -->


               50
             /   \
           18     99
           /\     / \
         12 25   75 100


         */

        assertSame(i6, tree.remove(6));
        root = tree.getRoot();
        assertEquals(7, tree.size());

        assertEquals((Integer) 50, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());



        // MAKING HARDER
        // PREDECESSOR TEST

        //ROTATION CHECK (REMOVE) [RIGHTLEFT ROTATION]
        //WILL REMOVE CHILD NODE FOR ROTATE TEST

        /*
               50
             /   \
           18     99
           /\     / \
         12 25   75 100

         */

        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        //ASSUME THAT ADD METHOD WORKS PROPERLY

         /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */




         /*

               (50)
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


      predecessor = 26
      move 26 to top node.

                26
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   /     / \   \
      11 13 24    74 76  105


         */


        assertSame(i50, tree.remove(50));
        root = tree.getRoot();
        assertEquals(13, tree.size());

        assertEquals((Integer) 26, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 25, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 24, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());




         /*

               (26)
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   /     / \   \
      11 13 24    74 76  105


      predecessor = 25
      move 25 to top node.

                25
              /    \
            18      99
          /  \      / \
        12   24    75  100
       / \         / \   \
      11 13       74 76  105


         */


        assertSame(i26, tree.remove(26));
        root = tree.getRoot();
        assertEquals(12, tree.size());

        assertEquals((Integer) 25, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getLeft().getData());
        assertEquals(1, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 24, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*

               (25)
              /    \
            18      99
          /  \      / \
        12   24    75  100
       / \         / \   \
      11 13       74 76  105


      predecessor = 24
      move 24 to top node.

                24
              /    \
            18      99
           /        / \
         12        75  100
        / \        / \   \
       11 13      74 76  105

       after rotation

                24
              /    \
            12      99
           / \      / \
         11  18    75  100
             /     / \   \
           13     74 76  105


         */


        assertSame(i25, tree.remove(25));
        root = tree.getRoot();
        assertEquals(11, tree.size());

        assertEquals((Integer) 24, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 18, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*

               (24)
              /    \
            12      99
           / \      / \
         11  18    75  100
             /     / \   \
           13     74 76  105


      predecessor = 18
      move 18 to top node.

                18
              /    \
            12      99
           / \      / \
         11  13    75  100
                   / \   \
                  74 76  105

         */


        assertSame(i24, tree.remove(24));
        root = tree.getRoot();
        assertEquals(10, tree.size());


        assertEquals((Integer) 18, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 13, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());


        /*

               (18)
              /    \
            12      99
           / \      / \
         11  13    75  100
                   / \   \
                  74 76  105


      predecessor = 13
      move 13 to top node.

                13
              /    \
            12      99
           /        / \
         11        75  100
                   / \   \
                  74 76  105

         */


        assertSame(i18, tree.remove(18));
        root = tree.getRoot();
        assertEquals(9, tree.size());


        assertEquals((Integer) 13, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 99, root.getRight().getData());
        assertEquals(2, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getRight().getLeft().getData());
        assertEquals(1, root.getRight().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getRight().getHeight());
        assertEquals(-1, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getRight().getLeft().getLeft().getData());
        assertEquals(0, root.getRight().getLeft().getLeft().getHeight());
        assertEquals(0, root.getRight().getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getRight().getLeft().getRight().getData());
        assertEquals(0, root.getRight().getLeft().getRight().getHeight());
        assertEquals(0, root.getRight().getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getRight().getBalanceFactor());



        /*

               (13)
              /    \
            12      99
           /        / \
         11        75  100
                   / \   \
                  74 76  105


      predecessor = 12
      move 12 to top node.

                12
              /    \
            11      99
                    / \
                   75  100
                   / \   \
                  74 76  105

           ->

                12  99
                / \ / \
               11  75  100
                   / \   \
                  74 76  105

           ->
                   99
                  /  \
                12   100
                / \    \
               11  75  105
                   / \
                  74 76

         */


        assertSame(i13, tree.remove(13));
        root = tree.getRoot();
        assertEquals(8, tree.size());

        assertEquals((Integer) 99, root.getData());
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals((Integer) 12, root.getLeft().getData());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());

        assertEquals((Integer) 100, root.getRight().getData());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(-1, root.getRight().getBalanceFactor());

        assertEquals((Integer) 11, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getBalanceFactor());

        assertEquals((Integer) 75, root.getLeft().getRight().getData());
        assertEquals(1, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());

        assertEquals((Integer) 105, root.getRight().getRight().getData());
        assertEquals(0, root.getRight().getRight().getHeight());
        assertEquals(0, root.getRight().getRight().getBalanceFactor());

        assertEquals((Integer) 74, root.getLeft().getRight().getLeft().getData());
        assertEquals(0, root.getLeft().getRight().getLeft().getHeight());
        assertEquals(0, root.getLeft().getRight().getLeft().getBalanceFactor());

        assertEquals((Integer) 76, root.getLeft().getRight().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getRight().getBalanceFactor());


    }

    @Test(timeout = TIMEOUT)
    public void testRemoveException() {

        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.remove(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            tree.remove(i999);
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {

        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        assertEquals(14, tree.size());

        assertSame(i50, tree.get(50));
        assertSame(i18, tree.get(18));
        assertSame(i99, tree.get(99));
        assertSame(i12, tree.get(12));
        assertSame(i25, tree.get(25));
        assertSame(i75, tree.get(75));
        assertSame(i100, tree.get(100));
        assertSame(i11, tree.get(11));
        assertSame(i13, tree.get(13));
        assertSame(i24, tree.get(24));
        assertSame(i26, tree.get(26));
        assertSame(i74, tree.get(74));
        assertSame(i76, tree.get(76));
        assertSame(i105, tree.get(105));
    }

    @Test(timeout = TIMEOUT)
    public void testGetException() {

        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.get(null);
            fail();
        } catch (IllegalArgumentException e) {

        }

        try {
            tree.get(i999);
            fail();
        } catch (NoSuchElementException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testContains() {

        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        assertEquals(14, tree.size());

        assertTrue(tree.contains(50));
        assertTrue(tree.contains(18));
        assertTrue(tree.contains(99));
        assertTrue(tree.contains(12));
        assertTrue(tree.contains(25));
        assertTrue(tree.contains(75));
        assertTrue(tree.contains(100));
        assertTrue(tree.contains(11));
        assertTrue(tree.contains(13));
        assertTrue(tree.contains(24));
        assertTrue(tree.contains(26));
        assertTrue(tree.contains(74));
        assertTrue(tree.contains(76));
        assertTrue(tree.contains(105));
        assertFalse(tree.contains(999));
    }

    @Test(timeout = TIMEOUT)
    public void testContainsException() {

        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;
        Integer i999 = 999;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        try {
            tree.contains(null);
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    @Test(timeout = TIMEOUT)
    public void testHeight() {

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        assertEquals(0, tree.height());
        /*
                50
         */
        tree.add(i18);
        tree.add(i99);
        assertEquals(1, tree.height());
        /*

                50
              /    \
            18      99


         */
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        assertEquals(2, tree.height());
        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100


         */
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);
        assertEquals(3, tree.height());
        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {

        /*

                50
              /    \
            18      99
          /  \      / \
        12   25    75  100
       / \   / \   / \   \
      11 13 24 26 74 76  105


         */

        Integer i50 = 50;
        Integer i18 = 18;
        Integer i99 = 99;
        Integer i12 = 12;
        Integer i25 = 25;
        Integer i75 = 75;
        Integer i100 = 100;
        Integer i11 = 11;
        Integer i13 = 13;
        Integer i24 = 24;
        Integer i26 = 26;
        Integer i74 = 74;
        Integer i76 = 76;
        Integer i105 = 105;

        tree.add(i50);
        tree.add(i18);
        tree.add(i99);
        tree.add(i12);
        tree.add(i25);
        tree.add(i75);
        tree.add(i100);
        tree.add(i11);
        tree.add(i13);
        tree.add(i24);
        tree.add(i26);
        tree.add(i74);
        tree.add(i76);
        tree.add(i105);

        tree.clear();
        assertEquals(0, tree.size());
        assertNull(tree.getRoot());
    }

    @Test(timeout = TIMEOUT)
    public void testWordSearch() {

        AVL<String> letterTree = new AVL<>();

        String a = new String("a");
        String b = new String("b");
        String c = new String("c");
        String d = new String("d");
        String e = new String("e");
        String f = new String("f");
        String g = new String("g");
        String h = new String("h");
        String i = new String("i");
        String j = new String("j");
        String k = new String("k");
        String l = new String("l");
        String m = new String("m");
        String n = new String("n");
        String o = new String("o");
        String p = new String("p");
        String q = new String("q");
        String r = new String("r");
        String s = new String("s");
        String t = new String("t");
        String u = new String("u");
        String v = new String("v");
        String w = new String("w");
        String x = new String("x");
        String y = new String("y");
        String z = new String("z");
        letterTree.add(p);
        letterTree.add(h);
        letterTree.add(w);
        letterTree.add(d);
        letterTree.add(l);
        letterTree.add(t);
        letterTree.add(y);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(j);
        letterTree.add(n);
        letterTree.add(r);
        letterTree.add(v);
        letterTree.add(x);
        letterTree.add(z);
        letterTree.add(a);
        letterTree.add(c);
        letterTree.add(e);
        letterTree.add(g);
        letterTree.add(i);
        letterTree.add(k);
        letterTree.add(m);
        letterTree.add(o);
        letterTree.add(q);
        letterTree.add(s);
        letterTree.add(u);


        /*


                       p
                   /      \
                /            \
              /                \
             h                  w
           /   \             /     \
          d      l           t      y
         / \    /  \       /  \   /  \
        b   f   j   n     r   v  x    z
       /\  /\   /\  /\   /\  /
      a c  e g  i k m o q s  u
        */

        assertEquals(26, letterTree.size());

        String[] word = new String[] {"p", "h", "d", "b", "a"};
        List<String> path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(p, path.get(0));
        assertSame(h, path.get(1));
        assertSame(d, path.get(2));
        assertSame(b, path.get(3));
        assertSame(a, path.get(4));

        word = new String[] {"a", "b", "d", "h", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(a, path.get(0));
        assertSame(b, path.get(1));
        assertSame(d, path.get(2));
        assertSame(h, path.get(3));
        assertSame(p, path.get(4));

        word = new String[] {"p", "w", "y", "z"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(p, path.get(0));
        assertSame(w, path.get(1));
        assertSame(y, path.get(2));
        assertSame(z, path.get(3));

        word = new String[] {"z", "y", "w", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(z, path.get(0));
        assertSame(y, path.get(1));
        assertSame(w, path.get(2));
        assertSame(p, path.get(3));

        word = new String[] {"d", "h", "p", "w"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(d, path.get(0));
        assertSame(h, path.get(1));
        assertSame(p, path.get(2));
        assertSame(w, path.get(3));

        word = new String[] {"y", "w", "p", "h"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(y, path.get(0));
        assertSame(w, path.get(1));
        assertSame(p, path.get(2));
        assertSame(h, path.get(3));

        word = new String[] {"p", "h", "l", "j", "k"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(p, path.get(0));
        assertSame(h, path.get(1));
        assertSame(l, path.get(2));
        assertSame(j, path.get(3));
        assertSame(k, path.get(4));

        word = new String[] {"k", "j", "l", "h", "p"};
        path = letterTree.wordSearch(word);
        assertEquals(5, path.size());

        assertSame(k, path.get(0));
        assertSame(j, path.get(1));
        assertSame(l, path.get(2));
        assertSame(h, path.get(3));
        assertSame(p, path.get(4));

        word = new String[] {"j", "l", "n", "m"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(j, path.get(0));
        assertSame(l, path.get(1));
        assertSame(n, path.get(2));
        assertSame(m, path.get(3));

        word = new String[] {"m", "n", "l", "j"};
        path = letterTree.wordSearch(word);
        assertEquals(4, path.size());

        assertSame(m, path.get(0));
        assertSame(n, path.get(1));
        assertSame(l, path.get(2));
        assertSame(j, path.get(3));

        word = new String[] {"m"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        assertSame(m, path.get(0));

        word = new String[] {"p"};
        path = letterTree.wordSearch(word);
        assertEquals(1, path.size());

        assertSame(p, path.get(0));

        word = new String[] {};
        path = letterTree.wordSearch(word);
        assertEquals(0, path.size());
        // This should not throw any Exception
        // This does not means null.
        // wordSearch([]) returns an empty list [].
        // From JavaDOCS.


    }

    @Test(timeout = TIMEOUT)
    public void testWordSearchException() {

        AVL<String> letterTree = new AVL<>();

        String a = new String("a");
        String b = new String("b");
        String c = new String("c");
        String d = new String("d");
        String e = new String("e");
        String f = new String("f");
        String g = new String("g");
        String h = new String("h");
        String i = new String("i");
        String j = new String("j");
        String k = new String("k");
        String l = new String("l");
        String m = new String("m");
        String n = new String("n");
        String o = new String("o");
        String p = new String("p");
        String q = new String("q");
        String r = new String("r");
        String s = new String("s");
        String t = new String("t");
        String u = new String("u");
        String v = new String("v");
        String w = new String("w");
        String x = new String("x");
        String y = new String("y");
        String z = new String("z");
        letterTree.add(p);
        letterTree.add(h);
        letterTree.add(w);
        letterTree.add(d);
        letterTree.add(l);
        letterTree.add(t);
        letterTree.add(y);
        letterTree.add(b);
        letterTree.add(f);
        letterTree.add(j);
        letterTree.add(n);
        letterTree.add(r);
        letterTree.add(v);
        letterTree.add(x);
        letterTree.add(z);
        letterTree.add(c);
        letterTree.add(e);
        letterTree.add(g);
        letterTree.add(i);
        letterTree.add(k);
        letterTree.add(m);
        letterTree.add(o);
        letterTree.add(q);
        letterTree.add(s);


        /*


                       p
                   /      \
                /            \
              /                \
             h                  w
           /   \             /     \
          d      l           t      y
         / \    /  \       /  \   /  \
        b   f   j   n     r   v  x    z
        \  /\   /\  /\   /\
        c  e g  i k m o q s

        */

        assertEquals(24, letterTree.size());

        String[] word;
        List<String> path;

        try {
            word = null;
            path = letterTree.wordSearch(word);
            fail();
        } catch (IllegalArgumentException ee) {

        }

        try {
            word = new String[] {"o", "x"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"o", "s", "q", "n"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"a", "u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }


        try {
            word = new String[] {"a"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"t", "w", "i", "h"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"d", "h", "l", "j", "a", "b", "k"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

        try {
            word = new String[] {"r", "t", "v", "u"};
            path = letterTree.wordSearch(word);
            fail();

        } catch (NoSuchElementException ee) {

        }

    }
}
