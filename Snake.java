public class Snake extends Entity {
    public Snake(int from, int to) {
        super(from, to);
        if (from <= to) throw new IllegalArgumentException("Snake must go down (from > to)");
    }
    @Override
    public int changePos() { return to; }
}
