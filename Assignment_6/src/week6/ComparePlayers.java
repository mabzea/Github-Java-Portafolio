package week6;
import java.util.Scanner;

public class ComparePlayers {
    public static void main(String[] args) {
        Player player1 = new Player();
        Player player2 = new Player();
        
        System.out.println("Enter information for Player 1:");
        player1.readPlayer();
        
        System.out.println("\nEnter information for Player 2:");
        player2.readPlayer();
        
        if (player1.equals(player2)) {
            System.out.println("\nSame player");
        } else {
            System.out.println("\nDifferent players");
        }
    }
}
