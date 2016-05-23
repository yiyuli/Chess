import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;


public class ChessView {

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private JButton[][] chessBoardSquares = new JButton[Board.boardHeight][Board.boardWidth];
    private JButton start = new JButton("Start/Restart");
    private JButton resign = new JButton("Resign");
    private JButton undo = new JButton("Undo");
    private JLabel score = new JLabel("<html><h1>score</h1><b>white player: 0<br>black player: 0</html>");
    private JLabel message = new JLabel("Chess is ready to play!");

    private static final String COLS = "abcdefgh";

    ChessView(JFrame applet) {
        initializeGui(applet);
    }

    final void initializeGui(JFrame applet) {
        // set up the main GUI
        applet.getContentPane().add(gui);

        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        tools.add(start);
        tools.add(resign);
        tools.addSeparator();
        tools.add(undo);
        tools.addSeparator();
        tools.add(message);

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        chessBoard.add(new JLabel(""));

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        gui.add(tools, BorderLayout.PAGE_START);
        gui.add(chessBoard);
    }

    void setUpBoardAndPieces() {
        gui.add(score, BorderLayout.LINE_START);
        setUpBoardSquares();
        setUpPieceImages();

        for (int file = 0; file < 8; file++) {
            chessBoard.add(new JLabel(COLS.substring(file, file + 1), SwingConstants.CENTER));
        }
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {
                switch (rank) {
                    case 0:
                        chessBoard.add(new JLabel("" + (8 - file), SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[rank][file]);
                }
            }
        }
    }

    private void setUpBoardSquares() {
        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int rank = 0; rank < Board.boardHeight; rank++) {
            for (int file = 0; file < Board.boardWidth; file++) {
                JButton button = new JButton();
                button.setMargin(buttonMargin);

                if ((file % 2 == 1 && rank % 2 == 1) || (file % 2 == 0 && rank % 2 == 0)) {
                    button.setBackground(Color.WHITE);
                } else {
                    button.setBackground(Color.BLACK);
                }
                button.setOpaque(true);
                button.setBorderPainted(false);
                chessBoardSquares[rank][file] = button;
            }
        }
    }

    private void setUpPieceImages() {
        // set up piece images
        for (int file = 0; file < Board.boardWidth; file++) {
            chessBoardSquares[file][1].setIcon(getImageIcon("/White_Pawn.png"));
            chessBoardSquares[file][6].setIcon(getImageIcon("/Black_Pawn.png"));
        }

        chessBoardSquares[0][0].setIcon(getImageIcon("/White_Rook.png"));
        chessBoardSquares[1][0].setIcon(getImageIcon("/White_Knight.png"));
        chessBoardSquares[2][0].setIcon(getImageIcon("/White_Bishop.png"));
        chessBoardSquares[3][0].setIcon(getImageIcon("/White_Queen.png"));
        chessBoardSquares[4][0].setIcon(getImageIcon("/White_King.png"));
        chessBoardSquares[5][0].setIcon(getImageIcon("/White_Bishop.png"));
        chessBoardSquares[6][0].setIcon(getImageIcon("/White_Knight.png"));
        chessBoardSquares[7][0].setIcon(getImageIcon("/White_Rook.png"));

        chessBoardSquares[0][7].setIcon(getImageIcon("/Black_Rook.png"));
        chessBoardSquares[1][7].setIcon(getImageIcon("/Black_Knight.png"));
        chessBoardSquares[2][7].setIcon(getImageIcon("/Black_Bishop.png"));
        chessBoardSquares[3][7].setIcon(getImageIcon("/Black_Queen.png"));
        chessBoardSquares[4][7].setIcon(getImageIcon("/Black_King.png"));
        chessBoardSquares[5][7].setIcon(getImageIcon("/Black_Bishop.png"));
        chessBoardSquares[6][7].setIcon(getImageIcon("/Black_Knight.png"));
        chessBoardSquares[7][7].setIcon(getImageIcon("/Black_Rook.png"));
    }

    private ImageIcon getImageIcon(String path) {
        BufferedImage image = null;
        File f = null;
        try {
            try {
                f = new File(this.getClass().getResource(path).toURI());
            } catch (URISyntaxException e) {

            }
            image = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ImageIcon(image);
    }

    void addStartListener(ActionListener a) {
        start.addActionListener(a);
    }

    void addResignListener(ActionListener a) {
        resign.addActionListener(a);
    }

    void addUndoListener(ActionListener a) {
        undo.addActionListener(a);
    }

    void addPieceListener(ActionListener a) {
        for (JButton[] buttons : chessBoardSquares) {
            for (JButton button : buttons) {
                button.addActionListener(a);
            }
        }
    }

    final JComponent getGui() {
        return gui;
    }

    int findPiecePosition(JButton buttonToFound) {
        for (int i = 0; i < Board.boardWidth; i++) {
            for (int j = 0; j < Board.boardHeight; j++) {
                if (buttonToFound == chessBoardSquares[i][j]) return j * Board.boardHeight + i;
            }
        }

        return -1;
    }

    void updateMessage(String newMessage) {
        message.setText(newMessage);
    }

    void updateScore(int whiteScore, int blackScore) {
        score.setText("<html><h1>score</h1><b>white player: " + whiteScore + "<br>black player: " + blackScore + "</html>");
    }
}