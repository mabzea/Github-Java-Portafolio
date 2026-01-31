package week6;

public class DogTest {
	public static void main(String[] args) {

        // Create and print a Labrador and a Yorkshire. [cite: 13]
        Labrador lab = new Labrador("Buddy", "yellow");
        Yorkshire yorkie = new Yorkshire("Pip");

        System.out.println(lab.getName() + " says " + lab.speak());
        System.out.println(yorkie.getName() + " says " + yorkie.speak());

        // Print the average breed weight for both. [cite: 23]
        System.out.println("The average breed weight for a Labrador is: " + lab.avgBreedWeight());
        System.out.println("The average breed weight for a Yorkshire is: " + yorkie.avgBreedWeight());
    }
}