package com.company;


import java.awt.*;
import java.io.File;
import java.util.Scanner;

/**
 * controls the validation of the game on a 2D array
 */
public class MapLayout {
    private static int gameGrid[][];
    private Scanner s;
    private final int ROWS = 32;
    private final int COLS = 37;
    private int pacManX;
    private int pacManY;
    private int pacManXSpawn;
    private int pacManYSpawn;
    private boolean powerUp;
    private int redGhostTemp;
    private int blueGhostTemp;
    private int pinkGhostTemp;
    private int orangeGhostTemp;
    private int blueGhostX;
    private int blueGhostY;
    private int redGhostX;
    private int redGhostY;
    private int redGhostXSpawn;
    private int redGhostYSpawn;
    private int pinkGhostXSpawn;
    private int pinkGhostYSpawn;
    private int blueGhostXSpawn;
    private int blueGhostYSpawn;
    private int orangeGhostXSpawn;
    private int orangeGhostYSpawn;
    private int pinkGhostX;
    private int pinkGhostY;
    private int orangeGhostX;
    private int orangeGhostY;
    static private Score playerScore;
    private int powerUpCounter;
    private boolean redDoorOpen;
    private boolean blueDoorOpen;
    private boolean pinkDoorOpen;
    private boolean orangeDoorOpen;
    private boolean redAlive;
    private boolean blueAlive;
    private boolean pinkAlive;
    private boolean orangeAlive;
    private boolean pacManAlive;
    private int doorTimer;


    public MapLayout() {

        gameGrid = new int[ROWS][COLS];
        playerScore = new Score();
        pacManAlive = true;
        redAlive = true;
        blueAlive = true;
        pinkAlive = true;
        orangeAlive = true;
        powerUp = false;
        powerUpCounter = 0;
        redDoorOpen = true;
        pinkDoorOpen = true;
        blueDoorOpen = true;
        orangeDoorOpen = true;
        openFileMap();
        createMap();

    }

    /**
     * creates the 2D representation of the map using the file containing the map
     * initalized pacman and ghost positions
     */
    private void createMap() {

        for (int i = 0; i < ROWS - 1; i++) {
            for (int j = 0; j < COLS - 1; j++) {
                gameGrid[i][j] = Integer.parseInt(s.next());
                switch (gameGrid[i][j]) {
                    case 5:
                        pacManX = j;
                        pacManY = i;
                        pacManXSpawn = j;
                        pacManYSpawn = i;
                        break;
                    case 6:
                        redGhostX = j;
                        redGhostY = i;
                        redGhostXSpawn = j;
                        redGhostYSpawn = i;
                        break;
                    case 7:
                        pinkGhostX = j;
                        pinkGhostY = i;
                        pinkGhostXSpawn = j;
                        pinkGhostYSpawn = i;
                        break;
                    case 8:
                        blueGhostX = j;
                        blueGhostY = i;
                        blueGhostXSpawn = j;
                        blueGhostYSpawn = i;
                        break;
                    case 9:
                        orangeGhostX = j;
                        orangeGhostY = i;
                        orangeGhostXSpawn = j;
                        orangeGhostYSpawn = i;
                        break;
                }
            }
        }
    }

