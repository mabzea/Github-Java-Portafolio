package week6;
import java.util.Scanner;

public class Player {
	private String name;
    private String team;
    private int jerseyNumber;

    public void readPlayer() {
        Scanner scan = new Scanner(System.in);
        System.out.print("Name: ");
        name = scan.nextLine();
        System.out.print("Team: ");
        team = scan.nextLine();
        System.out.print("Jersey number: ");
        jerseyNumber = scan.nextInt();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Player other = (Player) obj;
        
        // Two players are the same if they have the same team and jersey number.
        return this.jerseyNumber == other.jerseyNumber && 
               this.team.equals(other.team);
    }
}
