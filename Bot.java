import java.util.Arrays;

public class Bot extends Player {

    public Bot(String name, int diceCount) {
        super(name, diceCount);
    }

    @Override
    public int roll() {
        int[] faces = rollFaces();
        int s = sum(faces);
        System.out.println(name + " (bot) rolled " + Arrays.toString(faces) + " => " + s + ".");
        return s;
    }
}
