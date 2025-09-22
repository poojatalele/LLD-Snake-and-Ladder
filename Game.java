import java.util.*;

public class Game {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Snake & Ladder ===");

        System.out.print("Board size m (board is m x m, e.g., 10): ");
        int m = readPositive(sc);

        System.out.print("Number of players: ");
        int nPlayers = readPositive(sc);

        int maxDice = Math.max(1, nPlayers / 6);
        System.out.print("Number of dice (1.." + maxDice + "): ");
        int diceCount = readInRange(sc, 1, maxDice);

        System.out.print("How many snakes? ");
        int snakes = readNonNegative(sc);

        System.out.print("How many ladders? ");
        int ladders = readNonNegative(sc);

        Board board = new Board();
        board.initializeBoard(m, snakes, ladders);

        List<Player> players = new ArrayList<>();
        sc.nextLine();
        for (int i = 1; i <= nPlayers; i++) {
            System.out.print("Player " + i + " name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) name = "P" + i;

            System.out.print("Type (H for Human, B for Bot): ");
            String t = sc.nextLine().trim().toUpperCase();
            if (t.equals("H")) {
                players.add(new Human(name, diceCount, sc));
            } else {
                players.add(new Bot(name, diceCount));
            }
        }

        System.out.println();
        System.out.println("Board ready. Last cell = " + board.getLastCell());
        System.out.println("Using " + diceCount + " dice for everyone (max allowed = " + maxDice + ").");
        board.printEntitiesSummary();
        System.out.println("\nInitial board:");
        board.printBoard(players);
        System.out.println("\nLet’s play!\n");

        boolean won = false;
        while (!won) {
            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                System.out.println("---- " + p.getName() + "'s turn (at " + p.getPosition() + ") ----");
                int roll = p.roll();

                Board.MoveResult res = board.applyMove(p.getPosition(), roll);

                if (res.overshoot) {
                    System.out.println("Overshoot! Need exact to win. Stay at " + res.from + ".");
                } else if (res.entity != null) {
                    System.out.println("Landed on " + res.entity + "!");
                    System.out.println(p.getName() + " moves to " + res.finalPos + ".");
                    p.setPosition(res.finalPos);
                } else {
                    System.out.println(p.getName() + " moves to " + res.finalPos + ".");
                    p.setPosition(res.finalPos);
                }

                if (p.getPosition() == board.getLastCell()) {
                    System.out.println("\n🎉 " + p.getName() + " WINS! 🎉");
                    won = true;
                    board.printBoard(players);
                    break;
                }

                board.printBoard(players);
                System.out.println();
            }
        }
        sc.close();
    }

    private static int readPositive(Scanner sc) {
        while (true) {
            int v = sc.nextInt();
            if (v > 1) return v;
            System.out.print("Enter a number > 1: ");
        }
    }

    private static int readNonNegative(Scanner sc) {
        while (true) {
            int v = sc.nextInt();
            if (v >= 0) return v;
            System.out.print("Enter a number >= 0: ");
        }
    }

    private static int readInRange(Scanner sc, int lo, int hi) {
        while (true) {
            int v = sc.nextInt();
            if (v >= lo && v <= hi) return v;
            System.out.print("Enter a number in [" + lo + ", " + hi + "]: ");
        }
    }
}
