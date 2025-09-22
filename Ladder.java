public class Ladder extends Entity {
    public Ladder(int from, int to) {
        super(from, to);
        if (from >= to) throw new IllegalArgumentException("Ladder must go up (from < to)");
    }
    @Override
    public int changePos() { return to; }
}
