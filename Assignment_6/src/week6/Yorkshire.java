package week6;

public class Yorkshire extends Dog {
	private int breedWeight = 35;

    public Yorkshire(String name) {
        super(name);
    }

    // Small bark -- overrides speak method in Dog
    @Override
    public String speak() {
        return "woof";
    }
    
    @Override
    public int avgBreedWeight() {
        return breedWeight;
    }
}