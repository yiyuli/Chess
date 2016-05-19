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
        King king = new King(0, 4, Chess.WHITE);
        board.addPiece(king);
        Rook rook1 = new Rook(1, 0, Chess.BLACK);
        Rook rook2 = new Rook(0, 0, Chess.BLACK);
        board.addPiece(rook1);
        board.addPiece(rook2);
        int isCheckmated = board.checkCheckmatedOrStalemate(Chess.WHITE);
        assertEquals(Chess.CHECKMATE, isCheckmated);
    }

    @Test
    public void stalemateTest() {
        King king = new King(0, 4, Chess.WHITE);
        board.addPiece(king);
        Rook rook1 = new Rook(1, 0, Chess.BLACK);
        Rook rook2 = new Rook(7, 3, Chess.BLACK);
        Rook rook3 = new Rook(7, 5, Chess.BLACK);
        board.addPiece(rook1);
        board.addPiece(rook2);
        board.addPiece(rook3);
        int isStalemate = board.checkCheckmatedOrStalemate(Chess.WHITE);
        assertEquals(Chess.STALEMATE, isStalemate);
    }

    @Test
    public void normalStateTest() {
        King king = new King(0, 4, Chess.WHITE);
        board.addPiece(king);
        Rook rook1 = new Rook(1, 0, Chess.BLACK);
        board.addPiece(rook1);
        int isNormalState = board.checkCheckmatedOrStalemate(Chess.WHITE);
        assertEquals(Chess.NORMALSTATE, isNormalState);
    }

}
