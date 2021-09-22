import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

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
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            serverSocket = new ServerSocket(port,0,host);
            System.out.println("Server started");
            clientSocket = serverSocket.accept();
            System.out.println("Client connected");
            is = clientSocket.getInputStream();
            os = clientSocket.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);

            String name = dis.readUTF();
            System.out.println("Client " + name);

            dos.writeUTF("Hello " + name + "!");

            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Server();
    }
}
