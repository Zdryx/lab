package com.ivan.connection;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Connect implements Closeable {
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;


    public Connect(String ip, int port) {
        try {
            this.socket = new Socket(ip, port);
            this.reader = this.createReader();
            this.writer = this.createWriter();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public Connect(ServerSocket Server) {
        try {
            this.socket = Server.accept();
            this.reader = this.createReader();
            this.writer = this.createWriter();
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public void writeLine(String message) {
        try {
            this.writer.write(message);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() {
        try {
            return this.reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BufferedReader createReader() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        return bufferedReader;
    }

    private BufferedWriter createWriter() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        return bufferedWriter;
    }

    public void close() throws IOException {
        this.reader.close();
        this.writer.close();
        this.socket.close();
    }
}