public class MyStack<Item> {
  private Node first = null;

  private class Node {
    private Item item;
    private Node next;
  }

  public Item pop() {
    if(isEmpty())
      throw new NullPointerException("Stack is Empty!");
    Item item = first.item;
    first = first.next;
    return item;
  }

  public void push(Item item) {
    Node oldfirst = first;
    first = new Node();
    first.next = oldfirst;
    first.item = item;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public Item peek() {
    return first.item;
  }
}
