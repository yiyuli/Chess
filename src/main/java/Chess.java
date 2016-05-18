public class Chess {

    public static void main(String[] args) {
        Board board = new Board();
        int currentPlayer = Chess.WHITE;
        //
        int startRank = 0;
        int startFile = 0;
        int endRank = 0;
        int endFile = 0;
        //
        while (!board.checkCheckmatedOrStalemate(currentPlayer)) {
            board.updateBoard(startRank, startFile, endRank, endFile, currentPlayer);
            currentPlayer = 1 - currentPlayer;
        }
        // currentPlayer is checkMated and lost
    }

    public static final int WHITE = 0;
    public static final int BLACK = 1;

}
