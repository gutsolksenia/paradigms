import java.util.Scanner;

/**
 * Created by ksenia on 28.10.16.
 */
public class HumanPlayer extends AbstractPlayer {
    private static final String INCORRECT_INPUT_TEXT = "Incorrect input format, please, try again.";
    private static final String INCORRECT_MOVE_TEXT = "Incorrect move, please, try again.";

    protected HumanPlayer(BoardConfiguration.Cell playerType) {
        super(playerType);
    }

    @Override
    public void makeMove(BoardConfiguration configuration) {
        super.makeMove(configuration);
        System.out.println("Make your move, please.");
        Scanner scanner = new Scanner(System.in);
        String move = scanner.nextLine();
        String[] fromTo = move.split(" ");
        if (fromTo.length != 2) {
            System.out.println(INCORRECT_INPUT_TEXT);
            return;
        }

        for (String s: fromTo) {
            if (s.length() != 2) {
                System.out.println(INCORRECT_INPUT_TEXT);
                makeMove(configuration);
            }
        }
        int xFrom = fromTo[0].toLowerCase().charAt(0) - 'a';
        int yFrom = fromTo[0].charAt(1) - '1';
        int xTo = fromTo[1].toLowerCase().charAt(0) - 'a';
        int yTo = fromTo[1].charAt(1) - '1';
        try {
            configuration.getMove(xFrom, yFrom, xTo, yTo).doMove();
        } catch (IllegalArgumentException e) {
            System.out.println(INCORRECT_MOVE_TEXT);
            return;
        }
    }
}
