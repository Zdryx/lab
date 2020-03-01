package com.ivan.server;

import com.sun.jmx.snmp.SnmpUnsignedInt;

import java.io.*;

public class ParseFile {
    private File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public boolean checkLine(String line) {
        String[] splitArrayLine = line.split(";");
        // Проверяем количество аргументов
        if (splitArrayLine.length == 5) {
            for (int i = 0; i <= splitArrayLine.length - 1; i++) {
                System.out.println(splitArrayLine[i]);
            }
            //Проверяем правильность mac-адреса
            String[] splitArrayMac = splitArrayLine[0].split(":");
            //Проверка длины
            if (splitArrayMac.length == 6) {
                // Проверка диапазона значений mac
                boolean flagCheckMac = false;
                for (int i = 0; i <= splitArrayMac.length - 1; i++) {
                    if (Integer.valueOf(splitArrayMac[i],16) >= 0 && Integer.valueOf(splitArrayMac[i], 16) <= 255) {
                        flagCheckMac = true;
                    } else {
                        flagCheckMac = false;
                    }
                }
                if (flagCheckMac) {
                    //Проверка формата данных: омманд, координат и цвета
                    try {
                        if (splitArrayLine[1].equals("start") || splitArrayLine[1].equals("move")) {
                            if (Double.valueOf(splitArrayLine[2]) >= 0 && Double.valueOf(splitArrayLine[2]) <= 1 &&
                                    Double.valueOf(splitArrayLine[3]) >= 0 && Double.valueOf(splitArrayLine[3]) <= 1 &&
                                    Integer.valueOf(splitArrayLine[4].trim()) == -16777216) {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    }catch (NumberFormatException e){
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

}
/*
        try {
            FileInputStream fileInputStream = new FileInputStream(file.getPath());

            label.setText(packPath);
            FileOutputStream fileOutputStream = new FileOutputStream(packPath);
            int i;
            int iPrev = 0;
            boolean flagIs = false;
            boolean signalFlag = false;
            String line = "";
            long count = 0;
            while ((i = fileInputStream.read()) != -1) {
                if (signalFlag) {
                    if (flagIs && (char) i != '\n') {
                        if ((char) i == ' ') {
                            buffer = buffer + '_';
                        } else if ((char) i == '/') {
                            flagIs = false;
                            count--;
                        } else {
                            buffer = buffer + (char) i;
                        }
                    } else {
                        flagIs = false;
                    }
                    System.out.println((char) i);
                    if ((char) i == '=' && (char) iPrev == 'm') {
                        count++;
                        flagIs = true;

                        if ((buffer.toCharArray()[buffer.length() - 1]) != '\n') {
                            buffer = buffer + "\n";
                        }
                        System.out.println("yes");
                    }
                    iPrev = i;
                } else {
                    if ((char) i != '\n') {
                        line += (char) i;
                    } else {
                        if (line.contains("ObjTp=Sg")) {
                            signalFlag = true;
                            System.out.println("yes" + line);
                        }
                        line = "";
                    }
                }
            }

            // перевод строки в байты
            buffer += "Количество переменных=" + count;
            byte[] textForWrite = buffer.getBytes();
            fileOutputStream.write(textForWrite, 0, textForWrite.length);

        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

*/

