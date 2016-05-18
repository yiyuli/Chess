import java.util.ArrayList;

class Board {
    static final int boardWidth = 8;
    static final int boardHeight = 8;

    private Piece[][] squares;
    private Piece whiteKing;
    private Piece blackKing;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    Board() {
        squares = new Piece[boardWidth][boardHeight];
        /* initialize empty squares */
        for (int rank = 2; rank < 6; rank++) {
            for (int file = 0; file < boardWidth; file++) {
                squares[rank][file] = null;
            }
        }
        /* initialize pawns for both sides */
        for (int file = 0; file < boardWidth; file++) {
            squares[1][file] = new Pawn(1, file, Chess.WHITE);
            whitePieces.add(squares[1][file]);
        }

        for (int file = 0; file < boardWidth; file++) {
            squares[6][file] = new Pawn(6, file, Chess.BLACK);
            blackPieces.add(squares[6][file]);
        }
        /* initialize the rest pieces */
        squares[0][0] = new Rook(0, 0, Chess.WHITE);
        squares[0][7] = new Rook(0, 7, Chess.WHITE);
        squares[0][1] = new Knight(0, 1, Chess.WHITE);
        squares[0][6] = new Knight(0, 6, Chess.WHITE);
        squares[0][2] = new Bishop(0, 2, Chess.WHITE);
        squares[0][5] = new Bishop(0, 5, Chess.WHITE);
        squares[0][3] = new Queen(0, 3, Chess.WHITE);
        squares[0][4] = new King(0, 4, Chess.WHITE);
        whiteKing = squares[0][4];
        for (int file = 0; file < boardWidth; file++) whitePieces.add(squares[0][file]);

        squares[7][0] = new Rook(7, 0, Chess.BLACK);
        squares[7][7] = new Rook(7, 7, Chess.BLACK);
        squares[7][1] = new Knight(7, 1, Chess.BLACK);
        squares[7][6] = new Knight(7, 6, Chess.BLACK);
        squares[7][2] = new Bishop(7, 2, Chess.BLACK);
        squares[7][5] = new Bishop(7, 5, Chess.BLACK);
        squares[7][3] = new Queen(7, 3, Chess.BLACK);
        squares[7][4] = new King(7, 4, Chess.BLACK);
        blackKing = squares[7][4];
        for (int file = 0; file < boardWidth; file++) blackPieces.add(squares[7][file]);
    }

    boolean updateBoard(int startRank, int startFile, int endRank, int endFile, int playerId) {
        /* check move is valid */
        Piece piece = squares[startRank][startFile];
        if (piece == null) return false;
        if (!piece.isValidMove(startRank, startFile, endRank, endFile, playerId, this)) return false;

        /* update the new move */
        if (squares[endRank][endFile] != null) {
            ArrayList<Piece> componentPieces = 1 - playerId == Chess.WHITE ? whitePieces : blackPieces;
            componentPieces.remove(squares[endRank][endFile]);
        }
        squares[endRank][endFile] = piece;
        squares[startRank][startFile] = null;

        return true;
    }

    boolean checkCheckmatedOrStalemate(int playerId) {
        boolean checkmatedOrStalemate = false;
        if (noLegalMoves(playerId)) {
            if (checked(playerId)) {
                checkmatedOrStalemate = true;
            } else {
                checkmatedOrStalemate = true;
            }
        }

        return checkmatedOrStalemate;
    }

    Piece getSquare(int rank, int file) {
        return squares[rank][file];
    }

    private boolean noLegalMoves(int playerId) {

        return false;
    }

    private boolean checked(int playerId) {

        return false;
    }
}
