package bsts;

public class LinkedNodesBSTNoRecursion<E extends Comparable<E>> implements BST<E> {

  protected Node<E> root;

  public LinkedNodesBSTNoRecursion() {
    this.root = null;
  }

  public LinkedNodesBSTNoRecursion(E element) {
    this.root = new Node<>(element);
  }

  public static void main(String[] args) {
    LinkedNodesBSTNoRecursion<Integer> tree = new LinkedNodesBSTNoRecursion<>();
    tree.add(3);
    tree.add(1);
    tree.add(9);
    tree.add(7);
    tree.add(10);
    tree.add(6);

    tree.remove(9);
    tree.remove(3);
  }

  @Override
  public boolean add(E element) {
    if (root == null) {
      root = new Node<>(element);
      return true;
    }

    Node<E> curr = root;
    Node<E> parent = null;

    while (curr != null) {
      parent = curr;

      if (curr.element.compareTo(element) == 0) {
        return false;
      } else if (curr.element.compareTo(element) > 0) {
        curr = curr.left;
      } else {
        curr = curr.right;
      }
    }

    if (parent.element.compareTo(element) > 0) {
      parent.left = new Node<>(element);
    } else {
      parent.right = new Node<>(element);
    }
    return true;
  }

  @Override
  public boolean remove(E element) {
    Node<E> curr = root;
    Node<E> parent = null;

    while (curr != null && curr.element.compareTo(element) != 0) {
      parent = curr;
      if (curr.element.compareTo(element) > 0) {
        curr = curr.left;
      } else {
        curr = curr.right;
      }
    }

    if (curr == null) {
      return false; // the node is not in
    }

    if (parent == null) {
      //remove the root
      root = deleteNode(root);
    } else if (parent.element.compareTo(element) > 0) {
      parent.left = deleteNode(parent.left);
    } else {
      parent.right = deleteNode(parent.right);
    }
    return true;
  }

  private Node<E> deleteNode(Node<E> node) {
    if (node.right != null) {
      Node<E> replacementNode = findMinNode(node.right);
      replacementNode.right = removeMinNode(node.right);
      replacementNode.left = node.left;
      return replacementNode;
    } else if (node.left != null) {
      Node<E> replacementNode = findMaxNode(node.left);
      replacementNode.right = node.right;
      replacementNode.left = removeMaxNode(node.left);
      ;
      return replacementNode;
    }
    return null; //leaf node
  }

  private Node<E> findMinNode(Node<E> subtree) {
    Node<E> curr = subtree;
    while (curr.left != null) {
      curr = curr.left;
    }
    return curr;
  }

  private Node<E> findMaxNode(Node<E> subtree) {
    Node<E> curr = subtree;
    while (curr.right != null) {
      curr = curr.right;
    }
    return curr;
  }

  private Node<E> removeMinNode(Node<E> subtree) {
    assert subtree != null;

    Node<E> curr = subtree;
    Node<E> parent = null;
    while (curr.left != null) {
      parent = curr;
      curr = curr.left;
    }
    if (parent == null) {
      return curr.right;
    } else {
      parent.left = curr.right;
      return subtree;
    }
  }

  private Node<E> removeMaxNode(Node<E> subtree) {
    assert subtree != null;

    Node<E> curr = subtree;
    Node<E> parent = null;
    while (curr.right != null) {
      parent = curr;
      curr = curr.right;
    }
    if (parent == null) {
      return curr.left;
    } else {
      parent.right = curr.left;
      return subtree;
    }
  }

  @Override
  public boolean contains(E element) {
    Node<E> curr = root;
    while (curr != null) {
      if (curr.element.compareTo(element) == 0) {
        return true;
      } else if (curr.element.compareTo(element) > 0) {
        curr = curr.left;
      } else {
        curr = curr.right;
      }
    }
    return false;
  }

  private class Node<E> {

    private E element;
    private Node<E> left;
    private Node<E> right;

    public Node(E element) {
      this.element = element;
    }

    @Override
    public String toString() {
      return "N[" + element + "]";
    }
  }
}
