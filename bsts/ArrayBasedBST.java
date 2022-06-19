package bsts;

import java.util.Arrays;

public class ArrayBasedBST<E extends Comparable<E>> implements BST<E> {

  private static final int DEFAULT_CAPACITY = 1024;
  private int size;
  private E[] elements;

  public ArrayBasedBST() {
    this(DEFAULT_CAPACITY);
  }

  public ArrayBasedBST(int capacity) {
    size = 0;
    elements = (E[]) new Comparable[capacity];
  }

  public static void main(String[] args) {
    ArrayBasedBST<Integer> bst = new ArrayBasedBST<>((int) Math.pow(2, 4) - 1);
    bst.add(14);
    bst.add(11);
    bst.add(17);
    bst.add(9);
    bst.add(13);
    bst.add(12);
    bst.add(18);

    System.out.println(bst);
    bst.remove((Integer) 14);
    System.out.println(bst);

  }

  @Override
  public boolean add(E element) {
    return add(0, element);
  }

  private boolean add(int position, E element) {
    if (position >= elements.length) {
      throw new ArrayIndexOutOfBoundsException(
          "Position " + position + " is beyond the size of the elements");
    }
    if (elements[position] == null) {
      elements[position] = element;
      size = size + 1;
      return true;
    } else {
      if (element.compareTo(elements[position]) == 0) {
        return false; //the element is already in the set
      } else if (element.compareTo(elements[position]) < 0) {
        int positionOfLeftChild = getLeftChildIndex(position);
        return add(positionOfLeftChild, element);
      } else {
        int positionOfRightChild = getRightChildIndex(position);
        return add(positionOfRightChild, element);
      }
    }
  }

  @Override
  public boolean contains(E element) {
    return contains(0, element);
  }

  private boolean contains(int position, E element) {
    if (position >= elements.length) {
      return false;
    }
    if (elements[position] == null) {
      return false;
    } else {
      if (element.compareTo(elements[position]) == 0) {
        return true;
      } else if (element.compareTo(elements[position]) < 0) {
        int positionOfLeftChild = getLeftChildIndex(position);
        return contains(positionOfLeftChild, element);
      } else {
        int positionOfRightChild = getRightChildIndex(position);
        return contains(positionOfRightChild, element);
      }
    }
  }

  public int findPosition(int root, E element) {
    int left = getLeftChildIndex(root);
    int right = getRightChildIndex(root);
    if (elements[root] == null) {
      return -1;
    }
    if (elements[root].compareTo(element) == 0) {
      return root;
    }
    if (elements[root].compareTo(element) > 0) {
      return findPosition(left, element);
    }
    if (elements[root].compareTo(element) < 0) {
      return findPosition(right, element);
    }
    return -1;
  }

  @Override
  public boolean remove(E element) {
    int pos = findPosition(0, element);
    System.out.println("Found at pos " + pos);
    if (pos >= 0) {
      return removeFromPos(pos);
    }
    return false;
  }

  private boolean removeFromPos(int position) {
    if (position < (elements.length / 2)) {
      int left = getLeftChildIndex(position);
      int right = getRightChildIndex(position);
      if (elements[left] == null && elements[right] == null) {
        elements[position] = null;
        size--;
        return true;
      }
      if (elements[left] != null && elements[right] == null) {
        elements[position] = elements[left];
        return removeFromPos(left);
      }
      if (elements[left] == null && elements[right] != null) {
        elements[position] = elements[right];
        return removeFromPos(right);
      }
      int minPosInRight = getMinPos(right);
      elements[position] = elements[minPosInRight];
      return removeFromPos(minPosInRight);
    } else {
      elements[position] = null;
      size--;
      return true;
    }
  }

  private int getMinPos(int curr) {
    int left = getLeftChildIndex(curr);
    if (elements[left] != null) {
      return getMinPos(left);
    }
    return curr;
  }

//  private void swap(int x, int y) {
//    assert x < size;
//    assert y < size;
//    E temp = elements[x];
//    elements[x] = elements[y];
//    elements[y] = temp;
//  }

  public int size() {
    return size;
  }

  private int getLeftChildIndex(int root) {
    return root * 2 + 1;
  }

  private int getRightChildIndex(int root) {
    return 2 * root + 2;
  }

  @Override
  public String toString() {
    return Arrays.toString(elements);
  }
}
