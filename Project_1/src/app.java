public class app {
    
    public enum Difficulty {
        EASY,    // Represents a low-challenge setting
        MEDIUM,  // Represents a balanced challenge setting
        HARD     // Represents a high-challenge setting
    }

    // Abstract Method Example: Defines the blueprint for all enemy entities.
    abstract static class Monster {
        String name; // Stores the name of the specific monster

        public Monster(String name) {
            this.name = name; // Constructor to initialize the monster's name
        }

        // Abstract Method 1: Forces subclasses to define unique combat behavior.
        public abstract void performAttack();

        // Additional Abstract Method: Forces subclasses to define unique loot/death behavior.
        public abstract void onDeath();
    }

    // Subclass demonstrating inheritance and method overriding.
    static class Dragon extends Monster {

        public Dragon(String name) {
            super(name); // Passes the name up to the Monster parent constructor
        }

        // @Override Example 1: Providing specific implementation for the attack logic.
        @Override
        public void performAttack() {
            System.out.println(name + " breathes a stream of intense fire!"); // Specific fire attack
        }

        // @Override Example 2 (Additional): Providing specific implementation for death logic.
        @Override
        public void onDeath() {
            System.out.println(name + " collapses, leaving behind a pile of gold."); // Specific death reward
        }

        // Nested Class Example: Encapsulates logic that only exists within a Dragon.
        static class SpecialAbility {
            String powerName = "Inferno"; // The name of this nested skill

            void activate() {
                System.out.println("Activating special ability: " + powerName); // Execution logic for the skill
            }
        }
    }

    /**
     * Entry point of the application.
     */
    public static void main(String[] args) {
        // Enumeration Usage: Setting the game state.
        Difficulty gameLevel = Difficulty.HARD; // Assigns the HARD constant from the Enum
        System.out.println("Game started on " + gameLevel + " difficulty."); // Outputs the selected difficulty

        // Instantiation: Creating a specific Dragon object.
        Dragon fireDrake = new Dragon("Smaug"); // Creates a new Dragon instance named Smaug

        // Method Invocation: Triggering overridden behaviors.
        fireDrake.performAttack(); // Executes the Dragon-specific version of performAttack
        fireDrake.onDeath();       // Executes the Dragon-specific version of onDeath

        // Nested Class Usage: Accessing logic bundled inside the Dragon class.
        Dragon.SpecialAbility ability = new Dragon.SpecialAbility(); // Instantiates the static nested class
        ability.activate(); // Calls the method within the nested class
    }
}