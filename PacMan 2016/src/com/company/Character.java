package com.company;

import javax.swing.*;
import java.awt.*;

/**
 * the generic character class all characters inherit these traits
 */
public class Character {
    static public MapLayout mapLayout;
    int xAxisVisualSpawn;
    int yAxisVisualSpawn;
    int xAxisVisual;
    int yAxisVisual;
    int xSpawnMap;
    int ySpawnMap;
    boolean alive;
    int color;
    int lastAxisXVisual;
    int lastAxisYVisual;
    int deltaX;
    int deltaY;
    String up;
    String right;
    String down;
    String left;
    String orientation;
    boolean moving;
    int animationTimer;

    Character() {
        mapLayout = new MapLayout();

    }

    /**
     * @param orientation the direction of the skin
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    /**
     * @param g updates the graphic representation of the character onto the screen
     */
    public void updateCharacter(Graphics g) {
        ImageIcon image = new ImageIcon(orientation);
        g.drawImage(image.getImage(), xAxisVisual, yAxisVisual, null);

    }

    /**
     * @param distance  the total distance needed to pass on the visual representation of the character
     * @param xDistance the xAxis distance from the last distance
     * @param yDistance the yAxis distance from the last distance
     * @return
     */
    public boolean movable(int distance, int xDistance, int yDistance) {
        if (xDistance == distance || yDistance == distance) {
            moving = true;
            return true;
        }
        moving = false;
        return false;
    }

    /**
     * @param deltaX     the change in the MapLayouts xAxis
     * @param deltaY     the change in the MapLayouts yAxis
     * @param characterX the current xAxis position of the character on the MapLayout
     * @param characterY the current yAxis position of the character on the MapLayout
     * @param color      the integer representation on the MapLayout of the character 5-9
     */
    public void mapUpdate(int deltaX, int deltaY, int characterX, int characterY, int color) {
        int xDistance = Math.abs(xAxisVisual - lastAxisXVisual);
        int yDistance = Math.abs(yAxisVisual - lastAxisYVisual);
        if (movable(20, xDistance, yDistance)) {
            mapLayout.movePositions(characterY, characterX, characterY + deltaY,
                    characterX + deltaX, color);
            lastAxisXVisual = xAxisVisual;
            lastAxisYVisual = yAxisVisual;
        }
    }

    /**
     * @param deltaX the change in the xAxis visual
     * @param deltaY the change in the yAxis visual
     */
    public void move(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        mapUpdate(deltaX, deltaY, mapLayout.getCharacterX(color), mapLayout.getCharacterY(color), color);
        if (deltaX != 0) {
            xAxisVisual += deltaX;
        } else {
            yAxisVisual += deltaY;
        }

    }
}
