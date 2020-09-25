import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of a binary search tree.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular
     * for loop will not work here. What other type of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        for (T x: data) {
            if (x == null) {
                throw new IllegalArgumentException("Cannot add null data to"
                        + "the BST");
            } else {
                this.add(x);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data to"
                    + " the BST");
        }  else {
            if (root == null) {
                root = new BSTNode<>(data);
                size++;
            } else  {
                BSTNode<T> parentNode = traverseAdd(data, root);
                if (parentNode != null) {
                    if (parentNode.getData().compareTo(data) < 0) {
                        BSTNode<T> positionNode = new BSTNode<>(data);
                        positionNode.setRight(parentNode.getRight());
                        parentNode.setRight(positionNode);
                    } else if (parentNode.getData().compareTo(data) > 0) {
                        BSTNode<T> positionNode = new BSTNode<>(data);
                        positionNode.setLeft(parentNode.getLeft());
                        parentNode.setLeft(positionNode);
                    }
                    size++;
                }
            }
        }
    }

    /**
     * Finds position in which to add the new node at.
     *
     * @param data the data to be added
     * @param currNode the current node
     * @return the node found, or null
     */
    private BSTNode<T> traverseAdd(T data, BSTNode<T> currNode) {
        if (currNode.getData().compareTo(data) < 0) {
            if (currNode.getRight() != null) {
                return traverseAdd(data, currNode.getRight());
            } else {
                return currNode;
            }
        } else if (currNode.getData().compareTo(data) > 0) {
            if (currNode.getLeft() != null) {
                return traverseAdd(data, currNode.getLeft());
            } else {
                return currNode;
            }
        } else {
            return null;
        }
    }

    /**
     * Helper for the remove method.
     *
     * @param data the data to be added
     * @param currNode node to traverse from
     * @param dataRemove  the data removed
     * @return the node to remove
     */
    private BSTNode<T> removeHelper(T data, BSTNode<T> currNode,
                                    BSTNode<T> dataRemove)  {
        if (currNode == null) {
            return null;
        } else if (currNode.getData().compareTo(data) < 0) {
            currNode.setRight(removeHelper(data, currNode.getRight(),
                    dataRemove));
        } else if (currNode.getData().compareTo(data) > 0) {
            currNode.setLeft(removeHelper(data, currNode.getLeft(),
                    dataRemove));
        } else {
            dataRemove.setData(currNode.getData());
            if (currNode.getLeft() == null) {
                return currNode.getRight();
            } else if (currNode.getRight() == null) {
                return currNode.getLeft();
            } else {
                BSTNode<T> below = traverseSuccessor(currNode);
                currNode.setData(below.getData());
                currNode.setRight(removeHelper(below.getData(),
                        currNode.getRight(), dataRemove));
            }
        }
        size--;
        return dataRemove;
    }

    /**
     * Helper for the remove method.
     *
     * @param currNode the current node
     * @return the successor
     */
    private BSTNode<T> traverseSuccessor(BSTNode<T> currNode) {
        if (currNode.getLeft() != null) {
            return traverseSuccessor(currNode.getLeft());
        } else {
            return currNode;
        }
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You must use recursion to find and remove the successor (you will likely
     * need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null, cannot remove"
                    + "null data from BST");
        }
        if (size == 1) {
            T rootData = root.getData();
            clear();
            return rootData;
        }
        BSTNode<T> dataRemove = new BSTNode<>(null);
        removeHelper(data, root, dataRemove);
        if (dataRemove == null) {
            throw new NoSuchElementException("The data is not found in"
                    + " the BST");
        }
        return dataRemove.getData();
    }

    /**
     * Finds position of the data.
     *
     * @param data the data to be added
     * @param currNode the current node
     * @return the node found, or null
     */
    private BSTNode<T> traverseGet(T data, BSTNode<T> currNode) {
        if (currNode == null) {
            return null;
        } else if (currNode.getData().compareTo(data) < 0) {
            return traverseGet(data, currNode.getRight());
        } else if (currNode.getData().compareTo(data) > 0) {
            return traverseGet(data, currNode.getLeft());
        } else {
            return currNode;
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
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
            throw new IllegalArgumentException("Cannot add null data to"
                    + "the BST");
        }
        BSTNode<T> find = traverseGet(data, root);
        if (find == null) {
            throw new NoSuchElementException("The data ("
                    +  data.toString() + ") was not found in the list");
        }
        return find.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data cannot be found"
                    + "in the BST");
        }
        return traverseGet(data, root) != null;
    }

    /**
     * Helper for preorder
     *
     * @param list the list to add to
     * @param currNode the current node in the BST
     */
    private void preorderHelper(List<T> list, BSTNode<T> currNode) {
        if (currNode != null) {
            list.add(currNode.getData());
            preorderHelper(list, currNode.getLeft());
            preorderHelper(list, currNode.getRight());
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        preorderHelper(list, root);
        return list;
    }

    /**
     * Helper for inorder
     *
     * @param list the list to add to
     * @param currNode the current node in the BST
     */
    private void inorderHelper(List<T> list, BSTNode<T> currNode) {
        if (currNode != null) {
            inorderHelper(list, currNode.getLeft());
            list.add(currNode.getData());
            inorderHelper(list, currNode.getRight());
        }
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        inorderHelper(list, root);
        return list;
    }

    /**
     * Helper for postorder
     *
     * @param list the list to add to
     * @param currNode the current node in the BST
     */
    private void postorderHelper(List<T> list, BSTNode<T> currNode) {
        if (currNode != null) {
            postorderHelper(list, currNode.getLeft());
            postorderHelper(list, currNode.getRight());
            list.add(currNode.getData());
        }
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        postorderHelper(list, root);
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n).
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        if (root == null) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        List<BSTNode<T>> q = new ArrayList<>();
        int count = 0;
        q.add(root);
        while (count != q.size()) {
            list.add(q.get(count).getData());
            if (q.get(count).getLeft() != null) {
                q.add(q.get(count).getLeft());
            }
            if (q.get(count).getRight() != null) {
                q.add(q.get(count).getRight());
            }
            count++;
        }
        return list;
    }

    /**
     * Helper for kLargest
     *
     * Adds k largest elements to a list
     *
     * @param list the list to add to
     * @param currNode the current node in the BST
     * @param k number of elements to put in the list
     */
    private void kHelper(LinkedList<T> list, BSTNode<T> currNode, int k) {
        if (currNode != null) {
            kHelper(list, currNode.getRight(), k);
            if (list.size() != k) {
                list.addFirst(currNode.getData());
                kHelper(list, currNode.getLeft(), k);
            }
        }
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in the efficiency penalty.
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
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     * in the BST
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("Cannot look for more items"
                    + "than the BST contains");
        }
        LinkedList<T> list = new LinkedList<>();
        kHelper(list, root, k);
        return list;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Helper for height method
     *
     * @param currNode current node being traversed through
     * @return height of current node
     */
    private int heightHelper(BSTNode<T> currNode) {
        if (currNode == null) {
            return -1;
        } else if (currNode.getLeft() == null && currNode.getRight() == null) {
            return 0;
        }
        return Math.max(heightHelper(currNode.getLeft()),
                heightHelper(currNode.getRight())) + 1;
    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
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
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
