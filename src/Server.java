import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


class WCL implements Runnable{
    Socket cs;
    int count = 0;
    public WCL(Socket cs, int count){
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
            is = cs.getInputStream();
            os = cs.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);

            String name = dis.readUTF();
            System.out.println("Client № " + count + " named " + name);

            dos.writeUTF("Hello " + name + "!");

            cs.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
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
}
