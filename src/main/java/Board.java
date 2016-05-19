import java.util.ArrayList;

/**
 * Class to control the board. Used to add, move, remove pieces, and check whether a checkmate or stalemate occur
 */
class Board {
    static final int boardWidth = 8;
    static final int boardHeight = 8;

    private Piece[][] squares;
    private Piece whiteKing;
    private Piece blackKing;
    private ArrayList<Piece> whitePieces;
    private ArrayList<Piece> blackPieces;

    /**
     * Constructor for board, used to initialize the board
     */
    Board() {
        squares = new Piece[boardWidth][boardHeight];
        whitePieces = new ArrayList<Piece>();
        blackPieces = new ArrayList<Piece>();
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

    /**
     * Determine whether a move is legal given the previous and current position. If not, return false.
     * If Yes, move the piece and update the board.
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank row index of end position
     * @param endFile column index of end position
     * @param playerId determine the player
     *
     * @return boolean whether a move is successful
     */
    boolean movePiece(int startRank, int startFile, int endRank, int endFile, int playerId) {
        /* check move is valid */
        if (outOfBoard(startRank, startFile) || outOfBoard(endRank, endFile)) return false;
        Piece startPiece = squares[startRank][startFile];
        Piece endPiece = squares[endRank][endFile];

        if (startPiece == null) return false;
        if (!startPiece.isValidMove(startRank, startFile, endRank, endFile, playerId, this)) return false;
        if (checkedAfterMove(startRank, startFile, endRank, endFile, playerId, startPiece, endPiece)) return false;

        /* update piece position */
        updatePiecePosition(startRank, startFile, endRank, endFile, playerId, startPiece, endPiece);

        return true;
    }

    /**
     * Check the game state
     *
     * @param playerId determine the player
     * @return game state: checkmate, stalemate, normal state
     */
    int checkCheckmatedOrStalemate(int playerId) {
        if (noLegalMoves(playerId)) {
            if (checked(playerId))
                return Chess.CHECKMATE;
            else
                return Chess.STALEMATE;
        }

        return Chess.NORMALSTATE;
    }

    /**
     * Return the piece given its position on the board
     *
     * @param rank  row index
     * @param file column index
     *
     * @return piece object
     */
    Piece getSquare(int rank, int file) {
        return squares[rank][file];
    }

    /**
     * Add a piece on the board
     *
     * @param piece piece to be added
     */
    void addPiece(Piece piece) {
        int rank = piece.rank;
        int file = piece.file;
        int playerId = piece.playerId;
        ArrayList<Piece> playerPieces = playerId == Chess.WHITE ? whitePieces : blackPieces;
        squares[rank][file] = piece;
        playerPieces.add(piece);
        if (piece instanceof King) {
            if (playerId == Chess.WHITE)
                whiteKing = piece;
            else
                blackKing = piece;
        }
    }

    /**
     * Clear the board
     */
    void clear() {
        for (int rank = 0; rank < boardHeight; rank++) {
            for (int file = 0; file < boardWidth; file++) {
                squares[rank][file] = null;
            }
        }
        whitePieces.clear();
        blackPieces.clear();
        whiteKing = null;
        blackKing = null;
    }

    private boolean outOfBoard(int rank, int file) {
        return rank < 0 || rank > Board.boardHeight || file < 0 || file > Board.boardWidth;
    }

    private boolean noLegalMoves(int playerId) {
        boolean noLegalMoves = true;
        ArrayList<Piece> playerPieces = playerId == Chess.WHITE ? whitePieces : blackPieces;

        outerLoop:
        for (Piece piece : playerPieces) {
            int startRank = piece.rank;
            int startFile = piece.file;
            for (int rank = 0; rank < boardHeight; rank++) {
                for (int file = 0; file < boardWidth; file++) {
                    if (piece.isValidMove(startRank, startFile, rank, file, playerId, this)) {
                        Piece startPiece = squares[startRank][startFile];
                        Piece endPiece = squares[rank][file];
                        if (!checkedAfterMove(startRank, startFile, rank, file, playerId, startPiece, endPiece)) {
                            noLegalMoves = false;
                            break outerLoop;
                        }
                    }
                }
            }
        }

        return noLegalMoves;
    }

    private boolean checkedAfterMove(int startRank, int startFile, int endRank, int endFile, int playerId, Piece startPiece, Piece endPiece) {
        boolean checkedAfterMove = false;
        updatePiecePosition(startRank, startFile, endRank, endFile, playerId, startPiece, endPiece);
        if (checked(playerId)) checkedAfterMove = true;
        restorePiecePosition(startRank, startFile, endRank, endFile, playerId, startPiece, endPiece);

        return checkedAfterMove;
    }

    private boolean checked(int playerId) {
        ArrayList<Piece> componentPieces = playerId == Chess.WHITE ? blackPieces : whitePieces;
        Piece playerKing = playerId == Chess.WHITE ? whiteKing : blackKing;
        if (playerKing == null) return false;
        boolean checked = false;
        for (Piece piece : componentPieces) {
            if (piece.isValidMove(piece.rank, piece.file, playerKing.rank, playerKing.file, 1 - playerId, this)) {
                checked = true;
                break;
            }
        }

        return checked;
    }

    private void updatePiecePosition(int startRank, int startFile, int endRank, int endFile, int playerId, Piece startPiece, Piece endPiece) {
        if (endPiece != null) {
            ArrayList<Piece> componentPieces = 1 - playerId == Chess.WHITE ? whitePieces : blackPieces;
            componentPieces.remove(endPiece);
        }
        squares[endRank][endFile] = startPiece;
        squares[startRank][startFile] = null;
        startPiece.rank = endRank;
        startPiece.file = endFile;
    }

    private void restorePiecePosition(int startRank, int startFile, int endRank, int endFile, int playerId, Piece startPiece, Piece endPiece) {
        if (endPiece != null) {
            ArrayList<Piece> componentPieces = 1 - playerId == Chess.WHITE ? whitePieces : blackPieces;
            componentPieces.add(endPiece);
        }
        squares[startRank][startFile] = startPiece;
        squares[endRank][endFile] = endPiece;
        startPiece.rank = startRank;
        startPiece.file = startFile;
    }
}
