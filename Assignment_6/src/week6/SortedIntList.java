package week6;

public class SortedIntList extends IntList {
    public SortedIntList(int size) {
        super(size);
    }

    @Override
    public void add(int value) {
        if (numElements == list.length) {
            System.out.println("Can't add, list is full");
        } else {
            int i = numElements;
            // Find insertion point and shift elements
            while (i > 0 && list[i-1] > value) {
                list[i] = list[i-1];
                i--;
            }
            list[i] = value;
            numElements++;
        }
    }
}