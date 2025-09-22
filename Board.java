import java.util.*;

public class Board {
    private int size;        
    private int lastCell;    
    private final Map<Integer, Entity> entities = new HashMap<>();
    private final Random rng = new Random();

    public void initializeBoard(int m, int numSnakes, int numLadders) {
        if (m < 2) throw new IllegalArgumentException("Board size must be >= 2");
        this.size = m;
        this.lastCell = m * m;
        entities.clear();

        for (int i = 0; i < numSnakes; i++) placeRandomSnake();
        for (int i = 0; i < numLadders; i++) placeRandomLadder();
    }

    public void setEntity(Entity e) {
        int from = e.getFrom();
        if (from <= 1 || from >= lastCell) {
            throw new IllegalArgumentException("Entity start must be between 2 and " + (lastCell - 1));
        }
        if (entities.containsKey(from)) {
            throw new IllegalArgumentException("Cell " + from + " already has an entity");
        }
        entities.put(from, e);
    }

    public int getLastCell() {
        return lastCell;
    }

    public Entity getEntityAt(int index) {
        return entities.get(index);
    }

    public void printEntitiesSummary() {
        if (entities.isEmpty()) {
            System.out.println("No snakes/ladders.");
            return;
        }
        List<Entity> list = new ArrayList<>(entities.values());
        list.sort(Comparator.comparingInt(Entity::getFrom));
        System.out.println("Entities:");
        for (Entity e : list) System.out.println("  - " + e);
    }

    public static class MoveResult {
        public final int from;
        public final int target;
        public final int finalPos; 
        public final Entity entity;
        public final boolean overshoot;

        public MoveResult(int from, int target, int finalPos, Entity entity, boolean overshoot) {
            this.from = from;
            this.target = target;
            this.finalPos = finalPos;
            this.entity = entity;
            this.overshoot = overshoot;
        }
    }

    public MoveResult applyMove(int current, int roll) {
        int target = current + roll;
        if (target > lastCell) {
            return new MoveResult(current, target, current, null, true);
        }
        Entity e = entities.get(target);
        int finalPos = (e != null) ? e.changePos() : target;
        return new MoveResult(current, target, finalPos, e, false);
    }

    public void printBoard(List<Player> players) {
        Map<Integer, List<String>> onCell = new HashMap<>();
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            onCell.computeIfAbsent(p.getPosition(), k -> new ArrayList<>())
                  .add("P" + (i + 1));
        }

        int digits = String.valueOf(lastCell).length();
        int cellWidth = Math.max(digits + 5, 8); 

        System.out.println();
        for (int r = size - 1; r >= 0; r--) {
            StringBuilder line = new StringBuilder();
            for (int c = 0; c < size; c++) {
                int idx = indexAt(r, c); 
                String content = String.format("%" + digits + "d", idx);

                Entity e = entities.get(idx);
                if (e != null) {
                    content += (e instanceof Snake) ? "S" : "L";
                } else {
                    content += " ";
                }

                List<String> tags = onCell.get(idx);
                if (tags != null && !tags.isEmpty()) {
                    content += "|" + String.join(",", tags);
                }

                line.append(padRight("[" + content + "]", cellWidth));
            }
            System.out.println(line);
        }
    }

    private int indexAt(int rowFromTop, int colFromLeft) {
        int row = (size - 1) - rowFromTop; 
        if (row % 2 == 0) {
            return row * size + colFromLeft + 1;
        } else {
            return row * size + (size - colFromLeft);
        }
    }

    private static String padRight(String s, int width) {
        if (s.length() >= width) return s;
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < width) sb.append(' ');
        return sb.toString();
    }

    private void placeRandomSnake() {
        int attempts = 0;
        while (attempts++ < 1000) {
            int from = rnd(2, lastCell - 1);
            int to   = rnd(1, from - 1);
            if (entities.containsKey(from)) continue;
            try {
                setEntity(new Snake(from, to));
                return;
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private void placeRandomLadder() {
        int attempts = 0;
        while (attempts++ < 1000) {
            int from = rnd(2, lastCell - 1);       
            int to   = rnd(from + 1, lastCell - 1);
            if (entities.containsKey(from)) continue;
            try {
                setEntity(new Ladder(from, to));
                return;
            } catch (IllegalArgumentException ignored) {}
        }
    }

    private int rnd(int lo, int hi) {
        if (hi < lo) return lo;
        return lo + rng.nextInt(hi - lo + 1);
    }
}
