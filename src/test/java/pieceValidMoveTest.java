import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class PieceValidMoveTest {
    private Board board;

    @Before
    public void setUpBoard() {
        board = new Board();
        board.clear();
    }

    @Test
    public void outOfBoardTest() {
        Rook rook = new Rook(3, 4, Chess.WHITE);
        board.addPiece(rook);
        boolean move1 = board.movePiece(3, 4, -1, 4, Chess.WHITE);
        assertFalse(move1);
        boolean move2 = board.movePiece(3, 4, 3, -3, Chess.WHITE);
        assertFalse(move2);
    }

    @Test
    public void notMovingTest() {
        Rook rook = new Rook(3, 4, Chess.WHITE);
        board.addPiece(rook);
        boolean move1 = board.movePiece(3, 4, 3, 4, Chess.WHITE);
        assertFalse(move1);
    }

    @Test
    public void diffPlayerStartPlace_samePlayerEndPlaceTest() {
        Rook rook = new Rook(3, 4, Chess.WHITE);
        board.addPiece(rook);
        boolean move1 = board.movePiece(3, 4, 7, 4, Chess.BLACK);
        assertFalse(move1);
        Pawn pawn = new Pawn(5, 4, Chess.WHITE);
        board.addPiece(pawn);
        boolean move2 = board.movePiece(3, 4, 5, 4, Chess.WHITE);
        assertFalse(move2);
    }

    @Test
    public void pawnMoveTest() {
        Pawn pawn = new Pawn(1, 2, Chess.WHITE);
        board.addPiece(pawn);
        boolean move1 = board.movePiece(1, 2, 3, 2, Chess.WHITE);
        assertTrue(move1);
        boolean move2 = board.movePiece(3, 2, 4, 2, Chess.WHITE);
        assertTrue(move2);
        Rook rook = new Rook(5, 3, Chess.BLACK);
        board.addPiece(rook);
        boolean move3 = board.movePiece(4, 2, 5, 3, Chess.WHITE);
        assertTrue(move3);
    }

    @Test
    public void rookMoveTest() {
        Rook rook = new Rook(3, 4, Chess.WHITE);
        board.addPiece(rook);
        boolean move1 = board.movePiece(3, 4, 6, 4, Chess.WHITE);
        boolean move2 = board.movePiece(6, 4, 6, 1, Chess.WHITE);
        assertTrue(move1);
        assertTrue(move2);
        Pawn pawn = new Pawn(6, 3, Chess.BLACK);
        board.addPiece(pawn);
        boolean move3 = board.movePiece(6, 1, 6, 4, Chess.WHITE);
        assertFalse(move3);
    }

    @Test
    public void knightMoveTest() {
        Knight knight = new Knight(3, 3, Chess.WHITE);
        board.addPiece(knight);
        boolean move1 = board.movePiece(3, 3, 4, 5, Chess.WHITE);
        boolean move2 = board.movePiece(4, 5, 6, 6, Chess.WHITE);
        assertTrue(move1);
        assertTrue(move2);
    }

    @Test
    public void bishopMoveTest() {
        Bishop bishop = new Bishop(3, 4, Chess.WHITE);
        board.addPiece(bishop);
        boolean move1 = board.movePiece(3, 4, 5, 6, Chess.WHITE);
        assertTrue(move1);
        Pawn pawn = new Pawn(3, 4, Chess.BLACK);
        board.addPiece(pawn);
        boolean move2 = board.movePiece(5, 6, 2, 3, Chess.WHITE);
        assertFalse(move2);
    }

    @Test
    public void queenMoveTest() {
        Queen queen = new Queen(3, 4, Chess.WHITE);
        board.addPiece(queen);
        boolean move1 = board.movePiece(3, 4, 5, 6, Chess.WHITE);
        assertTrue(move1);
        boolean move2 = board.movePiece(5, 6, 2, 6, Chess.WHITE);
        assertTrue(move2);
    }

    @Test
    public void kingMoveTest() {
        King king = new King(0, 3, Chess.WHITE);
        board.addPiece(king);
        boolean move1 = board.movePiece(0, 3, 1, 4, Chess.WHITE);
        boolean move2 = board.movePiece(1, 4, 2, 4, Chess.WHITE);
        assertTrue(move1);
        assertTrue(move2);
    }
}
