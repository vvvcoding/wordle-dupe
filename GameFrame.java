/*
 * Wordle Dupe took 4 days to complete
 * First semi-solo project. Can most likely be faster and cleaner
 * Spanish function doesn't work because I couldn't find a list of words in Spanish
 * Every other function works well
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameFrame extends JFrame implements ActionListener, KeyListener{
    
    //Fields
    private int maxCols = 5;
    private int maxRows = 5;
    JButton buttons[][];    //wordle grid
    JPanel buttonsPanel;    //panel that holds the wordle grid
    JLabel winningWordLabel, gameStatusLabel, timerLabel;   //labels at the top and bottom of the frame
    private String winningWord = "";    //contains the word that wins the game
    private int currI = 0;  //current row
    private int currJ = 0;  //current column
    private int letterCounter = 0;
    private boolean gameOver = false;   //if game is still running
    private int timerSetting = 0;   //if there is a timer
    private int languageSetting = 0;    //english or spanish
    private int sizeSetting = 0;    //5x5 or 6x6
    private String fullWordList = "";   //list of all words of one size
    private String commonWordList = ""; //list of possible words to win
    private int fullListSize = 12972;   //size of full list of words
    private int commonListSize = 3014;    //size of common list of words
    private int timeLeft = 90;   //time left on timer
    private Timer gameTimer;    //game timer

    //Constructor
    public GameFrame(int timerSetting, int languageSetting, int sizeSetting) {

        //Game frame settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setSize(900,1000);

        //Set settings
        this.timerSetting = timerSetting;
        this.languageSetting = languageSetting;
        this.sizeSetting = sizeSetting;

        if (sizeSetting == 1) {
            maxCols = 6;
            maxRows = 6;
        }

        //Create the button panel
        buttons = new JButton[maxRows][maxCols];
        buttonsPanel = new JPanel();
        buttonsPanel.setVisible(true);
        buttonsPanel.setLayout(new GridLayout(maxRows, maxCols));

        for (int i = 0; i < buttons.length; i ++) {
            for (int j = 0; j < buttons[0].length; j ++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setForeground(Color.BLACK);
                
                //Disables buttons
                buttons[i][j].setEnabled(false);

                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                buttonsPanel.add(buttons[i][j]);
            }
        }

        this.add(buttonsPanel);
        this.setTitle("Wordle Dupe");
        this.addKeyListener(this);

        //Create the bottom panel
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 2));

        //Create and configure labels
        gameStatusLabel = new JLabel("Game Status: In Progress", SwingConstants.CENTER);
        gameStatusLabel.setFont(new Font("Arial", Font.BOLD, 20));

        winningWordLabel = new JLabel("Winning Word: ???", SwingConstants.CENTER);
        winningWordLabel.setFont(new Font("Arial", Font.BOLD, 20));

        //Add labels to the label panel
        labelPanel.add(gameStatusLabel);
        labelPanel.add(winningWordLabel);

        // Add label panel to the SOUTH region of the frame
        this.add(labelPanel, BorderLayout.SOUTH);

        //Set size and color of bottom panel
        labelPanel.setPreferredSize(new Dimension(900, 100));
        labelPanel.setBackground(Color.GRAY);

        //Create top panel
        if (timerSetting == 1) {
            //Timer mode
            JPanel timerPanel = new JPanel();
            timerPanel.setLayout(null);
            this.add(timerPanel, BorderLayout.NORTH);
            timerPanel.setPreferredSize(new Dimension(900, 100));
            timerPanel.setBackground(Color.GRAY);

            //Create and style timer label
            timerLabel = new JLabel("Time Left: " + timeLeft);
            timerLabel.setFont(new Font("Impact", Font.TRUETYPE_FONT, 50));
            timerLabel.setBounds(300, 15, 300, 50);
            timerPanel.add(timerLabel);

            //Create and customize timer object
            timeLeft = 90;
            gameTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (timeLeft > 0) {
                        //Decrement timer
                        timeLeft --;
                        timerLabel.setText("Time Left: " + timeLeft);
                    } else {
                        //If no time is left, game over lost
                        gameOverLost();
                        gameTimer.stop();
                    }
                }
            });
            //Start timer
            gameTimer.start();
        } else {
            //Regular panel
            JPanel regularTopPanel = new JPanel();
            regularTopPanel.setLayout(null);
            this.add(regularTopPanel, BorderLayout.NORTH);
            regularTopPanel.setPreferredSize(new Dimension(900, 100));
            regularTopPanel.setBackground(Color.GRAY);

            //Create and style label
            JLabel wordleLabel = new JLabel("Wordle Dupe");
            wordleLabel.setFont(new Font("Georgia", Font.TRUETYPE_FONT, 50));
            wordleLabel.setBounds(300, 15, 300, 50);
            regularTopPanel.add(wordleLabel);
        }

        //Repaint and revalidate are used to refresh the frame after
        //everything has been added
        this.repaint();
        this.revalidate();

        //Places frame in the middle of the screen
        this.setLocationRelativeTo(null);

        //Get a random word
        fileForSize();
        winningWord = getWord(commonListSize, commonWordList);
        System.out.println(winningWord);
    }

    //Methods
    public void chooseWordsFile() {
        if (languageSetting == 0) {
            //0 = english file

        } else if (languageSetting == 1) {
            //1 = spanish file

        }
    }

    public void fileForSize() {
        if (sizeSetting == 0) {
            //0 = 5 letter words
            fullWordList = "words.txt";
            commonWordList = "WORDS";
            fullListSize = 12972;
            commonListSize = 3014;
        } else if (sizeSetting == 1) {
            //1 = 6 letter words
            fullWordList = "words6.txt";
            commonWordList = "words6.txt";
            fullListSize = 15801;
            commonListSize = 15801;
        }
    }

    public String getWord(int fileSize, String fileToUse) {
        //Get random number
        Random rand = new Random();
        int lineNumber = rand.nextInt(fileSize) + 1;

        //Read the random word from the file at that index
        String line = "";
        try {
            line = Files.readAllLines(Paths.get(fileToUse)).get(lineNumber);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return line;
    }

    public boolean validateInput(int inputFileSize, String fileToUse) {
        int fileSize = inputFileSize;
        String userInputWord = "";

        //Get user input word
        for (int i = 0; i < maxCols; i ++) {
            userInputWord += buttons[currI][i].getText();
        }

        //Check if word is in the file
        try {
            int low = 1;
            int high = fileSize;
            
            while (low <= high) {
                int mid = (low + high) / 2;
                String middleWord = Files.readAllLines(Paths.get(fileToUse)).get(mid);

                if (middleWord.equals(userInputWord)) {
                    return true;
                } else if (middleWord.compareTo(userInputWord) < 0) {
                    //The user entered word is greater than the middle word
                    low = mid + 1;
                } else {
                    //The user entered word is less than the middle word
                    high = mid - 1;
                }
            }
        } catch (IOException ioe) {
            System.out.println("Error, file not found.");
            ioe.printStackTrace();
        }
        
        //If user entered word not found, return false
        return false;
    }

    public void checkLetters() {
        //Count of times no characters were added;
        int noneAddedCount = 0;

        //Store counts of each letter in an array
        char [] characters = winningWord.toCharArray();
        int [] targetCharCount = new int[26];
        for (int i = 0; i < characters.length; i ++) {
            char c = characters[i];
            targetCharCount[c - 'a']++;
        }

        //Check if need to set the block to green
        for (int i = 0; i < maxCols; i ++) {
            char greenChar = winningWord.charAt(i);
            String eachChar = buttons[currI][i].getText();
            char individualChar = eachChar.charAt(0);
            if (greenChar == individualChar) {
                buttons[currI][i].setBackground(Color.GREEN);
                targetCharCount[greenChar - 'a'] --;
            }
        }
        
        //Check if need to set the blocks to yellow or red
        for (int i = 0; i < maxCols; i ++) {
            String eachChar = buttons[currI][i].getText();
            char individualChar = eachChar.charAt(0);

            if (buttons[currI][i].getBackground() == Color.GREEN) {
                continue;
            }

            if (targetCharCount[individualChar - 'a'] > 0) {
                buttons[currI][i].setBackground(Color.YELLOW);
                targetCharCount[individualChar - 'a'] --;
                noneAddedCount ++;
            } else {
                buttons[currI][i].setBackground(Color.RED);
                noneAddedCount ++;
            }
        }

        //Check if win or lose
        if ((noneAddedCount == 0) && (gameOver == false)) {
            gameOverWin();
        }
        if (((currI + 1) > (maxCols - 1)) && (gameOver == false)) {
            gameOverLost();
        }
    }

    public void gameOverWin() {
        winningWordLabel.setText("Winning Word: " + winningWord);
        gameStatusLabel.setText("Game Status: You Won!");
        gameOver = true;
        if (timerSetting == 1){
            gameTimer.stop();
        }
    }

    public void gameOverLost() {
        winningWordLabel.setText("Winning Word: " + winningWord);
        gameStatusLabel.setText("Game Status: You Lost!");
        gameOver = true;
        if (timerSetting == 1){
            gameTimer.stop();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if (gameOver == false) {
            //Get character pressed
            char typedChar = e.getKeyChar();

            if ((typedChar == KeyEvent.VK_BACK_SPACE) && (currJ > 0)) {
                //If backspace pressed
                buttons[currI][letterCounter - 1].setText("");
                if (letterCounter == maxCols) {
                    letterCounter --;
                } else {
                    letterCounter --;
                    currJ --;
                }
            } else if ((typedChar == KeyEvent.VK_ENTER) && (letterCounter == maxCols) && (currI < maxRows)) {
                //Check if user entered valid word
                if (validateInput(fullListSize, fullWordList) == false) {
                    System.out.println("Invalid input, re-enter a word.");
                } else {
                    //Check if letters match the random word
                    checkLetters();

                    //Reset pointers
                    currI ++;
                    currJ = 0;
                    letterCounter = 0;
                }
            } else if (((int)typedChar >= 97) && ((int)typedChar <= 122) && (currI < maxRows)) {
                //If any letter pressed
                if (buttons[currI][currJ].getText().isEmpty()) {
                    buttons[currI][currJ].setText(String.valueOf(typedChar));
                    buttons[currI][currJ].setFont(new Font("Comic Sans", Font.BOLD, 40));
                    buttons[currI][currJ].setForeground(Color.RED);
                    letterCounter ++;
                    if (currJ < (maxCols - 1)) {
                        currJ ++;
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}