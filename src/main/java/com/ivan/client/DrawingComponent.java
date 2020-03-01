package com.ivan.client;

import javax.swing.*;
import java.awt.*;

public class DrawingComponent extends JPanel {
    int[][] finalCoordinates;
    int n;

    public void setData(int[][] finalCoordinates, int n) {
        this.finalCoordinates = finalCoordinates;
        this.n = n;
    }

    protected void paintComponent (Graphics graphics){
        Graphics2D drp = (Graphics2D)graphics;
        System.out.println("nnnnnnnnnnnnnnn" + n);
        for (int i = 1; i <= n; i++)
        {
            int x1 = finalCoordinates[i-1][1];
            int y1 = finalCoordinates[i-1][0];
            int x2 = finalCoordinates[i][1];
            int y2 = finalCoordinates[i][0];
            drp.drawLine(x1, y1, x2, y2);


        }
    }
}