    /**
     * opens the text file that contains the map
     */
    private void openFileMap() {
        try {
            s = new Scanner(new File("Resources/MapLayout.txt"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param row the row of the 2D array
     * @param col the col of the 2D array
     * @return
     */
    public int getMapPosition(int row, int col) {
        return gameGrid[row][col];
    }

    /**
     * @param row   the row of the 2D array
     * @param col   the col of the 2D array
     * @param value the value that you would like to set the position to
     */
    public void setMapPosition(int row, int col, int value) {
        gameGrid[row][col] = value;
    }

    /**
     * @param nextRow the next row position that will be checked
     * @param nextCol the next col position that will be checked
     * @param color   the integer representation of the characters
     * @return returns weather or not the next position is allowable to be moved to
     */
    public boolean noCollisionDetected(int nextRow, int nextCol, int color) {
        doorTimer++;
        if (gameGrid[nextRow][nextCol] == 1) {
            return false;
        }
        if (gameGrid[nextRow][nextCol] == 4 && doorTimer < 20000 && color > 6 && getGhostAlive(color)) {
            return false;
        } else if ((gameGrid[nextRow][nextCol] == 4 && doorTimer < 40000 && color > 7) && getGhostAlive(color)) {
            return false;
        } else if ((gameGrid[nextRow][nextCol] == 4 && doorTimer < 60000 && color > 8) && getGhostAlive(color)) {
            return false;
        } else if ((gameGrid[nextRow][nextCol] == 4 && doorTimer < 80000 && color > 9) && getGhostAlive(color)) {
            return false;
        } else if (gameGrid[nextRow][nextCol] == 4 && !getGhostDoorOpen(color)) {
            return false;
        }
        if (color == 5 && gameGrid[nextRow][nextCol] == 4) {
            return false;
        }
        return gameGrid[nextRow][nextCol] <= 5 || color <= 5;
    }

    /**
     * @return pacmans X coordinate on the 2D array
     */
    public int getPacManX() {

        return pacManX;
    }

    /**
     * @return pacmans y coordinate on the 2D array
     */
    public int getPacManY() {

        return pacManY;
    }

    /**
     * @param color the integer representation of the ghost
     * @return the color ghosts x coordinate
     */
    public int getCharacterX(int color) {
        switch (color) {
            case 5:
                return pacManX;
            case 6:
                return redGhostX;
            case 7:
                return pinkGhostX;

            case 8:
                return blueGhostX;

            case 9:
                return orangeGhostX;
        }
        return -1;
    }

    /**
     * @param color the integer representation of the ghost
     * @return the color ghosts y coordinate
     */
    public int getCharacterY(int color) {
        switch (color) {
            case 5:
                return pacManY;
            case 6:
                return redGhostY;
            case 7:
                return pinkGhostY;

            case 8:
                return blueGhostY;
            case 9:
                return orangeGhostY;
        }
        return -1;
    }

    /**
     * @param color the integer representation of the ghost
     * @param x     the x coordinate of the ghost on the 2D array
     * @param y     the y coordinate of the ghost on the 2D array
     */
    public void setGhostCoordinates(int color, int x, int y) {
        switch (color) {
            case 6:
                redGhostX = x;
                redGhostY = y;
                break;
            case 7:
                pinkGhostX = x;
                pinkGhostY = y;
                break;

            case 8:
                blueGhostX = x;
                blueGhostY = y;
                break;
            case 9:
                orangeGhostX = x;
                orangeGhostY = y;
                break;
        }

    }

    /**
     * @param currentRow the current row on the 2D array of the character
     * @param currentCol the current col on the 2D array of the character
     * @param nextRow    the next row on the 2D array that the character wants to move to
     * @param nextCol    the next col on the 2D array that the character wants to move to
     * @param character  the integer representation of the character
     */
    public void movePositions(int currentRow, int currentCol, int nextRow, int nextCol, int character) {

        switch (character) {
            case 5:
                checkIfEaten(currentRow, currentCol, nextRow, nextCol, character);
                if (gameGrid[nextRow][nextCol] == 2) {
                    playerScore.pacDotEaten();
                }
                if (gameGrid[nextRow][nextCol] == 3) {
                    playerScore.powerPelletEaten();
                    powerUp = true;
                    powerUpCounter++;
                }
                checkIfEaten(currentRow, currentCol, nextRow, nextCol, character);
                gameGrid[currentRow][currentCol] = 0;
                pacManX = nextCol;
                pacManY = nextRow;
                gameGrid[nextRow][nextCol] = 5;

                break;
            case 6:
                ghostSwap(6, redGhostTemp, currentRow, currentCol, nextRow, nextCol);


                break;
            case 7:
                ghostSwap(7, pinkGhostTemp, currentRow, currentCol, nextRow, nextCol);

                break;
            case 8:
                ghostSwap(8, blueGhostTemp, currentRow, currentCol, nextRow, nextCol);
                break;
            case 9:
                ghostSwap(9, orangeGhostTemp, currentRow, currentCol, nextRow, nextCol);
                break;
        }
    }

    /**
     * @param color the integer representation of the ghost
     * @return returns the corresponding ghosts 2D array  x spawn location
     */
    public int getGhostXSpawn(int color) {
        switch (color) {
            case 6:
                return redGhostXSpawn;
            case 7:
                return pinkGhostXSpawn;
            case 8:
                return blueGhostXSpawn;
            case 9:
                return orangeGhostXSpawn;
        }
        return -1;
    }

    /**
     * @param color the integer representation of the ghost
     * @return returns the corresponding ghosts 2D array  y spawn location
     */
    public int getGhostYSpawn(int color) {
        switch (color) {
            case 6:
                return redGhostYSpawn;
            case 7:
                return pinkGhostYSpawn;
            case 8:
                return blueGhostYSpawn;
            case 9:
                return orangeGhostYSpawn;
        }
        return -1;
    }

    /**
     * @return the powerUp boolean
     */
    public boolean getPowerUp() {

        return powerUp;
    }

    /**
     * @param currentRow the characters current row on the 2D array
     * @param currentCol the characters current col on the 2D array
     * @param nextRow    the characters next row on the 2D array
     * @param nextCol    the characters next col on the 2D array
     * @param character  the integer representation of the character
     */
    public void checkIfEaten(int currentRow, int currentCol, int nextRow, int nextCol, int character) {
        boolean pacManEatable = gameGrid[nextRow][nextCol] == 5 && !powerUp
                || gameGrid[currentRow][currentCol] == 5 && !powerUp;
        if (powerUpCounter > 0) {
            powerUpCounter++;
            if (powerUpCounter > 100) {
                powerUpCounter = 0;
                powerUp = false;
            }
        }
        switch (character) {
            case 5:
                if (getGhostAlive(gameGrid[currentRow][currentCol]) && powerUp || (getGhostAlive(gameGrid[nextRow][nextCol]) && powerUp)) {
                    setGhostAlive(gameGrid[currentRow][currentCol], false);
                    setGhostAlive(gameGrid[nextRow][nextCol], false);
                    playerScore.ghostEaten();


                }
                break;
            case 6:
                if (pacManEatable && getGhostAlive(6)) {

                    mapReset();
                }
                break;
            case 7:
                if (pacManEatable && getGhostAlive(7)) {

                    mapReset();
                }
                break;
            case 8:
                if (pacManEatable && getGhostAlive(8)) {

                    mapReset();
                }
                break;
            case 9:
                if (pacManEatable && getGhostAlive(9)) {
                    mapReset();
                }
                break;
        }
    }

    /**
     * @param color the integer representation of the ghost
     * @return the corresponding ghost alive boolean to see weather the ghost is alive or not
     */
    public boolean getGhostAlive(int color) {
        switch (color) {
            case 6:
                return redAlive;
            case 7:
                return pinkAlive;
            case 8:
                return blueAlive;
            case 9:
                return orangeAlive;
        }
        return false;
    }

    /**
     * @param color the integer representation of the ghost
     * @param alive sets the ghost alive status to the corresponding color
     */
    public void setGhostAlive(int color, boolean alive) {
        switch (color) {
            case 6:
                redAlive = alive;
                break;
            case 7:
                pinkAlive = alive;
                break;
            case 8:
                blueAlive = alive;
                break;
            case 9:
                orangeAlive = alive;
                break;
        }
    }

    /**
     * @return the pacmanAlive boolean status
     */
    public boolean getPacManAlive() {
        return pacManAlive;
    }

    /**
     * @param alive the pacManAlive boolean status
     */
    public void setPacManAlive(boolean alive) {
        pacManAlive = alive;
    }

    /**
     * @param color the integer representation of the ghost
     * @return weather or not the corresponding ghost door is open or not
     */
    private boolean getGhostDoorOpen(int color) {
        switch (color) {
            case 6:
                return redDoorOpen;

            case 7:
                return pinkDoorOpen;

            case 8:
                return blueDoorOpen;

            case 9:
                return orangeDoorOpen;

        }
        return false;
    }

    /**
     * @param color the integer representation of the ghost
     * @param value sets the corresponding ghost door boolean status
     */
    public void setGhostDoorOpen(int color, boolean value) {

        switch (color) {
            case 6:
                redDoorOpen = value;
                break;

            case 7:
                pinkDoorOpen = value;
                break;

            case 8:
                blueDoorOpen = value;
                break;

            case 9:
                orangeDoorOpen = value;
                break;
        }
    }

    /**
     * resets the values of the positions on the map
     */
    public void mapReset() {
        doorTimer = 0;
        pacManAlive = false;
        pacManX = pacManXSpawn;
        pacManY = pacManYSpawn;
        redGhostTemp = 0;
        pinkGhostTemp = 0;
        blueGhostTemp = 0;
        orangeGhostTemp = 0;
        redAlive = true;
        blueAlive = true;
        pinkAlive = true;
        orangeAlive = true;
        redDoorOpen = true;
        pinkDoorOpen = true;
        blueDoorOpen = true;
        orangeDoorOpen = true;
        setMapPosition(pacManY, pacManX, 0);
        setMapPosition(pacManYSpawn, pacManXSpawn, 5);
    }

    /**
     * @return the playerScore class to be used in the display class
     */
    public Score getPlayerScore() {

        return playerScore;
    }

    /**
     * @param color the integer representation of the ghost
     * @param value the value that the ghost temp will be
     */
    public void setGhostTemp(int color, int value) {
        switch (color) {
            case 6:
                redGhostTemp = value;
                break;

            case 7:
                pinkGhostTemp = value;
                break;

            case 8:
                blueGhostTemp = value;
                break;

            case 9:
                orangeGhostTemp = value;
                break;
        }
    }

    /**
     * @param color      the integer representation of the ghost
     * @param temp       the temp value that the ghost is swapping out with
     * @param currentRow the current row that the ghost is on
     * @param currentCol the current col that the ghost is on
     * @param nextRow    the next row that the ghost will be on
     * @param nextCol    the next col that the ghost will be on
     */
    public void ghostSwap(int color, int temp, int currentRow, int currentCol, int nextRow, int nextCol) {
        if (temp == 5) {
            temp = 0;
        }
        checkIfEaten(currentRow, currentCol, nextRow, nextCol, color);
        gameGrid[currentRow][currentCol] = temp;
        setGhostTemp(color, gameGrid[nextRow][nextCol]);
        gameGrid[nextRow][nextCol] = color;
        setGhostCoordinates(color, nextCol, nextRow);
    }

    /**
     * @return the rows total length of the 2D array
     */
    public int getROWS() {
        return ROWS;
    }

    /**
     * @return the cols total length of the 2D array
     */
    public int getCOLS() {
        return COLS;
    }
}