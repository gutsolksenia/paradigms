import java.util.List;

/**
 * Created by ksenia on 28.10.16.
 */
public class ComputerPlayer extends AbstractPlayer {

    public ComputerPlayer(BoardConfiguration.Cell playerType) {
        super(playerType);
    }

    @Override
    public void makeMove(BoardConfiguration configuration) {
        super.makeMove(configuration);
        List<BoardConfiguration.Move> moves = configuration.getPossibleMoves();
        if (moves.size() > 0) {
            moves.get(0).doMove();
        }
    }
}
