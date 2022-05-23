import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String[] commands = {"Start Listening", "Stop Listening", "Quit"};//command choose
        //select choose to perform
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


            String readLine = "";

            do {
                for (int i = 1; i <= commands.length; ++i) {
                    System.out.println(i + ") " + commands[i - 1]);
                }

                System.out.print("Select One Option Please: ");
              //  System.out.println("Working Directory = " + System.getProperty("user.dir"));
                readLine = reader.readLine();
                //choose option
                switch (readLine) {
                    case "1":
                        Server server = new Server("0.0.0.0", 5000);
                        server.start();
                        break;
                    case "2":
                        System.exit(0);
                        break;


                }

            } while (!readLine.equals("3"));

        } catch (Exception e) {


        }


        Server server = new Server("0.0.0.0", 80);
        server.start();
    }
}

