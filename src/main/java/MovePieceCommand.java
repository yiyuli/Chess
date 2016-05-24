import javax.swing.*;

public class MovePieceCommand {
    private ChessController controller;
    private JButton pieceToMove;
    private JButton pieceMoveTo;
    private Icon pieceMoveToIcon;
    private int currentPlayer;
    private int previousPlayer;
    private ChessView view;

    public MovePieceCommand(ChessController controller, JButton pieceMoveTo) {
        this.controller = controller;
        this.pieceMoveTo = pieceMoveTo;
        this.pieceMoveToIcon = pieceMoveTo.getIcon();
        this.pieceToMove = controller.pieceToMove;
        this.view = controller.view;
        this.currentPlayer = controller.currentPlayer;
        previousPlayer = currentPlayer;
    }

    public void execute() {
        pieceMoveTo.setIcon(pieceToMove.getIcon());
        pieceToMove.setIcon(null);
        controller.currentPlayer = currentPlayer == Board.WHITE ? Board.BLACK : Board.WHITE;
        currentPlayer = controller.currentPlayer;
        String player = currentPlayer == Board.WHITE ? "White" : "Black";
        view.updateMessage(player + " player's turn");
    }

    public void undo() {
        pieceToMove.setIcon(pieceMoveTo.getIcon());
        pieceMoveTo.setIcon(pieceMoveToIcon);
        controller.currentPlayer = previousPlayer;
        String player = previousPlayer == Board.WHITE ? "White" : "Black";
        view.updateMessage(player + " player's turn");
    }
}
