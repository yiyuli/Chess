abstract class Piece {
    int rank;
    int file;
    int playerId;

    Piece(int rank, int file, int playerId) {
        this.rank = rank;
        this.file = file;
        this.playerId = playerId;
    }

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

    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean pawnValidMove = false;

        if (playerId == Chess.WHITE) {
            pawnValidMove |= (startFile == endFile && endRank - startRank == 2 && startRank == 1 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (startFile == endFile && endRank - startRank == 1 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (Math.abs(startFile - endFile) == 1 && endRank - startRank == 1 && board.getSquare(endRank, endFile) != null);
        } else {
            pawnValidMove |= (startFile == endFile && endRank - startRank == -2 && startRank == 6 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (startFile == endFile && endRank - startRank == -1 && board.getSquare(endRank, endFile) == null);
            pawnValidMove |= (Math.abs(startFile - endFile) == 1 && endRank - startRank == -1 && board.getSquare(endRank, endFile) != null);
        }

        return basicChecking && pawnValidMove;
    }

}

class Rook extends Piece {
    protected Rook(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean rookValidMove = isValidRookMove(startRank, startFile, endRank, endFile, board);

        return basicChecking && rookValidMove;
    }

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

class Knight extends Piece {
    protected Knight(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean knightValidMove = Math.pow(Math.abs(startRank - endRank), 2) + Math.pow(Math.abs(startFile - endFile), 2) == 5;

        return basicChecking && knightValidMove;
    }
}

class Bishop extends Piece {
    protected Bishop(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean bishopValidMove = isValidBishopMove(startRank, startFile, endRank, endFile, board);

        return basicChecking && bishopValidMove;
    }

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

class Queen extends Piece {
    protected Queen(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean isValidRookMove = Rook.isValidRookMove(startRank, startFile, endRank, endFile, board);
        boolean isValidBishopMove = Bishop.isValidBishopMove(startRank, startFile, endRank, endFile, board);
        boolean queenValidMove = isValidRookMove || isValidBishopMove;

        return basicChecking && queenValidMove;
    }
}

class King extends Piece {
    protected King(int rank, int file, int playerId) {
        super(rank, file, playerId);
    }

    @Override
    boolean isValidMove(int startRank, int startFile, int endRank, int endFile, int playerId, Board board) {
        boolean basicChecking = super.isValidMove(startRank, startFile, endRank, endFile, playerId, board);
        boolean kingValidMove = Math.abs(startRank - endRank) <= 1 && Math.abs(startFile - endFile) <= 1;

        return basicChecking && kingValidMove;
    }
}


