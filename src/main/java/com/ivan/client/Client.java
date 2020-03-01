package com.ivan.client;

import com.ivan.connection.Connect;
import com.ivan.math.Bezye;
import com.ivan.math.TransformationCoodrdinates;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Client extends JFrame{

    private static DrawingComponent drawingComponent;
    private static int height = 1000;
    private static int width = 1920;


    public Client() {
        super("Client");
        /* Выставленеие размера и позиции окна*/
        setBounds(100, 100, 1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel jPanel = new JPanel(new BorderLayout());
        setContentPane(jPanel);
        setSize(width, height);
        drawingComponent = new DrawingComponent();
        jPanel.add(drawingComponent, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        // Начальная точка
        String startCommand = "";
        // Список комманд текущей линии
        ArrayList<String> bufferCommands = new ArrayList<>();
        int count = 0;
        int col = 100;//скважность параметра t в методе Безье

        Client app = new Client(); //Создаем экземпляр нашего приложения

        app.setVisible(true); //С этого момента приложение запущено!
        //app.pack(); /* Подбор оптимального размера */

        while (true) {
            try (Connect connect = new Connect("127.0.0.1", 29288)) {

                System.out.println("Connected to server");

                String response = connect.readLine();
                System.out.println(response);



                String[] splitArrayLine = response.split(";");
                double x = Double.valueOf(splitArrayLine[2]);
                double y = Double.valueOf(splitArrayLine[3]);

                if(splitArrayLine[0].equals("60:21:C0:2A:E0:33")) {
                    //Если пришла первая комманда "start"
                    if (splitArrayLine[1].equals("start") && startCommand.equals("")) {
                        startCommand = response;
                        bufferCommands.add(count, startCommand);
                        count++;
                    }

                    // Добавляем комманды move линию
                    if (splitArrayLine[1].equals("move")) {
                        bufferCommands.add(count, response);
                        count++;
                    }

                    //Вызов построения линии по точкам
                    if (splitArrayLine[1].equals("start") && !startCommand.equals("") && !startCommand.equals(response)) {
                        startCommand = response;

                        //Преобразование координат из относительных в координаты при заданном разрешении
                        TransformationCoodrdinates transformationCoodrdinates = new TransformationCoodrdinates(bufferCommands, count-1, height, width);
                        int[][] coordinates = transformationCoodrdinates.getCoordinates();

                        // Отправим преобразованные координаты в метод преобразования Безье


                        Bezye bezye = new Bezye(coordinates, count-1, col);
                        int[][] finalCoordinates = bezye.getFinalCoordinates();

                        //Нарисуем линию по полученным точкам
                        //DrawingComponent drawingComponent = new DrawingComponent();
                        drawingComponent.setData(finalCoordinates, col);
                        app.repaint(); //перерисовывать при изменении данных

                        bufferCommands.clear();
                        count = 0;

                        bufferCommands.add(count, startCommand);
                        count++;
                    }
                }
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
