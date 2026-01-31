package week6;

public class Labrador extends Dog {
	private String color; //black, yellow, or chocolate?
    private int breedWeight = 75;

    public Labrador(String name, String color) {
        
        super(name);
        this.color = color;
    }

    // Big bark -- overrides speak method in Dog
    @Override
    public String speak() {
        return "WOOF";
    }

       @Override
    public int avgBreedWeight() {
        return breedWeight;
    }
}