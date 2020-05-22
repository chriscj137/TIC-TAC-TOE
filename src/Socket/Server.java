/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import tictactoe.Models.GameUser;

/**
 *
 * @author chris
 */
public class Server {

    private ArrayList<ClientHandler> clientHandlers;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private int port = 5056;
    private ServerSocket serverSocket;

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public ArrayList<GameUser> getConnectedClients() {
        ArrayList<GameUser> gameUsersConnected = new ArrayList();
        for (ClientHandler c : clientHandlers) {
            if (c.getGu().Playing) {
                gameUsersConnected.add(c.getGu());
            }
        }
        return gameUsersConnected;
    }

    /*
            SignUp Process. Referr to DB.java to find declaration.
            1. Insert a new user checking for disponibility.
                //boolean = db.newUser(username, password)
            2. Get the current User with the ConnectionStatus updated.
                //GameUser = db.getUser(username, password)
            This method is also used when the User wants to logIn.
            There's no need to update the status, it's make it by itself.
            3. If a user wants to LogOut just call this method with false value:
                //db.updateConnectionUser(username, false)
     */
    public Server() throws IOException, SQLException {
        this.clientHandlers = new ArrayList<>();
        serverSocket = new ServerSocket(port);
        Socket s = null;
        try {
            while (true) {
                s = serverSocket.accept();

                System.out.println("New client");
                DataInputStream dis = new DataInputStream(s.getInputStream());
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                System.out.println("Create thread");

                ClientHandler ch = new ClientHandler(s, dis, dos, this);
                clientHandlers.add(ch);

                executor.execute(ch);
            }
        } catch (IOException e) {
            s.close();
            e.printStackTrace();
            throw e;
        } finally {
            executor.shutdown();
        }
    }
}
