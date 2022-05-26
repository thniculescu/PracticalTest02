package ro.pub.cs.systems.eim.practicaltest02;


import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {


    int port;
    String address;
    String request_data;
    TextView responseTextView;

    ClientThread(int port, String address, String request_data, TextView responseTextView) {
        this.port = port;
        this.address = address;
        this.request_data = request_data;
        this.responseTextView = responseTextView;
    }

    @Override
    public void run() {
        try {
            Log.e("Eroare", port + address + request_data);
            Socket socket = new Socket(address, port);
            //* Add logs and checks */

            BufferedReader bufferedReader = Utils.getReader(socket);
            PrintWriter printWriter = Utils.getWriter(socket);

            /* data;data2*/
            printWriter.println(request_data);

            String response = bufferedReader.readLine();
            Log.d("Eroare", "SERV A RASP: " + response);


            responseTextView.post(new Runnable() {
                @Override
                public void run() {
                    responseTextView.append(response + "\n");
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
