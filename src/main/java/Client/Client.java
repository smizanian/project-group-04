package Client;

import Client.Database.GameDatabase;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static Socket socket1;
    public static DataInputStream dataInputStream1;
    public static DataOutputStream dataOutputStream1;

    public static Socket socket2;
    public static DataInputStream dataInputStream2;
    public static DataOutputStream dataOutputStream2;

    public static void setupSocketConnectionRegister(){
        try{
            socket1 = new Socket("localhost", 8080);
            dataInputStream1 = new DataInputStream(socket1.getInputStream());
            dataOutputStream1 = new DataOutputStream(socket1.getOutputStream());


            socket2 = new Socket("localhost", 8569);
            dataInputStream2 = new DataInputStream(socket2.getInputStream());
            dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());


            GameClient.setSocket(socket1,dataOutputStream1,dataInputStream1);
            GameDatabase.setSocket(socket1,dataOutputStream1,dataInputStream1);
        } catch (IOException e){
            System.out.println("Connection lost");
            System.exit(5);
        }
    }


}
