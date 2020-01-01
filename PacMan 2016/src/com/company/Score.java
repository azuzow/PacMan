package com.company;

/**
 * the score tracker for the player
 */
public class Score {
    private boolean win;
    private int totalScore;
    private int ghost;
    private int ghostEatenCounter;
    private int pacDOtEatenCounter;

    public Score() {
        win = false;
        totalScore = 0;
        ghost = 200;
    }

    /**
     * called when a pacDot is eaten raises the score
     * also keeps track if the player eats all the pacDots the player right
     */
    public void pacDotEaten() {
        if (pacDOtEatenCounter == 241) {
            win = true;
        }
        pacDOtEatenCounter++;
        int PACDOT = 10;
        totalScore += PACDOT;
    }

    /**
     * called when a power up is eaten and raises the score by the corresponding amount
     */
    public void powerPelletEaten() {

        int POWERPELLET = 50;
        totalScore += POWERPELLET;
    }

    /**
     * raises the score by the corresponding amount for eating a ghost
     */
    public void ghostEaten() {
        if (ghostEatenCounter == 4) {
            ghost = 200;
        }
        totalScore += ghost;
        ghost *= 2;
        ghostEatenCounter++;
    }

    /**
     * @return the total score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * @return weather or not the player has won
     */
    public boolean getWin() {
        return win;
    }

}
