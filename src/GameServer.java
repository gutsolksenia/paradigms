/**
 * Created by ksenia on 28.10.16.
 */
public class GameServer {
    public static void main(String[] args) {
        Player whitePlayer = new ComputerPlayer(BoardConfiguration.Cell.WHITE);
        Player blackPlayer = new RandomPlayer(BoardConfiguration.Cell.BLACK);
        BoardConfiguration configuration = new BoardConfiguration();
        while (!configuration.gameIsOver()) {
            while (configuration.getActivePlayer().equals(BoardConfiguration.Cell.WHITE)) {
                configuration.show();
                whitePlayer.makeMove(configuration);
            }

            while (configuration.getActivePlayer().equals(BoardConfiguration.Cell.BLACK)) {
                configuration.show();
                blackPlayer.makeMove(configuration);
            }
        }
        configuration.show();
    }
}
