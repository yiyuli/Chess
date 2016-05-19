/**
 * Abstract class for all pieces
 */
abstract class Piece {
    int rank;
    int file;
    int playerId;

    /**
     * Constructor for pieces
     *
     * @param rank     row index
     * @param file     column index
     * @param playerId determine the player
     */
    Piece(int rank, int file, int playerId) {
        this.rank = rank;
        this.file = file;
        this.playerId = playerId;
    }

    /**
     * Determine whether the move is legal
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean diffPlayerStartPlace = this.playerId != playerId;
        boolean notMoving = startRank == endRank && startFile == endFile;
        boolean samePlayerEndPlace = false;
        if (board.getSquare(endRank, endFile) != null) {
            samePlayerEndPlace = this.playerId == board.getSquare(endRank, endFile).playerId;
        }

        return !(diffPlayerStartPlace || notMoving || samePlayerEndPlace);
    }
}

class Pawn extends Piece {
    protected Pawn(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    /**
     * Determine whether the move is legal
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean pawnValidMove = isValidPawnMove(startRank, startFile, endRank, endFile, playerId, board);

        return basicChecking && pawnValidMove;
    }

    /**
     * /**
     * Check whether a move satisfies the rule of a pawn
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    static boolean isValidPawnMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean pawnValidMove = false;

        if (playerId == Board.WHITE) {
            pawnValidMove |= (startFile == endFile && endRank - startRank == 2 && startRank == 1 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (startFile == endFile && endRank - startRank == 1 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (Math.abs(startFile - endFile) == 1 && endRank - startRank == 1 && board.getSquare(endRank, endFile) != null);
        } else {
            pawnValidMove |= (startFile == endFile && endRank - startRank == -2 && startRank == 6 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (startFile == endFile && endRank - startRank == -1 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (Math.abs(startFile - endFile) == 1 && endRank - startRank == -1 && board.getSquare(endRank, endFile) != null);
        }
        return pawnValidMove;
    }

}

class Rook extends Piece {
    protected Rook(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    /**
     * Determine whether the move is legal
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean rookValidMove = isValidRookMove(startRank, startFile, endRank, endFile, board);

        return basicChecking && rookValidMove;
    }

    /**
     * Check whether a move satisfies the rule of a rook
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    static boolean isValidRookMove(int startRank, int startFile, int endRank, int endFile, Board board) {
        boolean validMove = startRank == endRank || startFile == endFile;
        boolean noObstacle = true;
        if (startRank == endRank) {
            int smallerFile = startFile > endFile ? endFile : startFile;
            for (int file = smallerFile + 1; file < smallerFile + Math.abs(startFile - endFile); file++) {
                if (board.getSquare(startRank, file) != null) noObstacle = false;
            }
        } else {
            int smallerRank = startRank > endRank ? endRank : startRank;
            for (int rank = smallerRank + 1; rank < smallerRank + Math.abs(startRank - endRank); rank++) {
                if (board.getSquare(rank, startFile) != null) noObstacle = false;
            }
        }
        return validMove && noObstacle;
    }
}

/**
 * Class for Knight piece
 */
class Knight extends Piece {
    protected Knight(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    /**
     * Determine whether the move is legal
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean knightValidMove = isValidKnightMove(startRank, startFile, endRank, endFile);

        return basicChecking && knightValidMove;
    }

    /**
     * Check whether a move satisfies the rule of a Knight
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @return boolean whether the move is legal
     */
    static boolean isValidKnightMove(int startRank, int startFile, int endRank, int endFile) {
        return Math.pow(Math.abs(startRank - endRank), 2) + Math.pow(Math.abs(startFile - endFile), 2) == 5;
    }
}

/**
 * Class for Bishop piece
 */
class Bishop extends Piece {
    protected Bishop(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    /**
     * Check whether the move is legal
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean bishopValidMove = isValidBishopMove(startRank, startFile, endRank, endFile, board);

        return basicChecking && bishopValidMove;
    }

    /**
     * Check whether a move satisfies the rule of a bishop
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    static boolean isValidBishopMove(int startRank, int startFile, int endRank, int endFile, Board board) {
        boolean validMove = Math.abs(startRank - endRank) == Math.abs(startFile - endFile);
        boolean noObstacle = true;
        int smallerRank = startRank < endRank ? startRank : endRank;
        int smallerFile = startFile < endFile ? startFile : endFile;
        int smallerDiff = Math.abs(startRank - endRank) < Math.abs(startFile - endFile) ? Math.abs(startRank - endRank) : Math.abs(startFile - endFile);

        for (int diagonalDistance = 1; diagonalDistance < smallerDiff; diagonalDistance++) {
            if (board.getSquare(smallerRank + diagonalDistance, smallerFile + diagonalDistance) != null) {
                noObstacle = false;
                break;
            }
        }

        return validMove && noObstacle;
    }
}

/**
 * Class for Queen piece
 */
class Queen extends Piece {
    protected Queen(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    /**
     * Determine whether the move is legal
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean isValidRookMove = Rook.isValidRookMove(startRank, startFile, endRank, endFile, board);
        boolean isValidBishopMove = Bishop.isValidBishopMove(startRank, startFile, endRank, endFile, board);
        boolean queenValidMove = isValidRookMove || isValidBishopMove;

        return basicChecking && queenValidMove;
    }
}

/**
 * Class for King piece
 */
class King extends Piece {
    protected King(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    /**
     * Determine whether the move is legal
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @param playerId  determine the player
     * @param board     store the board information, used to check if there is obstacle
     * @return boolean whether the move is legal
     */
    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean kingValidMove = isKingValidMove(startRank, startFile, endRank, endFile);

        return basicChecking && kingValidMove;
    }

    /**
     * Check whether a move satisfies the rule of a king
     *
     * @param startRank row index of starting position
     * @param startFile column index of starting position
     * @param endRank   row index of end position
     * @param endFile   column index of end position
     * @return boolean whether the move is legal
     */
    static boolean isKingValidMove(int startRank, int startFile, int endRank, int endFile) {
        return Math.abs(startRank - endRank) <= 1 && Math.abs(startFile - endFile) <= 1;
    }
}


