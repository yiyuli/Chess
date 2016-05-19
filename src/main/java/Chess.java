/**
 * Class for starting, controlling, and ending the game
 */
public class Chess {
    /**
     * Main method for the chess game
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        Board board = new Board();
        int currentPlayer = Chess.WHITE;
        //
        int startRank = 0;
        int startFile = 0;
        int endRank = 0;
        int endFile = 0;
        //
        while (board.checkCheckmatedOrStalemate(currentPlayer) == Chess.NORMALSTATE) {
            board.movePiece(startRank, startFile, endRank, endFile, currentPlayer);
            currentPlayer = 1 - currentPlayer;
        }
        // currentPlayer is checkMated and lost
    }

    static final int WHITE = 0;
    static final int BLACK = 1;
    static final int CHECKMATE = 1;
    static final int STALEMATE = 2;
    static final int NORMALSTATE = 0;
}
