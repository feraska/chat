import java.io.*;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {
    private ServerSocket server;
    private String ip;

    private int port;

    public Server(String ip,int port){
        this.ip=ip;
        this.port=port;

    }



    @Override
    public void run() {

        try
        {
           server = new ServerSocket(port,0,InetAddress.getByName(ip));


            while(!server.isClosed())
            {
                System.out.println("wait to connection : "+ ip + ":" + port);
                Socket sockk = server.accept();//wait to connection server to client\
                //SocketChannel socketChannel=serverSocketChannel.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(sockk.getInputStream()));
                String clientName=bufferedReader.readLine();
                ClientThread clientThread = new ClientThread(sockk,clientName);
                System.out.println("Connection Successfully "+clientName);
              //  bufferedReader.close();
                clientThread.start();
            }
        }
        catch (IOException e)
        {

            try
            {
                server.close();
            }
            catch (Exception e1)
            {

            }

        }
        //for (Socket socket:list) {


       // }



    }



}
