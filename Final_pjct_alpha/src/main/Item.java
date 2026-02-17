package main;

public class Item implements Interactable {
    private String name;
    private String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public void use() {
        System.out.println("You use the " + name + ". Nothing happens... yet.");
    }

    public boolean isHidden() { return false; }
}