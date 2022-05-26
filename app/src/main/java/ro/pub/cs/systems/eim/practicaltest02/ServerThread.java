package ro.pub.cs.systems.eim.practicaltest02;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerThread extends Thread{

    private int port;
    private ServerSocket serverSocket;
    private HashMap<String, GenericResults> data;

    public ServerThread(int port) {
        this.port = port;
        try {

            this.serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            Log.e("Eroare", "E busit serverul " + port);
            e.printStackTrace();
        }

        this.data = new HashMap<>();
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client_socket = serverSocket.accept();

                /* TODO, Try to run the following code on a separate thread */
                Thread wkth = new WorkThr(client_socket);
                wkth.start();

            } catch (Exception  e) {
                e.printStackTrace();
            }
        }
    }
}

class WorkThr extends Thread {
    Socket client_socket;

    public WorkThr(Socket client_socket) {
        this.client_socket = client_socket;
    }

    @Override
    public void run() {
        String clientResponse = "";

        try {
            if (client_socket != null) {
                BufferedReader bufferReader = Utils.getReader(client_socket);
                String request_data = bufferReader.readLine();
                Log.e("Eroare", request_data);


                Integer a, b;
                long rez;
                String[] vals = request_data.split(",");
                a = Integer.parseInt(vals[1]);
                b = Integer.parseInt(vals[2]);
                if (vals[0].equals("add")) {
                    rez = a + b;
                } else if (vals[0].equals("mul")) {
                    Thread.sleep(2000);
                    rez = (long) a * b;
                } else {
                    rez = 0;
                }

                if (rez > Integer.MAX_VALUE) {
                    clientResponse = "overflow";
                } else {
                    clientResponse = Integer.toString((int) rez);
                }

                /* Write to client socket the reponse */
                PrintWriter printWriter = Utils.getWriter(client_socket);
                printWriter.println(clientResponse);

                client_socket.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
