package com.company;


public class Ghost extends Character {

    private String vulnerableWhite;
    private String vulnerableBlue;
    private String dead;
    private boolean inSpawn;
    private boolean goingToSpawn;
    private boolean checkPoint;

    /**
     * @param right        the right orientation image
     * @param down         the down orientation image
     * @param left         the left orientation image
     * @param up           the up orientation image
     * @param xSpawnVisual the start location of the xAxis of the ghost
     * @param ySpawnVisual the start location of the yAxis of the ghost
     * @param color        the integer representation of the color of the ghost 6 = red , 7 = pink, 8 = blue, 9 = orange
     */
    public Ghost(String right, String down, String left, String up, int xSpawnVisual, int ySpawnVisual, int color) {
        xSpawnMap = mapLayout.getGhostXSpawn(color);
        ySpawnMap = mapLayout.getGhostYSpawn(color);
        checkPoint = false;
        inSpawn = true;
        animationTimer = 0;
        vulnerableBlue = "Resources/ghost_vulnerable_0.png";
        vulnerableWhite = "Resources/ghost_vulnerable_2.png";
        mapLayout.setGhostAlive(color, true);
        dead = "Resources/ghost_died_0.png";
        goingToSpawn = false;
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
        deltaY = -1;
        alive = true;
        xAxisVisual = xSpawnVisual;
        yAxisVisual = ySpawnVisual;
        xAxisVisualSpawn = xSpawnVisual;
        yAxisVisualSpawn = ySpawnVisual;
        lastAxisXVisual = xSpawnVisual;
        lastAxisYVisual = ySpawnVisual;
        this.color = color;
        orientation = down;
    }

    /**
     * @return returns the direction that the ghost should go depending on if the ghost is alive or dead
     */
    public int priority() {
        int pacX = mapLayout.getPacManX();
        int pacY = mapLayout.getPacManY();
        int ghostX = mapLayout.getCharacterX(color);
        int ghostY = mapLayout.getCharacterY(color);
        int targetX;
        int targetY;

        if (inSpawn) {
            targetX = mapLayout.getGhostXSpawn(7);
            targetY = ySpawnMap - 3;
        } else if (alive) {
            targetX = pacX;
            targetY = pacY;
        } else if (goingToSpawn && !checkPoint) {
            targetX = mapLayout.getGhostXSpawn(7);
            targetY = ySpawnMap - 3;
        } else {
            targetX = mapLayout.getGhostXSpawn(7);
            targetY = ySpawnMap;
        }
        int xDistance = Math.abs(targetX - ghostX);
        int yDistance = Math.abs(targetY - ghostY);
        if (xDistance >= yDistance) {
            if (targetX < ghostX) {
                //return left
                return 1;

            } else {
                //return right
                return 2;

            }
        } else {
            if (targetY < ghostY) {
                //return up
                return 3;
            } else {
                //return down
                return 4;
            }
        }
    }

