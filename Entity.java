public abstract class Entity {
    protected final int from;
    protected final int to;

    public Entity(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() { return from; }
    public int getTo()   { return to;   }

    public abstract int changePos();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + from + " -> " + to + ")";
    }
}
