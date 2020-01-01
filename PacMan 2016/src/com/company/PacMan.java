package com.company;

import java.awt.event.KeyEvent;

/**
 * the graphical representation of pacMan
 */
public class PacMan extends Character {
    private final int STARTINGLIVES = 3;
    private int lives;
    private String currentDirection;
    private String closed;
    private int deathTimer;

    /**
     * initalizes all of pacMans requirements
     */
    public PacMan() {
        lives = STARTINGLIVES;
        deathTimer = 0;
        xAxisVisualSpawn = 260;
        yAxisVisualSpawn = 340;
        color = 5;
        mapLayout = new MapLayout();
        xAxisVisual = xAxisVisualSpawn;
        yAxisVisual = yAxisVisualSpawn;
        lastAxisXVisual = xAxisVisualSpawn;
        lastAxisYVisual = yAxisVisualSpawn;
        animationTimer = 0;
        deltaX = 1;
        right = "Resources/pacman_0_2.png";
        down = "Resources/pacman_1_2.png";
        left = "Resources/pacman_2_2.png";
        up = "Resources/pacman_3_2.png";
        closed = "Resources/pacman_0_0.png";
        setOrientation(right);
        alive = true;
    }

    /**
     * @param direction the direction that the player input was on the keyboard
     *                  moves the player in the direction that the player pressed
     */
    public void setDirection(int direction) {
        int pacmanX = mapLayout.getPacManX();
        int pacmanY = mapLayout.getPacManY();
        if (!mapLayout.getPacManAlive()) {
            deathTimer++;
            mapLayout.setMapPosition(pacmanY, pacmanX, 0);
            xAxisVisual = xAxisVisualSpawn;
            yAxisVisual = yAxisVisualSpawn;
            lastAxisXVisual = xAxisVisualSpawn;
            lastAxisYVisual = yAxisVisualSpawn;
            mapLayout.setMapPosition(ySpawnMap, xSpawnMap, 5);
            if (deathTimer == 5) {
                mapLayout.setPacManAlive(true);
                deathTimer = 0;
                lives--;
            }
            setOrientation(left);
            deltaX = -1;
            deltaY = 0;
            currentDirection = left;

        } else if (direction == KeyEvent.VK_UP &&
                mapLayout.noCollisionDetected(mapLayout.getPacManY() - 1, mapLayout.getPacManX(), color)
                && moving) {

            currentDirection = up;
            setOrientation(up);
            move(0, -1);


        } else if (direction == KeyEvent.VK_DOWN &&
                mapLayout.noCollisionDetected(mapLayout.getPacManY() + 1, mapLayout.getPacManX(), color)
                && moving) {

            currentDirection = down;
            setOrientation(down);
            move(0, 1);
        } else if (direction == KeyEvent.VK_LEFT &&
                mapLayout.noCollisionDetected(mapLayout.getPacManY(), mapLayout.getPacManX() - 1, color)
                && moving) {
            currentDirection = left;
            setOrientation(left);
            move(-1, 0);


        } else if (direction == KeyEvent.VK_RIGHT &&
                mapLayout.noCollisionDetected(mapLayout.getPacManY(), mapLayout.getPacManX() + 1, color)
                && moving) {

            currentDirection = right;
            setOrientation(right);
            move(1, 0);
        } else {
            if (mapLayout.noCollisionDetected(mapLayout.getPacManY() + deltaY, mapLayout.getPacManX() + deltaX, color)) {
            move(deltaX,deltaY);

            }
        }
        animation();
    }

    /**
     * the animation for opening and closing the mouth
     */
    public void animation() {
        animationTimer++;

        if (animationTimer == 20) {
            setOrientation(currentDirection);

        }
        if (animationTimer == 40) {
            animationTimer = 0;

            setOrientation(closed);

        }
    }

    /**
     * @return the lives the player has still
     */
    public int getLives() {
        return lives;
    }

}
