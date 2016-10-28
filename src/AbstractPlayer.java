/**
 * Created by ksenia on 28.10.16.
 */
public abstract class AbstractPlayer implements Player {
    private final BoardConfiguration.Cell playerType;

    public AbstractPlayer(BoardConfiguration.Cell playerType) {
        this.playerType = playerType;
        if (playerType.equals(BoardConfiguration.Cell.EMPTY)) {
            throw new IllegalArgumentException();
        }
    }

    public void makeMove(BoardConfiguration configuration) {
        if (!playerType.equals(configuration.getActivePlayer())) {
            throw new IllegalStateException();
        }
    }

    public BoardConfiguration.Cell getPlayerType() {
        return playerType;
    }
}
