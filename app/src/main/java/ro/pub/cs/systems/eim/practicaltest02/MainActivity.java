package ro.pub.cs.systems.eim.practicaltest02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    Button startService;
    private ServerThread serverThread;
    Button clientStartRequest;

    TextView responseTextView;
    EditText serverPortEditText, clientConnectIpAdd, clientConnectPort, clientVal1, clientVal2;
    Button opAdd, opMul;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseTextView = findViewById(R.id.resultEditText);
        startService = findViewById(R.id.StarServerButton);
        serverPortEditText = findViewById(R.id.ServerPortEditText);
        clientConnectPort = findViewById(R.id.ClientConnectPortEditText);
        clientConnectIpAdd = findViewById(R.id.ClientConnectAddressEditText);
        clientVal1 = findViewById(R.id.ClientRequestDataVal1);
        clientVal2 = findViewById(R.id.ClientRequestDataVal2);
        opAdd = findViewById(R.id.SendRequestButtonAdd);
        opMul = findViewById(R.id.SendRequestButtonMul);

        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serverThread = new ServerThread(Integer.parseInt(serverPortEditText.getText().toString()));
                serverThread.start();

            }
        });



        opAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1, op2;
                op1 = clientVal1.getText().toString();
                op2 = clientVal2.getText().toString();
                ClientThread clientThread = new ClientThread(
                        Integer.parseInt(clientConnectPort.getText().toString()),
                        clientConnectIpAdd.getText().toString(),
                        "add," + op1 + "," + op2 + "\n",
                        responseTextView);
                clientThread.start();
            }
        });

        opMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1, op2;
                op1 = clientVal1.getText().toString();
                op2 = clientVal2.getText().toString();
                ClientThread clientThread = new ClientThread(
                        Integer.parseInt(clientConnectPort.getText().toString()),
                        clientConnectIpAdd.getText().toString(),
                        "mul," + op1 + "," + op2 + "\n",
                        responseTextView);
                clientThread.start();
            }
        });
    }
}