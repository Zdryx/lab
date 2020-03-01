package com.ivan.math;

import java.util.ArrayList;

public class  TransformationCoodrdinates {
    int[][] commands;

    //Метод возвращает массив списков комманд, где индекс списка - номер комманды в линии
    public TransformationCoodrdinates(ArrayList bufferCommands, int length, int height, int width) {
        commands = new int[length+1][3];

        for(int i = 0; i<=length; i++){
            // Делим i-ую комманду на части
            String[] splitString = bufferCommands.get(i).toString().split(";");
            // Масштабируем координаты под наше разрешение
            // Формируем комманду, номер списка соответствует порядковому номеру координат

            commands[i][0] = (int)(Double.valueOf(splitString[2])*height);
            commands[i][1] = (int)(Double.valueOf(splitString[3])*width);
            commands[i][2] = (int)(Double.valueOf(splitString[4])*1);
        }
    }

    public int[][] getCoordinates() {
        return commands;
    }
}

