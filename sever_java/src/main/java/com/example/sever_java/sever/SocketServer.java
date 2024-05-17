package com.example.sever_java.sever;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Service
public class SocketServer {


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Map<String , Socket> CLIENT_MAP = new HashMap<>();

        serverSocket = new ServerSocket(1024);
        System.out.println("服務器已經啟動");
        while (true){

            Socket socket = serverSocket.accept();
            String ip = socket.getInetAddress().getHostAddress();
            String clientKey = ip + socket.getPort();
            CLIENT_MAP.put(clientKey,socket);
            System.out.println("客戶ip: " + ip + ",端口" +  socket.getPort() );
            new Thread(()->{
                while (true){

                    try {
                        InputStream inputStream = socket.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"utf8");
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        String readLine = bufferedReader.readLine();
                        System.out.println( "收到消息->" + readLine);

                        CLIENT_MAP.forEach((k,v) ->{
                            try {
                                OutputStream outPutStream = v.getOutputStream();
                                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outPutStream,"utf8"));
                                printWriter.println(v.getPort() +":" +   readLine);
                                printWriter.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });


                    } catch (IOException e) {
                        throw new RuntimeException(e);

                    }
                }
            }).start();
        }
    }
}
