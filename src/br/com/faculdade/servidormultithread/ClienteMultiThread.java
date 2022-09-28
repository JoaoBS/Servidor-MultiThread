package br.com.faculdade.servidormultithread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteMultiThread extends Thread {

    private final String SERVER_ADDRESS = "127.0.0.1";
    private Socket clientSocket;
    private Scanner scanner;
    private BufferedWriter out;
    private BufferedReader in;

    public ClienteMultiThread() {
        scanner = new Scanner(System.in);

    }

    public void run() {
        try {
            clientSocket = new Socket(SERVER_ADDRESS, ServidorMultiThread.PORTA);
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println("Cliente conectado no servidor em: " + SERVER_ADDRESS + ":" + ServidorMultiThread.PORTA);

            numEnviado();

        } catch (Exception e) {
            System.out.println("Erro na execução do programa: " + e);
        }
    }

    private void numEnviado() throws IOException {
        int num;
        System.out.print("Informe um valor a ser enviado para o servidor: ");
        num = scanner.nextInt();
        out.write(num);
        out.newLine();
        out.flush();

        int dobroDoNumero = in.read();

        System.out.println(
                "Valor enviado para o servidor foi: " + num
                + "\nValor recebido pelo servidor foi: : " + dobroDoNumero);
        
        System.out.println("Cliente finalizado");
        clientSocket.close();
    }

    public static void main(String[] args) {
        ClienteMultiThread cliente = new ClienteMultiThread();
        cliente.start();
        
    }

}