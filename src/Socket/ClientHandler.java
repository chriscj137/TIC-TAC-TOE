/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tictactoe.DB;
import tictactoe.Models.GameUser;

/**
 *
 * @author chris
 */
public class ClientHandler implements Runnable {

    private final DataInputStream dis;
    private final DataOutputStream dos;
    private final Socket s;
    private GameUser gu;

    /**
     *
     * @param socket the Socket of the client
     * @param dis the DataInputStream of the socket, used to hook an Input
     * Stream to the Socket communication
     * @param dos the DataOutputStream of the socket, used for posting to the
     * other end of the communication
     */
    public ClientHandler(Socket socket, DataInputStream dis, DataOutputStream dos) {
        this.s = socket;
        this.dis = dis;
        this.dos = dos;
    }

    /**
     * Main code for the ClientHandler. Ensure there's a way to end execution
     * (no infinite loops), and properly close the socket before returning
     */
    @Override
    public void run() {
        String received;
        String sent;
        Boolean endCommunication = false;

        while (!endCommunication) {
            try {
                received = dis.readUTF();

                switch (received) {
                    case "login":
                        login();
                        break;
                    case "register":
                        register();
                        break;
                    case "logout":
                        logout();
                        break;
                    case "exit":
                        logout();
                        endCommunication = false;
                        break;
                }

            } catch (IOException ex) {
            } catch (SQLException ex) {
                Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            dis.close();
            dos.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws IOException if the communication in the DataInputStream failed
     * @throws SQLException if the communication with the database connection
     * pool failed
     */
    private void login() throws IOException, SQLException {
        if (gu == null) {
            String username = dis.readUTF();
            String password = dis.readUTF();

            gu = DB.getUser(username, password);

            if (gu != null) {
                DB.updateConnectionUser(gu.idUser, true);
                System.out.println(gu);
            }
        }
    }

    private void logout() {
        if (gu != null) {
            DB.updateConnectionUser(gu.idUser, false);
        }
    }

    /**
     *
     * @throws IOException if the communication in the DataInputStream failed
     * @throws SQLException if the communication with the database connection
     * pool failed
     */
    private void register() throws IOException, SQLException {
        String username = dis.readUTF();
        String password = dis.readUTF();

        DB.newUser(username, password);
    }
}
