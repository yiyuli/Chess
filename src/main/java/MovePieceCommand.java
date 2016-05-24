import javax.swing.*;

public class MovePieceCommand {
    private ChessController controller;
    private JButton pieceToMove;
    private JButton pieceMoveTo;
    private Icon pieceMoveToIcon;
    private int currentPlayer;
    private int previousPlayer;
    private ChessView view;

    private Board board;
    private int startRank;
    private int startFile;
    private int endRank;
    private int endFile;
    private Piece startPiece;
    private Piece endPiece;

    public MovePieceCommand(ChessController controller, JButton pieceMoveTo, int startRank, int startFile, int endRank, int endFile) {
        this.controller = controller;
        this.pieceMoveTo = pieceMoveTo;
        this.pieceMoveToIcon = pieceMoveTo.getIcon();
        this.pieceToMove = controller.pieceToMove;
        this.view = controller.view;
        this.board = controller.board;
        this.currentPlayer = controller.currentPlayer;
        previousPlayer = currentPlayer;

        this.startRank = startRank;
        this.startFile = startFile;
        this.endRank = endRank;
        this.endFile = endFile;
        this.startPiece = controller.board.getSquare(startRank, startFile);
        this.endPiece = controller.board.getSquare(endRank, endFile);
    }

    public void execute() {
        board.updatePiecePosition(startRank, startFile, endRank, endFile, currentPlayer, startPiece, endPiece);

        pieceMoveTo.setIcon(pieceToMove.getIcon());
        pieceToMove.setIcon(null);
        controller.currentPlayer = currentPlayer == Board.WHITE ? Board.BLACK : Board.WHITE;
        currentPlayer = controller.currentPlayer;
        String player = currentPlayer == Board.WHITE ? "White" : "Black";
        view.updateMessage(player + " player's turn");
    }

    public void undo() {
        board.restorePiecePosition(startRank, startFile, endRank, endFile, currentPlayer, startPiece, endPiece);

        pieceToMove.setIcon(pieceMoveTo.getIcon());
        pieceMoveTo.setIcon(pieceMoveToIcon);
        controller.currentPlayer = previousPlayer;
        String player = previousPlayer == Board.WHITE ? "White" : "Black";
        view.updateMessage(player + " player's turn");
    }
}
