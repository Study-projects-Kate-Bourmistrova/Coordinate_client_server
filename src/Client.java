import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    int port = 8000;
    InetAddress host;
    Socket clientSocket;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    public Client(){
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            clientSocket = new Socket(host, port);
            System.out.println("Client started");
            is = clientSocket.getInputStream();
            os = clientSocket.getOutputStream();
            dis = new DataInputStream(is);
            dos = new DataOutputStream(os);

            dos.writeUTF("Kate");
            String ans = dis.readUTF();
            System.out.println("Ans : " + ans);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}
