import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for checking the game state: checkmate, stalemate, normal state.
 */
public class CheckmateAndStalemateTest {
    private Board board;

    @Before
    public void setUpBoard() {
        board = new Board();
        board.clear();
    }

    @Test
    public void checkmatedTest() {
        King king = new King(0, 4, Board.WHITE);
        board.addPiece(king);
        Rook rook1 = new Rook(1, 0, Board.BLACK);
        Rook rook2 = new Rook(0, 0, Board.BLACK);
        board.addPiece(rook1);
        board.addPiece(rook2);
        int isCheckmated = board.checkCheckmatedOrStalemate(Board.WHITE);
        assertEquals(Board.CHECKMATE, isCheckmated);
    }

    @Test
    public void stalemateTest() {
        King king = new King(0, 4, Board.WHITE);
        board.addPiece(king);
        Rook rook1 = new Rook(1, 0, Board.BLACK);
        Rook rook2 = new Rook(7, 3, Board.BLACK);
        Rook rook3 = new Rook(7, 5, Board.BLACK);
        board.addPiece(rook1);
        board.addPiece(rook2);
        board.addPiece(rook3);
        int isStalemate = board.checkCheckmatedOrStalemate(Board.WHITE);
        assertEquals(Board.STALEMATE, isStalemate);
    }

    @Test
    public void normalStateTest() {
        King king = new King(0, 4, Board.WHITE);
        board.addPiece(king);
        Rook rook1 = new Rook(1, 0, Board.BLACK);
        board.addPiece(rook1);
        int isNormalState = board.checkCheckmatedOrStalemate(Board.WHITE);
        assertEquals(Board.NORMALSTATE, isNormalState);
    }

}
