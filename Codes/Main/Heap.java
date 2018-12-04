import java.util.Arrays;

@SuppressWarnings("unchecked")
public class Heap<X extends Comparable<? super X>> {
    private int lastAdd;
    private X[] heapArray;

    public Heap(X first) {
        heapArray = (X[])new Comparable[3];
        heapArray[0] = first;
        lastAdd = 0;
    }

    public Heap() {
        heapArray = (X[])new Comparable[1];
        heapArray[0] = null;
        lastAdd = -1;
    }

    public boolean isEmpty() {
        return lastAdd == -1;
    }

    public void Insert(X a) {
        if(lastAdd+1 == heapArray.length)
            heapArray = increaseheight(heapArray);
        lastAdd+=1;
        heapArray[lastAdd] = a;
        bubbleUpHeapify(lastAdd);
    }

    public void updateOnItem(X a) {
        int i = getItemIndex(a);
        if(i == -1) {
            System.out.println("Item not present in the heap.");
            return;
        }
        X item = removeAtIndex(i);
        Insert(item);
    }

    public int getItemIndex(X a) {
        for(int i=0; i<heapArray.length; i++)
            if(heapArray[i] == null)
                return -1;
            else if(heapArray[i].equals(a))
                return i;
        return -1;
    }

    private void bubbleUpHeapify(int index) {
        if(index<=0)
            return;
        X child = heapArray[index];
        X parent = heapArray[(index-1)/2];
        if(child.compareTo(parent)>=0) // child>=parent
            return;
        else {
            swapData(index, (index-1)/2);
            bubbleUpHeapify((index-1)/2);
        }
    }

    public X removeMin() {
        X removed = heapArray[0];
        heapArray[0] = heapArray[lastAdd];
        heapArray[lastAdd] = null;
        lastAdd -= 1;
        bubbleDownHeapify(0);
        return removed;
    }

    private X removeLast() {
        X last = heapArray[lastAdd];
        heapArray[lastAdd] = null;
        lastAdd -= 1;
        return last;
    }

    private X removeAtIndex(int i) {
        if(i == 0) {
            return removeMin();
        }
        if(i == lastAdd) {
            return removeLast();
        }
        try {
            X removed = heapArray[i];
            X temp = removeLast();
            heapArray[i] = temp;
            bubbleUpHeapify(i);
            bubbleDownHeapify(i);
            return removed;
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Index is out of bound.");
            return null;
        }
    }

    private void bubbleDownHeapify(int index) {
        if(isExternal(index))
            return;
        if(right(index) == -1) {
            if(heapArray[left(index)].compareTo(heapArray[index]) < 0)
                swapData(left(index), index);
        }
        else {
            if(heapArray[left(index)].compareTo(heapArray[right(index)]) < 0) {
                if(heapArray[left(index)].compareTo(heapArray[index]) < 0) {
                    swapData(index, left(index));
                    bubbleDownHeapify(left(index));
                }
            }
            else {
                if(heapArray[right(index)].compareTo(heapArray[index]) < 0) {
                    swapData(index, right(index));
                    bubbleDownHeapify(right(index));
                }
            }
        }
    }

    private int left(int index) {
        if(2*index+1 >= heapArray.length)
            return -1;
        if(heapArray[2*index+1] == null)
            return -1;
        return 2*index+1;
    }

    private int right(int index) {
        if(2*index+2 >= heapArray.length)
            return -1;
        if(heapArray[2*index+2] == null)
            return -1;
        return 2*index+2;
    }

    private boolean isExternal(int index) {
        boolean is = false;
        if(left(index)!=-1)
            is = is || (heapArray[left(index)] != null);
        if(right(index)!=-1)
            is = is || (heapArray[right(index)] != null);
        return !is;
    }

    private void swapData(int index1, int index2) {
        X temp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = temp;
    }

    private X[] increaseheight(X[] arr) {
        X[] newArr = (X[])new Comparable[arr.length*2+1];
        for(int i=0; i<arr.length; i++)
            newArr[i] = arr[i];
        return newArr;
    }

    public void printHeap(int... values) {
        int i=0, space=0;
        if(values.length == 2){
            i = values[0];
            space = values[1];
        }
        if(i>=heapArray.length)
            return;
        if(heapArray[i] == null)
            return;
        printHeap(2*i+1, space+2);
        for(int k = 0; k<space; k++)
            System.out.print("   ");
        System.out.println("  > "+heapArray[i]);
        printHeap(2*i+2, space+2);
    }
}
