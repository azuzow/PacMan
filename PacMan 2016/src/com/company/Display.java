package com.company;



import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * this is where the graphics are displayed onto the the screen
 */
public class Display extends JPanel {
    private ImageIcon backGround;
    private ImageIcon gameOver;
    private ImageIcon win;
    private PacMan player;
    private Ghost redGhost;
    private Ghost blueGhost;
    private Ghost pinkGhost;
    private Ghost orangeGhost;
    private int keyCode;
    private MapLayout mapLayout;
    private Balls balls;
    private JPanel scorePanel;
    private JLabel playerScore;
    private JLabel[] pacManLives;


    private InGameTime clock;


    public Display() {
        createBoardComponents();
    }

    /**
     * creates all the components needed for the display to work
     * sets all the images to their necessary paths
     */
    public void createBoardComponents() {
        JLabel pacManLivesText = new JLabel();
        pacManLivesText.setText("Lives");
        pacManLivesText.setForeground(Color.WHITE);
        win = new ImageIcon("Resources/ok-hand-sign_1f44c.png");
        ImageIcon pacManIcon = new ImageIcon("Resources/pacman_0_2.png");
        pacManLives = new JLabel[3];
        backGround = new ImageIcon("Resources/pacmanBackground.png");
        gameOver = new ImageIcon("Resources/gameover.png");
        scorePanel = new JPanel();
        scorePanel.add(pacManLivesText);
        for (int i = 0; i < 3; i++) {
            pacManLives[i] = new JLabel();
            pacManLives[i].setIcon(pacManIcon);
            scorePanel.add(pacManLives[i]);
        }
        setLayout(new BorderLayout());
        add(scorePanel, BorderLayout.SOUTH);
        scorePanel.setBackground(Color.BLACK);
        playerScore = new JLabel("0");
        scorePanel.add(playerScore);
        scorePanel.setPreferredSize(new Dimension(575, 50));
        createGhosts();

        int delay = 10;
        balls = new Balls();
        JFrame window = new JFrame("PacMan -Alex Zuzow");
        clock = new InGameTime(delay);
        player = new PacMan();
        mapLayout = new MapLayout();
        setFocusable(true);

        window.add(this);
        window.setSize(565, 708);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

    }

    /**
     * @param g updates the graphics of the background,pacMan, the ghosts, and the score
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(backGround.getImage(), 0, 0, null);
        //  mapLayout.mapVisual(g);
        balls.drawBalls(g);
        player.updateCharacter(g);
        redGhost.updateCharacter(g);
        pinkGhost.updateCharacter(g);
        blueGhost.updateCharacter(g);
        orangeGhost.updateCharacter(g);
        updateScore(g);
    }

    /**
     * creates a timer and a keyListener
     * used to control the player movement and update the graphics
     */
    public class InGameTime implements ActionListener, KeyListener {
        Timer currentTime;

        public InGameTime(int delay) {
            currentTime = new Timer(delay, this);
            currentTime.start();
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);

        }

        /**
         * stops the timer
         */
        public void stopClock() {
            currentTime.stop();
        }

        /**
         * @param e sets the players orientation and direction they want to go
         *          updates ghosts routes based off of the new player position
         *          repaints after they all move
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            player.setDirection(keyCode);
            redGhost.chaseMode();
            pinkGhost.chaseMode();
            blueGhost.chaseMode();
            orangeGhost.chaseMode();

            repaint();

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        /**
         * @param e gets the direction that the player wants to go
         */
        @Override
        public void keyReleased(KeyEvent e) {
            keyCode = e.getKeyCode();

        }
    }

    /**
     * initializes all of the ghosts
     */
    public void createGhosts() {
        redGhost = new Ghost("Resources/ghost_0_0.png", "Resources/ghost_0_2.png",
                "Resources/ghost_0_4.png", "Resources/ghost_0_6.png", 242, 282, 6);
        pinkGhost = new Ghost("Resources/ghost_1_0.png", "Resources/ghost_1_2.png",
                "Resources/ghost_1_4.png", "Resources/ghost_1_6.png", 262, 282, 7);
        blueGhost = new Ghost("Resources/ghost_2_0.png", "Resources/ghost_2_2.png",
                "Resources/ghost_2_4.png", "Resources/ghost_2_6.png", 282, 282, 8);
        orangeGhost = new Ghost("Resources/ghost_3_0.png", "Resources/ghost_3_2.png",
                "Resources/ghost_3_4.png", "Resources/ghost_3_6.png", 302, 282, 9);

    }

    /**
     * @param g shows the game over screen when the player loses
     *          this method is used to update the panel that shows the score
     */
    public void updateScore(Graphics g) {

        if (mapLayout.getPlayerScore().getWin()) {
            g.drawImage(win.getImage(), 242, 262, null);
            clock.stopClock();
        }
        if (player.getLives() == 0) {
            deathScreen(g);
            clock.stopClock();
        }
        if (player.getLives() < 3) {
            scorePanel.remove(pacManLives[player.getLives()]);
        }

        playerScore.setForeground(Color.white);

        playerScore.setText("Score " + mapLayout.getPlayerScore().getTotalScore());

    }

    /**
     * @param g used to show the game over icon after player dies three times
     */
    public void deathScreen(Graphics g) {
        g.drawImage(gameOver.getImage(), 242, 262, null);

    }

}






























