import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    int port = 8000;
    InetAddress host;
    ServerSocket serverSocket;
    Socket clientSocket;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    public Server() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            serverSocket = new ServerSocket(port,0,host);
            System.out.println("Server started");
            int count = 0;

            while(true) {
                clientSocket = serverSocket.accept();

                count++;
                System.out.println("Client connected");

                WCL task = new WCL(clientSocket,count);
                executor.submit(task);

            }
            //serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server();
    }

    static class WCL implements Runnable {
        Socket cs;
        int count = 0;

        public WCL(Socket cs, int count) {
            this.cs = cs;
            this.count = count;
        }

        @Override
        public void run() {
            InputStream is = null;
            OutputStream os = null;
            DataInputStream dis;
            DataOutputStream dos;

            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                is = cs.getInputStream();
                os = cs.getOutputStream();
                dis = new DataInputStream(is);
                dos = new DataOutputStream(os);

                String coordinates = dis.readUTF();
                System.out.println("Client № " + count + " is here");
                //System.out.println("Json : " + coordinates);

                Coordinate coord = gson.fromJson(coordinates,Coordinate.class);
                //String to_send = gson.toJson(coordinates);

                coord.id = count;
                System.out.println("Json2 : " + gson.toJson(coord));
                dos.writeUTF(gson.toJson(coord));

                cs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
