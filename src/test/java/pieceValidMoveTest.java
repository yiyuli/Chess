import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the moving mechanism of all pieces
 */
public class PieceValidMoveTest {
    private Board board;

    @Before
    public void setUpBoard() {
        board = new Board();
        board.clear();
    }

    @Test
    public void outOfBoardTest() {
        Rook rook = new Rook(3, 4, Board.WHITE);
        board.addPiece(rook);
        boolean move1 = board.canMovePiece(3, 4, -1, 4, Board.WHITE);
        assertFalse(move1);
        boolean move2 = board.canMovePiece(3, 4, 3, -3, Board.WHITE);
        assertFalse(move2);
    }

    @Test
    public void notMovingTest() {
        Rook rook = new Rook(3, 4, Board.WHITE);
        board.addPiece(rook);
        boolean move1 = board.canMovePiece(3, 4, 3, 4, Board.WHITE);
        board.movePiece(3, 4, 3, 4, Board.WHITE);
        assertFalse(move1);
    }

    @Test
    public void diffPlayerStartPlace_samePlayerEndPlaceTest() {
        Rook rook = new Rook(3, 4, Board.WHITE);
        board.addPiece(rook);
        boolean move1 = board.canMovePiece(3, 4, 7, 4, Board.BLACK);
        assertFalse(move1);
        Pawn pawn = new Pawn(5, 4, Board.WHITE);
        board.addPiece(pawn);
        boolean move2 = board.canMovePiece(3, 4, 5, 4, Board.WHITE);
        assertFalse(move2);
    }

    @Test
    public void pawnMoveTest() {
        Pawn pawn = new Pawn(1, 2, Board.WHITE);
        board.addPiece(pawn);
        boolean move1 = board.canMovePiece(1, 2, 3, 2, Board.WHITE);
        board.movePiece(1, 2, 3, 2, Board.WHITE);
        assertTrue(move1);
        boolean move2 = board.canMovePiece(3, 2, 4, 2, Board.WHITE);
        board.movePiece(3, 2, 4, 2, Board.WHITE);
        assertTrue(move2);
        Rook rook = new Rook(5, 3, Board.BLACK);
        board.addPiece(rook);
        boolean move3 = board.canMovePiece(4, 2, 5, 3, Board.WHITE);
        board.movePiece(4, 2, 5, 3, Board.WHITE);
        assertTrue(move3);
    }

    @Test
    public void rookMoveTest() {
        Rook rook = new Rook(3, 4, Board.WHITE);
        board.addPiece(rook);
        boolean move1 = board.canMovePiece(3, 4, 6, 4, Board.WHITE);
        board.movePiece(3, 4, 6, 4, Board.WHITE);
        boolean move2 = board.canMovePiece(6, 4, 6, 1, Board.WHITE);
        board.movePiece(6, 4, 6, 1, Board.WHITE);
        assertTrue(move1);
        assertTrue(move2);
        Pawn pawn = new Pawn(6, 3, Board.BLACK);
        board.addPiece(pawn);
        boolean move3 = board.canMovePiece(6, 1, 6, 4, Board.WHITE);
        assertFalse(move3);
    }

    @Test
    public void knightMoveTest() {
        Knight knight = new Knight(3, 3, Board.WHITE);
        board.addPiece(knight);
        boolean move1 = board.canMovePiece(3, 3, 4, 5, Board.WHITE);
        board.movePiece(3, 3, 4, 5, Board.WHITE);
        boolean move2 = board.canMovePiece(4, 5, 6, 6, Board.WHITE);
        board.movePiece(4, 5, 6, 6, Board.WHITE);
        assertTrue(move1);
        assertTrue(move2);
    }

    @Test
    public void bishopMoveTest() {
        Bishop bishop = new Bishop(3, 4, Board.WHITE);
        board.addPiece(bishop);
        boolean move1 = board.canMovePiece(3, 4, 5, 6, Board.WHITE);
        board.movePiece(3, 4, 5, 6, Board.WHITE);
        assertTrue(move1);
        Pawn pawn = new Pawn(3, 4, Board.BLACK);
        board.addPiece(pawn);
        boolean move2 = board.canMovePiece(5, 6, 2, 3, Board.WHITE);
        assertFalse(move2);
    }

    @Test
    public void queenMoveTest() {
        Queen queen = new Queen(3, 4, Board.WHITE);
        board.addPiece(queen);
        boolean move1 = board.canMovePiece(3, 4, 5, 6, Board.WHITE);
        board.movePiece(3, 4, 5, 6, Board.WHITE);
        assertTrue(move1);
        boolean move2 = board.canMovePiece(5, 6, 2, 6, Board.WHITE);
        board.movePiece(5, 6, 2, 6, Board.WHITE);
        assertTrue(move2);
    }

    @Test
    public void kingMoveTest() {
        King king = new King(0, 3, Board.WHITE);
        board.addPiece(king);
        boolean move1 = board.canMovePiece(0, 3, 1, 4, Board.WHITE);
        board.movePiece(0, 3, 1, 4, Board.WHITE);
        boolean move2 = board.canMovePiece(1, 4, 2, 4, Board.WHITE);
        board.movePiece(1, 4, 2, 4, Board.WHITE);
        assertTrue(move1);
        assertTrue(move2);
    }
}
