package com.company;

import javax.swing.*;
import java.awt.*;

public class Balls extends MapLayout {

    /**
     * the visual representation of the balls on the map
     */
    public Balls() {
    }

    /**
     * @param g creates the image representation of the balls on the map
     */
    public void drawBalls(Graphics g) {
        ImageIcon pacDot = new ImageIcon("Resources/food.png");
        ImageIcon powerPellet = new ImageIcon("Resources/powerBall.png");
        for (int i = 0; i < getROWS() - 1; i++) {
            for (int j = 0; j < getCOLS() - 1; j++) {
                if (getMapPosition(i, j) == 2) {
                    g.drawImage(pacDot.getImage(), j * 20 - 72, i * 20 + 10, null);

                }
                if (getMapPosition(i, j) == 3) {
                    g.drawImage(powerPellet.getImage(), j * 20 - 74, i * 20 + 5, null);

                }

            }

        }
    }

}
