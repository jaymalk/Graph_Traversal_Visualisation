public class MyQueue <Item> {
  private Node last, first;

  private class Node {
    Item item;
    Node next;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public void enqueue(Item item) {
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if(isEmpty()) first = last;
    else          oldlast.next = last;
  }

  public Item dequeue() {
    if(isEmpty())
      throw new NullPointerException("The Queue is Empty...");
    Item item = first.item;
    first = first.next;
    if(isEmpty()) last = null;
    return item;
  }
}
