import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to "
                    + "an AVL");
        }
        for (T curr : data) {
            if (curr == null) {
                throw new IllegalArgumentException("Cannot add null data to "
                        + "an AVL");
            } else {
                add(curr);
            }
        }
    }

    /**
     * Add the data to the AVL. Start by adding it as a leaf and rotate the tree
     * as needed. Should traverse the tree to find the appropriate location.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data "
                    + "to an AVL");
        } else if (root == null) {
            size++;
            root = new AVLNode<>(data);
            root.setHeight(0);
            root.setBalanceFactor(0);
        } else {
            root = addTraverse(root, data);
            size++;

        }
    }

    /**
     * Helper for add
     * Search the tree to add data
     *
     * @param curr the current node
     * @param data data to remove
     * @return the current node
     */
    private AVLNode<T> addTraverse(AVLNode<T> curr, T data) {
        if (curr == null) {
            return (new AVLNode<>(data));
        }
        if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(addTraverse(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(addTraverse(curr.getRight(), data));
        } else {
            return curr;
        }
        updateBF(curr);
        //Checks if left is null
        if (curr.getLeft() == null) {
            if (curr.getRight() == null) {
                //Can do this because if they're both null, they're both heights
                //of -1
                curr.setHeight(0);
            } else {
                //Can do this because right will now be the max, there's no
                //situation in an AVL where height is lower than -1
                curr.setHeight(curr.getRight().getHeight() + 1);
            }
        } else if (curr.getRight() == null) {
            curr.setHeight(curr.getLeft().getHeight() + 1);
        } else {
            curr.setHeight(Math.max(curr.getLeft().getHeight(),
                    curr.getRight().getHeight()) + 1);
        }

        curr = rotations(curr);
        return curr;
    }

    /**
     * Checks balance factors to see if rotations are needed
     *
     * @param curr the current node
     * @return the current node
     */
    private AVLNode<T> rotations(AVLNode<T> curr) {
        if (Math.abs(curr.getBalanceFactor()) > 1) {
            if (curr.getBalanceFactor() > 0) {
                if (curr.getLeft().getBalanceFactor() < 0) {
                    curr.setLeft(toTheLeft(curr.getLeft()));
                    //left rotation on node.left
                    curr = toTheRight(curr);
                    //right rotation
                } else {
                    curr = toTheRight(curr);
                    //right rotation
                }
            } else if (curr.getBalanceFactor() < 0) {
                if (curr.getRight().getBalanceFactor() > 0) {
                    curr.setRight(toTheRight(curr.getRight()));
                    //right rotation on node.right
                    curr = toTheLeft(curr);
                    //left rotation
                } else {
                    curr = toTheLeft(curr);
                    //left rotation
                }
            }
            return curr;
        }
        return curr;
    }

    /**
     * rotation to the left
     *
     * @param curr the current node
     * @return the node below the current node
     */
    private AVLNode<T> toTheLeft(AVLNode<T> curr) {
        AVLNode<T> below = curr.getRight();
        curr.setRight(below.getLeft());
        below.setLeft(curr);
        updateBF(curr);
        updateBF(below);
        return below;
    }

    /**
     * rotation to the right
     *
     * @param curr the current node
     * @return the node below the current node
     */
    private AVLNode<T> toTheRight(AVLNode<T> curr) {
        AVLNode<T> below = curr.getLeft();
        curr.setLeft(below.getRight());
        below.setRight(curr);
        updateBF(curr);
        updateBF(below);
        return below;
    }

    /**
     * Updates balance factors of nodes
     *
     * @param curr the current node
     */
    private void updateBF(AVLNode<T> curr) {
        int leftHeight;
        int rightHeight;

        if (curr.getLeft() == null) {
            leftHeight = -1;
        } else {
            leftHeight = curr.getLeft().getHeight();
        }
        if (curr.getRight() == null) {
            rightHeight = -1;
        } else {
            rightHeight = curr.getRight().getHeight();
        }

        curr.setHeight(Math.max(leftHeight, rightHeight) + 1);
        curr.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor.
     * You must use recursion to find and remove the predecessor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be stored"
                    + "in an AVL, therefore you cannot remove null data from an"
                    + " AVL");
        }
        if (size == 0) {
            throw new NoSuchElementException("Data is not found, AVL is "
                    + "empty");
        }
        if (size == 1) {
            AVLNode<T> dR = root;
            clear();
            return dR.getData();
        } else {
            AVLNode<T> dR = new AVLNode<>(null);
            removeHelper(root, data, dR);
            size--;
            if (dR.getData() == null) {
                throw new NoSuchElementException(data.toString() + " is not "
                        + "found in the AVL");
            }
            return dR.getData();
        }
    }

    /**
     * Helper for remove
     * Searches the tree to find the spot to remove
     * Also calls for rotations and changes height and balance factor
     *
     * @param curr the current node
     * @param data data to remove
     * @param dataR the data removed
     * @return the current node
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data,
                                    AVLNode<T> dataR) {
        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(removeHelper(curr.getLeft(), data, dataR));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(removeHelper(curr.getRight(), data, dataR));
        } else {
            dataR.setData(curr.getData());
            if ((curr.getRight() == null) && (curr.getLeft() == null)) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                AVLNode<T> below = pred(curr.getLeft());
                curr.setData(below.getData());
                return curr;
            }
        }
        if (curr.getLeft() == null) {
            if (curr.getRight() == null) {
                //Can do this because if they're both null, they're both heights
                //of -1
                curr.setHeight(0);
            } else {
                //Can do this because right will now be the max, there's no
                //situation in an AVL where height is lower than -1
                curr.setHeight(curr.getRight().getHeight() + 1);
            }
        } else if (curr.getRight() == null) {
            curr.setHeight(curr.getLeft().getHeight() + 1);
        } else {
            curr.setHeight(Math.max(curr.getLeft().getHeight(),
                    curr.getRight().getHeight()) + 1);
        }

        rotations(curr);

        return dataR;
    }

    /**
     * Search for the predecessor
     *
     * @param curr the current node
     * @return the predecessor node to the current node
     */
    private AVLNode<T> pred(AVLNode<T> curr) {
        if (curr.getRight() != null) {
            return pred(curr.getRight());
        }
        return curr;
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find null data in"
                    + " an AVL");
        }
        AVLNode<T> foundData = search(root, data);
        if (foundData == null) {
            throw new NoSuchElementException(data.toString() + "is not "
                    + "found in the AVL");
        }
        return foundData.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for"
                    + " null data in an AVL");
        }
        return search(root, data) != null;
    }

    /**
     * Helper for get and contains
     * Search the tree to find if the data is in the tree
     *
     * @param curr the current node
     * @param data data to remove
     * @return the current node
     */
    private AVLNode<T> search(AVLNode<T> curr, T data) {
        if (curr == null) {
            return null;
        } else if (curr.getData().compareTo(data) < 0) {
            return search(curr.getRight(), data);
        } else if (curr.getData().compareTo(data) > 0) {
            return search(curr.getLeft(), data);
        } else {
            return curr;
        }
    }

    /**
     * Returns the data in the deepest node. If there are more than one node
     * with the same deepest depth, return the right most node with the
     * deepest depth.
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (root == null) {
            return null;
        }
        if (size == 1) {
            return root.getData();
        }
        return deepNodeHelp(root).getData();
    }

    /**
     * Helper for maxDeepestNode
     * Search the tree to find the deepest node
     *
     * @param curr the current node
     * @return the deepest node
     */
    private AVLNode<T> deepNodeHelp(AVLNode<T> curr) {
        if (curr.getRight() == null && curr.getLeft() == null) {
            return curr;
        }
        if (curr.getLeft() == null) {
            return deepNodeHelp(curr.getRight());
        } else if (curr.getRight() == null) {
            return deepNodeHelp(curr.getLeft());
        } else {
            if (curr.getRight().getHeight() < curr.getLeft().getHeight()) {
                return deepNodeHelp(curr.getLeft());
            } else if (curr.getRight().getHeight()
                    > curr.getLeft().getHeight()) {
                return deepNodeHelp(curr.getRight());
            } else {
                return deepNodeHelp(curr.getRight());
            }
        }
    }

    /**
     * Returns the data of the deepest common ancestor between two nodes with
     * the given data. The deepest common ancestor is the lowest node (i.e.
     * deepest) node that has both data1 and data2 as descendants.
     * If the data are the same, the deepest common ancestor is simply the node
     * that contains the data. You may not assume data1 < data2.
     * (think carefully: should you use value equality or reference equality?).
     *
     * Must run in O(log n) for all cases
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * deepestCommonAncestor(3, 1): 2
     *
     * Example
     * Tree:
     *           3
     *        /    \
     *       1      4
     *      / \
     *     0   2
     * deepestCommonAncestor(0, 2): 1
     *
     * @param data1 the first data
     * @param data2 the second data
     * @throws java.lang.IllegalArgumentException if one or more of the data
     *          are null
     * @throws java.util.NoSuchElementException if one or more of the data are
     *          not in the tree
     * @return the data of the deepest common ancestor
     */
    public T deepestCommonAncestor(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Cannot search with null "
                    + "data in an AVL");
        }
        if (!contains(data1)) {
            throw new NoSuchElementException(data1.toString() + " is not"
                    + " in the AVL");
        }
        if (!contains(data2)) {
            throw new NoSuchElementException(data2.toString() + " is not"
                    + " in the AVL");
        }
        if (data1 == data2) {
            return data1;
        }
        //recursive call comparing the data
        AVLNode<T> ancestor = searchAncestor(root, data1, data2);
        if (ancestor == null) {
            throw new NoSuchElementException("The data points have no"
                    + " ancestor");
        }
        return ancestor.getData();

    }

    /**
     * Helper for deepestCommonAncestor
     * Search the tree to find the deepest common ancestor, taking d1
     * and d2 and finding the ancestor of those data
     *
     * @param curr the current node
     * @param d1 data 1
     * @param d2 data 2
     * @return the ancestor
     */
    private AVLNode<T> searchAncestor(AVLNode<T> curr, T d1, T d2) {
        if (curr == null) {
            return null;
        }
        if (curr.getData().compareTo(d2) < 0
                && curr.getData().compareTo(d1) < 0) {
            return searchAncestor(curr.getRight(), d1, d2);
        }
        if (curr.getData().compareTo(d2) > 0
                && curr.getData().compareTo(d1) > 0) {
            return searchAncestor(curr.getLeft(), d1, d2);
        }
        return curr;
    }

    /**
     * Clear the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Return the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}

