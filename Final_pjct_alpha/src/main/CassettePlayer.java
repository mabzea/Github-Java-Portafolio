package main;

/**
 * A noisy audio decoy to distract enemies.
 * Satisfies Gameplay Requirement: At least 4 different useable item types.
 */
public class CassettePlayer extends Item {
    private boolean isPlaying;

    public CassettePlayer() {
        super("Cassette Player", 1.2);
        this.isPlaying = false;
    }


    public void use() {
        if (!isPlaying) {
            System.out.println("You hit PLAY on the heavy, vintage Pioneer cassette player.");
            System.out.println("A loud, distorted audio track echoes down the hall, drawing attention away from you.");
            isPlaying = true;
        } else {
            System.out.println("You hit STOP. The eerie silence returns to the prison corridor.");
            isPlaying = false;
        }
    }
}