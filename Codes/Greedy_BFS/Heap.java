import java.util.Arrays;

@SuppressWarnings("unchecked")
public class Heap<X extends Comparable<? super X>> {
    int lastAdd;
    public X[] heapArray;

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
        if(lastAdd%2 == 0) {
            lastAdd+=1;
            heapArray[lastAdd] = a;
        }
        else {
            lastAdd+=1;
            heapArray[lastAdd] = a;
        }
        bubbleUpHeapify(lastAdd);
    }

    public void bubbleUpHeapify(int index) {
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

    public void bubbleDownHeapify(int index) {
        if(isExternal(index))
            return;
        if(right(index) == -1) {
            if(heapArray[left(index)].compareTo(heapArray[index]) <= 0)
                swapData(left(index), index);
        }
        else {
            if(heapArray[left(index)].compareTo(heapArray[right(index)]) <= 0) {
                if(heapArray[left(index)].compareTo(heapArray[index]) <= 0) {
                    swapData(index, left(index));
                    bubbleDownHeapify(left(index));
                }
            }
            else {
                if(heapArray[right(index)].compareTo(heapArray[index]) <= 0) {
                    swapData(index, right(index));
                    bubbleDownHeapify(right(index));
                }
            }
        }
    }

    public int left(int index) {
        if(2*index+1 >= heapArray.length)
            return -1;
        if(heapArray[2*index+1] == null)
            return -1;
        return 2*index+1;
    }

    public int right(int index) {
        if(2*index+2 >= heapArray.length)
            return -1;
        if(heapArray[2*index+2] == null)
            return -1;
        return 2*index+2;
    }

    public boolean isExternal(int index) {
        boolean is = false;
        if(left(index)!=-1)
            is = is || (heapArray[left(index)] != null);
        if(right(index)!=-1)
            is = is || (heapArray[right(index)] != null);
        return !is;
    }

    public void swapData(int index1, int index2) {
        X temp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = temp;
    }

    public X[] increaseheight(X[] arr) {
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
