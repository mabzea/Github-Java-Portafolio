package week6;

public class ListTest {
	public static void main(String[] args) {
        System.out.println("--- Unsorted List ---");
        IntList myList = new IntList(10);
        myList.add(100);
        myList.add(50);
        myList.add(200);
        myList.add(25);
        System.out.println(myList);

        System.out.println("\n--- Sorted List ---");
        SortedIntList mySortedList = new SortedIntList(10);
        mySortedList.add(100);
        mySortedList.add(50);
        mySortedList.add(200);
        mySortedList.add(25);
        System.out.println(mySortedList);
    }
}
