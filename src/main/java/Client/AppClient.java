package Client;

import GUI.MainFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AppClient {

    private final static String SERVER_ADDRESS = "127.0.0.1";
    private final static int PORT = 8100;
    Socket socket = null;

    PrintWriter out = null;
    BufferedReader in = null;

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public PrintWriter getOut() {
        return out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    MainFrame mainFrame = null;

    public AppClient() {
        init();
    }

    public Socket getSocket() {
        return socket;
    }

    public void init() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Bine ai venit in aplicatia ReadsProfiler!");
            mainFrame = new MainFrame(this);
            mainFrame.setVisible(true);
        } catch (Exception ex) {
            System.out.println("Error at client initalization: " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        AppClient client = new AppClient();


        boolean timeOut = false;
        long previousTime = System.nanoTime();

        while (true) {


            String request = client.readFromKeyboard();


            previousTime = System.nanoTime();


            if (request.equalsIgnoreCase("exit")) {
                break;
            } else if (timeOut == true) {
                client.sendRequestToServer("timeout");
                timeOut = false;
            } else {
                client.sendRequestToServer(request);
            }
            client.readResponeFromServer();
        }
    }

    private void readResponeFromServer() {
        try {
            String response = in.readLine();
            System.out.println("Am primit: " + response);
        } catch (Exception e) {
            System.out.println("Coulnt read from socket: " + e.getMessage());
        }
    }

    //Implement the sendRequestToServer method
    public void sendRequestToServer(String request) {
        out.println(request);
    }


    private String readFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
