import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("TIC TOC TOE");
    JLabel label = new JLabel();
    JPanel panel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel scorePanel =new JPanel();
    JLabel labelX = new JLabel();
    JLabel labelO = new JLabel();

    JButton reset =new JButton("Reset");

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    int scoreX;
    int scoreO;
    boolean scoreCounted = false;  // sınıf seviyesinde tanımla


    TicTacToe() {

        //mac de ki duzeni sagladi
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        label.setBackground(Color.white);
        label.setForeground(Color.black);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setText("Tic-Toc-Toe");
        label.setOpaque(true);

        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        frame.add(panel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        scorePanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        scorePanel.add(labelX);
        scorePanel.add(labelO);
        scorePanel.add(reset);
        panel.add(scorePanel, BorderLayout.EAST);

        labelX.setText("X: 0");
        labelX.setFont(new Font("Arial", Font.BOLD, 20));
        labelX.setForeground(Color.black);

        labelO.setText("O: 0");
        labelO.setFont(new Font("Arial", Font.BOLD, 20));
        labelO.setForeground(Color.black);

        reset.setForeground(Color.red);


        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });




        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.white);
                tile.setForeground(Color.black);
                tile.setFont(new Font("Arial", Font.BOLD, 100));
                tile.setFocusable(false);
                tile.setOpaque(true); // Zorunlu - Arka plan rengini gösterebilmek için
                tile.setContentAreaFilled(true);

                tile.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return;

                        JButton clickedTile = (JButton) e.getSource();

                        if (clickedTile.getText().equals("")) {
                            clickedTile.setText(currentPlayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                                label.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }

        frame.setVisible(true);
    }

    void checkWinner() {
        // Horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;

            if (board[r][0].getText().equals(board[r][1].getText()) &&
                    board[r][1].getText().equals(board[r][2].getText())) {

                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }

                handleWin();
                return;
            }
        }

        // Vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;

            if (board[0][c].getText().equals(board[1][c].getText()) &&
                    board[1][c].getText().equals(board[2][c].getText())) {

                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }

                handleWin();
                return;
            }
        }

        // Diagonal
        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {

            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }

            handleWin();
            return;
        }

        // Anti-diagonal
        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {

            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            handleWin();
            return;
        }

        // Tie
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            label.setText("Tie!");
            gameOver = true;
        }
    }

    void handleWin() {
        gameOver = true;
        label.setText(currentPlayer + " is the winner!");

        if (currentPlayer.equals(playerX)) {
            scoreX++;
            labelX.setText("X: " + scoreX);
            if (scoreX == 3) {
                showWinnerPopup(playerX);
            }
        } else {
            scoreO++;
            labelO.setText("O: " + scoreO);
            if (scoreO == 3) {
                showWinnerPopup(playerO);
            }
        }
    }



    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.black);

    }




    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.white);
                board[r][c].setForeground(Color.black);
            }
        }
        turns = 0;
        gameOver = false;
        currentPlayer = playerX;
        label.setText("Tic-Toc-Toe");

    }

    void showWinnerPopup(String winner) {
        int response = JOptionPane.showOptionDialog(
                frame,
                winner + " kazandı! Skoru 3'e ulaştı.\nYeniden başlamak ister misiniz?",
                "Oyun Bitti",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Yeniden Başla"},
                "Yeniden Başla"
        );

        if (response == 0) {
            resetAll(); // skorlar + tahta sıfırlanır
        }
    }


    void resetAll() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setBackground(Color.white);
                board[r][c].setForeground(Color.black);
            }
        }
        scoreX = 0;
        scoreO = 0;
        labelX.setText("X: 0");
        labelO.setText("O: 0");
        label.setText("Tic-Toc-Toe");


    }




    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.black);
    }


}
