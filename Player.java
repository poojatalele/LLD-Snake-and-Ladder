import java.util.Random;

public abstract class Player {
    protected final String name;
    protected int position = 1;        
    protected final int diceCount;     
    protected final Random rng = new Random();

    public Player(String name, int diceCount) {
        this.name = name;
        this.diceCount = Math.max(1, diceCount);
    }

    public abstract int roll();

    protected int[] rollFaces() {
        int[] faces = new int[diceCount];
        for (int i = 0; i < diceCount; i++) {
            faces[i] = rng.nextInt(6) + 1; 
        }
        return faces;
    }

    protected int sum(int[] a) {
        int s = 0;
        for (int v : a) s += v;
        return s;
    }

    public String getName() { return name; }
    public int getPosition() { return position; }
    public void setPosition(int p) { position = p; }
}
