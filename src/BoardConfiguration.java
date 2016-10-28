import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ksenia on 28.10.16.
 */

public class BoardConfiguration {
    static final int BOARD_SIZE = 8;
    private static final int MAX_CHECKER_COUNT = 12;
    private final Cell[][] field = new Cell[BOARD_SIZE][BOARD_SIZE];
    private Cell activePlayer = Cell.WHITE;
    private List<Move> possibleMoves;
    private boolean gameOver = false;

    {
       for (Cell[] row: field) {
           Arrays.fill(row, Cell.EMPTY);
       }

       for (int i = 0; i < 2 * MAX_CHECKER_COUNT; i+=2) {
           field[i / BOARD_SIZE][(i / BOARD_SIZE + i) % BOARD_SIZE] = Cell.WHITE;
       }

       for (int i = 0; i < 2 * MAX_CHECKER_COUNT; i+=2) {
           field[BOARD_SIZE - i / BOARD_SIZE - 1][BOARD_SIZE - (i / BOARD_SIZE + i) % 8 - 1] = Cell.BLACK;
       }
       updatePossibleMoves();
    }

    public Cell getActivePlayer() {
        return activePlayer;
    }

    private void showCell(Cell cell) {
        if (cell.equals(Cell.EMPTY)) {
            System.out.print("_|");
        }
        if (cell.equals(Cell.WHITE)) {
            System.out.print("W|");
        }
        if (cell.equals(Cell.BLACK)) {
            System.out.print("B|");
        }
    }

    public void show() {
        System.out.println(activePlayer.toString());
        System.out.println("_|A|B|C|D|E|F|G|H");
        int i = 0;
        for (Cell[] row: field) {
            System.out.print(new Integer(++i).toString() + "|");
            for (Cell cell: row) {
                showCell(cell);
            }
            System.out.println();
        }
    }

    public Move getMove(int xFrom, int yFrom, int xTo, int yTo) {
        return new Move(xFrom, yFrom, xTo, yTo);
    }

    public enum Cell {
        BLACK, WHITE, EMPTY
    }

    private List<Move> getGoodMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Move move = new Move(i, j, i + 2, j + 2);
                if (move.permissible()) {
                    moves.add(move);
                }
                move = new Move(i, j, i - 2, j + 2);
                if (move.permissible()) {
                    moves.add(move);
                }
                move = new Move(i, j, i + 2, j - 2);
                if (move.permissible()) {
                    moves.add(move);
                }
                move = new Move(i, j, i - 2, j - 2);
                if (move.permissible()) {
                    moves.add(move);
                }
            }

        }
        return moves;
    }

    private void updatePossibleMoves() {
        possibleMoves = getGoodMoves();
        if (possibleMoves.size() > 0) {
            return;
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                Move move = new Move(i, j, i + 1, j + 1);
                if (move.permissible() && activePlayer.equals(Cell.WHITE)) {
                    possibleMoves.add(move);
                }
                move = new Move(i, j, i - 1, j + 1);
                if (move.permissible() && activePlayer.equals(Cell.WHITE)) {
                    possibleMoves.add(move);
                }

                move = new Move(i, j, i + 1, j - 1);
                if (move.permissible() && activePlayer.equals(Cell.BLACK)) {
                    possibleMoves.add(move);
                }

                move = new Move(i, j, i - 1, j - 1);
                if (move.permissible() && activePlayer.equals(Cell.BLACK)) {
                    possibleMoves.add(move);
                }
            }
        }
    }

    public List<Move> getPossibleMoves() {
        return possibleMoves;
    }

    public boolean gameIsOver() {
        return gameOver;
    }

    public class Move {
        private final int xFrom;
        private final int yFrom;
        private final int xTo;
        private final int yTo;

        private Move(int xFrom, int yFrom, int xTo, int yTo) {
            this.xFrom = xFrom;
            this.yFrom = yFrom;
            this.xTo = xTo;
            this.yTo = yTo;
        }

        private boolean permissible() {
            if (xFrom < 0 || xTo < 0 || yFrom < 0 || yTo < 0) {
                return false;
            }

            if (xFrom >= BOARD_SIZE || xTo >= BOARD_SIZE || yFrom >= BOARD_SIZE || yTo >= BOARD_SIZE) {
                return false;
            }

            if (!field[yFrom][xFrom].equals(activePlayer) || !field[yTo][xTo].equals(Cell.EMPTY)) {
                return false;
            }

            if (Math.abs(xTo - xFrom) == 1) {
                if (activePlayer.equals(Cell.WHITE) && yTo == yFrom + 1) {
                    return true;
                }
                if (activePlayer.equals(Cell.BLACK) && yTo == yFrom - 1) {
                    return true;
                }
            }

            if (Math.abs(xTo - xFrom) == 2 && Math.abs(yTo - yFrom) == 2) {
                Cell eaten = field[(yFrom + yTo) / 2][(xFrom + xTo) / 2];
                if (!(eaten == Cell.EMPTY) && !(eaten == activePlayer)) {
                    return true;
                }
            }
            return false;
        }

        public void doMove() {
            if (!permissible()) {
                throw new IllegalArgumentException();
            }

            field[yFrom][xFrom] = Cell.EMPTY;
            field[yTo][xTo] = activePlayer;

            boolean wasEaten = false;
            if (Math.abs(xFrom - xTo) == 2) {
                field[(yFrom + yTo) / 2][(xFrom + xTo) / 2] = Cell.EMPTY;
                wasEaten = true;
            }


            if (!(wasEaten && !getGoodMoves().isEmpty())) {
                if (activePlayer.equals(Cell.BLACK)) {
                    activePlayer = Cell.WHITE;
                } else {
                    activePlayer = Cell.BLACK;
                }
            }

            updatePossibleMoves();
            if (possibleMoves.isEmpty()) {
                gameOver = true;
            }
        }
    }
}
