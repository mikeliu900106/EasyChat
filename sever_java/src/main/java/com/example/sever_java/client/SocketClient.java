package com.example.sever_java.client;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {


    public static void main(String[] args) throws IOException {
        Socket socket = null;
        socket = new Socket("127.0.0.1",1024);
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter printStream = new PrintWriter(outputStream);
        System.out.println("請輸入內容");
        new Thread(()->{
            while (true){
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();
                    printStream.println(input);
                    printStream.flush();
            }
        }).start();

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        new Thread(()->{
            while (true){
                try {
                    String readLine = bufferedReader.readLine();
                    System.out.println("收到服務端消息->"+readLine);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }).start();

    }
}
