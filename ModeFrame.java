import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModeFrame extends JFrame implements ActionListener, KeyListener {
    
    //Fields
    JButton playButton, timerButton, languageButton, sixBySixButton;
    JLabel titleLable;
    int setTimer, setLanguage, setSize;

    //Constructor
    public ModeFrame() {

        //Game frame settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(900,900);
        this.getContentPane().setBackground(Color.BLUE);
        this.setLayout(null);
        this.setFocusable(true);
        this.setTitle("Mode Screen");
        this.setLocationRelativeTo(null);

        //Create and style buttons
        playButton = new JButton("Play");
        playButton.setBackground(Color.YELLOW);
        playButton.setFont(new Font("Impact", Font.TRUETYPE_FONT, 20));
        playButton.setEnabled(true);
        playButton.setFocusable(false);
        playButton.setBounds(325, 300, 250, 50);

        timerButton = new JButton("Timer: Off");
        timerButton.setBackground(Color.YELLOW);
        timerButton.setFont(new Font("Impact", Font.TRUETYPE_FONT, 20));
        timerButton.setEnabled(true);
        timerButton.setFocusable(false);
        timerButton.setBounds(325, 450, 250, 50);

        languageButton = new JButton("English");
        languageButton.setBackground(Color.YELLOW);
        languageButton.setFont(new Font("Impact", Font.TRUETYPE_FONT, 20));
        languageButton.setEnabled(true);
        languageButton.setFocusable(false);
        languageButton.setBounds(325, 600, 250, 50);

        sixBySixButton = new JButton("5x5");
        sixBySixButton.setBackground(Color.YELLOW);
        sixBySixButton.setFont(new Font("Impact", Font.TRUETYPE_FONT, 20));
        sixBySixButton.setEnabled(true);
        sixBySixButton.setFocusable(false);
        sixBySixButton.setBounds(325, 750, 250, 50);

        //Create and style lable
        titleLable = new JLabel("Wordle Dupe");
        titleLable.setBounds(250, 50, 400, 150);
        titleLable.setFont(new Font("Georgia", Font.TRUETYPE_FONT, 50));
        titleLable.setForeground(Color.WHITE);
        titleLable.setHorizontalAlignment(SwingConstants.CENTER);

        //Add all components to the frame
        this.add(titleLable);
        this.add(playButton);
        this.add(timerButton);
        this.add(languageButton);
        this.add(sixBySixButton);
        this.setVisible(true);

        //Initialize settings
        setTimer = 0;
        setLanguage = 0;
        setSize = 0;

        //Play button mouse listener
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //Get rid of current frame and create GameFrame
                dispose();
                new GameFrame(setTimer, setLanguage, setSize);
            }
        });

        //Timer button mouse listener
        timerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (setTimer == 0) {
                    //1 = timer is on
                    timerButton.setText("Timer: On");
                    setTimer = 1;
                } else {
                    //0 = timer is off
                    timerButton.setText("Timer: Off");
                    setTimer = 0;
                }
            }
        });

        //Language button mouse listener
        languageButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String currentLanguage = languageButton.getText();
                if (currentLanguage == "English") {
                    //1 = spanish words
                    languageButton.setText("Spanish");
                    setLanguage = 1;
                } else {
                    //0 = english words
                    languageButton.setText("English");
                    setLanguage = 0;
                }
            }
        });

        //6x6 button mouse listener
        sixBySixButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (setSize == 0) {
                    sixBySixButton.setText("6x6");
                    setSize = 1;
                } else {
                    sixBySixButton.setText("5x5");
                    setSize = 0;
                }
            }
        });
    }


    //Methods


    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'keyPressed'");
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