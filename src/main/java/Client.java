import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    Coordinate coord = new Coordinate(0,0); // get from the sever when created instead
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

            coord.setX_coordinate(5);
            coord.setY_coordinate(205);

            //dos.writeUTF("Kate");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String obj = gson.toJson(coord);

            dos.writeUTF(obj);

            String ans = dis.readUTF();
            Coordinate coord2 = gson.fromJson(ans, Coordinate.class);

            System.out.println("Ans : " + coord2);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Client();
    }
}
