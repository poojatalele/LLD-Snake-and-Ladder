import java.util.Arrays;
import java.util.Scanner;

public class Human extends Player {
    private final Scanner scanner;

    public Human(String name, int diceCount, Scanner scanner) {
        super(name, diceCount);
        this.scanner = scanner;
    }

    @Override
    public int roll() {
        System.out.print(name + ", press ENTER to roll " + diceCount + " dice...");
        scanner.nextLine(); // wait for user
        int[] faces = rollFaces();
        int s = sum(faces);
        System.out.println(name + " rolled " + Arrays.toString(faces) + " => " + s + ".");
        return s;
    }
}
