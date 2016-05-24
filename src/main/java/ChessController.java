import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChessController {
    private ChessController controller = this;
    private JFrame frame;
    private boolean game_started = false;
    private int whiteScore = 0;
    private int blackScore = 0;
    private CommandManager commandManager = new CommandManager();

    Board board;
    ChessView view;
    JButton pieceToMove = new JButton();
    int currentPlayer;

    public void init() {
        board = new Board();
        frame = new JFrame("Chess");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationByPlatform(true);
        view = new ChessView(frame);
        display();

        view.addStartListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!game_started) {
                    resetGame();
                    game_started = true;
                } else {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure to restart?", "Restart", JOptionPane.YES_NO_OPTION);
                    if (confirm == 0) {
                        resetGame();
                    }
                }
            }
        });

        view.addResignListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure to resign?", "Resign", JOptionPane.YES_NO_OPTION);
                if (confirm == 0) {
                    if (currentPlayer == Board.WHITE) blackScore++;
                    else whiteScore++;
                    resetGame();
                }
            }
        });

        view.addUndoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (commandManager.isUndoAvailable()) {
                    commandManager.undo();
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to undo.");
                }
            }
        });
    }

    private void addPiecesListener() {
        view.addPieceListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton button = (JButton) e.getSource();
                if (pieceToMove.getIcon() == null) {
                    pieceToMove = button;
                } else {
                    int pieceToMovePosition = view.findPiecePosition(pieceToMove);
                    int pieceMovedPosition = view.findPiecePosition(button);
                    int startRank = 7 - pieceToMovePosition / 8;
                    int startFile = pieceToMovePosition % 8;
                    int endRank = 7 - pieceMovedPosition / 8;
                    int endFile = pieceMovedPosition % 8;
                    if (board.canMovePiece(startRank, startFile, endRank, endFile, currentPlayer)) {
                        commandManager.executeCommand(new MovePieceCommand(controller, button, startRank, startFile, endRank, endFile));
                        /*
                        button.setIcon(pieceToMove.getIcon());
                        pieceToMove.setIcon(null);
                        currentPlayer = currentPlayer == Board.WHITE ? Board.BLACK : Board.WHITE;
                        String player = currentPlayer == Board.WHITE ? "White" : "Black";
                        view.updateMessage(player + " player's turn");
                        */
                        String player = currentPlayer == Board.WHITE ? "White" : "Black";
                        detectGameState(player);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Illegal Move.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    pieceToMove = new JButton();
                }
            }
        });
    }

    private void detectGameState(String player) {
        int gameState = board.checkCheckmatedOrStalemate(currentPlayer);
        switch (gameState) {
            case Board.CHECKMATE:
                JOptionPane.showMessageDialog(null, player + " is checkmated");
                if (currentPlayer == Board.WHITE) blackScore++;
                else whiteScore++;
                resetGame();
                break;
            case Board.STALEMATE:
                JOptionPane.showMessageDialog(null, "Stalemate");
                resetGame();
                break;
            case Board.CHECKED:
                JOptionPane.showMessageDialog(null, player + " is checked");
                break;
            case Board.NORMALSTATE:
                break;
            default:
                break;
        }
    }

    private void resetGame() {
        view.getGui().removeAll();
        view.initializeGui(frame);
        view.setUpBoardAndPieces();
        addPiecesListener();
        view.updateMessage("White player's turn");
        view.updateScore(whiteScore, blackScore);
        currentPlayer = Board.WHITE;
        display();
        board.clear();
        board.init();
        commandManager = new CommandManager();
    }

    private void display() {
        frame.add(view.getGui());
        frame.pack();
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChessController controller = new ChessController();
                controller.init();
            }
        });
    }
}
