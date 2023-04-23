/**
 * Tic Tac Toe Game using a simple GUI
 *
 * @author Wajd Mariam
 * @version April 8, 2023
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TicTacToe {
    public static final String PLAYER_X = "X"; // player using "X"
    public static final String PLAYER_O = "O"; // player using "O"
    public static final String EMPTY = ""; // Empty string
    private boolean player1 = false; // Initially set false to indicate that its not player1 turn
    private int playerScore1 = 0;
    private boolean player2 = false; // Initially set false to indicate that its not player2 turn
    private int playerScore2 = 0;
    private int tieGamesCounter = 0;

    private int numFreeSquares = 9;
    JFrame frame = new JFrame("Tic Tac Toe");
    // Score labels at the top of the frame:
    JLabel scoreLabels = new JLabel("Player 1 (X):" + playerScore1 + "     Player 2 (O):" + playerScore2 + "     Tie Games:" + tieGamesCounter);
    JPanel button_panel = new JPanel(); // panel that contains all the button objects
    JButton[][] buttons = new JButton[3][3]; // 2D array of button objects
    // Score labels at the bottom of the frame:
    JLabel gameStatus = new JLabel();

    /* The reset menu item */
    private JMenuItem newGame;

    /* The quit menu item */
    private JMenuItem quitItem;

    private int currentWinner = 0; // Initially set to 0, 1 for Player 1 (X), 2 for Player 2 (O)


    // Constructor:
    public TicTacToe() {

        frame.setSize(650, 650);
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        // MENU:
        JMenuBar menuBar = new JMenuBar(); // creating a JMenuBar object
        frame.setJMenuBar(menuBar); // add menu bar to our frame
        JMenu fileMenu = new JMenu("Options"); // create a menu
        menuBar.add(fileMenu); // and add to our menu bar
        newGame  = new JMenuItem("New"); // create a menu item called "New"
        fileMenu.add(newGame); // and add to our menu
        newGame.addActionListener(new ActionListener() { // add action listener
            @Override
            /**
             * Calls method startingPlayer and clearBoard to clear all buttons and variables for a new game.
             * param e button that was clicked
             */
            public void actionPerformed(ActionEvent e) {
                clearBoard(true);
                playGame();
            }
        });
        quitItem = new JMenuItem("Quit"); // create a menu item called "Quit"
        fileMenu.add(quitItem); // add to the menu object
        quitItem.addActionListener(new ActionListener() {
            @Override
            /**
             * Exits the application.
             */
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Shortcuts for the menu items:
        final int SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK)); // ctrl-n for new game
        quitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK)); // ctrl-q for quit game

        // LABEL:
        Font font = new Font("Arial", Font.BOLD, 25);
        // Score labels located at the top of the frame in the center.
        scoreLabels.setHorizontalAlignment(JLabel.CENTER);
        scoreLabels.setFont(font);
        contentPane.add(scoreLabels,BorderLayout.NORTH);
        // Game status label located at the bottom of the frame in the center.
        gameStatus.setHorizontalAlignment(JLabel.CENTER);
        gameStatus.setFont(font);
        contentPane.add(gameStatus, BorderLayout.SOUTH);

        // BUTTONS:
        // Setting the layout to grid layout
        button_panel.setLayout(new GridLayout(3,3));
        frame.add(button_panel);
        // adding buttons:
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j] = new JButton();
                button_panel.add(buttons[i][j]);
                buttons[i][j].setBackground(new Color(102, 255, 255));
                buttons[i][j].setFocusable(false);
                buttons[i][j].addActionListener(this::buttonClicked);
            }
        }

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // exit when we hit the "X"
        frame.setResizable(true); // we can resize it
        frame.setVisible(true); // it's visible
        playGame(); // starts the game.  and prompts the user to decide who starts the game first Player 1 (X) or Player 2(O)
    }

    /**
     * Starts  prompts the user to enter which players starts the game (Player 1 (X) or Player 2 (O))
     */
    public void playGame() {

        Object[] options = {"Player 1 (X)", "Player 2 (O)"};

        // show the option pane and get the user's choice
        int choice = JOptionPane.showOptionDialog(null,
                "Who wants to start first?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        // Player 1 starts:
        if (choice == JOptionPane.YES_OPTION) {
            player1 = true;
            player2 = false;
            gameStatus.setText("GAME IN PROGRESS - Player 1 Turn");
        }
        // Player 2 starts:
        else if (choice == JOptionPane.NO_OPTION) {
            player1 = false;
            player2 = true;
            gameStatus.setText("GAME IN PROGRESS - Player 2 Turn");
        }
    }

    /**
     * Sets everything up for a new game or a new round, depending on the "cleartype" parameter.
     * Marks all buttons in the Tic Tac Toe board as empty, resets the used variables, and prompts
     * the user to decides who plays first in the new round.
     *
     * @param boolean cleartype true -> new game, false -> new round.
     *
     */
    public void clearBoard(boolean clearType) {
        // clearType: true for clear everything (new game), false for clearing for a new game (to keep scores):
        if (clearType) {
            // resetting all scores to 0.
            playerScore1 = 0;
            playerScore2 = 0;
            tieGamesCounter = 0;
            changeLabels(playerScore1, playerScore2, tieGamesCounter); // updates all labels to 0
        }
        // Pausing the game for 0.5 seconds to reset:
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Resetting variables to default:
        player1 = false;
        player2 = false;
        numFreeSquares = 9;

        clearAllButtons();
        playGame();
    }

    /**
     * Clears all buttons and resets background color to original.
     */
    public void clearAllButtons() {
        // clearing buttons:
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(new Color(102, 255, 255));
            }
        }
    }

    /**
     * Sets the clicked-on button to either "X" or "O" and
     * @param ActionEvent e button that was clicked
     */
    public void buttonClicked (ActionEvent e) {
        Font gameFont = new Font("Arial", Font.BOLD, 50);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (e.getSource() == buttons[i][j]) {
                    // if it is player 1's turn:
                    if (player1) {
                        // if current button is empty
                        if (buttons[i][j].getText() == EMPTY) {
                            buttons[i][j].setText(PLAYER_X);
                            buttons[i][j].setFont(gameFont);
                            player1 = false;
                            player2 = true;
                            numFreeSquares--;
                            gameStatus.setText("GAME IN PROGRESS - Player 2 Turn");
                            if (numFreeSquares <= 6) {
                                haveWinner(i, j);
                            }
                        } break;
                    }
                    else {
                        if (buttons[i][j].getText() == EMPTY) {
                            buttons[i][j].setText(PLAYER_O);
                            buttons[i][j].setFont(gameFont);
                            buttons[i][j].setFont(gameFont);
                            player1 = true;
                            player2 = false;
                            numFreeSquares--;
                            gameStatus.setText("GAME IN PROGRESS - Player 1 Turn");
                            if (numFreeSquares <= 6) {
                                haveWinner(i, j);
                            }
                        } break;
                    }
                }
            }
        }
    }


    /**
     * This method updates the score labels for player 1 and 2 and the tie games score.
     *
     * @param int playerLabel1 player 1 (X) score
     * @param int playerLabel2 player 2 (O) score
     * @param int tieGamesLabel tie games score
     *
     */
    public void changeLabels(int playerLabel1, int playerLabel2, int tieGamesLabel) {
        scoreLabels.setText("Player 1 (X):" + playerLabel1 + "     Player 2 (O):" + playerLabel2 + "     Tie Games:" + tieGamesLabel);
    }


    /**
     * Returns true if filling the given buttons gives us a winner, and false otherwise.
     *
     * @param int row of square just set
     * @param int col of square just set
     *
     * @return true if we have a winner, false otherwise
     */
    public boolean haveWinner(int row, int col)
    {
        // check row "row"
        if ( buttons[row][0].getText() == buttons[row][1].getText() && buttons[row][0].getText() == (buttons[row][2].getText())) {
            displayWinner(row, buttons[row][0].getText(), 0);
            return true;
        }

        // check column "col"
        if ( buttons[0][col].getText() == buttons[1][col].getText() && buttons[0][col].getText() == (buttons[2][col].getText())) {
            displayWinner(col , buttons[0][col].getText(), 1);
            return true;
        }

        // if row=col check one diagonal
        if (row==col)
            if ( buttons[0][0].getText() == buttons[1][1].getText() && buttons[0][0].getText() == (buttons[2][2].getText())) {
                displayWinner(0, buttons[0][0].getText(), 2);
                return true;
            }

        // if row=2-col check other diagonal
        if (row==2-col)
            if ( buttons[0][2].getText() == buttons[1][1].getText() && buttons[0][2].getText() == (buttons[2][0].getText())) {
                displayWinner(0, buttons[0][2].getText(), 3);
                return true;
            }

        // Check for a tie game:
        if (numFreeSquares == 0) { // If the number of free squares == 0 and no winner -> tie game:
            tieGame();
        }
        return false;
    }


    /**
     * Sets and displays the "tie game" conditions
     */
    public void tieGame()
    {
        allRedButtons(); // Turns all buttons to red to indicate no winners.
        tieGamesCounter += 1;
        changeLabels(playerScore1, playerScore2, tieGamesCounter);
        gameStatus.setText("Game Paused"); // Changes the text at the bottom of the frame.
        // Pop-up menu to indicate a tie game.
        JOptionPane.showMessageDialog(frame, "TIE GAME!", "Tic Tac Toe",  JOptionPane.INFORMATION_MESSAGE );
        clearBoard(false);
    }


    /**
     * Sets all the buttons' background color to red.
     */
    public void allRedButtons() {
        for(int i=0; i<3; i++) {
            for (int j=0; j<3; j++) {
                buttons[i][j].setBackground(new Color(255, 0, 0));
            }
        }
    }

    /**
     *
     * @param int key row or column number
     * @param String symbol string "X" or string "O"
     * @param int flag determines which buttons turn green
     */
    public void displayWinner(int key, String player, int flag) {
        // raising a flag indication which player won:

        // Determining which player won the game:
        if (player.equals(PLAYER_X)) { // Player 1 (X) won:
            playerScore1 += 1;
            changeLabels(playerScore1, playerScore2, tieGamesCounter);
            currentWinner = 1;
        }
        else {
            playerScore2 += 1;
            changeLabels(playerScore1, playerScore2, tieGamesCounter);
            currentWinner = 2;
        }

        // Changing all the buttons to red first.
        allRedButtons();

        // then, changing the color of the selected buttons that created a win condition to green:
        // Condition 1: flag == 0 -> one of the rows created a win condition:
        if (flag == 0) {
            for(int i=0; i<3; i++) {
                buttons[key][i].setBackground(new Color(0, 255, 0));
            }
        }
        // Condition 2: flag == 1 -> one of the columns created a win condition:
        else if (flag == 1) {
            for(int j=0; j<3; j++) {
                buttons[j][key].setBackground(new Color(0, 255, 0));
            }
        }
        // Condition 3: flag == 2 -> diagonal created a win condition:
        else if (flag == 2) {
            buttons[0][0].setBackground(new Color(0, 255, 0));
            buttons[1][1].setBackground(new Color(0, 255, 0));
            buttons[2][2].setBackground(new Color(0, 255, 0));
        }
        // Condition 4: flag == 3-> other diagonal created a win condition:
        else {
            buttons[0][2].setBackground(new Color(0, 255, 0));
            buttons[1][1].setBackground(new Color(0, 255, 0));
            buttons[2][0].setBackground(new Color(0, 255, 0));
        }

        // Player 1 (X) won the game:
        if (currentWinner == 1) {
            gameStatus.setText("GAME PAUSED");
            JOptionPane.showMessageDialog(frame, "Player 1 Won!", "Tic Tac Toe",  JOptionPane.INFORMATION_MESSAGE );
            clearBoard(false);
        }
        // Player 2 (O) won the game:
        else {
            gameStatus.setText("GAME PAUSED");
            JOptionPane.showMessageDialog(frame, "Player 2 Won!", "Tic Tac Toe", JOptionPane.INFORMATION_MESSAGE);
            clearBoard(false);
        }
    }
}
