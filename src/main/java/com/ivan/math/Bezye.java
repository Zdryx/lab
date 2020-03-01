package com.ivan.math;


public class Bezye {
    int[][] finalCoordinates;


    public Bezye(int[][] coordinates, int k, int col){
        double t = 0;
        finalCoordinates = new int[col+1][3];
        System.out.println("collllllllll=" + col);
        //Расчитываем финальный набор точек, по которым будет строиться кривая
        for (int i=0; i<=col; i++){
            int[] rezult;
            rezult = calculateBezye(t, coordinates, k);
            t+=0.01;
            finalCoordinates[i][0] = rezult[0];
            finalCoordinates[i][1] = rezult[1];
            //Используем цвет для рисования от первой комманды
            finalCoordinates[i][2] = coordinates[0][2];
        }
    }

    // Расчет факториала
    private double fact(double arg){
        if (arg < 0) throw new RuntimeException("negative argument.");
        if (arg == 0) return 1;

        double rezult = 1;
        for (int i=1; i<=arg; i++)
            rezult *= i;
        return rezult;
    }

    //Расчет координат
    private int[] calculateBezye(double t, int[][] coordinates, int k)
    {
        double x = 0;
        double y = 0;
        int n = k;

        int[] rezult = new int[2];

        for (int i=0; i <= k; i++)
        {
            x += fact(n)/(fact(i)*fact(n-i)) * coordinates[i][0] * Math.pow(t, i) * Math.pow(1-t, n-i);
           //System.out.println("x " + x + " t=" + t);
            y += fact(n)/(fact(i)*fact(n-i)) * coordinates[i][1] * Math.pow(t, i) * Math.pow(1-t, n-i);
        }

        rezult[0] = (int)x;
        //System.out.println("rezult[0]" + rezult[0]);
        rezult[1] = (int)y;
        //System.out.println("rezult[1]" + rezult[1]);
        return rezult;
    }

    public int[][] getFinalCoordinates() {
        return finalCoordinates;
    }
}





/*



    private void drawCurve(ArrayList<Point> points, Graphics painter){
        for (int i = 1; i < points.size(); i++)
        {
            int x1 = (int)(points.get(i-1).getX());
            int y1 = (int)(points.get(i-1).getY());
            int x2 = (int)(points.get(i).getX());
            int y2 = (int)(points.get(i).getY());
            painter.drawLine(x1, y1, x2, y2);
        }
    }*/