package com.ivan.server;

import com.ivan.connection.Connect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;

public class GuiForm extends JFrame {
    private JButton openFileButton;
    private JPanel panel;
    private JButton startButton;
    private File file;

    public GuiForm() throws HeadlessException {
        setContentPane(panel);
        setVisible(true);
        //setSize(400, 400);
        setBounds(400, 400, 800, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        openFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                int ret = fileopen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    try{
                        file = fileopen.getSelectedFile();
                    }
                    catch (NullPointerException err){
                        err.printStackTrace();

                    }

                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                ParseFile parseFile = new ParseFile(file);
                try {
                    int i;
                    // Запускаем сервер
                    ServerSocket server = new ServerSocket(29288);
                    System.out.println("Server started!");

                    FileInputStream fileInputStream = new FileInputStream(file.getPath());
                    String line = "";
                    while ((i = fileInputStream.read()) != -1) {
                        if (i != '\n') {
                            line = line + (char) i;
                        } else {
                            boolean isChecked = parseFile.checkLine(line);
                            System.out.println("Check=" + isChecked);
                            if (isChecked) {
                                Connect connect = new Connect(server);
                                String finalLine = line;
                                new Thread(() -> {
                                    connect.writeLine(finalLine);
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e1) {
                                        throw new RuntimeException(e1);
                                    }
                                    try {
                                        connect.close();
                                    } catch (IOException e2) {
                                        throw new RuntimeException(e2);
                                    }
                                }).start();
                                /*Thread thread = new Thread(new Runnable() {
                                    public void run() {
                                        connect.writeLine(finalLine);
                                        //System.out.println("Request: " + request);
                                        try {
                                            Thread.sleep(1000);
                                        } catch (InterruptedException e1) {
                                            throw new RuntimeException(e1);
                                        }
                                        try {
                                            connect.close();
                                        } catch (IOException e2) {
                                            throw new RuntimeException(e2);
                                        }
                                    }
                                });
                                thread.start();*/
                            }
                            line = "";
                        }

                    }

                } catch (IOException err) {
                    System.out.println(err.getMessage());
                }
            }
        });
    }

}