    /**
     * this is how the movement of the ghost is decided it checks to see if the movement that is prioritized is possible
     * if it isnt possible it will move in a random direction
     */
    public void chaseMode() {

        alive = mapLayout.getGhostAlive(color);
        int ghostY = mapLayout.getCharacterY(color);
        int ghostX = mapLayout.getCharacterX(color);
        if (!mapLayout.getPacManAlive()) {
            deltaY = -1;
            deltaX = 0;
            mapLayout.setGhostCoordinates(color, xSpawnMap, ySpawnMap);
            mapLayout.setMapPosition(ghostY, ghostX, 0);
            mapLayout.setMapPosition(ySpawnMap, xSpawnMap, color);
            xAxisVisual = xAxisVisualSpawn;
            yAxisVisual = yAxisVisualSpawn;
            lastAxisXVisual = xAxisVisualSpawn;
            lastAxisYVisual = yAxisVisualSpawn;
            inSpawn = true;
            alive = true;
            checkPoint = false;

        } else if (!alive) {
            goingToSpawn = true;
            mapLayout.setGhostDoorOpen(color, true);
            if (mapLayout.getCharacterY(color) == ySpawnMap - 3 && (mapLayout.getCharacterX(color) == xSpawnMap || mapLayout.getCharacterX(color) == xSpawnMap + 1 || mapLayout.getCharacterX(color) == xSpawnMap - 1)) {
                checkPoint = true;
            }
        } else if (mapLayout.getCharacterY(color) == ySpawnMap - 3 && alive) {
            inSpawn = false;
            goingToSpawn = false;
            mapLayout.setGhostDoorOpen(color, false);
        }
        if (mapLayout.getPowerUp() && alive) {
            moveRandom();
            animation(vulnerableBlue, vulnerableWhite);
        } else if (mapLayout.noCollisionDetected(ghostY, ghostX - 1, color) && moving && priority() == 1) {
            if (alive) {
                setOrientation(left);
            } else {
                setOrientation(dead);
                if (mapLayout.getCharacterY(color) == ySpawnMap && checkPoint) {
                    mapLayout.setGhostAlive(color, true);
                    inSpawn = true;
                    mapLayout.setGhostDoorOpen(color, true);
                }
            }
            move(-1, 0);
        } else if (mapLayout.noCollisionDetected(ghostY, ghostX + 1, color) && moving && priority() == 2) {
            if (alive) {
                setOrientation(right);
            } else {
                setOrientation(dead);
                if (mapLayout.getCharacterY(color) == ySpawnMap && checkPoint) {
                    mapLayout.setGhostAlive(color, true);
                    inSpawn = true;
                    checkPoint = false;
                }
            }
            move(1, 0);
        } else if (mapLayout.noCollisionDetected(ghostY - 1, ghostX, color) && moving && priority() == 3) {
            if (alive) {
                setOrientation(up);
            } else {
                setOrientation(dead);
                if (mapLayout.getCharacterY(color) == ySpawnMap && checkPoint) {
                    mapLayout.setGhostAlive(color, true);
                    inSpawn = true;
                    checkPoint = false;
                }
            }
            move(0, -1);
        } else if (mapLayout.noCollisionDetected(ghostY + 1, ghostX, color) && moving && priority() == 4) {
            if (alive) {
                setOrientation(down);
            } else {
                setOrientation(dead);
                if (mapLayout.getCharacterY(color) == ySpawnMap && checkPoint) {
                    mapLayout.setGhostAlive(color, true);
                    inSpawn = true;
                    checkPoint = false;
                }
            }
            move(0, 1);
        } else {

            if (mapLayout.noCollisionDetected(ghostY + deltaY, ghostX + deltaX, color)) {
                move(deltaX, deltaY);
            } else {
                moveRandom();
            }
        }
    }

    /**
     * makes a random number and depending on the number it will move the character in that position
     */
    public void moveRandom() {
        int random;
        random = (int) (Math.random() * 4) + 1;
        if (moving) {
            if (random == 1 && deltaX != 1) {
                deltaX = 1;
                deltaY = 0;
            } else if (random == 2 && deltaX != -1) {
                deltaX = -1;
                deltaY = 0;

            } else if (random == 3 && deltaY != 1) {
                deltaY = 1;
                deltaX = 0;
            } else if (random == 4 && deltaY != -1) {
                deltaY = -1;
                deltaX = 0;
            }
        }
        if (mapLayout.noCollisionDetected(mapLayout.getCharacterY(color) + deltaY, mapLayout.getCharacterX(color) + deltaX, color)) {
            move(deltaX, deltaY);
        }
    }

    /**
     * @param first  the first image used in the ghost animation
     * @param second the second image used in the ghost animation
     */
    public void animation(String first, String second) {
        animationTimer++;

        if (animationTimer == 20) {
            setOrientation(first);
        }
        if (animationTimer == 40) {
            animationTimer = 0;
            setOrientation(second);

        }

    }

}

