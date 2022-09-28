package br.com.faculdade.servidormultithread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMultiThread extends Thread {

    public static final int PORTA = 4000;
    private static ServerSocket serverSocket;
    private BufferedWriter out;
    private BufferedReader in;

    public void run() {
        try {
            serverSocket = new ServerSocket(PORTA);
            System.out.println("Servidor iniciado na porta: " + PORTA);
            clietConnection();
        } catch (Exception e) {
        }
    }

    private void clietConnection() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente: " + clientSocket.getLocalAddress().getHostAddress() + " Conectou!");
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            int num = in.read();
            int dobroDoNumero = num * 2;
            
            System.out.println(
                    "O cliente: " + clientSocket.getLocalAddress().getHostAddress()
                    + " enviou o valor: " + num + "\nE o valor a ser devolvido para o cliente será: " + dobroDoNumero);

            out.write(dobroDoNumero);
            out.newLine();
            out.flush();
            
            System.out.println("Servidor finalizado! ");
            
            serverSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ServidorMultiThread server = new ServidorMultiThread();
        server.start();
    }

}