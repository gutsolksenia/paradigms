import java.util.List;
import java.util.Random;

/**
 * Created by ksenia on 29.10.16.
 */
public class RandomPlayer extends AbstractPlayer {
    protected RandomPlayer(BoardConfiguration.Cell playerType) {
        super(playerType);
    }
    private static final Random random = new Random();

    @Override
    public void makeMove(BoardConfiguration configuration) {
        super.makeMove(configuration);
        List<BoardConfiguration.Move> moves = configuration.getPossibleMoves();
        if (moves.size() > 0) {
            moves.get(random.nextInt(moves.size())).doMove();
        }
    }
}
